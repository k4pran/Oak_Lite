import imagemod.ImageTransform;
import imagemod.SpriteRendering;

import java.awt.image.BufferedImage;
import java.util.ArrayList;

/**
 * Constructs the foreground part of the image including padding, coloring, and stitching together of ocarina
 * sprites. Also renders parts of the image transparent to layer image later.
 * @author ryan
 */

public class ForegroundFactory {

    private ArrayList<BufferedImage> foregrounds;
    private int noteOffColor;
    private boolean isFirstFrame = false;

    public ForegroundFactory(int noteColor, boolean isFirstFrame) {
        this.foregrounds = new ArrayList<>();
        this.noteOffColor = noteColor;
        this.isFirstFrame = isFirstFrame;
    }

    public BufferedImage createForeGround(ArrayList<BufferedImage> sprites, int rows, int cols) {

        ArrayList<BufferedImage> foregroundParts;
        if (isFirstFrame) {
            rows -= 1;
        }
        foregroundParts = renderInactiveSprites(sprites, noteOffColor);
        return stitchSprites(renderInactiveSprites(foregroundParts, noteOffColor), rows, cols);
    }

    public ArrayList<BufferedImage> renderInactiveSprites(ArrayList<BufferedImage> sprites, int color) {
        for(int i = 0; i < sprites.size(); i++) {
            sprites.set(i, SpriteRendering.colorSprite(sprites.get(i), color));
            sprites.set(i, SpriteRendering.addTransparency(sprites.get(i)));
        }
        return sprites;
    }

    public BufferedImage stitchSprites(ArrayList<BufferedImage> imageParts, int rows, int cols) {
        if(isFirstFrame) {
            BufferedImage stitchedImage = ImageTransform.stitchImages(imageParts, rows, cols);
            stitchedImage = ImageTransform.padImageBottom(stitchedImage, imageParts.get(0).getHeight());
            return ImageTransform.padImageTop(stitchedImage, imageParts.get(0).getHeight());
        }

        else {
            BufferedImage stitchedImage = ImageTransform.stitchImages(imageParts, rows, cols);
            return ImageTransform.padImageBottom(stitchedImage, imageParts.get(0).getHeight());
        }
    }
}
