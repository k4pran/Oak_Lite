import org.apache.commons.cli.*;

public class CmdParser {

    //================================================================================
    // Properties
    //================================================================================

    CommandLine cmd;

    //================================================================================
    // Constructors
    //================================================================================

    public CmdParser(String[] args) throws CommandLineException {
        cmd = parseCmdLine(args);
    }

    //================================================================================
    // General methods
    //================================================================================

    public void loadFromCmdLine() throws CommandLineException {
        new SettingsLoader(cmd);
        new TextLoader(cmd);
    }

    public CommandLine parseCmdLine(String[] args) throws CommandLineException {
        Options options = new Options();

        options.addOption(Option.builder("h")
                .longOpt("help")
                .desc("Display help.")
                .build());

        options.addOption(Option.builder("i")
                .longOpt("file")
                .desc("Midi input file path. REQUIRED.")
                .hasArg()
                .argName("<file>")
                .required()
                .build());

        options.addOption(Option.builder("o")
                .longOpt("output")
                .desc("Output file path.")
                .hasArgs()
                .argName("<path>")
                .build()
        );

        options.addOption(Option.builder("w")
                .longOpt("width")
                .desc("Scales the output image to width 'w'")
                .hasArg()
                .argName("<pixels>")
                .build()
        );

        options.addOption(Option.builder("h")
                .longOpt("height")
                .desc("Scales the output image to height 'h'")
                .hasArg()
                .argName("<pixels>")
                .build()
        );

        options.addOption(Option.builder("b")
                .longOpt("background")
                .desc("Background image file path. If not set defaults to a white background.")
                .hasArg()
                .argName("<pixels>")
                .build()
        );

        options.addOption(Option.builder("c")
                .longOpt("color")
                .desc("Note colour for ocarina sprites. Default is 'red'.\n" +
                        "Accepted color formats:\n" +
                        "\tColor name - e.g. 'blue' or 'green' -- Accepts most basic colors.\n" +
                        "\tRGB - e.g. 255 0 0 -- R, G and B ranges 0-255, each divided by a space.\n" +
                        "\tHEX - e.g. FF 23 EE -- Hex value ranges 0-FF, each divided by a space.")
                .hasArg()
                .argName("<color>")
                .build()
        );

        options.addOption(Option.builder("t")
                .longOpt("title")
                .desc("Music title text. REQUIRED.")
                .hasArg()
                .argName("<text>")
                .required()
                .build()
        );

        options.addOption(Option.builder("tc")
                .longOpt("title_color")
                .desc("Music title text color.")
                .hasArg()
                .argName("<color>")
                .build()
        );

        options.addOption(Option.builder("g")
                .longOpt("grid")
                .desc("Grid dimensions. e.g. 3 creates a 3*3 grid. Defaults to 5*5")
                .hasArg()
                .argName("<dim>")
                .build()
        );

        try {
            org.apache.commons.cli.CommandLineParser commandLineParser = new DefaultParser();
            CommandLine cmd = commandLineParser.parse(options, args);

            if (cmd.hasOption("h")) {
                HelpFormatter helpFormatter = new HelpFormatter();
                helpFormatter.setLeftPadding(10);
                helpFormatter.setWidth(helpFormatter.getWidth() + 40);
                helpFormatter.setLongOptPrefix(" --");
                helpFormatter.printHelp("Ocarina Factory", options);
                return null;
            }


            return cmd;
        }
        catch (ParseException e) {
            e.printStackTrace();
            throw new CommandLineException("Unable to parse command line.");
        }
    }
}
