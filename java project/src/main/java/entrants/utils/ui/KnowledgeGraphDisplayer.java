package entrants.utils.ui;

import entrants.utils.graph.KnowledgeGraph;
import entrants.utils.graph.Node;
import org.graphstream.graph.Graph;
import org.graphstream.graph.implementations.DefaultGraph;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public class KnowledgeGraphDisplayer {
    // ATTRIBUTES
    private final KnowledgeGraph graphData;
    private final Graph graphUI;

    public KnowledgeGraphDisplayer(KnowledgeGraph g) {
        if (g == null) {
            throw new NullPointerException();
        }
        graphUI = new DefaultGraph("Knowledge");
        graphData = g;
        registerListener();
    }

    public KnowledgeGraph getKnowledgeGraph() {
        return this.graphData;
    }

    public void display() {
        graphUI.display();
    }

    // TOOLS
    private void registerListener() {
        PropertyChangeSupport support = graphData.getPropertyChangeSupport();

        support.addPropertyChangeListener(KnowledgeGraph.ADDED_NODE_PROPERTY, new PropertyChangeListener() {
            @Override
            public void propertyChange(PropertyChangeEvent evt) {
                graphUI.addNode(evt.getNewValue().toString());
            }
        });

        support.addPropertyChangeListener(KnowledgeGraph.ADDED_EDGE_PROPERTY, new PropertyChangeListener() {
            @Override
            public void propertyChange(PropertyChangeEvent evt) {
                KnowledgeGraph.Edge edge = (KnowledgeGraph.Edge) evt.getNewValue();
                graphUI.addEdge(
                        edge.toString(), edge.getFirst().toString(), edge.getSecond().toString(), edge.isDirected()
                );
            }
        });
    }
}
