import color.ColorConversions;
import midi.*;
import org.apache.commons.cli.CommandLine;
import validation.Validator;

import javax.sound.midi.Sequence;
import java.awt.*;
import java.io.File;
import java.util.ArrayList;

public class SettingsLoader {

    //================================================================================
    // Properties
    //================================================================================

    private MidiFile midiFile;
    private Sequence sequence;
    private ArrayList<MidiNote> midiNotes;
    private File background;
    private String outputFilePath;
    private int width = 0;
    private int height = 0;
    private Color noteColor;
    private int dim;

    private CommandLine cmd;

    //================================================================================
    // Constructors
    //================================================================================

    public SettingsLoader(CommandLine cmd) throws CommandLineException {
        this.cmd = cmd;
        Conductor.setSettings(load());
    }

    //================================================================================
    // General methods
    //================================================================================

    private Settings load() throws CommandLineException {

        if(cmd.hasOption("i")) {
            String midiPath = cmd.getOptionValue("i");
            try {
                midiFile = new MidiFile(new File(midiPath));
                this.sequence = midiFile.getSequence();

                MidiCropper midiCropper = new MidiCropper(sequence);
                sequence = midiCropper.cropDuplicates();
                NoteExtractor noteExtractor = new NoteExtractor(sequence);
                noteExtractor.renderSequence();

                noteExtractor.transposeSequence();
                this.midiNotes = noteExtractor.simpleToMidiNotes(noteExtractor.getSimpleOnNotes());
                this.sequence = noteExtractor.getSequence();
            }
            catch (MidiFileLoaderException e) {
                e.printStackTrace();
                throw new CommandLineException("Unable to load midi file");
            }
        }

        if(cmd.hasOption("o")) {
            String path = cmd.getOptionValue("o");
            if(Validator.isValidPath(path)) {
                outputFilePath = path;
            }
            else {
                throw new CommandLineException("Output file path is not valid: " + cmd.getOptionValue("o"));
            }
        }

        if (cmd.hasOption("w")) {
            try {
                width = Integer.parseInt(cmd.getOptionValue("w"));
            }
            catch (NumberFormatException e) {
                System.out.println("Invalid width " + cmd.getOptionValue("w") + ". Using default values.");
            }
        }

        if (cmd.hasOption("h")) {
            try {
                height = Integer.parseInt(cmd.getOptionValue("h"));
            }
            catch (NumberFormatException e) {
                System.out.println("Invalid height " + cmd.getOptionValue("h") + ". Using default values.");
            }
        }

        if(cmd.hasOption("b")) {
            if(Validator.isValidImageFile(cmd.getOptionValue("b"))) {
                background = new File(cmd.getOptionValue("b"));
            }
            else {
                throw new CommandLineException("Unable to load background image: " + cmd.getOptionValue("b"));
            }
        }

        if(cmd.hasOption("c")) {
            Color color = ColorConversions.interrogateColor(cmd.getOptionValue("c"));
            if(color != null) {
                noteColor = color;
            }
            else {
                throw new CommandLineException("Invalid color format (Note on color): "  + cmd.getOptionValue("c"));
            }
        }

        if(cmd.hasOption("g")) {
            try {
                dim = Integer.parseInt(cmd.getOptionValue("g"));
            }
            catch (NumberFormatException e) {
                System.out.println("Invalid grid dimension: " + cmd.getOptionValue("g") + ". Using default values.");
            }
        }

        return new Settings(midiFile,
                            sequence,
                            midiNotes,
                            background,
                            outputFilePath,
                            width,
                            height,
                            noteColor,
                            dim);
    }
}
