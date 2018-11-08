package entrants.utils.graph;

import entrants.utils.graph.interfaces.IUndirectedGraph;
import org.junit.Assert;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

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
    void getDegreeOf() throws Exception {
        // Nodes added through addEdge()
        Node[] nodes = new Node[] {
                new Node(0, 0, 0),
                new Node(1, 0, 1),
                new Node(2, 1, 0)
        };

        // Edges
        Edge[] edges = new Edge[] {
                new Edge(nodes[0], nodes[1]),
                new Edge(nodes[2], nodes[0])
        };

        for (Edge edge : edges) {
            graph.addEdge(edge);
        }

        assertEquals(graph.getDegreeOf(nodes[0]), 2);
        assertThrows(
                IUndirectedGraph.NodeNotFoundException.class,
                () -> graph.getDegreeOf(new Node(3, 1, 1))
        );
    }

    @Test
    void getNeighboursAsNodesOf() throws Exception {
        // Nodes added through addEdge()
        Node[] nodes = new Node[] {
                new Node(0, 0, 0),
                new Node(1, 0, 1),
                new Node(2, 1, 0)
        };

        // Edges
        Edge[] edges = new Edge[] {
                new Edge(nodes[0], nodes[1]),
                new Edge(nodes[2], nodes[0])
        };

        for (Edge edge : edges) {
            graph.addEdge(edge);
        }

        assertTrue(graph.getNeighboursAsNodesOf(nodes[0]).containsAll(Arrays.asList(nodes[1], nodes[2])));
        assertThrows(AssertionError.class, () -> graph.getNeighboursAsNodesOf(null));
        assertThrows(
                IUndirectedGraph.NodeNotFoundException.class,
                () -> graph.getNeighboursAsNodesOf(new Node(3, 1, 1))
        );
    }

    @Test
    void getNeighboursAsEdgesOf() throws Exception {
        // Nodes added through addEdge()
        Node[] nodes = new Node[] {
                new Node(0, 0, 0),
                new Node(1, 0, 1),
                new Node(2, 1, 0)
        };

        // Edges
        Edge[] edges = new Edge[] {
                new Edge(nodes[0], nodes[1]),
                new Edge(nodes[2], nodes[0])
        };

        for (Edge edge : edges) {
            graph.addEdge(edge);
        }

        assertThrows(AssertionError.class, () -> graph.getNeighboursAsEdgesOf(null));
        assertTrue(graph.getNeighboursAsEdgesOf(nodes[0]).containsAll(Arrays.asList(edges)));
        assertThrows(
                IUndirectedGraph.NodeNotFoundException.class,
                () -> graph.getNeighboursAsEdgesOf(new Node(3, 1, 1))
        );
    }

    @Test
    void getNodeByID() {
        Node[] nodes = new Node[] {
                new Node(0, 0, 0),
                new Node(1, 0, 1),
                new Node(2, 1, 0)
        };

        graph.addNode(nodes[0]);
        graph.addEdge(new Edge(nodes[1], nodes[2]));

        assertEquals(nodes[0], graph.getNodeByID(nodes[0].getId()));
        assertEquals(nodes[1], graph.getNodeByID(nodes[1].getId()));
        assertEquals(nodes[2], graph.getNodeByID(nodes[2].getId()));

        assertNull(graph.getNodeByID(12));
        assertThrows(AssertionError.class, () -> graph.getDegreeOf(null));
    }

    @Test
    void getPropertyChangeSupport() {
        assertNotNull(graph.getPropertyChangeSupport());
    }

    @Test
    void addNode() {
        Node[] nodes = new Node[] {
                new Node(0, 0, 0),
                new Node(1, 0, 1),
                new Node(2, 1, 0)
        };
        for (Node node : nodes) {
            graph.addNode(node);
        }

        assertTrue(graph.getNodes().containsAll(Arrays.asList(nodes)));
        assertEquals(graph.getNodes().size(), nodes.length);
        assertThrows(AssertionError.class, () -> graph.addNode(null));
        assertFalse(graph.addNode(nodes[0]));
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
        assertThrows(AssertionError.class, () -> graph.addEdge(null));
    }

    @Test
    void removeNode() {
        // Nodes
        Node[] nodes = new Node[] {
                new Node(0, 0, 0),
                new Node(1, 0, 1),
                new Node(2, 1, 0),
                new Node(3, 1, 1)
        };

        graph.addNode(nodes[3]);

        // Edges
        Edge[] edges = new Edge[] {
                new Edge(nodes[0], nodes[1]),
                new Edge(nodes[0], nodes[2]),
                new Edge(nodes[1], nodes[2])
        };

        for (Edge edge : edges) {
            graph.addEdge(edge);
        }

        assertTrue(graph.removeNode(nodes[0]));
        assertFalse(graph.removeNode(nodes[0]));
        assertTrue(graph.getEdges().contains(edges[2]));
        assertEquals(graph.getEdges().size(), 1);
        assertTrue(graph.getNodes().containsAll(Arrays.asList(nodes[1], nodes[2], nodes[3])));
        assertEquals(graph.getNodes().size(), 3);

        graph.removeNode(nodes[3]);
        assertTrue(graph.getEdges().contains(edges[2]));
        assertEquals(graph.getEdges().size(), 1);
        assertTrue(graph.getNodes().containsAll(Arrays.asList(nodes[1], nodes[2])));
        assertEquals(graph.getNodes().size(), 2);

        assertThrows(AssertionError.class, () -> graph.removeNode(null));
    }

    @Test
    void removeEdge() {
        // Nodes
        Node[] nodes = new Node[] {
                new Node(0, 0, 0),
                new Node(1, 0, 1),
                new Node(2, 1, 0)
        };

        // Edges
        Edge[] edges = new Edge[] {
                new Edge(nodes[0], nodes[1]),
                new Edge(nodes[0], nodes[2]),
                new Edge(nodes[1], nodes[2])
        };

        for (Edge edge : edges) {
            graph.addEdge(edge);
        }

        assertTrue(graph.removeEdge(edges[0]));
        assertEquals(graph.getEdges().size(), 2);
        assertTrue(graph.getEdges().containsAll(Arrays.asList(edges[2], edges[1])));

        assertFalse(graph.removeEdge(edges[0]));
        assertThrows(AssertionError.class, () -> graph.removeEdge(null));
    }

    @Test
    void clear() {
        graph.addNode(new Node(0, 0, 0));
        graph.clear();

        assertEquals(0, graph.getNodes().size());
        assertEquals(0, graph.getEdges().size());
    }

    @Test
    void circleNode() {
        Node[] nodes = new Node[] {
                new Node(0, 0, 0),
                new Node(1, 0, 1),
                new Node(2, 1, 0),
                new Node(3, 1, 1)
        };
        for (Node node : nodes) {
            graph.addEdge(new Edge(nodes[0], nodes[1]));
            graph.addEdge(new Edge(nodes[0], nodes[2]));
            graph.addEdge(new Edge(nodes[1], nodes[3]));
        }

        List<Node> circling = Arrays.asList(nodes[1], nodes[2]);
        Map<Node, List<Edge>> result = graph.circleNode(nodes[0], circling);

        assertNotNull(result);
        assertTrue(circling.containsAll(result.keySet()));
        assertEquals(2, result.size());
        assertFalse(result.containsKey(nodes[0]));
        assertFalse(result.containsKey(nodes[3]));
    }
}