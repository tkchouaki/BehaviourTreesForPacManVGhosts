package entrants.ghosts.username;

import entrants.utils.Commons;
import entrants.utils.graph.AgentKnowledge;
import entrants.utils.graph.DiscreteKnowledgeGraph;
import entrants.utils.ui.KnowledgeGraphDisplayer;
import pacman.controllers.IndividualGhostController;
import pacman.game.Constants;
import pacman.game.Game;
import pacman.game.internal.Maze;

import java.nio.file.Paths;

/**
 * Created by Piers on 11/11/2015.
 */
public class Blinky extends Ghost{

    public Blinky() {
        super(Constants.GHOST.BLINKY);
    }

    public Blinky(boolean display)
    {
        super(Constants.GHOST.BLINKY, display);
    }
}
