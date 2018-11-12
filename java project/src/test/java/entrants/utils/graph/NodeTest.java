package entrants.utils.graph;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pacman.game.Constants;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

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
        assertFalse(node.containsPill());
    }

    @Test
    void getContainedPillId() {
        assertFalse(node.containsPowerPill());
    }

    @Test
    void setContainedPillId() {
        node.setContainedPillId(12);
        assertTrue(node.containsPill());
        assertEquals(12, node.getContainedPillId());
    }

    @Test
    void containsPowerPill() {
        node.setContainedPowerPillId(12);
        assertTrue(node.containsPowerPill());
        assertEquals(12, node.getContainedPowerPillId());
    }

    @Test
    void getContainedPowerPillId() {
        assertEquals(-1, node.getContainedPillId());
    }

    @Test
    void setContainedPowerPillId() {
        assertEquals(-1, node.getContainedPowerPillId());
    }

    @Test
    void containsPacMan() {
        assertFalse(node.containsPacMan());
    }

    @Test
    void setContainsPacMan() {
        node.setContainedPacMan(new PacManDescription(new Node(1, 1, 1)));
        assertTrue(node.containsPacMan());
    }

    @Test
    void containsGhost() {
        assertFalse(node.containsGhost());
        for (Constants.GHOST ghost : Constants.GHOST.values()) {
            assertFalse(node.containsGhost(ghost));
        }
    }

    @Test
    void getContainedGhosts() {
        assertNotNull(node.getContainedGhosts());
        assertEquals(0, node.getContainedGhosts().size());
    }

    @Test
    void setContainedGhosts() {
        Set<GhostDescription> ghosts = new HashSet<>();
        Node n = new Node(1, 1, 1);
        for (Constants.GHOST g : Constants.GHOST.values()) {
            ghosts.add(new GhostDescription(g, n));
        }
        node.setContainedGhosts(ghosts);

        assertTrue(ghosts.containsAll(node.getContainedGhosts()));
        assertTrue(node.getContainedGhosts().containsAll(ghosts));
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