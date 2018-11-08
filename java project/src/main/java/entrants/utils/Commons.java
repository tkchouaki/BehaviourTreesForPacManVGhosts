package entrants.utils;

import entrants.utils.graph.AgentKnowledge;
import entrants.utils.graph.Edge;
import entrants.utils.graph.Node;
import entrants.utils.graph.UndirectedGraph;
import entrants.utils.graph.interfaces.IUndirectedGraph;
import pacman.game.Constants;
import pacman.game.Game;
import pacman.game.comms.BasicMessage;
import pacman.game.comms.Message;
import pacman.game.comms.Messenger;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.*;
import java.util.logging.Logger;


public abstract class Commons {
    /**
     * The logger to use.
     */
    private static final Logger LOGGER = Logger.getLogger(Commons.class.getName());

    /**
     * Converts a Boolean value to a value of the primitive type boolean.
     * Basically True & False values are kept and null values are replaced with false
     * @param b
     * The Boolean to convert
     * @return
     * The result of the conversion
     */
    public static boolean getBooleanValue(Boolean b)
    {
        if(b==null)
        {
            return false;
        }
        return b;
    }


    /**
     * Updates an Agent's Knowledge from a Game object
     * @param game
     * The game to use for the update
     * @param agentKnowledge
     * The AgentKnowledge to update
     */
    public static Collection<Node> updateAgentsKnowledge(AgentKnowledge agentKnowledge, Game game)
    {
        //We retrieve the PacMan's & the ghosts positions (if we can see them)
        int pacManPosition = game.getPacmanCurrentNodeIndex();

        Map<Constants.GHOST, Integer> ghostsPositions = new HashMap<>();
        for(Constants.GHOST ghost : Constants.GHOST.values())
        {
            //While retrieving the positions of the ghosts
            //We also retrieve their edible time.
            //If we can observe it, we directly update it.
            //Otherwise, we just decrement the old value
            ghostsPositions.put(ghost, game.getGhostCurrentNodeIndex(ghost));
            int edibleTime = game.getGhostEdibleTime(ghost);
            if(edibleTime > -1)
            {
                agentKnowledge.getGhostDescription(ghost).setEdibleTime(edibleTime);
            }
            else
            {
                agentKnowledge.getGhostDescription(ghost).decrementEdibleTime();
            }
        }

        //We loop through the nodes of the graph to update them. the changed nodes are store into a set.
        Collection<Node> changedNodes = new HashSet<>();
        for(Node node : agentKnowledge.getGraph().getNodes())
        {
            //We update the pills information
            if(Commons.updatePillsInfo(game, node)){
                changedNodes.add(node);
                node.setLastUpdateTick(game.getCurrentLevelTime());
                if (node.getContainedPillId() == -1 && node.getContainedPowerPillId() == -1) {
                    sendToAllGhostExceptMe(
                            game, agentKnowledge.getOwner(), Message.MessageType.PILL_NOT_SEEN, node.getId()
                    );
                    LOGGER.info(agentKnowledge.getOwner() + ": no more pills at node " + node.getId());
                }
            }
            //Check if we see a node where we thought there was PacMan but now it's not.
            if(node.containsPacMan() && !node.getId().equals(pacManPosition))
            {
                //We change the last update time of the old Pac Man position.
                agentKnowledge.getPacManDescription().setPosition(null);
                node.setLastUpdateTick(game.getCurrentLevelTime());
            }
            //We check if the current node is the PacMan's position to update it.
            //We add its old & new position to the changed nodes.
            if(node.getId().equals(pacManPosition))
            {
                changedNodes.add(agentKnowledge.getPacManDescription().getPosition());
                agentKnowledge.getPacManDescription().setPosition(node);
                node.setLastUpdateTick(game.getCurrentLevelTime());
                changedNodes.add(node);
                sendToAllGhostExceptMe(game, agentKnowledge.getOwner(), Message.MessageType.PACMAN_SEEN, node.getId());
                LOGGER.info(agentKnowledge.getOwner() + ": Pacman seen at node " + node.getId());
            }
            //We check if the current node is a ghost's current position.
            //We also add its old & new position to the changed nodes.
            for(Constants.GHOST ghost : Constants.GHOST.values())
            {
                if(ghostsPositions.get(ghost).equals(node.getId()))
                {
                    changedNodes.add(agentKnowledge.getGhostDescription(ghost).getPosition());
                    agentKnowledge.getGhostDescription(ghost).setPosition(node);
                    changedNodes.add(node);
                }
            }
        }
        //We process the received messages
        changedNodes.addAll(readMessages(agentKnowledge, game));
        //We return the changed nodes.
        changedNodes.remove(null);
        return changedNodes;
    }

