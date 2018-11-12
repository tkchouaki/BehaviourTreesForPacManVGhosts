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
            classes.add(ghost.className.toLowerCase());
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

        //LOGGER.info("Classes for node " + n.getId() + ": " + classes);
        org.graphstream.graph.Node graphStreamNode = graph.getNode(n.getId().toString());
        if(graphStreamNode != null)
        {
            graphStreamNode.addAttribute("ui.class", String.join(",", classes));
        }
    }


}
