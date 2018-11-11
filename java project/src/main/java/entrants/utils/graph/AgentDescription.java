package entrants.utils.graph;

/**1
 * This class is used to describe an agent as he is perceived by another fellow agent.
 * At this level, we just keep its last known position & the tick of the last update
 */
public abstract class AgentDescription {
    private Node previousPosition;
    private Node position;
    private int lastUpdateTick;

    /**
     * Initializes an agent's description with a position
     * @param position
     * The position of the agent
     */
    public AgentDescription(Node position)
    {
        this.setPosition(position);
        this.lastUpdateTick = 0;
    }

    /**
     * Retrieves the second last known position of the agent.
     * @return
     * The second last known position of the agent.
     */
    public Node getPreviousPosition()
    {
        return this.previousPosition;
    }

    /**
     * Retrieves the last known position of the agent.
     * @return
     * The last known position of the agent
     */
    public Node getPosition()
    {
        return this.position;
    }

    /**
     * Updates the agent's position
     * @param position
     */
    public void setPosition(Node position)
    {
        this.previousPosition = this.position;
        if(this.position != null)
        {
            this.removeFromPosition();
        }
        this.position = position;
        if(this.position != null)
        {
            this.addToPosition();
        }
    }

    /**
     * To be implemented by subclasses
     */
    public abstract void removeFromPosition();

    /**
     * To be implemented by subclasses
     */
    public abstract void addToPosition();

    /**
     * Retrieves the last tick when the description was updated
     * @return The last tick when the description was updated
     */
    public int getLastUpdateTick()
    {
        return this.lastUpdateTick;
    }

    /**
     * Sets the lastUpdateTick attribute
     * Only works if the given value is higher than the current one.
     * @param lastUpdateTick The new value for the lastUpdateTick attribute
     * @return True if the value was updated, false otherwise.
     */
    public boolean setLastUpdateTick(int lastUpdateTick)
    {
        if(this.lastUpdateTick < lastUpdateTick)
        {
            this.lastUpdateTick = lastUpdateTick;
            return true;
        }
        return false;
    }
}
