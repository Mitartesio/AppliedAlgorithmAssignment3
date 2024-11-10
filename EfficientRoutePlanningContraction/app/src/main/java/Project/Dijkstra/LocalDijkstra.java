package Project.Dijkstra;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import Project.Graphs.Bag;
import Project.Graphs.Edge;
import Project.Graphs.EdgeWeightedGraph;

/******************************************************************************
 *  Compilation:  javac DijkstraUndirectedSP.java
 *  Execution:    java DijkstraUndirectedSP input.txt s
 *  Dependencies: EdgeWeightedGraph.java IndexMinPQ.java Stack.java Edge.java
 *  Data files:   https://algs4.cs.princeton.edu/43mst/tinyEWG.txt
 *                https://algs4.cs.princeton.edu/43mst/mediumEWG.txt
 *                https://algs4.cs.princeton.edu/43mst/largeEWG.txt
 *
 *  Dijkstra's algorithm. Computes the shortest path tree.
 *  Assumes all weights are non-negative.
 *
 *  % java DijkstraUndirectedSP tinyEWG.txt 6
 *  6 to 0 (0.58)  6-0 0.58000
 *  6 to 1 (0.76)  6-2 0.40000   1-2 0.36000
 *  6 to 2 (0.40)  6-2 0.40000
 *  6 to 3 (0.52)  3-6 0.52000
 *  6 to 4 (0.93)  6-4 0.93000
 *  6 to 5 (1.02)  6-2 0.40000   2-7 0.34000   5-7 0.28000
 *  6 to 6 (0.00)
 *  6 to 7 (0.74)  6-2 0.40000   2-7 0.34000
 *
 *  % java DijkstraUndirectedSP mediumEWG.txt 0
 *  0 to 0 (0.00)
 *  0 to 1 (0.71)  0-44 0.06471   44-93  0.06793  ...   1-107 0.07484
 *  0 to 2 (0.65)  0-44 0.06471   44-231 0.10384  ...   2-42  0.11456
 *  0 to 3 (0.46)  0-97 0.07705   97-248 0.08598  ...   3-45  0.11902
 *  ...
 *
 *  % java DijkstraUndirectedSP largeEWG.txt 0
 *  0 to 0 (0.00)
 *  0 to 1 (0.78)  0-460790 0.00190  460790-696678 0.00173   ...   1-826350 0.00191
 *  0 to 2 (0.61)  0-15786  0.00130  15786-53370   0.00113   ...   2-793420 0.00040
 *  0 to 3 (0.31)  0-460790 0.00190  460790-752483 0.00194   ...   3-698373 0.00172
 *
 ******************************************************************************/


/**
 *  The {@code DijkstraUndirectedSP} class represents a data type for solving
 *  the single-source shortest paths problem in edge-weighted graphs
 *  where the edge weights are non-negative.
 *  <p>
 *  This implementation uses Dijkstra's algorithm with a binary heap.
 *  The constructor takes &Theta;(<em>E</em> log <em>V</em>) time in the
 *  worst case, where <em>V</em> is the number of vertices and
 *  <em>E</em> is the number of edges.
 *  Each instance method takes &Theta;(1) time.
 *  It uses &Theta;(<em>V</em>) extra space (not including the
 *  edge-weighted graph).
 *  <p>
 *  For additional documentation,
 *  see <a href="https://algs4.cs.princeton.edu/44sp">Section 4.4</a> of
 *  <i>Algorithms, 4th Edition</i> by Robert Sedgewick and Kevin Wayne.
 *  See {@link DijkstraSP} for a version on edge-weighted digraphs.
 *  <p>
 *  This correctly computes shortest paths if all arithmetic performed is
 *  without floating-point rounding error or arithmetic overflow.
 *  This is the case if all edge weights are integers and if none of the
 *  intermediate results exceeds 2<sup>52</sup>. Since all intermediate
 *  results are sums of edge weights, they are bounded by <em>V C</em>,
 *  where <em>V</em> is the number of vertices and <em>C</em> is the maximum
 *  weight of any edge.
 *  <p>
 *  @author Robert Sedgewick
 *  @author Kevin Wayne
 *  @author Nate Liu
 */
