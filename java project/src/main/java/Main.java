
import entrants.ghosts.username.*;
import entrants.utils.logging.GhostLogger;
import entrants.utils.ui.DebugWindow;
import examples.StarterPacMan.MyPacMan;
import examples.StarterPacMan.MyPacMan;
import pacman.Executor;
import pacman.controllers.IndividualGhostController;
import pacman.controllers.MASController;
import pacman.game.Constants.*;

import javax.swing.*;
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

        // Run the game
        executor.runGameTimed(new MyPacMan(), new MASController(controllers));
    }
}
