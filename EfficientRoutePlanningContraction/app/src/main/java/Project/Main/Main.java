package Project.Main;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Scanner;

//import Project.Dijkstra.BidirectionalDijkstra;
import Project.Dijkstra.DijkstraUndirectedSP;
import Project.Graphs.EdgeWeightedGraph;
import Project.Graphs.GraphBuilder;
import Project.Graphs.MappingInverter;
import Project.Graphs.ShortcutAppender;

public class Main {

    public static void main(String[] args) throws FileNotFoundException {
        InputStream inputStreamTest = Main.class.getResourceAsStream("/denmark.graph"); ///Small_graph_for_test.graph /denmark.graph
                if (inputStreamTest == null) {
                    throw new FileNotFoundException("Resource 'Small_graph_for_test.graph' not found in classpath");
                }
                //GraphBuilderResult graphResultTest = GraphBuilder.buildGraphFromInputStream(inputStreamTest);
                System.out.println("Start");
                Long start1 = System.nanoTime();

                // Fpr dijstra test***
                EdgeWeightedGraph graph = GraphBuilder.buildGraphFromInputStream(inputStreamTest);

                DijkstraUndirectedSP dijkstra = new DijkstraUndirectedSP(graph);

                System.out.print("This is the result: ");
                System.out.println(dijkstra.computeShortestPath(171516, 244966));

                //***** 

                // ContractionHierarchy cont = new ContractionHierarchy(graphResultTest.getGraph());
                // LocalDijkstra4 ld = new LocalDijkstra4(graphResultTest.getGraph());
                // System.out.println("value 1 is: " + ld.computeEdgeDifference(18497, true));
                // System.out.println("value 5 is: " + ld.computeEdgeDifference(121887, true));
                // System.out.println("value 3 is: " + ld.computeEdgeDifference(121888, true));
                // System.out.println("value 1 is: " + ld.computeEdgeDifference(18497, true));
                // System.out.println("value 1 is: " + ld.computeEdgeDifference(488310, true));
                Long end1 = System.nanoTime();
                System.out.println((end1-start1)/1_000_000_000.0);

        // Make sure there are enough arguments before accessing them
        /* if (args.length == 0) {
            System.out.println("No arguments provided. Please specify 'Test', 'Dijkstra', or 'BiDijkstra'.");
            return; // Exit gracefully
        }

        // Example of handling different arguments
        for (int i = 0; i < args.length; i++) {
            if (args[i].equals("Test")) {
                System.out.println("Running Test...");
                // Load the graph from the resource
                // Test the graph using the existing logic
                long start = System.nanoTime();
                // LocalDijkstra3 ld = new LocalDijkstra3(graphResultTest.getGraph());
                // ld.computeEdgeDifference(13);
                // ContractionHierarchy cont = new ContractionHierarchy(graphResultTest.getGraph());
                // cont.print();
                long end = System.nanoTime();
                System.out.println((end - start) / 1_000_000_000.0);
                break; // If this argument was found, stop further checks

            }else if(args[i].equals("ContractionHierarchyTest")) {
                // ContractionHierarchy cont = new ContractionHierarchy(graphResultTest.getGraph());
            }
            
            else if (args[i].equals("Write")) {
                HashMap<Long, Integer> actualIDtoIndexMap = graphResultTest.getActualIDtoIndexMap();
                HashMap<Integer, Long> indexToActualIDMap = MappingInverter.invertMap(actualIDtoIndexMap);

                // Append shortcuts to the contracted graph file
                ShortcutAppender shortcutAppender = new ShortcutAppender();
                String shortcutsFilePath = "/Users/frederikkolbel/ITU/Third semester/Applied Algorithms/Hand-ins/Hand-in_3/Git folder/AppliedAlgorithmsAssignment3/EfficientRoutePlanningContraction/app/src/main/resources/shortcuts.graph"; // Path to your shortcuts file
                String contractedGraphFilePath = "/Users/frederikkolbel/ITU/Third semester/Applied Algorithms/Hand-ins/Hand-in_3/Git folder/AppliedAlgorithmsAssignment3/EfficientRoutePlanningContraction/app/src/main/resources/denmarkWithContractions.graph"; // Path to your contracted graph file
                shortcutAppender.appendShortcuts(shortcutsFilePath, contractedGraphFilePath, indexToActualIDMap);


            }
            else
                if (args[i].equals("Query")) {
                    InputStream inputStreamwithShortcuts = Main.class.getResourceAsStream("/denmarkWithContractions.graph"); ///Small_graph_for_test.graph
                    if (inputStreamTest == null) {
                        throw new FileNotFoundException("Resource 'Small_graph_for_test.graph' not found in classpath");
                    }
                    GraphBuilderResult graphResultforQuery = GraphBuilder.buildGraphFromInputStream(inputStreamwithShortcuts);

                    // need to get Rank(), which happens durinf contraction
                    // QueryBidirectionalDijkstra query = new QueryBidirectionalDijkstra(graphResultforQuery.getGraph(),rank);
                    // System.out.println(query.computeShortestPath(1, 50000));

                }
            
            else if (args[i].equals("Dijkstra")) {
                System.out.println("Running Dijkstra...");
                // Logic for 'Dijkstra'
                InputStream inputStreamDijkstra = Main.class.getResourceAsStream("/denmark.graph"); //
                if (inputStreamDijkstra == null) {
                    throw new FileNotFoundException("Resource 'denmark.graph' not found in classpath");
                }
                InputStream txtNode = Main.class.getResourceAsStream("/node_pairs.txt");
                if (txtNode == null) {
                    throw new FileNotFoundException("Resource 'node_pairs.txt' not found in classpath");//saving
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
                    
                    long startTime = System.nanoTime();
                    spGraph.computeShortestPath(startNode, endNode);
                    long endTime = System.nanoTime();

                    double duration = (endTime - startTime) / 1_000_000_000.0;
                    
                    //System.out.println(duration);
                    // see if can read the node pairs
                    // System.out.println("Dijkstra finished for nodes " + startNode + " and " + endNode);
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
                InputStream inputStreamBiDijkstra = Main.class.getResourceAsStream("/denmark.graph"); //
                if (inputStreamBiDijkstra == null) {
                    throw new FileNotFoundException("Resource 'denmark.graph' not found in classpath");
                }
                InputStream txtNode = Main.class.getResourceAsStream("/node_pairs.txt");
                if (txtNode == null) {
                    throw new FileNotFoundException("Resource 'node_pairs.txt' not found in classpath");
                }
                GraphBuilderResult graphResultBiDijkstra = GraphBuilder.buildGraphFromInputStream(inputStreamBiDijkstra);

                EdgeWeightedGraph graph = graphResultBiDijkstra.getGraph();

                BidirectionalDijkstra biGraph = new BidirectionalDijkstra(graph);

                //loading node pairs from txt file
                //File nodePairFile = new File("EfficientRoutePlanningContraction/app/src/main/resources/node_pairs.txt\"");
                Scanner scanner = new Scanner(txtNode);
                while (scanner.hasNextInt()) {
                    int startNode = scanner.nextInt();
                    int endNode = scanner.nextInt();
                    
                    long startTime = System.nanoTime();
                    biGraph.computeShortestPath(startNode, endNode);
                    long endTime = System.nanoTime();

                    double duration = (endTime - startTime) / 1_000_000_000.0;
                }

                scanner.close();
                break; // If this argument was found, stop further checks
            } else {
                System.out.println("Unknown argument: " + args[i]);
            }
        }
        */
    }
}