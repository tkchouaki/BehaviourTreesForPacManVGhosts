// ******************************************************* 
//                   MACHINE GENERATED CODE                
//                       DO NOT MODIFY                     
//                                                         
// Generated on 11/08/2018 15:23:05
// ******************************************************* 
package entrants.BT.Model.Conditions;

/** ModelCondition class created from MMPM condition IsPacManCloseToPowerPill. */
public class IsPacManCloseToPowerPill extends
		jbt.model.task.leaf.condition.ModelCondition {

	/** Constructor. Constructs an instance of IsPacManCloseToPowerPill. */
	public IsPacManCloseToPowerPill(jbt.model.core.ModelTask guard) {
		super(guard);
	}

	/**
	 * Returns a entrants.BT.Execution.Conditions.IsPacManCloseToPowerPill task
	 * that is able to run this task.
	 */
	public jbt.execution.core.ExecutionTask createExecutor(
			jbt.execution.core.BTExecutor executor,
			jbt.execution.core.ExecutionTask parent) {
		return new entrants.BT.Execution.Conditions.IsPacManCloseToPowerPill(
				this, executor, parent);
	}
}