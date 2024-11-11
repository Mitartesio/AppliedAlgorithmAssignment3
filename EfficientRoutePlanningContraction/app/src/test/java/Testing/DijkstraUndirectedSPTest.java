package Testing;

import static org.junit.Assert.*;
import org.junit.BeforeClass;
import org.junit.Test;

import Project.Dijkstra.DijkstraUndirectedSP;
import Project.Graphs.*;
import Project.Graphs.EdgeWeightedGraph;

public class DijkstraUndirectedSPTest {
    private static EdgeWeightedGraph GTest;
    @BeforeClass // before each individual test case. Not sure if it is the right approach
    public static void SetUp() {
    
        GTest = new EdgeWeightedGraph(6);
    // Add edges to the graph with weights
        GTest.addEdge(new Edge(0, 1, 0.5));
        GTest.addEdge(new Edge(0, 2, 1.2));
        GTest.addEdge(new Edge(1, 2, 0.8));
        GTest.addEdge(new Edge(1, 3, 2.1));
        GTest.addEdge(new Edge(2, 3, 1.5));
        GTest.addEdge(new Edge(3, 4, 0.9));
        GTest.addEdge(new Edge(4, 5, 1.4));
    }

    @Test
    public void TestShortestPathCalc() {
        // Create undirected dijkstra graph object
        DijkstraUndirectedSP sp = new DijkstraUndirectedSP(GTest); //wrong DijkstraSP that takes graph, s and t?
        
        //
        double shortestPath = sp.computeShortestPath(0, 5);

        // 5 -> 4 (1.4), 4 -> 3 (2.3), 3 -> 1 (4.4), 1 -> 0 (4.9). Other path is 5.0 straight up.
        double expectedDistance = 4.9;

        //checking if expectedDistance = shortestPath. Have to add a delta, given we're dealing with doubles
        //the delta is the maximum difference the two values can have to each other while still being considered equal.
        assertEquals(expectedDistance, shortestPath, 0.001);

        //need to verify that the path also includes the correct edges
        Iterable<Edge> path = sp.pathTo(5);
        StringBuilder pathStr = new StringBuilder();
        for (Edge edge : path) {
            pathStr.append(edge.toString()).append(" ");
        }

        String expectedPath = "0-1 1-3 3-4 4-5";

        assertEquals(expectedPath, pathStr.toString().trim()); 

    }

    @Test
    public void testRelaxationCount() {
        // Create undirected dijkstra graph object
        DijkstraUndirectedSP sp = new DijkstraUndirectedSP(GTest, 0, 5);

        // Call the computeShortestPath method to update relax counter
        sp.computeShortestPath(0, 5);

        // Set the expected number of relaxations done
        int expectedRelaxations = 6;

        assertEquals(expectedRelaxations, sp.getCounterRelaxed());

    }

    @Test
    public void testUnreachableNode() {
        // Create DijkstraUndirectedSP instance with a separate target
        DijkstraUndirectedSP sp = new DijkstraUndirectedSP(GTest, 0, 5);

        // Assume vertex 5 is unreachable (depending on graph setup)
        double unreachableDistance = sp.computeShortestPath(0, 10); // Node that doesn't exist in this graph

        assertEquals(Double.POSITIVE_INFINITY, unreachableDistance, 0.001);
    }


}
