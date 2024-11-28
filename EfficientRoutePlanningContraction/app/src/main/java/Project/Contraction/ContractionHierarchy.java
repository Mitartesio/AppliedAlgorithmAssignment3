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
            while(!PQ.isEmpty()){
                PQ.delMin();
        }
        // if(!PQ.isEmpty()){
        //     this.PQ = new IndexMinPQ<>(graph.V());
        // }
        for(int i = 0; i<graph.V(); i++){
            if(!ld.isNodeContracted(i)){
            PQ.insert(i, ld.computeEdgeDifference(i,false));}
        }
    }

    private void lazyUpdate(){
        int counter = 0;
        int testcounter = 0;
        

        while(!PQ.isEmpty()){

            if(counter == 1){
                //reset PQ
                System.out.println(testcounter);
                testcounter = 0;
                createContractionHierarchy();
                counter=0;
            }
            
            int leastNode = PQ.minIndex();
            int currentPriority = PQ.minKey();


            int updatedPriority = ld.computeEdgeDifference(leastNode,false);
            
            if(updatedPriority > currentPriority){
                PQ.changeKey(leastNode,updatedPriority);
                counter++;
            }else{
                counter++;
                System.out.println("Deleting: " + leastNode + "With the original value of: " + currentPriority + "updated value: " + updatedPriority);
                PQ.delMin();
                // System.out.println("The test counter is now: " + testcounter);
                // System.out.println("The counter is now" + counter);
                testcounter++;
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

        graph.contractVertex(node);
    }

    public void assignRank(int node) {
        rank[node] = rankCounter;
        rankCounter++;
    }

    public int[] getRankArray() {
        return rank;
    }
    
}

