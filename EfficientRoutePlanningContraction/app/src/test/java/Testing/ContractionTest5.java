package Testing;

import java.io.FileNotFoundException;
import java.io.InputStream;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import Project.Dijkstra.LocalDijkstra4;
import Project.Graphs.GraphBuilder;
import Project.Graphs.GraphBuilderResult;

public class ContractionTest5{

    private GraphBuilderResult smallGraph;
    // private static LocalDijkstra2 ld;

    @Before
    public void graphInit() throws FileNotFoundException {
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

//Here we are testing that the contractions actully work

@Test
public void testContraction(){
    LocalDijkstra4 ld = new LocalDijkstra4(smallGraph.getGraph());
    assertEquals(0,ld.computeEdgeDifference(5, true));
    assertEquals(-1,ld.computeEdgeDifference(3, true));
    assertEquals(2,ld.computeEdgeDifference(1, true));
}

@Test
public void testContraction2(){
    LocalDijkstra4 ld = new LocalDijkstra4(smallGraph.getGraph());
    assertEquals(-1,ld.computeEdgeDifference(12, true));
    assertEquals(-2,ld.computeEdgeDifference(14, true));
    assertEquals(-1,ld.computeEdgeDifference(16, true));
}

// @Test
// public void testContraction3(){
//     LocalDijkstra4 ld = new LocalDijkstra4(smallGraph.getGraph());
//     ld.computeEdgeDifference(12, true));
//     assertEquals(-2,ld.computeEdgeDifference(14, true));
//     assert    assertEquals(Equals(-1,ld.computeEdgeDifference(16, true));
// }
}
