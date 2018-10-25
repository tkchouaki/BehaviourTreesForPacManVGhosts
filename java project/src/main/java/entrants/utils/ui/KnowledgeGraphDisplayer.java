package entrants.utils.ui;

import entrants.utils.ChangeEventListener;
import entrants.utils.graph.Edge;
import entrants.utils.graph.Node;
import entrants.utils.graph.UndirectedGraph;
import org.graphstream.graph.Graph;
import org.graphstream.graph.implementations.DefaultGraph;

import javax.swing.event.ChangeEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public class KnowledgeGraphDisplayer {
    // ATTRIBUTES
    private final UndirectedGraph<Node, Edge> graphData;
    private final Graph graphUI;
    private final NodeRenderer renderer;

    public KnowledgeGraphDisplayer(UndirectedGraph<Node, Edge> g, String css) {
        if (g == null || css == null) {
            throw new NullPointerException();
        }
        graphUI = new DefaultGraph("Knowledge");
        graphUI.addAttribute("ui.stylesheet", "url(" + css + ")");
        graphData = g;
        renderer = new NodeRenderer(graphUI);

        registerListener();
        updateUI();
    }

    public UndirectedGraph getKnowledgeGraph() {
        return this.graphData;
    }

    public void display() {
        graphUI.display();
    }

    // TOOLS
    private void registerListener() {
        PropertyChangeSupport support = graphData.getPropertyChangeSupport();

        support.addPropertyChangeListener(UndirectedGraph.NODE_ADDED_PROP, new PropertyChangeListener() {
            @Override
            public void propertyChange(PropertyChangeEvent evt) {
                graphUI.addNode(evt.getNewValue().toString());
            }
        });

        support.addPropertyChangeListener(UndirectedGraph.EDGE_ADDED_PROP, new PropertyChangeListener() {
            @Override
            public void propertyChange(PropertyChangeEvent evt) {
                KnowledgeGraph.Edge edge = (KnowledgeGraph.Edge) evt.getNewValue();
                graphUI.addEdge(
                        edge.toString(), edge.getFirst().toString(), edge.getSecond().toString(), edge.isDirected()
                );
            }
        });

        for (Node n : graphData.getNodes()) {
            n.addChangeEventListener(new ChangeEventListener() {
                @Override
                public void changed(ChangeEvent evt) {
                    renderer.render(n);
                }
            });
        }
    }

    private void updateUI() {
        for (Node n : graphData.getNodes()) {
            graphUI.addNode(n.toString());
            renderer.render(n);
        }

        for (Edge e : graphData.getEdges()) {
            graphUI.addEdge(
                    e.toString(), e.getNodeA().toString(), e.getNodeB().toString()
            );
        }
    }
}
