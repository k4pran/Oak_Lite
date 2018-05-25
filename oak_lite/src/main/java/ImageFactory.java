import imagemod.ImageTransform;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import me.tongfei.progressbar.ProgressBar;

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

    private ProgressBar progressBar;

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
        this.rows = settings.getRows();
        this.cols = settings.getCols();
    }

    //================================================================================
    // General methods
    //================================================================================

    public void createImages(ArrayList<BufferedImage> sprites) {
        progressBar = new ProgressBar("Creating frames", sprites.size());
        progressBar.start();
        calculateSpriteCounts(sprites);
        ArrayList<BufferedImage> pdfImages = coordinatePdfCreation(sprites);
        progressBar.stop();

        PdfOutput pdfOutput = new PdfOutput(settings.getOutputFilePath(), pdfImages);
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
        int width = settings.getOutputWidth();
        int height = settings.getOutputHeight();
        if (width > 0.0 || height > 0.0) {
            width = width > 0.0 ? settings.getOutputWidth() : img.getWidth();
            height = height > 0.0 ? settings.getOutputHeight() : img.getHeight();
            img = ImageTransform.scale(img, width, height);
        }

        if (isLast) {
            progressBar.stepBy(lastFrameCount);
        }
        else {
            progressBar.stepBy(sprites.size());
        }
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
