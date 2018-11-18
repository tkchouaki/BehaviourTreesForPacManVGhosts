package entrants.ghosts.username;

import entrants.BT.Library.BTLibrary;
import entrants.utils.Commons;
import entrants.utils.graph.*;
import entrants.utils.graph.interfaces.IUndirectedGraph;
import jbt.execution.core.*;
import jbt.model.core.ModelTask;
import pacman.controllers.IndividualGhostController;
import pacman.game.Constants;
import pacman.game.Game;
import pacman.game.comms.BasicMessage;
import pacman.game.comms.Message;
import pacman.game.comms.Messenger;
import pacman.game.internal.Maze;

import java.beans.PropertyChangeSupport;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * This class serves as a base class for the ghosts
 * Each ghost has a knowledge about its environment (The topology of the level, the other ghosts, the nodes containing pills/power pills) and the PacMan
 * The knowledge of a ghost can be visualized using GraphStream.
 * At each step, the ghost runs its Behaviour Tree to decide which action to perform.
 */
public class Ghost extends IndividualGhostController {

    // STATICS
    public static final String MAZE_CHANGED_PROP = "maze_changed";
    public static final String SAVE_CURRENT_STATE_PROP = "save_current_state";

    private static final int POSITION_SENDING_FREQUENCY = 10;
    private static final Logger LOGGER = Logger.getLogger(Ghost.class.getName());
    private static final String USED_BT = "StarterGhost_V2";

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

    // Behaviour Tree related
    private IContext context;
    private ModelTask bt;

    /**
     * Initializes the ghost, its knowledge is not displayed
     *
     * @param ghost The ghost type (Blinky, Inky, Pinky & Sue)
     */
    public Ghost(Constants.GHOST ghost) {
        this(ghost, false);
    }

    /**
     * Initialzes the ghost
     *
     * @param ghost   The ghost type (Blinky, Inky, Pinky & Sue)
     * @param display Set to true to display the knowledge of the ghost in a GraphStream Window
     */
    public Ghost(Constants.GHOST ghost, boolean display) {
        super(ghost);
        this.display = display;
        this.support = new PropertyChangeSupport(this);
        this.initJBT();
    }

    /**
     * Initializes the Behaviour Tree indicated in the {@link Ghost#USED_BT} constant.
     */
    private void initJBT() {
        initJBT(USED_BT);
    }

    /**
     * Initializes the Behaviour Tree
     *
     * @param behaviourTreeName The name of the Behaviour Tree to use.
     */
    private void initJBT(String behaviourTreeName) {
        IBTLibrary btLibrary = new BTLibrary();
        bt = btLibrary.getBT(behaviourTreeName);
        context = ContextFactory.createContext(btLibrary);
    }

