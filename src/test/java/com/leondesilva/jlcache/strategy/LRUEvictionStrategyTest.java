package com.leondesilva.jlcache.strategy;

import com.leondesilva.jlcache.InMemoryCache;
import com.leondesilva.jlcache.exceptions.CacheException;
import com.leondesilva.jlcache.pojo.LRUEvictionMetaData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.LinkedList;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.core.Is.is;

/**
 * Class to test LRU eviction strategy.
 */
public class LRUEvictionStrategyTest {
    private LRUEvictionStrategy<String ,String> strategy;
    private InMemoryCache<String, String> cache;
    private static final String KEY1 = "Key1";
    private static final String KEY2 = "Key2";
    private static final String KEY3 = "Key3";
    private static final String KEY4 = "Key4";
    private static final String KEY5 = "Key5";
    private static final String KEY6 = "Key6";
    private static final String KEY7 = "Key7";

    private static final String VALUE1 = "Value1";
    private static final String VALUE2 = "Value2";
    private static final String VALUE3 = "Value3";
    private static final String VALUE4 = "Value4";
    private static final String VALUE5 = "Value5";
    private static final String VALUE6 = "Value6";
    private static final String VALUE7 = "Value7";

    /**
     * Setup method.
     *
     */
    @BeforeEach
    public void setup() throws CacheException {
        cache = new InMemoryCache<>();
        strategy = new LRUEvictionStrategy<>(cache, 5);
    }

    /**
     * Test to verify that the data insertion is done up to the max size.
     *
     * @throws CacheException if an error occurs when trying to cache
     */
    @Test
    public void should_insert_the_data_when_insertion_count_is_equal_to_max_size() throws CacheException {
        strategy.put(KEY1, VALUE1);
        strategy.put(KEY2, VALUE2);
        strategy.put(KEY3, VALUE3);
        strategy.put(KEY4, VALUE4);
        strategy.put(KEY5, VALUE5);

        // In memory cache data verifications
        assertThat(cache.getSize(), is(equalTo(5)));
        assertThat(cache.get(KEY1), is(equalTo(VALUE1)));
        assertThat(cache.get(KEY2), is(equalTo(VALUE2)));
        assertThat(cache.get(KEY3), is(equalTo(VALUE3)));
        assertThat(cache.get(KEY4), is(equalTo(VALUE4)));
        assertThat(cache.get(KEY5), is(equalTo(VALUE5)));

        LRUEvictionMetaData<String> metaData = (LRUEvictionMetaData<String>)cache.getMetaData();
        LinkedList<String> nodeList = metaData.getNodeList();

        // In memory cache meta data (LRU node list) verifications
        assertThat(nodeList.size(), is(equalTo(5)));
        assertThat(nodeList, contains(KEY5, KEY4, KEY3, KEY2, KEY1));

        // Strategy output verifications
        assertThat(strategy.getSize(), is(equalTo(5)));
        assertThat(strategy.containsKey(KEY1), is(equalTo(true)));
        assertThat(strategy.containsKey(KEY2), is(equalTo(true)));
        assertThat(strategy.containsKey(KEY3), is(equalTo(true)));
        assertThat(strategy.containsKey(KEY4), is(equalTo(true)));
        assertThat(strategy.containsKey(KEY5), is(equalTo(true)));

        assertThat(strategy.get(KEY1), is(equalTo(VALUE1)));
        assertThat(strategy.get(KEY2), is(equalTo(VALUE2)));
        assertThat(strategy.get(KEY3), is(equalTo(VALUE3)));
        assertThat(strategy.get(KEY4), is(equalTo(VALUE4)));
        assertThat(strategy.get(KEY5), is(equalTo(VALUE5)));
    }

    /**
     * Test to verify that the data insertion is done up to the max size and if extra insertions happen the cache and
     * node list is maintained according to LRU eviction.
     *
     * @throws CacheException if an error occurs when trying to cache
     */
    @Test
    public void should_insert_the_data_and_maintain_max_entry_count_even_extra_insertions_happen() throws CacheException {
        strategy.put(KEY1, VALUE1);
        strategy.put(KEY2, VALUE2);
        strategy.put(KEY3, VALUE3);
        strategy.put(KEY4, VALUE4);
        strategy.put(KEY5, VALUE5);
        strategy.put(KEY6, VALUE6);
        strategy.put(KEY7, VALUE7);

        // In memory cache data verifications
        assertThat(cache.getSize(), is(equalTo(5)));
        assertThat(cache.get(KEY1), is(equalTo(null)));
        assertThat(cache.get(KEY2), is(equalTo(null)));
        assertThat(cache.get(KEY3), is(equalTo(VALUE3)));
        assertThat(cache.get(KEY4), is(equalTo(VALUE4)));
        assertThat(cache.get(KEY5), is(equalTo(VALUE5)));
        assertThat(cache.get(KEY6), is(equalTo(VALUE6)));
        assertThat(cache.get(KEY7), is(equalTo(VALUE7)));

        LRUEvictionMetaData<String> metaData = (LRUEvictionMetaData<String>)cache.getMetaData();
        LinkedList<String> nodeList = metaData.getNodeList();

        // In memory cache meta data (LRU node list) verifications
        assertThat(nodeList.size(), is(equalTo(5)));
        assertThat(nodeList, contains(KEY7, KEY6, KEY5, KEY4, KEY3));

        // Strategy output verifications
        assertThat(strategy.getSize(), is(equalTo(5)));
        assertThat(strategy.containsKey(KEY1), is(equalTo(false)));
        assertThat(strategy.containsKey(KEY2), is(equalTo(false)));
        assertThat(strategy.containsKey(KEY3), is(equalTo(true)));
        assertThat(strategy.containsKey(KEY4), is(equalTo(true)));
        assertThat(strategy.containsKey(KEY5), is(equalTo(true)));
        assertThat(strategy.containsKey(KEY6), is(equalTo(true)));
        assertThat(strategy.containsKey(KEY7), is(equalTo(true)));

        assertThat(strategy.get(KEY3), is(equalTo(VALUE3)));
        assertThat(strategy.get(KEY4), is(equalTo(VALUE4)));
        assertThat(strategy.get(KEY5), is(equalTo(VALUE5)));
        assertThat(strategy.get(KEY6), is(equalTo(VALUE6)));
        assertThat(strategy.get(KEY7), is(equalTo(VALUE7)));
    }

