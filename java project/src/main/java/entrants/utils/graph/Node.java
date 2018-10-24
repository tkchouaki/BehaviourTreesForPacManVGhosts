package entrants.utils.graph;

import entrants.utils.Commons;
import pacman.game.Constants;
import pacman.game.Game;
import pacman.game.info.GameInfo;
import pacman.game.internal.Maze;

import java.util.HashSet;
import java.util.Set;

/**
 * This class represents a node on the PacMan game
 * A Node is a point in the 2D world, it can contain & pill, a power pill, a pacman, some ghosts.
 * Each Node in the world is identified by an index.
 */
public class Node {
    private final Integer id;
    /**
     * Every pill has an ID.
     * If the current node doesn't contain a pill, this id is set to -1
     */
    private int containedPillId;

    /**
     * Every power pill also has an ID.
     * If the current node doesn't contain a power pill, this id is set to -1
     */
    private int containedPowerPillId;

    private boolean containsPacMan;


    private Set<Constants.GHOST> containedGhosts;

    /**
     * Initializes a Node with the given ID
     * By default, a node doesn't contain anything
     * @param id
     * The node's ID
     */
    public Node(Integer id)
    {
        this.id = id;
        this.setContainedPillId(-1);
        this.setContainedPowerPillId(-1);
        this.setContainedGhosts(new HashSet<>());
        this.setContainsPacMan(false);
    }

    /**
     * Sets The ID of the contained Pill
     * @param containedPillId
     * the ID of the contained pill
     */
    public void setContainedPillId(int containedPillId) {
        this.containedPillId = containedPillId;
    }

    /**
     * Sets the ID of the contained power pill
     * @param containedPowerPillId
     * the id of the contained power pill
     */
    public void setContainedPowerPillId(int containedPowerPillId)
    {
        this.containedPowerPillId = containedPowerPillId;
    }

    /**
     * Checks if the current Node contains a pill
     * @return
     * True if the current Node contains a pill
     */
    public boolean containsPill() {
        return containedPillId >=0 ;
    }

    /**
     * Checks if the current Node contains a power pill
     * @return
     * True if the current node contains a power pill
     */
    public boolean containsPowerPill() {
        return containedPowerPillId >= 0;
    }

    /**
     * Returns the ID of the contained pill
     * @return
     * The ID of the contained Pill
     * If the current Node doesn't contain a pill, the returned value is -1
     */
    public int getContainedPillId() {
        return containedPillId;
    }

    /**
     * Returns the ID of the contained power pill
     * @return
     * The ID of the contained power pill
     * If the current Node doesn't contain a power pill, the returned value is -1
     */
    public int getContainedPowerPillId() {
        return containedPowerPillId;
    }

    /**
     * Checks if PacMan is in the current Node
     * @return
     * True if PacMan is in the current Node
     */
    public boolean containsPacMan() {
        return containsPacMan;
    }

    /**
     * Sets the containsPacMan attribute's value
     * @param containsPacMan
     */
    public void setContainsPacMan(boolean containsPacMan) {
        this.containsPacMan = containsPacMan;
    }

    /**
     * Retrieves the ghosts present in the current Node
     * @return
     * A Set of the ghosts present in the current Node.
     * The Returned Set is a copy, operations on it will not affect the Node.
     */
    public Set<Constants.GHOST> getContainedGhosts() {
        return new HashSet<>(containedGhosts);
    }

    /**
     * Checks if the current Node Contains a ghost
     * @return
     * True if the current Node contains a ghost
     */
    public boolean containsGhost()
    {
        return this.containedGhosts.size() > 0;
    }

    /**
     * Sets the containedGhosts attribute's value.
     * The given Set is copied, it can be manipulated freely after the call to this method
     * @param containedGhosts
     * A Set containing the ghosts present in the Node
     */
    public void setContainedGhosts(Set<Constants.GHOST> containedGhosts) {
        this.containedGhosts = new HashSet<>(containedGhosts);
    }

    /**
     * Retrieves the ID of the current node
     * @return
     * The current Node's ID
     */
    public Integer getId()
    {
        return this.id;
    }

    /**
     * Checks if the current node is equal to a given object
     * @param obj
     * The object to compare with the node
     * @return
     * True if the given object is a Node with the same id as the current Node
     */
    @Override
    public boolean equals(Object obj) {
        if (obj != null && obj.getClass().equals(this.getClass())) {
            Node n = (Node) obj;
            return n.getId().equals(this.getId());
        }
        return false;
    }

    /**
     * Returns the hash code of the current node
     * @return
     * The hash code of the current node (which is the hash code of its ID)
     */
    @Override
    public int hashCode()
    {
        return this.id.hashCode();
    }

    @Override
    public String toString()
    {
        return this.id.toString();
    }

    /**
     * Retrieves a Node with the desired ID from an Iterable of Nodes
     * @param nodes
     * The search scope
     * @param id
     * The id of the desired Node
     * @return
     * The node with the given id, null if not found
     */
    public static Node getNodeById(Iterable<Node> nodes, Integer id)
    {
        for(Node node : nodes)
        {
            if(node.id.equals(id))
            {
                return node;
            }
        }
        return null;
    }

    /**
     * Retrieves the nodes containing pills from an Iterable of Nodes
     * @param nodes
     * The search scope
     * @return
     * A Set containing the Nodes of the given Iterable that contain pills
     */
    public static Set<Node> getNodesWithPills(Iterable<Node> nodes)
    {
        Set<Node> result = new HashSet<>();
        for(Node node : nodes)
        {
            if(node.containsPill())
            {
                result.add(node);
            }
        }
        return result;
    }

    /**
     * Retrieves the nodes containing power pills from an Iterable of Nodes
     * @param nodes
     * The search scope
     * @return
     * A Set containing the Nodes of the given Iterable that contain power pills
     */
    public static Set<Node> getNodesWithPowerPills(Iterable<Node> nodes)
    {
        Set<Node> result = new HashSet<>();
        for(Node node : nodes)
        {
            if(node.containsPowerPill())
            {
                result.add(node);
            }
        }
        return result;
    }


    /**
     * Retrieves the nodes containing ghosts from an Iterable of Nodes
     * @param nodes
     * The search scope
     * @return
     * A Set containing the Nodes of the given Iterable that contain ghosts
     */
    public static Set<Node> getNodesContainingGhosts(Iterable<Node> nodes)
    {
        Set<Node> result = new HashSet<>();
        for(Node node : nodes)
        {
            if(node.containsGhost())
            {
                result.add(node);
            }
        }
        return result;
    }


    /**
     * Returns the node containing a specified ghost among a given iterable of nodes
     * @param nodes
     * The search scope
     * @param ghost
     * The desired ghost
     * @return
     * The Node containing the specified ghost among the given iterable
     * Caution : this method assumes that a ghost can only be at one node at a time
     * If no such Node is found, a null value is returned
     */
    public static Node getNodeContainingGhost(Iterable<Node> nodes, Constants.GHOST ghost)
    {
        for(Node node : nodes)
        {
            if(node.getContainedGhosts().contains(ghost))
            {
                return node;
            }
        }
        return null;
    }

    /**
     * Returns the node containing PacMan among a given iterable of nodes
     * @param nodes
     * The search scope
     * @return
     * The Node containing PacMan among the given iterable
     * Caution : this method assumes that PacMan can only be at one node at a time
     * If no such Node is found, a null value is returned
     */
    public static Node getNodeContainingPacMan(Iterable<Node> nodes)
    {
        for(Node node : nodes)
        {
            if(node.containsPacMan())
            {
                return node;
            }
        }
        return null;
    }
}
