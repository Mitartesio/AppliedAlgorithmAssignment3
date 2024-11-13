// package Testing;


// import Project.Graphs.EdgeWeightedGraph;
// import Project.Graphs.GraphBuilder;
// import Project.Graphs.GraphBuilderResult;
// import Project.Dijkstra.BidirectionalDijkstra;
// import Project.Dijkstra.DijkstraUndirectedSP;
// import Project.Graphs.Edge;


// import org.junit.Assert.*;
// import org.junit.*;

// import static org.junit.Assert.assertEquals;
// import static org.junit.Assert.assertNotNull;
// import static org.junit.Assert.assertTrue;

// import java.io.*;
// import java.net.URL;

// import static org.junit.Assert.*;


// public class DenmarkGraphTesting {

//     private static GraphBuilderResult largeGraph;


    

//     @BeforeClass // Use @BeforeClass if you want to initialize the graph once for all tests
//     public static void graphInit() throws FileNotFoundException {
//         InputStream inputStream = SmallGraphTesting.class.getResourceAsStream("/denmark.graph");
//         if (inputStream == null) {
//             throw new FileNotFoundException("Resource 'denmark.graph' not found in classpath");
//         }
//         largeGraph = GraphBuilder.buildGraphFromInputStream(inputStream);
//     }


//     @Test
//     public void checkLargeGraphVertexes() {
//         EdgeWeightedGraph graph = largeGraph.getGraph();

//         // Check correct number of vertexes
//         assertEquals("Graph should have 569586 vertexes", 569586, graph.V());

//         // Check whether vertex 1000 is not null and has the right longitude/latitude
//         float[] vertex1000Coords = largeGraph.getVertexMap().get(115724L);
//         assertNotNull("Vertex 1000 should exist in the vertex map", vertex1000Coords);
//         assertEquals("Longitude of vertex 1000 should be 1.0", 9.12669, vertex1000Coords[0], 0.01);
//         assertEquals("Latitude of vertex 1000 should be 2.0", 55.7337, vertex1000Coords[1], 0.01);
//         }



// }

