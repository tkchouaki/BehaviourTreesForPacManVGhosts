package entrants.utils.graph.interfaces;

import java.beans.PropertyChangeSupport;
import java.util.Collection;

/**
 * Interface for Undirected graphs. No assumptions are made about nodes and edges, except that they need to extend
 * NodeInterface and EdgeInterface respectively.
 * UndirectedGraphs are also beans, which mean you need to do the following when implementing this interface :
 * <ul>
 *     <li>Fire the NODE_ADDED_PROP property when a <strong>new node</strong> is added</li>
 *     <li>Fire the EDGE_ADDED_PROP property when a <strong>new edge</strong> is inserted</li>
 *     <li>Fire the NODE_REMOVED_PROP property when a <strong>valid node</strong> is deleted</li>
 *     <li>Fire the EDGE_REMOVED_PROP property when a <strong>valid edge</strong> is deleted</li>
 * </ul>
 * @param <N> Nodes' type
 * @param <E> Edges' type
 * @contract.inv <pre>
 *     getNodes() != null
 *     getEdges() != null
 *     getPropertyChangeSupport() != null
 *     getDegreeOf(node) >= 0
 *     getNeighboursAsNodesOf(node) != null
 *     getNeighboursAsEdgesOf(node) != null
 * </pre>
 */
public interface IUndirectedGraph<N extends NodeInterface, E extends EdgeInterface> {

    //STATICS
    String NODE_ADDED_PROP = "node_added_prop";
    String EDGE_ADDED_PROP = "edge_added_prop";
    String NODE_REMOVED_PROP = "node_removed_prop";
    String EDGE_REMOVED_PROP = "edge_removed_prop";
    String GRAPH_CLEARED_PROP = "graph_cleared";

    /**
     * Exception indicating that the node you requested is not registered in the graph.
     */
    class NodeNotFoundException extends Exception {
        public NodeNotFoundException() {
            super();
        }
        public NodeNotFoundException(String message) {
            super(message);
        }
    }

    // REQUESTS
    /**
     * Get all registered nodes in the graph.
     * @return a collection containing all nodes
     */
    Collection<N> getNodes();

    /**
     * Get all registered edges in the graph.
     * @return a collection containing all nodes.
     */
    Collection<E> getEdges();

    /**
     * Get the PropertyChangeSupport associated to this bean. Supported properties are :
     * <ul>
     *     <li>NODE_ADDED_PROP</li>
     *     <li>EDGE_ADDED_PROP</li>
     *     <li>NODE_REMOVED_PROP</li>
     *     <li>EDGE_REMOVED_PROP</li>
     *     <li>other implementation specific properties</li>
     * </ul>
     * @return the PropertyChangeSupport
     */
    PropertyChangeSupport getPropertyChangeSupport();

    /**
     * Get the degree of the provided node.
     * @param node the node
     * @return the node's degree
     * @contract.pre node != null
     * @throws NodeNotFoundException if the node is not registered in the graph
     */
    int getDegreeOf(N node) throws NodeNotFoundException;

    /**
     * Get all neighbours of the provided node.
     * @param node the node
     * @return a collection of nodes
     * @contract.pre node != null
     * @throws NodeNotFoundException if the node is not registered in the graph
     */
    Collection<N> getNeighboursAsNodesOf(N node) throws NodeNotFoundException;

    /**
     * Get all neighbours of the provided node. Result is the edges linking your node and its neighbours.
     * @param node the node
     * @return a collection of edges
     * @contract.pre node != null
     * @throws NodeNotFoundException if the node is not registered in the graph
     */
    Collection<E> getNeighboursAsEdgesOf(N node) throws NodeNotFoundException;

    /**
     * Find a node in the grapg by providing its id.
     * @param nodeID the id of the desired node
     * @return the node if it has been found, else null
     */
    N getNodeByID(Integer nodeID);

    /**
     * Add a new node in the graph. If the node was already registered, this is a no-op.
     * This method fires a NODE_ADDED_PROP when a new node is added to the graph.
     * @param node the node you want to add to the graph
     * @return true if the node has been added, false if it was already registered in the graph
     * @contract.pre node != null
     * @contract.post <pre>
     *     getNodes().contains(node)
     *     getNodeByID(node.getID()).equals(node)
     *     addNode(node) ==>
     *          getNeighboursAsNodesOf(node).size() == 0
     *          && getNeighboursAsEdgesOf(node).size() == 0
     *          && getDegreeOf(node) == 0
     *     !addNode(node) ==>
     *          getNeighboursAsNodesOf(node) == old getNeighboursAsNodesOf(node)
     *          && getNeighboursAsEdgesOf(node) == old getNeighboursAsEdgesOf(node)
     *          && getDegreeOf(node) == old getDegreeOf(node)
     * </pre>
     */
    boolean addNode(N node);

    /**
     * Add a new edge in the graph. If the edge was already registered, this is a no-op. If nodes in the edge where not
     * registered, this method will create them (and thus fire a NODE_ADDED_PROP property)
     * This method fires a EDGE_ADDED_PROP when a new edge is added to the graph.
     * @param edge the edge you want to add to the graph
     * @return true if the node has been added, false if it was already registered in the graph
     * @contract.pre edge != null
     * @contract.post <pre>
     *     getNodes().contains(edge.getNodeA())
     *     getNodes().contains(edge.getNodeB())
     *     getEdges().contains(edge)
     *     getNeighboursAsNodesOf(edge.getNodeA()).contains(edge.getNodeB())
     *     getNeighboursAsNodesOf(edge.getNodeB()).contains(edge.getNodeA())
     *     getNeighboursAsEdgesOf(edge.getNodeA()).contains(edge)
     *     getNeighboursAsEdgesOf(edge.getNodeB()).contains(edge)
     * </pre>
     */
    boolean addEdge(E edge);

    /**
     * Remove a node from the graph. If not such node matches, this is a no-op. If the node is linked with others by
     * edges, this method will also delete relevant edges (and thus fire a EDGE_REMOVED_PROP <strong>before</strong>
     * node's effective deletion and NODE_REMOVED_PROP fire).
     * @param node the node to be deleted
     * @return true is the node has been deleted, false if this was a no-op
     * @contract.post <pre>
     *     !getNodes().contains(node)
     *     !getEdges().contains(getNeighboursAsEdgesOf(node))
     * </pre>
     */
    boolean removeNode(N node);

    /**
     * Remove an edge from the graph. If not such edge matches, this is a no-op. Fires a EDGE_REMOVED_PROP property.
     * @param edge the edge to be deleted
     * @return true is the edge has been deleted, false if this was a no-op
     * @contract.post !getEdges().contains(edge)
     */
    boolean removeEdge(E edge);

    /**
     * Clear the entire graph. Method fires GRAPH_CLEARED_PROP property containing in oldValue the graph before deletion
     * ie a map N -> Collection<E>.
     * @contract.post <pre>
     *     getNodes().size() == 0
     *     getEdges().size() == 0
     * </pre>
     */
    void clear();
}
