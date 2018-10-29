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

/** ExecutionCondition class created from MMPM condition IsPillStillAvailable. */
public class IsPillStillAvailable extends
		jbt.execution.task.leaf.condition.ExecutionCondition {
	/**
	 * Value of the parameter "pillIndex" in case its value is specified at
	 * construction time. null otherwise.
	 */
	private java.lang.Integer pillIndex;
	/**
	 * Location, in the context, of the parameter "pillIndex" in case its value
	 * is not specified at construction time. null otherwise.
	 */
	private java.lang.String pillIndexLoc;

	/**
	 * Constructor. Constructs an instance of IsPillStillAvailable that is able
	 * to run a entrants.BT.Model.Conditions.IsPillStillAvailable.
	 * 
	 * @param pillIndex
	 *            value of the parameter "pillIndex", or null in case it should
	 *            be read from the context. If null,
	 *            <code>pillIndexLoc<code> cannot be null.
	 * @param pillIndexLoc
	 *            in case <code>pillIndex</code> is null, this variable
	 *            represents the place in the context where the parameter's
	 *            value will be retrieved from.
	 */
	public IsPillStillAvailable(
			entrants.BT.Model.Conditions.IsPillStillAvailable modelTask,
			jbt.execution.core.BTExecutor executor,
			jbt.execution.core.ExecutionTask parent,
			java.lang.Integer pillIndex, java.lang.String pillIndexLoc) {
		super(modelTask, executor, parent);

		this.pillIndex = pillIndex;
		this.pillIndexLoc = pillIndexLoc;
	}

	/**
	 * Returns the value of the parameter "pillIndex", or null in case it has
	 * not been specified or it cannot be found in the context.
	 */
	public java.lang.Integer getPillIndex() {
		if (this.pillIndex != null) {
			return this.pillIndex;
		} else {
			return (java.lang.Integer) this.getContext().getVariable(
					this.pillIndexLoc);
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

		if(game.isPillStillAvailable(getPillIndex())){
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