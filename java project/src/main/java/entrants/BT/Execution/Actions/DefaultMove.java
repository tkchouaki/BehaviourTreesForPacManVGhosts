// ******************************************************* 
//                   MACHINE GENERATED CODE                
//                MUST BE CAREFULLY COMPLETED              
//                                                         
//           ABSTRACT METHODS MUST BE IMPLEMENTED          
//                                                         
// Generated on 11/08/2018 15:23:05
// ******************************************************* 
package entrants.BT.Execution.Actions;

import entrants.ghosts.username.Ghost;
import entrants.utils.graph.*;
import entrants.utils.graph.interfaces.IUndirectedGraph;
import pacman.game.Constants;
import pacman.game.Game;

import java.util.HashSet;
import java.util.Set;

/** ExecutionAction class created from MMPM action DefaultMove. */
public class DefaultMove extends jbt.execution.task.leaf.action.ExecutionAction {

	/**
	 * Constructor. Constructs an instance of DefaultMove that is able to run a
	 * entrants.BT.Model.Actions.DefaultMove.
	 */
	public DefaultMove(entrants.BT.Model.Actions.DefaultMove modelTask,
			jbt.execution.core.BTExecutor executor,
			jbt.execution.core.ExecutionTask parent) {
		super(modelTask, executor, parent);

	}

	protected void internalSpawn() {
		/*
		 * Do not remove this first line unless you know what it does and you
		 * need not do it.
		 */
		this.getExecutor().requestInsertionIntoList(
				jbt.execution.core.BTExecutor.BTExecutorList.TICKABLE, this);
		/* TODO: this method's implementation must be completed. */
	}

	protected jbt.execution.core.ExecutionTask.Status internalTick() {
		/*
		 * TODO: this method's implementation must be completed. This function
		 * should only return Status.SUCCESS, Status.FAILURE or Status.RUNNING.
		 * No other values are allowed.
		 */
		Ghost ghost = (Ghost) this.getContext().getVariable("GHOST");
		AgentKnowledge agentKnowledge = ghost.getKnowledge();
		UndirectedGraph<Node, Edge> graph = ghost.getKnowledge().getGraph();
		Node target = agentKnowledge.getKnowledgeAboutMySelf().getPosition();
		IUndirectedGraph<Node, Edge> discreteKnowledgeGraph = ghost.getDiscreteGraph();
		int minUpdateTime=-1;
		for(Node node : Node.getDecisionNodes(discreteKnowledgeGraph.getNodes()))
		{
			if(target == null || node.getLastUpdateTick() < minUpdateTime)
			{
				target = node;
				minUpdateTime = node.getLastUpdateTick();
			}
		}
		this.getContext().setVariable("SELECTED_NODE", target);
		this.getContext().setVariable("CLOSING", true);
		return jbt.execution.core.ExecutionTask.Status.SUCCESS;
	}

	protected void internalTerminate() {
		/* TODO: this method's implementation must be completed. */
	}

	protected void restoreState(jbt.execution.core.ITaskState state) {
		/* TODO: this method's implementation must be completed. */
	}

	protected jbt.execution.core.ITaskState storeState() {
		/* TODO: this method's implementation must be completed. */
		return null;
	}

	protected jbt.execution.core.ITaskState storeTerminationState() {
		/* TODO: this method's implementation must be completed. */
		return null;
	}
}