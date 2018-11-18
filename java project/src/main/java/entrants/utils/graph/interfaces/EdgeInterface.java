package entrants.utils.graph.interfaces;

/**
 * This interface describes the features that a class that represents edges linking NodeInterface objects should offer
 * The Edges are not oriented
 */
public interface EdgeInterface<N extends NodeInterface> {
    /**
     * Retrieves the 'nodeA' of the edge
     * @return A N object
     */
    N getNodeA();

    /**
     * Retrieves the 'nodeB' of the edge
     * @return A N object
     */
    N getNodeB();

    /**
     * Checks if a N Object is concerned by the current edge
     * @param node The node to check
     * @return True if the given node is concerned by the current edge
     */
    boolean concernsNode(N node);

    /**
     * Retrieves the neighbour of a Node with the current edge
     * If the given node is not concerned by the current Edge, a Null value is returned
     *
     * @param node A node
     * @return The node's neighbour with the current edge
     */
    N getNeighbour(N node);
}
