package entrants.utils.graph;

import entrants.utils.graph.interfaces.EdgeInterface;
import entrants.utils.graph.interfaces.IUndirectedGraph;
import entrants.utils.graph.interfaces.NodeInterface;

import java.beans.PropertyChangeSupport;
import java.util.*;

public class UndirectedGraph implements IUndirectedGraph<NodeInterface, EdgeInterface> {
    // ATTRIBUTES
    private final Map<NodeInterface, Set<EdgeInterface>> topology;
    private final PropertyChangeSupport support;

    // CONSTRUCTOR
    public UndirectedGraph() {
        topology = new HashMap<>();
        support = new PropertyChangeSupport(this);
    }

    @Override
    public Collection<NodeInterface> getNodes() {
        return new ArrayList<>(topology.keySet());
    }

    @Override
    public Collection<EdgeInterface> getEdges() {
        Set<EdgeInterface> values = new HashSet<EdgeInterface>();
        for (Collection<EdgeInterface> edges : topology.values()) {
            values.addAll(edges);
        }
        return values;
    }

    @Override
    public PropertyChangeSupport getPropertyChangeSupport() {
        return support;
    }

    @Override
    public int getDegreeOf(NodeInterface node) throws NodeNotFoundException {
        if (node == null) {
            throw new AssertionError();
        }
        Collection<EdgeInterface> edges = topology.get(node);
        if (edges == null) {
            throw new NodeNotFoundException();
        }
        return edges.size();
    }

    @Override
    public Collection<NodeInterface> getNeighboursAsNodesOf(NodeInterface node) throws NodeNotFoundException {
         if (node == null) {
             throw new AssertionError();
         }
        Collection<EdgeInterface> edges = topology.get(node);
        if (edges == null) {
            throw new NodeNotFoundException();
        }
        List<NodeInterface> result = new ArrayList<>();
        for (EdgeInterface edge : edges) {
            if (!edge.getNodeA().equals(node)) {
                result.add(edge.getNodeA());
            } else {
                result.add(edge.getNodeB());
            }
        }
        return result;
    }

    @Override
    public Collection<EdgeInterface> getNeighboursAsEdgesOf(NodeInterface node) throws NodeNotFoundException {
        if (node == null) {
            throw new AssertionError();
        }
        Collection<EdgeInterface> edges = topology.get(node);
        if (edges == null) {
            throw new NodeNotFoundException();
        }
        return edges;
    }

    @Override
    public NodeInterface getNodeByID(Integer nodeID) {
        int i = 0;
        List<NodeInterface> nodes = new ArrayList<>(topology.keySet());
        while (i < nodes.size() && !nodes.get(i).getId().equals(nodeID)) {
            i++;
        }
        if (i == nodes.size()) return null;
        return nodes.get(i);
    }

    @Override
    public boolean addNode(NodeInterface node) {
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

    @Override
    public boolean addEdge(EdgeInterface edge) {
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

    @Override
    public boolean removeNode(NodeInterface node) {
        if (node == null) {
            throw new AssertionError();
        }
        if (!topology.containsKey(node)) return false;
        for (EdgeInterface edge : topology.get(node)) {
            removeEdge(edge);
        }
        topology.remove(node);
        support.firePropertyChange(NODE_REMOVED_PROP, node, null);
        return true;
    }

    @Override
    public boolean removeEdge(EdgeInterface edge) {
        if (edge == null) {
            throw new AssertionError();
        }
        if (!topology.containsKey(edge.getNodeA()) || !topology.containsKey(edge.getNodeB())) return false;
        topology.get(edge.getNodeA()).remove(edge);
        topology.get(edge.getNodeB()).remove(edge);
        support.firePropertyChange(EDGE_REMOVED_PROP, edge, null);
        return true;
    }
}
