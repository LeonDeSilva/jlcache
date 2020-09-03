package com.leondesilva.cache.inmemory;

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
     */
    void put(K key, V value);

    /**
     * Method to get the value for a given key.
     *
     * @param key the key to retrieve
     * @return the value for the given key
     */
    V get(K key);

    /**
     * Method to delete the key.
     *
     * @param key the key to be deleted
     */
    void delete(K key);

    /**
     * Method to delete all the keys and values
     *
     */
    void deleteAll();

    /**
     * Method to check whether the key does contain in the cache.
     *
     * @param key the key
     * @return true if contains and false if not
     */
    boolean containsKey(K key);

    /**
     * Method to get the cache size.
     *
     * @return the cache size
     */
    int getSize();

    /**
     * Method to store meta data.
     *
     * @param metaData the meta data
     */
    void storeMetaData(MetaData metaData);

    /**
     * Method to get the meta data.
     *
     * @return the meta data
     */
    MetaData getMetaData();
}
