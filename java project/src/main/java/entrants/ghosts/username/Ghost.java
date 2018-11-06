package entrants.ghosts.username;

import entrants.utils.Commons;
import entrants.utils.graph.AgentKnowledge;
import entrants.utils.graph.DiscreteKnowledgeGraph;
import entrants.utils.graph.Edge;
import entrants.utils.graph.Node;
import entrants.utils.graph.interfaces.IUndirectedGraph;
import entrants.utils.ui.KnowledgeGraphDisplayer;
import pacman.controllers.IndividualGhostController;
import pacman.game.Constants;
import pacman.game.Game;
import pacman.game.comms.Message;
import pacman.game.internal.Maze;

import java.beans.PropertyChangeSupport;
import java.nio.file.Paths;
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
    public static final int POSITION_SENDING_FREQUENCY = 10;
    private static final Logger LOGGER = Logger.getLogger(Ghost.class.getName());
    static {
        LOGGER.setLevel(Level.INFO);
    }

    // ATTRIBUTES
    private AgentKnowledge knowledge;
    private DiscreteKnowledgeGraph discreteKnowledgeGraph;
    private KnowledgeGraphDisplayer displayer;
    private Maze currentMaze;
    private boolean initialized;
    private final PropertyChangeSupport support;
    private boolean display;

    /**
     * Initializes the ghost, its knowledge is not displayed
     * @param ghost
     * The ghost type (Blinky, Inky, Pinky & Sue)
     */
    public Ghost(Constants.GHOST ghost) {
        this(ghost, false);
    }

    /**
     * Initialzes the ghost
     * @param ghost
     * The ghost type (Blinky, Inky, Pinky & Sue)
     * @param display
     * Set to true to display the knowledge of the ghost in a GraphStream Window
     */
    public Ghost(Constants.GHOST ghost, boolean display) {
        super(ghost);
        this.display = display;
        this.support = new PropertyChangeSupport(this);
    }

    /**
     * Retireves the next move of the ghost
     * @param game
     * A Game object representing the current state of the game
     * @param l
     * The timedue
     * @return
     * The next move to be performed by the agent
     */
    @Override
    public Constants.MOVE getMove(Game game, long l) {
        if(game.getCurrentLevelTime()%Ghost.POSITION_SENDING_FREQUENCY == 0)
        {
            Commons.sendToAllGhostExceptMe(game, this.ghost, Message.MessageType.I_AM, game.getGhostCurrentNodeIndex(this.ghost));
        }
        //if its the first iteration or the maze has changed
        if (this.knowledge == null || !game.getCurrentMaze().equals(this.currentMaze)) {
            Maze oldMaze = this.currentMaze;
            //We update the current maze
            this.currentMaze = game.getCurrentMaze();
            //We reset the knowledge
            this.knowledge = new AgentKnowledge(this.ghost);
            Commons.initAgentsKnowledge(this.knowledge, game);
            discreteKnowledgeGraph = new DiscreteKnowledgeGraph(this.knowledge.getGraph());
            this.support.firePropertyChange(Ghost.MAZE_CHANGED_PROP, oldMaze, this.currentMaze);
            if(this.display)
            {
                if (displayer == null) {
                    displayer = new KnowledgeGraphDisplayer(discreteKnowledgeGraph, "file:///" + Paths.get(".").toAbsolutePath().normalize().toString() + "/src/main/java/entrants/utils/ui/kgraph.css");
                    displayer.display();
                } else {
                    displayer.setGraphData(discreteKnowledgeGraph);
                }
            }
        } else {
            this.discreteKnowledgeGraph.update(Commons.updateAgentsKnowledge(this.knowledge, game));
        }
        return Constants.MOVE.DOWN;
    }

    public PropertyChangeSupport getPropertyChangeSupport() {
        return support;
    }

    public IUndirectedGraph<Node, Edge> getDiscreteGraph()
    {
        return this.discreteKnowledgeGraph;
    }
}
