package entrants.utils.graph;

import pacman.game.Constants;

public class AgentKnowledge {
    private final WeightedUndirectedGraph<Node, Edge> graph;
    private final Constants.GHOST ghost;

    public AgentKnowledge(Constants.GHOST ghost)
    {
        if (ghost == null) {
            throw new AssertionError();
        }
        this.ghost = ghost;
        this.graph = new WeightedUndirectedGraph<>();
    }

    public UndirectedGraph<Node, Edge> getGraph()
    {
        return this.graph;
    }

    public Constants.GHOST getAgent() {
        return ghost;
    }
}
