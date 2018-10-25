package entrants.utils.graph.interfaces;

public interface WeightedEdgeInterface extends EdgeInterface{
    boolean setWeight(String key, double weight);
    Double getWeight(String key);
}
