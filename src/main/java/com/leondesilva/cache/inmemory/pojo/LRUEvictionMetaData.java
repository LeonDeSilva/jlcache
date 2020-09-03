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
     * Method to set the node list.
     *
     * @param nodeList the node list
     */
    public void setNodeList(LinkedList<K> nodeList) {
        this.nodeList = nodeList;
    }
}
