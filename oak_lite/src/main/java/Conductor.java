import midi.NoteProcessingException;
import midi.NoteToImage;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

/**
 * Controls the flow of the program.
 */

public class Conductor {

    //================================================================================
    // Properties
    //================================================================================

    private static Settings settings;
    private static ArrayList<BufferedImage> ocarinaSprites;

    //================================================================================
    // General methods
    //================================================================================

    public static void start(String[] args) throws CommandLineException, NoteProcessingException, FrameConstructionException {

        parseCommandLine(args);
        createImageFrames();
    }

    private static void parseCommandLine(String[] args) throws CommandLineException {
        CmdParser parser = new CmdParser(args);

        if(parser.cmd.hasOption("gui")) {
            System.out.println("continuing");
        }
        else {
            parser.loadFromCmdLine();
        }
    }

    private static void createImageFrames() {
        // Get image sprites
        ocarinaSprites = NoteToImage.mapNotesToImages(settings.getMidiNotes());

        // Construct image frame
        ImageFactory imageFactory = new ImageFactory(settings);
        imageFactory.createImages(ocarinaSprites);
    }

    //================================================================================
    // Accessors
    //================================================================================

    public static void setSettings(Settings settings) {
        Conductor.settings = settings;
    }
}
