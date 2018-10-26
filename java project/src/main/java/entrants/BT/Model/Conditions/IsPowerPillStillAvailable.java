// ******************************************************* 
//                   MACHINE GENERATED CODE                
//                       DO NOT MODIFY                     
//                                                         
// Generated on 10/26/2018 14:10:42
// ******************************************************* 
package entrants.BT.Model.Conditions;

/** ModelCondition class created from MMPM condition IsPowerPillStillAvailable. */
public class IsPowerPillStillAvailable extends
		jbt.model.task.leaf.condition.ModelCondition {
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
	 * Constructor. Constructs an instance of IsPowerPillStillAvailable.
	 * 
	 * @param powerPillIndex
	 *            value of the parameter "powerPillIndex", or null in case it
	 *            should be read from the context. If null,
	 *            <code>powerPillIndexLoc</code> cannot be null.
	 * @param powerPillIndexLoc
	 *            in case <code>powerPillIndex</code> is null, this variable
	 *            represents the place in the context where the parameter's
	 *            value will be retrieved from.
	 */
	public IsPowerPillStillAvailable(jbt.model.core.ModelTask guard,
			java.lang.Integer powerPillIndex, java.lang.String powerPillIndexLoc) {
		super(guard);
		this.powerPillIndex = powerPillIndex;
		this.powerPillIndexLoc = powerPillIndexLoc;
	}

	/**
	 * Returns a entrants.BT.Execution.Conditions.IsPowerPillStillAvailable task
	 * that is able to run this task.
	 */
	public jbt.execution.core.ExecutionTask createExecutor(
			jbt.execution.core.BTExecutor executor,
			jbt.execution.core.ExecutionTask parent) {
		return new entrants.BT.Execution.Conditions.IsPowerPillStillAvailable(
				this, executor, parent, this.powerPillIndex,
				this.powerPillIndexLoc);
	}
}