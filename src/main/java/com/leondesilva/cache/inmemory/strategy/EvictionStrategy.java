package com.leondesilva.cache.inmemory.strategy;

import com.leondesilva.cache.inmemory.exceptions.CacheException;

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
     * @throws CacheException if an error occurs when trying to run a caching related task
     */
    void put(K key, V value) throws CacheException;

    /**
     * Method to get the value for a given key.
     *
     * @param key the key
     * @return the value
     * @throws CacheException if an error occurs when trying to run a caching related task
     */
    V get(K key) throws CacheException;

    /**
     * Method to delete an entry for a given key.
     *
     * @param key the key to delete
     * @throws CacheException if an error occurs when trying to run a caching related task
     */
    void delete(K key) throws CacheException;

    /**
     * Method to delete all the keys.
     *
     * @throws CacheException if an error occurs when trying to run a caching related task
     */
    void deleteAll() throws CacheException;

    /**
     * Method to check whether the key contains in cache
     *
     * @param key the key
     * @return true of contains and false if not
     * @throws CacheException if an error occurs when trying to run a caching related task
     */
    boolean containsKey(K key) throws CacheException;

    /**
     * Method to get the size of the cache.
     *
     * @return the size of the cache
     * @throws CacheException if an error occurs when trying to run a caching related task
     */
    int getSize() throws CacheException;
}
