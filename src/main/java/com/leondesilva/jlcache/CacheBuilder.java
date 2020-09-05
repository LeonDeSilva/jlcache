package com.leondesilva.jlcache;

import com.leondesilva.jlcache.enumeration.CacheEvictionType;
import com.leondesilva.jlcache.exceptions.CacheBuilderException;
import com.leondesilva.jlcache.exceptions.CacheException;

import java.io.Serializable;
import java.nio.file.FileSystems;

/**
 * Class to represent the cache builder.
 */
public class CacheBuilder {
    private int maxEntrySize;
    private CacheEvictionType cacheEvictionType;

    /**
     * Method to create an in-memory cache.
     *
     * @param <K> the type of the key
     * @param <V> the type of the value
     * @return the in memory cache that is built
     * @throws CacheBuilderException if an error occurs when building the cache
     */
    public <K extends Serializable, V extends Serializable> Cache<K, V> buildInMemoryCache() throws CacheBuilderException {
        InMemoryCache<K, V> inMemoryCache = new InMemoryCache<>();

        if (cacheEvictionType == null) {
            return inMemoryCache;
        }

        try {
            return new EvictableCache<>(inMemoryCache, this.maxEntrySize, this.cacheEvictionType);
        } catch (CacheException e) {
            throw new CacheBuilderException("Error occurred when building evictable cache.", e);
        }
    }

    /**
     * Method to build a file system cache.
     *
     * @param folderPath the folder path
     * @param <K>        the type of the key
     * @param <V>        the type of the value
     * @return the file system cache that is built
     * @throws CacheBuilderException if an error occurs when building the cache
     */
    public <K extends Serializable, V extends Serializable> Cache<K, V> buildFileSystemCache(String folderPath) throws CacheBuilderException {
        FileSystemCache<K, V> fileSystemCache;

        try {
            fileSystemCache = new FileSystemCache<>(folderPath);
        } catch (CacheException e) {
            throw new CacheBuilderException("Error occurred when building file system cache.", e);
        }

        if (cacheEvictionType == null) {
            return fileSystemCache;
        }

        try {
            return new EvictableCache<>(fileSystemCache, this.maxEntrySize, this.cacheEvictionType);
        } catch (CacheException e) {
            throw new CacheBuilderException("Error occurred when building evictable cache.", e);
        }
    }

    /**
     * Method to set the eviction policy.
     *
     * @param maxEntrySize      the max entry size
     * @param cacheEvictionType the cache eviction type
     * @return the cache builder
     */
    public CacheBuilder setEviction(int maxEntrySize, CacheEvictionType cacheEvictionType) {
        this.maxEntrySize = maxEntrySize;
        this.cacheEvictionType = cacheEvictionType;
        return this;
    }

    /**
     * Method to get the builder for two level cache.
     *
     * @return the two level cache builder
     */
    public TwoLevelCacheBuilder twoLevelCache() {
        return new TwoLevelCacheBuilder();
    }

    /**
     * Inner class to represent the two level cache builder.
     */
    public static class TwoLevelCacheBuilder {
        private int level1CacheMaxEntrySize;
        private CacheEvictionType level1CacheEvictionType;

        private int level2CacheMaxEntrySize;
        private CacheEvictionType level2CacheEvictionType;
        private String level2CacheFolderPath;

        /**
         * Method to set the level 1 cache eviction policy.
         *
         * @param maxEntrySize      the max entry size
         * @param cacheEvictionType the cache eviction policy
         * @return the two level cache builder
         */
        public TwoLevelCacheBuilder setLevel1CacheEviction(int maxEntrySize, CacheEvictionType cacheEvictionType) {
            this.level1CacheMaxEntrySize = maxEntrySize;
            this.level1CacheEvictionType = cacheEvictionType;
            return this;
        }

        /**
         * Method to set the level 2 cache eviction policy.
         *
         * @param maxEntrySize      the max entry size
         * @param cacheEvictionType the cache eviction policy
         * @return the two level cache builder
         */
        public TwoLevelCacheBuilder setLevel2CacheEviction(int maxEntrySize, CacheEvictionType cacheEvictionType) {
            this.level2CacheMaxEntrySize = maxEntrySize;
            this.level2CacheEvictionType = cacheEvictionType;
            return this;
        }

        /**
         * Method to set the level 2 cache folder path. If this is not set, the current directory will be used.
         *
         * @param folderPath the folder path
         * @return the two level cache builder
         */
        public TwoLevelCacheBuilder setLevel2CacheFolderPath(String folderPath) {
            this.level2CacheFolderPath = folderPath;
            return this;
        }

        /**
         * Method to build to two level cache.
         *
         * @param <K> the type of the key
         * @param <V> the type of the value
         * @return the two level cache that is built
         * @throws CacheBuilderException if an error occurs when building the cache
         */
        public <K extends Serializable, V extends Serializable> Cache<K, V> build() throws CacheBuilderException {
            CacheBuilder level1CacheBuilder = new CacheBuilder();
            CacheBuilder level2CacheBuilder = new CacheBuilder();

            if (level2CacheFolderPath == null) {
                level2CacheFolderPath = FileSystems.getDefault().getPath(".").toString();
            }

            Cache<K, V> level1Cache = level1CacheBuilder.setEviction(level1CacheMaxEntrySize, level1CacheEvictionType).buildInMemoryCache();
            Cache<K, V> level2Cache = level2CacheBuilder.setEviction(level2CacheMaxEntrySize, level2CacheEvictionType).buildFileSystemCache(level2CacheFolderPath);

            return new TwoLevelCache<>(level1Cache, level2Cache);
        }
    }
}
