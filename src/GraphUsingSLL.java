public class GraphUsingSLL {

    Vertex[] arrayOfLists;
    int indexCounter = 0;
    boolean undirected = true;

    public GraphUsingSLL(int vertexCount, String graphType) {
        if(graphType.equals("directed")) {
            undirected = false;
        }
        arrayOfLists = new Vertex[vertexCount];
    }

    public void addVertex(String vertexName) {
        arrayOfLists[indexCounter] = new Vertex(vertexName, null);
    }

    public void addEdge(String sourceVertexName, String destinationVertexName) {
        int vertexOne = indexForName(sourceVertexName);
        int vertexTwo = indexForName(destinationVertexName);

        arrayOfLists[vertexOne].adjList = new Node(vertexTwo, arrayOfLists[vertexOne].adjList);
        if(undirected) {
            arrayOfLists[vertexTwo].adjList = new Node(vertexOne, arrayOfLists[vertexTwo].adjList);
        }
    }

    private int indexForName(String s) {
        // Search through array to find where the vertices exists and return index position of that given index
        for (int i = 0; i < arrayOfLists.length; i++) {
            if(arrayOfLists[i].name.equals(s)) {
                return i;
            }
        }
        return -1;
    }

    public void print() {
        System.out.println();
        for (int i = 0; i < arrayOfLists.length; i++) {
            System.out.println(arrayOfLists[i].name);
            for (Node node = arrayOfLists[i].adjList; node != null; node = node.next) {
                System.out.println("----->" + arrayOfLists[node.vertexIndex].name);
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
        GraphUsingSLL graph = new GraphUsingSLL(3, "undirected");

        graph.addVertex("David");
        graph.addVertex("Kevin");
        graph.addVertex("Adam");


        graph.addEdge("David", "Adam");



    }
}
