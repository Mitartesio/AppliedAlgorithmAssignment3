package Project.Dijkstra;

import Project.Graphs.EdgeWeightedGraph;

public class ContractionHierarchy {
    private IndexMinPQ<Integer> PQ;
    private EdgeWeightedGraph graph;
    private int counter;

    public ContractionHierarchy(EdgeWeightedGraph graph){
        this.graph = graph;
        this.PQ = new IndexMinPQ<>(graph.V());
        this.counter = 0;
        createContractionHierarchy();
        while(!PQ.isEmpty()){
            System.out.println(PQ.delMin());
        }
    }

    private void createContractionHierarchy(){
        for(int i = 0; i<graph.V(); i++){
            System.out.println();
            LocalDijkstra ld = new LocalDijkstra(graph, i);
            PQ.insert(i,ld.getCounter());
            System.out.println(counter);
            counter++;
        }
    }
}

