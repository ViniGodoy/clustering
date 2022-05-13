package br.com.vinigodoy.clustering.data;

/**
 * Do operations over a knn / kmeans element.
 *
 * For knn, the solver must be able to:
 * - Determine a distance between two elements
 * - Convert the element to a long value (for knn cache)
 *
 * For k-means the solver must also:
 * - Be able to sum elements
 * - Divide elements to a scalar value
 *
 * @param <T> The element type
 */
public interface ElementSolver<T> extends DistanceMeasurer<T> {
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
