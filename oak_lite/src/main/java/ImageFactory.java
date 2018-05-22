import imagemod.ImageTransform;
import me.tongfei.progressbar.ProgressBar;

import java.awt.image.BufferedImage;
import java.util.ArrayList;

/**
 * Responsible for constructing a frame template for each frame to be used in the video.
 * Sprites are the name given to a single ocarina image.
 * A composite of these sprites along with background, titles etc make up a video frame.
 * The first frame (title frame) of the tutorial holds the title so will contain a row
 * less and the rows/cols in the last frame can vary.
 */

public class ImageFactory {

    //================================================================================
    // Properties
    //================================================================================

    private static int defPixelWidth = 800;
    private static int defPixelHeight = 600;

    private Settings settings;
    private int rows;
    private int cols;
    private int spritesPerPage;
    private int titleFrameCount;
    private int lastFrameCount;

    //================================================================================
    // Constructors
    //================================================================================

    public ImageFactory(Settings settings) {
        this.settings = settings;
        this.rows = settings.getROWS();
        this.cols = settings.getCOLS();
    }

    //================================================================================
    // General methods
    //================================================================================

    public void createImages(ArrayList<BufferedImage> sprites) {
        System.out.println("Creating pdf...");
        calculateSpriteCounts(sprites);
        ArrayList<BufferedImage> pdfImages = coordinatePdfCreation(sprites);
        System.out.println("Writing pdf...");
        PdfOutput pdfOutput = new PdfOutput("test", pdfImages);
        pdfOutput.writePdf();
    }

    private void calculateSpriteCounts(ArrayList<BufferedImage> sprites) {
        this.spritesPerPage = rows * cols;
       this.titleFrameCount = sprites.size() >= (rows - 1) * cols ?
                (rows - 1) * cols : sprites.size();
        this.lastFrameCount = (sprites.size() - titleFrameCount) % spritesPerPage == 0 ?
                spritesPerPage : (sprites.size() - titleFrameCount) % spritesPerPage;
    }

    private ArrayList<BufferedImage> coordinatePdfCreation(ArrayList<BufferedImage> sprites) {
        ArrayList<BufferedImage> finishedFrames = new ArrayList<>();
        ArrayList<BufferedImage> spriteSubArr;

        // FIRST
        spriteSubArr = new ArrayList<>(sprites.subList(0, titleFrameCount));
        finishedFrames.add(createPdf(spriteSubArr, true, false));

        // MIDDLE
        int lowerIndex = titleFrameCount;
        int upperIndex = titleFrameCount + spritesPerPage;

        while(upperIndex <= sprites.size() - lastFrameCount) {
            spriteSubArr = new ArrayList<>(sprites.subList(lowerIndex, upperIndex));
            finishedFrames.add(createPdf(spriteSubArr, false, false));
            lowerIndex += spritesPerPage;
            upperIndex += spritesPerPage;
        }

        // LAST
        spriteSubArr = new ArrayList<>(sprites.subList(
                sprites.size() - lastFrameCount, sprites.size()));
        finishedFrames.add(createPdf(spriteSubArr, false, true));

        return finishedFrames;
    }

    private BufferedImage createPdf(ArrayList<BufferedImage> sprites, boolean isTitle, boolean isLast) {

        BufferedImage img;

        img = addForeGround(sprites, isTitle);
        img = addBackground(img);
        img = addTextToImage(img, isTitle);
        img = ImageTransform.scale(img, defPixelWidth, defPixelHeight);

        return img;
    }

    private BufferedImage addForeGround(ArrayList<BufferedImage> sprites, boolean isTitle) {

        ForegroundFactory foregroundFactory = new ForegroundFactory(settings.getNoteColor().getRGB(), isTitle);
        int rows = this.rows;
        return foregroundFactory.createForeGround(sprites, rows, cols);
    }

    public BufferedImage addBackground(BufferedImage image) {
        return ImageTransform.addBackground(image, settings.getBackground());
    }

    public BufferedImage addTextToImage(BufferedImage image, boolean isTitle) {
        if(isTitle) {
            TextFactory.addTitle(image, CustomText.getTitleText(), rows + 1);
        }
        return image;
    }
}
