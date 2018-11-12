package entrants.utils.graph;

/**
 * This class serves to describe the PacMan as he is perceived by the other agents.
 * For now, just his position is stored
 */
public class PacManDescription extends AgentDescription{

    /**
     * Initializes the description with a given position.
     * @param position
     * The PacMan's position.
     */
    public PacManDescription(Node position) {
        super(position);
    }

    /**
     * Removes the PacManDescription from its current position.
     */
    @Override
    public void removeFromPosition() {
        if(this.getPosition().containsPacMan())
        {
            this.getPosition().setContainedPacManDescription(null);
        }
    }

    /**
     * Adds the PacManDescription to its current position.
     */
    @Override
    public void addToPosition() {
        if(!this.getPosition().containsPacMan())
        {
            this.getPosition().setContainedPacManDescription(this);
        }
    }
}
