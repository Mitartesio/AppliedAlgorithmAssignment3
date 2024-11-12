package Project.Dijkstra;

import java.util.HashMap;
import java.util.HashSet;

import Project.Graphs.Edge;
import Project.Graphs.EdgeWeightedGraph;

public class BidirectionalDijkstra {

    private double[] distToS;          // distTo[v] = distance  of shortest s->v path
    private double[] distToT;
    private double[] distTo;
    private int counterRelaxed;


    private Edge[] edgeTo;            // edgeTo[v] = last edge on shortest s->v path


    private IndexMinPQ<Double> pqs;    // priority queue of vertices
    private IndexMinPQ<Double> pqt;    // priority queue of vertices
    private IndexMinPQ<Double> pq;


    private EdgeWeightedGraph G;

    private double distanceTotal;

    private HashMap<Integer,Boolean> settled;
    private HashSet<Edge> relaxedEdges;

     public BidirectionalDijkstra(EdgeWeightedGraph G) {
        this.G = G;
        distToS = new double[G.V()];
        distToT = new double[G.V()];
        distTo = new double[G.V()];
        counterRelaxed = 0;

        edgeTo = new Edge[G.V()]; // lets see if we will use it
        distanceTotal = Double.POSITIVE_INFINITY;

        relaxedEdges = new HashSet<>();


        settled = new HashMap<>();
            for (int i = 0; i < G.V(); i++) {
                settled.put(i, false); // 0 for false, 1 for true
            }

        }
        

    public double computeShortestPath(int s, int t) {
        validateVertex(s);
        validateVertex(t);

        for (int v = 0; v < G.V(); v++){
            distToS[v] = Double.POSITIVE_INFINITY; 
            distToT[v] = Double.POSITIVE_INFINITY; 
        }

        distToS[s] = 0.0;
        distToT[t] = 0.0;

        pqs = new IndexMinPQ<>(G.V());
        pqt = new IndexMinPQ<>(G.V());
        pq = new IndexMinPQ<>(G.V());

        pqs.insert(s,0.0);
        pqt.insert(t, 0.0);


        while(!(pqs.isEmpty()) || !(pqt.isEmpty())){

            int i;
            if ((!(pqs.isEmpty())) && (pqs.minKey() <= pqt.minKey())) {
                i = 0;
                
            } else {
                i = 1;
                
            }

            int u = 0;

            if (i==0) {
                u = pqs.delMin();
                
            } else {
                u = pqt.delMin();
                
            }

            if(settled.get(u) == true) break;

            settled.put(u,true);

            for (Edge e : G.adj(u)) {
                relax(e, u, i);
            }
            distanceTotal = Math.min(distanceTotal, (distToS[u] + distToT[u]));   
        }
        return distanceTotal;
    }


    private void relax(Edge e, int v, int i) { //new version with stricter relaxation counter increments
        if (i == 0) {
            distTo = distToS;
            pq = pqs;
        } else {
            distTo = distToT;
            pq = pqt;
        }

        int w = e.other(v);

        if (relaxedEdges.contains(e)) {
            return;
        }
        // If a shorter path to w is found, update the distance
        if (distTo[w] > distTo[v] + e.weight()) {
            distTo[w] = distTo[v] + e.weight();
            edgeTo[w] = e;
            // If w is already in the pq, then decrease the key, otherwise insert it
            if (pq.contains(w)) {
                pq.decreaseKey(w, distTo[w]);
            } else {
                pq.insert(w, distTo[w]);
            }

            relaxedEdges.add(e);
            // Only count relaxation once for each direction
            counterRelaxed++;
        }
    }


    private void validateVertex(int v) {
        int V = distToS.length;
        if (v < 0 || v >= V)
            throw new IllegalArgumentException("vertex " + v + " is not between 0 and " + (V-1));
    }

    public int getCounterRelaxed(){
        return counterRelaxed;
    }
    
}
