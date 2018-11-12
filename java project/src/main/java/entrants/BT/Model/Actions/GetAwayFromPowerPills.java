// ******************************************************* 
//                   MACHINE GENERATED CODE                
//                       DO NOT MODIFY                     
//                                                         
// Generated on 11/12/2018 11:11:22
// ******************************************************* 
package entrants.BT.Model.Actions;

/** ModelAction class created from MMPM action GetAwayFromPowerPills. */
public class GetAwayFromPowerPills extends
		jbt.model.task.leaf.action.ModelAction {

	/** Constructor. Constructs an instance of GetAwayFromPowerPills. */
	public GetAwayFromPowerPills(jbt.model.core.ModelTask guard) {
		super(guard);
	}

	/**
	 * Returns a entrants.BT.Execution.Actions.GetAwayFromPowerPills task that
	 * is able to run this task.
	 */
	public jbt.execution.core.ExecutionTask createExecutor(
			jbt.execution.core.BTExecutor executor,
			jbt.execution.core.ExecutionTask parent) {
		return new entrants.BT.Execution.Actions.GetAwayFromPowerPills(this,
				executor, parent);
	}
}