package entrants.utils;

import entrants.utils.graph.AgentKnowledge;
import entrants.utils.graph.Edge;
import entrants.utils.graph.Node;
import entrants.utils.graph.UndirectedGraph;
import pacman.game.Constants;
import pacman.game.Game;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;


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
            }
            //We check if the current node is the PacMan's position to update it.
            //We add its old & new position to the changed nodes.
            if(node.getId().equals(pacManPosition))
            {
                changedNodes.add(agentKnowledge.getPacManDescription().getPosition());
                agentKnowledge.getPacManDescription().setPosition(node);
                changedNodes.add(node);
            }
            //We check if the current node is a ghost's current position.
            //We also add its old & new position to the changed nodes.
            for(Constants.GHOST ghost : Constants.GHOST.values())
            {
                if(ghostsPositions.get(ghost).equals(node.getId()))
                {
                    if(!ghost.equals(Constants.GHOST.BLINKY))
                    {
                        System.out.println("i saw " + ghost.className + " on " + node.getId());
                    }
                    changedNodes.add(agentKnowledge.getGhostDescription(ghost).getPosition());
                    agentKnowledge.getGhostDescription(ghost).setPosition(node);
                    changedNodes.add(node);
                }
            }
        }
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
}
