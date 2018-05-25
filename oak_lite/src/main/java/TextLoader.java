import color.ColorConversionException;
import color.ColorConversions;
import org.apache.commons.cli.CommandLine;
import java.awt.*;

public class TextLoader {

    private CommandLine cmd;
    private String title;
    private Color titleColor;

    public TextLoader(CommandLine cmd) throws CommandLineException {
        this.cmd = cmd;
        load();
    }

    private void load() {
        if(cmd.hasOption("t")) {
            title = cmd.getOptionValue("t");
        }
        else {
            title = "";
        }

        if (cmd.hasOption("tc")) {
            try {
                titleColor = ColorConversions.interrogateColor(cmd.getOptionValue("tc"));
            }
            catch (ColorConversionException e) {
                System.out.println("Invalid color: " + cmd.getOptionValue("tc") + " for title color. Defaulting to black");
                titleColor = Color.BLACK;
            }
        }
        else {
            titleColor = Color.BLACK;
        }
        CustomText.createTitleFont(title, titleColor);
    }
}
