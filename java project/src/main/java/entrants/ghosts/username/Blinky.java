package entrants.ghosts.username;

import pacman.game.Constants;

/**
 * Created by Piers on 11/11/2015.
 */
public class Blinky extends Ghost{

    public Blinky() {
        this(false);
    }

    public Blinky(boolean display) {
        super(Constants.GHOST.BLINKY, display);
        initJBT();
    }
}
