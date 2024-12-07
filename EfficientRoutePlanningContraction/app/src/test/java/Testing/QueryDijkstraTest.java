package Testing;

import java.io.FileNotFoundException;
import java.io.InputStream;

import org.junit.Before;
import org.junit.Test;

import Project.Dijkstra.DijkstraUndirectedSP;
import Project.Graphs.EdgeWeightedGraph;
import Project.Graphs.GraphBuilder;

public class QueryDijkstraTest {
    //We will test queryDijkstra by testing whether it gives the same result as bidirectional Dijkstra
    private EdgeWeightedGraph bigGraph;

    @Before
    public void graphInit() throws FileNotFoundException {
        InputStream inputStream = ContractionTest5.class.getResourceAsStream("/Denmark.graph");
        this.bigGraph = GraphBuilder.buildGraphFromInputStream(inputStream);
        if (inputStream == null) {
            throw new FileNotFoundException("Resource 'denmark.graph' not found in classpath");
        }

        //We also need to set the graph up with 
        
    }
    @Test
    public void testLowValues(){
        DijkstraUndirectedSP dj = new DijkstraUndirectedSP(bigGraph);
        // QueryBidirectionalDijkstra qd = new QueryBidirectionalDijkstra(queryGraph);
        for(int i = 0; i<1000; i++){
            // assertEquals(dj.computeShortestPath(i, i+100), qd.computeShortestPath(i,i+100));
        }
    }
    @Test
    public void testMediumValues(){
        DijkstraUndirectedSP dj = new DijkstraUndirectedSP(bigGraph);
        // QueryBidirectionalDijkstra qd = new QueryBidirectionalDijkstra(queryGraph);
        for(int i = 293721; i<293821; i++){
            // assertEquals(dj.computeShortestPath(i, i+100), qd.computeShortestPath(i,i+100));
        }
    }
    @Test
    public void testLargeValues(){
        DijkstraUndirectedSP dj = new DijkstraUndirectedSP(bigGraph);
        // QueryBidirectionalDijkstra qd = new QueryBidirectionalDijkstra(queryGraph);
        for(int i = 587443; i<587543; i++){
            // assertEquals(dj.computeShortestPath(i, i+100), qd.computeShortestPath(i,i+100));
        }
    }
}
