package adt;

//Author: Chan Zhi Yang

public interface MapInterface<K, V> {

    /**
     * Adds a new key-value pair to the map. If the key already exists in the map,
     * the associated value is updated.
     *
     * @param key The key to be added or replaced
     * @param value The value associated with the key
     * @return The previous value associated with the key, or 'null' if the key is new
     */
    public V put(K key, V value);

    /**
     * Retrieves the value associated with the specified key from the map.
     *
     * @param key The key to retrieve the value for
     * @return The value associated with the specified key, or ‘null’ if the key is not present.
     */
    public V getValue(K key);

    /**
     * Retrieves the value associated with the specified key, or a default value if
     * the key is not present.
     *
     * @param key The key to retrieve the value for
     * @param defaultValue The default value to return if the key is not present
     * @return The value associated with the specified key, or defaultValue if the key is not present.
     */
    public V getOrDefault(K key, V defaultValue);

    /**
     * Replaces the value associated with the specified key with the new value.
     *
     * @param key The key to replace the value for
     * @param value The new value to associate with the key
     * @return ‘true’ if the value was successfully replaced, ‘false’ if the key was not found
     */
    public boolean replace(K key, V value);

    /**
     * Remove the key-value pair associated with the specified key from the map.
     *
     * @param key The key to remove along with its value
     * @return The value that was associated with the removed key, or ‘null’ if the key was not present.
     */
    public V remove(K key);

    /**
     * Checks if the specified key exists in the map.
     *
     * @param key The key to check for existence
     * @return 'true' if the key is present in the map, 'false' otherwise
     */
    public boolean containsKey(K key);

    /**
     * Checks if the map is empty (contains no key-value pairs).
     *
     * @return ‘true’ if the map contains no key-value pairs, ‘false’ otherwise.
     */
    public boolean isEmpty();

    /**
     * Checks if the map is full (reached a predefined capacity).
     *
     * @return 'true' if the map is full, 'false' otherwise
     */
    public boolean isFull();

    /**
     * Gets the number of key-value pairs in the map.
     *
     * @return The number of key-value pairs in the map
     */
    public int size();

    /**
     * Removes all key-value pairs from the map, leaving it empty.
     */
    public void clear();

    /**
     * Copies all key-value associations from the current map to the
     * specified destination map, creating an identical data replication.
     *
     * @param destination The map to which key-value associations will be copied
     */
    public void copy(MapInterface<K, V> destination);
}
