package entrants.utils.graph;

public class Node {
    private String id;

    public Node(String id)
    {
        this.setId(id);
    }


    public void setId(String id)
    {
        this.id = id.toLowerCase();
    }

    public String getId()
    {
        return this.id;
    }
}
