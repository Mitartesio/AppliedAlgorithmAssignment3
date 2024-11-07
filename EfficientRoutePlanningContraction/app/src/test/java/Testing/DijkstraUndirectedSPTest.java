package Project.Dijkstra;

import Project.Dijkstra.DijkstraUndirectedSP;
import Project.Graphs.*;

import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class DijkstraUndirectedSPTest {
    
    private EdgeWeightedGraph G;

    @BeforeEach // before each individual test case. Not sure if it is the right approach
    public void SetUp() {
    
        G = new EdgeWeightedGraph(6);
    // Add edges to the graph with weights
        G.addEdge(new Edge(0, 1, 0.5));
        G.addEdge(new Edge(0, 2, 1.2));
        G.addEdge(new Edge(1, 2, 0.8));
        G.addEdge(new Edge(1, 3, 2.1));
        G.addEdge(new Edge(2, 3, 1.5));
        G.addEdge(new Edge(3, 4, 0.9));
        G.addEdge(new Edge(4, 5, 1.4));
    }

    @Test
    public void TestShortestPathCalc() {
        // Create undirected dijkstra graph object
        DijkstraUndirectedSP sp = new DijkstraUndirectedSP(G); //wrong DijkstraSP that takes graph, s and t?
        
        //
        double shortestPath = sp.computeShortestPath(0, 5);

        // 5 -> 4 (1.4), 4 -> 3 (2.3), 3 -> 1 (4.4), 1 -> 0 (4.9). Other path is 5.0 straight up.
        double expectedDistance = 4.9;

        //checking if expectedDistance = shortestPath. Have to add a delta? to have it not be depreciated?
        //the delta is the maximum difference the two values can have to each other while still being considered equal.
        assertEquals(expectedDistance, shortestPath, 0.001);

        //need to verify that the path also includes the correct edges
        Iterable<Edge> path = sp.pathTo(5);
        StringBuilder pathStr = new StringBuilder();
        for (Edge edge : path) {
            pathStr.append(edge.toString()).append(" ");
        }

        String expectedPath = "0-1 1-3 3-4 4-5";

        assertEquals(expectedPath, pathStr); // not sure if we should do .trim() on the pathStr

    }

    @Test
    public void testRelaxationCount() {
        // Create undirected dijkstra graph object
        DijkstraUndirectedSP sp = new DijkstraUndirectedSP(G, 0, 5);

        // Call the computeShortestPath to ensure counter is updated
        sp.computeShortestPath(0, 5);

        // Check the number of relaxations done
        int expectedRelaxations = 6;

        assertEquals(expectedRelaxations, sp.getCounterRelaxed());

    }

    @Test
    public void testUnreachableNode() {
        // Create DijkstraUndirectedSP instance with a separate target
        DijkstraUndirectedSP sp = new DijkstraUndirectedSP(G, 0, 5);

        // Assume vertex 5 is unreachable (depending on graph setup)
        double unreachableDistance = sp.computeShortestPath(0, 10); // Node that doesn't exist in this graph

        assertEquals(Double.POSITIVE_INFINITY, unreachableDistance, 0.001);

}
}
