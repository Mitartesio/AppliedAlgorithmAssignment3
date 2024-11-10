package Testing;

import java.io.FileNotFoundException;
import java.io.InputStream;

import static org.junit.Assert.assertEquals;
import org.junit.BeforeClass;
import org.junit.Test;

import Project.Dijkstra.LocalDijkstra2;
import Project.Graphs.GraphBuilder;
import Project.Graphs.GraphBuilderResult;

public class ContractionTest2 {

    private static GraphBuilderResult smallGraph;
    // private static LocalDijkstra2 ld;

    @BeforeClass // Use @BeforeClass if you want to initialize the graph once for all tests
    public static void graphInit() throws FileNotFoundException {
        InputStream inputStream = SmallGraphTesting.class.getResourceAsStream("/Small_graph_for_test.graph");
        if (inputStream == null) {
            throw new FileNotFoundException("Resource 'denmark.graph' not found in classpath");
        }
        smallGraph = GraphBuilder.buildGraphFromInputStream(inputStream);
    }
    
    @Test
    public void testZero(){
        LocalDijkstra2 ld = new LocalDijkstra2(smallGraph.getGraph());
        assertEquals(-2,ld.computeEdgeDifference(14));
    }

    @Test
    public void test(){
        LocalDijkstra2 ld = new LocalDijkstra2(smallGraph.getGraph());
        assertEquals(-1,ld.computeEdgeDifference(16));
    }


@Test
public void testPositive(){
    LocalDijkstra2 ld = new LocalDijkstra2(smallGraph.getGraph());
        assertEquals(1,ld.computeEdgeDifference(1));
}

@Test
public void zeroMinusOne(){
    LocalDijkstra2 ld = new LocalDijkstra2(smallGraph.getGraph());
        assertEquals(-1,ld.computeEdgeDifference(4));
}

@Test
public void testZeroTwo(){
    LocalDijkstra2 ld = new LocalDijkstra2(smallGraph.getGraph());
        assertEquals(0,ld.computeEdgeDifference(13));
}

@Test
public void testMultipleCalls(){
    LocalDijkstra2 ld = new LocalDijkstra2(smallGraph.getGraph());
        assertEquals(-2,ld.computeEdgeDifference(14));
        assertEquals(-1,ld.computeEdgeDifference(16));
        assertEquals(1,ld.computeEdgeDifference(1));
        assertEquals(-1,ld.computeEdgeDifference(4));
        assertEquals(0,ld.computeEdgeDifference(13));
}
}
