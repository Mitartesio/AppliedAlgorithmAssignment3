package Project.Dijkstra;

import java.util.HashMap;
import java.util.HashSet;

import Project.Graphs.Bag;
import Project.Graphs.Edge;
import Project.Graphs.EdgeWeightedGraph;

public class LocalDijkstra3 {
    private HashMap<Integer, Double> distTo;          // distTo[v] = distance  of shortest s->v path
    private IndexMinPQ<Double> pq;    // priority queue of vertices
    private final EdgeWeightedGraph G;
    private HashSet<Integer> visitedNodes;
    private int s;

    public LocalDijkstra3(EdgeWeightedGraph G){
        this.G = G;
        distTo = new HashMap<>();
        pq = new IndexMinPQ<>(G.V());
    }

    public int computeEdgeDifference(int s){
        this.s = s;
        int counter = 0;

        Bag<Edge> initialBag = G.adjacentEdges(s);
        HashSet<Integer> visitedEndNodes = new HashSet<>();

        for(Edge edge : initialBag){
            int startNode = edge.other(s);
            distTo.put(startNode, 0.0);
            int nodeCounter = 0;
            visitedEndNodes.add(startNode);

            Double highestValue = getHighestValue(initialBag,edge);

            HashSet<Integer> endNodes = initializeSet(initialBag, startNode, visitedEndNodes);
            fillMinPq(startNode);

            int leastNode = startNode;

            while(nodeCounter <50 && !endNodes.isEmpty() && distTo.get(leastNode) < highestValue){
                leastNode = pq.delMin();
                if(endNodes.contains(leastNode)){
                    endNodes.remove(leastNode);
                    counter++;
                }
            }
            reset();
        }
        return counter-initialBag.size(); 
    }

    private Double getHighestValue(Bag<Edge> bag, Edge edge){
        Double value = 0.0;
        for(Edge edge2 : bag){
            if(!edge2.equals(edge)){
                if(value < edge.weight() + edge2.weight()){
                    value = edge.weight() + edge2.weight();
                }
            }
        }
        return value;
    }

     //This will reset all collections
     private void reset(){
        distTo.clear();
        IndexMinPQ<Double> newPq = new IndexMinPQ<>(G.V());            
        pq = newPq;   
        visitedNodes.clear();
        }

    private HashSet<Integer> initializeSet(Bag<Edge> bag, int node, HashSet<Integer> visitedEndNodes){
        HashSet<Integer> set = new HashSet<>();
        for(Edge edge : bag){
            if(!visitedEndNodes.contains(edge.other(s)) && edge.other(s) != node){
                set.add(edge.other(s));
            }
        }
        return set;
    }

    private void fillMinPq(int node){
        Bag<Edge> bag = G.adjacentEdges(node);
            for(Edge edge2 : bag){
                //If value is contained in the distTo we only decrease it if a new shorter path is found
                if(pq.contains(edge2.other(node))){
                    if(distTo.get(node) + edge2.weight() > distTo.get(edge2.other(node))){
                        pq.decreaseKey(edge2.other(node), distTo.get(node) + edge2.weight());
                    }
                }
                pq.insert(edge2.other(s), edge2.weight());
            }
    }
}
