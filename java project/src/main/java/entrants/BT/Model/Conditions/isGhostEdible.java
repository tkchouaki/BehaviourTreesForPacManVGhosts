// ******************************************************* 
//                   MACHINE GENERATED CODE                
//                       DO NOT MODIFY                     
//                                                         
// Generated on 10/26/2018 14:10:42
// ******************************************************* 
package entrants.BT.Model.Conditions;

/** ModelCondition class created from MMPM condition isGhostEdible. */
public class isGhostEdible extends jbt.model.task.leaf.condition.ModelCondition {
	/**
	 * Value of the parameter "ghost" in case its value is specified at
	 * construction time. null otherwise.
	 */
	private java.lang.String ghost;
	/**
	 * Location, in the context, of the parameter "ghost" in case its value is
	 * not specified at construction time. null otherwise.
	 */
	private java.lang.String ghostLoc;

	/**
	 * Constructor. Constructs an instance of isGhostEdible.
	 * 
	 * @param ghost
	 *            value of the parameter "ghost", or null in case it should be
	 *            read from the context. If null, <code>ghostLoc</code> cannot
	 *            be null.
	 * @param ghostLoc
	 *            in case <code>ghost</code> is null, this variable represents
	 *            the place in the context where the parameter's value will be
	 *            retrieved from.
	 */
	public isGhostEdible(jbt.model.core.ModelTask guard,
			java.lang.String ghost, java.lang.String ghostLoc) {
		super(guard);
		this.ghost = ghost;
		this.ghostLoc = ghostLoc;
	}

	/**
	 * Returns a entrants.BT.Execution.Conditions.isGhostEdible task that is
	 * able to run this task.
	 */
	public jbt.execution.core.ExecutionTask createExecutor(
			jbt.execution.core.BTExecutor executor,
			jbt.execution.core.ExecutionTask parent) {
		return new entrants.BT.Execution.Conditions.isGhostEdible(this,
				executor, parent, this.ghost, this.ghostLoc);
	}
}