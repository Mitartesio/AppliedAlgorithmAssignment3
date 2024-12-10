package Testing;

import static org.junit.Assert.*;
import org.junit.BeforeClass;
import org.junit.Test;

import Project.Dijkstra.DijkstraUndirectedSP;
import Project.Graphs.*;

public class DijkstraUndirectedSPTest {
private static EdgeWeightedGraph GTest;

@BeforeClass // before each individual test case.
public static void SetUp() {

GTest = new EdgeWeightedGraph(6);

Vertex v0 = new Vertex(0, 0, 0, 0);
Vertex v1 = new Vertex(1, 0, 0, 0);
Vertex v2 = new Vertex(2, 0, 0, 0);
Vertex v3 = new Vertex(3, 0, 0, 0);
Vertex v4 = new Vertex(4, 0, 0, 0);
Vertex v5 = new Vertex(5, 0, 0, 0);

GTest.addVertexByIndex(v0);
GTest.addVertexByIndex(v1);
GTest.addVertexByIndex(v2);
GTest.addVertexByIndex(v3);
GTest.addVertexByIndex(v4);
GTest.addVertexByIndex(v5);

// Add edges to the graph with weights
GTest.addEdge(new Edge(v0, v1, 0.5));
GTest.addEdge(new Edge(v0, v2, 1.2));
GTest.addEdge(new Edge(v1, v2, 0.8));
GTest.addEdge(new Edge(v1, v3, 2.1));
GTest.addEdge(new Edge(v2, v3, 1.5));
GTest.addEdge(new Edge(v3, v4, 0.9));
GTest.addEdge(new Edge(v4, v5, 1.4));
}

@Test
public void TestShortestPathCalc() {
// Create undirected dijkstra graph object
DijkstraUndirectedSP sp = new DijkstraUndirectedSP(GTest);

double shortestPath = sp.computeShortestPath(0, 5);
// 5 -> 4 (1.4), 4 -> 3 (2.3), 3 -> 1 (4.4), 1 -> 0 (4.9). Other path is 5.0
double expectedDistance = 4.9;

//checking if expectedDistance = shortestPath. Have to add a delta, given we're dealing with doubles
//the delta is the maximum difference the two values can have to each other while still being considered equal.
assertEquals(expectedDistance, shortestPath, 0.001);

}

@Test
public void testPathCalculation() {
// Create undirected Dijkstra graph object
DijkstraUndirectedSP sp = new DijkstraUndirectedSP(GTest);

// Compute the shortest path distance
sp.computeShortestPath(0, 5);

// Get the path from node 0 to node 5
Iterable<Edge> path = sp.pathTo(5);

// Build the path string
StringBuilder pathStr = new StringBuilder();
for (Edge edge : path) {
Vertex vertex = edge.either();
int from = vertex.getVertexIndex();
int to = edge.other(from);
pathStr.append(from).append("-").append(to).append(" ");
}
// Expected path sequence
String expectedPath = "0-1 1-3 3-4 4-5";

// Assert that the computed path matches the expected path
assertEquals(expectedPath, pathStr.toString().trim());
}

@Test
public void testRelaxationCount() {

assertNotNull("Graph is null, cannot run it",GTest);
// Create undirected dijkstra graph object
DijkstraUndirectedSP sp = new DijkstraUndirectedSP(GTest);

// Call the computeShortestPath method to update relax counter
sp.computeShortestPath(0, 5);

// Set the expected number of relaxations done
int expectedRelaxations = 5;

assertEquals(expectedRelaxations, sp.getCounterRelaxed());

}

@Test
public void testUnreachableNode() {
// Create DijkstraUndirectedSP instance with a separate target
DijkstraUndirectedSP sp = new DijkstraUndirectedSP(GTest);

// Assume vertex 5 is unreachable (depending on graph setup)
double unreachableDistance = sp.computeShortestPath(0, 10); // Node that doesn't exist in this graph

assertEquals(Double.POSITIVE_INFINITY, unreachableDistance, 0.001);
}

}