package entrants.ghosts.username;

import entrants.utils.Commons;
import entrants.utils.graph.AgentKnowledge;
import entrants.utils.graph.DiscreteKnowledgeGraph;
import entrants.utils.ui.KnowledgeGraphDisplayer;
import pacman.controllers.IndividualGhostController;
import pacman.game.Constants;
import pacman.game.Game;

import java.nio.file.Paths;

/**
 * Created by Piers on 11/11/2015.
 */
public class Blinky extends IndividualGhostController {
    private AgentKnowledge knowledge;
    private DiscreteKnowledgeGraph discreteKnowledgeGraph;

    public Blinky() {
        super(Constants.GHOST.BLINKY);
        this.knowledge = null;
    }

    @Override
    public Constants.MOVE getMove(Game game, long timeDue) {
        if(this.knowledge == null)
        {
            KnowledgeGraphDisplayer displayer;
            this.knowledge = new AgentKnowledge();
            Commons.initAgentsKnowledge(this.knowledge, game);
            discreteKnowledgeGraph = new DiscreteKnowledgeGraph(this.knowledge.getGraph());
            displayer = new KnowledgeGraphDisplayer(discreteKnowledgeGraph, "file:///" + Paths.get(".").toAbsolutePath().normalize().toString() + "/src/main/java/entrants/utils/ui/kgraph.css");
            displayer.display();
        }
        else
        {
            this.discreteKnowledgeGraph.update(Commons.updateAgentsKnowledge(this.knowledge, game));
        }
        return Constants.MOVE.DOWN;
    }
}
