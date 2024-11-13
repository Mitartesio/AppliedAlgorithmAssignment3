package Project.Graphs;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;

/**
 * Utility class to write EdgeWeightedGraph to a file with actual vertex IDs.
 */
public class GraphWriter {

    /**
     * Writes the edges of the graph to the specified output file, mapping internal indices to actual vertex numbers.
     *
     * @param outputFilePath     The path to the output graph file (e.g., "denmarkWithContractions.graph").
     * @param graphBuilderResult The GraphBuilderResult containing the graph and vertex mapping.
     */
    public void writeGraphWithContractions(String outputFilePath, GraphBuilderResult graphBuilderResult) {
        EdgeWeightedGraph graph = graphBuilderResult.getGraph();
        HashMap<Long, Integer> actualIDtoIndexMap = graphBuilderResult.getActualIDtoIndexMap();

        // Invert the mapping to get internal index to actual vertex ID
        HashMap<Integer, Long> indexToActualIDMap = MappingInverter.invertMap(actualIDtoIndexMap);

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(outputFilePath))) {
            for (int v = 0; v < graph.V(); v++) {
                Long actualV = indexToActualIDMap.get(v);
                if (actualV == null) {
                    System.err.println("No actual vertex ID found for internal index: " + v + ". Skipping.");
                    continue; // Skip edges with unmapped vertices
                }

                for (Edge e : graph.adj(v)) {
                    int w = e.other(v);
                    Long actualW = indexToActualIDMap.get(w);
                    if (actualW == null) {
                        System.err.println("No actual vertex ID found for internal index: " + w + ". Skipping edge.");
                        continue; // Skip edges with unmapped vertices
                    }
                    double weight = e.weight();

                    // Write the edge to the output file
                    writer.write(actualV + " " + actualW + " " + weight);
                    writer.newLine();
                }
            }
            System.out.println("Graph successfully written to " + outputFilePath);
        } catch (IOException e) {
            System.err.println("Error writing to file " + outputFilePath + ": " + e.getMessage());
            e.printStackTrace();
        }
    }
}
