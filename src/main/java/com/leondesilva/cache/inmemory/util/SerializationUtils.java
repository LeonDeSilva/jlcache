package com.leondesilva.cache.inmemory.util;

import com.leondesilva.cache.inmemory.exceptions.SerializationException;

import java.io.*;

/**
 * Class to represent the serialization utilities.
 */
public final class SerializationUtils {
    /**
     * Constructor to instantiate SerializationUtils.
     */
    private SerializationUtils() {
        // Private constructor
    }

    /**
     * Method to read file and deserialize and object.
     *
     * @param file the file to read
     * @param <T>  the type of the object
     * @return the deserialized object
     * @throws SerializationException if an error occurs when trying to read file or when trying to deserialize
     */
    public static <T> T readFileAndDeserialize(File file) throws SerializationException {
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(file.toString()))) {
            return (T) in.readObject();
        } catch (IOException | ClassNotFoundException e) {
            throw new SerializationException("Error occurred when trying to read file and deserialize", e);
        }
    }

    /**
     * Method to serialize an object and write to file.
     *
     * @param object the object to be serialized
     * @param file   the file to be written
     * @param <T>    the type of the object
     * @throws SerializationException if an error occurs when trying to serialize and write to file
     */
    public static <T> void serializeAndWriteToFile(T object, File file) throws SerializationException {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(file.toString()))) {
            out.writeObject(object);
        } catch (IOException e) {
            throw new SerializationException("Error occurred when trying to serialize and write to file", e);
        }
    }
}
