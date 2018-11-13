
import entrants.ghosts.username.*;
import entrants.utils.logging.GhostLogger;
import entrants.utils.ui.DebugWindow;
import examples.StarterPacMan.MyPacMan;
import examples.StarterPacMan.MyPacMan;
import pacman.Executor;
import pacman.controllers.IndividualGhostController;
import pacman.controllers.MASController;
import pacman.controllers.examples.StarterPacMan;
import pacman.game.Constants.*;
import pacman.game.util.Stats;

import javax.swing.*;
import java.util.Arrays;
import java.util.EnumMap;


/**
 * Created by pwillic on 06/05/2016.
 */
public class Main {

    public static void main(String[] args) {
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

        // Launch debug window
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
        GhostLogger.setup();

        // Run the game
        boolean experience = false;
        if(!experience)
        {
            executor.runGameTimed(new MyPacMan(), new MASController(controllers));
        }
        else
        {
            int nbTrials = 20;
            Double[] scores = new Double[nbTrials];
            for(int i=0; i<nbTrials; i++)
            {
                Stats[] stats = executor.runExperiment(new MyPacMan(), new MASController(controllers), 1, "");
                scores[i] = stats[0].getAverage();
            }
            for(Double score : scores)
            {
                System.out.println(score);
            }
        }
    }
}
