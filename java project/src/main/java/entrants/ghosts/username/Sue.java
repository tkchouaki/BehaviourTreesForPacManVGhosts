package entrants.ghosts.username;

import entrants.utils.Commons;
import entrants.utils.graph.AgentKnowledge;
import entrants.utils.graph.DiscreteKnowledgeGraph;
import pacman.controllers.IndividualGhostController;
import pacman.controllers.MASController;
import pacman.game.Constants;
import pacman.game.Game;

/**
 * Created by Piers on 11/11/2015.
 */
public class Sue extends IndividualGhostController {

    private AgentKnowledge knowledge;
    private DiscreteKnowledgeGraph discreteKnowledgeGraph;
    private boolean initialized;

    public Sue() {
        super(Constants.GHOST.SUE);
        this.knowledge = new AgentKnowledge(ghost);
    }

    public AgentKnowledge getAgentKnowledge() {
        return knowledge;
    }

    @Override
    public Constants.MOVE getMove(Game game, long timeDue)
    {
        if(!initialized)
        {
            Commons.initAgentsKnowledge(this.knowledge, game);
            discreteKnowledgeGraph = new DiscreteKnowledgeGraph(this.knowledge.getGraph());
            initialized = true;
        }
        else
        {
            this.discreteKnowledgeGraph.update(Commons.updateAgentsKnowledge(this.knowledge, game));
        }
        return Constants.MOVE.LEFT;
    }
}
