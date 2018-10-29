
import entrants.ghosts.username.*;
import entrants.utils.ui.DebugWindow;
import examples.StarterPacMan.MyPacMan;
import entrants.pacman.username.MyPacMan;
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

        Executor executor = new Executor.Builder()
                .setVisual(true)
                .setTickLimit(4000)
                .build();

        EnumMap<GHOST, IndividualGhostController> controllers = new EnumMap<>(GHOST.class);

        controllers.put(GHOST.INKY, new Inky());
        controllers.put(GHOST.BLINKY, new Blinky());
        controllers.put(GHOST.PINKY, new Pinky());
        controllers.put(GHOST.SUE, new Sue());

        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                DebugWindow window = new DebugWindow();
                window.getModel().registerAgent(((Blinky) controllers.get(GHOST.BLINKY)));
                window.getModel().registerAgent(((Sue) controllers.get(GHOST.SUE)));
                window.setVisible(true);
            }
        });
        executor.runGameTimed(new MyPacMan(), new MASController(controllers));
    }
}
