package Project.Dijkstra;

import Project.Graphs.EdgeWeightedGraph;

public class ContractionHierarchy {
    private IndexMinPQ<Integer> PQ;
    private EdgeWeightedGraph graph;

    public ContractionHierarchy(EdgeWeightedGraph graph){
        this.graph = graph;
        this.PQ = new IndexMinPQ<>(graph.V());
        createContractionHierarchy();
    }

    private void createContractionHierarchy(){
        for(int i = 0; i<graph.V(); i++){
            LocalDijkstra ld = new LocalDijkstra(graph, i);
            PQ.insert(ld.getCounter(),i);
        }
    }
}

