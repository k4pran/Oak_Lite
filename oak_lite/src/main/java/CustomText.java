import java.awt.*;

public class CustomText {

    //================================================================================
    // Default objects
    //================================================================================

    private static CustomText titleText;

    public static void createTitleFont(String title, Color titleColor) {
        titleText = new CustomText(title, new Font("Baghdad", Font.BOLD, 140),
                titleColor.getRGB());
    }

    //================================================================================
    // Properties
    //================================================================================

    private String text;
    private Font font;
    private int color;

    //================================================================================
    // Constructors
    //================================================================================


    public CustomText(String text, Font font, int color) {
        this.text = text;
        this.font = font;
        this.color = color;
    }

    //================================================================================
    // Accessors
    //================================================================================

    public static CustomText getTitleText() {
        return titleText;
    }

    public Font getFont() {
        return font;
    }

    public String getText() {
        return text;
    }

    public int getColor() {
        return color;
    }
}
