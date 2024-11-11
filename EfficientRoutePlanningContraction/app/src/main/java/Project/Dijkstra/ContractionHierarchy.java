package Project.Dijkstra;

import Project.Graphs.EdgeWeightedGraph;

public class ContractionHierarchy {
    private IndexMinPQ<Integer> PQ;
    private EdgeWeightedGraph graph;
    private int lazyCounter;
    private LocalDijkstra3 ld;

    public ContractionHierarchy(EdgeWeightedGraph graph){
        this.graph = graph;
        this.PQ = new IndexMinPQ<>(graph.V());
        this.lazyCounter = 0;
        ld = new LocalDijkstra3(graph);
        createContractionHierarchy();
        // while(!PQ.isEmpty()){
        //     PQ.delMin(); //Implement where it is actully removed
        //     lazyCounter++;
        //     if(lazyCounter == 50){
        //         createContractionHierarchy();
        //     }
        // }
    }

    private void createContractionHierarchy(){
        for(int i = 0; i<graph.V(); i++){
            PQ.insert(i, ld.computeEdgeDifference(i));
        }
    }
}

