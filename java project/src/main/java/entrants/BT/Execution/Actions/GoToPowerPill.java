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
import entrants.utils.graph.*;
import pacman.game.Constants;
import pacman.game.Game;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

/** ExecutionAction class created from MMPM action GoToPowerPill. */
public class GoToPowerPill extends
		jbt.execution.task.leaf.action.ExecutionAction {
	/**
	 * Value of the parameter "powerPillIndex" in case its value is specified at
	 * construction time. null otherwise.
	 */
	private java.lang.Integer powerPillIndex;
	/**
	 * Location, in the context, of the parameter "powerPillIndex" in case its
	 * value is not specified at construction time. null otherwise.
	 */
	private java.lang.String powerPillIndexLoc;

	/**
	 * Constructor. Constructs an instance of GoToPowerPill that is able to run
	 * a entrants.BT.Model.Actions.GoToPowerPill.
	 * 
	 * @param powerPillIndex
	 *            value of the parameter "powerPillIndex", or null in case it
	 *            should be read from the context. If null,
	 *            <code>powerPillIndexLoc<code> cannot be null.
	 * @param powerPillIndexLoc
	 *            in case <code>powerPillIndex</code> is null, this variable
	 *            represents the place in the context where the parameter's
	 *            value will be retrieved from.
	 */
	public GoToPowerPill(entrants.BT.Model.Actions.GoToPowerPill modelTask,
			jbt.execution.core.BTExecutor executor,
			jbt.execution.core.ExecutionTask parent,
			java.lang.Integer powerPillIndex, java.lang.String powerPillIndexLoc) {
		super(modelTask, executor, parent);

		this.powerPillIndex = powerPillIndex;
		this.powerPillIndexLoc = powerPillIndexLoc;
	}

	/**
	 * Returns the value of the parameter "powerPillIndex", or null in case it
	 * has not been specified or it cannot be found in the context.
	 */
	public java.lang.Integer getPowerPillIndex() {
		if (this.powerPillIndex != null) {
			return this.powerPillIndex;
		} else {
			return (java.lang.Integer) this.getContext().getVariable(
					this.powerPillIndexLoc);
		}
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
		AgentKnowledge agentKnowledge = ghost.getKnowledge();
		UndirectedGraph<Node, Edge> graph = ghost.getKnowledge().getGraph();
		Set<Node> ghostsPositions = new HashSet<>();
		Set<Node> nodesWithPowerPills = Node.getNodesWithPowerPills(graph.getNodes());
		Node target = agentKnowledge.getKnowledgeAboutMySelf().getPosition();
		if(nodesWithPowerPills.size() > 0)
		{
			//I first retrieve the positions of all the ghosts i know (excluding myself)
			for(Constants.GHOST ghostValue : Constants.GHOST.values())
			{
				if(ghostValue.equals(ghost.getGhostEnumValue()))
				{
					continue;
				}
				GhostDescription ghostDescription = agentKnowledge.getGhostDescription(ghostValue);
				if(ghostDescription.getPosition() != null)
				{
					ghostsPositions.add(ghostDescription.getPosition());
				}
			}
			int minRank = -1;
			for(Node nodeWithPowerPill : nodesWithPowerPills)
			{
				int rank = 0;
				int meToPowerPill = game.getShortestPathDistance(agentKnowledge.getKnowledgeAboutMySelf().getPosition().getId(), nodeWithPowerPill.getId(), game.getGhostLastMoveMade(ghost.getGhostEnumValue()));
				for(Node ghostPosition : ghostsPositions)
				{
					int otherToPowerPill = game.getShortestPathDistance(ghostPosition.getId(), nodeWithPowerPill.getId());
					if(otherToPowerPill < meToPowerPill)
					{
						rank++;
					}
				}
				if(minRank == -1 || rank<minRank)
				{
					minRank = rank;
					target = nodeWithPowerPill;
				}
			}
			this.getContext().setVariable("SELECTED_NODE", target);
			this.getContext().setVariable("CLOSING", true);
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