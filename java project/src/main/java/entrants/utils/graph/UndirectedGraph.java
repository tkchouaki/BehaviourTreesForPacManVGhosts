package entrants.utils.graph;

import entrants.utils.graph.interfaces.EdgeInterface;
import entrants.utils.graph.interfaces.IUndirectedGraph;
import entrants.utils.graph.interfaces.NodeInterface;

import java.beans.PropertyChangeSupport;
import java.util.*;

/**
 * This class describes an Undirected Graph
 * @param <N>
 *     The class that represents the nodes of the graph
 * @param <E>
 *     The class that represents the edged of the graph
 */
public class UndirectedGraph<N extends NodeInterface, E extends EdgeInterface<N>> implements IUndirectedGraph<N, E> {
    // ATTRIBUTES
    private final Map<N, Set<E>> topology;
    private final PropertyChangeSupport support;

    /**
     * Initializes the graph as an empty one
     */
    // CONSTRUCTOR
    public UndirectedGraph() {
        topology = new HashMap<>();
        support = new PropertyChangeSupport(this);
    }

    /**
     * Retrieves a collection of all the nodes of the graph
     * @return
     * A collection of all the nodes of the graph
     */
    @Override
    public Collection<N> getNodes() {
        return new ArrayList<>(topology.keySet());
    }

    /**
     * Retrieves a collection of all the nodes of the graph
     * @return
     * A collection of all the nodes of the graph
     */
    @Override
    public Collection<E> getEdges() {
        Set<E> values = new HashSet<>();
        for (Collection<E> edges : topology.values()) {
            values.addAll(edges);
        }
        return values;
    }

    /**
     * Returns the property change support of the graph
     * @return
     * The property change support of the graph
     */
    @Override
    public PropertyChangeSupport getPropertyChangeSupport() {
        return support;
    }

    /**
     * Computes the degree of a given node
     * @param node the node
     * @return The degree of a given node
     * @throws NodeNotFoundException fired if the given node doesn't exist in the graph
     */
    @Override
    public int getDegreeOf(N node) throws NodeNotFoundException {
        if (node == null) {
            throw new AssertionError();
        }
        Collection<E> edges = topology.get(node);
        if (edges == null) {
            throw new NodeNotFoundException();
        }
        return edges.size();
    }

    /**
     * Returns the neighbouring nodes of a given node
     * @param node the node
     * @return A collection of the given node's neighbours.
     * @throws NodeNotFoundException Fired if the given node doesn't exist in the graph
     */
    @Override
    public Collection<N> getNeighboursAsNodesOf(N node) throws NodeNotFoundException {
         if (node == null) {
             throw new AssertionError();
         }
        Collection<E> edges = topology.get(node);
        if (edges == null) {
            throw new NodeNotFoundException();
        }
        List<N> result = new ArrayList<>();
        for (E edge : edges) {
            if (!edge.getNodeA().equals(node)) {
                result.add(edge.getNodeA());
            } else {
                result.add(edge.getNodeB());
            }
        }
        return result;
    }

    /**
     * Retrieves the Edges that concern a given node (i.e the edges between the given node & its neighbours)
     * @param node the node
     * @return The edges that concer
     * @throws NodeNotFoundException
     */
    @Override
    public Collection<E> getNeighboursAsEdgesOf(N node) throws NodeNotFoundException {
        if (node == null) {
            throw new AssertionError();
        }
        Collection<E> edges = topology.get(node);
        if (edges == null) {
            throw new NodeNotFoundException();
        }
        return edges;
    }

    /**
     * Retrieves a node with the given ID from the graph
     * @param nodeID the id of the desired node
     * @return The node with the given ID if it exists in the graph, null otherwise
     */
    @Override
    public N getNodeByID(Integer nodeID) {
        int i = 0;
        List<N> nodes = new ArrayList<>(topology.keySet());
        while (i < nodes.size() && !nodes.get(i).getId().equals(nodeID)) {
            i++;
        }
        if (i == nodes.size()) return null;
        return nodes.get(i);
    }

    /**
     * Adds a node to the graph if it doesn't exist yet
     * @param node the node you want to add to the graph
     * @return True if the node didn't exist, False otherwise
     */
    @Override
    public boolean addNode(N node) {
        if (node == null) {
            throw new AssertionError();
        }
        if (topology.containsKey(node)) {
            return false;
        }
        topology.put(node, new HashSet<>());
        support.firePropertyChange(NODE_ADDED_PROP, null, node);
        return true;
    }

