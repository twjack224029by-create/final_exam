import java.util.ArrayList;
import java.util.List;

public class Q04_ChainedHashTable {

    private static class Entry {
        int key;
        String value;

        Entry(int key, String value) {
            this.key = key;
            this.value = value;
        }
    }

    private final List<List<Entry>> buckets;
    private final int bucketCount;
    private int size;

    public Q04_ChainedHashTable(int bucketCount) {
        if (bucketCount <= 0) {
            throw new IllegalArgumentException("Bucket count must be greater than 0.");
        }
        this.bucketCount = bucketCount;
        this.size = 0;
        this.buckets = new ArrayList<>(bucketCount);
        for (int i = 0; i < bucketCount; i++) {
            this.buckets.add(new ArrayList<>());
        }
    }

    public void put(int key, String value) {
        int index = getBucketIndex(key);
        List<Entry> bucket = buckets.get(index);

        for (Entry entry : bucket) {
            if (entry.key == key) {
                entry.value = value;
                return;
            }
        }

        bucket.add(new Entry(key, value));
        size++;
    }

    public String get(int key) {
        int index = getBucketIndex(key);
        List<Entry> bucket = buckets.get(index);

        for (Entry entry : bucket) {
            if (entry.key == key) {
                return entry.value;
            }
        }
        return null;
    }

    public boolean remove(int key) {
        int index = getBucketIndex(key);
        List<Entry> bucket = buckets.get(index);

        for (int i = 0; i < bucket.size(); i++) {
            if (bucket.get(i).key == key) {
                bucket.remove(i);
                size--;
                return true;
            }
        }
        return false;
    }

    public int size() {
        return size;
    }

    public int longestChain() {
        int max = 0;
        for (List<Entry> bucket : buckets) {
            if (bucket.size() > max) {
                max = bucket.size();
            }
        }
        return max;
    }

    private int getBucketIndex(int key) {
        return Math.floorMod(key, bucketCount);
    }
}
