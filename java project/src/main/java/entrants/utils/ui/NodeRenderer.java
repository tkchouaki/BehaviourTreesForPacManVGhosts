package entrants.utils.ui;

import entrants.utils.graph.GhostDescription;
import entrants.utils.graph.Node;
import org.graphstream.graph.Graph;
import pacman.game.Constants;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class NodeRenderer {
    private static final Logger LOGGER = Logger.getLogger(NodeRenderer.class.getName());

    // ATTRIBUTES
    private Graph graph;

    public NodeRenderer(Graph g) {
        if (g == null) {
            throw new NullPointerException();
        }
        graph = g;
    }

    // COMMANDS
    public void render(Node n) {
        List<String> classes = new ArrayList<>();

        for (Constants.GHOST ghost : n.getContainedGhosts()) {
            String ghostClass = ghost.className.toLowerCase();
            if(n.getContainedGhostDescription(ghost).getEdibleTime()>0)
            {
                ghostClass+="_frighted";
            }
            classes.add(ghostClass);
        }

        if (n.containsPacMan()) {
            classes.add("pacman");
        }
        else if(n.containsPowerPill()) {
            classes.add("power_pill");
        }
        else if (n.containsPill()) {
            classes.add("pill");
        }

        if(n.isDanger())
        {
            classes.add("danger");
        }

        if(n.isGoal())
        {
            classes.add("goal");
        }

        //LOGGER.info("Classes for node " + n.getId() + ": " + classes);
        org.graphstream.graph.Node graphStreamNode = graph.getNode(n.getId().toString());
        if(graphStreamNode != null)
        {
            graphStreamNode.addAttribute("ui.class", String.join(",", classes));
        }
    }


}
