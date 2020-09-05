package com.leondesilva.jlcache;

import com.leondesilva.jlcache.exceptions.CacheException;
import com.leondesilva.jlcache.pojo.MetaData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.*;

/**
 * Class to test the two level cache.
 */
public class TwoLevelCacheTest {
    private TwoLevelCache<String, String> twoLevelCache;
    private Cache<String, String> cache1;
    private Cache<String, String> cache2;

    private static final String KEY1 = "Key1";
    private static final String KEY2 = "Key2";
    private static final String KEY3 = "Key3";
    private static final String KEY_THAT_DOES_NOT_EXIST_IN_BOTH_CACHES = "NonExistingKey";

    private static final String VALUE1 = "Value1";
    private static final String VALUE2 = "Value2";
    private static final String VALUE3 = "Value3";

    /**
     * Setup method.
     *
     */
    @BeforeEach
    public void setup() throws CacheException {
        cache1 = mock(Cache.class);
        cache2 = mock(Cache.class);

        when(cache1.get(KEY1)).thenReturn(VALUE1);
        when(cache1.get(KEY2)).thenReturn(VALUE2);
        when(cache1.containsKey(KEY1)).thenReturn(true);
        when(cache1.containsKey(KEY2)).thenReturn(true);
        when(cache1.containsKey(KEY_THAT_DOES_NOT_EXIST_IN_BOTH_CACHES)).thenReturn(false);
        when(cache1.getSize()).thenReturn(2);

        when(cache2.get(KEY1)).thenReturn(VALUE1);
        when(cache2.get(KEY2)).thenReturn(VALUE2);
        when(cache2.get(KEY3)).thenReturn(VALUE3);
        when(cache2.containsKey(KEY1)).thenReturn(true);
        when(cache2.containsKey(KEY2)).thenReturn(true);
        when(cache2.containsKey(KEY3)).thenReturn(true);
        when(cache2.containsKey(KEY_THAT_DOES_NOT_EXIST_IN_BOTH_CACHES)).thenReturn(false);
        when(cache2.getSize()).thenReturn(3);

        twoLevelCache = new TwoLevelCache<>(cache1, cache2);
    }

    /**
     * Test to verify that put method is called on both level1 and level2 caches.
     *
     * @throws CacheException if an error occurs when dealing with the cache
     */
    @Test
    public void should_call_put_method_in_both_level1_and_level2_caches_when_put_method_is_called() throws CacheException {
        twoLevelCache.put(KEY1, VALUE1);
        verify(cache1, times(1)).put(KEY1, VALUE1);
        verify(cache2, times(1)).put(KEY1, VALUE1);
    }

    /**
     * Test to verify that the value is returned when the level1 cache when it has the key.
     *
     * @throws CacheException if an error occurs when dealing with the cache
     */
    @Test
    public void should_return_value_from_the_level1_cache_when_level1_cache_has_the_key() throws CacheException {
        String result = twoLevelCache.get(KEY1);
        verify(cache1, times(1)).get(KEY1);
        verifyZeroInteractions(cache2);
        assertThat(result, is(equalTo(VALUE1)));
    }

    /**
     * Test to verify that the value is returned when the level2 cache when level1 cache does not and level2 cache has the key.
     *
     * @throws CacheException if an error occurs when dealing with the cache
     */
    @Test
    public void should_return_value_from_the_level2_cache_when_level2_cache_has_the_key() throws CacheException {
        String result = twoLevelCache.get(KEY3);
        verify(cache1, times(0)).get(KEY3);
        verify(cache2, times(1)).get(KEY3);
        assertThat(result, is(equalTo(VALUE3)));
    }

    /**
     * Test to verify that the null is returned when both caches does not have the key.
     *
     * @throws CacheException if an error occurs when dealing with the cache
     */
    @Test
    public void should_return_null_when_both_level1_and_level2_caches_does_not_have_the_key() throws CacheException {
        String result = twoLevelCache.get(KEY_THAT_DOES_NOT_EXIST_IN_BOTH_CACHES);
        verify(cache1, times(0)).get(KEY_THAT_DOES_NOT_EXIST_IN_BOTH_CACHES);
        verify(cache2, times(0)).get(KEY_THAT_DOES_NOT_EXIST_IN_BOTH_CACHES);
        assertThat(result, is(equalTo(null)));
    }

    /**
     * Test to verify that delete method is called on both level1 and level2 caches.
     *
     * @throws CacheException if an error occurs when dealing with the cache
     */
    @Test
    public void should_call_delete_method_in_both_level1_and_level2_caches_when_delete_method_is_called() throws CacheException {
        twoLevelCache.delete(KEY1);
        verify(cache1, times(1)).delete(KEY1);
        verify(cache1, times(1)).delete(KEY1);
    }

    /**
     * Test to verify that delete all method is called on both level1 and level2 caches.
     *
     * @throws CacheException if an error occurs when dealing with the cache
     */
    @Test
    public void should_call_delete_all_method_in_both_level1_and_level2_caches_when_delete_all_method_is_called() throws CacheException {
        twoLevelCache.deleteAll();
        verify(cache1, times(1)).deleteAll();
        verify(cache1, times(1)).deleteAll();
    }

    /**
     * Test to verify that true is returned when the level1 cache has the key.
     *
     * @throws CacheException if an error occurs when dealing with the cache
     */
    @Test
    public void should_return_true_if_the_level1_cache_contains_the_key() throws CacheException {
        boolean result = twoLevelCache.containsKey(KEY2);
        verify(cache1, times(1)).containsKey(KEY2);
        verify(cache2, times(0)).containsKey(KEY2);
        assertThat(result, is(equalTo(true)));
    }

    /**
     * Test to verify that true is returned when the level2 cache has the key and level1 cache does not.
     *
     * @throws CacheException if an error occurs when dealing with the cache
     */
    @Test
    public void should_return_true_if_the_level1_cache_does_not_and_level2_cache_contains_the_key() throws CacheException {
        boolean result = twoLevelCache.containsKey(KEY3);
        verify(cache1, times(1)).containsKey(KEY3);
        verify(cache2, times(1)).containsKey(KEY3);
        assertThat(result, is(equalTo(true)));
    }

    /**
     * Test to verify that false is returned when both caches does not have the key.
     *
     * @throws CacheException if an error occurs when dealing with the cache
     */
    @Test
    public void should_return_false_if_the_both_caches_does_not_contain_the_key() throws CacheException {
        boolean result = twoLevelCache.containsKey(KEY_THAT_DOES_NOT_EXIST_IN_BOTH_CACHES);
        verify(cache1, times(1)).containsKey(KEY_THAT_DOES_NOT_EXIST_IN_BOTH_CACHES);
        verify(cache2, times(1)).containsKey(KEY_THAT_DOES_NOT_EXIST_IN_BOTH_CACHES);
        assertThat(result, is(equalTo(false)));
    }

    /**
     * Test to verify that total size of the level1 and level2 cache is returned.
     *
     * @throws CacheException if an error occurs when dealing with the cache
     */
    @Test
    public void should_return_the_total_of_the_level1_and_level2_cache_sizes() throws CacheException {
        assertThat(twoLevelCache.getSize(), is(equalTo(5)));
    }

    /**
     * Test to verify that meta is stored and retrieved correctly.
     */
    @Test
    public void should_successfully_store_meta_data() {
        MetaData metaData = mock(MetaData.class);
        twoLevelCache.storeMetaData(metaData);

        assertThat(twoLevelCache.getMetaData(), is(equalTo(metaData)));
    }

}