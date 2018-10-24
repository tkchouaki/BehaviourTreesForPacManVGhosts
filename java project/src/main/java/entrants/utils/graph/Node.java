package entrants.utils.graph;

import pacman.game.Constants;
import pacman.game.Game;
import pacman.game.info.GameInfo;
import pacman.game.internal.Maze;

import java.util.HashSet;
import java.util.Set;

public class Node {
    private String id;
    private boolean containsPill;
    private boolean containsPowerPill;
    private boolean containsPacMan;
    private Set<Constants.GHOST> containedGhosts;

    public Node(String id)
    {
        this.setId(id);
        this.containsPill = false;
        this.containsPowerPill = false;
        this.containedGhosts = new HashSet<>();
    }

    public boolean containsPill() {
        return containsPill;
    }

    public void setContainsPill(boolean containsPill) {
        this.containsPill = containsPill;
    }

    public boolean containsPowerPill() {
        return containsPowerPill;
    }

    public void setContainsPowerPill(boolean containsPowerPill) {
        this.containsPowerPill = containsPowerPill;
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

    public String getId()
    {
        return this.id;
    }

    public void setId(String id)
    {
        this.id = id.toLowerCase();
    }

    @Override
    public int hashCode()
    {
        return this.id.hashCode();
    }

    public void addInfo(GameInfo gameInfo, Maze maze, Game game)
    {
        pacman.game.internal.Node node = maze.graph[0];

    }
}