public class LocalDijkstra {
    private HashMap<Integer, Double> distTo;          // distTo[v] = distance  of shortest s->v path
    private HashMap<Integer, Double> edgeTo;            // edgeTo[v] = last edge on shortest s->v path
    private IndexMinPQ<Double> pq;    // priority queue of vertices
    private EdgeWeightedGraph G;
    private int counter;
    private int s;
    private Set<Integer> alreadyVisited;
    private HashSet<Integer> visitedNodes;

    /**
     * Computes a shortest-paths tree from the source vertex {@code s} to every
     * other vertex in the edge-weighted graph {@code G}.
     *
     * @param  G the edge-weighted digraph
     * @param  s the source vertex
     * @throws IllegalArgumentException if an edge weight is negative
     * @throws IllegalArgumentException unless {@code 0 <= s < V}
     */
    
    /* 
     * Local Dijkstra
     */
    public LocalDijkstra(EdgeWeightedGraph G, int s) {
        this.s = s;
        this.G = G;
        this.distTo = new HashMap<>();
        this.edgeTo = new HashMap<>();
        this.pq = new IndexMinPQ<>(G.V());
        this.counter = 0;

        Bag<Edge> initialBag = G.adjacentEdges(s);
        
        
        this.alreadyVisited = new HashSet<>();
        this.visitedNodes = new HashSet<>();

        
        for(Edge edge : initialBag){
            visitedNodes.clear();
            distTo.clear();
            edgeTo.clear();
            IndexMinPQ<Double> newPq = new IndexMinPQ<>(569586);
            pq = newPq;

            //Keeps track over number of iterations
            int nodeCounter = 0;

            int startNode = edge.other(s);

            distTo.put(startNode, 0.0);

            //Build initial graph
            buildGraph(startNode);

            visitedNodes.add(startNode);

            //Find first leastNode
            if(pq.isEmpty()){
                continue;
            }
            int leastNode = relaxLocal();

            //Set containing all end nodes from the startNode
            HashSet<Integer> endNodes = initializeHashSet(initialBag, startNode);

            if(endNodes.contains(leastNode)){
                endNodes.remove(leastNode);
                System.out.println(distTo.get(leastNode));
                System.out.println(edge.weight() + findEdge(initialBag, leastNode).weight());
                if(distTo.get(leastNode) > edge.weight() + findEdge(initialBag, leastNode).weight()){
                    //Slayyy
                    counter++;
                }
            }

            while(nodeCounter < 50 && !endNodes.isEmpty()){
            
            //Build graph with newest leastNode
            buildGraph(leastNode);
            

            //Pop and find new leastNode
            if(!pq.isEmpty()){
            leastNode = relaxLocal();
             }else{
                break;
             }
            //If node is contained in the set we remove it and increment counter if the route is equal to the one going through s
            if(endNodes.contains(leastNode)){
                endNodes.remove(leastNode);
                System.out.println(distTo.get(leastNode));
                System.out.println(edge.weight() + findEdge(initialBag, leastNode).weight());
                if(distTo.get(leastNode) > edge.weight() + findEdge(initialBag, leastNode).weight()){
                    //Slayyy
                    counter++;
                }
            }
            nodeCounter++;
        }
        alreadyVisited.add(startNode);
        }

        }

        private HashSet<Integer> initializeHashSet(Bag<Edge> bag, int node){
            HashSet<Integer> set = new HashSet<>();
            for(Edge edge : bag){
                if(!alreadyVisited.contains(edge.other(s)) && edge.other(s) != node){
                    set.add(edge.other(s));
                }
            }
            return set;
        }

        //finds edge based on node s and n
        private Edge findEdge(Bag<Edge> bag , int n){
            for(Edge edge : bag){
                if(edge.other(s) == n){
                    return edge;
                }
            }
            return null;
        }

