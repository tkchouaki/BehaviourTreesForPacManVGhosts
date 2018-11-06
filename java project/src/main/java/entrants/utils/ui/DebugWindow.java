package entrants.utils.ui;

import org.graphstream.ui.swingViewer.ViewPanel;
import org.graphstream.ui.view.Viewer;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.BadLocationException;
import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.HashMap;
import java.util.Map;

/**
 * A debug window designed to see how agent's knowledge is and display some log information.
 */
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
        setMinimumSize(new Dimension(900, 500));
        setTitle("Debug window");
    }

    public DebugModel getModel() {
        return model;
    }

    public JTextArea getLogsArea() {
        return logs;
    }

    // TOOLS
    private void createModel() {
        model = new DebugModel();
    }

    private void createComponents() {
        logs = new JTextArea(); {
            logs.setEditable(false);
            logs.setLineWrap(true);
        }

        displayers = new HashMap<>();
        displayPanel = new JPanel(new GridLayout(2, 2, 5, 5));
    }

    private void placeComponents() {
        setLayout(new BorderLayout());
        this.add(displayPanel, BorderLayout.CENTER);
        JScrollPane jScrollPane = new JScrollPane(); {
            jScrollPane.setViewportView(logs);
            jScrollPane.setPreferredSize(new Dimension(300, 400));
        }
        this.add(jScrollPane, BorderLayout.EAST);
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

        logs.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                logs.setCaretPosition(logs.getText().length());
            }

            @Override
            public void removeUpdate(DocumentEvent e) {

            }

            @Override
            public void changedUpdate(DocumentEvent e) {

            }
        });
    }

    private void addDisplayer(KnowledgeGraphDisplayer displayer) {
        Viewer viewer = new Viewer(
                displayer.getUIGraph(), Viewer.ThreadingModel.GRAPH_IN_ANOTHER_THREAD
        );

        viewer.enableAutoLayout();

        ViewPanel view = viewer.addDefaultView(false);
        view.setPreferredSize(new Dimension(300, 200));

        displayers.put(displayer, view);
        displayPanel.add(view);
    }

    private void removeDisplayer(KnowledgeGraphDisplayer displayer) {
        ViewPanel view = displayers.remove(displayer);
        displayPanel.remove(view);
    }
}
