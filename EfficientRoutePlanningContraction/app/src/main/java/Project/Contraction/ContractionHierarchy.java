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

    //Make a PQ with all nodes not contracted yet
    private void createContractionHierarchy(){
        //Check if PQ is empty and create new PQ with remaining nodes if not
        if(!PQ.isEmpty()){
        IndexMinPQ<Integer> newPQ = new IndexMinPQ<>(graph.V());
        this.PQ = newPQ;
    }
        for(int i = 0; i<graph.V(); i++){
            if(!graph.isContracted(i)){
            PQ.insert(i, ld.computeEdgeDifference(i,false));
        }
        }
    }

    //Method for executing lazy updating on the PQ
    private void lazyUpdate(){
        //Lazy counter
        int counter = 0;
        
        

        while(!PQ.isEmpty()){
            //If lazy counter hits 50 compute all edges and fill the PQ
            if(counter == 50){
                createContractionHierarchy();
                counter=0;
            }
            
            //Get least node in the PQ
            int leastNode = PQ.delMin();

            //Check if PQ is empty and execute if so
            if(PQ.size() == 0){
                contractNode(leastNode);
                break;
            }

            //Find the updated priority of leastNode
            int updatedPriority = ld.computeEdgeDifference(leastNode,false);

            //If the updated priority is bigger than the minimum value in the PQ insert it again with updated value
            if(updatedPriority > PQ.minKey()){
                PQ.insert(leastNode, updatedPriority);
                counter++;
            }else{
                //Contract
                contractNode(leastNode);

                //Vi skal lige snakke om vi vil gøre det her

                
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

    //Method for contracting the node
    public void contractNode(int node){

        assignRank(node);

        //Compute edge difference with removal
        ld.computeEdgeDifference(node, true);

        graph.contractVertex(node);
    }

    //Assign rank to the node
    public void assignRank(int node) {
        rank[node] = rankCounter;
        rankCounter++;
    }

    public int[] getRankArray() {
        return rank;
    }
    
}

