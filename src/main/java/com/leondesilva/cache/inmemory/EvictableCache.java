package com.leondesilva.cache.inmemory;

import com.leondesilva.cache.inmemory.enumeration.CacheEvictionType;
import com.leondesilva.cache.inmemory.exceptions.UnknownEvictionTypeException;
import com.leondesilva.cache.inmemory.pojo.MetaData;
import com.leondesilva.cache.inmemory.strategy.EvictionStrategy;
import com.leondesilva.cache.inmemory.strategy.EvictionStrategyFactory;

import java.io.Serializable;

/**
 * Class to represent the implementation of the evictable cache.
 *
 * @param <K> the type of the key
 * @param <V> the type of the value
 */
public class EvictableCache<K extends Serializable, V extends Serializable> implements Cache<K, V> {
    private EvictionStrategy<K, V> cacheEvictionStrategy;
    private MetaData metaData;

    /**
     * Constructor to instantiate the evictable cache.
     *
     * @param cache             the cache to be used with the eviction strategy
     * @param maxEntrySize      the max entry size
     * @param cacheEvictionType the cache eviction type
     * @throws UnknownEvictionTypeException if the eviction type is invalid
     */
    public EvictableCache(Cache<K, V> cache, int maxEntrySize, CacheEvictionType cacheEvictionType) throws UnknownEvictionTypeException {
        this.cacheEvictionStrategy = EvictionStrategyFactory.create(cache, maxEntrySize, cacheEvictionType);
    }

    /**
     * Method to put the key and value to the cache.
     *
     * @param key   the key
     * @param value the value
     */
    public void put(K key, V value) {
        cacheEvictionStrategy.put(key, value);
    }

    /**
     * Method to get the value for a given key.
     *
     * @param key the key to retrieve
     * @return the value for the given key
     */
    public V get(K key) {
        return cacheEvictionStrategy.get(key);
    }

    /**
     * Method to delete the key.
     *
     * @param key the key to be deleted
     */
    public void delete(K key) {
        cacheEvictionStrategy.delete(key);
    }

    /**
     * Method to delete all the keys and values
     */
    public void deleteAll() {
        cacheEvictionStrategy.deleteAll();
    }

    /**
     * Method to check whether the key does contain in the cache.
     *
     * @param key the key
     * @return true if contains and false if not
     */
    public boolean containsKey(K key) {
        return false;
    }

    /**
     * Method to get the cache size.
     *
     * @return the cache size
     */
    public int getSize() {
        return 0;
    }

    /**
     * Method to store meta data.
     *
     * @param metaData the meta data
     */
    @Override
    public void storeMetaData(MetaData metaData) {
        this.metaData = metaData;
    }

    /**
     * Method to get the meta data.
     *
     * @return the meta data
     */
    @Override
    public MetaData getMetaData() {
        return metaData;
    }
}
