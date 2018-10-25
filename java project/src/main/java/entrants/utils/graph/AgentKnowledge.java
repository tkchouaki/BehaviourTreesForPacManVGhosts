package entrants.utils.graph;

public class AgentKnowledge {
    private final UndirectedGraph<Node, Edge> graph;

    public AgentKnowledge()
    {
        this.graph = new UndirectedGraph<>();
    }

    public UndirectedGraph<Node, Edge> getGraph()
    {
        return this.graph;
    }
}
