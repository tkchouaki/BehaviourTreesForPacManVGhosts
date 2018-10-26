package entrants.ghosts.username;

import entrants.utils.Commons;
import entrants.utils.graph.AgentKnowledge;
import entrants.utils.graph.DiscreteKnowledgeGraph;
import entrants.utils.ui.KnowledgeGraphDisplayer;
import pacman.controllers.IndividualGhostController;
import pacman.game.Constants;
import pacman.game.Game;
import pacman.game.internal.Maze;

import java.nio.file.Paths;

public class Ghost extends IndividualGhostController {
    private AgentKnowledge knowledge;
    private boolean display;
    private DiscreteKnowledgeGraph discreteKnowledgeGraph;
    private KnowledgeGraphDisplayer displayer;
    private Maze currentMaze;

    public Ghost(Constants.GHOST ghost) {
        this(ghost, false);
    }

    public Ghost(Constants.GHOST ghost, boolean display) {
        super(ghost);
        this.display = display;
    }

    @Override
    public Constants.MOVE getMove(Game game, long l) {
        if (this.knowledge == null || !game.getCurrentMaze().equals(this.currentMaze)) {
            this.currentMaze = game.getCurrentMaze();
            this.knowledge = new AgentKnowledge();
            Commons.initAgentsKnowledge(this.knowledge, game);
            discreteKnowledgeGraph = new DiscreteKnowledgeGraph(this.knowledge.getGraph());
            if (displayer == null) {
                displayer = new KnowledgeGraphDisplayer(discreteKnowledgeGraph, "file:///" + Paths.get(".").toAbsolutePath().normalize().toString() + "/src/main/java/entrants/utils/ui/kgraph.css");
                displayer.display();
            } else {
                displayer.setGraphData(discreteKnowledgeGraph);
            }
        } else {
            this.discreteKnowledgeGraph.update(Commons.updateAgentsKnowledge(this.knowledge, game));
        }
        return Constants.MOVE.DOWN;
    }
}