        //This method takes as argument a node and puts adjacent nodes into the pq, distTo and edgeTo
        private void buildGraph(int node){
            Bag<Edge> newBag = G.adjacentEdges(node);
            for(Edge edge : newBag){
                // System.out.println("other edge: " + edge.other(node));
                // System.out.println(edge.other(node) + " " + distTo.get(node) + " " + edge.weight() + " " + edgeTo.get(edge.other(node)));
                //Make sure node is not start node
                if(edge.other(node) != s && !visitedNodes.contains(edge.other(node))){
                    //If the collections do not contain the node in question
                if(!edgeTo.containsKey(edge.other(node))){
                    // System.out.println(edge.other(node));
                    edgeTo.put(edge.other(node), distTo.get(node) + edge.weight());
                    pq.insert(edge.other(node), distTo.get(node) + edge.weight());
                    distTo.put(edge.other(node), Double.POSITIVE_INFINITY);
                }
                else if 
                //If the node in question is contained but has a smaller value than already stored in distTo
                (distTo.get(node) + edge.weight() < edgeTo.get(edge.other(node))) {
                    // System.out.println(edge.other(node));
                    edgeTo.put(edge.other(node), distTo.get(node) + edge.weight());
                    pq.decreaseKey(edge.other(node), distTo.get(node) + edge.weight());
                    distTo.put(edge.other(node), Double.POSITIVE_INFINITY);
                }
            }
               }
            }

        //This method relaxes the least node and returns it
        private int relaxLocal(){
        // System.out.println(pq.size());
        int leastNode = pq.delMin();
        double leastValue = edgeTo.get(leastNode);
        this.distTo.put(leastNode, leastValue);
        visitedNodes.add(leastNode);
        return leastNode;
    }

