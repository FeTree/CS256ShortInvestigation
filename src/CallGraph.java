import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;

public class CallGraph {

    ArrayList<Vertex> arrayOfLists;
    boolean undirected = true;
    int maxEdges;

    // Track interactions
    //HashSet<Integer> interactions;
    //Since there are 3 people in our model, need to initialize 6 of these variables to track interactions among everybody
    int davidCoughedOnKevin;
    int davidCoughedOnRafael;
    int kevinCoughedOnDavid;
    int kevinCoughedOnRafael;
    int rafaelCoughedOnDavid;
    int rafaelCoughedOnKevin;

    public CallGraph(int vertexCount, String graphType) {
        if (graphType.equals("directed")) {
            undirected = false;
        }
        arrayOfLists = new ArrayList<>();
        maxEdges = 2 * (vertexCount * (vertexCount - 1) / 2);
        //To set up tracking interactions
        //interactions = new HashSet<>();
        davidCoughedOnKevin = 0;
        davidCoughedOnRafael = 0;
        kevinCoughedOnDavid = 0;
        kevinCoughedOnRafael = 0;
        rafaelCoughedOnDavid = 0;
        rafaelCoughedOnKevin = 0;
    }

    public void addVertex(String vertexName) {
        arrayOfLists.add(new Vertex(vertexName, null));
    }

    public void addEdge(String sourceVertexName, String destinationVertexName) {
        int vertexOne = indexForName(sourceVertexName);
        int vertexTwo = indexForName(destinationVertexName);

//        System.out.print("Vertex 1 ID: " + vertexOne);
//        System.out.println("\tVertex 2 ID: " + vertexTwo);

        // Main Functionality of the call graph, tracks how many times each vertex has coughed on another vertex
        if (vertexOne == 0 && vertexTwo == 1) {
            // Vertex 0 = David and Vertex 1 = Kevin, so increase their interaction
            //David --> Kevin
            davidCoughedOnKevin++;
        }
        if (vertexOne == 0 && vertexTwo == 2) {
            //David --> Rafael
            davidCoughedOnKevin++;
        }
        if (vertexOne == 1 && vertexTwo == 0) {
            // Vertex 1 = Kevin and Vertex 0 = David, so increase their interaction
            //Kevin --> David
            kevinCoughedOnDavid++;
        }
        if (vertexOne == 1 && vertexTwo == 2) {
            //Kevin --> Rafael
            kevinCoughedOnRafael++;
        }
        if (vertexOne == 2 && vertexTwo == 1) {
            //Rafael --> Kevin
            rafaelCoughedOnKevin++;
        }
        if (vertexOne == 2 && vertexTwo == 0) {
            //Rafael --> David
            rafaelCoughedOnDavid++;
        }
        arrayOfLists.get(vertexOne).adjList = new Node(vertexTwo, arrayOfLists.get(vertexOne).adjList); // Add edge
        if (undirected) {
            arrayOfLists.get(vertexTwo).adjList = new Node(vertexOne, arrayOfLists.get(vertexTwo).adjList); // Add to other edge since undirected
        }
    }

    public boolean edgeAlreadyExists(int sourceID, int destinationID) {
        Vertex source = arrayOfLists.get(sourceID);
        Vertex destination = arrayOfLists.get(destinationID);

        Vertex current = source;
        while (current.adjList != null) {
            if (source.adjList.next == destination.adjList) {
                return true;
            }
            current.adjList = current.adjList.next;
        }

        return false;
    }

    private int indexForName(String s) {
        // Search through array to find where the vertices exists and return index position of that given index
        for (int i = 0; i < arrayOfLists.size(); i++) {
            if (arrayOfLists.get(i).name.equals(s)) {
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

        Vertex(String name, Node node) {
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

    class Person {
        String name;
        int coughedOn;

        public Person(String name, int coughedOn) {
            this.name = name;
            this.coughedOn = coughedOn;
        }
    }

    public void determineMostLikelyToGetAVirus() {
        Person[] people = sortByCoughs(); // Array of people sorted by least coughed on to most coughed on

        for (int i = 0; i < people.length; i++) {
            System.out.println(people[i].name + " was coughed on " + people[i].coughedOn);
        }
        System.out.println("Therefore " + people[people.length - 1].name + " is most likely to be sick since he/she was coughed on " + people[people.length - 1].coughedOn + " times");
    }

    public Person[] sortByCoughs() {
        Person david = new Person("David", rafaelCoughedOnDavid + kevinCoughedOnDavid);
        Person kevin = new Person("Kevin", davidCoughedOnKevin + rafaelCoughedOnKevin);
        Person raffi = new Person("Rafael", kevinCoughedOnRafael + davidCoughedOnRafael);
        Person[] arr = {kevin, raffi, david};
        Person[] temp = new Person[15];
        if (arr[1].coughedOn < arr[0].coughedOn) {
            temp[0] = arr[0];
            arr[0] = arr[1];
            arr[1] = temp[0];
        }
        if (arr[2].coughedOn < arr[1].coughedOn) {
            temp[0] = arr[1];
            arr[1] = arr[2];
            arr[2] = temp[0];
        }
        if (arr[1].coughedOn < arr[0].coughedOn) {
            temp[0] = arr[0];
            arr[0] = arr[1];
            arr[1] = temp[0];
        }
        return arr;
    }

    public static void main(String[] args) {
        GraphUsingSLL graph = new GraphUsingSLL(3, "directed");

        //Add people
        graph.addVertex("David");
        graph.addVertex("Kevin");
        graph.addVertex("Rafael");

        // David has coughed on Kevin 3 times here
        graph.addEdge("David", "Kevin");
        graph.addEdge("David", "Kevin");
        graph.addEdge("David", "Kevin");

        // Kevin has coughed on David once
        graph.addEdge("Kevin", "David");
        graph.addEdge("Kevin", "David");


        // Rafael has coughed on kevin 5 times here
        graph.addEdge("Rafael", "Kevin");
        graph.addEdge("Rafael", "Kevin");
        graph.addEdge("Rafael", "Kevin");
        graph.addEdge("Rafael", "Kevin");
        graph.addEdge("Rafael", "Kevin");

        //Print Graph
        graph.print();

        //Determine who is most likely to get a virus
        graph.determineMostLikelyToGetAVirus();
    }
}

