package entrants.utils.graph;

import entrants.utils.ui.KnowledgeGraphDisplayer;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.nio.file.Paths;
import java.util.*;

/**
 * Graph used for representing map topology. This graph also stores in its nodes various information such as Ghosts,
 * Pacman, Pills ... For more details, {@see Node}.
 * @contract.inv <pre>
 *     size() >= 0
 *     getNodes() != null
 *     getPropertyChangeSupport() != null
 * </pre>
 */
public class KnowledgeGraph {

    // STATICS
    public static void main(String[] args) {
        // Create a graph
        KnowledgeGraph graph = new KnowledgeGraph();

        // Register some property listeners
        PropertyChangeSupport support = graph.getPropertyChangeSupport();
        support.addPropertyChangeListener(ADDED_NODE_PROPERTY, new PropertyChangeListener() {
            @Override
            public void propertyChange(PropertyChangeEvent evt) {
                System.out.println("Node added: " + ((Node) evt.getNewValue()).getId());
            }
        });
        support.addPropertyChangeListener(ADDED_EDGE_PROPERTY, new PropertyChangeListener() {
            @Override
            public void propertyChange(PropertyChangeEvent evt) {
                System.out.println("Edge added: " + evt.getNewValue());
            }
        });

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

        // Setup display
        System.setProperty("org.graphstream.ui.renderer", "org.graphstream.ui.j2dviewer.J2DGraphRenderer");

        KnowledgeGraphDisplayer displayer = new KnowledgeGraphDisplayer(
                graph,
                "file:///" + Paths.get(".").toAbsolutePath().normalize().toString() + "/src/main/java/entrants/utils/ui/kgraph.css"
        );
        displayer.display();
    }

    /**
     * Internal class used to represent edges.
     * @contract.inv <pre>
     *     getFirst() != null
     *     getSecond() != null
     * </pre>
     */
    public static final class Edge {
        private final Node n;
        private final Node m;
        private final boolean directed;

        /**
         * Create a new edge from n to m.
         * @param n source
         * @param m target
         * @param directed is this edge a directed edge ?
         * @contract.pre <pre>
         *     n != null
         *     m != null
         * </pre>
         * @contract.post <pre>
         *     getFirst().equals(n)
         *     getSecond().equals(m)
         *     isDirected() == directed
         * </pre>
         */
        private Edge(Node n, Node m, boolean directed) {
            assert n != null && m != null;
            this.n = n;
            this.m = m;
            this.directed = directed;
        }

        /**
         * Get the source node
         * @return node
         */
        public Node getFirst() {
            return n;
        }

        /**
         * Get the target node
         * @return node
         */
        public Node getSecond() {
            return m;
        }

        /**
         * Is this edge directed ?
         * @return true is directed, else false
         */
        public boolean isDirected() {
            return directed;
        }

        @Override
        public String toString() {
            return "(" + n.getId() + (directed ? "-->" : "<->") + m.getId() + ")";
        }

        @Override
        public boolean equals(Object obj) {
            if (obj != null && obj.getClass().equals(this.getClass())) {
                Edge edge = (Edge) obj;

                if (!this.isDirected() && !edge.isDirected()) {
                    return (getFirst().equals(edge.getFirst()) || getFirst().equals(edge.getSecond()))
                            && (getSecond().equals(edge.getFirst()) || getSecond().equals(edge.getSecond()));

                } else if (this.isDirected() && edge.isDirected()) {
                    return getFirst().equals(edge.getFirst()) && getSecond().equals(edge.getSecond());
                }
            }
            return false;
        }

        @Override
        public int hashCode() {
            if (!isDirected()) {
                int i = getFirst().getId();
                int j = getSecond().getId();
                if (i >= j) {
                    return ("(" + j + "<-->" + i + ")").hashCode();
                }
                return ("(" + i + "<-->" + j + ")").hashCode();
            }
            return toString().hashCode();
        }
    }

