import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class TSPSortedEdges {
    static class Edge {
        int source;
        int destination;
        int weight;
       
        public Edge(int source, int destination, int weight) {
            this.source = source;
            this.destination = destination;
            this.weight = weight;
        }
    }

    static class SortByCost implements Comparator<Edge> {
        public int compare(Edge a, Edge b) {
            if ( a.weight < b.weight ) return -1;
            else if ( a.weight == b.weight ) return 0;
            else return 1;
        }
    }

    static class Graph {
        int vertices;
        ArrayList<Edge> allEdges = new ArrayList<>();
        ArrayList<Edge> path = new ArrayList<>();
        int visited[];
        int totalCost;
        Edge currentEdge;

        Graph(int vertices) {
            this.vertices = vertices;
        }

        public void sortedEdges() {
            visited = new int[vertices + 1];
            Edge[] edges = new Edge[allEdges.size()];
            allEdges.toArray(edges);
            Arrays.sort(edges, new SortByCost());
            allEdges = new ArrayList<Edge>(Arrays.asList(edges));
            int visit = 0;
            int count = 0;
            boolean allVisit = true;
            // boolean allVisit = false;

            for (int i = 0; i < visited.length; i++) {
                visited[i] = 0;
            }

            // currentEdge = allEdges.get(0);
            // visited[currentEdge.source] += 1;
            // path.add(currentEdge);

            while(allVisit != false) {
                for (int i = 0; i < allEdges.size(); i++) {
                    currentEdge = allEdges.get(i);
                    if (visited[currentEdge.source] < 2 
                        && visited[currentEdge.destination] < 2) {
                        visited[currentEdge.source] += 1;
                        visited[currentEdge.destination] += 1;
                        path.add(currentEdge);
                        totalCost += currentEdge.weight;
                    }
                    for (int j = 0; j < visited.length; j++) {
                        if (visited[j] == 2) {
                            count++;
                        }
                        if (count == visited.length) {
                            break;
                        }
                    }
                    count = 0;
                }
                visit++;
            }


            //Initialize the graph with the first edge

            //Select the next cheapest path

            //While selecting the next cheapest path check 2 condition: 
            // 1. make sure the city is not visted 3 time
            // 2. make sure the all city is visited before
            //    a circuit is formed
            printPath(path);
        }

        // Function that add edges from input file
        public void addEdge(int source, int destination, int weight) {

            Edge edge = new Edge(source, destination, weight);
            allEdges.add(edge); //add to total edges
        }

        public void printPath(ArrayList<Edge> edgeList) {
            // print the path using sort edges algorithm
            System.out.print("Sorted Weight: \n");
            for (int i = 0; i < edgeList.size(); i++) {
                Edge edge = edgeList.get(i);
                System.out.print(edge.source + "-->" + edge.destination
                                + ": " + edge.weight);
                System.out.println();
            }
            System.out.println("Total Cost: " + totalCost);
        }

    }

    public static void main(String[] args) throws IOException {
        // Declare int variables
        int vertices, tmpV1, tmpV2, weight;
        
        // Retrieving data from input file and pass into graph
        try {
            File file = new File("input.txt");
            Scanner sc = new Scanner(file);
            
            vertices = Integer.parseInt(sc.nextLine());

            Graph graph = new Graph(vertices);

            while (sc.hasNextLine()) {
                String[] line = sc.nextLine().split(" ");
                tmpV1 = Integer.parseInt(line[0]);
                tmpV2 = Integer.parseInt(line[1]);
                weight = Integer.parseInt(line[2]);
                graph.addEdge(tmpV1, tmpV2, weight);
            }

            // Close scanner
            sc.close();

            graph.sortedEdges();

        } catch (IOException e) {

            // Catch exception if file doesn't exist
            System.exit(0);
        }
    }
}