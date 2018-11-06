package entrants.utils.graph;

/**1
 * This class is used to describe an agent as he is perceived by another fellow agent.
 * At this level, we just keep its last known position
 */
public abstract class AgentDescription {
    private Node position;

    /**
     * Initializes an agent's description with a position
     * @param position
     * The position of the agent
     */
    public AgentDescription(Node position)
    {
        this.setPosition(position);
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
}
