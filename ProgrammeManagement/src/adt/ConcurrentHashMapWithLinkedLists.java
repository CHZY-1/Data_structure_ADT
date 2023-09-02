package adt;

//Author: Chan Zhi Yang
import java.io.Serializable;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.concurrent.locks.ReentrantLock;

public class ConcurrentHashMapWithLinkedLists<K, V> implements MapInterface<K, V>, Serializable {

    private static final int DEFAULT_CAPACITY = 31;
    private static final float DEFAULT_LOAD_FACTOR = 0.75f;

    private LinkedList<Entry<K, V>>[] buckets;
    private ReentrantLock[] locks;
    private int capacity;
    private final float loadFactor;

    private int totalNumberOfEntries;

    // Default constructor
    public ConcurrentHashMapWithLinkedLists() {
        this(DEFAULT_CAPACITY, DEFAULT_LOAD_FACTOR);
    }

    // Constructor with custom capacity
    public ConcurrentHashMapWithLinkedLists(int capacity) {
        this(capacity, DEFAULT_LOAD_FACTOR);
    }

    // Constructor with custom capacity and load factor
    public ConcurrentHashMapWithLinkedLists(int capacity, float loadFactor) {
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

    // Check if the number is prime number
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

    // Method to create the bucket array
    private LinkedList<Entry<K, V>>[] createBucketArray(int capacity) {
        LinkedList<Entry<K, V>>[] buckets = new LinkedList[capacity];
        for (int i = 0; i < capacity; i++) {
            buckets[i] = new LinkedList<>();
        }
        return buckets;
    }

    // Method to create the lock array
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

        // Hashed Index
        int bucketIndex = getBucketIndex(key);
        Entry<K, V> newEntry = new Entry<>(key, value);

        // Get specific bucket based on hashed index
        LinkedList<Entry<K, V>> bucket = buckets[bucketIndex];

        // Get the lock for that bucket
        ReentrantLock lock = locks[bucketIndex];

        lock.lock();
        try {

            // Replace the value if same key already exists
            // Remove this block to allow duplicate key store in the linked list
            Iterator<Entry<K, V>> iterator = bucket.iterator();
            while (iterator.hasNext()) {
                Entry<K, V> entry = iterator.next();

                // if same key is found
                if (entry.getKey().equals(key)) {
                    // replace value
                    V oldValue = entry.getValue();
                    entry.setValue(value);
                    return oldValue;
                }
            }

            bucket.add(newEntry);
            totalNumberOfEntries++;

            // Check if needed to do rehashing
            if ((float) totalNumberOfEntries / capacity > loadFactor) {
                rehash();
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

        synchronized (buckets[bucketIndex]) {
            Iterator<Entry<K, V>> iterator = bucket.iterator();

            // iterate through bucket
            while (iterator.hasNext()) {
                Entry<K, V> entry = iterator.next();
                if (entry.getKey().equals(key)) {
                    // return value if the key is found
                    return entry.getValue();
                }
            }
        }

        // key not exists
        return null;
    }

    //  Return default value if key not exists
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

        synchronized (buckets[bucketIndex]) {
            Iterator<Entry<K, V>> iterator = bucket.iterator();

            // Iterate through bucket
            while (iterator.hasNext()) {
                Entry<K, V> entry = iterator.next();

                // if same key is found
                if (entry.getKey().equals(key)) {

                    //replace the value
                    entry.setValue(value);
                    return true;
                }
            }
        }

        return false;
    }

    @Override
    public V remove(K key) {

        // Hashed Index
        int bucketIndex = getBucketIndex(key);

        // Get specific bucket based on hashed index
        LinkedList<Entry<K, V>> bucket = buckets[bucketIndex];

        synchronized (buckets[bucketIndex]) {
            Iterator<Entry<K, V>> iterator = bucket.iterator();

            // Iterate through bucket
            while (iterator.hasNext()) {
                Entry<K, V> entry = iterator.next();

                // if the entry is not null and the key is already exist in the Hash Map
                if (entry != null && entry.getKey().equals(key)) {

                    // Remove the Node
                    iterator.remove();
                    totalNumberOfEntries--;

                    if (bucket.isEmpty()) {
                        // Reinitialize the bucket if it is empty
                        buckets[bucketIndex] = new LinkedList<>();
                    }

                    return entry.getValue();
                }
            }
        }

        return null;
    }

    // Check if the key Exists in Map
    @Override
    public boolean containsKey(K key) {
        int bucketIndex = getBucketIndex(key);

        LinkedList<Entry<K, V>> bucket = buckets[bucketIndex];
        synchronized (buckets[bucketIndex]) {

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

        // Iterate through all the buckets
        for (LinkedList<Entry<K, V>> bucket : buckets) {
            synchronized (bucket) {
                if (!bucket.isEmpty()) {
                    // Return False if found any bucket is not empty
                    return false;
                }
            }
        }

        // Return True if all the bucket is empty
        return true;
    }

    @Override
    public boolean isFull() {
        // The capacity will expand automatically
        return false;
    }

    @Override
    public int size() {
        return totalNumberOfEntries;
    }

    // Clear All bucket
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

        if (destination instanceof ConcurrentHashMapWithLinkedLists<K, V> destMap) {

            for (LinkedList<Entry<K, V>> bucket : buckets) {
                synchronized (bucket) {
                    Iterator<Entry<K, V>> iterator = bucket.iterator();

                    while (iterator.hasNext()) {
                        Entry<K, V> entry = iterator.next();

                        // Copy the entry into destination
                        destMap.put(entry.getKey(), entry.getValue());
                    }
                }
            }
        }
    }

    public int getTotalCapacity(){
        return capacity;
    }

    //    Rehash the map when load factor exceed threshold
    private void rehash() {
        System.out.println("Rehashing");
        int newCapacity = capacity * 2; // Double the capacity

        LinkedList<Entry<K, V>>[] newBuckets = new LinkedList[newCapacity];
        ReentrantLock[] newLocks = new ReentrantLock[newCapacity];

        // Reinitialize buckets and locks arrays
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

                // Calculate the new bucket index using the updated hash code
                int newBucketIndex = Math.abs(entry.getKey().hashCode()) % newCapacity;

//                System.out.println("old hash value : " + entry.getKey() + "; new hash value : " + newBucketIndex);
                LinkedList<Entry<K, V>> newBucket = newBuckets[newBucketIndex];
                ReentrantLock newLock = newLocks[newBucketIndex];

                // Lock it, add entry to the new bucket, after that unlock it.
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
    // In case require to develop a custom hash function.
    private int computeHashCode(K key) {
        if (key == null) {
            return 0;
        }

        // return default integer value, generated by java object class hashing algorithm.
        return key.hashCode();
    }


    // Iterator
    public Iterator<K> iteratorWithKeys() {
        return new LinkedHashMapIterator();
    }

    private class LinkedHashMapIterator implements Iterator<K>, Serializable {
        private int currentBucketIndex;
        private Iterator<Entry<K, V>> currentBucketIterator;

        public LinkedHashMapIterator() {
            currentBucketIndex = 0;
            moveToNextBucket();
        }

        private void moveToNextBucket() {
            while (currentBucketIndex < capacity) {
                currentBucketIterator = buckets[currentBucketIndex].iterator();

                // found non-empty bucket
                if (currentBucketIterator.hasNext()) {
                    return;
                }
                currentBucketIndex++;
            }
        }

        @Override
        public boolean hasNext() {
            return currentBucketIndex < capacity;
        }

        @Override
        public K next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            Entry<K, V> entry = currentBucketIterator.next();
            if (!currentBucketIterator.hasNext()) {
                currentBucketIndex++; // Move to the next bucket
                moveToNextBucket(); // Move to the next non-empty bucket
            }
            return entry.getKey();
        }
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
