package Project.Graphs;

import java.util.HashMap;

public class GraphBuilderResult {
    private EdgeWeightedGraph graph;
    private HashMap<Long, float[]> vertexMap;
    private HashMap<Long, Integer> longIntegerMap;

    public GraphBuilderResult(EdgeWeightedGraph graph, HashMap<Long, float[]> vertexMap, HashMap<Long, Integer> longIntegerMap) {
        this.graph = graph;
        this.vertexMap = vertexMap;
        this.longIntegerMap = longIntegerMap;
    }

    public EdgeWeightedGraph getGraph() {
        return graph;
    }

    public HashMap<Long, float[]> getVertexMap() {
        return vertexMap;
    }

    public HashMap<Long, Integer> getLongIntegerMap() {
        return longIntegerMap;
    }

    public int getIntegerRepresentation(Long n){
        return longIntegerMap.get(n);
    }
    
}
