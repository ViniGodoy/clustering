package br.com.vinigodoy.clustering.data;

import java.util.Comparator;

/**
 * Measure the distance between two different elements.
 *
 * Unless you want to use a cache, this is the only operation needed for knn algorithm. For caching, just implement
 * equals and hashcode methods in the element as well.
 *
 * @param <T> The element type.
 */
public interface DistanceMeasurer<T> {
    /**
     * Calculate the distance between two different elements
     *
     * @param element1 First element
     * @param element2 Second element
     * @return The distance
     */
    double distance(T element1, T element2);

    /**
     * Creates a new comparator based on the proximity to the given element.
     * @param element Element to compare (center)
     * @return The comparator
     */
    default Comparator<T> comparatorByDistance(T element) {
        return (e1, e2) -> Double.compare(distance(element, e1), distance(element, e2));
    }
}
