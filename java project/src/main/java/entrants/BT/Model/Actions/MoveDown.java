// ******************************************************* 
//                   MACHINE GENERATED CODE                
//                       DO NOT MODIFY                     
//                                                         
// Generated on 10/25/2018 12:08:02
// ******************************************************* 
package entrants.BT.Model.Actions;

/** ModelAction class created from MMPM action MoveDown. */
public class MoveDown extends jbt.model.task.leaf.action.ModelAction {

	/** Constructor. Constructs an instance of MoveDown. */
	public MoveDown(jbt.model.core.ModelTask guard) {
		super(guard);
	}

	/**
	 * Returns a entrants.BT.Execution.Actions.MoveDown task that is able to run
	 * this task.
	 */
	public jbt.execution.core.ExecutionTask createExecutor(
			jbt.execution.core.BTExecutor executor,
			jbt.execution.core.ExecutionTask parent) {
		return new entrants.BT.Execution.Actions.MoveDown(this, executor,
				parent);
	}
}