package Testing;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

import Project.Dijkstra.BidirectionalDijkstra;
import Project.Graphs.*;
import Project.Graphs.EdgeWeightedGraph;

public class BidirectionalDijkstraTest {

    private EdgeWeightedGraph G;

    @Before // before each individual test case. Not sure if it is the right approach
    public void SetUp() { // taken from undriected dijkstra test tile.

        G = new EdgeWeightedGraph(6);
        Vertex v0 = new Vertex(0, 0, 0, 0);
        Vertex v1 = new Vertex(1, 1, 0, 0);
        Vertex v2 = new Vertex(2, 2, 0, 0);
        Vertex v3 = new Vertex(3, 3, 0, 0);
        Vertex v4 = new Vertex(4, 4, 0, 0);
        Vertex v5 = new Vertex(5, 5, 0, 0);

        G.addVertexByIndex(v0);
        G.addVertexByIndex(v1);
        G.addVertexByIndex(v2);
        G.addVertexByIndex(v3);
        G.addVertexByIndex(v4);
        G.addVertexByIndex(v5);
        
        // Add edges to the graph with weights
        G.addEdge(new Edge(v0, v1, 0.5));
        G.addEdge(new Edge(v0, v2, 1.2));
        G.addEdge(new Edge(v1, v2, 0.8));
        G.addEdge(new Edge(v1, v3, 2.1));
        G.addEdge(new Edge(v2, v3, 1.5));
        G.addEdge(new Edge(v3, v4, 0.9));
        G.addEdge(new Edge(v4, v5, 1.4));
    }

    @Test
    public void testShortestPathCalculation() {
        BidirectionalDijkstra bidirectionalDijkstra = new BidirectionalDijkstra(G);
        double shortestPath = bidirectionalDijkstra.computeShortestPath(0, 5);

        // Expected shortest path from vertex 0 to 5.
        double expectedShortestPath = 4.9; // 5.0?

        assertEquals(expectedShortestPath, shortestPath, 0.001);
    }

    // not sure if this is needed, but just in case. In the event that s and t are
    // the same vertex
    @Test
    public void testSameST() {
        BidirectionalDijkstra bidirectionalDijkstra = new BidirectionalDijkstra(G);

        double shortestPath = bidirectionalDijkstra.computeShortestPath(3, 3);

        assertEquals(0.0, shortestPath, 0);
    }

    @Test
    public void testRelaxationCount() {
        BidirectionalDijkstra bidirectionalDijkstra = new BidirectionalDijkstra(G);

        bidirectionalDijkstra.computeShortestPath(0, 5);

        int relaxations = bidirectionalDijkstra.getCounterRelaxed();

        // relaxes each time the method is called, meaning. ITR1 s: 0 - 1, 0 - 2. t: 5 -
        // 4.
        // ITR2 s: 1 - 2, 1 - 3. t: 4 - 3. ITR3 s: 2 - 3, t: 3 - 1, 3 - 2.
        int expectedRelaxCounter = 6;

        assertEquals(expectedRelaxCounter, relaxations);
    }

    @Test
    public void testUnreachableNode() {
        // create graph object with unreachable nodes
        EdgeWeightedGraph disconnectedGraph = new EdgeWeightedGraph(6);
        Vertex testv0 = new Vertex(0, 0, 0, 0);
        Vertex testv1 = new Vertex(1, 0, 0, 0);
        Vertex testv2 = new Vertex(2, 0, 0, 0);
        Vertex testv3 = new Vertex(3, 0, 0, 0);

        disconnectedGraph.addVertexByIndex(testv0);
        disconnectedGraph.addVertexByIndex(testv1);
        disconnectedGraph.addVertexByIndex(testv2);
        disconnectedGraph.addVertexByIndex(testv3);

        disconnectedGraph.addEdge(new Edge(testv0, testv1, 0.5));
        disconnectedGraph.addEdge(new Edge(testv2, testv3, 1.0)); // Disconnected from nodes 0, 1, and 4, 5

        BidirectionalDijkstra bidirectionalDijkstra = new BidirectionalDijkstra(disconnectedGraph);

        double shortestPath = bidirectionalDijkstra.computeShortestPath(0, 3);

        // Expecting that 0 to 3 is unreachable, hence infinity
        assertEquals(Double.POSITIVE_INFINITY, shortestPath, 0.001);
    }

    @Test
    public void testSymmetryInResults() {
        // Ensure the bidirectional Dijkstra produces the same result when source and
        // target are swapped
        BidirectionalDijkstra bidirectionalDijkstra = new BidirectionalDijkstra(G);

        double pathFromSToT = bidirectionalDijkstra.computeShortestPath(0, 5);
        double pathFromTToS = bidirectionalDijkstra.computeShortestPath(5, 0);

        assertEquals(pathFromSToT, pathFromTToS, 0.001);
    }
}