    /**
     * Test to verify that the updated key is moved to the front of the LRU node list.
     *
     * @throws CacheException if an error occurs when trying to cache
     */
    @Test
    public void should_bring_the_updated_key_to_the_front_of_the_LRU_node_list() throws CacheException {
        strategy.put(KEY1, VALUE1);
        strategy.put(KEY2, VALUE2);
        strategy.put(KEY3, VALUE3);
        strategy.put(KEY4, VALUE4);
        strategy.put(KEY5, VALUE5);
        // Updating Key 2
        strategy.put(KEY2, VALUE7);

        // In memory cache data verifications
        assertThat(cache.getSize(), is(equalTo(5)));
        assertThat(cache.get(KEY1), is(equalTo(VALUE1)));
        assertThat(cache.get(KEY2), is(equalTo(VALUE7)));
        assertThat(cache.get(KEY3), is(equalTo(VALUE3)));
        assertThat(cache.get(KEY4), is(equalTo(VALUE4)));
        assertThat(cache.get(KEY5), is(equalTo(VALUE5)));

        LRUEvictionMetaData<String> metaData = (LRUEvictionMetaData<String>)cache.getMetaData();
        LinkedList<String> nodeList = metaData.getNodeList();

        // In memory cache meta data (LRU node list) verifications
        assertThat(nodeList.size(), is(equalTo(5)));

        // IMPORTANT VERIFICATION : KEY 2 should be in the front of the LRU node list as it has been updated.
        assertThat(nodeList, contains(KEY2, KEY5, KEY4, KEY3, KEY1));

        // Strategy output verifications
        assertThat(strategy.getSize(), is(equalTo(5)));
        assertThat(strategy.containsKey(KEY1), is(equalTo(true)));
        assertThat(strategy.containsKey(KEY2), is(equalTo(true)));
        assertThat(strategy.containsKey(KEY3), is(equalTo(true)));
        assertThat(strategy.containsKey(KEY4), is(equalTo(true)));
        assertThat(strategy.containsKey(KEY5), is(equalTo(true)));

        assertThat(strategy.get(KEY1), is(equalTo(VALUE1)));
        assertThat(strategy.get(KEY2), is(equalTo(VALUE7)));
        assertThat(strategy.get(KEY3), is(equalTo(VALUE3)));
        assertThat(strategy.get(KEY4), is(equalTo(VALUE4)));
        assertThat(strategy.get(KEY5), is(equalTo(VALUE5)));
    }

    /**
     * Test to verify that the key that is retrieved is moved to the front of the LRU node list.
     *
     * @throws CacheException if an error occurs when trying to cache
     */
    @Test
    public void should_bring_the_key_that_was_retrieved_to_the_front_of_the_LRU_node_list() throws CacheException {
        strategy.put(KEY1, VALUE1);
        strategy.put(KEY2, VALUE2);
        strategy.put(KEY3, VALUE3);
        strategy.put(KEY4, VALUE4);
        strategy.put(KEY5, VALUE5);
        // Retrieving Key 2
        strategy.get(KEY2);

        // In memory cache data verifications
        assertThat(cache.getSize(), is(equalTo(5)));
        assertThat(cache.get(KEY1), is(equalTo(VALUE1)));
        assertThat(cache.get(KEY2), is(equalTo(VALUE2)));
        assertThat(cache.get(KEY3), is(equalTo(VALUE3)));
        assertThat(cache.get(KEY4), is(equalTo(VALUE4)));
        assertThat(cache.get(KEY5), is(equalTo(VALUE5)));

        LRUEvictionMetaData<String> metaData = (LRUEvictionMetaData<String>)cache.getMetaData();
        LinkedList<String> nodeList = metaData.getNodeList();

        // In memory cache meta data (LRU node list) verifications
        assertThat(nodeList.size(), is(equalTo(5)));

        // IMPORTANT VERIFICATION : KEY 2 should be in the front of the LRU node list as it has been updated.
        assertThat(nodeList, contains(KEY2, KEY5, KEY4, KEY3, KEY1));

        // Strategy output verifications
        assertThat(strategy.getSize(), is(equalTo(5)));
        assertThat(strategy.containsKey(KEY1), is(equalTo(true)));
        assertThat(strategy.containsKey(KEY2), is(equalTo(true)));
        assertThat(strategy.containsKey(KEY3), is(equalTo(true)));
        assertThat(strategy.containsKey(KEY4), is(equalTo(true)));
        assertThat(strategy.containsKey(KEY5), is(equalTo(true)));

        assertThat(strategy.get(KEY1), is(equalTo(VALUE1)));
        assertThat(strategy.get(KEY2), is(equalTo(VALUE2)));
        assertThat(strategy.get(KEY3), is(equalTo(VALUE3)));
        assertThat(strategy.get(KEY4), is(equalTo(VALUE4)));
        assertThat(strategy.get(KEY5), is(equalTo(VALUE5)));
    }

