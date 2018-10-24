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
    private boolean containsPill;
    private boolean containsPowerPill;
    private boolean containsPacMan;
    private Set<Constants.GHOST> containedGhosts;

    public Node(Integer id)
    {
        this.setId(id);
        this.setContainsPill(false);
        this.setContainsPowerPill(false);
        this.setContainedGhosts(new HashSet<>());
        this.setContainsPacMan(false);
    }

    public void setContainsPill(boolean containsPill) {
        this.containsPill = containsPill;
    }

    public void setContainsPowerPill(boolean containsPowerPill) {
        this.containsPowerPill = containsPowerPill;
    }

    public boolean containsPill() {
        return containsPill;
    }

    public boolean containsPowerPill() {
        return containsPowerPill;
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

    @Override
    public boolean equals(Object o)
    {
        if(o instanceof Node)
        {
            return ((Node)o).id.equals(this.id);
        }
        return false;
    }

    public void updatePillsInfo(Game game)
    {
        this.containsPill = Commons.getBooleanValue(game.isPillStillAvailable(game.getPillIndex(this.id)));
    }

}
