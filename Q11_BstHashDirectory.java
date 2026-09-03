import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Q11_BstHashDirectory {

    private static class Node {
        int id;
        Node left;
        Node right;

        Node(int id) {
            this.id = id;
            this.left = null;
            this.right = null;
        }
    }

    private Node root;
    private final Map<Integer, String> nameMap;
    private boolean removedFlag;

    public Q11_BstHashDirectory() {
        this.root = null;
        this.nameMap = new HashMap<>();
    }

    public boolean add(int id, String name) {
        if (id <= 0 || name == null) {
            return false;
        }
        String trimmedName = name.trim();
        if (trimmedName.isEmpty()) {
            return false;
        }

        if (nameMap.containsKey(id)) {
            return false;
        }

        root = insertRecursive(root, id);
        nameMap.put(id, trimmedName);
        return true;
    }

    private Node insertRecursive(Node current, int id) {
        if (current == null) {
            return new Node(id);
        }
        if (id < current.id) {
            current.left = insertRecursive(current.left, id);
        } else if (id > current.id) {
            current.right = insertRecursive(current.right, id);
        }
        return current;
    }

    public String findName(int id) {
        return nameMap.get(id);
    }

    public boolean remove(int id) {
        if (!nameMap.containsKey(id)) {
            return false;
        }

        removedFlag = false;
        root = removeRecursive(root, id);
        nameMap.remove(id);
        return true;
    }

    private Node removeRecursive(Node current, int id) {
        if (current == null) {
            return null;
        }

        if (id < current.id) {
            current.left = removeRecursive(current.left, id);
        } else if (id > current.id) {
            current.right = removeRecursive(current.right, id);
        } else {
            removedFlag = true;

            if (current.left == null) {
                return current.right;
            } else if (current.right == null) {
                return current.left;
            }

            Node successor = findMin(current.right);
            current.id = successor.id;
            current.right = removeRecursive(current.right, successor.id);
        }
        return current;
    }

    private Node findMin(Node node) {
        while (node.left != null) {
            node = node.left;
        }
        return node;
    }

    public List<Integer> idsBetween(int low, int high) {
        if (low > high || root == null) {
            return Collections.emptyList();
        }
        List<Integer> result = new ArrayList<>();
        rangeSearchHelper(root, low, high, result);
        return result;
    }

    private void rangeSearchHelper(Node node, int low, int high, List<Integer> result) {
        if (node == null) {
            return;
        }

        if (node.id > low) {
            rangeSearchHelper(node.left, low, high, result);
        }

        if (node.id >= low && node.id <= high) {
            result.add(node.id);
        }

        if (node.id < high) {
            rangeSearchHelper(node.right, low, high, result);
        }
    }

    public int size() {
        return nameMap.size();
    }
}
