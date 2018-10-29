package entrants.ghosts.username;

import entrants.utils.Commons;
import entrants.utils.graph.AgentKnowledge;
import entrants.utils.graph.DiscreteKnowledgeGraph;
import entrants.utils.ui.KnowledgeGraphDisplayer;
import pacman.controllers.IndividualGhostController;
import pacman.controllers.MASController;
import pacman.game.Constants;
import pacman.game.Game;
import pacman.game.comms.Message;

import java.awt.*;
import java.nio.file.Paths;

/**
 * Created by Piers on 11/11/2015.
 */
public class Inky extends IndividualGhostController {
    private AgentKnowledge knowledge;
    private DiscreteKnowledgeGraph discreteKnowledgeGraph;

    public Inky() {
        super(Constants.GHOST.INKY);
    }

    @Override
    public Constants.MOVE getMove(Game game, long timeDue) {
        /*if(this.knowledge == null)
        {
            KnowledgeGraphDisplayer displayer;
            this.knowledge = new AgentKnowledge(ghost);
            Commons.initAgentsKnowledge(this.knowledge, game);
            discreteKnowledgeGraph = new DiscreteKnowledgeGraph(this.knowledge.getGraph());
            displayer = new KnowledgeGraphDisplayer(discreteKnowledgeGraph, "file:///" + Paths.get(".").toAbsolutePath().normalize().toString() + "/src/main/java/entrants/utils/ui/kgraph.css");
            displayer.display();
        }
        else
        {
            this.discreteKnowledgeGraph.update(Commons.updateAgentsKnowledge(this.knowledge, game));
        }*/
        return Constants.MOVE.UP;
    }
}
