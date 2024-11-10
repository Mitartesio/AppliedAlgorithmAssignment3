package Project.Dijkstra;

import Project.Graphs.EdgeWeightedGraph;

public class ContractionHierarchy {
    private IndexMinPQ<Integer> PQ;
    private EdgeWeightedGraph graph;
    private int lazyCounter;

    public ContractionHierarchy(EdgeWeightedGraph graph){
        this.graph = graph;
        this.PQ = new IndexMinPQ<>(graph.V());
        this.lazyCounter = 0;
        createContractionHierarchy();
        while(!PQ.isEmpty()){
            PQ.delMin(); //Implement where it is actully removed
            lazyCounter++;
            if(lazyCounter == 50){
                createContractionHierarchy();
            }
        }
    }

    private void createContractionHierarchy(){
        for(int i = 0; i<graph.V(); i++){
            LocalDijkstra ld = new LocalDijkstra(graph, i);
            PQ.insert(i,ld.getCounter());
        }
    }
}

