package entrants.ghosts.username;

import pacman.controllers.IndividualGhostController;
import pacman.controllers.MASController;
import pacman.game.Constants;
import pacman.game.Game;

/**
 * Created by Piers on 11/11/2015.
 */
public class Sue extends Ghost {

    public Sue() {
        super(Constants.GHOST.BLINKY);
    }

    public Sue(boolean display)
    {
        super(Constants.GHOST.BLINKY, display);
    }

    @Override
    public Constants.MOVE getMove(Game g, long l)
    {
        return Constants.MOVE.DOWN;
    }
}
