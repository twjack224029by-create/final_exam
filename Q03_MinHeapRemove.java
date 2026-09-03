import java.util.ArrayList;
import java.util.List;

public class Q03_MinHeapRemove {

    private final List<Integer> heap;

    public Q03_MinHeapRemove(List<Integer> values) {
        this.heap = new ArrayList<>();
        if (values != null) {
            for (Integer val : values) {
                if (val != null) {
                    this.heap.add(val);
                }
            }
        }
        for (int i = (heap.size() / 2) - 1; i >= 0; i--) {
            bubbleDown(i);
        }
    }

    public Integer removeMin() {
        if (heap.isEmpty()) {
            return null;
        }

        int minVal = heap.get(0);
        int lastVal = heap.remove(heap.size() - 1);

        if (!heap.isEmpty()) {
            heap.set(0, lastVal);
            bubbleDown(0);
        }

        return minVal;
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

    private void bubbleDown(int index) {
        int n = heap.size();
        while (index < n) {
            int smallest = index;
            int left = 2 * index + 1;
            int right = 2 * index + 2;

            if (left < n && heap.get(left) < heap.get(smallest)) {
                smallest = left;
            }
            if (right < n && heap.get(right) < heap.get(smallest)) {
                smallest = right;
            }

            if (smallest != index) {
                int temp = heap.get(index);
                heap.set(index, heap.get(smallest));
                heap.set(smallest, temp);

                index = smallest; 
            } else {
                break;
            }
        }
    }
}
