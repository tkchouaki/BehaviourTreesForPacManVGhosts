package entrants.utils.graph.interfaces;

public interface WeightedEdgeInterface<N extends NodeInterface> extends EdgeInterface<N> {
    boolean setWeight(String key, double weight);
    Double getWeight(String key);
}
