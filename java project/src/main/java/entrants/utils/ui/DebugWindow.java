package entrants.utils.ui;

import org.graphstream.ui.swingViewer.ViewPanel;
import org.graphstream.ui.view.Viewer;

import javax.swing.*;
import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.HashMap;
import java.util.Map;

public class DebugWindow extends JFrame {

    private JTextArea logs;
    private DebugModel model;
    private Map<KnowledgeGraphDisplayer, ViewPanel> displayers;
    private JPanel displayPanel;

    public DebugWindow() {
        createModel();
        createComponents();
        placeComponents();
        createController();

        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setMinimumSize(new Dimension(100, 100));
        setTitle("Hello world");
    }

    public DebugModel getModel() {
        return model;
    }

    // TOOLS
    private void createModel() {
        model = new DebugModel();
    }

    private void createComponents() {
        logs = new JTextArea();
        logs.setPreferredSize(new Dimension(200, 400));
        displayers = new HashMap<>();
        displayPanel = new JPanel(new FlowLayout());
    }

    private void placeComponents() {
        setLayout(new BorderLayout());
        this.add(displayPanel, BorderLayout.CENTER);
        this.add(logs, BorderLayout.EAST);
    }

    private void createController() {
        model.getPropertyChangeSupport().addPropertyChangeListener(DebugModel.DISPLAY_PROP, new PropertyChangeListener() {
            @Override
            public void propertyChange(PropertyChangeEvent evt) {
                if (evt.getOldValue() == null) {
                    // A new Displayer should be displayed
                    KnowledgeGraphDisplayer displayer = (KnowledgeGraphDisplayer) evt.getNewValue();
                    addDisplayer(displayer);
                } else {
                    KnowledgeGraphDisplayer displayer = (KnowledgeGraphDisplayer) evt.getOldValue();
                    removeDisplayer(displayer);
                }
            }
        });
    }

    private void addDisplayer(KnowledgeGraphDisplayer displayer) {
        Viewer viewer = new Viewer(
                displayer.getUIGraph(), Viewer.ThreadingModel.GRAPH_IN_ANOTHER_THREAD
        );
        viewer.enableAutoLayout();

        ViewPanel view = viewer.addDefaultView(false);
        view.setPreferredSize(new Dimension(600, 400));

        displayers.put(displayer, view);
        displayPanel.add(view);
    }

    private void removeDisplayer(KnowledgeGraphDisplayer displayer) {
        ViewPanel view = displayers.remove(displayer);
        displayPanel.remove(view);
    }
}
