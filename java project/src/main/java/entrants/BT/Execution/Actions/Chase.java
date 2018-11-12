// ******************************************************* 
//                   MACHINE GENERATED CODE                
//                MUST BE CAREFULLY COMPLETED              
//                                                         
//           ABSTRACT METHODS MUST BE IMPLEMENTED          
//                                                         
// Generated on 11/08/2018 15:23:05
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

/** ExecutionAction class created from MMPM action Chase. */
public class Chase extends jbt.execution.task.leaf.action.ExecutionAction {

	/**
	 * Constructor. Constructs an instance of Chase that is able to run a
	 * entrants.BT.Model.Actions.Chase.
	 */
	public Chase(entrants.BT.Model.Actions.Chase modelTask,
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
		int currentPosition = ghost.getKnowledge().getKnowledgeAboutMySelf().getPosition().getId();
		int pacManPosition = ghost.getKnowledge().getPacManDescription().getPosition().getId();
		if(ghost.getGhostEnumValue().equals(Constants.GHOST.BLINKY))
		{
			UndirectedGraph<Node, Edge> graph = ghost.getKnowledge().getGraph();
			Set<Node> forbidden = new HashSet<>();
			forbidden.add(ghost.getKnowledge().getPacManDescription().getPreviousPosition());
			Set<Node> targets = Node.getDecisionNodes(graph.getNodes());
			targets.remove(ghost.getKnowledge().getKnowledgeAboutMySelf().getPosition());
			List<Node> path = null;
			try {
				System.out.println("here");
				path = graph.getPathToClosest(ghost.getKnowledge().getKnowledgeAboutMySelf().getPosition(), targets, forbidden);
				Node target = path.get(path.size()-1);
				System.out.println(target.getId());
				this.getContext().setVariable("MOVE", game.getNextMoveTowardsTarget(currentPosition, target.getId(), Constants.DM.PATH));
			} catch (IUndirectedGraph.NodeNotFoundException e) {
				e.printStackTrace();
			}
		}
		else
		{
			this.getContext().setVariable("MOVE", game.getNextMoveTowardsTarget(currentPosition, pacManPosition, Constants.DM.PATH));
		}
		System.out.println("Chase");
		return jbt.execution.core.ExecutionTask.Status.SUCCESS;
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