package Project.Dijkstra;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

import Project.Graphs.Edge;
import Project.Graphs.EdgeWeightedGraph;

public class LocalDijkstra4 {
    private HashMap<Integer, Double> distTo;          // distTo[v] = distance  of shortest s->v path
    private IndexMinPQ<Double> pq;    // priority queue of vertices
    private EdgeWeightedGraph G;
    private HashSet<Integer> visitedNodes;
    private int s;
    private HashSet<Edge> shortCuts;

    public LocalDijkstra4(EdgeWeightedGraph G){
        this.G = G;
        distTo = new HashMap<>();
        pq = new IndexMinPQ<>(G.V());
        shortCuts = new HashSet<>();
    }

    public int computeEdgeDifference(int s,boolean insertEdges){
        this.s = s;
        int counter = 0;
        int contractedCounter = 0;

        //Neighbouring nodes to s (start node)
        List<Edge> initialBag = G.adjacentEdges(s);

        HashSet<Integer> visitedEndNodes = new HashSet<>();
        visitedNodes = new HashSet<>();

        for(Edge edge : initialBag){
            if(G.getVertex(edge.other(s)).isContracted()){
                contractedCounter++;
                continue;
            }
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

            while(nodeCounter<50 && !endNodes.isEmpty() && !pq.isEmpty()){
                //Find least node by deleting from Min pq
                int leastNode = pq.delMin();
                
                //Add to visited nodes
                visitedNodes.add(leastNode);

                // Check if the path just found is more expensive than highest value and break if so
                    if(distTo.get(leastNode) > highestValue){
                        int plus = endNodes.size();
    
                        if(insertEdges){
                            for(Integer integer : endNodes){
                                double weight = edge.weight() + findEdge(initialBag, integer).weight();
                                Edge shortCutEdge = new Edge(G.getVertex(startNode), G.getVertex(integer), weight);
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
                        
                        
                        // i edit between these two 1)
                        if(insertEdges){
                            //Find value of edge in question and add the value of edge from least node to s
                            double shortCutWeight = edge.weight() + findEdge(initialBag, leastNode).weight();
                            //Create and add edge
                            Edge shortCut = new Edge(G.getVertex(leastNode), G.getVertex(startNode), shortCutWeight);
                            shortCuts.add(shortCut);
                        }

                        // i edit between these two 2)//

                    counter++;
                }
                }
                nodeCounter++;
                if(nodeCounter == 50){
                    if(insertEdges){
                    for(Integer integer : endNodes){
                        double weight = edge.weight() + findEdge(initialBag, integer).weight();
                        Edge shortCutEdge = new Edge(G.getVertex(startNode), G.getVertex(integer), weight);
                        shortCuts.add(shortCutEdge);
                    }
                }
                counter += endNodes.size();
                }
                //Fill minpq from least node
                fillMinPq(leastNode);
            }
            reset();
        }
        if(insertEdges){
        contract();
    }
        //return
        return counter-initialBag.size()+contractedCounter; 
    }

    //Method for adding all the shortCuts
    private void contract() {
        // G.contractVertex(s);
        // G.removeVertex(G.getVertex(s));
        G.markVertexAsContracted(G.getVertex(s));
        for(Edge edge : shortCuts){
            long nodeA = edge.either().getVertexId();
            long nodeB = edge.other(edge.either()).getVertexId();
            G.addEdge(edge);
                //String contractString = nodeA + " " + nodeB + " " + edge.weight();
                // System.out.println(contractString);
                //G.writeEdge(contractString);
        }
        shortCuts.clear();
    }

    //finds edge based on node n
    private Edge findEdge(List<Edge> bag , int n){
        for(Edge edge : bag){
            if(edge.other(s) == n && !G.getVertex(edge.other(s)).isContracted()){
                return edge;
            }
        }
        return null;
    }

    private double getHighestValue(List<Edge> bag, Edge edge){
        double value = 0.0;
        for(Edge edge2 : bag){
            if(!edge2.equals(edge) && !G.getVertex(edge2.eitherInt()).isContracted() && !G.getVertex(edge2.other(s)).isContracted()){
                double potentialHighestValue = edge.weight() + edge2.weight();
                if(value < potentialHighestValue){
                    value = potentialHighestValue;
                }
            }
        }
        return value;
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

    private HashSet<Integer> initializeSet(List<Edge> bag, int node, HashSet<Integer> visitedEndNodes){
        HashSet<Integer> set = new HashSet<>();
        for(Edge edge : bag){
            int otherNode = edge.other(s);
            if(!G.getVertex(edge.other(s)).isContracted()){
            if(!visitedEndNodes.contains(otherNode) && otherNode != node){
                set.add(otherNode);
            }}
        }
        return set;
    }

    private void fillMinPq(int node){   
        List<Edge> bag = G.adjacentEdges(node);
            for(Edge edge2 : bag){
                if(!visitedNodes.contains(edge2.other(node)) && edge2.other(node) != s && !G.getVertex(edge2.other(node)).isContracted()){
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
