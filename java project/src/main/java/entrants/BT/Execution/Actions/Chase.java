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

	private static final int TO_CLOSEST_CIRCLING_NODE = 0;
	private static final int TO_CIRCLING_NODE_IM_CLOSEST_TO = 1;
	private static final int TO_PACMAN = 2;
	private static final int TO_CLOSEST_POSSIBLE_PACMAN_NEXT_DESTINATION = 3;
	private static final int ACCORDING_TO_AGENT = 4;
	private static final int CHOOSE_RANDOM_STRATEGY = -1;

	private static final int CHASING_STRATEGY = CHOOSE_RANDOM_STRATEGY;

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
		//use the strategy specified by the CHASING_STRATEGY constant
		//if it doesn't correspond to a strategy, a random one is picked
		routeStrategy(CHASING_STRATEGY);
		return jbt.execution.core.ExecutionTask.Status.SUCCESS;
	}

	private void routeStrategy(int chasingStrategy)
	{
		//if the chasing strategy is not valid, a random one is picked
		if(chasingStrategy>4 || chasingStrategy < 0)
		{
			chasingStrategy = new Random().nextInt(5);
		}
		switch(chasingStrategy)
		{
			case TO_CIRCLING_NODE_IM_CLOSEST_TO:
				this.toCirclingNodeImClosestTo();
				break;

			case TO_CLOSEST_CIRCLING_NODE:
				this.toClosestCirclingNode();
				break;

			case TO_PACMAN:
				this.toPacman();
				break;

			case TO_CLOSEST_POSSIBLE_PACMAN_NEXT_DESTINATION:
				this.toClosestPossiblePacmanNextDestination();
			break;

			case ACCORDING_TO_AGENT:
				this.accordingToAgent();
			break;
		}
	}

	private void accordingToAgent()
	{
		Ghost ghost = (Ghost) this.getContext().getVariable("GHOST");
		Constants.GHOST ghostValue = ghost.getGhostEnumValue();
		if(ghostValue.equals(Constants.GHOST.INKY) || ghostValue.equals(Constants.GHOST.SUE))
		{
			this.toPacman();
		}
		else
		{
			this.toClosestPossiblePacmanNextDestination();
		}
	}

	/**
	 * Sets the ghost to move to PacMan's position
	 */
	private void toPacman()
	{
		Ghost ghost = (Ghost) this.getContext().getVariable("GHOST");
		this.getContext().setVariable("SELECTED_NODE", ghost.getKnowledge().getPacManDescription().getPosition());
		this.getContext().setVariable("CLOSING", true);
	}

	/**
	 * Sets the ghost to move next possible position of pacman
	 */
	private void toClosestPossiblePacmanNextDestination()
	{
		Ghost ghost = (Ghost) this.getContext().getVariable("GHOST");
		UndirectedGraph<Node, Edge> graph = ghost.getKnowledge().getGraph();
		//we try to compute a path from PacMan to the rest of the decision nodes & forbidding its previous position.
		Set<Node> forbidden = new HashSet<>();
		forbidden.add(ghost.getKnowledge().getPacManDescription().getPreviousPosition());
		//we remove the ghost's position from the targets.
		Set<Node> targets = Node.getDecisionNodes(graph.getNodes());
		targets.remove(ghost.getKnowledge().getKnowledgeAboutMySelf().getPosition());
		List<Node> path = null;
		try {
			path = graph.getPathToClosest(ghost.getKnowledge().getPacManDescription().getPosition(), targets, forbidden);
			if(path != null && path.size() > 0 ){
				//we have the closest decision node from pacman that can be its next destination
				if(path.contains(ghost.getKnowledge().getKnowledgeAboutMySelf().getPosition()))
                {
                    this.toPacman();
                }
                else
                {
                    Node target = path.get(path.size()-1);
                    this.getContext().setVariable("SELECTED_NODE", target);
                    this.getContext().setVariable("CLOSING", true);
                }
			}
		} catch (IUndirectedGraph.NodeNotFoundException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Sets the ghost to move the closest decision node nearby pacman
	 */
	private void toClosestCirclingNode()
	{
		Game game = (Game) this.getContext().getVariable("GAME");
		Ghost ghost = (Ghost) this.getContext().getVariable("GHOST");
		UndirectedGraph<Node, Edge> graph = ghost.getKnowledge().getGraph();
		Node pacManPosition = ghost.getKnowledge().getPacManDescription().getPosition();
		Node ghostPosition = ghost.getKnowledge().getKnowledgeAboutMySelf().getPosition();

		Collection<Node> circlingNodes = graph.circleNode(pacManPosition, Node.getDecisionNodes(graph.getNodes())).keySet();
		ArrayList<Node> closestCirclingNodes = null;
		int minDistance=-1;
		for(Node node : circlingNodes)
		{
			int distance = game.getShortestPathDistance(ghostPosition.getId(), node.getId(), game.getGhostLastMoveMade(ghost.getGhostEnumValue()));
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
		this.getContext().setVariable("SELECTED_NODE", targetNode);
		this.getContext().setVariable("CLOSING", true);
	}

	/**
	 * Sets the ghost to move to a node nearby pacman whom he is the closest to
	 */
	private void toCirclingNodeImClosestTo()
	{
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
			int distanceToNode = game.getShortestPathDistance(ghostPosition.getId(), circlingNode.getId(), game.getGhostLastMoveMade(ghost.getGhostEnumValue()));
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

		for(int nodeIndex : game.getShortestPath(targetNode.getId(), pacManPosition.getId()))
		{
			if(nodeIndex == ghostPosition.getId())
			{
				this.toPacman();
				return;
			}
		}
        this.getContext().setVariable("SELECTED_NODE", targetNode);
		this.getContext().setVariable("CLOSING", true);
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