package com.leondesilva.jlcache;

import com.leondesilva.jlcache.exceptions.CacheException;
import com.leondesilva.jlcache.pojo.MetaData;

import java.io.Serializable;

/**
 * Class to represent the two level cache.
 *
 * @param <K> the type of the key
 * @param <V> the type of the value
 */
public class TwoLevelCache<K extends Serializable, V extends Serializable> implements Cache<K, V> {
    private Cache<K, V> level1Cache;
    private Cache<K, V> level2Cache;
    private MetaData metaData;

    /**
     * Constructor to instantiate the two level cache.
     *
     * @param level1Cache the level 1 cache
     * @param level2Cache the level 2 cache
     */
    public TwoLevelCache(Cache<K, V> level1Cache, Cache<K, V> level2Cache) {
        this.level1Cache = level1Cache;
        this.level2Cache = level2Cache;
    }

    /**
     * Method to put the key and value to the cache.
     *
     * @param key   the key
     * @param value the value
     * @throws CacheException if an error occurs when trying to run a caching related task
     */
    @Override
    public void put(K key, V value) throws CacheException {
        level1Cache.put(key, value);
        level2Cache.put(key, value);
    }

    /**
     * Method to get the value for a given key.
     *
     * @param key the key to retrieve
     * @return the value for the given key
     * @throws CacheException if an error occurs when trying to run a caching related task
     */
    @Override
    public V get(K key) throws CacheException {
        if (level1Cache.containsKey(key)) {
            return level1Cache.get(key);
        }

        if (level2Cache.containsKey(key)) {
            return level2Cache.get(key);
        }

        return null;
    }

    /**
     * Method to delete the key.
     *
     * @param key the key to be deleted
     * @throws CacheException if an error occurs when trying to run a caching related task
     */
    @Override
    public void delete(K key) throws CacheException {
        level1Cache.delete(key);
        level2Cache.delete(key);
    }

    /**
     * Method to delete all the keys and values
     *
     * @throws CacheException if an error occurs when trying to run a caching related task
     */
    @Override
    public void deleteAll() throws CacheException {
        level1Cache.deleteAll();
        level2Cache.deleteAll();
    }

    /**
     * Method to check whether the key does contain in the cache.
     *
     * @param key the key
     * @return true if contains and false if not
     * @throws CacheException if an error occurs when trying to run a caching related task
     */
    @Override
    public boolean containsKey(K key) throws CacheException {
        if (level1Cache.containsKey(key)) {
            return true;
        }

        return level2Cache.containsKey(key);
    }

    /**
     * Method to get the cache size.
     *
     * @return the cache size
     * @throws CacheException if an error occurs when trying to run a caching related task
     */
    @Override
    public int getSize() throws CacheException {
        return level1Cache.getSize() + level2Cache.getSize();
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
