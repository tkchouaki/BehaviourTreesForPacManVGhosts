// ******************************************************* 
//                   MACHINE GENERATED CODE                
//                       DO NOT MODIFY                     
//                                                         
// Generated on 10/25/2018 12:08:02
// ******************************************************* 
package entrants.BT.Model.Conditions;

/** ModelCondition class created from MMPM condition See. */
public class See extends jbt.model.task.leaf.condition.ModelCondition {
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
	 * Constructor. Constructs an instance of See.
	 * 
	 * @param agent
	 *            value of the parameter "agent", or null in case it should be
	 *            read from the context. If null, <code>agentLoc</code> cannot
	 *            be null.
	 * @param agentLoc
	 *            in case <code>agent</code> is null, this variable represents
	 *            the place in the context where the parameter's value will be
	 *            retrieved from.
	 * @param range
	 *            value of the parameter "range", or null in case it should be
	 *            read from the context. If null, <code>rangeLoc</code> cannot
	 *            be null.
	 * @param rangeLoc
	 *            in case <code>range</code> is null, this variable represents
	 *            the place in the context where the parameter's value will be
	 *            retrieved from.
	 */
	public See(jbt.model.core.ModelTask guard, java.lang.Integer agent,
			java.lang.String agentLoc, java.lang.Integer range,
			java.lang.String rangeLoc) {
		super(guard);
		this.agent = agent;
		this.agentLoc = agentLoc;
		this.range = range;
		this.rangeLoc = rangeLoc;
	}

	/**
	 * Returns a entrants.BT.Execution.Conditions.See task that is able to run
	 * this task.
	 */
	public jbt.execution.core.ExecutionTask createExecutor(
			jbt.execution.core.BTExecutor executor,
			jbt.execution.core.ExecutionTask parent) {
		return new entrants.BT.Execution.Conditions.See(this, executor, parent,
				this.agent, this.agentLoc, this.range, this.rangeLoc);
	}
}