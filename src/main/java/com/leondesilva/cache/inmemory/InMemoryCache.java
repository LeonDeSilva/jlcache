package com.leondesilva.cache.inmemory;

import com.leondesilva.cache.inmemory.pojo.MetaData;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * Class to represent the in memory cache.
 *
 * @param <K> the type of the key
 * @param <V> the type of the value
 */
public class InMemoryCache<K extends Serializable, V extends Serializable> implements Cache<K, V> {
    private Map<K, V> cacheMap;
    private MetaData metaData;

    /**
     * Constructor to instantiate in memory cache.
     */
    public InMemoryCache() {
        this.cacheMap = new HashMap<>();
    }

    /**
     * Method to put the key and value to the cache.
     *
     * @param key   the key
     * @param value the value
     */
    public void put(K key, V value) {
        this.cacheMap.put(key, value);
    }

    /**
     * Method to get the value for a given key.
     *
     * @param key the key to retrieve
     * @return the value for the given key
     */
    public V get(K key) {
        return cacheMap.get(key);
    }

    /**
     * Method to delete the key.
     *
     * @param key the key to be deleted
     */
    public void delete(K key) {
        cacheMap.remove(key);
    }

    /**
     * Method to delete all the keys and values
     */
    public void deleteAll() {
        cacheMap.clear();
    }

    /**
     * Method to check whether the key does contain in the cache.
     *
     * @param key the key
     * @return true if contains and false if not
     */
    public boolean containsKey(K key) {
        return cacheMap.containsKey(key);
    }

    /**
     * Method to get the cache size.
     *
     * @return the cache size
     */
    public int getSize() {
        return cacheMap.size();
    }

    /**
     * Method to store meta data.
     *
     * @param metaData the meta data
     */
    public void storeMetaData(MetaData metaData) {
        this.metaData = metaData;
    }

    /**
     * Method to get the meta data.
     *
     * @return the meta data
     */
    public MetaData getMetaData() {
        return this.metaData;
    }
}
