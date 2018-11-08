// ******************************************************* 
//                   MACHINE GENERATED CODE                
//                MUST BE CAREFULLY COMPLETED              
//                                                         
//           ABSTRACT METHODS MUST BE IMPLEMENTED          
//                                                         
// Generated on 11/08/2018 15:23:05
// ******************************************************* 
package entrants.BT.Execution.Conditions;

import entrants.ghosts.username.Ghost;
import entrants.utils.graph.Node;
import pacman.game.Game;

/**
 * ExecutionCondition class created from MMPM condition
 * IsPacManCloseToPowerPill.
 */
public class IsPacManCloseToPowerPill extends
		jbt.execution.task.leaf.condition.ExecutionCondition {

	/**
	 * Constructor. Constructs an instance of IsPacManCloseToPowerPill that is
	 * able to run a entrants.BT.Model.Conditions.IsPacManCloseToPowerPill.
	 */
	public IsPacManCloseToPowerPill(
			entrants.BT.Model.Conditions.IsPacManCloseToPowerPill modelTask,
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
		System.out.println(this.getClass().getCanonicalName() + " spawned");
	}

	protected jbt.execution.core.ExecutionTask.Status internalTick() {
		/*
		 * TODO: this method's implementation must be completed. This function
		 * should only return Status.SUCCESS, Status.FAILURE or Status.RUNNING.
		 * No other values are allowed.
		 */
		Game game = (Game) this.getContext().getVariable("GAME");
		Ghost ghost = (Ghost) this.getContext().getVariable("GHOST");
		int threshold = 15;
		int distanceToClosestPowerPill = -1;

		int pacManPosition = ghost.getKnowledge().getPacManDescription().getPosition().getId();
		int closestPowerPillPosition = -1;
		for(Node node : Node.getNodesWithPowerPills(ghost.getDiscreteGraph().getNodes()))
		{
			int distance = game.getShortestPathDistance(pacManPosition, node.getId());
			if(distance < distanceToClosestPowerPill || closestPowerPillPosition < 0)
			{
				distanceToClosestPowerPill = distance;
				closestPowerPillPosition = node.getId();
			}
		}
		if(distanceToClosestPowerPill>-1 && distanceToClosestPowerPill<=threshold)
		{
			return jbt.execution.core.ExecutionTask.Status.SUCCESS;
		}
		return Status.FAILURE;
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