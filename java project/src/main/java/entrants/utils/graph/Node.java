package entrants.utils.graph;

import entrants.utils.ChangeEventListener;
import entrants.utils.graph.interfaces.NodeInterface;
import pacman.game.Constants;

import javax.swing.event.ChangeEvent;
import javax.swing.event.EventListenerList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 * This class represents a node on the PacMan game
 * A Node is a point in the 2D world, it can contain & pill, a power pill, a pacman, some ghosts.
 * Each Node in the world is identified by an index.
 */
public class Node implements NodeInterface {

    private final Integer id;

    /**
     * The X component of the node's coordinates
     */
    private final int x;

    /**
     * The Y component of the node's coordinates
     */
    private final int y;


    private final EventListenerList support;
    private final ChangeEvent event;

    /**
     * Indicates if the node is a decision node
     */
    private boolean isDecisionNode;

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

    /**
     * The description of PacMan if it is in the current Node
     * If not, this attribute should be set to null
     */
    private PacManDescription containedPacMan;

    /**
     * A set of contained ghosts descriptions
     */
    private Set<GhostDescription> containedGhosts;

    private int lastUpdateTick;

    /**
     * Initializes a Node with the given ID & coordinates
     * By default, a node doesn't contain anything & is not a decision node
     * @param id
     * The node's ID
     * @param x
     * The x component of the Node's coordinates
     * @param y
     * The y component of the Node's coordinates
     */
    public Node(Integer id, int x, int y)
    {
        this(id, x, y, false);
    }