    /**
     * Updates the ghosts knowledge.
     * If a decision is required, it runs the Behaviour Tree & goes towards/away from the returned node.
     *
     * @param game A Game object representing the current state of the game
     * @param l    The timedue
     * @return The next move to be performed by the agent
     */
    @Override
    public Constants.MOVE getMove(Game game, long l) {
        //Just to check that we don't to over the time due.
        float begin = System.currentTimeMillis();

        //We send the ghost's position to other ghosts regularly
        if (game.getCurrentLevelTime() % Ghost.POSITION_SENDING_FREQUENCY == 0) {
            sendToAllGhostExceptMe(game, Message.MessageType.I_AM, game.getGhostCurrentNodeIndex(this.ghost));
        }

        //if its the first iteration or the maze has changed
        //we reset the ghost's knowledge
        if (this.knowledge == null || !game.getCurrentMaze().equals(this.currentMaze)) {
            Maze oldMaze = this.currentMaze;
            //We update the current maze
            this.currentMaze = game.getCurrentMaze();
            //We reset the knowledge
            initGhostsKnowledge(game);
            discreteKnowledgeGraph = new DiscreteKnowledgeGraph(this.knowledge.getGraph());
            this.support.firePropertyChange(Ghost.MAZE_CHANGED_PROP, oldMaze, this.currentMaze);
        } else {
            this.discreteKnowledgeGraph.update(updateGhostKnowledge(game));
        }

        Node position = getKnowledge().getKnowledgeAboutMySelf().getPosition();
        //if the position requires an action.
        if (game.doesGhostRequireAction(this.getGhostEnumValue())) {
            //We initialize the Behaviour Tree's context with the current game state & the ghost.
            context.setVariable("GAME", game);
            context.setVariable("GHOST", this);

            IBTExecutor btExecutor = BTExecutorFactory.createBTExecutor(bt, context);

            //We run the Behaviour Tree until it's finished
            do {
                btExecutor.tick();
            } while (btExecutor.getStatus() == ExecutionTask.Status.RUNNING);

            //We set the previous target to be no danger & no goal.
            Set<Node> toUpdate = new HashSet<>();
            if (currentTarget != null) {
                currentTarget.setDanger(false);
                currentTarget.setGoal(false);
                toUpdate.add(currentTarget);

            }

            //We retrieve the node selected by the Behaviour Tree
            //And a boolean indicating weather the ghost should move towards it or away from it.
            currentTarget = (Node) context.getVariable("SELECTED_NODE");
            toUpdate.add(currentTarget);
            Boolean closing = (Boolean) context.getVariable("CLOSING");
            String message;

            if (closing) {
                message = "going to " + currentTarget;
                currentTarget.setGoal(true);
            } else {
                message = "escaping " + currentTarget;
                currentTarget.setDanger(true);
            }

            //We retrieve the last move made by the ghost
            Constants.MOVE lastMove = game.getGhostLastMoveMade(this.getGhostEnumValue());
            //We set the next move according to the results of the behaviour tree
            Constants.MOVE nextMove;
            nextMove = computeNextMove(position, currentTarget, closing, game, lastMove);
            //We make sure to update the discrete knowledge graph to remove the old target & add the new one.
            this.discreteKnowledgeGraph.update(toUpdate);
            //If display is set to true, we want to save the current state of the graphstream display in an image
            //So we fire the appropriate property change event.
            if (display) {
                support.firePropertyChange(SAVE_CURRENT_STATE_PROP, null, game.getTotalTime());
            }

            //We print things just for blinky to for debugging purpose
            if (this.getGhostEnumValue().equals(Constants.GHOST.BLINKY)) {
                message = "blinky at " + position.getId() + " " + message + " at " + game.getTotalTime();
                if (nextMove != null) {
                    message += " making " + nextMove.toString() + " among : ";
                    boolean found = false;
                    for (Constants.MOVE move : game.getPossibleMoves(position.getId(), lastMove)) {
                        message += move.toString() + ", ";
                        if (move.equals(nextMove)) {
                            found = true;
                        }
                    }
                    message += " after having made " + lastMove.toString();
                    if (knowledge.getPacManDescription().getPosition() != null) {
                        message += " | pacman is at " + knowledge.getPacManDescription().getPosition();
                    }
                    if (!found) {
                        nextMove = computeNextMove(position, currentTarget, closing, game, lastMove);
                    }
                } else {
                    message = "blinky has no move to do";
                }
                LOGGER.info(message);
            }
            if (System.currentTimeMillis() - begin >= 10) {
                LOGGER.warning("problem," + this.getGhostEnumValue().toString() + " went over time limit");
            }
            return nextMove;
        }
        return null;
    }

    /**
     * @return The property change support
     */
    public PropertyChangeSupport getPropertyChangeSupport() {
        return support;
    }

    /**
     * @return The discrete graph of the current ghost
     */
    public IUndirectedGraph<Node, Edge> getDiscreteGraph() {
        return this.discreteKnowledgeGraph;
    }

    /**
     * @return The knowledge of the current ghost
     */
    public AgentKnowledge getKnowledge() {
        return this.knowledge;
    }

    /**
     * @return The {@link Constants.GHOST} value representing the current ghost
     */
    public Constants.GHOST getGhostEnumValue() {
        return this.ghost;
    }

