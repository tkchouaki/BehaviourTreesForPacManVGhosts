// ******************************************************* 
//                   MACHINE GENERATED CODE                
//                MUST BE CAREFULLY COMPLETED              
//                                                         
//           ABSTRACT METHODS MUST BE IMPLEMENTED          
//                                                         
// Generated on 11/12/2018 11:11:22
// ******************************************************* 
package entrants.BT.Execution.Actions;

import entrants.ghosts.username.Ghost;
import entrants.utils.graph.Edge;
import entrants.utils.graph.Node;
import entrants.utils.graph.UndirectedGraph;
import entrants.utils.graph.interfaces.IUndirectedGraph;
import pacman.game.Constants;
import pacman.game.Game;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/** ExecutionAction class created from MMPM action GetAwayFromPowerPills. */
public class GetAwayFromPowerPills extends
		jbt.execution.task.leaf.action.ExecutionAction {

	/**
	 * Constructor. Constructs an instance of GetAwayFromPowerPills that is able
	 * to run a entrants.BT.Model.Actions.GetAwayFromPowerPills.
	 */
	public GetAwayFromPowerPills(
			entrants.BT.Model.Actions.GetAwayFromPowerPills modelTask,
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
		/*
		 * TODO: this method's implementation must be completed. This function
		 * should only return Status.SUCCESS, Status.FAILURE or Status.RUNNING.
		 * No other values are allowed.
		 */
		Game game = (Game) this.getContext().getVariable("GAME");
		Ghost ghost = (Ghost) this.getContext().getVariable("GHOST");
		Node currentPosition = ghost.getKnowledge().getKnowledgeAboutMySelf().getPosition();
		UndirectedGraph<Node, Edge> graph = ghost.getKnowledge().getGraph();
		Set<Node> nodesWithPowerPills = Node.getNodesWithPowerPills(graph.getNodes());
		Node closestNodeWithPowerPill = null;
		int minDistance = 0;
		if(nodesWithPowerPills.size() > 0)
		{
			for(Node nodeWithPowerPill : nodesWithPowerPills)
			{
				int distance = game.getShortestPathDistance(nodeWithPowerPill.getId(), currentPosition.getId());
				if(closestNodeWithPowerPill == null || distance < minDistance)
				{
					minDistance = distance;
					closestNodeWithPowerPill = nodeWithPowerPill;
				}
			}
			this.getContext().setVariable("SELECTED_NODE", closestNodeWithPowerPill);
			this.getContext().setVariable("CLOSING", false);
			return Status.SUCCESS;
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