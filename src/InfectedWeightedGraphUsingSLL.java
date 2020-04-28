/*
Author: David Eisenbaum
Due date: May 4, 2019
Class: COMP256
 */
import java.util.*;

public class InfectedWeightedGraphUsingSLL {
    //Graph traits
    ArrayList<Vertex> arrayOfLists;
    ArrayList<String> names;

    Random random = new Random();

    //Store infected people
    ArrayList<Vertex> infectedPeople = new ArrayList<>();
    //Store those likely to be infected (have been coughed on more than 6 times)
    ArrayList<Vertex> likelyToBeInfectedPeople = new ArrayList<>();

    public InfectedWeightedGraphUsingSLL(int vertices, int infectedNumber, int socialDistancing) {
        this.arrayOfLists = new ArrayList<>();

        //Generate Names for each vertices
        names = new ArrayList<>();
        for (int i = 0; i < vertices; i++) {
            names.add("person" + i);
            addVertex(names.get(i)); // add to vertex list
        }
        //Randomly assign however many infected user chose
        for (int i = 0; i < infectedNumber ; i++) {
            int randomIndex = random.nextInt(arrayOfLists.size() - 1);
            arrayOfLists.get(randomIndex).isInfected = true;
        }

        int maxEdges = vertices * 4; // This number simulates how social the people are (lower means social distancing, higher means people are very social)
        if(socialDistancing == 1) {
            maxEdges = maxEdges / 3; // Chop interactions by a third if they are social distancing
        }

        //Randomly Assign Edges
        int randomNumberOfEdges = random.nextInt(maxEdges);
        for (int i = 0; i < randomNumberOfEdges; i++) {
            int max = arrayOfLists.size() - 1;
            int randomIndexForSource = random.nextInt(max);
            int randomIndexForDestination = random.nextInt(max);
            addEdge(arrayOfLists.get(randomIndexForSource).name, arrayOfLists.get(randomIndexForDestination).name);
        }
    }

    //Randomly Assign Edges

    //Vertex Class
    class Vertex {
        String name;
        Edge adjList;
        boolean isInfected = false;

        Vertex(String name, Edge node) {
            this.name = name;
            adjList = node;
        }
    }

    //Weighted Edge graph
    class Edge {
        int weight;
        int vertexIndex;
        public Edge next;

        public Edge(int vertexIndex, int weight, Edge node) {
            this.vertexIndex = vertexIndex;
            this.weight = weight;
            next = node;
        }
    }

    public void addVertex(String vertexName) {
        arrayOfLists.add(new Vertex(vertexName, null));
    }
    public void addEdge(String sourceVertexName, String destinationVertexName) {
        int vertexOne = indexForName(sourceVertexName);
        int vertexTwo = indexForName(destinationVertexName);

        //Generate Random Weight
        int maxWeight = 10;
        int randomizeWeight = random.nextInt(maxWeight);

        // Check and add those likely to be infected
        Vertex source = arrayOfLists.get(vertexOne);
        Vertex destination = arrayOfLists.get(vertexTwo);
        if(randomizeWeight >= 7 && arrayOfLists.get(vertexOne).isInfected){
            if(!likelyToBeInfectedPeople.contains(vertexTwo) && !infectedPeople.contains(vertexOne)) {
                likelyToBeInfectedPeople.add(arrayOfLists.get(vertexTwo));
            }
        }
        // No self loops
        if(source.name.equals(destination.name)) {
            return;
        }
        //directed insert
        arrayOfLists.get(vertexOne).adjList = new Edge(vertexTwo, randomizeWeight, arrayOfLists.get(vertexOne).adjList);
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
            //Add to infected list
            if(arrayOfLists.get(i).isInfected){
                infectedPeople.add(arrayOfLists.get(i));
            }

            for (Edge edge = arrayOfLists.get(i).adjList; edge != null; edge = edge.next) {
                // If current person is infected
                if(arrayOfLists.get(i).isInfected) {
                    System.out.print("---" + edge.weight+ "--->" + arrayOfLists.get(edge.vertexIndex).name);
                }else {
                    System.out.print("---" + edge.weight+ "--->" + arrayOfLists.get(edge.vertexIndex).name);
                }
            }
            System.out.println("\n");
        }
        //Print out infected people
        System.out.print("Infected People: ");
        for (int i = 0; i < infectedPeople.size(); i++) {
            System.out.print(infectedPeople.get(i).name + " ");
        }

        //Print out those likely infected
        System.out.print("\nAre likely infected: ");
        for (int i = 0; i <likelyToBeInfectedPeople.size() ; i++) {
            System.out.print(likelyToBeInfectedPeople.get(i).name + " ");
        }
    }

    public static void main(String[] args) {
        // Prompt user for input
        Scanner s = new Scanner(System.in);
        int vertices = 0;
        int infected = 0;
        int socialDistancing = 0;
        try {
            System.out.println("Enter Number of People: ");
            vertices = s.nextInt();
            System.out.println("Enter number of infected people");
            infected = s.nextInt();
            System.out.println("Are these people properly social distancing? Enter 1 for yes, Enter any other number for no.");
            socialDistancing = s.nextInt();
        }
        catch (Exception e){
            System.out.println(e);
            System.out.println("Invalid input");
        }

        //Initialize Graph with user input
        InfectedWeightedGraphUsingSLL graph = new InfectedWeightedGraphUsingSLL(vertices , infected, socialDistancing);

        //Print details about generated graph
        graph.print();
    }
}
