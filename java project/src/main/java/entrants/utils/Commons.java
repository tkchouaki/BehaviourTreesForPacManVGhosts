package entrants.utils;

import entrants.utils.graph.KnowledgeGraph;
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

    public void updateKnowledgeGraph(Game game, KnowledgeGraph graph)
    {
        int[] pillIndices = game.getPillIndices();
        int[] powerPillIndices = game.getPowerPillIndices();
    }
}
