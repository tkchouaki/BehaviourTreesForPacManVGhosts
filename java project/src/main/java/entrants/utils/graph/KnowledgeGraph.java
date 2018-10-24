package entrants.utils.graph;

import java.beans.PropertyChangeSupport;
import java.util.*;

/**
 * Graph used for representing map topology. This graph also stores in its nodes various information such as Ghosts,
 * Pacman, Pills ... For more details, {@see Node}.
 * @contract.inv <pre>
 *     size() >= 0
 * </pre>
 */
public class KnowledgeGraph {

    // STATICS
    public static void main(String[] args) {
        // Create a graph
        KnowledgeGraph graph = new KnowledgeGraph();

        // Add some nodes
        graph.addNode(new Node(0));
        graph.addNode(new Node(1));
        graph.addNode(new Node(2));

        // Nodes should be displayed but without any successors or predecessors
        System.out.println(graph);

        // Create some edges between already created nodes
        graph.addUndirectedEdge(new Node(0), new Node(1));
        graph.addUndirectedEdge(new Node(0), new Node(2));

        graph.addDirectedEdge(new Node(2), new Node(3));

        System.out.println(graph);

    }

    public static final String ADDED_NODE_PROPERTY = "added_node";
    public static final String ADDED_EDGE_PROPERTY = "added_edge";
    public static final String REMOVED_NODE_PROPERTY = "removed_node";
    public static final String REMOVED_EDGE_PROPERTY = "removed_node";

    // ATTRIBUTES
    private final Map<Node, Set<Node>> topology;
    private final Map<Node, Set<Node>> inverted_topology;
    private final PropertyChangeSupport support;

    // CONSTRUCTORS
    /**
     * Create a new empty KnowledgeGraph.
     * @contract.post <pre>
     *     size() == 0
     *     getSuccessor(node) == null
     *     getPredecessor(nde) == null
     * </pre>
     */
    public KnowledgeGraph() {
        topology = new HashMap<>();
        inverted_topology = new HashMap<>();
        support = new PropertyChangeSupport(this);
    }

    /**
     * Create a new KnowledgeGraph and provide some data to fill it.
     * @param topology data you want to add to the KnowledgeGraph
     * @contract.pre <pre>
     *     topology != null
     *     foreach n in topology.keys:
     *          n != null
     *          foreach m in topology[n]:
     *              m != null
     * </pre>
     * @contract.post <pre>
     *     size() will return the number of != nodes in the topology
     *     topology.containsKey(node) ==> getSuccessors(node).equals(topology.get(node))
     *     topology.get(key).contains(node) ==> getPredecessors(node).contains(key)
     * </pre>
     */
    public KnowledgeGraph(Map<Node, Set<Node>> topology) {
        if (topology == null) {
            throw new NullPointerException();
        }
        this.topology = new HashMap<>(topology);

        // Populate inverted map
        this.inverted_topology = new HashMap<>();
        for (Node n : this.topology.keySet()) {
            // Null keys are forbidden
            if (n == null) throw new NullPointerException();

            for (Node m : this.topology.get(n)) {
                // Null nodes are forbidden
                if (m == null) throw new NullPointerException();

                if (!inverted_topology.containsKey(m)) {
                    inverted_topology.put(m, new HashSet<>());
                }
                inverted_topology.get(m).add(n);
                this.topology.putIfAbsent(m, new HashSet<>());
            }

            //Add the node in the inverted topology
            inverted_topology.putIfAbsent(n, new HashSet<>());
        }

        support = new PropertyChangeSupport(this);
    }

    // REQUESTS
    /**
     * Get all successors of the provided node.
     * @param node the node you want to have successors
     * @return null if the node is not in the graph, otherwise a list of successors (can be empty)
     * @contract.pre <pre>
     *     node != null
     * </pre>
     */
    public Collection<Node> getSuccessors(Node node) {
        if (node == null) {
            throw new NullPointerException();
        }
        Set<Node> result = topology.get(node);
        return result == null ? null : new ArrayList<>(result);
    }

    /**
     * Get all predecessors of the provided node.
     * @param node the node you want to have predecessors
     * @return null if the node is not in the graph, otherwise a list of predecessors (can be empty)
     * @contract.pre <pre>
     *     node != null
     * </pre>
     */
    public Collection<Node> getPredecessors(Node node) {
        if (node == null) {
            throw new NullPointerException();
        }
        Set<Node> result = inverted_topology.get(node);
        return result == null ? null : new ArrayList<>(result);
    }

    /**
     * Get this KnowledgeGraph's size.
     * @return the size
     */
    public int size() {
        return topology.size();
    }

