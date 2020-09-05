package com.leondesilva.cache.inmemory.pojo;

import java.util.LinkedList;

/**
 * Implementation of the meta data to represent LRU eviction meta data.
 *
 * @param <K> the type of the key
 */
public class LRUEvictionMetaData<K> implements MetaData {
    private LinkedList<K> nodeList = new LinkedList<>();

    /**
     * Method to get the node list.
     *
     * @return the node list
     */
    public LinkedList<K> getNodeList() {
        return nodeList;
    }

    /**
     * Overridden equals method.
     *
     * @param o object to compare
     * @return true if equals and false if not
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        LRUEvictionMetaData<?> that = (LRUEvictionMetaData<?>) o;

        return nodeList != null ? nodeList.equals(that.nodeList) : that.nodeList == null;
    }

    /**
     * Overridden hash code method.
     *
     * @return hash code
     */
    @Override
    public int hashCode() {
        return nodeList != null ? nodeList.hashCode() : 0;
    }
}
