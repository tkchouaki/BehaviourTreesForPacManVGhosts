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

import java.util.*;

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
	}

	protected jbt.execution.core.ExecutionTask.Status internalTick() {
		/*
		 * TODO: this method's implementation must be completed. This function
		 * should only return Status.SUCCESS, Status.FAILURE or Status.RUNNING.
		 * No other values are allowed.
		 */
		Game game = (Game) this.getContext().getVariable("GAME");
		Ghost ghost = (Ghost) this.getContext().getVariable("GHOST");
		UndirectedGraph<Node, Edge> graph = ghost.getKnowledge().getGraph();
		Node ghostPosition = ghost.getKnowledge().getKnowledgeAboutMySelf().getPosition();
		Node pacManPosition = ghost.getKnowledge().getPacManDescription().getPosition();
        Collection<Node> circlingNodes = graph.circleNode(pacManPosition, Node.getDecisionNodes(graph.getNodes())).keySet();
        Collection<Node> nodesWithGhosts = Node.getNodesContainingGhosts(graph.getNodes());
        Map<Node, Integer> ranks = new HashMap<>();
        Node targetNode = null;
        for(Node circlingNode : circlingNodes)
        {
            int distanceToNode = game.getShortestPathDistance(ghostPosition.getId(), circlingNode.getId());
            ranks.put(circlingNode, 1);
            for(Node nodeWithGhost : nodesWithGhosts)
            {
                if(!nodeWithGhost.containsGhost(ghost.getGhostEnumValue()) && game.getShortestPathDistance(nodeWithGhost.getId(), circlingNode.getId()) < distanceToNode)
                {
                    ranks.put(circlingNode, ranks.get(circlingNode)+1);
                }
            }
            if(targetNode == null || ranks.get(targetNode) > ranks.get(circlingNode))
            {
                targetNode = circlingNode;
            }
        }
        this.getContext().setVariable("SELECTED_NODE", targetNode);
        this.getContext().setVariable("CLOSING", true);
        /*ArrayList<Node> closestCirclingNodes = null;
        int minDistance=-1;
        for(Node node : circlingNodes)
        {
            int distance = game.getShortestPathDistance(ghostPosition.getId(), node.getId());
            if(distance <= minDistance || minDistance < 0)
            {
                if(distance < minDistance || minDistance < 0)
                {
                    minDistance = distance;
                    closestCirclingNodes = new ArrayList<>();
                }
                closestCirclingNodes.add(node);
            }
        }
        Node targetNode = closestCirclingNodes.get(new Random().nextInt(closestCirclingNodes.size()));
        this.getContext().setVariable("MOVE", game.getNextMoveTowardsTarget(ghostPosition.getId(), targetNode.getId(), Constants.DM.PATH));
        */
		/*int currentPosition = ghost.getKnowledge().getKnowledgeAboutMySelf().getPosition().getId();
		int pacManPosition = ghost.getKnowledge().getPacManDescription().getPosition().getId();
		if(ghost.getGhostEnumValue().equals(Constants.GHOST.BLINKY) || ghost.getGhostEnumValue().equals(Constants.GHOST.INKY))
		{
			UndirectedGraph<Node, Edge> graph = ghost.getKnowledge().getGraph();
			Set<Node> forbidden = new HashSet<>();
			forbidden.add(ghost.getKnowledge().getPacManDescription().getPreviousPosition());
			Set<Node> targets = Node.getDecisionNodes(graph.getNodes());
			targets.remove(ghost.getKnowledge().getKnowledgeAboutMySelf().getPosition());
			List<Node> path = null;
			try {
				path = graph.getPathToClosest(ghost.getKnowledge().getPacManDescription().getPosition(), targets, forbidden);
				if(path != null && path.size() > 0 ){
					Node target = path.get(path.size()-1);
					this.getContext().setVariable("MOVE", game.getNextMoveTowardsTarget(currentPosition, target.getId(), Constants.DM.PATH));
					return jbt.execution.core.ExecutionTask.Status.SUCCESS;
				}
			} catch (IUndirectedGraph.NodeNotFoundException e) {
				e.printStackTrace();
			}
		}
		this.getContext().setVariable("MOVE", game.getNextMoveTowardsTarget(currentPosition, pacManPosition, Constants.DM.PATH));
		*/
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