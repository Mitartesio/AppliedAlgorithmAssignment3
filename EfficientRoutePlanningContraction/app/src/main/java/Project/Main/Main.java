package Project.Main;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Scanner;

import Project.Graphs.GraphBuilderResult;
import Project.Dijkstra.DijkstraUndirectedSP;
import Project.Graphs.GraphBuilder;

public class Main {

    public static void main(String[] args) throws FileNotFoundException {

        // if(args[0].equals("Test")){
        //     InputStream inputStream = Main.class.getResourceAsStream("/denmark.graph");
        // if (inputStream == null) {
        //     throw new FileNotFoundException("Resource 'denmark.graph' not found in classpath");
        // }
        // GraphBuilderResult graphResult = GraphBuilder.buildGraphFromInputStream(inputStream);

        // //Take start- and endnode
        // Scanner scanner = new Scanner(System.in);
        // for(int i = 0; i<1000; i++){
        //     int startNode = graphResult.getIntegerRepresentation(scanner.nextLong());
        //     int endNode = graphResult.getIntegerRepresentation(scanner.nextLong());
        //     //start nanoTime
        //     long startTime = System.nanoTime();
            
        //     //Make graph with start and end node
        //     DijkstraUndirectedSP dijkstra = new DijkstraUndirectedSP(graphResult.getGraph(),startNode,endNode);
        //     //end nanotime
        //     long endTime = System.nanoTime();

        //     long totalTime = endTime-startTime;
        //     System.out.println(dijkstra.getCounterRelaxed() + " " + totalTime);
        // }
        // scanner.close();
        // }
        

        // String filePath = "Efficient Route Planning/app/src/main/resources/denmark.graph";
        // GraphBuilderResult result = GraphBuilder.buildGraphFromFile(filePath);

        if(args[0].equals("JarTest")){
            System.out.println("Yupppiiiiii");
        }

        InputStream inputStream = Main.class.getResourceAsStream("/denmark.graph");
        if (inputStream == null) {
            throw new FileNotFoundException("Resource 'denmark.graph' not found in classpath");
        }
        GraphBuilderResult graphResult = GraphBuilder.buildGraphFromInputStream(inputStream);
        System.out.println(graphResult.getLongIntegerMap().size());
    }


}