    /**
     * Test to verify that the key is deleted successfully from cache and LRU node list.
     *
     * @throws CacheException if an error occurs when trying to cache
     */
    @Test
    public void should_successfully_delete_key_from_cache_and_LRU_node_list() throws CacheException {
        strategy.put(KEY1, VALUE1);
        strategy.put(KEY2, VALUE2);
        strategy.put(KEY3, VALUE3);
        strategy.put(KEY4, VALUE4);
        strategy.put(KEY5, VALUE5);

        // Delete key 3
        strategy.delete(KEY3);

        // In memory cache data verifications
        assertThat(cache.getSize(), is(equalTo(4)));
        assertThat(cache.get(KEY1), is(equalTo(VALUE1)));
        assertThat(cache.get(KEY2), is(equalTo(VALUE2)));
        assertThat(cache.get(KEY4), is(equalTo(VALUE4)));
        assertThat(cache.get(KEY5), is(equalTo(VALUE5)));

        LRUEvictionMetaData<String> metaData = (LRUEvictionMetaData<String>)cache.getMetaData();
        LinkedList<String> nodeList = metaData.getNodeList();

        // In memory cache meta data (LRU node list) verifications
        assertThat(nodeList.size(), is(equalTo(4)));

        // IMPORTANT VERIFICATION : KEY 3 should not be in the list
        assertThat(nodeList, contains(KEY5, KEY4, KEY2, KEY1));

        // Strategy output verifications
        assertThat(strategy.getSize(), is(equalTo(4)));
        assertThat(strategy.containsKey(KEY1), is(equalTo(true)));
        assertThat(strategy.containsKey(KEY2), is(equalTo(true)));
        assertThat(strategy.containsKey(KEY3), is(equalTo(false)));
        assertThat(strategy.containsKey(KEY4), is(equalTo(true)));
        assertThat(strategy.containsKey(KEY5), is(equalTo(true)));

        assertThat(strategy.get(KEY1), is(equalTo(VALUE1)));
        assertThat(strategy.get(KEY2), is(equalTo(VALUE2)));
        assertThat(strategy.get(KEY3), is(equalTo(null)));
        assertThat(strategy.get(KEY4), is(equalTo(VALUE4)));
        assertThat(strategy.get(KEY5), is(equalTo(VALUE5)));
    }

    /**
     * Test to verify that data is cleared correctly from the cache and the LRU node list.
     *
     * @throws CacheException if an error occurs when trying to cache
     */
    @Test
    public void should_successfully_clear_the_cache_and_LRU_node_list() throws CacheException {
        strategy.put(KEY1, VALUE1);
        strategy.put(KEY2, VALUE2);
        strategy.put(KEY3, VALUE3);
        strategy.put(KEY4, VALUE4);
        strategy.put(KEY5, VALUE5);

        // Delete all keys
        strategy.deleteAll();

        // In memory cache data verifications
        assertThat(cache.getSize(), is(equalTo(0)));

        LRUEvictionMetaData<String> metaData = (LRUEvictionMetaData<String>)cache.getMetaData();
        LinkedList<String> nodeList = metaData.getNodeList();

        // In memory cache meta data (LRU node list) verifications
        assertThat(nodeList.size(), is(equalTo(0)));

        // Strategy output verifications
        assertThat(strategy.getSize(), is(equalTo(0)));
        assertThat(strategy.containsKey(KEY1), is(equalTo(false)));
        assertThat(strategy.containsKey(KEY2), is(equalTo(false)));
        assertThat(strategy.containsKey(KEY3), is(equalTo(false)));
        assertThat(strategy.containsKey(KEY4), is(equalTo(false)));
        assertThat(strategy.containsKey(KEY5), is(equalTo(false)));

        assertThat(strategy.get(KEY1), is(equalTo(null)));
        assertThat(strategy.get(KEY2), is(equalTo(null)));
        assertThat(strategy.get(KEY3), is(equalTo(null)));
        assertThat(strategy.get(KEY4), is(equalTo(null)));
        assertThat(strategy.get(KEY5), is(equalTo(null)));
    }
}