    /**
     * Computes the next move for the ghost that will allow him to apply the result of the behaviour tree
     * @param position current position of the ghost
     * @param target the target of the move
     * @param closing a boolean indicating if the ghost should go towards the target of away from it
     * @param game the state of the game
     * @param lastMove the last move made by the ghost
     * @return
     */
    private static Constants.MOVE computeNextMove(Node position, Node target, boolean closing, Game game, Constants.MOVE lastMove) {
        Constants.MOVE move;
        if (closing) {
            try {
                move = game.getNextMoveTowardsTarget(position.getId(), target.getId(), lastMove, Constants.DM.MANHATTAN);
            } catch (Exception e) {
                move = game.getNextMoveTowardsTarget(position.getId(), target.getId(), Constants.DM.MANHATTAN);
            }
        } else {
            try {
                move = game.getNextMoveAwayFromTarget(position.getId(), target.getId(), lastMove, Constants.DM.MANHATTAN);
            } catch (Exception e) {
                move = game.getNextMoveAwayFromTarget(position.getId(), target.getId(), Constants.DM.MANHATTAN);
            }
        }
        return move;
    }


    /**
     * Initializes a Ghosts's knowledge with the fixed topology of a game.
     * Also sets the position of Pacman to its intial one
     *
     * @param game The game state to use for the initialization
     */
    private void initGhostsKnowledge(Game game) {
        AgentKnowledge agentKnowledge = new AgentKnowledge(this.getGhostEnumValue());
        UndirectedGraph<Node, Edge> graph = agentKnowledge.getGraph();
        //We add the nodes one by one to the graph
        for (int i = 0; i < game.getNumberOfNodes(); i++) {
            //Foreach node, we retrieve the neighbours & add the appropriate edge
            int[] neighbours = game.getNeighbouringNodes(i);
            for (int j = 0; j < neighbours.length; j++) {
                //We make sure we don't process the same edge twice.
                if (neighbours[j] <= i) {
                    continue;
                }
                Node a = new Node(i, game.getNodeXCood(i), game.getNodeYCood(i), game.isJunction(i));
                Node b = new Node(neighbours[j], game.getNodeXCood(neighbours[j]), game.getNodeYCood(neighbours[j]), game.isJunction(neighbours[j]));
                a.setContainedPowerPillId(game.getPowerPillIndex(a.getId()));
                a.setContainedPillId(game.getPillIndex(a.getId()));
                b.setContainedPowerPillId(game.getPowerPillIndex(b.getId()));
                b.setContainedPillId(game.getPillIndex(b.getId()));
                Edge edge = new Edge(a, b);
                graph.addEdge(edge);
            }
        }
        //We initialize Pacman's position.
        agentKnowledge.getPacManDescription().setPosition(graph.getNodeByID(game.getPacManInitialNodeIndex()));
        this.knowledge = agentKnowledge;
    }

