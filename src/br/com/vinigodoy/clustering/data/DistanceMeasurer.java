package br.com.vinigodoy.clustering.data;

import java.util.Comparator;

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
