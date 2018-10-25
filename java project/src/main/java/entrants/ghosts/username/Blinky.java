package entrants.ghosts.username;

import entrants.utils.Commons;
import entrants.utils.graph.AgentKnowledge;
import entrants.utils.ui.KnowledgeGraphDisplayer;
import pacman.controllers.IndividualGhostController;
import pacman.game.Constants;
import pacman.game.Game;

import java.nio.file.Paths;

/**
 * Created by Piers on 11/11/2015.
 */
public class Blinky extends IndividualGhostController {
    private AgentKnowledge knowledge;

    public Blinky() {
        super(Constants.GHOST.BLINKY);
        this.knowledge = null;
    }

    @Override
    public Constants.MOVE getMove(Game game, long timeDue) {
        if(this.knowledge == null)
        {
            KnowledgeGraphDisplayer displayer;
            this.knowledge = new AgentKnowledge();
            Commons.initAgentsKnowledge(this.knowledge, game);
            displayer = new KnowledgeGraphDisplayer(this.knowledge.getGraph(), "file:///" + Paths.get(".").toAbsolutePath().normalize().toString() + "/src/main/java/entrants/utils/ui/kgraph.css");
            displayer.display();
        }
        else
        {
            Commons.updateAgentsKnowledge(this.knowledge, game);
        }
        return Constants.MOVE.DOWN;
    }
}
