package Project.Graphs;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Locale;
import java.util.Scanner;
import java.io.InputStream;

public class GraphBuilder {
    
    public static GraphBuilderResult buildGraphFromInputStream(InputStream inputStream) {

        Scanner myScanner = new Scanner(inputStream);
        myScanner.useLocale(Locale.US);
        int n = myScanner.nextInt();
        int m = myScanner.nextInt();
        myScanner.nextLine();


        /* Input of vertexes and instantiation of verttexMap */
        HashMap<Long,float[]> vertexMap = new HashMap<>();
        HashMap<Long,Integer> longIntegerMap = new HashMap<>();
        // long[] vertexes = new long[n];

        for (int i = 0; i < n; i++) {
            long vertexNumber = myScanner.nextLong();
            longIntegerMap.put(vertexNumber, i);

            float[] floats = new float[2];
            floats[0] = myScanner.nextFloat();
            floats[1] = myScanner.nextFloat();
            myScanner.nextLine(); 
            vertexMap.put(vertexNumber, floats);
        }

        // Init and population of edges in graph
        EdgeWeightedGraph graph = new EdgeWeightedGraph(n);

        /* Input of edges */
        for (int i = 0; i < m; i++) {
            long v1 = myScanner.nextLong();
            long v2 = myScanner.nextLong();
            int weight = myScanner.nextInt();

            myScanner.nextLine();

            int v1Integer = longIntegerMap.get(v1);
            int v2Integer = longIntegerMap.get(v2);

            Edge edge = new Edge(v1Integer, v2Integer, weight);
            graph.addEdge(edge);
        }
        myScanner.close();
        return new GraphBuilderResult(graph, vertexMap, longIntegerMap);

    }


}
