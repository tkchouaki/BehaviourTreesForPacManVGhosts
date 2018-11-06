package entrants.utils.graph;

import entrants.utils.graph.interfaces.NodeInterface;
import entrants.utils.graph.interfaces.WeightedEdgeInterface;

/**
 * This class describes a Weighted Undirected Graph
 * It inherits from the UndirectedGraph class
 * @param <N>
 * The class that represents the nodes of the graph, this class should implement the Node interface
 * @param <E>
 * The class that represents the Edges of the graph, this class should implement the WeightedEdgeInterface
 */
public class WeightedUndirectedGraph<N extends NodeInterface, E extends WeightedEdgeInterface<N>> extends UndirectedGraph<N, E>{

}