    public static final String ADDED_NODE_PROPERTY = "added_node";
    public static final String ADDED_EDGE_PROPERTY = "added_edge";

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
     * Retrieves the Neighbours of Node
     * i.e : A Node's neighbours are the ones connected to him by undirected edges
     * @param n
     * A node
     * @return
     * A collection of its neighbours
     */
    public Collection<Node> getNeighbours(Node n)
    {
        Collection<Node> result = this.getSuccessors(n);
        if(result != null)
        {
            Collection<Node> predecessors = this.getPredecessors(n);
            for(Node m : predecessors )
            {
                if(!predecessors.contains(m)){
                    result.remove(m);
                }
            }
        }
        return result;
    }

    /**
     * Retrieves the degree of a node
     * The degree of a node here is the number of its successors
     * @param n
     * A node
     * @return
     * Its degree
     */
    public int getDegree(Node n)
    {
        Collection<Node> successors = this.getSuccessors(n);
        if(successors != null)
        {
            return successors.size();
        }
        return -1;
    }

    /**
     * Get this KnowledgeGraph's size.
     * @return the size
     */
    public int size() {
        return topology.size();
    }

    /**
     * Return all nodes in the graph.
     * @return the nodes
     */
    public Collection<Node> getNodes()
    {
        return new HashSet<>(this.topology.keySet());
    }


    /**
     * Retrieves a contained node with a given ID
     * @param id The id of the desired node
     * @return The node with the specified ID if it exists, null otherwise
     */
    public Node getNodeById(int id)
    {
        return Node.getNodeById(this.getNodes(), id);
    }

    /**
     * Get the PropertyChangeSupport associated to this object.
     * @return the PropertyChangeSupport
     */
    public PropertyChangeSupport getPropertyChangeSupport() {
        return this.support;
    }

    /**
     * Get all edges in this graph. This method costs a lot, as edges are calculated each time.
     * @return a collection of edges
     */
    public Collection<Edge> getEdges() {
        Set<Edge> edges = new HashSet<>();
        for (Node n : topology.keySet()) {
            for (Node m : getSuccessors(n)) {
                edges.add(new Edge(n, m, !getSuccessors(m).contains(n)));
            }
        }
        return edges;
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

        if (topology.putIfAbsent(node, new HashSet<>()) == null) {
            support.firePropertyChange(ADDED_NODE_PROPERTY, null, node);
        }
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
        if (topology.get(n).add(m)) {
            getPropertyChangeSupport().firePropertyChange(ADDED_EDGE_PROPERTY, null, new Edge(n, m, true));
        }
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
            if (topology.get(n).add(m)) {
                getPropertyChangeSupport().firePropertyChange(ADDED_EDGE_PROPERTY, null, new Edge(n, m, true));
            };
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
        if (n == null || m == null) {
            throw new NullPointerException();
        }
        addNode(n);
        addNode(m);

        boolean result = topology.get(n).add(m);
        inverted_topology.get(m).add(n);

        if (topology.get(m).add(n) || result) {
            getPropertyChangeSupport().firePropertyChange(ADDED_EDGE_PROPERTY, null, new Edge(n, m, false));
        }
        inverted_topology.get(n).add(m);
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

    /**
     * Removes a Node from the graph
     * @param n
     * The node to remove
     */
    public void removeNode(Node n)
    {
        topology.remove(n);
        inverted_topology.remove(n);
        for(Node node : this.getNodes())
        {
            topology.get(node).remove(n);
            inverted_topology.get(node).remove(n);
        }
    }

    /**
     * Removes an Undirected edge from the graph
     * @param n
     * @param m
     */
    public void removeUndirectedEdge(Node n, Node m)
    {
        removeDirectedEdge(n, m);
        removeDirectedEdge(m, n);
    }

    /**
     * Removes a Directed Edge
     * @param n
     * @param m
     */
    public void removeDirectedEdge(Node n, Node m)
    {
        Collection<Node> successors = topology.get(n);
        Collection<Node> predecessors = inverted_topology.get(m);
        if(successors != null && predecessors != null)
        {
            successors.remove(m);
            predecessors.remove(n);
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
}
