package com.leondesilva.cache.inmemory.strategy;

import com.leondesilva.cache.inmemory.Cache;
import com.leondesilva.cache.inmemory.enumeration.CacheEvictionType;
import com.leondesilva.cache.inmemory.exceptions.CacheException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.core.Is.is;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;

/**
 * Class to test EvictionStrategyFactory.
 */
public class EvictionStrategyFactoryTest {
    private Cache<String, String> cache;

    /**
     * Setup method.
     *
     */
    @BeforeEach
    public void setup() {
        cache = mock(Cache.class);
    }

    /**
     * Test to verify that LRU strategy is correctly created by the factory when type is passed as LRU.
     * @throws CacheException if an error occurs
     */
    @Test
    public void should_create_the_LRU_strategy_correctly() throws CacheException {
        EvictionStrategy<String, String> strategy = EvictionStrategyFactory.create(cache, 10, CacheEvictionType.LRU);
        assertThat(strategy.getClass(), is(equalTo(LRUEvictionStrategy.class)));
    }

    /**
     * Test to verify that LFU strategy is correctly created by the factory when type is passed as LFU.
     * @throws CacheException if an error occurs
     */
    @Test
    public void should_create_the_LFU_strategy_correctly() throws CacheException {
        EvictionStrategy<String, String> strategy = EvictionStrategyFactory.create(cache, 10, CacheEvictionType.LFU);
        assertThat(strategy.getClass(), is(equalTo(LFUEvictionStrategy.class)));
    }

    /**
     * Test to verify that cache exception is thrown if the type is passe as null.
     */
    @Test
    public void should_throw_exception_when_eviction_type_is_null() {
        assertThrows(CacheException.class, () -> EvictionStrategyFactory.create(cache, 10, null));
    }
}