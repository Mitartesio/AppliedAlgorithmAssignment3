package Project.Contraction;

import java.util.Arrays;

import Project.Dijkstra.IndexMinPQ;
import Project.Dijkstra.LocalDijkstra4;
import Project.Graphs.EdgeWeightedGraph;

public class ContractionHierarchy {
    private IndexMinPQ<Integer> PQ;
    private EdgeWeightedGraph graph;
    private int lazyCounter;
    private LocalDijkstra4 ld;

    private int[] rank;

    private int rankCounter;


    public ContractionHierarchy(EdgeWeightedGraph graph){
        this.graph = graph;
        this.rank = new int[graph.V()];
        Arrays.fill(rank, -1);

        rankCounter = 0;
        this.PQ = new IndexMinPQ<>(graph.V());
        this.lazyCounter = 0;
        ld = new LocalDijkstra4(graph);
        createContractionHierarchy();
        lazyUpdate();
        
    }

    private void createContractionHierarchy(){
        //     while(!PQ.isEmpty()){
        //         PQ.delMin();
        // }
        if(!PQ.isEmpty()){
        IndexMinPQ<Integer> newPQ = new IndexMinPQ<>(graph.V());
        this.PQ = newPQ;
    }
        for(int i = 0; i<graph.V(); i++){
            if(!graph.isContracted(graph.getVertex(i))){
            PQ.insert(i, ld.computeEdgeDifference(i,false));
        }
        }
    }

    private void lazyUpdate(){
        int counter = 0;
        // int secondTestCounter = 0;
        

        while(!PQ.isEmpty()){
            if(counter == 50){
                // secondTestCounter++;
                //reset PQ
                // System.out.println(testcounter);
                createContractionHierarchy();
                counter=0;
                // System.out.println("NOWNOWNOWNOWNOWNOWNOW");
            }
            // if(testcounter > 550000){
            //     System.out.println(testcounter);
            // }
            
            int leastNode = PQ.delMin();
            if(PQ.size() == 0){
                contractNode(leastNode);
                break;
            }
            int updatedPriority = ld.computeEdgeDifference(leastNode,false);

            
            if(updatedPriority > PQ.minKey()){
                PQ.insert(leastNode, updatedPriority);
                counter++;
            }else{
                // System.out.println("Deleting: " + leastNode + "With the original value of: " + updatedPriority);
                // if(testcounter%10000 == 0){
                //     System.out.println(testcounter);
                // }
                // PQ.delMin();
                // counter++;
                // System.out.println("The test counter is now: " + testcounter);
                // System.out.println("The counter is now" + counter);
                contractNode(leastNode);

                // for(Edge e : graph.adjacentEdges(leastNode)){
                //     int neighbor = e.other(leastNode);
                //     if (!ld.isNodeContracted(neighbor)) {
                //         int newPriority = ld.computeEdgeDifference(neighbor, false);
                //         if (PQ.contains(neighbor)) {
                //             PQ.changeKey(neighbor, newPriority);
                //         } else {
                //             if(!ld.isNodeContracted(neighbor)){
                //             PQ.insert(neighbor, newPriority);
                //             }
                //         }
                //     }
                // }

                
            }
        }
        
    }

    public void print(){
        // System.out.println("This is the total number of shortCuts");
        // ld.printTotal();
    }


    public void contractNode(int node){

        assignRank(node);

        ld.computeEdgeDifference(node, true);

        graph.markVertexAsContracted(graph.getVertex(node));
    }

    public void assignRank(int node) {
        rank[node] = rankCounter;
        rankCounter++;
    }

    public int[] getRankArray() {
        return rank;
    }
    
}