    /**
     * Updates an Agent's Knowledge from a Game object
     *
     * @param game The game to use for the update
     * @return A collection containing the nodes that might have changed
     */
    private Collection<Node> updateGhostKnowledge(Game game) {
        AgentKnowledge agentKnowledge = this.knowledge;
        //If PacMan was eaten, we reset its position to its spawning one.
        if (game.wasPacManEaten()) {
            this.knowledge.getPacManDescription().setPosition(this.knowledge.getGraph().getNodeByID(game.getPacManInitialNodeIndex()));
        }
        //We retrieve the PacMan's & the ghosts positions (if we can see them)
        int pacManPosition = game.getPacmanCurrentNodeIndex();
        Map<Constants.GHOST, Integer> ghostsPositions = new HashMap<>();
        //We loop through the nodes of the graph to update them. the changed nodes are store into a set.
        Collection<Node> changedNodes = new HashSet<>();
        for (Constants.GHOST ghost : Constants.GHOST.values()) {
            //While retrieving the positions of the ghosts
            //We also retrieve their edible time.
            //If we can observe it, we directly update it.
            //Otherwise, we just decrement the old value
            ghostsPositions.put(ghost, game.getGhostCurrentNodeIndex(ghost));
            int edibleTime = game.getGhostEdibleTime(ghost);
            if (edibleTime > -1) {
                if (ghost.equals(agentKnowledge.getOwner()) && edibleTime > 0) {
                    if (agentKnowledge.getGhostDescription(ghost).getEdibleTime() <= 0 && pacManPosition < 0) {
                        agentKnowledge.getPacManDescription().setPosition(null);
                    }
                }
                agentKnowledge.getGhostDescription(ghost).setEdibleTime(edibleTime);
            } else {
                agentKnowledge.getGhostDescription(ghost).decrementEdibleTime();
            }
        }

        for (Node node : agentKnowledge.getGraph().getNodes()) {
            if (!game.isNodeObservable(node.getId())) {
                continue;
            }
            changedNodes.add(node);
            //We update the pills information
            if (updatePillsInfo(game, node)) {
                node.setLastUpdateTick(game.getCurrentLevelTime());
                if (node.getContainedPillId() == -1 && node.getContainedPowerPillId() == -1) {
                    sendToAllGhostExceptMe(
                            game, Message.MessageType.PILL_NOT_SEEN, node.getId()
                    );
                    LOGGER.info(agentKnowledge.getOwner() + ": no more pills at node " + node.getId());
                }
            }

            //Check if we see a node where we thought there was PacMan but now it's not.
            if (node.containsPacMan() && !node.getId().equals(pacManPosition)) {
                //We change the last update time of the old Pac Man position.
                changedNodes.add(agentKnowledge.getPacManDescription().getPosition());
                agentKnowledge.getPacManDescription().setPosition(null);
                if (pacManPosition == -1 && game.getCurrentLevelTime() - node.getLastUpdateTick() <= 10 && node.isDecisionNode()) {
                    try {
                        Collection<Node> neighbours = agentKnowledge.getGraph().getNeighboursAsNodesOf(node);
                        List<Node> invisibleNodes = new ArrayList<>(neighbours);
                        for (Node neighbour : neighbours) {
                            if (game.isNodeObservable(neighbour.getId())) {
                                invisibleNodes.remove(neighbour);
                            }
                        }
                        if (invisibleNodes.size() == 1) {
                            agentKnowledge.getPacManDescription().setPosition(invisibleNodes.get(0));
                        }
                    } catch (IUndirectedGraph.NodeNotFoundException e) {
                        e.printStackTrace();
                    }
                }
                node.setLastUpdateTick(game.getCurrentLevelTime());
            }
            //We check if the current node is the PacMan's position to update it.
            //We add its old & new position to the changed nodes.
            if (node.getId().equals(pacManPosition)) {
                changedNodes.add(agentKnowledge.getPacManDescription().getPosition());
                agentKnowledge.getPacManDescription().setPosition(node);
                node.setLastUpdateTick(game.getCurrentLevelTime());
                changedNodes.add(node);
                sendToAllGhostExceptMe(game, Message.MessageType.PACMAN_SEEN, node.getId());
                LOGGER.info(agentKnowledge.getOwner() + ": Pacman seen at node " + node.getId());
            }
            //We check if the current node is a ghost's current position.
            //We also add its old & new position to the changed nodes.
            for (Constants.GHOST ghost : Constants.GHOST.values()) {
                if (ghostsPositions.get(ghost).equals(node.getId())) {
                    changedNodes.add(agentKnowledge.getGhostDescription(ghost).getPosition());
                    agentKnowledge.getGhostDescription(ghost).setPosition(node);
                    changedNodes.add(node);
                }
            }
        }
        //We process the received messages
        changedNodes.addAll(readMessages(game));
        //We return the changed nodes.
        changedNodes.remove(null);
        return changedNodes;
    }


