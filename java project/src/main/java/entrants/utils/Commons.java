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

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;


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

    /**
     * Updates an Agent's Knowledge from a Game object
     * @param game
     * The game to use for the update
     * @param agentKnowledge
     * The AgentKnowledge to update
     */
    public static Collection<Node> updateAgentsKnowledge(AgentKnowledge agentKnowledge, Game game)
    {
        Collection<Node> changedNodes = new HashSet<>();
        Messenger messenger = game.getMessenger();

        for(Node node : agentKnowledge.getGraph().getNodes())
        {
            if(Commons.updatePillsInfo(game, node)){
                changedNodes.add(node);
                sendToAllGhostExceptMe(
                        messenger, agentKnowledge.getAgent(), Message.MessageType.PILL_NOT_SEEN, node.getId()
                );
            }
        }
        changedNodes.addAll(readMessages(agentKnowledge, game));
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
        }
        return !(node.getContainedPillId() == oldPillId && node.getContainedPowerPillId() == oldPowerPillId);
    }

    public static Collection<Node> readMessages(AgentKnowledge agentKnowledge, Game game) {
        List<Message> messages = game.getMessenger().getMessages(agentKnowledge.getAgent());
        List<Node> toUpdate = new ArrayList<>();
        IUndirectedGraph<Node, Edge> graph = agentKnowledge.getGraph();

        for (Message message : messages) {
            Message.MessageType type = message.getType();
            if (type.equals(Message.MessageType.PILL_NOT_SEEN)) {
                Node node = graph.getNodeByID(message.getData());
                if (node != null) {
                    node.setContainedPillId(-1);
                    node.setContainedPowerPillId(-1);
                }
            }
        }

        return toUpdate;
    }

    public static void sendToAllGhostExceptMe(Messenger messenger, Constants.GHOST me, Message.MessageType type, int data) {
        for (Constants.GHOST ghost : Constants.GHOST.values()) {
            if (!ghost.equals(me)) {
                System.out.println("Received NO MORE PILL");
                messenger.addMessage(new BasicMessage(
                        me,
                        ghost,
                        type,
                        data,
                        0 // should be the current tick
                ));
            }
        }
    }
}
