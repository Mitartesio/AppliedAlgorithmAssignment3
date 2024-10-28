package Project.Main;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Scanner;

import Project.Graphs.GraphBuilderResult;
import Project.Dijkstra.DijkstraUndirectedSP;
import Project.Graphs.GraphBuilder;

public class Main {

    public static void main(String[] args) throws FileNotFoundException {

        if(args[0].equals("Test")){
            InputStream inputStream = Main.class.getResourceAsStream("/denmark.graph");
        if (inputStream == null) {
            throw new FileNotFoundException("Resource 'denmark.graph' not found in classpath");
        }
        GraphBuilderResult graphResult = GraphBuilder.buildGraphFromInputStream(inputStream);
        //Take start- and endnode
        Scanner scanner = new Scanner(System.in);
        for(int i = 0; i<1000; i++){
            System.out.println("nu");
            int startNode = scanner.nextInt();
            int endNode = scanner.nextInt();
            //start nanoTime
            long startTime = System.nanoTime();
            
            //Make graph with start and end node
            DijkstraUndirectedSP dijkstra = new DijkstraUndirectedSP(graphResult.getGraph(),startNode,endNode);
            //end nanotime
            long endTime = System.nanoTime();

            float totalTime = (endTime-startTime)/1_000_000_000.0f;
            System.out.println("starttime is" + startTime + ", " + "endtime is: " + endTime + ", totaltime is:" + totalTime);
            System.out.println(dijkstra.getCounterRelaxed() + " " + totalTime);
        }
        scanner.close();
        }
        

        // String filePath = "Efficient Route Planning/app/src/main/resources/denmark.graph";
        // GraphBuilderResult result = GraphBuilder.buildGraphFromFile(filePath);
        // InputStream inputStream = Main.class.getResourceAsStream("/denmark.graph");
        // if (inputStream == null) {
        //     throw new FileNotFoundException("Resource 'denmark.graph' not found in classpath");
        // }
        // GraphBuilderResult graphResult = GraphBuilder.buildGraphFromInputStream(inputStream);
        // System.out.println(graphResult.getLongIntegerMap().size());
        // int startNode = 10;
        // int endNode = 20;
        // DijkstraUndirectedSP dijkstra = new DijkstraUndirectedSP(graphResult.getGraph(),startNode,endNode);
        // System.out.println(dijkstra.getCounterRelaxed());
    }


}
