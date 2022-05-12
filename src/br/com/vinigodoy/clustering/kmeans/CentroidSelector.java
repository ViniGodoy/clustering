package br.com.vinigodoy.clustering.kmeans;

import java.util.List;

/**
 * Chooses k-means initial centroids.
 * @param <T> The element data type
 */
public interface CentroidSelector<T> {
    /**
     * Select count centroids from the dataset.
     * @param data The dataset.
     * @param count Number of centroids to be select
     * @return The centroid list
     */
    List<T> initialCentroids(Iterable<T> data, int count);

}

