// ******************************************************* 
//                   MACHINE GENERATED CODE                
//                       DO NOT MODIFY                     
//                                                         
// Generated on 11/08/2018 15:23:05
// ******************************************************* 
package entrants.BT.Model.Actions;

/** ModelAction class created from MMPM action Escape. */
public class Escape extends jbt.model.task.leaf.action.ModelAction {

	/** Constructor. Constructs an instance of Escape. */
	public Escape(jbt.model.core.ModelTask guard) {
		super(guard);
	}

	/**
	 * Returns a entrants.BT.Execution.Actions.Escape task that is able to run
	 * this task.
	 */
	public jbt.execution.core.ExecutionTask createExecutor(
			jbt.execution.core.BTExecutor executor,
			jbt.execution.core.ExecutionTask parent) {
		return new entrants.BT.Execution.Actions.Escape(this, executor, parent);
	}
}