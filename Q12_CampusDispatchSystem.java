import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;

public class Q12_CampusDispatchSystem {

    public record Request(String id, String location, int priority, long sequence) {}

    private final Map<String, Set<String>> graph;
    private final Map<String, Request> pendingMap;
    private final PriorityQueue<Request> pq;

    public Q12_CampusDispatchSystem() {
        this.graph = new HashMap<>();
        this.pendingMap = new HashMap<>();

        Comparator<Request> requestComparator = Comparator
                .comparingInt(Request::priority)
                .thenComparingLong(Request::sequence);

        this.pq = new PriorityQueue<>(requestComparator);
    }

    public boolean addLocation(String location) {
        if (location == null || location.trim().isEmpty()) {
            return false;
        }
        String loc = location.trim();
        if (!graph.containsKey(loc)) {
            graph.put(loc, new HashSet<>());
            return true;
        }
        return false; 
    }

    public boolean addRoad(String first, String second) {
        if (first == null || second == null) {
            return false;
        }
        String u = first.trim();
        String v = second.trim();

        if (u.equals(v) || !graph.containsKey(u) || !graph.containsKey(v)) {
            return false;
        }

        boolean addedU = graph.get(u).add(v);
        boolean addedV = graph.get(v).add(u);

        return addedU || addedV;
    }

    public boolean submit(Request request) {
        if (request == null || request.id() == null || request.location() == null) {
            return false;
        }

        String id = request.id().trim();
        String loc = request.location().trim();

        if (id.isEmpty() || loc.isEmpty()) {
            return false;
        }

        if (pendingMap.containsKey(id) || !graph.containsKey(loc)) {
            return false;
        }

        Request normalizedReq = new Request(id, loc, request.priority(), request.sequence());
        pendingMap.put(id, normalizedReq);
        pq.offer(normalizedReq);
        return true;
    }

    public Request nextReachable(String serviceCenter) {
        if (serviceCenter == null || !graph.containsKey(serviceCenter.trim())) {
            return null;
        }

        String startNode = serviceCenter.trim();
        Set<String> reachableLocations = getReachableLocations(startNode);

        List<Request> skipped = new ArrayList<>();
        Request result = null;

        while (!pq.isEmpty()) {
            Request current = pq.poll();

            if (!pendingMap.containsKey(current.id())) {
                continue;
            }

            if (reachableLocations.contains(current.location())) {
                result = current;
                pendingMap.remove(current.id()); 
                break;
            } else {
                skipped.add(current); 
            }
        }

        pq.addAll(skipped);

        return result;
    }

    public List<String> route(String start, String target) {
        if (start == null || target == null) {
            return Collections.emptyList();
        }

        String u = start.trim();
        String v = target.trim();

        if (!graph.containsKey(u) || !graph.containsKey(v)) {
            return Collections.emptyList(); 
        }

        if (u.equals(v)) {
            List<String> path = new ArrayList<>();
            path.add(u);
            return path;
        }

        Queue<String> queue = new ArrayDeque<>();
        Map<String, String> predecessor = new HashMap<>();

        queue.offer(u);
        predecessor.put(u, null);

        boolean found = false;

        while (!queue.isEmpty()) {
            String current = queue.poll();

            if (current.equals(v)) {
                found = true;
                break;
            }

            Set<String> neighbors = graph.get(current);
            if (neighbors != null) {
                for (String neighbor : neighbors) {
                    if (!predecessor.containsKey(neighbor)) {
                        predecessor.put(neighbor, current);
                        queue.offer(neighbor);
                    }
                }
            }
        }

        if (!found) {
            return Collections.emptyList();
        }

        List<String> path = new ArrayList<>();
        String curr = v;
        while (curr != null) {
            path.add(curr);
            curr = predecessor.get(curr);
        }

        Collections.reverse(path);
        return path;
    }

    public int pendingCount() {
        return pendingMap.size();
    }

    private Set<String> getReachableLocations(String start) {
        Set<String> visited = new HashSet<>();
        Queue<String> queue = new ArrayDeque<>();

        visited.add(start);
        queue.offer(start);

        while (!queue.isEmpty()) {
            String current = queue.poll();
            Set<String> neighbors = graph.get(current);
            if (neighbors != null) {
                for (String neighbor : neighbors) {
                    if (!visited.contains(neighbor)) {
                        visited.add(neighbor);
                        queue.offer(neighbor);
                    }
                }
            }
        }

        return visited;
    }
}
