package Project.Dijkstra;

import java.util.HashMap;
import java.util.HashSet;

import Project.Graphs.Bag;
import Project.Graphs.Edge;
import Project.Graphs.EdgeWeightedGraph;

public class LocalDijkstra4 {
    private HashMap<Integer, Double> distTo;          // distTo[v] = distance  of shortest s->v path
    private IndexMinPQ<Double> pq;    // priority queue of vertices
    private final EdgeWeightedGraph G;
    private HashSet<Integer> visitedNodes;
    private int s;
    private HashSet<Edge> shortCuts;

    public LocalDijkstra4(EdgeWeightedGraph G){
        this.G = G;
        distTo = new HashMap<>();
        pq = new IndexMinPQ<>(G.V());
    }

    public int computeEdgeDifference(int s, boolean insertEdges){
        this.s = s;
        int counter = 0;    

        //Neighbouring nodes to s (start node)
        Bag<Edge> initialBag = G.adjacentEdges(s);

        HashSet<Integer> visitedEndNodes = new HashSet<>();
        visitedNodes = new HashSet<>();
        if(insertEdges){
        shortCuts = new HashSet<>();
        }

        for(Edge edge : initialBag){
            int startNode = edge.other(s);
            distTo.put(startNode, 0.0);
            int nodeCounter = 0;

            //The end nodes that has been visited
            visitedEndNodes.add(startNode);

            //Clear visited nodes and add startNode
            visitedNodes.clear();
            visitedNodes.add(startNode);

            //Find the highest value between s and node we are searching for
            double highestValue = getHighestValue(initialBag,edge);

            HashSet<Integer> endNodes = initializeSet(initialBag, startNode, visitedEndNodes);
            fillMinPq(startNode);

            while(nodeCounter <50 && !endNodes.isEmpty() && !pq.isEmpty()){
                //Find least node by deleting from Min pq
                int leastNode = pq.delMin();
                
                //Add to visited nodes
                visitedNodes.add(leastNode);

                //Check if the path just found is more expensive than highest value and break if so
                if(distTo.get(leastNode) > highestValue){
                    int plus = endNodes.size();

                    if(insertEdges){
                        for(Integer integer : endNodes){
                            double weight = edge.weight() + findEdge(initialBag, integer).weight();
                            Edge shortCutEdge = new Edge(startNode, integer, weight);
                            shortCuts.add(shortCutEdge);
                        }
                    }

                    counter = counter + plus;
                    break;
                }

                //Check if node found is an endnode
                if(endNodes.contains(leastNode)){
                    endNodes.remove(leastNode);
                    //If path is greater than weight path going through s increment counter
                    if(distTo.get(leastNode) > edge.weight() + findEdge(initialBag, leastNode).weight()){
                    
                    //Add to shortcuts if insertEdges is true
                    if(insertEdges){
                        double weight = edge.weight() + findEdge(initialBag, leastNode).weight();
                        Edge shortCutEdge = new Edge(startNode, leastNode, weight);
                        shortCuts.add(shortCutEdge);
                    }
                    counter++;
                }
                    nodeCounter++;
                    if(nodeCounter == 50){
                        if(nodeCounter == 50){
                                int plus = endNodes.size();
            
                                if(insertEdges){
                                    for(Integer integer : endNodes){
                                        double weight = edge.weight() + findEdge(initialBag, integer).weight();
                                        Edge shortCutEdge = new Edge(startNode, integer, weight);
                                        shortCuts.add(shortCutEdge);
                                    }
                                }
            
                                counter = counter + plus;
                        }
                    }
                        
                }
                //Fill minpq from least node
                fillMinPq(leastNode);
            }
            reset();
        }
        //return
        if(insertEdges){
        contract();
        G.contractVertex(s);}
        return counter-initialBag.size(); 
    }

    //Method for adding all the shortCuts
    private void contract(){
        for(Edge edge : shortCuts){
            G.addEdge(edge);
            String contractString = edge.either() + " " + edge.other(edge.either()) + " " + edge.weight();
            G.writeEdge(contractString);
        }
        shortCuts.clear();
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

    // private int getHighestValue(Bag<Edge> bag, Edge edge){
    //     int value = 0;
    //     for(Edge edge2 : bag){
    //         if(!edge2.equals(edge)){
    //             int potentialHighestValue = edge.weight() + edge2.weight();
    //             if(value < potentialHighestValue){
    //                 value = potentialHighestValue;
    //             }
    //         }
    //     }
    //     return value;
    // }

    private double getHighestValue(Bag<Edge> bag, Edge currentEdge) {
        double highestValue = currentEdge.weight(); // Start with the weight of the current edge

        for (Edge edge : bag) {
            if (!edge.equals(currentEdge)) { // Avoid comparing the same edge
                double potentialHighestValue = edge.weight();
                if (highestValue < potentialHighestValue) {
                    highestValue = potentialHighestValue;
                }
            }
        }
        return highestValue;
    }

     //This will reset all collections
     private void reset(){
        distTo.clear();  
        while(!pq.isEmpty()){
            pq.delMin();
        } 
        //Maybe change to java util pq
        visitedNodes.clear();
        }

    private HashSet<Integer> initializeSet(Bag<Edge> bag, int node, HashSet<Integer> visitedEndNodes){
        HashSet<Integer> set = new HashSet<>();
        for(Edge edge : bag){
            int otherNode = edge.other(s);
            if(!visitedEndNodes.contains(otherNode) && otherNode != node){
                set.add(otherNode);
            }
        }
        return set;
    }

    private void fillMinPq(int node){
        Bag<Edge> bag = G.adjacentEdges(node);
            for(Edge edge2 : bag){
                if(!visitedNodes.contains(edge2.other(node)) && edge2.other(node) != s){
                //If value is contained in the distTo we only decrease it if a new shorter path is found
                if(pq.contains(edge2.other(node))){
                    double weight = distTo.get(node) + edge2.weight();
                    if(weight < distTo.get(edge2.other(node))){
                        distTo.put(edge2.other(node), weight);
                        pq.decreaseKey(edge2.other(node), weight);
                    }
                }
                    else{
                        double weight = distTo.get(node) + edge2.weight();
                        distTo.put(edge2.other(node), weight);
                        pq.insert(edge2.other(node), weight);
                    }
                }
                
            }
    }
}
