package com.leondesilva.cache.inmemory;

import com.leondesilva.cache.inmemory.exceptions.CacheException;
import com.leondesilva.cache.inmemory.pojo.MetaData;

import java.io.Serializable;

/**
 * Interface to represent the cache.
 *
 * @param <K> the key type
 * @param <V> the value type
 */
public interface Cache<K extends Serializable, V extends Serializable> {

    /**
     * Method to put the key and value to the cache.
     *
     * @param key   the key
     * @param value the value
     * @throws CacheException if an error occurs when trying to run a caching related task
     */
    void put(K key, V value) throws CacheException;

    /**
     * Method to get the value for a given key.
     *
     * @param key the key to retrieve
     * @return the value for the given key
     * @throws CacheException if an error occurs when trying to run a caching related task
     */
    V get(K key) throws CacheException;

    /**
     * Method to delete the key.
     *
     * @param key the key to be deleted
     * @throws CacheException if an error occurs when trying to run a caching related task
     */
    void delete(K key) throws CacheException;

    /**
     * Method to delete all the keys and values
     * @throws CacheException if an error occurs when trying to run a caching related task
     */
    void deleteAll() throws CacheException;

    /**
     * Method to check whether the key does contain in the cache.
     *
     * @param key the key
     * @return true if contains and false if not
     * @throws CacheException if an error occurs when trying to run a caching related task
     */
    boolean containsKey(K key) throws CacheException;

    /**
     * Method to get the cache size.
     *
     * @return the cache size
     * @throws CacheException if an error occurs when trying to run a caching related task
     */
    int getSize() throws CacheException;

    /**
     * Method to store meta data.
     *
     * @param metaData the meta data
     * @throws CacheException if an error occurs when trying to run a caching related task
     */
    void storeMetaData(MetaData metaData) throws CacheException;

    /**
     * Method to get the meta data.
     *
     * @return the meta data
     * @throws CacheException if an error occurs when trying to run a caching related task
     */
    MetaData getMetaData() throws CacheException;
}
