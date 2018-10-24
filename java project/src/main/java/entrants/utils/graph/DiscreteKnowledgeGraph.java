package entrants.utils.graph;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 * This class is used to simplify a KnowledgeGraph by only taking into account the interesting nodes
 */
public class DiscreteKnowledgeGraph extends KnowledgeGraph{
    private KnowledgeGraph graph;

    /**
     * Initializes the discrete graph with a target KnowledgeGraph.
     * At initialization, All the nodes of the target KnowledgeGraph are checked (can be time consuming)
     * @param graph
     * The target graph
     */
    public DiscreteKnowledgeGraph(KnowledgeGraph graph)
    {
        this.graph = graph;
        this.discretizeWholeGraph();
    }


    /**
     * Discritizes the whole target KnowledgeGraph (checking all the nodes)
     */
    private void discretizeWholeGraph()
    {
        //We first clear everything & copy the whole target graph
        this.copyTargetGraph();
        //We remove all non interesting nodes & we link all the predecessors of each removed node to its successors
        for(Node node : this.getNonInterestingNodes())
        {
            this.unlinkAndRemoveNode(node);
        }
    }

    /**
     * Removes a node & attaches its predecessors to its successors
     * @param node
     * The node to remove
     */
    private void unlinkAndRemoveNode(Node node)
    {
        Collection<Node> successors = this.getSuccessors(node);
        Collection<Node> predecessors = this.getPredecessors(node);
        this.removeNode(node);
        for(Node successor : successors)
        {
            for(Node predecessor : predecessors)
            {
                if(!predecessor.equals(successor))
                {
                    this.addDirectedEdge(predecessor, successor);
                }
            }
        }
    }

    /**
     * Clears everything &nd Copies the whole target KnowledgeGraph
     */
    private void copyTargetGraph()
    {
        this.emptyGraph();
        for(Node node : this.graph.getNodes())
        {
            this.addNode(node);
            this.addDirectedEdges(node, this.graph.getSuccessors(node));
        }
    }

    /**
     * Clears everything (removes all the nodes)
     */
    private void emptyGraph()
    {
        Collection<Node> nodes = this.getNodes();
        for(Node node : nodes)
        {
            this.removeNode(node);
        }
    }

    /**
     * Retrieves the non interesting Nodes
     * @return
     * A collection containing the non interesting nodes
     */
    private Collection<Node> getNonInterestingNodes()
    {
        Set<Node> result = new HashSet<>();
        for(Node node : this.getNodes())
        {
            if(!this.isNodeInteresting(node))
            {
                result.add(node);
            }
        }
        return result;
    }

    /**
     * Checks if a node is interesting (if he should be kept in the DiscreteGraph)
     * A node is considered interesting if it contains a Pill, a Power Pill, a PacMan or a Ghost.
     * It is also considered interesting if its degree is at least 3.
     * @param node
     * The node to check
     * @return
     * True if the node can be considered interesting, false otherwise
     */
    private boolean isNodeInteresting(Node node)
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
