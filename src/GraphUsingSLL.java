import java.util.ArrayList;

public class GraphUsingSLL {

    ArrayList<Vertex> arrayOfLists;
    int indexCounter = 0;
    boolean undirected = true;

    // Track interactions
    int davidAndKevinInteractions = 1;

    public GraphUsingSLL(int vertexCount, String graphType) {
        if(graphType.equals("directed")) {
            undirected = false;
        }
        arrayOfLists = new ArrayList<>();
    }

    public void addVertex(String vertexName) {
        arrayOfLists.add(new Vertex(vertexName, null));
    }

    public void addEdge(String sourceVertexName, String destinationVertexName) {
        int vertexOne = indexForName(sourceVertexName);
        int vertexTwo = indexForName(destinationVertexName);

//        System.out.println("Vertex 1 ID: " + vertexOne);
//        System.out.println("Vertex 2 ID: " + vertexTwo);

        if(edgeAlreadyExists(vertexOne, vertexTwo)){
           davidAndKevinInteractions++; //Connection already exists they have met again increase their count
        }

        arrayOfLists.get(vertexOne).adjList = new Node(vertexTwo, arrayOfLists.get(vertexOne).adjList);
        if(undirected) {
            arrayOfLists.get(vertexTwo).adjList = new Node(vertexOne, arrayOfLists.get(vertexTwo).adjList);
        }
    }

    public boolean edgeAlreadyExists(int sourceID, int destinationID) {
        Vertex source = arrayOfLists.get(sourceID);
        Vertex destination = arrayOfLists.get(destinationID);

        Vertex current = source;
        while (current.adjList != null) {
            if(source.adjList.next == destination.adjList) {
                return true;
            }
            current.adjList = current.adjList.next;
        }

        return false;
    }

    private int indexForName(String s) {
        // Search through array to find where the vertices exists and return index position of that given index
        for (int i = 0; i < arrayOfLists.size(); i++) {
            if(arrayOfLists.get(i).name.equals(s)) {
                return i;
            }
        }
        return -1;
    }

    public void print() {
        System.out.println();
        for (int i = 0; i < arrayOfLists.size(); i++) {
            System.out.print(arrayOfLists.get(i).name);
            for (Node node = arrayOfLists.get(i).adjList; node != null; node = node.next) {
                System.out.print("----> " + arrayOfLists.get(node.vertexIndex).name);
            }
            System.out.println("\n");
        }
    }
    //Vertex Class
    class Vertex {
        String name;
        Node adjList;
        Vertex(String name, Node node){
            this.name = name;
            adjList = node;
        }
    }
    //Custom Node class
    class Node {
        int vertexIndex;
        public Node next;
        public Node(int vertexIndex, Node node) {
            this.vertexIndex = vertexIndex;
            next = node;
        }
    }

    public static void main(String[] args) {
        GraphUsingSLL graph = new GraphUsingSLL(3, "directed");

        graph.addVertex("David");
        graph.addVertex("Kevin");

        graph.addEdge("David", "Kevin");
        graph.addEdge("David", "Kevin");
        graph.addEdge("David", "Kevin");
        graph.addEdge("Kevin", "David");
        System.out.println("Interactions between david and Kevin: " + graph.davidAndKevinInteractions);

        graph.print();
    }
}
