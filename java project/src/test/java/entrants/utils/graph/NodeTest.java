package entrants.utils.graph;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class NodeTest {

    private Node node;
    private static final int NODE_ID = 0;
    private static final int NODE_X = 0;
    private static final int NODE_Y = 0;

    @BeforeEach
    void setUp() {
        node = new Node(NODE_ID, NODE_X, NODE_Y);
    }

    @Test
    void getId() {
        assertEquals(node.getId().intValue(), NODE_ID);
    }

    @Test
    void getX() {
        assertEquals(node.getX(), NODE_X);
    }

    @Test
    void getY() {
        assertEquals(node.getY(), NODE_Y);
    }

    @Test
    void isDecisionNode() {
        assertFalse(node.isDecisionNode());
    }

    @Test
    void setIsDecisionNode() {
        node.setIsDecisionNode(true);
        assertTrue(node.isDecisionNode());
    }

    @Test
    void containsPill() {
    }

    @Test
    void getContainedPillId() {
    }

    @Test
    void setContainedPillId() {
    }

    @Test
    void containsPowerPill() {
    }

    @Test
    void getContainedPowerPillId() {
    }

    @Test
    void setContainedPowerPillId() {
    }

    @Test
    void containsPacMan() {
    }

    @Test
    void setContainsPacMan() {
    }

    @Test
    void containsGhost() {
    }

    @Test
    void getContainedGhosts() {
    }

    @Test
    void setContainedGhosts() {
    }

    @Test
    void addChangeEventListener() {
    }

    @Test
    void removeChangeEventListener() {
    }

    @Test
    void compareTo() {
    }

    @Test
    void getNodeById() {
    }

    @Test
    void getNodesWithPills() {
    }

    @Test
    void getNodesWithPowerPills() {
    }

    @Test
    void getNodesContainingGhosts() {
    }

    @Test
    void getNodeContainingGhost() {
    }

    @Test
    void getNodeContainingPacMan() {
    }
}