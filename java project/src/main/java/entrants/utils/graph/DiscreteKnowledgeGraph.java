package entrants.utils.graph;


import java.util.*;

/**
 * This class is used to simplify a KnowledgeGraph by only taking into account the interesting nodes
 */
public class DiscreteKnowledgeGraph extends UndirectedGraph<Node, Edge>{
    private UndirectedGraph<Node, Edge> graph;

    /**
     * Initializes the discrete graph with a target KnowledgeGraph.
     * At initialization, All the nodes of the target KnowledgeGraph are checked (can be time consuming)
     * @param graph
     * The target graph
     */
    public DiscreteKnowledgeGraph(UndirectedGraph<Node, Edge> graph)
    {
        this.graph = graph;
        this.discretizeWholeGraph();
    }

    /**
     * Updates the graph by rechecking all the source graph's nodes.
     * Removes the nodes that are no longer intresting.
     * Adds the nodes that became interesting.
     */
    public void update()
    {
        this.update(this.graph.getNodes());
    }

    /**
     * Updates the graph by rechecking all the given nodes
     * Removes the nodes that are no longer intresting.
     * Adds the nodes that became interesting.
     * @param updatedNodes
     * The nodes to recheck
     */
    public void update(Collection<Node> updatedNodes)
    {
        Collection<Node> nodes = this.getNodes();
        System.out.println("===");
        for(Node updatedNode : updatedNodes)
        {
            if(this.isNodeInteresting(updatedNode) && !nodes.contains(updatedNode))
            {
                this.addNewIntrestingNode(updatedNode);
            }
            else if(!this.isNodeInteresting(updatedNode) && nodes.contains(updatedNode))
            {
                this.unlinkAndRemoveNode(updatedNode);
            }
        }
    }

    /**
     * Adds a new interesting node.
     * The node to add is first encircled by nodes that are already present in the graph.
     * It is then added meanwhile adding the edges that links him to the encircling nodes.
     * The added edges are the sum results of the edges that links it to the encircling nodes.
     * @param node
     * The node to add.
     */
    private void addNewIntrestingNode(Node node)
    {
        Collection<Node> myNodes = this.getNodes();
        Map<Node, List<Edge>> pathsToMyNodes = this.graph.circleNode(node, myNodes);
        Collection<Node> neighbours = new HashSet<>();
        for(Node myNode : pathsToMyNodes.keySet())
        {
            neighbours.add(myNode);
            List<Edge> path = pathsToMyNodes.get(myNode);
            Edge toAdd = Edge.chainToEdge(node, path);
            this.addEdge(toAdd);
        }
        for(Node neighbourA : neighbours)
        {
            for(Node neighbourB : neighbours)
            {
                if(!neighbourA.equals(neighbourB))
                {
                    this.removeEdge(new Edge(neighbourA, neighbourB));
                }
            }
        }
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
        Collection<Node> neighbours = null;
        try {
            neighbours = this.getNeighboursAsNodesOf(node);
            System.out.println(node);
            this.removeNode(node);
            for(Node neighbourA : neighbours)
            {
                for(Node neighbourB : neighbours)
                {
                    if(!neighbourA.equals(neighbourB))
                    {
                        Edge edge = new Edge(neighbourA, neighbourB);
                        System.out.println(edge);
                        this.addEdge(edge);
                    }
                }
            }
        } catch (NodeNotFoundException e) {
            e.printStackTrace();
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
            try {
                for(Edge edge : this.graph.getNeighboursAsEdgesOf(node))
                {
                    this.addEdge(edge);
                }
            } catch (NodeNotFoundException e) {
                e.printStackTrace();
            }
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
        if(node.containsPowerPill())
        {
            return true;
        }
        return node.isDecisionNode();
    }
}
