package entrants.utils.graph;

import pacman.game.Constants;

import java.util.HashMap;
import java.util.Map;

/**
 * This class serves to store what an agent knows about its es environment & the other agents.
 */
public class AgentKnowledge {
    private final WeightedUndirectedGraph<Node, Edge> graph;
    private final Constants.GHOST owner;
    private final PacManDescription pacManDescription;
    private final Map<Constants.GHOST, GhostDescription> ghostsDescriptions;

    /**
     * Initializes an agent's knowledge with an empty graph.
     *
     * @param owner The ghost that owns the knowledge. If it's PacMan's knowledge specify a null value.
     */
    public AgentKnowledge(Constants.GHOST owner) {
        this.graph = new WeightedUndirectedGraph<>();
        this.owner = owner;
        this.pacManDescription = new PacManDescription(null);
        this.ghostsDescriptions = new HashMap<>();
        for (Constants.GHOST ghost : Constants.GHOST.values()) {
            this.ghostsDescriptions.put(ghost, new GhostDescription(ghost, null));
        }
    }

    /**
     * Retrieves the graph of the agent.
     *
     * @return The agent's graph.
     */
    public UndirectedGraph<Node, Edge> getGraph() {
        return this.graph;
    }

    /**
     * Retrieves the knowledge that the agent has about itself.
     *
     * @return An AgentDescription object representing what the agent knows about itself.
     * If the owner is PacMan, the returned object is also an instance of PacManDescription
     * If the owner is a ghost, the returned object is also an instance of GhostDescription
     */
    public AgentDescription getKnowledgeAboutMySelf() {
        if (this.owner == null) {
            return this.getPacManDescription();
        }
        return this.getGhostDescription(this.owner);
    }

    /**
     * Retrieves the description that the current agent has about PacMan
     *
     * @return A PacManDescription object
     */
    public PacManDescription getPacManDescription() {
        return this.pacManDescription;
    }

    /**
     * Retrieves the description that the agent has about a particular ghost
     *
     * @param ghost The ghost whose description is desired
     * @return The desired ghost's description
     */
    public GhostDescription getGhostDescription(Constants.GHOST ghost) {
        return this.ghostsDescriptions.get(ghost);
    }

    /**
     * Retrieves the descriptions that the agent has about all the ghosts.
     *
     * @return The ghosts descriptions.
     * Note that the received HashMap is a copy of the one that is maintained in the AgentKnowledge
     */
    public Map<Constants.GHOST, GhostDescription> getGhostsDescription() {
        return new HashMap<>(this.ghostsDescriptions);
    }

    /**
     * Retrieves the owner of the knowledge
     *
     * @return The Ghost owning the knowledge, if it is owned by PacMan, a null value is returned
     */
    public Constants.GHOST getOwner() {
        return this.owner;
    }
}
