import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;

public class Q01_PriorityRecord {

    public record Job(String id, int priority, long sequence) {}

    public static List<String> processOrder(List<Job> jobs) {
        if (jobs == null || jobs.isEmpty()) {
            return new ArrayList<>();
        }

        Comparator<Job> jobComparator = Comparator
                .comparingInt(Job::priority)
                .thenComparingLong(Job::sequence)
                .thenComparing(Job::id, Comparator.nullsLast(Comparator.naturalOrder()));

        PriorityQueue<Job> pq = new PriorityQueue<>(jobComparator);

        for (Job job : jobs) {
            if (job != null) {
                pq.offer(job);
            }
        }

        List<String> result = new ArrayList<>();
        while (!pq.isEmpty()) {
            Job job = pq.poll();
            if (job != null && job.id() != null) {
                result.add(job.id());
            }
        }

        return result;
    }
}
