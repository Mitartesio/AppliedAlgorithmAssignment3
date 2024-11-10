package Project.Dijkstra;

import java.util.HashMap;
import java.util.HashSet;

import Project.Graphs.Bag;
import Project.Graphs.Edge;
import Project.Graphs.EdgeWeightedGraph;

public class LocalDijkstra2 {
    private HashMap<Integer, Double> distTo;          // distTo[v] = distance  of shortest s->v path
    private HashMap<Integer, Double> edgeTo;            // edgeTo[v] = last edge on shortest s->v path
    private IndexMinPQ<Double> pq;    // priority queue of vertices
    private final EdgeWeightedGraph G;
    private int s;
    private HashSet<Integer> visitedEndNodes;
    private HashSet<Integer> visitedNodes;

    public LocalDijkstra2(EdgeWeightedGraph G){
        this.G = G;
        pq = new IndexMinPQ<>(G.V());
        distTo = new HashMap<>();
        edgeTo = new HashMap<>();

        //This hashSet is for nodes within visited within the run
        this.visitedNodes = new HashSet<>();
        //This is for endNodes that has been visited
        this.visitedEndNodes = new HashSet<>();
    }

    public int computeEdgeDifference(int n){
        int counter = 0;
        this.s = n;
        int nodeCounter = 0;

        //Make bag over edges connected to the initial node n
        Bag<Edge> initialBag = G.adjacentEdges(n);

        for(Edge edge : initialBag){
            //Initialize the remaining nodes to find
            nodeCounter = 0;

            //Find start node
            int startNode = edge.other(n);

            HashSet<Integer> endNodes = initializeSet(initialBag, startNode);

            distTo.put(edge.other(s),0.0);
            visitedNodes.add(startNode);

            //Create initial graph
            buildGraph(startNode);

            //Check if pq is not empty
            if(pq.isEmpty()){
                continue;
            }

            //Find initial leastNode and check if it is an endnode
            int leastNode = relaxLocal();

            //Check if we already found an endnode
            if(endNodes.contains(leastNode)){
                //remove node from end node
                endNodes.remove(leastNode);
                //Check if the cost of the path is smaller than the one going thorough n
                if(distTo.get(leastNode) > edge.weight() + findEdge(initialBag, leastNode).weight()){
                    //Slayyy
                    counter++;
                }
            }

            while(nodeCounter <50 && !endNodes.isEmpty()){

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
                if(distTo.get(leastNode) > edge.weight() + findEdge(initialBag, leastNode).weight()){
                    //Slayyy
                    counter++;
                }
            }
            nodeCounter++;
            }
            visitedEndNodes.add(startNode);
            reset();
    }
    return counter-initialBag.size();
}


    //finds edge based on node n
    private Edge findEdge(Bag<Edge> bag , int n){
        for(Edge edge : bag){
            if(edge.other(s) == n){
                return edge;
            }
        }
        return null;
    }

    private void buildGraph(int node){
        //Create a bag of all edges going from the node
        Bag<Edge> newBag = G.adjacentEdges(node);
        //Iterate through the bag and find all neighbouring nodes to the starting node
        for(Edge edge : newBag){
            
            //Check if node is node is equal to the starting node or already visited
            if(edge.other(node) != s && !visitedNodes.contains(edge.other(node))){
                //If edgeTo doesnt contain edge store it
            if(!edgeTo.containsKey(edge.other(node))){
                
                edgeTo.put(edge.other(node), distTo.get(node) + edge.weight());
                pq.insert(edge.other(node), distTo.get(node) + edge.weight());
                distTo.put(edge.other(node), Double.POSITIVE_INFINITY);
            }
            else if 
            //If edgeTo contains the node we only store it if the value of getting to this node is smaller than the one we have
            (distTo.get(node) + edge.weight() < edgeTo.get(edge.other(node))) {
                
                edgeTo.put(edge.other(node), distTo.get(node) + edge.weight());
                pq.decreaseKey(edge.other(node), distTo.get(node) + edge.weight());
                distTo.put(edge.other(node), Double.POSITIVE_INFINITY);
            }
        }
           }
        }

        //This will reset all collections
        private void reset(){
        distTo.clear();   
        edgeTo.clear(); 
        IndexMinPQ<Double> newPq = new IndexMinPQ<>(G.V());            
        pq = newPq;   
        visitedNodes.clear();
        }



    private HashSet<Integer> initializeSet(Bag<Edge> bag, int node){
        HashSet<Integer> set = new HashSet<>();
        for(Edge edge : bag){
            if(!visitedEndNodes.contains(edge.other(s)) && edge.other(s) != node){
                set.add(edge.other(s));
            }
        }
        return set;
    }

    private int relaxLocal(){
        //The leastNode is the next Node in the pq
        int leastNode = pq.delMin();

        double leastValue = edgeTo.get(leastNode);
        this.distTo.put(leastNode, leastValue);
        visitedNodes.add(leastNode);
        return leastNode;
    }
}
