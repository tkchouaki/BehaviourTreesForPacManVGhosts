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
import entrants.utils.graph.Edge;
import entrants.utils.graph.Node;
import entrants.utils.graph.UndirectedGraph;
import pacman.game.Constants;
import pacman.game.Game;

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
		Game game = (Game) this.getContext().getVariable("GAME");
		Ghost ghost = (Ghost) this.getContext().getVariable("GHOST");
		UndirectedGraph<Node, Edge> graph = ghost.getKnowledge().getGraph();
		Node target = null;
		for(Node node : Node.getDecisionNodes(graph.getNodes()))
		{
			if(target == null || node.getLastUpdateTick() < target.getLastUpdateTick())
			{
				target = node;
			}
		}
		if(target != null)
		{
			this.getContext().setVariable("MOVE", game.getNextMoveTowardsTarget(ghost.getKnowledge().getKnowledgeAboutMySelf().getPosition().getId(), target.getId(), Constants.DM.PATH));
		}
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