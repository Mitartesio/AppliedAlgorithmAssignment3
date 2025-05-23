package Project.Graphs;
/******************************************************************************
 *  Compilation:  javac EdgeWeightedGraph.java
 *  Execution:    java EdgeWeightedGraph filename.txt
 *  Dependencies: Bag.java Edge.java In.java StdOut.java
 *  Data files:   https://algs4.cs.princeton.edu/43mst/tinyEWG.txt
 *                https://algs4.cs.princeton.edu/43mst/mediumEWG.txt
 *                https://algs4.cs.princeton.edu/43mst/largeEWG.txt
 *
 *  An edge-weighted undirected graph, implemented using adjacency lists.
 *  Parallel edges and self-loops are permitted.
 *
 *  % java EdgeWeightedGraph tinyEWG.txt
 *  8 16
 *  0: 6-0 0.58000  0-2 0.26000  0-4 0.38000  0-7 0.16000
 *  1: 1-3 0.29000  1-2 0.36000  1-7 0.19000  1-5 0.32000
 *  2: 6-2 0.40000  2-7 0.34000  1-2 0.36000  0-2 0.26000  2-3 0.17000
 *  3: 3-6 0.52000  1-3 0.29000  2-3 0.17000
 *  4: 6-4 0.93000  0-4 0.38000  4-7 0.37000  4-5 0.35000
 *  5: 1-5 0.32000  5-7 0.28000  4-5 0.35000
 *  6: 6-4 0.93000  6-0 0.58000  3-6 0.52000  6-2 0.40000
 *  7: 2-7 0.34000  1-7 0.19000  0-7 0.16000  5-7 0.28000  4-7 0.37000
 *
 ******************************************************************************/

 import java.io.BufferedReader;
 import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
 import java.io.IOException;
 import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
 import Project.backing_Classes.*;
 import Project.Dijkstra.*;

 /**
  *  The {@code EdgeWeightedGraph} class represents an edge-weighted
  *  graph of vertices named 0 through <em>V</em> – 1, where each
  *  undirected edge is of type {@link Edge} and has a real-valued weight.
  *  It supports the following two primary operations: add an edge to the graph,
  *  iterate over all of the edges incident to a vertex. It also provides
  *  methods for returning the degree of a vertex, the number of vertices
  *  <em>V</em> in the graph, and the number of edges <em>E</em> in the graph.
  *  Parallel edges and self-loops are permitted.
  *  By convention, a self-loop <em>v</em>-<em>v</em> appears in the
  *  adjacency list of <em>v</em> twice and contributes two to the degree
  *  of <em>v</em>.
  *  <p>
  *  This implementation uses an <em>adjacency-lists representation</em>, which
  *  is a vertex-indexed array of {@link Bag} objects.
  *  It uses &Theta;(<em>E</em> + <em>V</em>) space, where <em>E</em> is
  *  the number of edges and <em>V</em> is the number of vertices.
  *  All instance methods take &Theta;(1) time. (Though, iterating over
  *  the edges returned by {@link #adj(int)} takes time proportional
  *  to the degree of the vertex.)
  *  Constructing an empty edge-weighted graph with <em>V</em> vertices takes
  *  &Theta;(<em>V</em>) time; constructing an edge-weighted graph with
  *  <em>E</em> edges and <em>V</em> vertices takes
  *  &Theta;(<em>E</em> + <em>V</em>) time.
  *  <p>
  *  For additional documentation,
  *  see <a href="https://algs4.cs.princeton.edu/43mst">Section 4.3</a> of
  *  <i>Algorithms, 4th Edition</i> by Robert Sedgewick and Kevin Wayne.
  *
  *  @author Robert Sedgewick
  *  @author Kevin Wayne
  */
 public class EdgeWeightedGraph {
     private static final String NEWLINE = System.getProperty("line.separator");
 
     private final int V;
     private int E;
    //private Bag<Edge>[] adj;

     private Map<Integer,Vertex> vertices;

     private Map<Long, Vertex> idToVertexMap = new HashMap<>();

     private int[] rankArray;
 
     /**
      * Initializes an empty edge-weighted graph with {@code V} vertices and 0 edges.
      *
      * @param  V the number of vertices
      * @throws IllegalArgumentException if {@code V < 0}
      */
     public EdgeWeightedGraph(int V) {
         if (V < 0) throw new IllegalArgumentException("Number of vertices must be non-negative");
         this.V = V;
         this.E = 0;
        this.vertices = new HashMap<>();
        
     }

     public void setRankArray(int[] rank) {
        if (rank.length != this.V()) {
            throw new IllegalArgumentException("Rank array size does not match the number of vertices in the graph");
        }
        this.rankArray = rank;
    
        // Assign rank to each vertex
        for (int i = 0; i < V(); i++) {
            Vertex v = getVertex(i);
            if (v != null) {
                v.setRank(rank[i]);
            }
        }
    }
    


     public int[] getRankArray(){
        return rankArray;
     }

     public void addVertex(long id, float[] coordinates) {
        int index = vertices.size();
        Vertex vertex = new Vertex(index, id,coordinates[0],coordinates[1]);
        vertices.put(index, vertex);
        idToVertexMap.put(id, vertex);
    }

    public void addVertexByIndex(Vertex v) {
        
        vertices.put(v.getVertexIndex(), v);

    }

     public Vertex getVertex(int index) {
        return vertices.get(index);
    }

    public Vertex getVertexById(long id) {
        return idToVertexMap.get(id);
    }

    public Collection<Vertex> getVertices() {
        return vertices.values();
    }

     public void markVertexAsContracted(Vertex v) {
        v.contract();
    }

    public void removeVertex(Vertex node){
        node.removeAllEdgesFromVertex(node);
    }

    
    public boolean isContracted(Vertex v) {
        return v.isContracted();
    }
 

     public List<Edge> adjacentEdges(int s){
        return getVertex(s).getEdges();
     }
 
 
     /**
      * Returns the number of vertices in this edge-weighted graph.
      *
      * @return the number of vertices in this edge-weighted graph
      */
     public int V() {
         return V;
     }
 
     /**
      * Returns the number of edges in this edge-weighted graph.
      *
      * @return the number of edges in this edge-weighted graph
      */
     public int E() {
         return E;
     }
 
     // throw an IllegalArgumentException unless {@code 0 <= v < V}
     private void validateVertex(Vertex v) {
         if (v.getVertexIndex() < 0 || v.getVertexIndex() >= V)
             throw new IllegalArgumentException("vertex " + v + " is not between 0 and " + (V-1));
     }
 
     /**
      * Adds the undirected edge {@code e} to this edge-weighted graph.
      *
      * @param  e the edge
      * @throws IllegalArgumentException unless both endpoints are between {@code 0} and {@code V-1}
      */
     public void addEdge(Edge e) {
         Vertex vertex1 = e.either();
         Vertex vertex2 = e.other(vertex1);
         validateVertex(vertex1);
         validateVertex(vertex2);
         vertex1.addEdge(e);
         vertex2.addEdge(e);
         E++;
     }


      /**
      * writes the undirected edge {@code e} to the specified file.
      *
      * @param  edgeAsString the edge we want to write into the file
      * @throws IllegalArgumentException unless both endpoints are between {@code 0} and {@code V-1}
      */

     public void writeEdge(String edgeAsString){
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("/Users/frederikkolbel/ITU/Third semester/Applied Algorithms/Hand-ins/Hand-in_3/Git folder/AppliedAlgorithmsAssignment3/EfficientRoutePlanningContraction/app/src/main/resources/shortcuts4.graph", true))) {
            
            writer.write(edgeAsString);
            writer.newLine(); // Start a new line after each edge
            
        } catch (IOException e) {
            System.err.println("Error writing edge to graph file: " + e.getMessage());
        }
     }

     public void writeRankArray(int[] rankArray){
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("/Users/frederikkolbel/ITU/Third semester/Applied Algorithms/Hand-ins/Hand-in_3/Git folder/AppliedAlgorithmsAssignment3/EfficientRoutePlanningContraction/app/src/main/resources/ranks.txt",true))) {
            for (int i = 0; i < rankArray.length; i++) {
                writer.write(Integer.toString(rankArray[i]));
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
     }

     public int[] readRankArrayFromFile(String dir){
                    int[] rank = new int[this.V]; // hopefully getting it from graph, should work
            try (BufferedReader reader = new BufferedReader(new FileReader(dir))) {
                for (int i = 0; i < V; i++) {
                    String line = reader.readLine();
                    if (line == null) {
                        throw new IOException("Not enough lines in rank file");
                    }
                    rank[i] = Integer.parseInt(line);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return rank;
     }
 
     /**
      * Returns the edges incident on vertex {@code v}.
      *
      * @param  v the vertex
      * @return the edges incident on vertex {@code v} as an Iterable
      * @throws IllegalArgumentException unless {@code 0 <= v < V}
      */
     public Iterable<Edge> adj(Vertex v) {
         validateVertex(v);
         return v.getEdges();
     }
 
     
     /**
      * Returns the degree of vertex {@code v}.
      *
      * @param  v the vertex
      * @return the degree of vertex {@code v}
      * @throws IllegalArgumentException unless {@code 0 <= v < V}
      */
     public int degree(Vertex v) {
         validateVertex(v);
         return v.getEdges().size();
     }
 
     /**
      * Returns all edges in this edge-weighted graph.
      * To iterate over the edges in this edge-weighted graph, use foreach notation:
      * {@code for (Edge e : G.edges())}.
      *
      * @return all edges in this edge-weighted graph, as an iterable
      */
     public Iterable<Edge> edges() {
         Bag<Edge> list = new Bag<Edge>();
         for (int v = 0; v < V; v++) {
             int selfLoops = 0;
             for (Edge e : adjacentEdges(v)) {
                 if (e.other(v) > v) {
                     list.add(e);
                 }
                 // add only one copy of each self loop (self loops will be consecutive)
                 else if (e.other(v) == v) {
                     if (selfLoops % 2 == 0) list.add(e);
                     selfLoops++;
                 }
             }
         }
         return list;
     }
 
     /**
      * Returns a string representation of the edge-weighted graph.
      * This method takes time proportional to <em>E</em> + <em>V</em>.
      *
      * @return the number of vertices <em>V</em>, followed by the number of edges <em>E</em>,
      *         followed by the <em>V</em> adjacency lists of edges
      */
     public String toString() {
         StringBuilder s = new StringBuilder();
         s.append(V + " " + E + NEWLINE);
         for (int v = 0; v < V; v++) {
             s.append(v + ": ");
             for (Edge e : adjacentEdges(v)) {
                 s.append(e + "  ");
             }
             s.append(NEWLINE);
         }
         return s.toString();
     }
 
     /**
      * Unit tests the {@code EdgeWeightedGraph} data type.
      *
      * @param args the command-line arguments
      */
     /* public static void main(String[] args) {
         In in = new In(args[0]);
         EdgeWeightedGraph G = new EdgeWeightedGraph(in);
         StdOut.println(G);
     } */
 
 }