    /**
     * Initializes a Node with the given ID, coordinates & isDecisionNode value
     * @param id
     * The node's ID
     * @param x
     * The x component of the node's coordinates
     * @param y
     * The y componenet of the node's coordinates
     * @param isDecisionNode
     * A boolean indicating if the node is a decision node
     */
    public Node(Integer id, int x, int y, boolean isDecisionNode)
    {
        support = new EventListenerList();
        event = new ChangeEvent(this);

        this.id = id;
        this.x = x;
        this.y = y;

        this.isDecisionNode = isDecisionNode;
        this.setContainedPillId(-1);
        this.setContainedPowerPillId(-1);
        this.setContainedGhosts(new HashSet<>());
        this.setContainedPacMan(null);
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
     * Retrieves the X component of the node's coordinates
     * @return
     */
    public int getX() {
        return x;
    }

    /**
     * Retrieves the Y component of the node's coordinates
     * @return
     */
    public int getY() {
        return y;
    }

    /**
     * Retrieves a boolean indicating if the current node is a decision node
     * i.e where the agent needs to make a decision
     * @return
     */
    public boolean isDecisionNode()
    {
        return this.isDecisionNode;
    }

    /**
     * Sets the isDecisionNode property
     * @param isDecisionNode
     */
    public void setIsDecisionNode(boolean isDecisionNode)
    {
        this.isDecisionNode = isDecisionNode;
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
     * Returns the ID of the contained pill
     * @return
     * The ID of the contained Pill
     * If the current Node doesn't contain a pill, the returned value is -1
     */
    public int getContainedPillId() {
        return containedPillId;
    }

    public int getLastUpdateTick() {
        return lastUpdateTick;
    }

    /**
     * Sets The ID of the contained Pill
     * @param containedPillId
     * the ID of the contained pill
     */
    public void setContainedPillId(int containedPillId) {
        this.containedPillId = containedPillId;
        fireChangeEvent();
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
     * Returns the ID of the contained power pill
     * @return
     * The ID of the contained power pill
     * If the current Node doesn't contain a power pill, the returned value is -1
     */
    public int getContainedPowerPillId() {
        return containedPowerPillId;
    }

    /**
     * Sets the ID of the contained power pill
     * @param containedPowerPillId
     * the id of the contained power pill
     */
    public void setContainedPowerPillId(int containedPowerPillId)
    {
        this.containedPowerPillId = containedPowerPillId;
        fireChangeEvent();
    }

    public PacManDescription getContainedPacManDescription()
    {
        return this.containedPacMan;
    }

    /**
     * Checks if PacMan is in the current Node
     * @return
     * True if PacMan is in the current Node
     */
    public boolean containsPacMan() {
        return this.containedPacMan != null;
    }

    /**
     * Sets the containsPacMan attribute's value
     * @param containedPacMan
     */
    public void setContainedPacMan(PacManDescription containedPacMan) {
        PacManDescription old = this.containedPacMan;
        this.containedPacMan = containedPacMan;
        if(old != null && old.getPosition().equals(this))
        {
            old.setPosition(null);
        }
        if(this.containedPacMan != null && !this.containedPacMan.getPosition().equals(this))
        {
            this.containedPacMan.setPosition(this);
        }
        fireChangeEvent();
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
     * Checks if the current node contains a specific ghost
     * @param ghost
     * The ghost to look for
     * @return
     * True if the current node contains the specified ghost, false otherwise.
     */
    public boolean containsGhost(Constants.GHOST ghost)
    {
        return GhostDescription.getGhostDescription(this.containedGhosts, ghost) != null;
    }

    /**
     * Retrieves the description of a ghost contained in the node.
     * @param ghost
     * The ghost whose description is desired
     * @return
     * The description of the desired ghost if it is in the current node.
     * Otherwise, a null value is returned.
     */
    public GhostDescription getContainedGhostDescription(Constants.GHOST ghost)
    {
        return GhostDescription.getGhostDescription(this.containedGhosts, ghost);
    }

    /**
     * Adds a ghost's description to the current Node.
     * @param ghostDescription
     * The ghost's description
     * @return
     * True if the description didn't exist in the node & it was added.
     */
    public boolean addGhost(GhostDescription ghostDescription)
    {
        boolean toReturn = this.containedGhosts.add(ghostDescription);
        if(!ghostDescription.getPosition().equals(this))
        {
            ghostDescription.setPosition(this);
        }
        this.fireChangeEvent();
        return toReturn;
    }


    /**
     * Removes a ghost's description from the current node
     * @param ghost
     * The ghost whose description will be removed.
     * @return
     * True if the ghost was in the current node.
     * False otherwise.
     */
    public boolean removeGhost(Constants.GHOST ghost)
    {
        GhostDescription toRemove = GhostDescription.getGhostDescription(this.containedGhosts, ghost);
        if(toRemove == null)
        {
            return false;
        }
        boolean toReturn = this.containedGhosts.remove(toRemove);
        toRemove.setPosition(null);
        this.fireChangeEvent();
        return toReturn;
    }

    /**
     * Retrieves the ghosts present in the current Node
     * @return
     * A Set of the ghosts present in the current Node.
     */
    public Collection<Constants.GHOST> getContainedGhosts() {
        return GhostDescription.getGhosts(this.containedGhosts);
    }

    /**
     * Sets the containedGhosts attribute's value.
     * The given Set is copied, it can be manipulated freely after the call to this method
     * @param containedGhosts
     * A Set containing the ghosts present in the Node
     */
    public void setContainedGhosts(Set<GhostDescription> containedGhosts) {
        this.containedGhosts = new HashSet<>(containedGhosts);
        fireChangeEvent();
    }

    public void addChangeEventListener(ChangeEventListener listener) {
        support.add(ChangeEventListener.class, listener);
    }

    public void removeChangeEventListener(ChangeEventListener listener) {
        support.remove(ChangeEventListener.class, listener);
    }

    public void setLastUpdateTick(int tick) {
        lastUpdateTick = tick;
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

    // TOOLS
    private void fireChangeEvent() {
        for (ChangeEventListener l : support.getListeners(ChangeEventListener.class)) {
            l.changed(event);
        }
    }

    /**
     * Compares the current Node with another one (allows to sort)
     * @param o
     * The object to compare the current node with
     * @return
     * An integer representing the difference between the current Node & the given object.
     */
    @Override
    public int compareTo(Object o) {
        if(o instanceof NodeInterface)
        {
            return this.getId() - ((NodeInterface)o).getId();
        }
        return Integer.MIN_VALUE;
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
