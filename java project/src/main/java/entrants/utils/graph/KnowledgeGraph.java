package entrants.utils.graph;

import java.util.*;

/**
 * Graph used for representing map topology. This graph also stores in its nodes various information such as Ghosts,
 * Pacman, Pills ... For more details, {@see Node}.
 * @contract.inv <pre>
 *     size() >= 0
 * </pre>
 */
public class KnowledgeGraph {

    public static void main(String[] args) {
        KnowledgeGraph graph = new KnowledgeGraph();
        System.out.println(graph);
    }

    // ATTRIBUTES
    private final Map<Node, List<Node>> topology;
    private final Map<Node, List<Node>> inverted_topology;

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
     *     size() == topology.size()
     *     topology.containsKey(node) ==> getSuccessors(node).equals(topology.get(node))
     *     topology.get(key).contains(node) ==> getPredecessors(node).contains(key)
     * </pre>
     */
    public KnowledgeGraph(Map<Node, List<Node>> topology) {
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
                    inverted_topology.put(m, new ArrayList<>());
                }
                inverted_topology.get(m).add(n);
                topology.putIfAbsent(m, new ArrayList<>());
            }

            //Add the node in the inverted topology
            inverted_topology.putIfAbsent(n, new ArrayList<>());
        }
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
    public List<Node> getSuccessors(Node node) {
        if (node == null) {
            throw new NullPointerException();
        }
        List<Node> result = topology.get(node);
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
    public List<Node> getPredecessors(Node node) {
        if (node == null) {
            throw new NullPointerException();
        }
        List<Node> result = inverted_topology.get(node);
        return result == null ? null : new ArrayList<>(result);
    }

    /**
     * Get this KnowledgeGraph's size.
     * @return the size
     */
    public int size() {
        return topology.size();
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
