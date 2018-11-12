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

        if (n.containsPill()) {
            classes.add("pill");
        }

        if (n.containsPowerPill()) {
            classes.add("power_pill");
        }

        if (n.containsPacMan()) {
            classes.add("pacman");
        }
        //LOGGER.info("Classes for node " + n.getId() + ": " + classes);
        org.graphstream.graph.Node graphStreamNode = graph.getNode(n.getId().toString());
        if(graphStreamNode != null)
        {
            graphStreamNode.setAttribute("ui.label", n.getId());
            graphStreamNode.setAttribute("x", n.getX());
            graphStreamNode.setAttribute("y", n.getY());
            graphStreamNode.addAttribute("ui.class", String.join(",", classes));
        }
    }


}
