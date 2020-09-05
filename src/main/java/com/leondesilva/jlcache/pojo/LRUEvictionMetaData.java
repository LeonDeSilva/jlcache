package com.leondesilva.jlcache.pojo;

import java.io.Serializable;
import java.util.LinkedList;

/**
 * Implementation of the meta data to represent LRU eviction meta data.
 *
 * @param <K> the type of the key
 */
public class LRUEvictionMetaData<K extends Serializable> implements MetaData {
    private LinkedList<K> nodeList = new LinkedList<>();

    /**
     * Method to get the node list.
     * Method marked as NOSONAR because it is mandatory to keep LinkedList type
     * in order to use the doubly linked list features which are not exposed in the common List interface.
     *
     * @return the node list
     */
    public LinkedList<K> getNodeList() { //NOSONAR
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
