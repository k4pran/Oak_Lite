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
    private final Color noteColor;
    private final int ROWS = 3;
    private final int COLS = 3;

    public Settings(MidiFile midiFile, Sequence sequence, ArrayList<MidiNote> midiNotes,
                    File background, String outputFilePath, Color noteColor) {
        this.midiFile = midiFile;
        this.sequence = sequence;
        this.midiNotes = midiNotes;
        this.background = background;
        this.outputFilePath = outputFilePath;
        this.noteColor = noteColor;
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

    public int getROWS() {
        return this.ROWS;
    }

    public int getCOLS() {
        return this.COLS;
    }

    public Color getNoteColor() {
        return noteColor;
    }
}
