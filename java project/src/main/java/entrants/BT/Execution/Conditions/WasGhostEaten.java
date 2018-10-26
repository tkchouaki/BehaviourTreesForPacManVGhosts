// ******************************************************* 
//                   MACHINE GENERATED CODE                
//                MUST BE CAREFULLY COMPLETED              
//                                                         
//           ABSTRACT METHODS MUST BE IMPLEMENTED          
//                                                         
// Generated on 10/26/2018 14:10:42
// ******************************************************* 
package entrants.BT.Execution.Conditions;

import pacman.game.Constants;
import pacman.game.Game;

/** ExecutionCondition class created from MMPM condition WasGhostEaten. */
public class WasGhostEaten extends
		jbt.execution.task.leaf.condition.ExecutionCondition {
	/**
	 * Value of the parameter "ghost" in case its value is specified at
	 * construction time. null otherwise.
	 */
	private java.lang.String ghost;
	/**
	 * Location, in the context, of the parameter "ghost" in case its value is
	 * not specified at construction time. null otherwise.
	 */
	private java.lang.String ghostLoc;

	/**
	 * Constructor. Constructs an instance of WasGhostEaten that is able to run
	 * a entrants.BT.Model.Conditions.WasGhostEaten.
	 * 
	 * @param ghost
	 *            value of the parameter "ghost", or null in case it should be
	 *            read from the context. If null,
	 *            <code>ghostLoc<code> cannot be null.
	 * @param ghostLoc
	 *            in case <code>ghost</code> is null, this variable represents
	 *            the place in the context where the parameter's value will be
	 *            retrieved from.
	 */
	public WasGhostEaten(entrants.BT.Model.Conditions.WasGhostEaten modelTask,
			jbt.execution.core.BTExecutor executor,
			jbt.execution.core.ExecutionTask parent, java.lang.String ghost,
			java.lang.String ghostLoc) {
		super(modelTask, executor, parent);

		this.ghost = ghost;
		this.ghostLoc = ghostLoc;
	}

	/**
	 * Returns the value of the parameter "ghost", or null in case it has not
	 * been specified or it cannot be found in the context.
	 */
	public java.lang.String getGhost() {
		if (this.ghost != null) {
			return this.ghost;
		} else {
			return (java.lang.String) this.getContext().getVariable(
					this.ghostLoc);
		}
	}

	protected void internalSpawn() {
		/*
		 * Do not remove this first line unless you know what it does and you
		 * need not do it.
		 */
		this.getExecutor().requestInsertionIntoList(
				jbt.execution.core.BTExecutor.BTExecutorList.TICKABLE, this);
		/* TODO: this method's implementation must be completed. */
		System.out.println(this.getClass().getCanonicalName() + " spawned");
	}

	protected jbt.execution.core.ExecutionTask.Status internalTick() {
		Game game = (Game) getContext().getVariable("GAME");

		if(game.wasGhostEaten(Constants.GHOST.valueOf(getGhost()))){
			return Status.SUCCESS;
		}else{
			return Status.FAILURE;
		}
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