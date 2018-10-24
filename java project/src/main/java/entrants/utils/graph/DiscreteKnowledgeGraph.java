package entrants.utils.graph;

import java.util.Collection;

public class DiscreteKnowledgeGraph extends KnowledgeGraph{
    private KnowledgeGraph graph;

    public DiscreteKnowledgeGraph(KnowledgeGraph graph)
    {
        this.graph = graph;
        this.discretizeWholeGraph();

    }


    public void discretizeWholeGraph()
    {
        this.copyTargetGraph();
        for(Node node : this.getNodes())
        {
            if(!this.isNodeInteresting(node))
            {
                this.unlinkAndRemoveNode(node);
            }
        }
    }

    private void unlinkAndRemoveNode(Node node)
    {
        Collection<Node> successors = this.getSuccessors(node);
        Collection<Node> predecessors = this.getPredecessors(node);
        this.removeNode(node);
        for(Node successor : successors)
        {
            for(Node predecessor : predecessors)
            {
                this.addDirectedEdge(predecessor, successor);
            }
        }
    }

    private void copyTargetGraph()
    {
        this.emptyGraph();
        for(Node node : this.graph.getNodes())
        {
            this.addNode(node);
            this.addDirectedEdges(node, this.graph.getSuccessors(node));
        }
    }

    private void emptyGraph()
    {
        Collection<Node> nodes = this.getNodes();
        for(Node node : nodes)
        {
            this.removeNode(node);
        }
    }

    /**
     * Checks if a node is interesting (if he should be kept in the DiscreteGraph)
     * @param node
     * @return
     */
    public boolean isNodeInteresting(Node node)
    {
        if(node.containsGhost() || node.containsPacMan())
        {
            return true;
        }
        if(node.containsPill() || node.containsPowerPill())
        {
            return true;
        }
        return graph.getDegree(node)>=3;
    }
}
