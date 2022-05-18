package br.com.vinigodoy.clustering.data;

import java.util.Iterator;

/**
 * A DataIterator is an iterator that can be explicitly closed. An iterator over a file is an example of a data
 * iterator.
 * @param <T> The element type.
 * @see DataSource
 */
public interface DataIterator<T> extends Iterator<T>, AutoCloseable {
    /**
     * Returns a data iterator from any memory-based iterator (such as list iterators), where closing is not needed.
     * @param memoryIterator The traditional in-memory iterator.
     * @return The iterator
     * @param <T> The data type.
     */
    static <T> DataIterator<T> of(Iterator<T> memoryIterator) {
        return new DataIterator<>() {
            @Override
            public void close() {
            }

            @Override
            public boolean hasNext() {
                return memoryIterator.hasNext();
            }

            @Override
            public T next() {
                return memoryIterator.next();
            }

            @Override
            public void remove() {
                memoryIterator.remove();
            }
        };
    }
}
