// ******************************************************* 
//                   MACHINE GENERATED CODE                
//                       DO NOT MODIFY                     
//                                                         
// Generated on 11/08/2018 15:23:05
// ******************************************************* 
package entrants.BT.Model.Actions;

/** ModelAction class created from MMPM action DefaultMove. */
public class DefaultMove extends jbt.model.task.leaf.action.ModelAction {

	/** Constructor. Constructs an instance of DefaultMove. */
	public DefaultMove(jbt.model.core.ModelTask guard) {
		super(guard);
	}

	/**
	 * Returns a entrants.BT.Execution.Actions.DefaultMove task that is able to
	 * run this task.
	 */
	public jbt.execution.core.ExecutionTask createExecutor(
			jbt.execution.core.BTExecutor executor,
			jbt.execution.core.ExecutionTask parent) {
		return new entrants.BT.Execution.Actions.DefaultMove(this, executor,
				parent);
	}
}