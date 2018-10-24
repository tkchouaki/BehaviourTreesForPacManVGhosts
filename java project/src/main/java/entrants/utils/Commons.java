package entrants.utils;

import entrants.utils.graph.KnowledgeGraph;
import entrants.utils.graph.Node;
import pacman.game.Game;

import java.util.HashSet;
import java.util.Set;

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
     * Updates a KnowledgeGraph from a Game object
     * @param game
     * The game to use for the update
     * @param graph
     * The graph to update
     */
    public static void updateKnowledgeGraph(Game game, KnowledgeGraph graph)
    {
        for(Node node : graph.getNodes())
        {
            Commons.updatePillsInfo(game, node);
        }
    }

    /**
     * Initializes a Knowledge Graph with the fixed topology of a game.
     * @param game
     * The game to use for the initialization
     * @return
     * The KnowledgeGraph
     */
    public static KnowledgeGraph initKnowledgeGraph(Game game)
    {
        KnowledgeGraph graph = new KnowledgeGraph();
        for(int i=0; i<game.getNumberOfNodes(); i++)
        {
            int[] neighbours = game.getNeighbouringNodes(i);
            for(int j=0; j<neighbours.length; j++)
            {
                if(neighbours[j] <= i)
                {
                    continue;
                }
                Node a = new Node(i);
                Node b = new Node(neighbours[j]);
                Commons.updatePillsInfo(game, a);
                Commons.updatePillsInfo(game, b);
                graph.addUndirectedEdge(a, b);
            }
        }
        return graph;
    }

    /**
     * This method updates the Pills Info of the current Node from a given Game object
     * @param game
     * The game object representing the state of the game from which to update the current node
     */
    public static void updatePillsInfo(Game game, Node node)
    {
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
    }
}
