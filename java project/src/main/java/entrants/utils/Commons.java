package entrants.utils;

import entrants.ghosts.username.Ghost;
import entrants.utils.graph.Edge;
import entrants.utils.graph.Node;
import entrants.utils.graph.UndirectedGraph;
import entrants.utils.graph.interfaces.IUndirectedGraph;
import pacman.game.Constants;
import pacman.game.Game;
import pacman.game.comms.BasicMessage;
import pacman.game.comms.Message;
import pacman.game.comms.Messenger;

import java.util.*;
import java.util.logging.Logger;


public abstract class Commons {
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

    private static final Logger LOGGER = Logger.getLogger(Commons.class.getName());

    /**
     * Updates an Agent's Knowledge from a Game object
     * @param game
     * The game to use for the update
     * @param agent
     * The Agent to update
     */
    public static Collection<Node> updateAgentsKnowledge(Ghost agent, Game game)
    {
        Collection<Node> changedNodes = new HashSet<>();

        for(Node node : agent.getDiscreteGraph().getNodes())
        {
            if(Commons.updatePillsInfo(game, node)){
                changedNodes.add(node);
                if (game.isNodeObservable(node.getId()) && node.getContainedPillId() == -1 && node.getContainedPowerPillId() == -1) {
                    sendToAllGhostExceptMe(
                            game, agent.getAgent(), Message.MessageType.PILL_NOT_SEEN, node.getId()
                    );
                    LOGGER.info(agent.getAgent() + ": no more pills at node " + node.getId());
                }
            }
        }
        changedNodes.addAll(readMessages(agent, game));
        return changedNodes;
    }

    /**
     * Initializes an Agent's knowledge with the fixed topology of a game.
     * @param game
     * The game to use for the initialization
     */
    public static void initAgentsKnowledge(Ghost agent, Game game)
    {
        UndirectedGraph<Node, Edge> graph = agent.getGraph();
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
                Commons.updatePillsInfo(game, a);
                Commons.updatePillsInfo(game, b);
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
        int oldPillId = node.getContainedPillId();
        int oldPowerPillId = node.getContainedPowerPillId();
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
            node.setLastUpdateTick(game.getCurrentLevelTime());
        }
        return !(node.getContainedPillId() == oldPillId && node.getContainedPowerPillId() == oldPowerPillId);
    }

    /**
     * Read received messages and update knowledge according to received informmation
     * @param agent the agent receiving
     * @param game the running game
     * @return all nodes that have been updated
     */
    public static Collection<Node> readMessages(Ghost agent, Game game) {
        List<Message> messages = game.getMessenger().getMessages(agent.getAgent());
        List<Node> toUpdate = new ArrayList<>();
        IUndirectedGraph<Node, Edge> graph = agent.getDiscreteGraph();

        for (Message message : messages) {
            Message.MessageType type = message.getType();
            Node node = graph.getNodeByID(message.getData());
            if (node == null) continue;

            // A pill just disappeared
            if (type.equals(Message.MessageType.PILL_NOT_SEEN)) {
                node.setContainedPillId(-1);
                node.setContainedPowerPillId(-1);
                node.setLastUpdateTick(game.getCurrentLevelTime());
                LOGGER.info(agent.getAgent() + " received no more pills for node " + node.getId());
            }

            // Teammates positions have changed
            if (type.equals(Message.MessageType.I_AM)) {
                Node old = Node.getNodeContainingGhost(agent.getDiscreteGraph().getNodes(), message.getSender());
                System.out.println(old);
                if (old == null || !old.getId().equals(node.getId())) {
                    // Remove old position
                    if (old != null) {
                        Set<Constants.GHOST> ghosts = old.getContainedGhosts();
                        ghosts.remove(message.getSender());
                        old.setContainedGhosts(ghosts);
                        old.setLastUpdateTick(game.getCurrentLevelTime());
                    }
                    // Set new one
                    Set<Constants.GHOST> ghosts = node.getContainedGhosts();
                    ghosts.add(message.getSender());
                    node.setContainedGhosts(ghosts);
                    node.setLastUpdateTick(game.getCurrentLevelTime());
                    LOGGER.info(agent.getAgent() + " received " + message.getSender() + "'s position (" + node.getId() +")");
                }
            }
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