    /**
     * Adds an edge to the graph if it doesn't already exist.
     * @param edge the edge you want to add to the graph
     * @return True if the edge didn't exist in the graph, False otherwise
     */
    @Override
    public boolean addEdge(E edge) {
        if (edge == null) {
            throw new AssertionError();
        }
        if (topology.get(edge.getNodeA()) == null) {
            addNode(edge.getNodeA());
        }
        if (topology.get(edge.getNodeB()) == null) {
            addNode(edge.getNodeB());
        }
        if (!topology.get(edge.getNodeA()).contains(edge)) {
            topology.get(edge.getNodeA()).add(edge);
            topology.get(edge.getNodeB()).add(edge);
            support.firePropertyChange(EDGE_ADDED_PROP, null, edge);
            return true;
        }
        return false;
    }

    /**
     * Removes a node from the graph if it exists
     * @param node the node to be deleted
     * @return True if the node existed in the graph, False otherwise
     */
    @Override
    public boolean removeNode(N node) {
        if (node == null) {
            throw new AssertionError();
        }
        if (!topology.containsKey(node)) return false;
        for (Iterator<E> it = topology.get(node).iterator(); it.hasNext();) {
            E edge = it.next();
            if (edge.getNodeA().equals(node)) {
                topology.get(edge.getNodeB()).remove(edge);
            } else {
                topology.get(edge.getNodeA()).remove(edge);
            }
            it.remove();
            support.firePropertyChange(EDGE_REMOVED_PROP, edge, null);
        }
        topology.remove(node);
        support.firePropertyChange(NODE_REMOVED_PROP, node, null);
        return true;
    }

    /**
     * Removes an edge from the graph if it exists
     * @param edge the edge to be deleted
     * @return True if the edge existed in the graph, False otherwise
     */
    @Override
    public boolean removeEdge(E edge) {
        if (edge == null) {
            throw new AssertionError();
        }
        if (!topology.containsKey(edge.getNodeA()) || !topology.containsKey(edge.getNodeB())) return false;
        if(!topology.get(edge.getNodeA()).contains(edge)) return false;
        topology.get(edge.getNodeA()).remove(edge);
        topology.get(edge.getNodeB()).remove(edge);
        support.firePropertyChange(EDGE_REMOVED_PROP, edge, null);
        return true;
    }

    /**
     * Removes everything from the graph
     */
    @Override
    public void clear() {
        Map<N, Collection<E>> result = new HashMap<>(this.topology);
        topology.clear();
        support.firePropertyChange(GRAPH_CLEARED_PROP, result, null);
    }

    // TODO: Add in interface if necessary or add javadoc
    public Map<N, List<E>> circleNode(N node, Collection<N> circlingNodes)
    {
        Map<N, List<E>> result = new HashMap<>();
        Map<N, E> predecessors = new HashMap<>();
        Collection<N> tempCirclingNodes = new HashSet<>(circlingNodes);
        Set<N> closed = new HashSet<>();
        Set<N> open = Collections.singleton(node);
        Set<N> nextOpen;

        predecessors.put(node, null);

        while(tempCirclingNodes.size() > 0 && open.size()>0)
        {
            nextOpen = new HashSet<>();
            for(N openNode : open)
            {
                if(closed.contains(openNode))
                {
                    continue;
                }
                try {
                    for(E edge: this.getNeighboursAsEdgesOf(openNode))
                    {
                        N neighbour = edge.getNeighbour(openNode);
                        if(closed.contains(neighbour))
                        {
                            continue;
                        }
                        predecessors.put(neighbour, edge);
                        if(!tempCirclingNodes.contains(neighbour))
                        {
                            nextOpen.add(neighbour);
                        }
                        else
                        {
                            tempCirclingNodes.remove(neighbour);
                        }
                    }
                } catch (NodeNotFoundException e) {
                    e.printStackTrace();
                }
                closed.add(openNode);
            }
            open = nextOpen;
        }

        for(N circlingNode : circlingNodes)
        {
            if(predecessors.containsKey(circlingNode))
            {
                List<E> path = new ArrayList<>();
                N currentNode = circlingNode;
                E currentEdge = predecessors.get(currentNode);
                while(currentEdge != null)
                {
                    path.add(0, currentEdge);
                    currentNode = currentEdge.getNeighbour(currentNode);
                    currentEdge = predecessors.get(currentNode);
                }
                if(path.size()>0)
                {
                    result.put(circlingNode, path);
                }
            }
        }
        return result;
    }
}
