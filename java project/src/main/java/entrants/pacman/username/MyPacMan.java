package entrants.pacman.username;

import entrants.BT.Library.BTLibrary;
import jbt.execution.core.*;
import jbt.model.core.ModelTask;
import pacman.controllers.PacmanController;
import pacman.game.Constants.MOVE;
import pacman.game.Game;

/*
 * This is the class you need to modify for your entry. In particular, you need to
 * fill in the getMove() method. Any additional classes you write should either
 * be placed in this package or sub-packages (e.g., entrants.pacman.username).
 */
public class MyPacMan extends PacmanController {
    private MOVE            myMove = MOVE.NEUTRAL;
    private IContext        context;
    private ModelTask       bt;

    public MyPacMan(){
        IBTLibrary btLibrary = new BTLibrary();

        bt = btLibrary.getBT("MsPacMan");

        context = ContextFactory.createContext(btLibrary);
    }

    public MOVE getMove(Game game, long timeDue) {
        context.setVariable("MOVE", MOVE.NEUTRAL);
        context.setVariable("GAME", game);

        IBTExecutor btExecutor = BTExecutorFactory.createBTExecutor(bt, context);

        do{
            btExecutor.tick();
        }while(btExecutor.getStatus() == ExecutionTask.Status.RUNNING);

        return (MOVE) context.getVariable("MOVE");
    }
}