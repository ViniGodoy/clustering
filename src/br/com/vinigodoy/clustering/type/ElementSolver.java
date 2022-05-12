package br.com.vinigodoy.clustering.type;

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
public interface ElementSolver<T> {
    /**
     * Calculate the distance between two different elements
     *
     * @param element1 First element
     * @param element2 Second element
     * @return The distance
     */
    double distance(T element1, T element2);

    /**
     * Adds two elements together
     *
     * @param element1 First element to add
     * @param element2 Second element to add
     * @return a new element, with the sum
     * @throws UnsupportedOperationException If this is a knn only solver.
     */
    default T add(T element1, T element2) {
        throw new UnsupportedOperationException();
    }

    /**
     * Divides the element by an scalar
     *
     * @param element The element to divide
     * @param scalar Scalar value
     * @return The division
     * @throws UnsupportedOperationException If this is a knn only solver.
     */
    default T divide(T element, int scalar) {
        throw new UnsupportedOperationException();
    }

    /**
     * Returns a value to be used in knn cache (if active). Elements with the same value will be considered equal.
     * If null is returned, this element will never be cached.
     */
    default Long cacheValue(T element) {
        return null;
    }
}
