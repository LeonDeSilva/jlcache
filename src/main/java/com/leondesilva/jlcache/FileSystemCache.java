package com.leondesilva.jlcache;

import com.leondesilva.jlcache.exceptions.CacheException;
import com.leondesilva.jlcache.exceptions.SerializationException;
import com.leondesilva.jlcache.pojo.MetaData;
import com.leondesilva.jlcache.util.SerializationUtils;

import java.io.IOException;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;

/**
 * Class to represent the file system cache.
 *
 * @param <K> the type of the key
 * @param <V> the type of the value
 */
public class FileSystemCache<K extends Serializable, V extends Serializable> implements Cache<K, V> {
    private String folderPath;
    private static final String CACHE_FILE_NAME = "cache";
    private static final String META_INFO_FILE_NAME = "meta";

    /**
     * Constructor to instantiate FileSystemCache.
     *
     * @param folderPath the folder path to write the cache data
     * @throws CacheException if an error occurs when trying to instantiate
     */
    public FileSystemCache(String folderPath) throws CacheException {
        this.folderPath = folderPath;

        try {
            if (!Paths.get(folderPath).toFile().exists()) {
                Files.createDirectories(Paths.get(folderPath));
            }

            if (!Paths.get(folderPath, CACHE_FILE_NAME).toFile().exists()) {
                Files.createFile(Paths.get(folderPath, CACHE_FILE_NAME));
            }

            if (!Paths.get(folderPath, META_INFO_FILE_NAME).toFile().exists()) {
                Files.createFile(Paths.get(folderPath, META_INFO_FILE_NAME));
            }

            writeCacheFile(new HashMap<>());
        } catch (IOException e) {
            throw new CacheException("Error occurred when trying to initialize file system cache.", e);
        }
    }

    /**
     * Method to put the key and value to the cache.
     *
     * @param key   the key
     * @param value the value
     * @throws CacheException if an error occurs when trying to run a caching related task
     */
    public void put(K key, V value) throws CacheException {
        HashMap<K, V> map = readCacheFileAsMap();
        map.put(key, value);
        writeCacheFile(map);
    }

    /**
     * Method to get the value for a given key.
     *
     * @param key the key to retrieve
     * @return the value for the given key
     * @throws CacheException if an error occurs when trying to run a caching related task
     */
    public V get(K key) throws CacheException {
        HashMap<K, V> map = readCacheFileAsMap();
        return map.get(key);
    }

    /**
     * Method to delete the key.
     *
     * @param key the key to be deleted
     * @throws CacheException if an error occurs when trying to run a caching related task
     */
    public void delete(K key) throws CacheException {
        HashMap<K, V> map = readCacheFileAsMap();
        map.remove(key);
        writeCacheFile(map);
    }

    /**
     * Method to delete all the keys and values
     *
     * @throws CacheException if an error occurs when trying to run a caching related task
     */
    public void deleteAll() throws CacheException {
        HashMap<K, V> map = readCacheFileAsMap();
        map.clear();
        writeCacheFile(map);
    }

    /**
     * Method to check whether the key does contain in the cache.
     *
     * @param key the key
     * @return true if contains and false if not
     * @throws CacheException if an error occurs when trying to run a caching related task
     */
    public boolean containsKey(K key) throws CacheException {
        HashMap<K, V> map = readCacheFileAsMap();
        return map.containsKey(key);
    }

    /**
     * Method to get the cache size.
     *
     * @return the cache size
     * @throws CacheException if an error occurs when trying to run a caching related task
     */
    public int getSize() throws CacheException {
        HashMap<K, V> map = readCacheFileAsMap();
        return map.size();
    }

    /**
     * Method to store meta data.
     *
     * @param metaData the meta data
     * @throws CacheException if an error occurs when trying to run a caching related task
     */
    public void storeMetaData(MetaData metaData) throws CacheException {
        writeMetaFile(metaData);
    }

    /**
     * Method to get the meta data.
     *
     * @return the meta data
     * @throws CacheException if an error occurs when trying to run a caching related task
     */
    public MetaData getMetaData() throws CacheException {
        return readMetaFile();
    }

    /**
     * Method to read the cache file as a hash map.
     *
     * @return the map by reading the cache file
     * @throws CacheException if an error occurs when trying to run a caching related task
     */
    private HashMap<K, V> readCacheFileAsMap() throws CacheException {
        try {
            return SerializationUtils.readFileAndDeserialize(Paths.get(folderPath, CACHE_FILE_NAME).toFile());
        } catch (SerializationException e) {
            throw new CacheException("Error occurred when trying to read cache file : " + Paths.get(folderPath, CACHE_FILE_NAME).toString(), e);
        }
    }

    /**
     * Method to write the hash map to a cache file.
     *
     * @param map the map to be written
     * @throws CacheException if an error occurs when trying to run a caching related task
     */
    private void writeCacheFile(HashMap<K, V> map) throws CacheException {
        try {
            SerializationUtils.serializeAndWriteToFile(map, Paths.get(folderPath, CACHE_FILE_NAME).toFile());
        } catch (SerializationException e) {
            throw new CacheException("Error occurred when trying to write to cache file : " + Paths.get(folderPath, CACHE_FILE_NAME).toString(), e);
        }
    }

    /**
     * Method to read the meta data file and get the meta data.
     *
     * @return the meta data
     * @throws CacheException if an error occurs when trying to run a caching related task
     */
    private MetaData readMetaFile() throws CacheException {
        try {
            return SerializationUtils.readFileAndDeserialize(Paths.get(folderPath, META_INFO_FILE_NAME).toFile());
        } catch (SerializationException e) {
            throw new CacheException("Error occurred when trying to read cache meta data file : " + Paths.get(folderPath, META_INFO_FILE_NAME).toString(), e);
        }
    }

    /**
     * Method to write meta data in to a meta data file.
     *
     * @param metaData the meta data to be written
     * @throws CacheException if an error occurs when trying to run a caching related task
     */
    private void writeMetaFile(MetaData metaData) throws CacheException {
        try {
            SerializationUtils.serializeAndWriteToFile(metaData, Paths.get(folderPath, META_INFO_FILE_NAME).toFile());
        } catch (SerializationException e) {
            throw new CacheException("Error occurred when trying to write to cache meta data file : " + Paths.get(folderPath, META_INFO_FILE_NAME).toString(), e);
        }
    }
}
