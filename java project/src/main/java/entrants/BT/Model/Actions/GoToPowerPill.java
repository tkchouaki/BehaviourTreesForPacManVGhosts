// ******************************************************* 
//                   MACHINE GENERATED CODE                
//                       DO NOT MODIFY                     
//                                                         
// Generated on 11/08/2018 15:23:05
// ******************************************************* 
package entrants.BT.Model.Actions;

/** ModelAction class created from MMPM action GoToPowerPill. */
public class GoToPowerPill extends jbt.model.task.leaf.action.ModelAction {
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
	 * Constructor. Constructs an instance of GoToPowerPill.
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
	public GoToPowerPill(jbt.model.core.ModelTask guard,
			java.lang.Integer powerPillIndex, java.lang.String powerPillIndexLoc) {
		super(guard);
		this.powerPillIndex = powerPillIndex;
		this.powerPillIndexLoc = powerPillIndexLoc;
	}

	/**
	 * Returns a entrants.BT.Execution.Actions.GoToPowerPill task that is able
	 * to run this task.
	 */
	public jbt.execution.core.ExecutionTask createExecutor(
			jbt.execution.core.BTExecutor executor,
			jbt.execution.core.ExecutionTask parent) {
		return new entrants.BT.Execution.Actions.GoToPowerPill(this, executor,
				parent, this.powerPillIndex, this.powerPillIndexLoc);
	}
}