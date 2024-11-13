package Project.Graphs;

import java.util.HashMap;
import java.util.Map;

public class MappingInverter {
    /**
     * Inverts a HashMap from Long to Integer to a HashMap from Integer to Long.
     *
     * @param originalMap The original HashMap<Long, Integer>.
     * @return The inverted HashMap<Integer, Long>.
     */
    public static HashMap<Integer, Long> invertMap(HashMap<Long, Integer> originalMap) {
        HashMap<Integer, Long> invertedMap = new HashMap<>();
        for (Map.Entry<Long, Integer> entry : originalMap.entrySet()) {
            Long actualID = entry.getKey();
            Integer internalIndex = entry.getValue();
            
            // Check for duplicate internal indices
            if (invertedMap.containsKey(internalIndex)) {
                System.err.println("Duplicate internal index found: " + internalIndex + ". Overwriting existing entry.");
            }
            invertedMap.put(internalIndex, actualID);
        }
        return invertedMap;
    }
}


