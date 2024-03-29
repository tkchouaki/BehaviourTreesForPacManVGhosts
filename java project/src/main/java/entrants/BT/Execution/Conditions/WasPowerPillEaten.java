// ******************************************************* 
//                   MACHINE GENERATED CODE                
//                MUST BE CAREFULLY COMPLETED              
//                                                         
//           ABSTRACT METHODS MUST BE IMPLEMENTED          
//                                                         
// Generated on 10/26/2018 14:10:42
// ******************************************************* 
package entrants.BT.Execution.Conditions;

import pacman.game.Constants;
import pacman.game.Game;

/** ExecutionCondition class created from MMPM condition WasPowerPillEaten. */
public class WasPowerPillEaten extends
		jbt.execution.task.leaf.condition.ExecutionCondition {

	/**
	 * Constructor. Constructs an instance of WasPowerPillEaten that is able to
	 * run a entrants.BT.Model.Conditions.WasPowerPillEaten.
	 */
	public WasPowerPillEaten(
			entrants.BT.Model.Conditions.WasPowerPillEaten modelTask,
			jbt.execution.core.BTExecutor executor,
			jbt.execution.core.ExecutionTask parent) {
		super(modelTask, executor, parent);

	}

	protected void internalSpawn() {
		/*
		 * Do not remove this first line unless you know what it does and you
		 * need not do it.
		 */
		this.getExecutor().requestInsertionIntoList(
				jbt.execution.core.BTExecutor.BTExecutorList.TICKABLE, this);
		/* TODO: this method's implementation must be completed. */
	}

	protected jbt.execution.core.ExecutionTask.Status internalTick() {
		Game game = (Game) getContext().getVariable("GAME");

		if(game.wasPowerPillEaten()){
			return Status.SUCCESS;
		}else{
			return Status.FAILURE;
		}
	}

	protected void internalTerminate() {
		/* TODO: this method's implementation must be completed. */
	}

	protected void restoreState(jbt.execution.core.ITaskState state) {
		/* TODO: this method's implementation must be completed. */
	}

	protected jbt.execution.core.ITaskState storeState() {
		/* TODO: this method's implementation must be completed. */
		return null;
	}

	protected jbt.execution.core.ITaskState storeTerminationState() {
		/* TODO: this method's implementation must be completed. */
		return null;
	}
}