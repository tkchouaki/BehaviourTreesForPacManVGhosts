package entrants.ghosts.username;

import entrants.utils.Commons;
import entrants.utils.graph.*;
import pacman.controllers.IndividualGhostController;
import pacman.game.Constants;
import pacman.game.Game;
import pacman.game.comms.Message;
import pacman.game.internal.Maze;

import java.beans.PropertyChangeSupport;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * This class serves as a base class for the ghosts
 * Each ghost has a knowledge about its environment (The topology of the level, the other ghosts, the nodes containing pills/power pills) and the PacMan
 * The knowledge of a ghost can be visualized using GraphStream
 */
public class Ghost extends IndividualGhostController {

    // STATICS
    public static final String MAZE_CHANGED_PROP = "maze_changed";
    private static final Logger LOGGER = Logger.getLogger(Ghost.class.getName());
    static {
        LOGGER.setLevel(Level.INFO);
    }

    // ATTRIBUTES
    private final WeightedUndirectedGraph<Node, Edge> graph;
    private DiscreteKnowledgeGraph discreteKnowledgeGraph;
    private Maze currentMaze;
    private boolean initialized;
    private final PropertyChangeSupport support;

    /**
     * Initializes the ghost
     * @param ghost The ghost type (Blinky, Inky, Pinky & Sue)
     */
    public Ghost(Constants.GHOST ghost) {
        super(ghost);
        this.graph = new WeightedUndirectedGraph<>();
        this.support = new PropertyChangeSupport(this);
        this.discreteKnowledgeGraph = new DiscreteKnowledgeGraph(graph);
    }

    public UndirectedGraph<Node, Edge> getGraph()
    {
        return this.graph;
    }

    public DiscreteKnowledgeGraph getDiscreteGraph() {
        return this.discreteKnowledgeGraph;
    }

    public Constants.GHOST getAgent() {
        return ghost;
    }

    public PropertyChangeSupport getPropertyChangeSupport() {
        return this.support;
    }

    /**
     * Retrieves the next move of the ghost
     * @param game A Game object representing the current state of the game
     * @param l the timedue
     * @return The next move to be performed by the agent
     */
    @Override
    public Constants.MOVE getMove(Game game, long l) {
        // Update knowledge
        if (!initialized || !game.getCurrentMaze().equals(this.currentMaze)) {
            changeMaze(game);
            initialized = true;
            LOGGER.info("Hello world");
        } else {
            this.discreteKnowledgeGraph.update(Commons.updateAgentsKnowledge(this, game));
        }
        // Send current position to others
        Commons.sendToAllGhostExceptMe(game, ghost, Message.MessageType.I_AM, game.getGhostCurrentNodeIndex(ghost));

        // Move (BT in the future)
        return Constants.MOVE.NEUTRAL;
    }

    private void changeMaze(Game game) {
        // Change current maze
        Maze old = currentMaze;
        this.currentMaze = game.getCurrentMaze();

        // Clear old graph
        this.graph.clear();

        // Fill with new associations
        Commons.initAgentsKnowledge(this, game);

        // Discrete it and tell the world we changed the maze
        discreteKnowledgeGraph = new DiscreteKnowledgeGraph(this.getGraph());
        support.firePropertyChange(MAZE_CHANGED_PROP, old, currentMaze);
    }
}
