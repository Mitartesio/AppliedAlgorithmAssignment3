package Testing;


import Project.Graphs.EdgeWeightedGraph;
import Project.Graphs.GraphBuilder;
import Project.Dijkstra.BidirectionalDijkstra;
import Project.Dijkstra.DijkstraUndirectedSP;
import Project.Graphs.Edge;


import org.junit.Assert.*;
import org.junit.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.*;
import java.net.URL;

import static org.junit.Assert.*;


public class DenmarkGraphTesting {

    private static EdgeWeightedGraph largeGraph;


    

    @Before // Use @BeforeClass if you want to initialize the graph once for all tests
    public void graphInit() throws FileNotFoundException {
        InputStream inputStream = SmallGraphTesting.class.getResourceAsStream("/denmark.graph");
        if (inputStream == null) {
            throw new FileNotFoundException("Resource 'denmark.graph' not found in classpath");
        }
        largeGraph = GraphBuilder.buildGraphFromInputStream(inputStream);
    }


    @Test
    public void checkLargeGraphVertexes() {

        // Check correct number of vertexes
        assertEquals("Graph should have 569586 vertexes", 569586, largeGraph.V());

        // Check whether vertex 1000 is not null and has the right longitude/latitude
        float coordinateX = largeGraph.getVertexById(115724).getCoordinateX();
        float coordinateY = largeGraph.getVertexById(115724).getCoordinateY();
        
        assertEquals("Longitude of vertex 1000 should be 1.0", 9.12669, coordinateX, 0.01);
        assertEquals("Latitude of vertex 1000 should be 2.0", 55.7337, coordinateY, 0.01);
        }



}

