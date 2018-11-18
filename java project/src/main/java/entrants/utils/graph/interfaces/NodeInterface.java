package entrants.utils.graph.interfaces;

/**
 * This interface describes the features that a class that represents nodes should offer
 * Here we just consider the nodes individually
 * We just should have a unique ID for each node
 */
public interface NodeInterface extends Comparable{
    /**
     * @return The ID of the node
     */
    Integer getId();
}
