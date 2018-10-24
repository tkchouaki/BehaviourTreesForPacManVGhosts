package entrants.utils.ui;

import entrants.utils.graph.Node;
import org.graphstream.graph.Graph;
import pacman.game.Constants;

import java.util.ArrayList;
import java.util.List;

public class NodeRenderer {
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

        graph.getNode(n.getId().toString()).addAttribute("ui.class", String.join(",", classes));
    }


}
