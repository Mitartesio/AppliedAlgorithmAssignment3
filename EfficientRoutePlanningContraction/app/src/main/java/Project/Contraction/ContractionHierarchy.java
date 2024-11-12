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

        while(!PQ.isEmpty()){
            int v = PQ.delMin(); //Implement where it is actully removed

            LocalDijkstra4 localdijkstra = new LocalDijkstra4(graph);
            localdijkstra.





            lazyCounter++;
            if(lazyCounter == 50){
                createContractionHierarchy();
            }
        }




    }

    private void createContractionHierarchy(){
        for(int i = 0; i<graph.V(); i++){
            PQ.insert(i, ld.computeEdgeDifference(i));
        }
    }
}

