package entrants.ghosts.username;

import entrants.utils.Commons;
import entrants.utils.graph.KnowledgeGraph;
import entrants.utils.graph.Node;
import pacman.controllers.IndividualGhostController;
import pacman.controllers.MASController;
import pacman.game.Constants;
import pacman.game.Game;
import scala.collection.immutable.Stream;

/**
 * Created by Piers on 11/11/2015.
 */
public class Blinky extends IndividualGhostController {
    private KnowledgeGraph graphe;

    public Blinky() {
        super(Constants.GHOST.BLINKY);
        this.graphe = new KnowledgeGraph();
    }

    @Override
    public Constants.MOVE getMove(Game game, long timeDue) {
        Commons.updateKnowledgeGraph(game, graphe);
        return Constants.MOVE.DOWN;
    }
}
