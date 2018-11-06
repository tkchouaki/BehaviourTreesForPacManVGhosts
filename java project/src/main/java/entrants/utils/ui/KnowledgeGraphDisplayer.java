package entrants.utils.ui;

import entrants.utils.ChangeEventListener;
import entrants.utils.graph.Edge;
import entrants.utils.graph.Node;
import entrants.utils.graph.UndirectedGraph;
import entrants.utils.graph.interfaces.IUndirectedGraph;
import org.graphstream.graph.Graph;
import org.graphstream.graph.implementations.DefaultGraph;
import org.graphstream.ui.view.Viewer;

import javax.swing.event.ChangeEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public class KnowledgeGraphDisplayer {
    // ATTRIBUTES
    private IUndirectedGraph<Node, Edge> graphData;
    private final Graph graphUI;
    private final NodeRenderer renderer;
    private String css;

    public KnowledgeGraphDisplayer(IUndirectedGraph<Node, Edge> g, String css) {
        if (css == null) {
            throw new NullPointerException();
        }
        this.css = "url(" + css + ")";
        graphUI = new DefaultGraph("Knowledge");
        graphUI.addAttribute("ui.stylesheet", this.css);
        graphData = (g == null) ? new UndirectedGraph<>() : g;
        renderer = new NodeRenderer(graphUI);

        registerListener();
        updateUI();
    }

    public IUndirectedGraph getKnowledgeGraph() {
        return this.graphData;
    }

    public Graph getUIGraph() {
        return graphUI;
    }

    public Viewer display() {
        return graphUI.display();
    }

    // TOOLS
    private void registerListener() {
        PropertyChangeSupport support = graphData.getPropertyChangeSupport();

        support.addPropertyChangeListener(UndirectedGraph.NODE_ADDED_PROP, new PropertyChangeListener() {
            @Override
            public void propertyChange(PropertyChangeEvent evt) {
                graphUI.addNode(evt.getNewValue().toString());
                Node n = (Node) evt.getNewValue();
                n.addChangeEventListener(new ChangeEventListener() {
                    @Override
                    public void changed(ChangeEvent evt) {
                        renderer.render((Node) evt.getSource());
                    }
                });
                renderer.render(n);
            }
        });

        support.addPropertyChangeListener(UndirectedGraph.EDGE_ADDED_PROP, new PropertyChangeListener() {
            @Override
            public void propertyChange(PropertyChangeEvent evt) {
                Edge edge = (Edge) evt.getNewValue();
                graphUI.addEdge(
                        edge.toString(), edge.getNodeA().toString(), edge.getNodeB().toString(), false
                );
            }
        });

        support.addPropertyChangeListener(UndirectedGraph.NODE_REMOVED_PROP, new PropertyChangeListener() {
            @Override
            public void propertyChange(PropertyChangeEvent evt) {
                Node node = (Node) evt.getOldValue();
                graphUI.removeNode(node.getId().toString());
            }
        });

        support.addPropertyChangeListener(UndirectedGraph.EDGE_REMOVED_PROP, new PropertyChangeListener() {
            @Override
            public void propertyChange(PropertyChangeEvent evt) {
                Edge edge = (Edge) evt.getOldValue();
                graphUI.removeEdge(edge.toString());
            }
        });

        for (Node n : graphData.getNodes()) {
            n.addChangeEventListener(new ChangeEventListener() {
                @Override
                public void changed(ChangeEvent evt)
                {
                    renderer.render(n);
                }
            });
        }
    }

    public void clear()
    {
        graphUI.clear();
        graphUI.addAttribute("ui.stylesheet", css);
    }

    public void setGraphData(IUndirectedGraph<Node, Edge> graphData)
    {
        this.clear();
        this.graphData = graphData;
        this.registerListener();
        this.updateUI();
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
