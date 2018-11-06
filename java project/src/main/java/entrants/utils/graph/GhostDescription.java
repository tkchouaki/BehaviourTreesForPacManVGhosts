package entrants.utils.graph;
import pacman.game.Constants;

import java.util.Collection;
import java.util.HashSet;

/**
 * This class serves to describe a ghost as he is perceived by another agent.
 * We keep which ghost it is, its position & its edible time.
 */
public class GhostDescription extends AgentDescription{
    private final Constants.GHOST ghost;
    private int edibleTime;

    /**
     * Initializes a ghost description for a given ghost in a given position.
     * @param ghost
     * The ghost concerned by the description
     * @param position
     * Its position
     */
    public GhostDescription(Constants.GHOST ghost, Node position)
    {
        this(ghost, position, 0);
    }

    /**
     * Initializes a ghost description for a given given ghost, position & edible time.
     * @param ghost
     * The ghost concerned by the description.
     * @param position
     * Its position
     * @param edibleTime
     * Its edible time.
     */
    public GhostDescription(Constants.GHOST ghost, Node position, int edibleTime)
    {
        super(position);
        this.ghost = ghost;
        this.edibleTime = edibleTime;
    }

    /**
     * Retrieves the ghost concerned by the description
     * @return
     * The ghost concerned by the description
     */
    public Constants.GHOST getGhost()
    {
        return this.ghost;
    }

    /**
     * Retrieves the ghost's edible time
     * @return
     * The ghost's edible time.
     */
    public int getEdibleTime()
    {
        return this.getEdibleTime();
    }

    /**
     * Updates the edible time of the ghost.
     * @param edibleTime
     */
    public void setEdibleTime(int edibleTime)
    {
        this.edibleTime = edibleTime;
        this.postSetEdibleTime();
    }

    /**
     * Decrements the edible time of the ghost.
     */
    public void decrementEdibleTime()
    {
        this.edibleTime--;
        this.postSetEdibleTime();
    }

    /**
     * Resets the edible time to 0 if it goes below.
     */
    private void postSetEdibleTime()
    {
        if(this.edibleTime<0)
        {
            this.edibleTime=0;
        }
    }

    /**
     * Retrieves a specified ghost's description among an iterable of ghost descriptions.
     * @param ghostDescriptions
     * The ghost descriptions where the search should be performed.
     * @param ghost
     * The ghost whose description is desired.
     * @return
     * The description of the specified ghost if it exists. null otherwise.
     */
    public static GhostDescription getGhostDescription(Iterable<GhostDescription> ghostDescriptions, Constants.GHOST ghost)
    {
        for(GhostDescription ghostDescription : ghostDescriptions)
        {
            if(ghostDescription.getGhost().equals(ghost))
            {
                return ghostDescription;
            }
        }
        return null;
    }

    /**
     * Retrieves the ghosts concerned by given descriptions.
     * @param ghostDescriptions
     * An iterable of ghost descriptions.
     * @return
     * A collection containing the ghosts concerned by the given descriptions.
     */
    public static Collection<Constants.GHOST> getGhosts(Iterable<GhostDescription> ghostDescriptions)
    {
        Collection<Constants.GHOST> ghosts = new HashSet<>();
        for(GhostDescription ghostDescription : ghostDescriptions)
        {
            ghosts.add(ghostDescription.getGhost());
        }
        return ghosts;
    }

    /**
     * Removes the agent from its current position.
     */
    @Override
    public void removeFromPosition() {
        if(this.getPosition().containsGhost(this.ghost))
        {
            this.getPosition().removeGhost(this.ghost);
        }
    }

    /**
     * Adds the agent ot its current position.
     */
    @Override
    public void addToPosition() {
        if(!this.getPosition().containsGhost(this.ghost))
        {
            this.getPosition().addGhost(this);
        }
    }
}
