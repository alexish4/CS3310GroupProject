import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class TSPSortedEdges {

    // Static class for edge
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

    // Static class for city
    static class City {
        String cityName;

        public City(String cityName) {
            this.cityName = cityName;
        }
    }

    // A Static class that can be use to sort 
    //  arrays from smallest to biggest
    static class SortByCost implements Comparator<Edge> {
        public int compare(Edge a, Edge b) {
            if ( a.weight < b.weight ) return -1;
            else if ( a.weight == b.weight ) return 0;
            else return 1;
        }
    }

    // Static class for the graph
    static class Graph {
        int vertices;
        ArrayList<Edge> allEdges = new ArrayList<>();
        ArrayList<Edge> path = new ArrayList<>();
        ArrayList<City> allCities = new ArrayList<>();
        int visited[];
        int totalCost;
        Edge currentEdge;

        Graph(int vertices) {
            this.vertices = vertices;
        }

        // A function that use the sorted edges algorithm
        //  to find a hamiltonian circuit
        public void sortedEdges() {
            //Keep track of visited city
            visited = new int[vertices + 1];

            // Using SortByCost class to sort the arrays
            Edge[] edges = new Edge[allEdges.size()];
            allEdges.toArray(edges);
            Arrays.sort(edges, new SortByCost());

            // Copy the array into a Array list
            allEdges = new ArrayList<Edge>(Arrays.asList(edges));

            // Variables that keep count
            int visit, count, currentSRC, currentDST, currentWT, index;
            visit = count = currentSRC = currentDST = currentWT = index = 0;

            // Initialize all city visited to 0
            for (int i = 0; i < visited.length; i++) {
                visited[i] = 0;
            }

            // While loop until all city is visit
            while(visit != (vertices + 1)) {
                for (int i = 0; i < allEdges.size(); i++) {
                    currentEdge = allEdges.get(i);
                    
                    // Check whether a city is visited twice
                    if (visited[currentEdge.source] < 2 
                        && visited[currentEdge.destination] < 2) {
                        visited[currentEdge.source] += 1;
                        visited[currentEdge.destination] += 1;
                        path.add(currentEdge);
                        currentSRC = currentEdge.source;
                        currentDST = currentEdge.destination;
                        currentWT = currentEdge.weight;
                        index = i;
                        totalCost += currentEdge.weight;
                    }
                }

                // Check if all city is visited twice
                if (visit == vertices) {
                    for (int j = 0; j < visited.length; j++) {
                        if (visited[j] == 2) {
                            count++;
                        }
                    }

                    // If not all city is visited move to
                    //  the next cheapest edge
                    if (count < vertices) {
                        System.out.println(currentSRC);
                        allEdges.remove(index);
                        visited[currentSRC] -= 1;
                        visited[currentDST] -= 1;
                        totalCost -= currentWT;
                        path.remove(count-1);
                        --visit;
                    }
                }
                visit++;
            }

            // Display result
            printPath(path);
        }

        // Function that add edges from input file
        public void addEdge(int source, int destination, int weight) {

            Edge edge = new Edge(source, destination, weight);
            allEdges.add(edge); // add to Array list
        }

        // Function that add city from input file
        public void addCity(String cityName) {
            City city = new City(cityName);
            allCities.add(city); // add to Array list
        }

        // Function that print out Hamiltonian Circuit
        public void printPath(ArrayList<Edge> edgeList) {

            // Print the path using sort edges algorithm
            System.out.print("\nHamiltonian Circuit Using Sorted Edges: \n\n");
            for (int i = 0; i < edgeList.size(); i++) {
                Edge edge = edgeList.get(i);
                City begin = allCities.get(edge.source);
                City end = allCities.get(edge.destination);
                System.out.print(begin.cityName + " --> " + end.cityName
                                + ": " + edge.weight);
                System.out.println("\n");
            }
            System.out.println("\nTotal Cost: " + totalCost);
        }

    }

    public static void main(String[] args) throws IOException {

        // Declare int variables
        int vertices, tmpV1, tmpV2, weight;
        String[] tmpCity;
        String[] line;
        
        // Retrieving data from input file and pass into graph
        try {
            File file = new File("input.txt");
            Scanner sc = new Scanner(file);
            
            vertices = Integer.parseInt(sc.nextLine());

            Graph graph = new Graph(vertices);

            tmpCity = sc.nextLine().split(",");
            for (int i = 0; i < tmpCity.length; i++) {
                graph.addCity(tmpCity[i]);
            }

            while (sc.hasNextLine()) {
                line = sc.nextLine().split(" ");
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