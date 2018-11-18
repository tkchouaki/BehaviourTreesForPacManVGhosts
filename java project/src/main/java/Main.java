
import entrants.BT.Execution.Actions.Chase;
import entrants.ghosts.username.*;
import entrants.utils.logging.GhostLogger;
import entrants.utils.ui.DebugWindow;
import examples.StarterPacMan.MyPacMan;
import pacman.Executor;
import pacman.controllers.IndividualGhostController;
import pacman.controllers.MASController;
import pacman.game.Constants.*;
import pacman.game.util.Stats;
import org.apache.commons.cli.*;
import javax.swing.*;
import java.util.EnumMap;


/**
 * The entry point of the program.
 * Checks the arguments passed by the command line & then runs the appropriate mode.
 */
public class Main {

    private static final String EXECUTION_NORMAL = "normal";
    private static final String EXECUTION_EXPERIENCE = "experience";
    private static final int DEFAULT_EXPERIENCES_NUMBER = 10;

    public static void main(String[] args) {

        //Default values, no argument is mendatory
        String execution = EXECUTION_NORMAL;
        int experiencesNumber = DEFAULT_EXPERIENCES_NUMBER;
        boolean graphStream = true;

        //Building up the arguments descriptions
        Options options = new Options();


        Option executionOption = new Option("e", "execution", true, "The type of execution (normal or experience). Default is normal");
        executionOption.setArgs(1);
        Option graphstreamOption = new Option("gs", "graphstream", false, "only useful when running in normal mode, no means do not use graphstream, everything else means yes. Default is yes.");
        graphstreamOption.setArgs(1);
        Option numberOfExperiencesOption = new Option("n", "experiencesNumber", true, "only useful when running in experience mode, the number of experiences to run for each chasing mode in the experience execution mode, should be >=1. Default is 10");
        numberOfExperiencesOption.setArgs(1);
        //this one should be an integer, if though it doesn't appear that the parsers checks it.
        numberOfExperiencesOption.setType(Integer.class);

        options.addOption(executionOption);
        options.addOption(graphstreamOption);
        options.addOption(numberOfExperiencesOption);

        CommandLineParser parser = new DefaultParser();
        HelpFormatter helpFormatter = new HelpFormatter();
        CommandLine cmd;

        //Checking if the arguments are passed with accepted values
        //When a bad value is given, prints a help message describing how to use the program & exits.
        try{
            cmd = parser.parse(options, args);
            String executionValue = cmd.getOptionValue("execution");
            if(executionValue!=null)
            {
                executionValue = executionValue.toLowerCase();
                if(executionValue.equals(EXECUTION_EXPERIENCE) || executionValue.equals(EXECUTION_NORMAL))
                {
                    execution = executionValue;
                }
                else{
                    helpFormatter.printHelp("utility-name", options);
                    return;
                }
            }
            String graphstreamValue = cmd.getOptionValue("graphstream");
            if(graphstreamValue != null)
            {
                graphstreamValue = graphstreamValue.toLowerCase();
                graphStream = !graphstreamValue.equals("no");
            }
            String experiencesNumberValue =  cmd.getOptionValue("experiencesNumber");
            if(experiencesNumberValue  != null)
            {
                try{
                    experiencesNumber = Integer.parseInt(experiencesNumberValue);
                }catch(NumberFormatException e){
                    helpFormatter.printHelp("utility-name", options);
                    return;
                }
                if(experiencesNumber <= 0)
                {
                    helpFormatter.printHelp("utility-name", options);
                    return;
                }
            }
        }catch (ParseException e) {
            System.out.println(e.getMessage());
            helpFormatter.printHelp("utility-name", options);
            System.exit(1);
        }

        // Set renderer for graphstream
        System.setProperty("org.graphstream.ui.renderer", "org.graphstream.ui.j2dviewer.J2DGraphRenderer");

        // Create the game
        Executor executor = new Executor.Builder()
                .setVisual(true)
                .setTickLimit(4000)
                .build();

        EnumMap<GHOST, IndividualGhostController> controllers = new EnumMap<>(GHOST.class);

        controllers.put(GHOST.INKY, new Inky());
        controllers.put(GHOST.BLINKY, new Blinky());
        controllers.put(GHOST.PINKY, new Pinky());
        controllers.put(GHOST.SUE, new Sue());

        if(execution.equals(EXECUTION_NORMAL))
        {
            if(graphStream)
            {
                SwingUtilities.invokeLater(new Runnable() {
                    @Override
                    public void run() {
                        DebugWindow window = new DebugWindow();
                        window.getModel().registerAgent((Ghost) controllers.get(GHOST.BLINKY));
                        window.getModel().registerAgent((Ghost) controllers.get(GHOST.SUE));
                        window.getModel().registerAgent((Ghost) controllers.get(GHOST.INKY));
                        window.getModel().registerAgent((Ghost) controllers.get(GHOST.PINKY));

                        // Configure Logger
                        GhostLogger.setup();
                        GhostLogger.setTextArea(window.getLogsArea());

                        window.setVisible(true);
                    }
                });
            }
            executor.runGameTimed(new MyPacMan(), new MASController(controllers));
        }
        else
        {
            GhostLogger.setup();
            for(int strategy=-1; strategy<=4; strategy++)
            {
                Chase.setChasingStrategy(strategy);
                System.out.println("-------------------");
                Double[] scores = new Double[experiencesNumber];
                for(int i=0; i<experiencesNumber; i++)
                {
                    Stats[] stats = executor.runExperiment(new MyPacMan(), new MASController(controllers), 1, "");
                    scores[i] = stats[0].getAverage();
                }
                for(Double score : scores)
                {
                    System.out.println(score.intValue());
                }
            }
        }
    }
}
