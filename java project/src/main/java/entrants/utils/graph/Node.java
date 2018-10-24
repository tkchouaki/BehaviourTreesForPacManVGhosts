package entrants.utils.graph;

import entrants.utils.Commons;
import pacman.game.Constants;
import pacman.game.Game;
import pacman.game.info.GameInfo;
import pacman.game.internal.Maze;

import java.util.HashSet;
import java.util.Set;

public class Node {
    private Integer id;
    private int containedPillId;
    private int containedPowerPillId;
    private boolean containsPacMan;
    private Set<Constants.GHOST> containedGhosts;

    public Node(Integer id)
    {
        this.setId(id);
        this.setContainedPillId(-1);
        this.setContainedPowerPillId(-1);
        this.setContainedGhosts(new HashSet<>());
        this.setContainsPacMan(false);
    }

    public void setContainedPillId(int containedPillId) {
        this.containedPillId = containedPillId;
    }

    public void setContainedPowerPillId(int containedPillId)
    {
        this.containedPillId = containedPillId;
    }

    public boolean containsPill() {
        return containedPillId >=0 ;
    }

    public boolean containsPowerPill() {
        return containedPowerPillId >= 0;
    }

    public int getContainedPillId() {
        return containedPillId;
    }

    public int getContainedPowerPillId() {
        return containedPowerPillId;
    }

    public boolean containsPacMan() {
        return containsPacMan;
    }

    public void setContainsPacMan(boolean containsPacMan) {
        this.containsPacMan = containsPacMan;
    }

    public Set<Constants.GHOST> getContainedGhosts() {
        return containedGhosts;
    }

    public void setContainedGhosts(Set<Constants.GHOST> containedGhosts) {
        this.containedGhosts = containedGhosts;
    }


    public Integer getId()
    {
        return this.id;
    }

    public void setId(Integer id)
    {
        this.id = id;
    }

    @Override
    public int hashCode()
    {
        return this.id.hashCode();
    }

    public void updatePillsInfo(Game game)
    {
        if(game.isNodeObservable(this.id))
        {
            int pillId = game.getPillIndex(this.id);
            if(pillId >= 0 && Commons.getBooleanValue(game.isPillStillAvailable(pillId)))
            {
                this.containedPillId = pillId;
            }
            else
            {
                this.containedPillId = -1;
            }

            int powerPillId = game.getPowerPillIndex(this.id);
            if(powerPillId >= 0 && Commons.getBooleanValue(game.isPowerPillStillAvailable(powerPillId)))
            {
                this.containedPowerPillId = powerPillId;
            }
            else
            {
                this.containedPowerPillId = game.getPowerPillIndex(this.id);
            }
        }
    }

    @Override
    public boolean equals(Object obj) {
        if (obj != null && obj.getClass().equals(this.getClass())) {
            Node n = (Node) obj;
            return n.getId().equals(this.getId());
        }
        return false;
    }

    public static Node getNodeById(Iterable<Node> nodes, Integer id)
    {
        for(Node node : nodes)
        {
            if(node.id.equals(id))
            {
                return node;
            }
        }
        return null;
    }
}