    // COMMANDS
    /**
     * Add a node to the graph. If the node is already present, the command is ignored.
     * @param node the node you want to add
     * @contract.pre node != null
     * @contract.post <pre>
     *     getSuccessors(node) != null
     *     getSuccessors(node).size() == 0
     *     getPredecessors(node) != null
     *     getPredecessors(node).size() == 0
     * </pre>
     */
    public void addNode(Node node) {
        if (node == null) {
            throw new NullPointerException();
        }
        topology.putIfAbsent(node, new HashSet<>());
        inverted_topology.putIfAbsent(node, new HashSet<>());
    }

    /**
     * Add a bunch of nodes into the graph. If some nodes are already present in the graph, they are ignored.
     * @param nodes a collection of nodes
     * @contract.pre <pre>
     *     nodes != null
     *     foreach node in nodes:
     *          node != null
     * </pre>
     * @contract.post <pre>
     *     foreach node in nodes:
     *          getSuccessors(node) != null
     *          getSuccessors(node).size() == 0
     *          getPredecessors(node) != null
     *          getPredecessors(node).size() == 0
     * </pre>
     */
    public void addNodes(Collection<Node> nodes) {
        if (nodes == null) {
            throw new NullPointerException();
        }
        for (Node n : nodes) {
            addNode(n);
        }
    }

    /**
     * Add a directed edge from n to m. If that edge already exists, command is ignored.
     * @param n source node
     * @param m target node
     * @contract.pre <pre>
     *     n != null
     *     m != null
     * </pre>
     * @contract.post <pre>
     *     getSuccessors(n) != null && getSuccessors(n).contains(m)
     *     getPredecessors(m) != null && getPredecessors(m).contains(n)
     * </pre>
     */
    public void addDirectedEdge(Node n, Node m) {
        if (n == null || m == null) {
            throw new NullPointerException();
        }
        addNode(n);
        addNode(m);
        topology.get(n).add(m);
        inverted_topology.get(m).add(n);
    }

    /**
     * Add directed edges from n to each m in nodes.
     * @param n source node
     * @param nodes targets nodes
     * @contract.pre <pre>
     *     n != null
     *     nodes != null
     *     foreach m in nodes:
     *          m != null
     * </pre>
     * @contract.post <pre>
     *     foreach m in nodes:
     *          getSuccessors(n).contains(m)
     *          getPredecessors(m).contains(n)
     * </pre>
     */
    public void addDirectedEdges(Node n, Collection<Node> nodes) {
        if (n == null || nodes == null) {
            throw new NullPointerException();
        }
        for (Node m : nodes) {
            if (m == null) {
                throw new NullPointerException();
            }
        }

        addNode(n);
        for (Node m : nodes) {
            addNode(m);
            topology.get(n).add(m);
            inverted_topology.get(m).add(n);
        }
    }

    /**
     * Add an undirected edge between n and m. If this link already exists, the command is ignored. If only one (n->m or
     * m->n) exists, the link is converted into an unidrected edge.
     * @param n one extrema
     * @param m the other extrema
     * @contract.pre <pre>
     *     n != null
     *     m != null
     * </pre>
     * @contract.post <pre>
     *     getSuccessors(n).contains(m)
     *     getSuccessors(m).contains(n)
     *     getPredecessors(n).contains(m)
     *     getPredecessors(m).contains(n)
     * </pre>
     */
    public void addUndirectedEdge(Node n, Node m) {
        addDirectedEdge(n, m);
        addDirectedEdge(m, n);
    }

    /**
     * Add an undirected edge between n and m. If this link already exists, the command is ignored. If only one (n->m or
     * m->n) exists, the link is converted into an unidrected edge.
     * @param n one extrema
     * @param nodes the other extrema
     * @contract.pre <pre>
     *     n != null
     *     nodes != null
     *     foreach m in nodes:
     *          m != null
     * </pre>
     * @contract.post <pre>
     *     foreach m in nodes:
     *          getSuccessors(n).contains(m)
     *          getSuccessors(m).contains(n)
     *          getPredecessors(n).contains(m)
     *          getPredecessors(m).contains(n)
     * </pre>
     */
    public void addUndirectedEdges(Node n, Collection<Node> nodes) {
        if (n == null || nodes == null) {
            throw new NullPointerException();
        }
        for (Node m : nodes) {
            addUndirectedEdge(n, m);
        }
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();

        builder.append("Successors adjacency list :\n");
        for (Node i : topology.keySet()) {
            builder.append(i.getId()).append(" -> [");
            for (Node j : topology.get(i)) {
                builder.append(j.getId()).append(",");
            }
            builder.append("]\n");
        }

        builder.append("Predecessors adjacency list :\n");
        for (Node i : inverted_topology.keySet()) {
            builder.append(i.getId()).append(" -> [");
            for (Node j : inverted_topology.get(i)) {
                builder.append(j.getId()).append(",");
            }
            builder.append("]\n");
        }
        return builder.toString();
    }

    public Set<Node> getNodes()
    {
        return new HashSet<>(this.topology.keySet());
    }
}
