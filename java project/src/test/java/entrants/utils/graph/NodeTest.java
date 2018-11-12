package entrants.utils.graph;

import entrants.utils.ChangeEventListener;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pacman.game.Constants;

import javax.swing.event.ChangeEvent;
import java.util.*;

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
    void setContainsPacManDescription() {
        node.setContainedPacManDescription(new PacManDescription(new Node(1, 1, 1)));
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
    void setContainedGhostsDescriptions() {
        Set<GhostDescription> ghosts = new HashSet<>();
        Node n = new Node(1, 1, 1);
        for (Constants.GHOST g : Constants.GHOST.values()) {
            ghosts.add(new GhostDescription(g, n));
        }
        node.setContainedGhostsDescriptions(ghosts);

        assertTrue(ghosts.containsAll(node.getContainedGhostsDescriptions()));
        assertTrue(node.getContainedGhostsDescriptions().containsAll(ghosts));
    }

    @Test
    void addChangeEventListener() {
        List<Object> notifications = new ArrayList<>();

        // Register a dummy listener
        node.addChangeEventListener(new ChangeEventListener() {
            @Override
            public void changed(ChangeEvent evt) {
                notifications.add(true);
            }
        });

        // Change something
        node.setContainedPillId(12);

        // Check if we received a notification
        assertEquals(1, notifications.size());
    }

    @Test
    void removeChangeEventListener() {
        List<Object> notifications = new ArrayList<>();

        // Register a dummy listener
        ChangeEventListener listener = new ChangeEventListener() {
            @Override
            public void changed(ChangeEvent evt) {
                notifications.add(true);
            }
        };
        node.addChangeEventListener(listener);

        // Remove it
        node.removeChangeEventListener(listener);

        // Be sure nothing is received
        assertEquals(0, notifications.size());
    }

    @Test
    void compareTo() {
        assertEquals(-1, node.compareTo(new Node(1, 1, 1)));
        assertEquals(Integer.MIN_VALUE, node.compareTo(new Object()));
    }

    @Test
    void getNodeById() {
        List<Node> nodes = new ArrayList<>(Arrays.asList(
                new Node(0, 0, 0),
                new Node(1, 1, 0),
                new Node(2, 0, 1),
                new Node(3, 1, 1)
        ));
        for (int i = 0; i < 4; ++i) {
            assertEquals(nodes.get(0), Node.getNodeById(nodes, 0));
        }
        assertNull(Node.getNodeById(nodes, 12));
    }

    @Test
    void getNodesWithPills() {
        List<Node> nodes = new ArrayList<>(Arrays.asList(
                new Node(0, 0, 0),
                new Node(1, 1, 0),
                new Node(2, 0, 1),
                new Node(3, 1, 1)
        ));
        assertEquals(0, Node.getNodesWithPills(nodes).size());
        for (Node node : nodes) {
            node.setContainedPillId(1);
        }
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