    /**
     * This method updates the Pills Info of the current Node from a given Game object
     *
     * @param game The game object representing the state of the game from which to update the current node
     */
    private static boolean updatePillsInfo(Game game, Node node) {
        //We store the old values to see if anything has changed.
        int oldPillId = node.getContainedPillId();
        int oldPowerPillId = node.getContainedPowerPillId();
        //We only update if the node is observable
        if (game.isNodeObservable(node.getId())) {
            int pillId = game.getPillIndex(node.getId());
            if (pillId >= 0 && Commons.getBooleanValue(game.isPillStillAvailable(pillId))) {
                node.setContainedPillId(pillId);
            } else {
                node.setContainedPillId(-1);
            }

            int powerPillId = game.getPowerPillIndex(node.getId());
            if (powerPillId >= 0 && Commons.getBooleanValue(game.isPowerPillStillAvailable(powerPillId))) {
                node.setContainedPowerPillId(powerPillId);
            } else {
                node.setContainedPowerPillId(-1);
            }
            //We shouldn't forget tu update the last update tick of the node.
            node.setLastUpdateTick(game.getCurrentLevelTime());
        }
        return !(node.getContainedPillId() == oldPillId && node.getContainedPowerPillId() == oldPowerPillId);
    }

    /**
     * Read received messages and update knowledge according to received information
     *
     * @param game the running game
     * @return all nodes that have been updated
     */
    private Collection<Node> readMessages(Game game) {
        AgentKnowledge agentKnowledge = this.knowledge;
        List<Message> messages = game.getMessenger().getMessages(agentKnowledge.getOwner());
        List<Node> toUpdate = new ArrayList<>();
        IUndirectedGraph<Node, Edge> graph = agentKnowledge.getGraph();

        for (Message message : messages) {
            Message.MessageType type = message.getType();
            Node node = graph.getNodeByID(message.getData());
            if (node == null) continue;

            // A pill just disappeared
            if (type.equals(Message.MessageType.PILL_NOT_SEEN)) {
                if (node.getLastUpdateTick() < message.getTick()) {
                    node.setContainedPillId(-1);
                    node.setContainedPowerPillId(-1);
                    node.setLastUpdateTick(message.getTick());
                    LOGGER.info(agentKnowledge.getOwner() + " received no more pills for node " + node.getId());
                }
            }

            // Teammates positions have changed
            else if (type.equals(Message.MessageType.I_AM)) {
                Node old = agentKnowledge.getGhostDescription(message.getSender()).getPosition();
                if (old == null || (!old.equals(node) && old.getLastUpdateTick() < message.getTick())) {
                    // Remove old position
                    if (old != null) {
                        // old.removeGhostDescription(message.getSender());
                        old.setLastUpdateTick(message.getTick());
                        toUpdate.add(old);
                    }

                    // Set new one
                    agentKnowledge.getGhostDescription(message.getSender()).setPosition(node);
                    node.setLastUpdateTick(message.getTick());
                    LOGGER.info(agentKnowledge.getOwner() + " received " + message.getSender() + "'s position (" + node.getId() + ")");
                }
            }

            // Pacman has been seen by one of my teammates
            else if (type.equals(Message.MessageType.PACMAN_SEEN)) {
                Node old = agentKnowledge.getPacManDescription().getPosition();
                if (old == null || (!old.equals(node) && old.getLastUpdateTick() < message.getTick())) {
                    if (old != null) {
                        old.setLastUpdateTick(message.getTick());
                        toUpdate.add(old);
                    }

                    // Set the new position
                    agentKnowledge.getPacManDescription().setPosition(node);
                    node.setLastUpdateTick(message.getTick());
                    LOGGER.info(agentKnowledge.getOwner() + " received PACMAN's position (" + node.getId() + ") from " + message.getSender());
                }
            }
            toUpdate.add(node);
        }
        return toUpdate;
    }

    /**
     * Utility method design to send messages to other ghosts
     *
     * @param game the current game
     * @param type message's type
     * @param data message's data
     */
    private void sendToAllGhostExceptMe(Game game, Message.MessageType type, int data) {
        Messenger messenger = game.getMessenger();

        for (Constants.GHOST ghost : Constants.GHOST.values()) {
            if (!ghost.equals(this.getGhostEnumValue())) {
                messenger.addMessage(new BasicMessage(
                        this.getGhostEnumValue(),
                        ghost,
                        type,
                        data,
                        game.getCurrentLevelTime()
                ));
            }
        }
    }
}
