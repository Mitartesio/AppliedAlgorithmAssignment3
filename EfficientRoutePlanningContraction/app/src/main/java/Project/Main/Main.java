package Project.Main;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Scanner;

import Project.Contraction.ContractionHierarchy;
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

                Long start1 = System.nanoTime();
                    ContractionHierarchy cont = new ContractionHierarchy(graph);
                    Long end1 = System.nanoTime();
                    System.out.println((end1-start1)/1_000_000_000.0);

                    DijkstraUndirectedSP dj = new DijkstraUndirectedSP(graph);
                    QueryBidirectionalDijkstra query = new QueryBidirectionalDijkstra(graph);

                    System.out.println("normal dijkstra shortest path: " + dj.computeShortestPath(graph.getVertexById(445047695L).getVertexIndex(),graph.getVertexById(7449627L).getVertexIndex()));
                    System.out.println("relaxations: " + dj.getCounterRelaxed());
                    System.out.println("query dijkstra shortest path: " + query.computeShortestPath(graph.getVertexById(445047695L).getVertexIndex(),graph.getVertexById(7449627L).getVertexIndex()));
                    System.out.println("relaxations: " + query.getCounterRelaxed());
                
                // Make sure there are enough arguments before accessing them
                // Example of handling different arguments
                if (args.length == 0) {
                    System.out.println("No arguments provided. Please specify 'Test', 'Dijkstra', or 'BiDijkstra'.");
                    return; // Exit gracefully

                }else if (args[0].equals("ContractionHierarchy")){
                    //Create the contraction hierarchy
                    // Long start1 = System.nanoTime();
                    // ContractionHierarchy cont = new ContractionHierarchy(graph);
                    // Long end1 = System.nanoTime();
                    // System.out.println((end1-start1)/1_000_000_000.0);

                    // DijkstraUndirectedSP dj = new DijkstraUndirectedSP(graph);
                    // QueryBidirectionalDijkstra query = new QueryBidirectionalDijkstra(graph);

                    // System.out.println("normal dijkstra shortest path: " + dj.computeShortestPath(graph.getVertexById(252531593L).getVertexIndex(),graph.getVertexById(3329107267L).getVertexIndex()));
                    // System.out.println("relaxations: " + dj.getCounterRelaxed());
                    // System.out.println("query dijkstra shortest path: " + query.computeShortestPath(graph.getVertexById(252531593L).getVertexIndex(),graph.getVertexById(3329107267L).getVertexIndex()));
                    // System.out.println("relaxations: " + query.getCounterRelaxed());

                }
                else if(args[0].equals("Dijkstra")){

                DijkstraUndirectedSP spGraph = new DijkstraUndirectedSP(graph);
                Scanner scanner = new Scanner(System.in);
                while (scanner.hasNextInt()) {
                    int startNode = scanner.nextInt();
                    int endNode = scanner.nextInt();
                    
                    long startTime = System.nanoTime();
                    spGraph.computeShortestPath(startNode, endNode);
                    long endTime = System.nanoTime();

                    double duration = (endTime - startTime) / 1_000_000_000.0;
                    System.out.println(spGraph.getCounterRelaxed() + " " + duration);
                }
                scanner. close();
                }
                else if(args[0].equals("BiDijkstra")){
                    //Bidijkstra run
                // BidirectionalDijkstra spGraph = new BidirectionalDijkstra(graph);
                Scanner scanner = new Scanner(System.in);
                while (scanner.hasNextInt()) {
                    int startNode = scanner.nextInt();
                    int endNode = scanner.nextInt();
                    
                    long startTime = System.nanoTime();
                    // spGraph.computeShortestPath(startNode, endNode);
                    long endTime = System.nanoTime();

                    double duration = (endTime - startTime) / 1_000_000_000.0;
                    // System.out.println(spGraph.getCounterRelaxed() + " " + duration);
                }
                scanner. close();
                }else if (args[0].equals("QueryDijkstra")) {

                    //Need to fix how to give the graph int[] rank

                    // QueryBidirectionalDijkstra spGraph = new QueryBidirectionalDijkstra(graphResultTest.getGraph(), );
                    // Scanner scanner = new Scanner(System.in);
                    // while (scanner.hasNextInt()) {
                    //     int startNode = scanner.nextInt();
                    //     int endNode = scanner.nextInt();
                        
                    //     long startTime = System.nanoTime();
                    //     spGraph.computeShortestPath(startNode, endNode);
                    //     long endTime = System.nanoTime();
    
                    //     double duration = (endTime - startTime) / 1_000_000_000.0;
                    // System.out.println(spGraph.getCounterRelaxed() + " " + duration);
                    // }
                    // scanner. close();
                }
            }
        }