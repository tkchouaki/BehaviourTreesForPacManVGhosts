package entrants.utils.ui;

import entrants.utils.graph.AgentKnowledge;

import java.beans.PropertyChangeSupport;
import java.nio.file.Paths;
import java.util.*;

public class DebugModel {
    public static final String CSS = "file:///" + Paths.get(".").toAbsolutePath().normalize().toString()
            + "/src/main/java/entrants/utils/ui/kgraph.css";
    public static final String AGENT_REGISTERED_PROP = "agent_registered";
    public static final String AGENT_REMOVED_PROP = "agent_removed";
    public static final String DISPLAY_PROP = "display";

    private final Map<AgentKnowledge, KnowledgeGraphDisplayer> tracedAgents;
    private final PropertyChangeSupport support;

    public DebugModel() {
        tracedAgents = new HashMap<>();
        support = new PropertyChangeSupport(this);
    }

    public Collection<AgentKnowledge> getRegisteredAgents() {
        return new ArrayList<>(tracedAgents.keySet());
    }

    public KnowledgeGraphDisplayer getDisplayer(AgentKnowledge agent) {
        return tracedAgents.get(agent);
    }

    public PropertyChangeSupport getPropertyChangeSupport() {
        return support;
    }

    public void registerAgent(AgentKnowledge agent) {
        if (agent == null) {
            throw new AssertionError();
        }
        tracedAgents.put(agent, new KnowledgeGraphDisplayer(agent.getGraph(), CSS));
        support.firePropertyChange(AGENT_REGISTERED_PROP, null, agent);
        support.firePropertyChange(DISPLAY_PROP, null, tracedAgents.get(agent));
    }

    public void deleteAgent(AgentKnowledge agent) {
        support.firePropertyChange(DISPLAY_PROP, null, tracedAgents.get(agent));
        tracedAgents.remove(agent);
        support.firePropertyChange(AGENT_REMOVED_PROP, agent, null);
    }
}
