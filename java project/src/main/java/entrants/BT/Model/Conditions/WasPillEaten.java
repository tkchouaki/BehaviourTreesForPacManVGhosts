// ******************************************************* 
//                   MACHINE GENERATED CODE                
//                       DO NOT MODIFY                     
//                                                         
// Generated on 10/26/2018 14:10:42
// ******************************************************* 
package entrants.BT.Model.Conditions;

/** ModelCondition class created from MMPM condition WasPillEaten. */
public class WasPillEaten extends jbt.model.task.leaf.condition.ModelCondition {

	/** Constructor. Constructs an instance of WasPillEaten. */
	public WasPillEaten(jbt.model.core.ModelTask guard) {
		super(guard);
	}

	/**
	 * Returns a entrants.BT.Execution.Conditions.WasPillEaten task that is able
	 * to run this task.
	 */
	public jbt.execution.core.ExecutionTask createExecutor(
			jbt.execution.core.BTExecutor executor,
			jbt.execution.core.ExecutionTask parent) {
		return new entrants.BT.Execution.Conditions.WasPillEaten(this,
				executor, parent);
	}
}