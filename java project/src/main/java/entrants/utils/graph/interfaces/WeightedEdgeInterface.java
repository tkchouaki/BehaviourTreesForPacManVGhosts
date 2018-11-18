package entrants.utils.graph.interfaces;

/**
 * This interface describes the features that a class that represents weighted edges linking NodeInterface objects should offer
 * The Edges are not oriented
 * An edge can have multiple weights, differentiated by string keys.
 */
public interface WeightedEdgeInterface<N extends NodeInterface> extends EdgeInterface<N> {
    /**
     * Sets a particular weight's value
     * @param key the string key of the weight
     * @param weight the value of the weight
     * @return True if the weight's value was set to the given one, false otherwise
     */
    boolean setWeight(String key, double weight);

    /**
     * Retrieves the value of a particular weight
     * @param key The string key of the weight
     * @return The weight's value, if the key was invalid, should return a null value.
     */
    Double getWeight(String key);
}
