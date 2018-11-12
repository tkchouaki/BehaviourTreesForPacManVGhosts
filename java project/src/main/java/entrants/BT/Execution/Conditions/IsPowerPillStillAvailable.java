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

/**
 * ExecutionCondition class created from MMPM condition
 * IsPowerPillStillAvailable.
 */
public class IsPowerPillStillAvailable extends
		jbt.execution.task.leaf.condition.ExecutionCondition {
	/**
	 * Value of the parameter "powerPillIndex" in case its value is specified at
	 * construction time. null otherwise.
	 */
	private java.lang.Integer powerPillIndex;
	/**
	 * Location, in the context, of the parameter "powerPillIndex" in case its
	 * value is not specified at construction time. null otherwise.
	 */
	private java.lang.String powerPillIndexLoc;

	/**
	 * Constructor. Constructs an instance of IsPowerPillStillAvailable that is
	 * able to run a entrants.BT.Model.Conditions.IsPowerPillStillAvailable.
	 * 
	 * @param powerPillIndex
	 *            value of the parameter "powerPillIndex", or null in case it
	 *            should be read from the context. If null,
	 *            <code>powerPillIndexLoc<code> cannot be null.
	 * @param powerPillIndexLoc
	 *            in case <code>powerPillIndex</code> is null, this variable
	 *            represents the place in the context where the parameter's
	 *            value will be retrieved from.
	 */
	public IsPowerPillStillAvailable(
			entrants.BT.Model.Conditions.IsPowerPillStillAvailable modelTask,
			jbt.execution.core.BTExecutor executor,
			jbt.execution.core.ExecutionTask parent,
			java.lang.Integer powerPillIndex, java.lang.String powerPillIndexLoc) {
		super(modelTask, executor, parent);

		this.powerPillIndex = powerPillIndex;
		this.powerPillIndexLoc = powerPillIndexLoc;
	}

	/**
	 * Returns the value of the parameter "powerPillIndex", or null in case it
	 * has not been specified or it cannot be found in the context.
	 */
	public java.lang.Integer getPowerPillIndex() {
		if (this.powerPillIndex != null) {
			return this.powerPillIndex;
		} else {
			return (java.lang.Integer) this.getContext().getVariable(
					this.powerPillIndexLoc);
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
	}

	protected jbt.execution.core.ExecutionTask.Status internalTick() {
		Game game = (Game) getContext().getVariable("GAME");

		if(game.isPillStillAvailable(getPowerPillIndex())){
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