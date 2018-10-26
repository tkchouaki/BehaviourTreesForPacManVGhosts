package entrants.utils.graph;

public class AgentKnowledge {
    private final WeightedUndirectedGraph<Node, Edge> graph;

    public AgentKnowledge()
    {
        this.graph = new WeightedUndirectedGraph<>();
    }

    public UndirectedGraph<Node, Edge> getGraph()
    {
        return this.graph;
    }
}
