// ******************************************************* 
//                   MACHINE GENERATED CODE                
//                       DO NOT MODIFY                     
//                                                         
// Generated on 10/26/2018 14:10:42
// ******************************************************* 
package entrants.BT.Model.Actions;

/** ModelAction class created from MMPM action GetPacManLastMoveMade. */
public class GetPacManLastMoveMade extends
		jbt.model.task.leaf.action.ModelAction {

	/** Constructor. Constructs an instance of GetPacManLastMoveMade. */
	public GetPacManLastMoveMade(jbt.model.core.ModelTask guard) {
		super(guard);
	}

	/**
	 * Returns a entrants.BT.Execution.Actions.GetPacManLastMoveMade task that
	 * is able to run this task.
	 */
	public jbt.execution.core.ExecutionTask createExecutor(
			jbt.execution.core.BTExecutor executor,
			jbt.execution.core.ExecutionTask parent) {
		return new entrants.BT.Execution.Actions.GetPacManLastMoveMade(this,
				executor, parent);
	}
}