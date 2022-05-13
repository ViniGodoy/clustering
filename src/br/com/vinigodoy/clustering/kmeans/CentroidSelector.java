package br.com.vinigodoy.clustering.kmeans;

import java.util.random.RandomGenerator;

/**
 * Chooses k-means initial centroids.
 * @param <T> The element data type
 */
public interface CentroidSelector<T> {
    /**
     * Random generator for centroid selection.
     */
    RandomGenerator RND = RandomGenerator.getDefault();
    /**
     * Select count centroids from the dataset.
     * @param data The dataset.
     * @param count Number of centroids to be select
     * @return The centroid list
     */
    Iterable<T> initialCentroids(Iterable<T> data, int count);

}

