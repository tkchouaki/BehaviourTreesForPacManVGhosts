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
import entrants.utils.graph.Node;
import pacman.game.Constants;
import pacman.game.Game;

/**
 * ExecutionAction class created from MMPM action Escape.
 * Implements the behaviour of the ghost escaping Pacman.
 * Note : entering this behaviour assumes the position of Pacman is known
 * This is checked by the {@link entrants.BT.Model.Conditions.IsPacManInSight} condition
 */
public class Escape extends jbt.execution.task.leaf.action.ExecutionAction {

	/**
	 * Constructor. Constructs an instance of Escape that is able to run a
	 * entrants.BT.Model.Actions.Escape.
	 */
	public Escape(entrants.BT.Model.Actions.Escape modelTask,
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

	/**
	 * Makes the ghost go away from the current Pacman Position.
	 * This method assumes the the position of Pacman is known.
	 * @return
	 * Always return true
	 */
	protected jbt.execution.core.ExecutionTask.Status internalTick() {
		/*
		 * TODO: this method's implementation must be completed. This function
		 * should only return Status.SUCCESS, Status.FAILURE or Status.RUNNING.
		 * No other values are allowed.
		 */
		Ghost ghost = (Ghost) this.getContext().getVariable("GHOST");
		Node toEscape = ghost.getKnowledge().getPacManDescription().getPosition();
		this.getContext().setVariable("SELECTED_NODE", toEscape);
		this.getContext().setVariable("CLOSING", false);
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