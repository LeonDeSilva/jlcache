package com.leondesilva.jlcache;

import com.leondesilva.jlcache.pojo.MetaData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.mock;

/**
 * Class to test the in memory cache.
 */
class InMemoryCacheTest {
    private InMemoryCache<String, String> cache;
    private static final String KEY1 = "Key1";
    private static final String KEY2 = "Key2";
    private static final String KEY3 = "Key3";
    private static final String VALUE1 = "Value1";
    private static final String VALUE2 = "Value2";
    private static final String VALUE3 = "Value3";

    /**
     * Setup method.
     *
     */
    @BeforeEach
    void setup() {
        cache = new InMemoryCache<>();
    }

    /**
     * Test verify whether the data insertion and retrievals are done correctly.
     */
    @Test
    void should_insert_and_get_data_correctly() {
        cache.put(KEY1, VALUE1);
        cache.put(KEY2, VALUE2);
        cache.put(KEY3, VALUE3);

        assertThat(cache.get(KEY1), is(equalTo(VALUE1)));
        assertThat(cache.get(KEY2), is(equalTo(VALUE2)));
        assertThat(cache.get(KEY3), is(equalTo(VALUE3)));
    }

    /**
     * Test verify whether the cache entry size is returned correctly.
     */
    @Test
    void should_return_the_cache_size_correctly() {
        cache.put(KEY1, VALUE1);
        cache.put(KEY2, VALUE2);
        assertThat(cache.getSize(), is(equalTo(2)));
    }

    /**
     * Test verify whether true is returned when the cache contains the key.
     */
    @Test
    void should_return_the_true_if_the_cache_contains_key() {
        cache.put(KEY1, VALUE1);
        assertThat(cache.containsKey(KEY1), is(equalTo(true)));
    }

    /**
     * Test verify whether false is returned when the cache does not contain the key.
     */
    @Test
    void should_return_the_false_if_the_cache_does_not_contain_the_key() {
        cache.put(KEY1, VALUE1);
        assertThat(cache.containsKey(KEY2), is(equalTo(false)));
    }

    /**
     * Test verify whether entries are deleted correctly from cache.
     */
    @Test
    void should_delete_an_entry_correctly() {
        cache.put(KEY1, VALUE1);
        cache.put(KEY2, VALUE2);
        assertThat(cache.containsKey(KEY2), is(equalTo(true)));

        cache.delete(KEY2);
        assertThat(cache.containsKey(KEY2), is(equalTo(false)));
    }

    /**
     * Test verify whether all entries are deleted correctly from cache.
     */
    @Test
    void should_delete_all_entries_correctly() {
        cache.put(KEY1, VALUE1);
        cache.put(KEY2, VALUE2);
        cache.put(KEY3, VALUE3);
        assertThat(cache.getSize(), is(equalTo(3)));

        cache.deleteAll();
        assertThat(cache.getSize(), is(equalTo(0)));
    }

    /**
     * Test verify whether meta data store and retrieval is done correctly.
     */
    @Test
    void should_store_and_get_meta_data_correctly() {
        MetaData metaData = mock(MetaData.class);
        cache.storeMetaData(metaData);
        assertThat(cache.getMetaData(), is(equalTo(metaData)));
    }
}