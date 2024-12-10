package Project.Graphs;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Locale;
import java.util.Scanner;
import java.io.InputStream;

public class GraphBuilder {
    
    public static EdgeWeightedGraph buildGraphFromInputStream(InputStream inputStream) {

        Scanner myScanner = new Scanner(inputStream);
        myScanner.useLocale(Locale.US);
        int n = myScanner.nextInt();
        int m = myScanner.nextInt();
        myScanner.nextLine();


        /* Input of vertexes and instantiation of verttexMap */
        //HashMap<Long,float[]> vertexMap = new HashMap<>();
        //HashMap<Long,Integer> longIntegerMap = new HashMap<>();
        // long[] vertexes = new long[n];

        // Init and population of edges in graph
        EdgeWeightedGraph graph = new EdgeWeightedGraph(n);

        for (int i = 0; i < n; i++) {
            long vertexNumber = myScanner.nextLong();
            float[] coordinates = new float[2];
            coordinates[0] = myScanner.nextFloat();
            coordinates[1] = myScanner.nextFloat();

            graph.addVertex(vertexNumber, coordinates);
            
            myScanner.nextLine(); 
            // make new vertex here. 
        }

        

        /* Input of edges */
        for (int i = 0; i < m; i++) {
            
            long v1 = myScanner.nextLong();
            long v2 = myScanner.nextLong();
            int weight = myScanner.nextInt();

            Edge e = new Edge(graph.getVertexById(v1), graph.getVertexById(v2), weight);

            myScanner.nextLine();

            graph.addEdge(e);
        }
        myScanner.close();
        return graph;

    }


}
