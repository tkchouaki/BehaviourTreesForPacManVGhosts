package entrants.ghosts.username;

import pacman.controllers.IndividualGhostController;
import pacman.controllers.MASController;
import pacman.game.Constants;
import pacman.game.Game;

/**
 * Created by Piers on 11/11/2015.
 */
public class Inky extends Ghost {

    public Inky() {
        super(Constants.GHOST.INKY);
    }

    public Inky(boolean display)
    {
        super(Constants.GHOST.INKY, display);
    }

    @Override
    public Constants.MOVE getMove(Game g, long l)
    {
        return Constants.MOVE.RIGHT;
    }
}
