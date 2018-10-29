// ******************************************************* 
//                   MACHINE GENERATED CODE                
//                       DO NOT MODIFY                     
//                                                         
// Generated on 10/26/2018 14:10:42
// ******************************************************* 
package entrants.BT.Model.Conditions;

/** ModelCondition class created from MMPM condition IsPillStillAvailable. */
public class IsPillStillAvailable extends
		jbt.model.task.leaf.condition.ModelCondition {
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
	 * Constructor. Constructs an instance of IsPillStillAvailable.
	 * 
	 * @param pillIndex
	 *            value of the parameter "pillIndex", or null in case it should
	 *            be read from the context. If null, <code>pillIndexLoc</code>
	 *            cannot be null.
	 * @param pillIndexLoc
	 *            in case <code>pillIndex</code> is null, this variable
	 *            represents the place in the context where the parameter's
	 *            value will be retrieved from.
	 */
	public IsPillStillAvailable(jbt.model.core.ModelTask guard,
			java.lang.Integer pillIndex, java.lang.String pillIndexLoc) {
		super(guard);
		this.pillIndex = pillIndex;
		this.pillIndexLoc = pillIndexLoc;
	}

	/**
	 * Returns a entrants.BT.Execution.Conditions.IsPillStillAvailable task that
	 * is able to run this task.
	 */
	public jbt.execution.core.ExecutionTask createExecutor(
			jbt.execution.core.BTExecutor executor,
			jbt.execution.core.ExecutionTask parent) {
		return new entrants.BT.Execution.Conditions.IsPillStillAvailable(this,
				executor, parent, this.pillIndex, this.pillIndexLoc);
	}
}