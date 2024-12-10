package Project.Graphs;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
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

    public static EdgeWeightedGraph addShortcuts(EdgeWeightedGraph graph, String shortcutsFilePath) throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(shortcutsFilePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                
                String[] parts = line.split("\\s+"); // Split by whitespace
                if (parts.length != 3) {
                    throw new IllegalArgumentException("Invalid shortcut format: " + line);
                }

                Long source = Long.parseLong(parts[0]);
                Long target = Long.parseLong(parts[1]);
                double weight = Double.parseDouble(parts[2]);

                // Add the shortcut edge
                Vertex sourceVertex = graph.getVertexById(source);
                Vertex targetVertex = graph.getVertexById(target);
                Edge shortcutEdge = new Edge(sourceVertex, targetVertex, weight);
                graph.addEdge(shortcutEdge);
            }
        }
        return graph;
    }


    public static void writeContractedGraphToFile(EdgeWeightedGraph graph, String filePath) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            // Write the number of vertices
            writer.write(graph.V() + "\n");
    
            // Write vertices
            // Each line: vertexIndex vertexId rank x y
            for (int i = 0; i < graph.V(); i++) {
                Vertex v = graph.getVertex(i);
                writer.write(v.getVertexIndex() + " " 
                           + v.getVertexId() + " "
                           + v.getRank() + " "
                           + v.getCoordinateX() + " "
                           + v.getCoordinateY() + "\n");
            }
    
            // Write number of edges
            writer.write(graph.E() + "\n");
    
            // Write edges
            // Each line: sourceIndex targetIndex weight
            for (Edge e : graph.edges()) {
                int vIndex = e.eitherInt();
                int wIndex = e.other(vIndex);
                double weight = e.weight();
                writer.write(vIndex + " " + wIndex + " " + weight + "\n");
            }
    
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    public static EdgeWeightedGraph readContractedGraphFromFile(String filePath) {
        EdgeWeightedGraph graph = null;
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            // Read the number of vertices
            int V = Integer.parseInt(reader.readLine().trim());
            graph = new EdgeWeightedGraph(V);
    
            // Temporary arrays to store vertex data
            // (You may not need these arrays if you directly create and add vertices)
            long[] ids = new long[V];
            int[] ranks = new int[V];
            double[] xs = new double[V];
            double[] ys = new double[V];
    
            // Read vertex data
            for (int i = 0; i < V; i++) {
                String line = reader.readLine().trim();
                String[] parts = line.split("\\s+");
                if (parts.length != 5) {
                    throw new IllegalArgumentException("Invalid vertex line: " + line);
                }
    
                int vertexIndex = Integer.parseInt(parts[0]);
                long vertexId = Long.parseLong(parts[1]);
                int rank = Integer.parseInt(parts[2]);
                float x = Float.parseFloat(parts[3]);
                float y = Float.parseFloat(parts[4]);
    
                // Create a new vertex with given attributes
                Vertex v = new Vertex(vertexIndex,vertexId, x, y);
                v.setRank(rank);
    
                graph.addVertexByIndex(v);
                ids[vertexIndex] = vertexId;
                ranks[vertexIndex] = rank;
                xs[vertexIndex] = x;
                ys[vertexIndex] = y;
            }
    
            graph.setRankArray(ranks);
    
            // Read the number of edges
            int E = Integer.parseInt(reader.readLine().trim());
    
            // Read edges
            for (int i = 0; i < E; i++) {
                String line = reader.readLine().trim();
                String[] parts = line.split("\\s+");
                if (parts.length != 3) {
                    throw new IllegalArgumentException("Invalid edge line: " + line);
                }
    
                int sourceIndex = Integer.parseInt(parts[0]);
                int targetIndex = Integer.parseInt(parts[1]);
                double weight = Double.parseDouble(parts[2]);
    
                Vertex sourceVertex = graph.getVertex(sourceIndex);
                Vertex targetVertex = graph.getVertex(targetIndex);
                Edge e = new Edge(sourceVertex, targetVertex, weight);
                graph.addEdge(e);
            }
    
        } catch (IOException e) {
            e.printStackTrace();
        }
        return graph;
    }
    
    


}
