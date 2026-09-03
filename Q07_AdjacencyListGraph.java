import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Q07_AdjacencyListGraph {

    private final Map<String, Set<String>> adjMap;

    public Q07_AdjacencyListGraph() {
        this.adjMap = new HashMap<>();
    }

    public boolean addVertex(String vertex) {
        if (vertex == null || vertex.trim().isEmpty()) {
            return false;
        }
        if (adjMap.containsKey(vertex)) {
            return false; 
        }
        adjMap.put(vertex, new LinkedHashSet<>());
        return true;
    }

    public boolean addEdge(String from, String to) {
        if (from == null || to == null || from.equals(to)) {
            return false;
        }
        if (!adjMap.containsKey(from) || !adjMap.containsKey(to)) {
            return false; // missing vertex
        }

        Set<String> neighbors = adjMap.get(from);
        return neighbors.add(to);
    }

    public boolean removeEdge(String from, String to) {
        if (from == null || to == null) {
            return false;
        }
        if (!adjMap.containsKey(from) || !adjMap.containsKey(to)) {
            return false;
        }

        Set<String> neighbors = adjMap.get(from);
        return neighbors.remove(to);
    }

    public List<String> outgoing(String vertex) {
        if (vertex == null || !adjMap.containsKey(vertex)) {
            return Collections.emptyList(); 
        }

        Set<String> neighbors = adjMap.get(vertex);
        if (neighbors == null || neighbors.isEmpty()) {
            return Collections.emptyList();
        }

        List<String> result = new ArrayList<>(neighbors);
        return Collections.unmodifiableList(result); 
    }

    public int inDegree(String vertex) {
        if (vertex == null || !adjMap.containsKey(vertex)) {
        }

        int count = 0;
        for (Set<String> neighbors : adjMap.values()) {
            if (neighbors.contains(vertex)) {
                count++;
            }
        }
        return count;
    }

    public int edgeCount() {
        int totalEdges = 0;
        for (Set<String> neighbors : adjMap.values()) {
            totalEdges += neighbors.size();
        }
        return totalEdges;
    }
}
