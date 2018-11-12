// ******************************************************* 
//                   MACHINE GENERATED CODE                
//                MUST BE CAREFULLY COMPLETED              
//                                                         
//           ABSTRACT METHODS MUST BE IMPLEMENTED          
//                                                         
// Generated on 10/26/2018 14:10:42
// ******************************************************* 
package entrants.BT.Execution.Actions;

/** ExecutionAction class created from MMPM action GetPossibleMove. */
public class GetPossibleMove extends
		jbt.execution.task.leaf.action.ExecutionAction {
	/**
	 * Value of the parameter "nodeIndex" in case its value is specified at
	 * construction time. null otherwise.
	 */
	private java.lang.Integer nodeIndex;
	/**
	 * Location, in the context, of the parameter "nodeIndex" in case its value
	 * is not specified at construction time. null otherwise.
	 */
	private java.lang.String nodeIndexLoc;
	/**
	 * Value of the parameter "lastMove" in case its value is specified at
	 * construction time. null otherwise.
	 */
	private java.lang.Integer lastMove;
	/**
	 * Location, in the context, of the parameter "lastMove" in case its value
	 * is not specified at construction time. null otherwise.
	 */
	private java.lang.String lastMoveLoc;

	/**
	 * Constructor. Constructs an instance of GetPossibleMove that is able to
	 * run a entrants.BT.Model.Actions.GetPossibleMove.
	 * 
	 * @param nodeIndex
	 *            value of the parameter "nodeIndex", or null in case it should
	 *            be read from the context. If null,
	 *            <code>nodeIndexLoc<code> cannot be null.
	 * @param nodeIndexLoc
	 *            in case <code>nodeIndex</code> is null, this variable
	 *            represents the place in the context where the parameter's
	 *            value will be retrieved from.
	 * @param lastMove
	 *            value of the parameter "lastMove", or null in case it should
	 *            be read from the context. If null,
	 *            <code>lastMoveLoc<code> cannot be null.
	 * @param lastMoveLoc
	 *            in case <code>lastMove</code> is null, this variable
	 *            represents the place in the context where the parameter's
	 *            value will be retrieved from.
	 */
	public GetPossibleMove(entrants.BT.Model.Actions.GetPossibleMove modelTask,
			jbt.execution.core.BTExecutor executor,
			jbt.execution.core.ExecutionTask parent,
			java.lang.Integer nodeIndex, java.lang.String nodeIndexLoc,
			java.lang.Integer lastMove, java.lang.String lastMoveLoc) {
		super(modelTask, executor, parent);

		this.nodeIndex = nodeIndex;
		this.nodeIndexLoc = nodeIndexLoc;
		this.lastMove = lastMove;
		this.lastMoveLoc = lastMoveLoc;
	}

	/**
	 * Returns the value of the parameter "nodeIndex", or null in case it has
	 * not been specified or it cannot be found in the context.
	 */
	public java.lang.Integer getNodeIndex() {
		if (this.nodeIndex != null) {
			return this.nodeIndex;
		} else {
			return (java.lang.Integer) this.getContext().getVariable(
					this.nodeIndexLoc);
		}
	}

	/**
	 * Returns the value of the parameter "lastMove", or null in case it has not
	 * been specified or it cannot be found in the context.
	 */
	public java.lang.Integer getLastMove() {
		if (this.lastMove != null) {
			return this.lastMove;
		} else {
			return (java.lang.Integer) this.getContext().getVariable(
					this.lastMoveLoc);
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
		/*
		 * TODO: this method's implementation must be completed. This function
		 * should only return Status.SUCCESS, Status.FAILURE or Status.RUNNING.
		 * No other values are allowed.
		 */
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