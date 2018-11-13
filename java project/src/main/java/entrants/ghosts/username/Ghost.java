package entrants.ghosts.username;

import entrants.BT.Library.BTLibrary;
import entrants.utils.Commons;
import entrants.utils.graph.AgentKnowledge;
import entrants.utils.graph.DiscreteKnowledgeGraph;
import entrants.utils.graph.Edge;
import entrants.utils.graph.Node;
import entrants.utils.graph.interfaces.IUndirectedGraph;
import jbt.execution.core.*;
import jbt.model.core.ModelTask;
import pacman.controllers.IndividualGhostController;
import pacman.game.Constants;
import pacman.game.Constants.MOVE;
import pacman.game.Game;
import pacman.game.comms.Message;
import pacman.game.internal.Maze;
import scala.collection.immutable.Stream;

import java.beans.PropertyChangeSupport;
import java.sql.Timestamp;
import java.time.LocalTime;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * This class serves as a base class for the ghosts
 * Each ghost has a knowledge about its environment (The topology of the level, the other ghosts, the nodes containing pills/power pills) and the PacMan
 * The knowledge of a ghost can be visualized using GraphStream
 */
public class Ghost extends IndividualGhostController {

    // BT
    private IContext        context;
    private ModelTask       bt;

    // STATICS
    public static final String MAZE_CHANGED_PROP = "maze_changed";
    public static final String SAVE_CURRENT_STATE_PROP = "save_current_state";
    public static final int POSITION_SENDING_FREQUENCY = 10;
    private static final Logger LOGGER = Logger.getLogger(Ghost.class.getName());
    static {
        LOGGER.setLevel(Level.INFO);
    }

    // ATTRIBUTES
    private AgentKnowledge knowledge;
    private DiscreteKnowledgeGraph discreteKnowledgeGraph;
    private Maze currentMaze;
    private final PropertyChangeSupport support;
    private boolean display;
    private Node currentTarget;
    private boolean closing;

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

    public void initJBT(){
       initJBT("StarterGhost");
    }

    public void initJBT(String behaviourTreeName){
        IBTLibrary btLibrary = new BTLibrary();
        bt = btLibrary.getBT(behaviourTreeName);
        context = ContextFactory.createContext(btLibrary);
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
        float begin = System.currentTimeMillis();
        //We send the ghost's position to other ghosts regularly
        if(game.getCurrentLevelTime()%Ghost.POSITION_SENDING_FREQUENCY == 0)
        {
            Commons.sendToAllGhostExceptMe(game, this.ghost, Message.MessageType.I_AM, game.getGhostCurrentNodeIndex(this.ghost));
        }
        //If PacMan was eaten, we reset its position to its spawning one.
        if(game.wasPacManEaten())
        {
            this.knowledge.getPacManDescription().setPosition(this.knowledge.getGraph().getNodeByID(game.getPacManInitialNodeIndex()));
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
        } else {
            this.discreteKnowledgeGraph.update(Commons.updateAgentsKnowledge(this.knowledge, game));
        }

        Node position = getKnowledge().getKnowledgeAboutMySelf().getPosition();
        //if the position requires an action.
        if (position != null && position.isDecisionNode() && game.doesGhostRequireAction(this.getGhostEnumValue())) {
            // ========== Behaviour Tree's LOOP =========
            //context.setVariable("MOVE", MOVE.NEUTRAL);
            context.setVariable("GAME", game);
            context.setVariable("GHOST", this);

            IBTExecutor btExecutor = BTExecutorFactory.createBTExecutor(bt, context);

            do {
                btExecutor.tick();
            } while (btExecutor.getStatus() == ExecutionTask.Status.RUNNING);
            Set<Node> toUpdate = new HashSet<>();
            if(currentTarget != null)
            {
                currentTarget.setDanger(false);
                currentTarget.setGoal(false);
                toUpdate.add(currentTarget);

            }
            currentTarget = (Node) context.getVariable("SELECTED_NODE");
            toUpdate.add(currentTarget);
            this.closing = (Boolean) context.getVariable("CLOSING");
            String message = "";
            Constants.MOVE lastMove = game.getGhostLastMoveMade(this.getGhostEnumValue());
            Constants.MOVE nextMove;
            if(closing)
            {
                message = "going to " + currentTarget;
                currentTarget.setGoal(true);
                nextMove = computeNextMove(position, currentTarget, closing, game, lastMove);
            }
            else
            {
                message = "escaping " + currentTarget;
                currentTarget.setDanger(true);
                nextMove = computeNextMove(position, currentTarget, closing, game, lastMove);
            }
            this.discreteKnowledgeGraph.update(toUpdate);
            if (display) {
                support.firePropertyChange(SAVE_CURRENT_STATE_PROP, null, game.getTotalTime());
            }
            if(this.getGhostEnumValue().equals(Constants.GHOST.BLINKY))
            {
                message = "blinky " + message + " at " + game.getTotalTime();
                if(nextMove != null)
                {
                    message += " making " + nextMove.toString() + " among : ";
                    boolean found = false;
                    for(Constants.MOVE move : game.getPossibleMoves(position.getId(), lastMove))
                    {
                        message += move.toString() + ", ";
                        if(move.equals(nextMove))
                        {
                           found = true;
                        }
                    }
                    message += " after having made " + lastMove.toString();
                    if(!found)
                    {
                        nextMove = computeNextMove(position, currentTarget, closing, game, lastMove);
                    }
                }
                else
                {
                    message = "blinky has no move to do";
                }
                //System.out.println(message);
            }
            if(System.currentTimeMillis() - begin >= 10)
            {
                System.out.println("problem");
            }
            return nextMove;
        }
        return null;
    }

    public PropertyChangeSupport getPropertyChangeSupport() {
        return support;
    }

    public IUndirectedGraph<Node, Edge> getDiscreteGraph()
    {
        return this.discreteKnowledgeGraph;
    }

    public AgentKnowledge getKnowledge()
    {
        return this.knowledge;
    }

    public Constants.GHOST getGhostEnumValue()
    {
        return this.ghost;
    }

    private Constants.MOVE computeNextMove(Node position, Node target, boolean closing, Game game, Constants.MOVE lastMove)
    {
        Constants.MOVE move;
        if(closing)
        {
            try
            {
                move = game.getNextMoveTowardsTarget(position.getId(), currentTarget.getId(), lastMove, Constants.DM.MANHATTAN);
            }catch (Exception e)
            {
                move = game.getNextMoveTowardsTarget(position.getId(), currentTarget.getId(), Constants.DM.MANHATTAN);
            }
        }
        else
        {
            try
            {
                move = game.getNextMoveAwayFromTarget(position.getId(), currentTarget.getId(), lastMove, Constants.DM.MANHATTAN);
            }catch (Exception e)
            {
                move = game.getNextMoveAwayFromTarget(position.getId(), currentTarget.getId(), Constants.DM.MANHATTAN);
            }
        }
        return move;
    }
}
