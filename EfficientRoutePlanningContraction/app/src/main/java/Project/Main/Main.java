package Project.Main;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Scanner;

import Project.Contraction.ContractionHierarchy;
import Project.Dijkstra.BidirectionalDijkstra;
import Project.Dijkstra.DijkstraUndirectedSP;
import Project.Graphs.EdgeWeightedGraph;
import Project.Graphs.GraphBuilder;
import Project.Graphs.GraphBuilderResult;

public class Main {

    public static void main(String[] args) throws FileNotFoundException {
        // Make sure there are enough arguments before accessing them
        if (args.length == 0) {
            System.out.println("No arguments provided. Please specify 'Test', 'Dijkstra', or 'BiDijkstra'.");
            return; // Exit gracefully
        }

        // Example of handling different arguments
        for (int i = 0; i < args.length; i++) {
            if (args[i].equals("Test")) {
                System.out.println("Running Test...");
                // Load the graph from the resource
                InputStream inputStreamTest = Main.class.getResourceAsStream("/denmark.graph"); ///Small_graph_for_test.graph
                if (inputStreamTest == null) {
                    throw new FileNotFoundException("Resource 'Small_graph_for_test.graph' not found in classpath");
                }
                GraphBuilderResult graphResultTest = GraphBuilder.buildGraphFromInputStream(inputStreamTest);

                // Test the graph using the existing logic
                long start = System.nanoTime();
                // LocalDijkstra3 ld = new LocalDijkstra3(graphResultTest.getGraph());
                // ld.computeEdgeDifference(13);
                ContractionHierarchy cont = new ContractionHierarchy(graphResultTest.getGraph());
                long end = System.nanoTime();
                System.out.println((end - start) / 1_000_000_000.0);
                break; // If this argument was found, stop further checks

            } else if (args[i].equals("Dijkstra")) {
                System.out.println("Running Dijkstra...");
                // Logic for 'Dijkstra'
                InputStream inputStreamDijkstra = Main.class.getResourceAsStream("/denmark.graph"); //
                if (inputStreamDijkstra == null) {
                    throw new FileNotFoundException("Resource 'denmark.graph' not found in classpath");
                }
                InputStream txtNode = Main.class.getResourceAsStream("/node_pairs.txt");
                if (txtNode == null) {
                    throw new FileNotFoundException("Resource 'node_pairs.txt' not found in classpath");//saving main just in case
                }
                GraphBuilderResult graphResultDijkstra = GraphBuilder.buildGraphFromInputStream(inputStreamDijkstra);

                EdgeWeightedGraph graph = graphResultDijkstra.getGraph();

                DijkstraUndirectedSP spGraph = new DijkstraUndirectedSP(graph);

                //loading node pairs from txt file
                //File nodePairFile = new File("EfficientRoutePlanningContraction/app/src/main/resources/node_pairs.txt\"");
                Scanner scanner = new Scanner(txtNode);
                while (scanner.hasNextInt()) {
                    int startNode = scanner.nextInt();
                    int endNode = scanner.nextInt();
                    
                    spGraph.computeShortestPath(startNode, endNode);
                    // see if can read the node pairs
                    System.out.println("Dijkstra finished for nodes " + startNode + " and " + endNode);
                }
                
                scanner. close();
                break;

                // try (Scanner scanner = new Scanner(graphResultDijkstra)) {
                //     // Your code to read the start and end nodes from System.in
                //     if (scanner.hasNextLong()) {
                //         int startNode = scanner.nextInt();
                //         if (scanner.hasNextInt()) {
                //             int endNode = scanner.nextInt();
                //             // Proceed with Dijkstra algorithm
                //         } else {
                //             System.out.println("Error: End node input is missing or invalid.");
                //         }
                //     } else {
                //         System.out.println("Error: Start node input is missing or invalid.");
                //     }
                // } catch (NumberFormatException e) {
                //     System.out.println("Error: Invalid input format. Please enter two integer node values.");
                // }
                // break; // If this argument was found, stop further checks

            } else if (args[i].equals("BiDijkstra")) {
                System.out.println("Running BiDijkstra...");
                // Logic for 'BiDijkstra'
                InputStream inputStreamBiDijkstra = Main.class.getResourceAsStream("/denmark.graph");
                if (inputStreamBiDijkstra == null) {
                    throw new FileNotFoundException("Resource 'denmark.graph' not found in classpath");
                }
                GraphBuilderResult graphResultBiDijkstra = GraphBuilder
                        .buildGraphFromInputStream(inputStreamBiDijkstra);

                Scanner scanner = new Scanner(System.in);
                for (int j = 0; j < 1000; j++) {
                    BidirectionalDijkstra biDijkstra = new BidirectionalDijkstra(graphResultBiDijkstra.getGraph());

                    int startNode = scanner.nextInt();
                    int endNode = scanner.nextInt();

                    long startTime = System.nanoTime();
                    biDijkstra.computeShortestPath(startNode, endNode);
                    long endTime = System.nanoTime();

                    float totalTime = (endTime - startTime) / 1_000_000_000.0f;
                    System.out.println("BiDijkstra " + biDijkstra.getCounterRelaxed() + " " + totalTime);
                }
                scanner.close();
                break; // If this argument was found, stop further checks
            } else {
                System.out.println("Unknown argument: " + args[i]);
            }
        }
    }
}