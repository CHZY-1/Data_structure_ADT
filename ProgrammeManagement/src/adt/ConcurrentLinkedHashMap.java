package adt;

//Author: Chan Zhi Yang
import java.util.Iterator;
import java.util.concurrent.locks.ReentrantLock;

public class ConcurrentLinkedHashMap<K, V> implements MapInterface<K, V>{

    private final LinkedList<Entry<K, V>>[] buckets;
    private final int capacity;
    private final ReentrantLock[] locks;

    // Constructor
    public ConcurrentLinkedHashMap(int capacity) {
        this.capacity = capacity;
        this.buckets = new LinkedList[capacity];
        this.locks = new ReentrantLock[capacity];
        for (int i = 0; i < capacity; i++) {
            buckets[i] = new LinkedList<>();
            locks[i] = new ReentrantLock();
        }
    }

    // Adds or updates a key-value pair in the map. If the key already exists, updates its associated value.
    @Override
    public V put(K key, V value) {
        int bucketIndex = getBucketIndex(key);

        Entry<K, V> newEntry = new Entry<>(key, value);

        LinkedList<Entry<K, V>> bucket = buckets[bucketIndex];

        synchronized (bucket) {
            int bucketSize = bucket.getNumberOfEntries();
            System.out.println("bucket.getNumberOfEntries() = " + bucket.getNumberOfEntries());

            for (int i = 0; i < bucketSize; i++) {
                System.out.println("bucket.getEntry(i) = " + bucket.getEntry(i));
                Entry<K, V> entry = bucket.getEntry(i);

                System.out.println(" entry.getKey().equals(key) = " + entry.getKey().equals(key));
                if (entry.getKey().equals(key)) {
                    V oldValue = entry.getValue();
                    entry.setValue(value);
                    return oldValue;
                }
            }

            bucket.add(newEntry);

            System.out.println("bucket added");

        }

        System.out.println(bucket.toString());

        return null;
    }

    // Retrieves the value associated with the specified key.
    @Override
    public V getValue(K key) {
        int bucketIndex = getBucketIndex(key);

        LinkedList<Entry<K, V>> bucket = buckets[bucketIndex];
        synchronized (bucket) {
            int bucketSize = bucket.getNumberOfEntries();
            for (int i = 0; i < bucketSize; i++) {
                Entry<K, V> entry = bucket.getEntry(i);
                if (entry != null && entry.getKey().equals(key)) {
                    return entry.getValue();
                }
            }
        }

        return null;
    }

    @Override
    public V getOrDefault(K key, V defaultValue) {
        V value = getValue(key);
        return (value != null) ? value : defaultValue;
    }

    @Override
    public boolean replace(K key, V value) {
        int bucketIndex = getBucketIndex(key);

        LinkedList<Entry<K, V>> bucket = buckets[bucketIndex];
        synchronized (bucket) {
            for (int i = 0; i < bucket.getNumberOfEntries(); i++) {
                Entry<K, V> entry = bucket.getEntry(i);
                if (entry != null && entry.getKey().equals(key)) {
                    entry.setValue(value);
                    return true;
                }
            }
        }

        return false;
    }

    @Override
    public V remove(K key) {
        int bucketIndex = getBucketIndex(key);

        LinkedList<Entry<K, V>> bucket = buckets[bucketIndex];
        synchronized (bucket) {
            Iterator<Entry<K, V>> iterator = bucket.iterator();
            while (iterator.hasNext()) {
                Entry<K, V> entry = iterator.next();
                if (entry != null && entry.getKey().equals(key)) {
                    iterator.remove();
                    return entry.getValue();
                }
            }
        }

        return null;
    }

    @Override
    public boolean containsKey(K key) {
        int bucketIndex = getBucketIndex(key);

        LinkedList<Entry<K, V>> bucket = buckets[bucketIndex];
        synchronized (bucket) {
            for (int i = 0; i < bucket.getNumberOfEntries(); i++) {
                Entry<K, V> entry = bucket.getEntry(i);
                if (entry != null && entry.getKey().equals(key)) {
                    return true;
                }
            }
        }

        return false;
    }

    @Override
    public boolean isEmpty() {
        for (LinkedList<Entry<K, V>> bucket : buckets) {
            synchronized (bucket) {
                if (!bucket.isEmpty()) {
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    public boolean isFull() {
        return false; // Linked list implementation doesn't have a fixed capacity
    }

    @Override
    public int size() {
        int totalSize = 0;
        for (LinkedList<Entry<K, V>> bucket : buckets) {
            synchronized (bucket) {
                totalSize += bucket.getNumberOfEntries();
            }
        }
        return totalSize;
    }

    @Override
    public void clear() {
        for (LinkedList<Entry<K, V>> bucket : buckets) {
            synchronized (bucket) {
                bucket.clear();
            }
        }
    }

    @Override
    public void copy(MapInterface<K, V> destination) {
        if (destination instanceof ConcurrentLinkedHashMap) {
            ConcurrentLinkedHashMap<K, V> destMap = (ConcurrentLinkedHashMap<K, V>) destination;
            for (LinkedList<Entry<K, V>> bucket : buckets) {
                synchronized (bucket) {
                    Iterator<Entry<K, V>> iterator = bucket.iterator();
                    while (iterator.hasNext()) {
                        Entry<K, V> entry = iterator.next();
                        destMap.put(entry.getKey(), entry.getValue());
                    }
                }
            }
        }
    }

    // Hash Function
    private int getBucketIndex(K key) {
        int hashCode = computeHashCode(key);
        return Math.abs(hashCode) % capacity;
    }

    private int computeHashCode(K key) {
        if (key == null) {
            return 0;
        }

        return key.hashCode();
    }


    // Entry class to store key-value pairs
    private static class Entry<K, V> {
        private K key;
        private V value;

        public Entry(K key, V value) {
            this.key = key;
            this.value = value;
        }

        public K getKey() {
            return key;
        }

        public V getValue() {
            return value;
        }

        public void setValue(V value) {
            this.value = value;
        }
    }
}
