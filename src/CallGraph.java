import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;

public class CallGraph {
    private int verticesCount; // Number of Vertices
    private int edgesCount; // Number of Edges

    private ArrayList[] adjacents; // Array of Integer Lists

    public CallGraph(int verticesCount) {
        this.verticesCount = verticesCount;
        this.edgesCount = 0;
        adjacents = new ArrayList[verticesCount];

        for (int i = 0; i < verticesCount ; i++) {
            adjacents[i] = new ArrayList<Integer>();
        }
    }
}
