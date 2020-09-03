package com.leondesilva.cache.inmemory.strategy;

import java.io.Serializable;

/**
 * Interface to represent the eviction strategy.
 *
 * @param <K> the type of the key
 * @param <V> the type of value
 */
public interface EvictionStrategy<K extends Serializable, V extends Serializable> {
    /**
     * Method to put the key and the value.
     *
     * @param key   the key
     * @param value the value
     */
    void put(K key, V value);

    /**
     * Method to get the value for a given key.
     *
     * @param key the key
     * @return the value
     */
    V get(K key);

    /**
     * Method to delete an entry for a given key.
     *
     * @param key the key to delete
     */
    void delete(K key);

    /**
     * Method to delete all the keys.
     *
     */
    void deleteAll();

    /**
     * Method to check whether the key contains in cache
     *
     * @param key the key
     * @return true of contains and false if not
     */
    boolean containsKey(K key);

    /**
     * Method to get the size of the cache.
     *
     * @return the size of the cache
     */
    int getSize();
}
