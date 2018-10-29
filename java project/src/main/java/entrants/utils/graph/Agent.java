package entrants.utils.graph;

import entrants.utils.Commons;
import pacman.controllers.IndividualGhostController;
import pacman.game.Constants;
import pacman.game.Game;

public class Agent extends IndividualGhostController {
    private final WeightedUndirectedGraph<Node, Edge> graph;
    private DiscreteKnowledgeGraph discreteGraph;
    private boolean initialized;

    public Agent(Constants.GHOST ghost)
    {
        super(ghost);
        this.graph = new WeightedUndirectedGraph<>();
        discreteGraph = new DiscreteKnowledgeGraph(this.graph);
    }

    @Override
    public Constants.MOVE getMove(Game game, long l) {
        if(!initialized)
        {
            Commons.initAgentsKnowledge(this, game);
            initialized = true;
        }
        else
        {
            this.discreteGraph.update(Commons.updateAgentsKnowledge(this, game));
        }
        return Constants.MOVE.DOWN;
    }

    public UndirectedGraph<Node, Edge> getGraph()
    {
        return this.graph;
    }

    public DiscreteKnowledgeGraph getDiscreteGraph() {
        return this.discreteGraph;
    }

    public Constants.GHOST getAgent() {
        return ghost;
    }
}
