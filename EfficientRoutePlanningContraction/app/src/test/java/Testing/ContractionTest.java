package Testing;

import java.io.FileNotFoundException;
import java.io.InputStream;

import static org.junit.Assert.assertEquals;
import org.junit.BeforeClass;
import org.junit.Test;

import Project.Dijkstra.LocalDijkstra;
import Project.Graphs.GraphBuilder;
import Project.Graphs.GraphBuilderResult;

public class ContractionTest {

    private static GraphBuilderResult smallGraph;


    

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
        LocalDijkstra ld = new LocalDijkstra(smallGraph.getGraph(), 14);
        assertEquals(-2,ld.getCounter());
    }

    @Test
    public void test(){
        LocalDijkstra ld = new LocalDijkstra(smallGraph.getGraph(), 16);
        assertEquals(-1,ld.getCounter());
    }


@Test
public void testPositive(){
    LocalDijkstra ld = new LocalDijkstra(smallGraph.getGraph(), 1);
    assertEquals(1, ld.getCounter());
}

@Test
public void zeroMinusOne(){
    LocalDijkstra ld = new LocalDijkstra(smallGraph.getGraph(), 4);
    assertEquals(-1, ld.getCounter());
}

@Test
public void testZeroTwo(){
    LocalDijkstra ld = new LocalDijkstra(smallGraph.getGraph(), 13);
    assertEquals(0, ld.getCounter());
}


}
