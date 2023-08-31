package adt;

//Author: Chan Zhi Yang
import java.io.Serializable;
import java.util.Iterator;
import java.util.concurrent.locks.ReentrantLock;

public class ConcurrentLinkedHashMap<K, V> implements MapInterface<K, V>, Serializable {

    private static final int DEFAULT_CAPACITY = 31;
    private static final float DEFAULT_LOAD_FACTOR = 0.75f;

    private LinkedList<Entry<K, V>>[] buckets;
    private ReentrantLock[] locks;
    private int capacity;
    private float loadFactor;

    private int totalNumberOfEntries;

    // Default constructor
    public ConcurrentLinkedHashMap() {
        this(DEFAULT_CAPACITY, DEFAULT_LOAD_FACTOR);
    }

    // Constructor with custom capacity
    public ConcurrentLinkedHashMap(int capacity) {
        this(capacity, DEFAULT_LOAD_FACTOR);
    }

    // Constructor with custom capacity and load factor
    public ConcurrentLinkedHashMap(int capacity, float loadFactor) {
        if (capacity <= 0 || loadFactor <= 0) {
            throw new IllegalArgumentException("Capacity and load factor must be positive");
        }

        if (!isPrime(capacity)) {
            //Prime number to reduce the chance of collision
            System.out.println("Warning: Capacity is not a prime number. Consider using a prime number for better performance.");
        }



        this.capacity = capacity;
        this.loadFactor = loadFactor;
        this.buckets = createBucketArray(capacity);
        this.locks = createLockArray(capacity);
    }

    private boolean isPrime(int number) {
        if (number <= 1) {
            return false;
        }
        if (number <= 3) {
            return true;
        }
        if (number % 2 == 0 || number % 3 == 0) {
            return false;
        }
        for (int i = 5; i * i <= number; i += 6) {
            if (number % i == 0 || number % (i + 2) == 0) {
                return false;
            }
        }
        return true;
    }


    // Private method to create the bucket array
    private LinkedList<Entry<K, V>>[] createBucketArray(int capacity) {
        LinkedList<Entry<K, V>>[] buckets = new LinkedList[capacity];
        for (int i = 0; i < capacity; i++) {
            buckets[i] = new LinkedList<>();
        }
        return buckets;
    }

    // Private method to create the lock array
    private ReentrantLock[] createLockArray(int capacity) {
        ReentrantLock[] locks = new ReentrantLock[capacity];
        for (int i = 0; i < capacity; i++) {
            locks[i] = new ReentrantLock();
        }
        return locks;
    }


    // Adds or updates a key-value pair in the map. If the key already exists, updates its associated value.
    @Override
    public V put(K key, V value) {

        int bucketIndex = getBucketIndex(key);
        Entry<K, V> newEntry = new Entry<>(key, value);

        LinkedList<Entry<K, V>> bucket = buckets[bucketIndex];
        ReentrantLock lock = locks[bucketIndex];

        lock.lock();
        try {
            boolean keyExists = false;

            Iterator<Entry<K, V>> iterator = bucket.iterator();
            while (iterator.hasNext()) {
                Entry<K, V> entry = iterator.next();

                if (entry.getKey().equals(key)) {
                    V oldValue = entry.getValue();
                    entry.setValue(value);
                    keyExists = true;
                    return oldValue;
                }
            }

            if (!keyExists) {
                bucket.add(newEntry);
                totalNumberOfEntries++;;

                // Check if needed to do rehashing
                if ((float) totalNumberOfEntries / capacity > loadFactor) {
                    rehash();
                }
            }
        } finally {
            lock.unlock();
        }

        return null;
    }

    public V getValue(K key) {
        int bucketIndex = getBucketIndex(key);
//        System.out.println("Hash Value in getValue method : " + bucketIndex);

        LinkedList<Entry<K, V>> bucket = buckets[bucketIndex];
        synchronized (bucket) {
            Iterator<Entry<K, V>> iterator = bucket.iterator();
            while (iterator.hasNext()) {
                Entry<K, V> entry = iterator.next();
                if (entry.getKey().equals(key)) {
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

//        System.out.println("Hash Value in replace method : " + bucketIndex);

        LinkedList<Entry<K, V>> bucket = buckets[bucketIndex];
        synchronized (bucket) {
            Iterator<Entry<K, V>> iterator = bucket.iterator();

            while (iterator.hasNext()) {
                Entry<K, V> entry = iterator.next();
                if (entry.getKey().equals(key)) {
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
                    totalNumberOfEntries--;
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

            Iterator<Entry<K, V>> iterator = bucket.iterator();
            while (iterator.hasNext()) {
                Entry<K, V> entry = iterator.next();
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
        // Linked list implementation doesn't have a fixed capacity
        return false;
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

    public int getTotalNumberOfEntries(){
        return totalNumberOfEntries;
    }

    //    Rehash the map when load factor threshold is exceeded
    private void rehash() {
        int newCapacity = capacity * 2;

        LinkedList<Entry<K, V>>[] newBuckets = new LinkedList[newCapacity];
        ReentrantLock[] newLocks = new ReentrantLock[newCapacity];

        for (int i = 0; i < newCapacity; i++) {
            newBuckets[i] = new LinkedList<>();
            newLocks[i] = new ReentrantLock();
        }

        // Iterate every bucket in hash map
        for (LinkedList<Entry<K, V>> bucket : buckets) {
            Iterator<Entry<K, V>> iterator = bucket.iterator();

            // Iterate the linked list in the bucket
            while (iterator.hasNext()) {

                // get entry in the current node
                Entry<K, V> entry = iterator.next();

                int newBucketIndex = Math.abs(entry.getKey().hashCode()) % newCapacity;
                LinkedList<Entry<K, V>> newBucket = newBuckets[newBucketIndex];
                ReentrantLock newLock = newLocks[newBucketIndex];

                newLock.lock();
                try {
                    newBucket.add(entry);
                } finally {
                    newLock.unlock();
                }
            }
        }

        // Update the buckets and locks arrays
        this.buckets = newBuckets;
        this.locks = newLocks;
        this.capacity = newCapacity;
    }

    // Get Hashed Value
    private int getBucketIndex(K key) {
        int hashCode = computeHashCode(key);

        // adjust hash value to fit within the bounds of array size
        return Math.abs(hashCode) % capacity;
    }

    // Default Hash Function
    // in case require to develop a custom hash function.
    private int computeHashCode(K key) {
        if (key == null) {
            return 0;
        }

        // return default integer value, generated by java object class hashing algorithm.
        return key.hashCode();
    }


    // Entry class to store key-value pairs
    private static class Entry<K, V> implements java.io.Serializable{
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

        @Override
        public String toString() {
            return "Entry{" +
                    "key=" + key +
                    ", value=" + value +
                    '}';
        }
    }
}
