package Project.Contraction;

import java.util.Arrays;

import Project.Dijkstra.IndexMinPQ;
import Project.Dijkstra.LocalDijkstra4;
import Project.Graphs.Edge;
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
        for(int i = 0; i<graph.V(); i++){
            PQ.insert(i, ld.computeEdgeDifference(i,false));
        }
    }

    private void lazyUpdate(){
        int counter = 0;
        // int testCounter = 0;

        

        while(!PQ.isEmpty()){
            if(counter == 50){
                //reset PQ
                IndexMinPQ<Integer> newPq = new IndexMinPQ<>(graph.V());
                this.PQ = newPq;
                createContractionHierarchy();
                counter=0;
            }

            
            int leastNode = PQ.minIndex();
            int currentPriority = PQ.minKey();


            int updatedPriority = ld.computeEdgeDifference(leastNode,false);

            if(PQ.size() == 0){
                // testCounter++;
                //write method
            }
            
            else if(updatedPriority > currentPriority){
                PQ.changeKey(leastNode,updatedPriority);
                counter++;
                continue;
            }else{
                PQ.delMin();
                contractNode(leastNode);

                for(Edge e : graph.adjacentEdges(leastNode)){
                    int neighbor = e.other(leastNode);
                    if (!graph.isContracted(neighbor)) {
                        int newPriority = ld.computeEdgeDifference(neighbor, false);
                        if (PQ.contains(neighbor)) {
                            PQ.changeKey(neighbor, newPriority);
                        } else {
                            PQ.insert(neighbor, newPriority);
                        }
                    }
                }

                
            }
        }
        
    }


    public void contractNode(int node){

        assignRank(node);

        graph.contractVertex(node);

        ld.computeEdgeDifference(node, true);
    }

    public void assignRank(int node) {
        rank[node] = rankCounter;
        rankCounter++;
    }

    public int[] getRankArray() {
        return rank;
    }
    
}

