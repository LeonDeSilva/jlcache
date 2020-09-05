package com.leondesilva.jlcache.strategy;

import com.leondesilva.jlcache.Cache;
import com.leondesilva.jlcache.exceptions.CacheException;
import com.leondesilva.jlcache.pojo.LRUEvictionMetaData;

import java.io.Serializable;
import java.util.LinkedList;

/**
 * Class to represent the LRU cache eviction strategy.
 *
 * @param <K> the type of the key
 * @param <V> the type of the value
 */
public class LRUEvictionStrategy<K extends Serializable, V extends Serializable> implements EvictionStrategy<K, V> {
    private Cache<K, V> cache;
    private int maxEntrySize;

    /**
     * Constructor to instantiate the LRUEvictionStrategy.
     *
     * @param cache        the cache
     * @param maxEntrySize the max entry size
     * @throws CacheException if an error occurs when trying to run a caching related task
     */
    public LRUEvictionStrategy(Cache<K, V> cache, int maxEntrySize) throws CacheException {
        this.cache = cache;
        this.maxEntrySize = maxEntrySize;

        LRUEvictionMetaData<K> metaData = new LRUEvictionMetaData<>();
        storeMetaData(metaData);
    }

    /**
     * Method to put the key and the value.
     *
     * @param key   the key
     * @param value the value
     */
    @Override
    public void put(K key, V value) throws CacheException {
        LRUEvictionMetaData<K> metaData = retrieveMetaData();
        LinkedList<K> nodeList = metaData.getNodeList();

        if (cache.containsKey(key)) {
            nodeList.remove(key);
        } else {
            if (nodeList.size() == maxEntrySize) {
                K lastKey = nodeList.removeLast();
                cache.delete(lastKey);
            }
        }

        cache.put(key, value);
        nodeList.add(0, key);
        storeMetaData(metaData);
    }

    /**
     * Method to get the value for a given key.
     *
     * @param key the key
     * @return the value
     */
    @Override
    public V get(K key) throws CacheException {
        LRUEvictionMetaData<K> metaData = retrieveMetaData();
        if (cache.containsKey(key)) {
            metaData.getNodeList().remove(key);
            metaData.getNodeList().add(0, key);
        }

        return cache.get(key);
    }

    /**
     * Method to delete an entry for a given key.
     *
     * @param key the key to delete
     */
    @Override
    public void delete(K key) throws CacheException {
        cache.delete(key);

        LRUEvictionMetaData<K> metaData = retrieveMetaData();
        metaData.getNodeList().remove(key);
        storeMetaData(metaData);
    }

    /**
     * Method to delete all the keys.
     */
    @Override
    public void deleteAll() throws CacheException {
        LRUEvictionMetaData<K> metaData = retrieveMetaData();
        cache.deleteAll();
        metaData.getNodeList().clear();
        storeMetaData(metaData);
    }

    /**
     * Method to check whether the key contains in cache
     *
     * @param key the key
     * @return true of contains and false if not
     */
    @Override
    public boolean containsKey(K key) throws CacheException {
        return cache.containsKey(key);
    }

    /**
     * Method to get the size of the cache.
     *
     * @return the size of the cache
     */
    @Override
    public int getSize() throws CacheException {
        return cache.getSize();
    }

    /**
     * Method to store the meta data.
     *
     * @param metaData the meta data to be stored
     */
    private void storeMetaData(LRUEvictionMetaData<K> metaData) throws CacheException {
        this.cache.storeMetaData(metaData);
    }

    /**
     * Method to retrieve the meta data.
     *
     * @return the meta data
     */
    private LRUEvictionMetaData<K> retrieveMetaData() throws CacheException {
        return (LRUEvictionMetaData) this.cache.getMetaData();
    }
}
