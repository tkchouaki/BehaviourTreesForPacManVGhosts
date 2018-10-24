package entrants.utils;

import entrants.utils.graph.KnowledgeGraph;
import entrants.utils.graph.Node;
import pacman.game.Game;

public abstract class Commons {
    public static boolean getBooleanValue(Boolean b)
    {
        if(b==null)
        {
            return false;
        }
        return b;
    }

    public static void updateKnowledgeGraph(Game game, KnowledgeGraph graph)
    {
        int pillsNumber = 0;
        for(int i=0; i<game.getNumberOfNodes(); i++)
        {
            int[] neighbours = game.getNeighbouringNodes(i);
            for(int j=0; j<neighbours.length; j++)
            {
                Node a = new Node(i);
                Node b = new Node(neighbours[j]);
                a.updatePillsInfo(game);
                if(a.containsPill())
                {
                    pillsNumber++;
                }
                graph.addUndirectedEdge(a, b);
            }
        }
        System.out.println(graph.getNodes().size() + " " + pillsNumber);
    }
}
