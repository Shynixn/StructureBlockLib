package com.github.shynixn.structureblocklib.api.service;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Serialization service.
 */
public interface StructureSerializationService {
    /**
     * Deserializes the {@link InputStream} to an NMS handle of DefinedStructure.
     * This call is blocking.
     *
     * @param inputStream Opened inputStream. Does not close the stream after processing.
     * @return A new NMS instance of DefinedStructure.
     */
    Object deSerialize(InputStream inputStream) throws IOException;

    /**
     * Serializes the NMS handle of DefinedStructure to an {@link OutputStream}.
     * This call is blocking.
     *
     * @param definedStructure NMS handle.
     * @param outputStream     Opened outputStream. Does not close the stream after processing.
     */
    void serialize(Object definedStructure, OutputStream outputStream) throws IOException;
}
