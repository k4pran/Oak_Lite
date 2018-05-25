import midi.MidiFile;
import midi.MidiNote;

import javax.sound.midi.Sequence;
import java.awt.*;
import java.io.File;
import java.util.ArrayList;

public class Settings {

    private final MidiFile midiFile;
    private final Sequence sequence;
    private final ArrayList<MidiNote> midiNotes;
    private final File background;
    private final String outputFilePath;
    private final int outputWidth;
    private final int outputHeight;
    private final Color noteColor;
    private final int rows;
    private final int cols;

    public Settings(MidiFile midiFile, Sequence sequence, ArrayList<MidiNote> midiNotes,
                    File background, String outputFilePath, int outputWidth, int outputHeight,
                    Color noteColor, int dim) {
        this.midiFile = midiFile;
        this.sequence = sequence;
        this.midiNotes = midiNotes;
        this.background = background;
        this.outputFilePath = outputFilePath;
        this.outputWidth = outputWidth;
        this.outputHeight = outputHeight;
        this.noteColor = noteColor;
        this.rows = dim;
        this.cols = dim;
    }

    public MidiFile getMidiFile() {
        return midiFile;
    }

    public Sequence getSequence() {
        return sequence;
    }

    public ArrayList<MidiNote> getMidiNotes() {
        return midiNotes;
    }

    public File getBackground() {
        return background;
    }

    public String getOutputFilePath() {
        return outputFilePath;
    }

    public int getOutputWidth() {
        return outputWidth;
    }

    public int getOutputHeight() {
        return outputHeight;
    }

    public int getRows() {
        return this.rows;
    }

    public int getCols() {
        return this.cols;
    }

    public Color getNoteColor() {
        return noteColor;
    }
}
