// ******************************************************* 
//                   MACHINE GENERATED CODE                
//                       DO NOT MODIFY                     
//                                                         
// Generated on 10/26/2018 14:10:42
// ******************************************************* 
package entrants.BT.Model.Actions;

/** ModelAction class created from MMPM action GetPossibleMove. */
public class GetPossibleMove extends jbt.model.task.leaf.action.ModelAction {
	/**
	 * Value of the parameter "nodeIndex" in case its value is specified at
	 * construction time. null otherwise.
	 */
	private java.lang.Integer nodeIndex;
	/**
	 * Location, in the context, of the parameter "nodeIndex" in case its value
	 * is not specified at construction time. null otherwise.
	 */
	private java.lang.String nodeIndexLoc;
	/**
	 * Value of the parameter "lastMove" in case its value is specified at
	 * construction time. null otherwise.
	 */
	private java.lang.Integer lastMove;
	/**
	 * Location, in the context, of the parameter "lastMove" in case its value
	 * is not specified at construction time. null otherwise.
	 */
	private java.lang.String lastMoveLoc;

	/**
	 * Constructor. Constructs an instance of GetPossibleMove.
	 * 
	 * @param nodeIndex
	 *            value of the parameter "nodeIndex", or null in case it should
	 *            be read from the context. If null, <code>nodeIndexLoc</code>
	 *            cannot be null.
	 * @param nodeIndexLoc
	 *            in case <code>nodeIndex</code> is null, this variable
	 *            represents the place in the context where the parameter's
	 *            value will be retrieved from.
	 * @param lastMove
	 *            value of the parameter "lastMove", or null in case it should
	 *            be read from the context. If null, <code>lastMoveLoc</code>
	 *            cannot be null.
	 * @param lastMoveLoc
	 *            in case <code>lastMove</code> is null, this variable
	 *            represents the place in the context where the parameter's
	 *            value will be retrieved from.
	 */
	public GetPossibleMove(jbt.model.core.ModelTask guard,
			java.lang.Integer nodeIndex, java.lang.String nodeIndexLoc,
			java.lang.Integer lastMove, java.lang.String lastMoveLoc) {
		super(guard);
		this.nodeIndex = nodeIndex;
		this.nodeIndexLoc = nodeIndexLoc;
		this.lastMove = lastMove;
		this.lastMoveLoc = lastMoveLoc;
	}

	/**
	 * Returns a entrants.BT.Execution.Actions.GetPossibleMove task that is able
	 * to run this task.
	 */
	public jbt.execution.core.ExecutionTask createExecutor(
			jbt.execution.core.BTExecutor executor,
			jbt.execution.core.ExecutionTask parent) {
		return new entrants.BT.Execution.Actions.GetPossibleMove(this,
				executor, parent, this.nodeIndex, this.nodeIndexLoc,
				this.lastMove, this.lastMoveLoc);
	}
}