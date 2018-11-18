package entrants.utils.graph;

import entrants.utils.graph.interfaces.WeightedEdgeInterface;

import java.util.*;

/**
 * This class describes an Edge between two neighbouring nodes in the game.
 * An Edge is not oriented.
 * An Edge has a distance.
 * If the Edge links two nodes that are not adjacent in the game, it can have pills & power pills.
 * The allowed weights are 'pills', 'power_pills' & 'distance'
 */
public class Edge implements WeightedEdgeInterface<Node> {

    public static final String WEIGHT_PILLS_NUMBER = "pills";
    public static final String WEIGHT_POWER_PILLS_NUMBER = "power pills";
    public static final String WEIGHT_DISTANCE = "distance";

    public static final Collection<String> ACCEPTED_WEIGHTS = Arrays.asList(WEIGHT_PILLS_NUMBER, WEIGHT_POWER_PILLS_NUMBER, WEIGHT_DISTANCE);

    private final Node nodeA;
    private final Node nodeB;

    private Map<String, Double> weights;

    /**
     * Initializes an Edge with two given nodes.
     * The distance between the nodes is computed and the other weights(pill & power pills) are set to 0.
     * The nodes are labelled A & B even though there is no order.
     * To ensure that an edge is non sensitive to the order in witch nodeA & nodeB are given,
     * they are registered according to their comparison
     * basically, nodeA will have the 'minimum' & nodeB will have the 'maximum"
     *
     * @param nodeA The 'first' node of the Edge
     * @param nodeB The 'second' node of the edge
     */
    public Edge(Node nodeA, Node nodeB) {
        if (nodeA.compareTo(nodeB) <= 0) {
            this.nodeA = nodeA;
            this.nodeB = nodeB;
        } else {
            this.nodeA = nodeB;
            this.nodeB = nodeA;
        }
        this.weights = new HashMap<>();
        for (String key : ACCEPTED_WEIGHTS) {
            this.weights.put(key, 0d);
        }
        this.setWeight(WEIGHT_DISTANCE, computeDistance());
    }

    /**
     * Computes the distance between the two nodes
     *
     * @return The distance between the nodes in term of game world units
     */
    private Double computeDistance() {
        Double distance = 0d;
        distance = Double.valueOf(Math.abs(this.nodeA.getX() - this.nodeB.getX()));
        if (distance == 0) {
            distance = Double.valueOf(Math.abs(this.nodeA.getY() - this.nodeB.getY()));
        }
        return distance;
    }

    /**
     * Sets the weight of a given key
     *
     * @param key    the key of the weight to be set
     * @param weight the new weight value
     * @return True if the given key was valid (the according weight will have been updated)
     * False otherwise
     */
    @Override
    public boolean setWeight(String key, double weight) {
        if (!ACCEPTED_WEIGHTS.contains(key)) {
            return false;
        }
        this.weights.put(key, weight);
        return true;
    }

    /**
     * Retrieves the weight value of a given key
     *
     * @param key the weight'k key
     * @return the weight with the corresponding key if it exists, null otherwise
     */
    @Override
    public Double getWeight(String key) {
        if (!ACCEPTED_WEIGHTS.contains(key)) {
            return null;
        }
        return this.weights.get(key);
    }

    /**
     * Retrieves the number of pills in the edge
     *
     * @return the number of pills in the edge
     */
    public Double getPillsNumber() {
        return this.getWeight(WEIGHT_PILLS_NUMBER);
    }

    /**
     * Retrieves the number of power pills in the edge
     *
     * @return The number of power pills in the edge
     */
    public Double getPowerPillsNumber() {
        return this.getWeight(WEIGHT_POWER_PILLS_NUMBER);
    }

    /**
     * Retrieves the 'nodeA' of the edge
     * The returned value might not be the 'nodeA' passed in the constructor
     *
     * @return The nodeA of the edge
     */
    @Override
    public Node getNodeA() {
        return this.nodeA;
    }

    /**
     * Retrieves the 'nodeB' of the edge
     * The returned value might not be the 'nodeB' passed in the constructor
     *
     * @return The nodeB of the edge
     */
    @Override
    public Node getNodeB() {
        return this.nodeB;
    }

    /**
     * Checks if the current edge contains the given node
     *
     * @param node The node to look for in the current edge
     * @return True if the node is concerned by the current edge
     */
    @Override
    public boolean concernsNode(Node node) {
        return nodeA.equals(node) || nodeB.equals(node);
    }

    /**
     * Checks if a given object equals to the current edge.
     * The comparison is made according to the ID of the nodes of each edge
     *
     * @param o The object to compare with
     * @return True if the current edge is the same as the given object
     */
    @Override
    public boolean equals(Object o) {
        if (o instanceof Edge) {
            Edge edge = (Edge) o;
            return this.nodeA.equals(edge.nodeA) && this.nodeB.equals(edge.nodeB);
        }
        return false;
    }

    /**
     * Retrieves the neighbour of a Node with the current edge
     * If the given node is not concerned by the current Edge, a Null value is returned
     *
     * @param node A node
     * @return The node's neighbour with the current edge
     */
    @Override
    public Node getNeighbour(Node node) {
        if (this.nodeA.equals(node)) {
            return this.nodeB;
        } else if (this.nodeB.equals(node)) {
            return this.nodeA;
        } else {
            return null;
        }
    }

    /**
     * @return The string representation of the edge (composed of the string representation of its nodes)
     */
    @Override
    public String toString() {
        return this.nodeA.toString() + "," + this.nodeB.toString();
    }

    /**
     * @return The hashCode of the edge (which is the hashCode of its string representation)
     */
    @Override
    public int hashCode() {
        return this.toString().hashCode();
    }

    /**
     * Converts a chain of edges to an Edge.
     * The given edges must be in the order in the form A-B-C-...-X-Y-Z
     *
     * @param startingNode The starting node of the chain (the node A)
     * @param edges        a list of edges
     * @return an edge which is the sum of the given edges. The sum is done on the weights of the edges & the nodes in between.
     */
    public static Edge chainToEdge(Node startingNode, List<Edge> edges) {
        Map<String, Double> weights = new HashMap<>();
        Node nodeA = startingNode;
        Node nodeB = startingNode;
        Edge lastEdge;
        Edge result;
        if (edges.size() == 0) {
            return null;
        }
        lastEdge = edges.get(edges.size() - 1);
        for (String key : ACCEPTED_WEIGHTS) {
            weights.put(key, 0d);
        }
        for (Edge edge : edges) {
            for (String key : ACCEPTED_WEIGHTS) {
                weights.put(key, weights.get(key) + edge.getWeight(key));
            }
            nodeB = edge.getNeighbour(nodeB);
            if (!edge.equals(lastEdge)) {
                for (String key : ACCEPTED_WEIGHTS) {
                    weights.put(key, weights.get(key) + getNodeWeight(nodeB, key));
                }
            }
        }
        result = new Edge(nodeA, nodeB);
        for (String key : ACCEPTED_WEIGHTS) {
            result.setWeight(key, weights.get(key));
        }
        return result;
    }

    /**
     * Retrieves a node's weight specified by its key string
     *
     * @param node the node
     * @param key  the weight's string key
     * @return the weight value of the given node for the specified key
     */
    private static Double getNodeWeight(Node node, String key) {
        if (WEIGHT_PILLS_NUMBER.equals(key)) {
            return node.containsPill() ? 1d : 0d;
        } else if (WEIGHT_POWER_PILLS_NUMBER.equals(key)) {
            return node.containsPowerPill() ? 1d : 0d;
        } else {
            return 0d;
        }
    }
}
