package entrants.utils.ui;

import entrants.utils.graph.Agent;

import java.beans.PropertyChangeSupport;
import java.nio.file.Paths;
import java.util.*;

public class DebugModel {
    public static final String CSS = "file:///" + Paths.get(".").toAbsolutePath().normalize().toString()
            + "/src/main/java/entrants/utils/ui/kgraph.css";
    public static final String AGENT_REGISTERED_PROP = "agent_registered";
    public static final String AGENT_REMOVED_PROP = "agent_removed";
    public static final String DISPLAY_PROP = "display";

    private final Map<Agent, KnowledgeGraphDisplayer> tracedAgents;
    private final PropertyChangeSupport support;

    public DebugModel() {
        tracedAgents = new HashMap<>();
        support = new PropertyChangeSupport(this);
    }

    public Collection<Agent> getRegisteredAgents() {
        return new ArrayList<>(tracedAgents.keySet());
    }

    public KnowledgeGraphDisplayer getDisplayer(Agent agent) {
        return tracedAgents.get(agent);
    }

    public PropertyChangeSupport getPropertyChangeSupport() {
        return support;
    }

    public void registerAgent(Agent agent) {
        if (agent == null) {
            throw new AssertionError();
        }
        tracedAgents.put(agent, new KnowledgeGraphDisplayer(agent.getDiscreteGraph(), CSS));
        support.firePropertyChange(AGENT_REGISTERED_PROP, null, agent);
        support.firePropertyChange(DISPLAY_PROP, null, tracedAgents.get(agent));
    }

    public void deleteAgent(Agent agent) {
        support.firePropertyChange(DISPLAY_PROP, null, tracedAgents.get(agent));
        tracedAgents.remove(agent);
        support.firePropertyChange(AGENT_REMOVED_PROP, agent, null);
    }
}
