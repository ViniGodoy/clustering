package br.com.vinigodoy.clustering.data;

import java.util.function.Consumer;

/**
 * Represents a data origin that can be opened and closed in any time. Datasources can be opened by calling it's
 * iterator() method. In this case a DataIterator is provided, allowing the iterator to also close the data connection.
 *
 * Implementations of this interface may use a physical datasource directly (such as a file), avoiding high memory
 * consumption, but sacrificing processing time. This allows very large datasets to be used.
 *
 * @param <T> Data type.
 * @see DataSource
 */
public interface DataSource<T> {
    /**
     * Provides a datasource from an in-memory dataset (such as, data in a collection), and there's no special
     * operation while opening or closing.
     *
     * @param memoryIterable Any iterable that works on memory.
     * @return The datasource, using this iterable
     * @param <T> Element data type.
     */
    static <T> DataSource<T> of(Iterable<T> memoryIterable) {
        return () -> {
            return DataIterator.of(memoryIterable.iterator());
        };
    }

    /**
     * Opens this DataSource and returns an iterator for its data.
     * @return The data iterator.
     * @throws Exception If any problem occurred while opening.
     */
    DataIterator<T> iterator() throws Exception;

    /**
     * Iterates over the datasource. The datasource is automatically opened and closed. Exceptions that occur in the
     * process are encapsulated in a RuntimeException. Calling close is guaranteed.
     *
     * @param consumer The consumer
     */
    default void forEach(Consumer<? super T> consumer) {
        try (var it = iterator()) {
            it.forEachRemaining(consumer);
        } catch (Exception e) {
            throw new RuntimeException("Problems while using the datasource: " + e.getMessage(), e);
        }
    }
}
