package entrants.utils.graph;

import entrants.utils.Commons;
import pacman.game.Constants;
import pacman.game.Game;
import pacman.game.info.GameInfo;
import pacman.game.internal.Maze;

import java.util.HashSet;
import java.util.Set;

public class Node {
    private String id;
    private int containedPillId;
    private int containedPowerPillId;
    private boolean containsPacMan;
    private Set<Constants.GHOST> containedGhosts;

    public Node(String id)
    {
        this.setId(id);
        this.containedPillId = -1;
        this.containedPowerPillId = -1;
        this.containedGhosts = new HashSet<>();
    }

    public boolean containsPill() {
        return containedPillId >= 0;
    }

    public boolean containsPowerPill() {
        return containedPowerPillId >= 0;
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

    public int getContainedPillId() {
        return containedPillId;
    }

    public void setContainedPillId(int containedPillId) {
        this.containedPillId = containedPillId;
    }

    public int getContainedPowerPillId() {
        return containedPowerPillId;
    }

    public void setContainedPowerPillId(int containedPowerPillId) {
        this.containedPowerPillId = containedPowerPillId;
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
