// ******************************************************* 
//                   MACHINE GENERATED CODE                
//                MUST BE CAREFULLY COMPLETED              
//                                                         
//           ABSTRACT METHODS MUST BE IMPLEMENTED          
//                                                         
// Generated on 10/25/2018 12:08:02
// ******************************************************* 
package entrants.BT.Execution.Conditions;

/** ExecutionCondition class created from MMPM condition See. */
public class See extends jbt.execution.task.leaf.condition.ExecutionCondition {
	/**
	 * Value of the parameter "agent" in case its value is specified at
	 * construction time. null otherwise.
	 */
	private java.lang.Integer agent;
	/**
	 * Location, in the context, of the parameter "agent" in case its value is
	 * not specified at construction time. null otherwise.
	 */
	private java.lang.String agentLoc;
	/**
	 * Value of the parameter "range" in case its value is specified at
	 * construction time. null otherwise.
	 */
	private java.lang.Integer range;
	/**
	 * Location, in the context, of the parameter "range" in case its value is
	 * not specified at construction time. null otherwise.
	 */
	private java.lang.String rangeLoc;

	/**
	 * Constructor. Constructs an instance of See that is able to run a
	 * entrants.BT.Model.Conditions.See.
	 * 
	 * @param agent
	 *            value of the parameter "agent", or null in case it should be
	 *            read from the context. If null,
	 *            <code>agentLoc<code> cannot be null.
	 * @param agentLoc
	 *            in case <code>agent</code> is null, this variable represents
	 *            the place in the context where the parameter's value will be
	 *            retrieved from.
	 * @param range
	 *            value of the parameter "range", or null in case it should be
	 *            read from the context. If null,
	 *            <code>rangeLoc<code> cannot be null.
	 * @param rangeLoc
	 *            in case <code>range</code> is null, this variable represents
	 *            the place in the context where the parameter's value will be
	 *            retrieved from.
	 */
	public See(entrants.BT.Model.Conditions.See modelTask,
			jbt.execution.core.BTExecutor executor,
			jbt.execution.core.ExecutionTask parent, java.lang.Integer agent,
			java.lang.String agentLoc, java.lang.Integer range,
			java.lang.String rangeLoc) {
		super(modelTask, executor, parent);

		this.agent = agent;
		this.agentLoc = agentLoc;
		this.range = range;
		this.rangeLoc = rangeLoc;
	}

	/**
	 * Returns the value of the parameter "agent", or null in case it has not
	 * been specified or it cannot be found in the context.
	 */
	public java.lang.Integer getAgent() {
		if (this.agent != null) {
			return this.agent;
		} else {
			return (java.lang.Integer) this.getContext().getVariable(
					this.agentLoc);
		}
	}

	/**
	 * Returns the value of the parameter "range", or null in case it has not
	 * been specified or it cannot be found in the context.
	 */
	public java.lang.Integer getRange() {
		if (this.range != null) {
			return this.range;
		} else {
			return (java.lang.Integer) this.getContext().getVariable(
					this.rangeLoc);
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