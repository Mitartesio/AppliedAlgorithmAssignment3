package Project.Graphs;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class GraphWriter {

    public static void writeGraphToFile(GraphBuilderResult result, String filePath) {
        // GEt the maps

        EdgeWeightedGraph graph = result.getGraph();
        HashMap<Long, float[]> vertexMap = result.getVertexMap();
        HashMap<Long, Integer> longIntegerMap = result.getLongIntegerMap();


        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            // Write number of vetex and edges
            int numVertices = vertexMap.size();
            int numEdges = graph.E() / 2; // edges are undirected, so stored two times
            writer.write(numVertices + " " + numEdges);
            writer.newLine();

            // Maps to store node indexes to their long IDs and coordinates
            Map<Integer, Long> indexToNodeId = new HashMap<>();
            Map<Integer, float[]> indexToCoordinates = new HashMap<>();


            // Build mappings from indexes to node IDs and coordinates
            for (Map.Entry<Long, Integer> entry : longIntegerMap.entrySet()) {
                Long nodeId = entry.getKey();
                Integer index = entry.getValue();
                indexToNodeId.put(index, nodeId);
                indexToCoordinates.put(index, vertexMap.get(nodeId));
            }

            // write vertexes
            for (int i = 0; i < numVertices; i++) {
                Long nodeId = indexToNodeId.get(i);
                float[] coords = indexToCoordinates.get(i);
                writer.write(nodeId + " " + coords[0] + " " + coords[1]);
                writer.newLine();
            }

            // Write edges
            // Use a Set to avoid writing duplicate edges (should work)
            Set<String> writtenEdges = new HashSet<>();

            for (int v = 0; v < graph.V(); v++) {
                for (Edge edge : graph.adj(v)) {
                    int v1 = edge.either();
                    int v2 = edge.other(v1);

                    // Each edge once
                    String edgeKey = v1 < v2 ? v1 + "-" + v2 : v2 + "-" + v1;
                    if (writtenEdges.contains(edgeKey)) {
                        continue;
                    }
                    writtenEdges.add(edgeKey);

                    double weight = edge.weight();

                    // Get original node IDs
                    Long nodeId1 = indexToNodeId.get(v1);
                    Long nodeId2 = indexToNodeId.get(v2);

                    writer.write(nodeId1 + " " + nodeId2 + " " + (int) weight);
                    writer.newLine();
                }
            }

        } catch (IOException e) {
            System.err.println("Error writing to file: " + e.getMessage());
        }
    }
}
