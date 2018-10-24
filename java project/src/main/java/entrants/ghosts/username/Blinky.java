package entrants.ghosts.username;

import entrants.utils.Commons;
import entrants.utils.graph.DiscreteKnowledgeGraph;
import entrants.utils.graph.KnowledgeGraph;
import entrants.utils.ui.KnowledgeGraphDisplayer;
import pacman.controllers.IndividualGhostController;
import pacman.game.Constants;
import pacman.game.Game;

import java.nio.file.Paths;

/**
 * Created by Piers on 11/11/2015.
 */
public class Blinky extends IndividualGhostController {
    private KnowledgeGraph graph;

    public Blinky() {
        super(Constants.GHOST.BLINKY);
        this.graph = null;
    }

    @Override
    public Constants.MOVE getMove(Game game, long timeDue) {
        if(this.graph == null)
        {
            KnowledgeGraphDisplayer displayer;
            this.graph = Commons.initKnowledgeGraph(game);
            displayer = new KnowledgeGraphDisplayer(new DiscreteKnowledgeGraph(this.graph), "file:///" + Paths.get(".").toAbsolutePath().normalize().toString() + "/src/main/java/entrants/utils/ui/kgraph.css");
            displayer.display();
        }
        else
        {
            Commons.updateKnowledgeGraph(game, this.graph);
        }
        return Constants.MOVE.DOWN;
    }
}
