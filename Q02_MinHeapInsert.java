import java.util.ArrayList;
import java.util.List;

public class Q02_MinHeapInsert {

    private final List<Integer> heap;

    public Q02_MinHeapInsert() {
        this.heap = new ArrayList<>();
    }

    public void add(int value) {
        heap.add(value); 
        bubbleUp(heap.size() - 1);
    }

    public Integer peek() {
        if (heap.isEmpty()) {
            return null;
        }
        return heap.get(0);
    }

    public int size() {
        return heap.size();
    }

    public List<Integer> snapshot() {
        return new ArrayList<>(heap); 
    }

    public boolean isValidMinHeap() {
        int n = heap.size();
        for (int i = 0; i < n; i++) {
            int left = 2 * i + 1;
            int right = 2 * i + 2;

            if (left < n && heap.get(i) > heap.get(left)) {
                return false;
            }
            if (right < n && heap.get(i) > heap.get(right)) {
                return false;
            }
        }
        return true;
    }

    private void bubbleUp(int index) {
        while (index > 0) {
            int parentIndex = (index - 1) / 2;
            if (heap.get(index) < heap.get(parentIndex)) {
                int temp = heap.get(index);
                heap.set(index, heap.get(parentIndex));
                heap.set(parentIndex, temp);

                index = parentIndex; 
            } else {
                break;
            }
        }
    }
}
