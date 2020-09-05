package com.leondesilva.jlcache;

import com.leondesilva.jlcache.enumeration.CacheEvictionType;
import com.leondesilva.jlcache.exceptions.CacheException;
import com.leondesilva.jlcache.pojo.MetaData;
import com.leondesilva.jlcache.strategy.EvictionStrategy;
import com.leondesilva.jlcache.strategy.EvictionStrategyFactory;

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
     * @throws CacheException if the eviction type is invalid or if the cache initialization fails
     */
    public EvictableCache(Cache<K, V> cache, int maxEntrySize, CacheEvictionType cacheEvictionType) throws CacheException {
        this.cacheEvictionStrategy = EvictionStrategyFactory.create(cache, maxEntrySize, cacheEvictionType);
    }

    /**
     * Method to put the key and value to the cache.
     *
     * @param key   the key
     * @param value the value
     * @throws CacheException if an error occurs when trying to run a caching related task
     */
    public void put(K key, V value) throws CacheException {
        cacheEvictionStrategy.put(key, value);
    }

    /**
     * Method to get the value for a given key.
     *
     * @param key the key to retrieve
     * @return the value for the given key
     * @throws CacheException if an error occurs when trying to run a caching related task
     */
    public V get(K key) throws CacheException {
        return cacheEvictionStrategy.get(key);
    }

    /**
     * Method to delete the key.
     *
     * @param key the key to be deleted
     * @throws CacheException if an error occurs when trying to run a caching related task
     */
    public void delete(K key) throws CacheException {
        cacheEvictionStrategy.delete(key);
    }

    /**
     * Method to delete all the keys and values
     *
     * @throws CacheException if an error occurs when trying to run a caching related task
     */
    public void deleteAll() throws CacheException {
        cacheEvictionStrategy.deleteAll();
    }

    /**
     * Method to check whether the key does contain in the cache.
     *
     * @param key the key
     * @return true if contains and false if not
     * @throws CacheException if an error occurs when trying to run a caching related task
     */
    public boolean containsKey(K key) throws CacheException {
        return cacheEvictionStrategy.containsKey(key);
    }

    /**
     * Method to get the cache size.
     *
     * @return the cache size
     * @throws CacheException if an error occurs when trying to run a caching related task
     */
    public int getSize() throws CacheException {
        return cacheEvictionStrategy.getSize();
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
