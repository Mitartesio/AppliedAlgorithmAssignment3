package Project.Graphs;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;

public class ShortcutAppender {

    /**
     * Appends shortcuts from a shortcuts file to the contracted graph file using the provided mapping.
     *
     * @param shortcutsFilePath          Path to the shortcuts file (e.g., "shortcuts.graph").
     * @param contractedGraphFilePath    Path to the contracted graph file (e.g., "denmarkWithContractions.graph").
     * @param indexToActualIDMap         HashMap mapping internal indices to actual Long vertex IDs.
     */
    public void appendShortcuts(String shortcutsFilePath, String contractedGraphFilePath, HashMap<Integer, Long> indexToActualIDMap) {
        try (
            BufferedReader reader = new BufferedReader(new FileReader(shortcutsFilePath));
            BufferedWriter writer = new BufferedWriter(new FileWriter(contractedGraphFilePath, true)) // 'true' for append mode
        ) {
            String line;
            int lineNumber = 0;
            HashSet<String> writtenEdges = new HashSet<>();
            
            while ((line = reader.readLine()) != null) {
                lineNumber++;
                line = line.trim();
                if (line.isEmpty()) continue; // Skip empty lines
                
                String[] parts = line.split("\\s+");
                if (parts.length != 3) {
                    System.err.println("Invalid format at line " + lineNumber + " in shortcuts file: " + line);
                    continue; // Skip invalid lines
                }
                
                try {
                    int internalIndexA = Integer.parseInt(parts[0]);
                    int internalIndexB = Integer.parseInt(parts[1]);
                    double weight = Double.parseDouble(parts[2]);
                    
                    Long actualID_A = indexToActualIDMap.get(internalIndexA);
                    Long actualID_B = indexToActualIDMap.get(internalIndexB);
                    
                    if (actualID_A == null || actualID_B == null) {
                        System.err.println("Missing vertex mapping for internal indices at line " + lineNumber + ": " + internalIndexA + ", " + internalIndexB);
                        continue; // Skip edges with missing mappings
                    }
                    
                    // Create a unique identifier for the undirected edge
                    String edgeId = actualID_A < actualID_B ? actualID_A + "-" + actualID_B : actualID_B + "-" + actualID_A;
                    
                    if (writtenEdges.contains(edgeId)) {
                        System.out.println("Duplicate shortcut found and skipped: " + edgeId);
                        continue; // Skip duplicate edge
                    }
                    
                    writtenEdges.add(edgeId);
                    
                    // Write the mapped shortcut to the contracted graph file
                    writer.write(actualID_A + " " + actualID_B + " " + weight);
                    writer.newLine();
                    
                } catch (NumberFormatException e) {
                    System.err.println("Number format exception at line " + lineNumber + " in shortcuts file: " + line);
                    // Optionally log or handle the exception as needed
                }
            }
            System.out.println("Shortcuts successfully appended to " + contractedGraphFilePath);
        } catch (IOException e) {
            System.err.println("Error processing files: " + e.getMessage());
            e.printStackTrace();
        }
    }
}

