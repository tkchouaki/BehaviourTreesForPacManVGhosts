package entrants.utils.ui;

import entrants.ghosts.username.Ghost;
import org.graphstream.stream.file.FileSinkImages;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.*;

public class DebugModel {
    public static final String CSS = "file:///" + Paths.get(".").toAbsolutePath().normalize().toString()
            + "/src/main/java/entrants/utils/ui/kgraph.css";
    public static final String AGENT_REGISTERED_PROP = "agent_registered";
    public static final String AGENT_REMOVED_PROP = "agent_removed";
    public static final String DISPLAY_PROP = "display";

    private final Map<Ghost, KnowledgeGraphDisplayer> tracedAgents;
    private final PropertyChangeSupport support;
    private final FileSinkImages pic;

    public DebugModel() {
        tracedAgents = new HashMap<>();
        support = new PropertyChangeSupport(this);

        this.pic = new FileSinkImages(FileSinkImages.OutputType.PNG, FileSinkImages.Resolutions.HD720);
    }

    public Collection<Ghost> getRegisteredAgents() {
        return new ArrayList<>(tracedAgents.keySet());
    }

    public KnowledgeGraphDisplayer getDisplayer(Ghost agent) {
        return tracedAgents.get(agent);
    }

    public PropertyChangeSupport getPropertyChangeSupport() {
        return support;
    }

    public void registerAgent(Ghost agent) {
        if (agent == null) {
            throw new AssertionError();
        }
        tracedAgents.put(agent, new KnowledgeGraphDisplayer(agent.getDiscreteGraph(), CSS));
        agent.getPropertyChangeSupport().addPropertyChangeListener(Ghost.MAZE_CHANGED_PROP, new PropertyChangeListener() {
            @Override
            public void propertyChange(PropertyChangeEvent evt) {
                tracedAgents.get(agent).setGraphData(agent.getDiscreteGraph());
            }
        });
        agent.getPropertyChangeSupport().addPropertyChangeListener(Ghost.SAVE_CURRENT_STATE_PROP, new PropertyChangeListener() {
            @Override
            public void propertyChange(PropertyChangeEvent evt) {
                try {
                    pic.writeAll(tracedAgents.get(agent).getUIGraph(), agent.getGhostEnumValue() + "_" + evt.getNewValue() + ".png");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        support.firePropertyChange(AGENT_REGISTERED_PROP, null, agent);
        support.firePropertyChange(DISPLAY_PROP, null, tracedAgents.get(agent));
    }

    public void deleteAgent(Ghost agent) {
        support.firePropertyChange(DISPLAY_PROP, null, tracedAgents.get(agent));
        tracedAgents.remove(agent);
        support.firePropertyChange(AGENT_REMOVED_PROP, agent, null);
    }
}
