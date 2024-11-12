package Testing;

import java.io.FileNotFoundException;
import java.io.InputStream;

import static org.junit.Assert.assertEquals;
import org.junit.BeforeClass;
import org.junit.Test;

import Project.Dijkstra.LocalDijkstra4;
import Project.Graphs.GraphBuilder;
import Project.Graphs.GraphBuilderResult;

public class ContractionTest4{

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
        LocalDijkstra4 ld = new LocalDijkstra4(smallGraph.getGraph());
        assertEquals(-2,ld.computeEdgeDifference(14,false));
    }

    @Test
    public void test(){
        LocalDijkstra4 ld = new LocalDijkstra4(smallGraph.getGraph());
        assertEquals(-1,ld.computeEdgeDifference(16,false));
    }


@Test
public void testPositive(){
        LocalDijkstra4 ld = new LocalDijkstra4(smallGraph.getGraph());
        assertEquals(1,ld.computeEdgeDifference(1,false));
}

@Test
public void zeroMinusOne(){
    LocalDijkstra4 ld = new LocalDijkstra4(smallGraph.getGraph());
        assertEquals(-1,ld.computeEdgeDifference(4,false));
}

@Test
public void testZeroTwo(){
    LocalDijkstra4 ld = new LocalDijkstra4(smallGraph.getGraph());
        assertEquals(0,ld.computeEdgeDifference(13,false));
}

@Test
public void testMultipleCalls(){
    LocalDijkstra4 ld = new LocalDijkstra4(smallGraph.getGraph());
        assertEquals(-2,ld.computeEdgeDifference(14,false));
        assertEquals(-1,ld.computeEdgeDifference(16,false));
        assertEquals(1,ld.computeEdgeDifference(1,false));
        assertEquals(-1,ld.computeEdgeDifference(4,false));
        assertEquals(0,ld.computeEdgeDifference(13,false));
}
}