    //Returns edge 
    public int getCounter(){
        System.out.println(G.adjacentEdges(s).size());
        return this.counter - G.adjacentEdges(s).size();
    }
        }
        
    


    /* 
     * New method to compute the shortest path from start to end vertex
     */

    // public void computeNodes(int s){
    //     Bag<Edges> bag = G.
    // }

    // public double computeShortestPath(int s, int t) {
    //     validateVertex(s);

    //     pq = new IndexMinPQ<>(G.V());
    //     pq.insert(s, distTo[s]);

    //     while (!pq.isEmpty()) {
    //         int v = pq.delMin();

    //         // Check if it's end vertex
    //         if (v == t) {
    //             return distTo[v]; // Return the distance
    //         }
    //         for (Edge e : G.adj(v)) {
    //             relax(e, v);
    //         }
    //     }

    //     // the target vertex t is not reachable, maybe throw exception instead
    //     return Double.POSITIVE_INFINITY;
    // }

    // public int getCounterRelaxed(){
    //     return counterRelaxed;
    // }

    // // relax edge e and update pq if changed
    // private void relax(Edge e, int v) {
    //     int w = e.other(v);
    //     if (distTo[w] > distTo[v] + e.weight()) {
    //         distTo[w] = distTo[v] + e.weight();
    //         edgeTo[w] = e;
    //         if (pq.contains(w)) pq.decreaseKey(w, distTo[w]);
    //         else                pq.insert(w, distTo[w]);
    //     }
    //     counterRelaxed++;
    // }

    // /**
    //  * Returns the length of a shortest path between the source vertex {@code s} and
    //  * vertex {@code v}.
    //  *
    //  * @param  v the destination vertex
    //  * @return the length of a shortest path between the source vertex {@code s} and
    //  *         the vertex {@code v}; {@code Double.POSITIVE_INFINITY} if no such path
    //  * @throws IllegalArgumentException unless {@code 0 <= v < V}
    //  */
    // public double distTo(int v) {
    //     validateVertex(v);
    //     return distTo[v];
    // }

    // /**
    //  * Returns true if there is a path between the source vertex {@code s} and
    //  * vertex {@code v}.
    //  *
    //  * @param  v the destination vertex
    //  * @return {@code true} if there is a path between the source vertex
    //  *         {@code s} to vertex {@code v}; {@code false} otherwise
    //  * @throws IllegalArgumentException unless {@code 0 <= v < V}
    //  */
    // public boolean hasPathTo(int v) {
    //     validateVertex(v);
    //     return distTo[v] < Double.POSITIVE_INFINITY;
    // }

    // /**
    //  * Returns a shortest path between the source vertex {@code s} and vertex {@code v}.
    //  *
    //  * @param  v the destination vertex
    //  * @return a shortest path between the source vertex {@code s} and vertex {@code v};
    //  *         {@code null} if no such path
    //  * @throws IllegalArgumentException unless {@code 0 <= v < V}
    //  */
    // public Iterable<Edge> pathTo(int v) {
    //     validateVertex(v);
    //     if (!hasPathTo(v)) return null;
    //     Stack<Edge> path = new Stack<Edge>();
    //     int x = v;
    //     for (Edge e = edgeTo[v]; e != null; e = edgeTo[x]) {
    //         path.push(e);
    //         x = e.other(x);
    //     }
    //     return path;
    // }


    // // check optimality conditions:
    // // (i) for all edges e = v-w:            distTo[w] <= distTo[v] + e.weight()
    // // (ii) for all edge e = v-w on the SPT: distTo[w] == distTo[v] + e.weight()
    // private boolean check(EdgeWeightedGraph G, int s) {

    //     // check that edge weights are non-negative
    //     for (Edge e : G.edges()) {
    //         if (e.weight() < 0) {
    //             System.err.println("negative edge weight detected");
    //             return false;
    //         }
    //     }

    //     // check that distTo[v] and edgeTo[v] are consistent
    //     if (distTo[s] != 0.0 || edgeTo[s] != null) {
    //         System.err.println("distTo[s] and edgeTo[s] inconsistent");
    //         return false;
    //     }
    //     for (int v = 0; v < G.V(); v++) {
    //         if (v == s) continue;
    //         if (edgeTo[v] == null && distTo[v] != Double.POSITIVE_INFINITY) {
    //             System.err.println("distTo[] and edgeTo[] inconsistent");
    //             return false;
    //         }
    //     }

    //     // check that all edges e = v-w satisfy distTo[w] <= distTo[v] + e.weight()
    //     for (int v = 0; v < G.V(); v++) {
    //         for (Edge e : G.adj(v)) {
    //             int w = e.other(v);
    //             if (distTo[v] + e.weight() < distTo[w]) {
    //                 System.err.println("edge " + e + " not relaxed");
    //                 return false;
    //             }
    //         }
    //     }

    //     // check that all edges e = v-w on SPT satisfy distTo[w] == distTo[v] + e.weight()
    //     for (int w = 0; w < G.V(); w++) {
    //         if (edgeTo[w] == null) continue;
    //         Edge e = edgeTo[w];
    //         if (w != e.either() && w != e.other(e.either())) return false;
    //         int v = e.other(w);
    //         if (distTo[v] + e.weight() != distTo[w]) {
    //             System.err.println("edge " + e + " on shortest path not tight");
    //             return false;
    //         }
    //     }
    //     return true;
    // }

    // // throw an IllegalArgumentException unless {@code 0 <= v < V}
    // private void validateVertex(int v) {
    //     int V = distTo.length;
    //     if (v < 0 || v >= V)
    //         throw new IllegalArgumentException("vertex " + v + " is not between 0 and " + (V-1));
    // }

    // /**
    //  * Unit tests the {@code DijkstraUndirectedSP} data type.
    //  *
    //  * @param args the command-line arguments
    //  */
    // /* public static void main(String[] args) {
    //     In in = new In(args[0]);
    //     EdgeWeightedGraph G = new EdgeWeightedGraph(in);
    //     int s = Integer.parseInt(args[1]);

    //     // compute shortest paths
    //     DijkstraUndirectedSP sp = new DijkstraUndirectedSP(G, s);


    //     // print shortest path
    //     for (int t = 0; t < G.V(); t++) {
    //         if (sp.hasPathTo(t)) {
    //             StdOut.printf("%d to %d (%.2f)  ", s, t, sp.distTo(t));
    //             for (Edge e : sp.pathTo(t)) {
    //                 StdOut.print(e + "   ");
    //             }
    //             StdOut.println();
    //         }
    //         else {
    //             StdOut.printf("%d to %d         no path\n", s, t);
    //         }
    //     }
    // } */


