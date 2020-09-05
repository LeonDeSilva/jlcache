package com.leondesilva.jlcache;

import com.leondesilva.jlcache.enumeration.CacheEvictionType;
import com.leondesilva.jlcache.exceptions.CacheBuilderException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.nio.file.Path;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.core.Is.is;

/**
 * Class to test cache builder.
 */
public class CacheBuilderTest {
    /**
     * Test to verify that only an in-memory cache is built when eviction is not set.
     *
     * @throws CacheBuilderException if an error occurs when building the cache
     */
    @Test
    public void should_build_an_in_memory_cache_only_if_eviction_is_not_set() throws CacheBuilderException {
        CacheBuilder cacheBuilder = new CacheBuilder();
        Cache<String, String> cache = cacheBuilder.buildInMemoryCache();
        assertThat(cache.getClass(), is(equalTo(InMemoryCache.class)));
    }

    /**
     * Test to verify that only an evictable in-memory cache is built when eviction is set.
     *
     * @throws CacheBuilderException if an error occurs when building the cache
     */
    @Test
    public void should_build_an_evictable_in_memory_cache_if_eviction_is_set() throws CacheBuilderException {
        CacheBuilder cacheBuilder = new CacheBuilder();
        Cache<String, String> cache = cacheBuilder.setEviction(10, CacheEvictionType.LRU).buildInMemoryCache();
        assertThat(cache.getClass(), is(equalTo(EvictableCache.class)));
    }

    /**
     * Test to verify that only a file system cache is built when eviction is not set.
     *
     * @throws CacheBuilderException if an error occurs when building the cache
     */
    @Test
    public void should_build_a_file_system_cache_only_if_eviction_is_not_set(@TempDir Path tempDirPath) throws CacheBuilderException {
        CacheBuilder cacheBuilder = new CacheBuilder();
        Cache<String, String> cache = cacheBuilder.buildFileSystemCache(tempDirPath.toString());
        assertThat(cache.getClass(), is(equalTo(FileSystemCache.class)));
    }

    /**
     * Test to verify that a evictable file system cache is built when eviction is set.
     *
     * @throws CacheBuilderException if an error occurs when building the cache
     */
    @Test
    public void should_build_an_evictable_in_memory_cache_if_eviction_is_set(@TempDir Path tempDirPath) throws CacheBuilderException {
        CacheBuilder cacheBuilder = new CacheBuilder();
        Cache<String, String> cache = cacheBuilder.setEviction(10, CacheEvictionType.LRU).buildFileSystemCache(tempDirPath.toString());
        assertThat(cache.getClass(), is(equalTo(EvictableCache.class)));
    }

    /**
     * Test to verify that a two level cache is built correctly.
     *
     * @throws CacheBuilderException if an error occurs when building the cache
     */
    @Test
    public void should_build_a_two_level_cache_correctly() throws CacheBuilderException {
        CacheBuilder cacheBuilder = new CacheBuilder();
        Cache<String, String> cache = cacheBuilder.twoLevelCache()
                .setLevel1CacheEviction(5, CacheEvictionType.LRU)
                .setLevel2CacheEviction(5, CacheEvictionType.LRU)
                .build();
        assertThat(cache.getClass(), is(equalTo(TwoLevelCache.class)));
    }

    /**
     * Test to verify that a two level cache is built correctly when folder path is set.
     *
     * @throws CacheBuilderException if an error occurs when building the cache
     */
    @Test
    public void should_build_a_two_level_cache_correctly_when_folder_path_is_set(@TempDir Path tempDirPath) throws CacheBuilderException {
        CacheBuilder cacheBuilder = new CacheBuilder();
        Cache<String, String> cache = cacheBuilder.twoLevelCache()
                .setLevel1CacheEviction(5, CacheEvictionType.LRU)
                .setLevel2CacheEviction(5, CacheEvictionType.LRU)
                .setLevel2CacheFolderPath(tempDirPath.toString())
                .build();
        assertThat(cache.getClass(), is(equalTo(TwoLevelCache.class)));
    }
}