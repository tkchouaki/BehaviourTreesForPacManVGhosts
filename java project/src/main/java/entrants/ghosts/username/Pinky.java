package entrants.ghosts.username;

//import com.sun.corba.se.impl.encoding.CDROutputStream;
import pacman.controllers.IndividualGhostController;
import pacman.controllers.MASController;
import pacman.game.Constants;
import pacman.game.Game;

/**
 * Created by Piers on 11/11/2015.
 */
public class Pinky extends Ghost {

    public Pinky() {
        super(Constants.GHOST.PINKY);
    }

    @Override
    public Constants.MOVE getMove(Game g, long l)
    {
        return Constants.MOVE.UP;
    }
}
