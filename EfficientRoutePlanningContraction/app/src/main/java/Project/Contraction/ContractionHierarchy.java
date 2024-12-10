package Project.Contraction;

import java.util.Arrays;

import Project.Dijkstra.IndexMinPQ;
import Project.Dijkstra.LocalDijkstra;
import Project.Graphs.Edge;
import Project.Graphs.EdgeWeightedGraph;

public class ContractionHierarchy {
    private IndexMinPQ<Integer> PQ;
    private EdgeWeightedGraph graph;
    private LocalDijkstra ld;

    private int[] rank;

    private int rankCounter;


    public ContractionHierarchy(EdgeWeightedGraph graph){
        this.graph = graph;
        this.rank = new int[graph.V()];
        Arrays.fill(rank, -1);

        rankCounter = 0;
        this.PQ = new IndexMinPQ<>(graph.V());
        ld = new LocalDijkstra(graph);
        createContractionHierarchy();
        lazyUpdate();
        graph.setRankArray(rank);
    }

    private void createContractionHierarchy(){
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
        

        while(!PQ.isEmpty()){
            if(counter == 50){
                createContractionHierarchy();
                counter=0;
            }
            
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
                contractNode(leastNode);

                for(Edge e : graph.adjacentEdges(leastNode)){
                    int neighbor = e.other(leastNode);
                    if (!graph.isContracted(graph.getVertex(neighbor))) {
                        int newPriority = ld.computeEdgeDifference(neighbor, false);
                        if (PQ.contains(neighbor)) {
                            PQ.changeKey(neighbor, newPriority);
                        } else {
                            if(!graph.isContracted(graph.getVertex(neighbor))){
                            PQ.insert(neighbor, newPriority);
                            }
                        }
                    }
                }

                
            }
        }
        
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