    /**
     * Initializes an Agent's knowledge with the fixed topology of a game.
     * @param game
     * The game to use for the initialization
     */
    public static void initAgentsKnowledge(AgentKnowledge agentKnowledge, Game game)
    {
        UndirectedGraph<Node, Edge> graph = agentKnowledge.getGraph();
        for(int i=0; i<game.getNumberOfNodes(); i++)
        {
            int[] neighbours = game.getNeighbouringNodes(i);
            for(int j=0; j<neighbours.length; j++)
            {
                if(neighbours[j] <= i)
                {
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
    }

    /**
     * This method updates the Pills Info of the current Node from a given Game object
     * @param game
     * The game object representing the state of the game from which to update the current node
     */
    public static boolean updatePillsInfo(Game game, Node node)
    {
        //We store the old values to see if anything has changed.
        int oldPillId = node.getContainedPillId();
        int oldPowerPillId = node.getContainedPowerPillId();
        //We only update if the node is observable
        if(game.isNodeObservable(node.getId()))
        {
            int pillId = game.getPillIndex(node.getId());
            if(pillId >= 0 && Commons.getBooleanValue(game.isPillStillAvailable(pillId)))
            {
                node.setContainedPillId(pillId);
            }
            else
            {
                node.setContainedPillId(-1);
            }

            int powerPillId = game.getPowerPillIndex(node.getId());
            if(powerPillId >= 0 && Commons.getBooleanValue(game.isPowerPillStillAvailable(powerPillId)))
            {
                node.setContainedPowerPillId(powerPillId);
            }
            else
            {
                node.setContainedPowerPillId(-1);
            }
            //We shouldn't forget tu update the last update tick of the node.
            node.setLastUpdateTick(game.getCurrentLevelTime());
        }
        return !(node.getContainedPillId() == oldPillId && node.getContainedPowerPillId() == oldPowerPillId);
    }

    /**
     * Read received messages and update knowledge according to received information
     * @param agentKnowledge the receiving agent's knowledge
     * @param game the running game
     * @return all nodes that have been updated
     */
    public static Collection<Node> readMessages(AgentKnowledge agentKnowledge, Game game) {
        List<Message> messages = game.getMessenger().getMessages(agentKnowledge.getOwner());
        List<Node> toUpdate = new ArrayList<>();
        IUndirectedGraph<Node, Edge> graph = agentKnowledge.getGraph();

        for (Message message : messages) {
            Message.MessageType type = message.getType();
            Node node = graph.getNodeByID(message.getData());
            if (node == null) continue;

            // A pill just disappeared
            if (type.equals(Message.MessageType.PILL_NOT_SEEN)) {
                if(node.getLastUpdateTick() < message.getTick())
                {
                    node.setContainedPillId(-1);
                    node.setContainedPowerPillId(-1);
                    node.setLastUpdateTick(message.getTick());
                    LOGGER.info(agentKnowledge.getOwner() + " received no more pills for node " + node.getId());
                }
            }

            // Teammates positions have changed
            else if (type.equals(Message.MessageType.I_AM)) {
                Node old = agentKnowledge.getGhostDescription(message.getSender()).getPosition();
                if (old == null || (!old.equals(node) && old.getLastUpdateTick() < message.getTick()))
                {
                    // Remove old position
                    if (old != null)
                    {
                        // old.removeGhost(message.getSender());
                        old.setLastUpdateTick(message.getTick());
                        toUpdate.add(old);
                    }

                    // Set new one
                    agentKnowledge.getGhostDescription(message.getSender()).setPosition(node);
                    node.setLastUpdateTick(message.getTick());
                    LOGGER.info(agentKnowledge.getOwner() + " received " + message.getSender() + "'s position (" + node.getId() +")");
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
                    LOGGER.info(agentKnowledge.getOwner() + " received PACMAN's position (" + node.getId() +") from " + message.getSender());
                }
            }
            toUpdate.add(node);
        }
        return toUpdate;
    }

    /**
     * Utility method design to send messages to other ghosts
     * @param game the current game
     * @param me the ghost sending the message
     * @param type message's type
     * @param data message's data
     */
    public static void sendToAllGhostExceptMe(Game game, Constants.GHOST me, Message.MessageType type, int data) {
        Messenger messenger = game.getMessenger();

        for (Constants.GHOST ghost : Constants.GHOST.values()) {
            if (!ghost.equals(me)) {
                messenger.addMessage(new BasicMessage(
                        me,
                        ghost,
                        type,
                        data,
                        game.getCurrentLevelTime()
                ));
            }
        }
    }
}
