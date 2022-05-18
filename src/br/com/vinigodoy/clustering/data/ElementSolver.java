package br.com.vinigodoy.clustering.data;

/**
 * Perform math operations in elements, allowing them to be used in algorithms such as k-means.
 *
 * The solver must:
 * - Be able to create a zeroed element.
 * - Be able to sum elements
 * - Divide elements to a scalar value
 * - Create a new zeroed element
 * - Calculate distance between elements (see DistanceMeasurer interface)
 *
 * @param <T> The element type
 * @see DistanceMeasurer
 */
public interface ElementSolver<T> extends DistanceMeasurer<T> {
    /**
     * Creates a new zeroed element.
     *
     * @return A new zeroed element.
     */
    T create();
    /**
     * Accumulate the value of the element, into an accumulator element. Equivalent to:
     * accumulator += element;
     *
     * @param acc Accumulator element
     * @param element Element to add
     * @throws UnsupportedOperationException If this is a knn only solver.
     */
    default void accumulate(T acc, T element) {
        throw new UnsupportedOperationException();
    }

    /**
     * Divides the element by a scalar
     *
     * @param element The element to divide
     * @param scalar Scalar value
     * @return The division
     * @throws UnsupportedOperationException If this is a knn only solver.
     */
    default T divide(T element, int scalar) {
        throw new UnsupportedOperationException();
    }
}
