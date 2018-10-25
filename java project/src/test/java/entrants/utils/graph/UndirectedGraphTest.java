package entrants.utils.graph;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class UndirectedGraphTest {
    // ATTRIBUTES
    private UndirectedGraph<Node, Edge> graph;

    @BeforeEach
    void setUp() {
        graph = new UndirectedGraph<>();
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void getNodes() {
        // Nodes added to the graph
        Node[] nodes = new Node[] {
                new Node(0, 0, 0),
                new Node(1, 0, 1),
                new Node(2, 1, 0),
        };
        for (Node node : nodes) {
            graph.addNode(node);
        }
        assertTrue(graph.getNodes().containsAll(Arrays.asList(nodes)));
    }

    @Test
    void getEdges() {
        // Nodes added through addEdge()
        Node[] nodes = new Node[] {
                new Node(0, 0, 0),
                new Node(1, 0, 1)
        };

        // Edges
        Edge[] edges = new Edge[] {
                new Edge(nodes[0], nodes[1]),
                new Edge(nodes[1], nodes[0])
        };

        for (Edge edge : edges) {
            graph.addEdge(edge);
        }
        assertTrue(graph.getEdges().contains(edges[0]));
        assertEquals(1, graph.getEdges().size());
    }

    @Test
    void getPropertyChangeSupport() {
    }

    @Test
    void getDegreeOf() {
    }

    @Test
    void getNeighboursAsNodesOf() {
    }

    @Test
    void getNeighboursAsEdgesOf() {
    }

    @Test
    void getNodeByID() {
    }

    @Test
    void addNode() {
    }

    @Test
    void addEdge() {
        // Nodes added through addEdge()
        Node[] nodes = new Node[] {
                new Node(0, 0, 0),
                new Node(1, 0, 1)
        };
        // Node added with addNode()
        Node node1 = new Node(2, 1, 0);
        Node node2 = new Node(3, 1, 1);

        graph.addNode(node1);
        graph.addNode(node2);

        // Edges
        Edge[] edges = new Edge[] {
                new Edge(nodes[0], nodes[1]),
                new Edge(nodes[0], node1),
                new Edge(node1, node2)
        };

        for (Edge edge : edges) {
            graph.addEdge(edge);
        }
        assertTrue(graph.getEdges().containsAll(Arrays.asList(edges)));
        assertTrue(graph.getNodes().containsAll(Arrays.asList(nodes))); // nodes added through addEdge()

    }

    @Test
    void removeNode() {
    }

    @Test
    void removeEdge() {
    }
}