import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Queue;

public class Q10_UnweightedShortestPath {

    public static List<String> shortestPath(Map<String, List<String>> graph, String start, String target) {
        if (graph == null || start == null || target == null) {
            return new ArrayList<>();
        }
        if (!graph.containsKey(start) || !graph.containsKey(target)) {
            return new ArrayList<>();
        }

        if (start.equals(target)) {
            List<String> singlePath = new ArrayList<>();
            singlePath.add(start);
            return singlePath;
        }

        Queue<String> queue = new ArrayDeque<>();
        Map<String, String> predecessor = new HashMap<>();

        queue.offer(start);
        predecessor.put(start, null); 

        boolean found = false;

        while (!queue.isEmpty()) {
            String current = queue.poll();

            if (current.equals(target)) {
                found = true;
                break;
            }

            List<String> neighbors = graph.get(current);
            if (neighbors != null) {
                for (String neighbor : neighbors) {
                    if (neighbor != null && !predecessor.containsKey(neighbor)) {
                        predecessor.put(neighbor, current);
                        queue.offer(neighbor);
                    }
                }
            }
        }

        if (!found) {
            return new ArrayList<>();
        }

        List<String> path = new ArrayList<>();
        String curr = target;
        while (curr != null) {
            path.add(curr);
            curr = predecessor.get(curr);
        }

        Collections.reverse(path);
        return path;
    }
}
