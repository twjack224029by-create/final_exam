import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Q05_StudentHashIndex {

    private final Map<String, Set<String>> studentMap;
    private final Map<String, Set<String>> courseMap;

    public Q05_StudentHashIndex() {
        this.studentMap = new HashMap<>();
        this.courseMap = new HashMap<>();
    }

    public boolean enroll(String studentId, String courseId) {
        String sId = normalize(studentId);
        String cId = normalize(courseId);

        if (sId == null || cId == null) {
            return false;
        }

        Set<String> courses = studentMap.computeIfAbsent(sId, k -> new HashSet<>());
        if (courses.contains(cId)) {
            return false; 
        }

        courses.add(cId);
        courseMap.computeIfAbsent(cId, k -> new HashSet<>()).add(sId);
        return true;
    }

    public boolean drop(String studentId, String courseId) {
        String sId = normalize(studentId);
        String cId = normalize(courseId);

        if (sId == null || cId == null) {
            return false;
        }

        Set<String> courses = studentMap.get(sId);
        if (courses == null || !courses.contains(cId)) {
            return false;
        }

        courses.remove(cId);
        if (courses.isEmpty()) {
            studentMap.remove(sId); 
        }

        Set<String> students = courseMap.get(cId);
        if (students != null) {
            students.remove(sId);
            if (students.isEmpty()) {
                courseMap.remove(cId); 
            }
        }

        return true;
    }

    public Set<String> coursesOf(String studentId) {
        String sId = normalize(studentId);
        if (sId == null) {
            return Collections.emptySet();
        }

        Set<String> courses = studentMap.get(sId);
        if (courses == null || courses.isEmpty()) {
            return Collections.emptySet();
        }

        return Collections.unmodifiableSet(new HashSet<>(courses));
    }

    public Set<String> studentsIn(String courseId) {
        String cId = normalize(courseId);
        if (cId == null) {
            return Collections.emptySet();
        }

        Set<String> students = courseMap.get(cId);
        if (students == null || students.isEmpty()) {
            return Collections.emptySet();
        }

        return Collections.unmodifiableSet(new HashSet<>(students));
    }

    public int enrollmentCount() {
        int count = 0;
        for (Set<String> courses : studentMap.values()) {
            count += courses.size();
        }
        return count;
    }

    private String normalize(String input) {
        if (input == null) {
            return null;
        }
        String trimmed = input.trim();
        if (trimmed.isEmpty()) {
            return null;
        }
        return trimmed.toUpperCase();
    }
}
