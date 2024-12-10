package Project.Main;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Scanner;

import Project.Contraction.ContractionHierarchy;
import Project.Dijkstra.BidirectionalDijkstra;
import Project.Dijkstra.DijkstraUndirectedSP;
import Project.Dijkstra.QueryBidirectionalDijkstra;
import Project.Graphs.EdgeWeightedGraph;
import Project.Graphs.GraphBuilder;


public class Main {

    public static void main(String[] args) throws FileNotFoundException {
        InputStream inputStreamTest = Main.class.getResourceAsStream("/denmark.graph"); // /Small_graph_for_test.graph /denmark.graph
                if (inputStreamTest == null) {
                    throw new FileNotFoundException("Resource 'Small_graph_for_test.graph' not found in classpath");
                }
                EdgeWeightedGraph graph = GraphBuilder.buildGraphFromInputStream(inputStreamTest); 
                
                // Make sure there are enough arguments before accessing them
                // Example of handling different arguments
                if (args.length == 0) {
                    System.out.println("No arguments provided. Please specify 'Test', 'Dijkstra', or 'BiDijkstra'.");
                    return; // Exit gracefully

                }else if (args[0].equals("ContractionHierarchy")){
                    //Create the contraction hierarchy
                    Long start1 = System.nanoTime();
                    ContractionHierarchy cont = new ContractionHierarchy(graph);
                    Long end1 = System.nanoTime();
                    System.out.println((end1-start1)/1_000_000_000.0);
                    // QueryBidirectionalDijkstra qDijkstra = new QueryBidirectionalDijkstra(graph);
                    // qDijkstra.computeShortestPath(0, 0)
                    GraphBuilder.writeContractedGraphToFile(graph, "/Users/frederikkolbel/ITU/Third semester/Applied Algorithms/Hand-ins/Hand-in_3/Git folder/AppliedAlgorithmsAssignment3/EfficientRoutePlanningContraction/app/src/main/resources/denmarkWithOurContractions.graph");
                }
                else if(args[0].equals("Dijkstra")){

                DijkstraUndirectedSP spGraph = new DijkstraUndirectedSP(graph);
                Scanner scanner = new Scanner(System.in);
                while (scanner.hasNextInt()) {
                    int startNode = scanner.nextInt();
                    int endNode = scanner.nextInt();
                    
                    long startTime = System.nanoTime();
                    Double distance = spGraph.computeShortestPath(startNode, endNode);
                    long endTime = System.nanoTime();

                    double duration = (endTime - startTime) / 1_000_000_000.0;
                    System.out.println(spGraph.getCounterRelaxed() + " " + duration + " " + distance);
                }
                scanner. close();
                }
                else if(args[0].equals("BiDijkstra")){
                    //Bidijkstra run
                BidirectionalDijkstra spGraph = new BidirectionalDijkstra(graph);
                Scanner scanner = new Scanner(System.in);
                while (scanner.hasNextInt()) {
                    int startNode = scanner.nextInt();
                    int endNode = scanner.nextInt();
                    
                    long startTime = System.nanoTime();
                    Double distance = spGraph.computeShortestPath(startNode, endNode);
                    long endTime = System.nanoTime();

                    double duration = (endTime - startTime) / 1_000_000_000.0;
                    System.out.println(spGraph.getCounterRelaxed() + " " + duration + " " + distance);
                }
                scanner. close();
                }else if (args[0].equals("QueryDijkstra")) {

                    //Need to fix how to give the graph int[] rank

                    // int[] rank = graph.readRankArrayFromFile("/Users/frederikkolbel/ITU/Third semester/Applied Algorithms/Hand-ins/Hand-in_3/Git folder/AppliedAlgorithmsAssignment3/EfficientRoutePlanningContraction/app/src/main/resources/ranks.txt");
                    EdgeWeightedGraph contractedGraph = GraphBuilder.readContractedGraphFromFile("/Users/frederikkolbel/ITU/Third semester/Applied Algorithms/Hand-ins/Hand-in_3/Git folder/AppliedAlgorithmsAssignment3/EfficientRoutePlanningContraction/app/src/main/resources/denmarkWithOurContractions.graph");
                    // try {
                    //     contractedGraph =  GraphBuilder.addShortcuts(graph, "/Users/frederikkolbel/ITU/Third semester/Applied Algorithms/Hand-ins/Hand-in_3/Git folder/AppliedAlgorithmsAssignment3/EfficientRoutePlanningContraction/app/src/main/resources/shortcuts4.graph");
                    // } catch (Exception e) {
                    //     System.out.println("contracted graph not instantiated");
                    // }
                    //contractedGraph.setRankArray(rank);
                    System.out.println(contractedGraph.V());
                    
                    System.out.println(contractedGraph.E());
                    Scanner scanner = new Scanner(System.in);
                    while (scanner.hasNextInt()) {
                        QueryBidirectionalDijkstra spGraph = new QueryBidirectionalDijkstra(contractedGraph);
                        int startNode = scanner.nextInt();
                        int endNode = scanner.nextInt();
                        
                        long startTime = System.nanoTime();
                        Double distance = spGraph.computeShortestPath(startNode,endNode);
                        long endTime = System.nanoTime();
    
                        double duration = (endTime - startTime) / 1_000_000_000.0;
                    System.out.println(spGraph.getCounterRelaxed() + " " + duration + " " + distance);
                    }
                    scanner. close();
                }
            }
        }