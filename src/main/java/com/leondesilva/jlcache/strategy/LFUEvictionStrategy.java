package com.leondesilva.jlcache.strategy;

import com.leondesilva.jlcache.Cache;

import java.io.Serializable;

/**
 * Class to represent the LFU cache eviction strategy.
 *
 * @param <K> the type of the key
 * @param <V> the type of the value
 */
public class LFUEvictionStrategy<K extends Serializable, V extends Serializable> implements EvictionStrategy<K, V> {

    /**
     * Constructor to instantiate the LFUEvictionStrategy.
     *
     * @param cache        the cache
     * @param maxEntrySize the max entry size
     */
    public LFUEvictionStrategy(Cache<K, V> cache, int maxEntrySize) {
        // NOTE: NOT Implemented as the current selected flow to be implemented is the LRU.
    }

    /**
     * Method to put the key and the value.
     *
     * @param key   the key
     * @param value the value
     */
    @Override
    public void put(K key, V value) {
        // NOTE: NOT Implemented as the current selected flow to be implemented is the LRU.
    }

    /**
     * Method to get the value for a given key.
     *
     * @param key the key
     * @return the value
     */
    @Override
    public V get(K key) {
        // NOTE: NOT Implemented as the current selected flow to be implemented is the LRU.
        return null;
    }

    /**
     * Method to delete an entry for a given key.
     *
     * @param key the key to delete
     */
    @Override
    public void delete(K key) {
        // NOTE: NOT Implemented as the current selected flow to be implemented is the LRU.
    }

    /**
     * Method to delete all the keys.
     */
    @Override
    public void deleteAll() {
        // NOTE: NOT Implemented as the current selected flow to be implemented is the LRU.
    }

    /**
     * Method to check whether the key contains in cache
     *
     * @param key the key
     * @return true of contains and false if not
     */
    @Override
    public boolean containsKey(K key) {
        // NOTE: NOT Implemented as the current selected flow to be implemented is the LRU.
        return false;
    }

    /**
     * Method to get the size of the cache.
     *
     * @return the size of the cache
     */
    @Override
    public int getSize() {
        // NOTE: NOT Implemented as the current selected flow to be implemented is the LRU.
        return 0;
    }
}
