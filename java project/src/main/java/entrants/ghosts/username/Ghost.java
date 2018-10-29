package entrants.ghosts.username;

import entrants.utils.Commons;
import entrants.utils.graph.AgentKnowledge;
import entrants.utils.graph.DiscreteKnowledgeGraph;
import entrants.utils.ui.KnowledgeGraphDisplayer;
import pacman.controllers.IndividualGhostController;
import pacman.game.Constants;
import pacman.game.Game;
import pacman.game.internal.Maze;

import java.nio.file.Paths;

/**
 * This class serves as a base class for the ghosts
 * Each ghost has a knowledge about its environment (The topology of the level, the other ghosts, the nodes containing pills/power pills) and the PacMan
 * The knowledge of a ghost can be visualized using GraphStream
 */
public class Ghost extends IndividualGhostController {
    private AgentKnowledge knowledge;
    private boolean display;
    private DiscreteKnowledgeGraph discreteKnowledgeGraph;
    private KnowledgeGraphDisplayer displayer;
    private Maze currentMaze;

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
        if (this.knowledge == null || !game.getCurrentMaze().equals(this.currentMaze)) {
            this.currentMaze = game.getCurrentMaze();
            this.knowledge = new AgentKnowledge();
            Commons.initAgentsKnowledge(this.knowledge, game);
            discreteKnowledgeGraph = new DiscreteKnowledgeGraph(this.knowledge.getGraph());
            if (displayer == null) {
                displayer = new KnowledgeGraphDisplayer(discreteKnowledgeGraph, "file:///" + Paths.get(".").toAbsolutePath().normalize().toString() + "/src/main/java/entrants/utils/ui/kgraph.css");
                displayer.display();
            } else {
                displayer.setGraphData(discreteKnowledgeGraph);
            }
        } else {
            this.discreteKnowledgeGraph.update(Commons.updateAgentsKnowledge(this.knowledge, game));
        }
        return Constants.MOVE.DOWN;
    }
}
