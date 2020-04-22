import java.util.ArrayList;

public class GraphFromUdemy {

    private int verticesCount; // Number of Vertices
    private int edgesCount; // Number of Edges

    private ArrayList[] adjacents; // Array of Integer Lists

    public GraphFromUdemy(int verticesCount) {
        this.verticesCount = verticesCount;
        this.edgesCount = 0;
        adjacents = new ArrayList[verticesCount];

        for (int i = 0; i < verticesCount; i++) {
            adjacents[i] = new ArrayList<Integer>();
        }
    }

    public int getVerticesCount() {
        return verticesCount;
    }

    public int getEdgesCount() {
        return edgesCount;
    }

    public void addEdge(int source, int destination) {
        adjacents[source].add(destination);
        edgesCount++;
    }

    //Method to show what is adjacent for a given vertex
    public Object[] adj(int source) {
        return adjacents[source].toArray();
    }
}
