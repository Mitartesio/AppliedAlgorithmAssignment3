package Project.Contraction;

import Project.Dijkstra.IndexMinPQ;
import Project.Dijkstra.LocalDijkstra4;
import Project.Graphs.EdgeWeightedGraph;

public class ContractionHierarchy {
    private IndexMinPQ<Integer> PQ;
    private EdgeWeightedGraph graph;
    private int lazyCounter;
    private LocalDijkstra4 ld;

    public ContractionHierarchy(EdgeWeightedGraph graph){
        this.graph = graph;
        this.PQ = new IndexMinPQ<>(graph.V());
        this.lazyCounter = 0;
        ld = new LocalDijkstra4(graph);
        createContractionHierarchy();
        // lazyUpdate();
    }

    private void createContractionHierarchy(){
        for(int i = 0; i<graph.V(); i++){
            PQ.insert(i, ld.computeEdgeDifference(i));
            // System.out.println(i);
        }
    }

    private void lazyUpdate(){
        int counter = 0;
        int testCounter = 0;
        while(!PQ.isEmpty()){
            if(counter == 50){
                //reset PQ
                IndexMinPQ<Integer> newPq = new IndexMinPQ<>(graph.V());
                this.PQ = newPq;
                createContractionHierarchy();
            }
            int leastNode = PQ.delMin();
            int nodeDifference = ld.computeEdgeDifference(leastNode);
            if(PQ.size() == 0){
                testCounter++;
                //write method
            }
            else if(nodeDifference <= PQ.minKey()){
                //Write method with with leastNode and nodeDifference
                testCounter++;
                System.out.println(testCounter);
            
            }else{
                PQ.insert(leastNode, nodeDifference);
                counter++;
            }
        }
        System.out.println("This is the testCounter my homies: " + testCounter);
    }
}

