package com.leondesilva.jlcache;

import com.leondesilva.jlcache.enumeration.CacheEvictionType;
import com.leondesilva.jlcache.exceptions.CacheException;
import com.leondesilva.jlcache.pojo.MetaData;
import com.leondesilva.jlcache.strategy.EvictionStrategy;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.internal.util.reflection.Whitebox;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.*;

/**
 * Class to test evictable cache.
 */
public class EvictableCacheTest {
    private EvictableCache<String, String> evictableCache;
    private Cache<String, String> cache;
    private EvictionStrategy<String, String> strategy;

    /**
     * Setup method.
     *
     */
    @BeforeEach
    public void setup() throws CacheException {
        cache = mock(Cache.class);
        strategy = mock(EvictionStrategy.class);
        evictableCache = new EvictableCache<>(cache, 10, CacheEvictionType.LFU);
        Whitebox.setInternalState(evictableCache, "cacheEvictionStrategy", strategy);
    }

    /**
     * Test to verify that the strategy put method is called when evictable cache put method is called.
     *
     * @throws CacheException if an error occurs when dealing with the cache
     */
    @Test
    public void should_call_the_strategy_when_evictable_cache_put_is_called() throws CacheException {
        evictableCache.put("A", "B");
        verify(strategy, times(1)).put("A", "B");
    }

    /**
     * Test to verify that the strategy get method is called when evictable cache get method is called.
     *
     * @throws CacheException if an error occurs when dealing with the cache
     */
    @Test
    public void should_call_the_strategy_when_evictable_cache_get_is_called() throws CacheException {
        evictableCache.get("A");
        verify(strategy, times(1)).get("A");
    }

    /**
     * Test to verify that the strategy containsKey method is called when evictable cache containsKey method is called.
     *
     * @throws CacheException if an error occurs when dealing with the cache
     */
    @Test
    public void should_call_the_strategy_when_evictable_cache_contains_is_called() throws CacheException {
        evictableCache.containsKey("A");
        verify(strategy, times(1)).containsKey("A");
    }

    /**
     * Test to verify that the strategy getSize method is called when evictable cache getSize method is called.
     *
     * @throws CacheException if an error occurs when dealing with the cache
     */
    @Test
    public void should_call_the_strategy_when_evictable_cache_get_size_is_called() throws CacheException {
        evictableCache.getSize();
        verify(strategy, times(1)).getSize();
    }

    /**
     * Test to verify that the strategy delete method is called when evictable cache delete method is called.
     *
     * @throws CacheException if an error occurs when dealing with the cache
     */
    @Test
    public void should_call_the_strategy_when_evictable_cache_delete_is_called() throws CacheException {
        evictableCache.delete("A");
        verify(strategy, times(1)).delete("A");
    }

    /**
     * Test to verify that the strategy deleteAll method is called when evictable cache deleteAll method is called.
     *
     * @throws CacheException if an error occurs when dealing with the cache
     */
    @Test
    public void should_call_the_strategy_when_evictable_cache_delete_all_is_called() throws CacheException {
        evictableCache.deleteAll();
        verify(strategy, times(1)).deleteAll();
    }

    /**
     * Test to verify that meta is stored and retrieved correctly.
     */
    @Test
    public void should_successfully_store_meta_data() {
        MetaData metaData = mock(MetaData.class);
        evictableCache.storeMetaData(metaData);

        assertThat(evictableCache.getMetaData(), is(equalTo(metaData)));
    }
}