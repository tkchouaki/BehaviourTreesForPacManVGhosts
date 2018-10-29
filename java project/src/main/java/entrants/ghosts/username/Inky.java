package entrants.ghosts.username;

import entrants.utils.graph.Agent;
import entrants.utils.graph.DiscreteKnowledgeGraph;
import pacman.controllers.IndividualGhostController;
import pacman.game.Constants;
import pacman.game.Game;

/**
 * Created by Piers on 11/11/2015.
 */
public class Inky extends IndividualGhostController {
    private Agent knowledge;
    private DiscreteKnowledgeGraph discreteKnowledgeGraph;

    public Inky() {
        super(Constants.GHOST.INKY);
    }

    @Override
    public Constants.MOVE getMove(Game game, long timeDue) {
        /*if(this.knowledge == null)
        {
            KnowledgeGraphDisplayer displayer;
            this.knowledge = new Agent(ghost);
            Commons.initAgentsKnowledge(this.knowledge, game);
            discreteKnowledgeGraph = new DiscreteKnowledgeGraph(this.knowledge.getGraph());
            displayer = new KnowledgeGraphDisplayer(discreteKnowledgeGraph, "file:///" + Paths.get(".").toAbsolutePath().normalize().toString() + "/src/main/java/entrants/utils/ui/kgraph.css");
            displayer.display();
        }
        else
        {
            this.discreteKnowledgeGraph.update(Commons.updateAgentsKnowledge(this.knowledge, game));
        }*/
        return Constants.MOVE.UP;
    }
}
