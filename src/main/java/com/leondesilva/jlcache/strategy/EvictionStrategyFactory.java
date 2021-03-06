package com.leondesilva.jlcache.strategy;

import com.leondesilva.jlcache.Cache;
import com.leondesilva.jlcache.enumeration.CacheEvictionType;
import com.leondesilva.jlcache.exceptions.CacheException;

import java.io.Serializable;

/**
 * Class to represent the eviction strategy factory.
 */
public final class EvictionStrategyFactory {
    /**
     * Private constructor
     */
    private EvictionStrategyFactory() {
        // private constructor
    }

    /**
     * Method to create the eviction strategy.
     *
     * @param cache             the cache to be used with the eviction strategy
     * @param maxEntrySize      the max entry size
     * @param cacheEvictionType the cache eviction type
     * @param <K>               the type of the key
     * @param <V>               the type of the value
     * @return the eviction strategy
     * @throws CacheException if the eviction type is invalid or if strategy initialization error occurs
     */
    public static <K extends Serializable, V extends Serializable> EvictionStrategy<K, V> create(Cache<K, V> cache,
                                                                                                 int maxEntrySize,
                                                                                                 CacheEvictionType cacheEvictionType) throws CacheException {
        if (cacheEvictionType == null) {
            throw new CacheException("Eviction type cannot be null.");
        }

        switch (cacheEvictionType) {
            case LRU:
                return new LRUEvictionStrategy<>(cache, maxEntrySize);
            case LFU:
                return new LFUEvictionStrategy<>(cache, maxEntrySize);
            default:
                throw new CacheException("Invalid eviction type.");
        }
    }
}
