package demo;

import com.leondesilva.jlcache.Cache;
import com.leondesilva.jlcache.CacheBuilder;
import com.leondesilva.jlcache.enumeration.CacheEvictionType;
import com.leondesilva.jlcache.exceptions.CacheBuilderException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.nio.file.Path;

/**
 * Demo class
 */
public class Demo {
    /**
     * Example for building an in-memory cache without eviction policy.
     */
    @Test
    public void example_build_in_memory_cache() throws CacheBuilderException {
        CacheBuilder cacheBuilder = new CacheBuilder();
        Cache<String, String> cache = cacheBuilder.buildInMemoryCache();
    }

    /**
     * Example for building an in-memory cache with eviction policy.
     */
    @Test
    public void example_build_in_memory_cache_with_eviction_policy() throws CacheBuilderException {
        CacheBuilder cacheBuilder = new CacheBuilder();
        Cache<String, String> cache = cacheBuilder.setEviction(10, CacheEvictionType.LRU).buildInMemoryCache();
    }

    /**
     * Example for building a file system cache without eviction policy.
     */
    @Test
    public void example_build_file_system_cache(@TempDir Path directoryLocation) throws CacheBuilderException {
        CacheBuilder cacheBuilder = new CacheBuilder();
        Cache<String, String> cache = cacheBuilder.buildFileSystemCache(directoryLocation.toString());
    }

    /**
     * Example for building a file system cache with eviction policy.
     */
    @Test
    public void example_build_file_system_cache_with_eviction_policy(@TempDir Path directoryLocation) throws CacheBuilderException {
        CacheBuilder cacheBuilder = new CacheBuilder();
        Cache<String, String> cache = cacheBuilder.setEviction(10, CacheEvictionType.LRU).buildFileSystemCache(directoryLocation.toString());
    }

    /**
     * Example for building a two level cache without eviction policy.
     */
    @Test
    public void example_build_two_level_cache_without_eviction_policy(@TempDir Path directoryLocation) throws CacheBuilderException {
        CacheBuilder cacheBuilder = new CacheBuilder();
        Cache<String, String> cache = cacheBuilder.twoLevelCache()
                                                  .setLevel2CacheFolderPath(directoryLocation.toString())
                                                  .build();
    }

    /**
     * Example for building a two level cache with eviction policy.
     * NOTE: If required you can set eviction policies for both of the cache levels or if required to only one level.
     */
    @Test
    public void example_build_two_level_cache_with_eviction_policy(@TempDir Path directoryLocation) throws CacheBuilderException {
        CacheBuilder cacheBuilder = new CacheBuilder();
        Cache<String, String> cache = cacheBuilder.twoLevelCache()
                .setLevel1CacheEviction(10, CacheEvictionType.LRU)
                .setLevel2CacheEviction(10, CacheEvictionType.LRU)
                .setLevel2CacheFolderPath(directoryLocation.toString())
                .build();
    }
}
