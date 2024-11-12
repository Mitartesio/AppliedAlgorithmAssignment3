package Project.Dijkstra;

import java.util.HashMap;

import Project.Graphs.Edge;
import Project.Graphs.EdgeWeightedGraph;

public class QueryBidirectionalDijkstra {


    private double[] distToS;          // distTo[v] = distance  of shortest s->v path
    private double[] distToT;
    private int counterRelaxed;
    private Edge[] edgeTo;            // edgeTo[v] = last edge on shortest s->v path
    private IndexMinPQ<Double> pqs;    // priority queue of vertices
    private IndexMinPQ<Double> pqt;    // priority queue of vertices

    private EdgeWeightedGraph G;
    private double distanceTotal;
    private HashMap<Integer,Boolean> settled;

    private int[] rank;

     public QueryBidirectionalDijkstra(EdgeWeightedGraph G,int[] rank) {
        this.G = G;
        distToS = new double[G.V()];
        distToT = new double[G.V()];

        edgeTo = new Edge[G.V()]; // lets see if we will use it
        distanceTotal = Double.POSITIVE_INFINITY;
        settled = new HashMap<>();
        this.rank = rank;

        }
        

    public double computeShortestPath(int s, int t) {
        validateVertex(s); validateVertex(t);
        initialize(s, t);
        boolean r = true;

        while (!pqs.isEmpty() || !pqt.isEmpty()) {
            int u;


            if (r && !pqs.isEmpty()) {
                u = pqs.delMin();
            } else if(!r && !pqt.isEmpty()) {
                u = pqt.delMin();
            } else{
                r = !r;
                continue;
            }

            if (settled.get(u)) {
                break;
            }

            settled.put(u, true);



            for (Edge e : G.adj(u)) {
                relax(e, u, r);
                distanceTotal = Math.min(distanceTotal, distToS[u] + distToT[u]);
            }

            // Should set it back to true or false, debug to check
            r = !r;

        }
        return distanceTotal;
        
    }


    private void initialize(int s, int t) {
        for (int v = 0; v < G.V(); v++) {
            distToS[v] = Double.POSITIVE_INFINITY; // int.POSITIVE_INFINITY;
            distToT[v] = Double.POSITIVE_INFINITY; //int.POSITIVE_INFINITY;
            settled.put(v, false);
        }
        distToS[s] = 0.0;
        distToT[t] = 0.0;
        pqs = new IndexMinPQ<>(G.V());
        pqt = new IndexMinPQ<>(G.V());


        pqs.insert(s, 0.0);
        pqt.insert(t, 0.0);
        counterRelaxed = 0;
    }


    private void relax(Edge e, int v, boolean r) {
        double[] distTo = r ? distToS : distToT;
        IndexMinPQ<Double> pq = r ? pqs : pqt;

        int w = e.other(v);

        if (rank[w] <= rank[v]) {
            return;
        }

        if (distTo[w] > distTo[v] + e.weight()) {
            distTo[w] = distTo[v] + e.weight();
            edgeTo[w] = e;
            if (pq.contains(w)) pq.decreaseKey(w, distTo[w]);
            else pq.insert(w, distTo[w]);
        }
        counterRelaxed++;
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
