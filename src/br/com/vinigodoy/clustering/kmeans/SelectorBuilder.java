package br.com.vinigodoy.clustering.kmeans;

import br.com.vinigodoy.clustering.data.DataSource;
import br.com.vinigodoy.clustering.data.ElementSolver;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;

import static br.com.vinigodoy.clustering.kmeans.CentroidSelector.RND;

public class SelectorBuilder<T> {
    private int limit = Integer.MAX_VALUE;
    private boolean unique = false;

    public SelectorBuilder() {
    }

    /**
     * Specify a maximum number of data to read. This avoids to load the entire dataset into memory, just for centroid
     * selection.
     * @param limit Limit value.
     * @return This builder
     */
    public SelectorBuilder<T> withLimit(int limit) {
        this.limit = limit;
        return this;
    }

    /**
     * Avoid using duplicate data points when selecting centroids. Duplications are discarded by hashCode.
     * @param unique If true, remove duplicate data.
     * @return This builder
     * @see Object#hashCode()
     */
    public SelectorBuilder<T> unique(boolean unique) {
        this.unique = unique;
        return this;
    }

    private static <T> T random(List<T> data) {
        return data.get(RND.nextInt(data.size()));
    }

    private Collection<T> copy(DataSource<T> data, Collection<T> collection) {
        try (final var it = data.iterator()) {
            while (it.hasNext() && collection.size() < limit) collection.add(it.next());
            return collection;
        } catch (Exception e) {
            throw new RuntimeException("Unable to open datasource", e);
        }
    }

    private List<T> toList(DataSource<T> data) {
        return unique ?
            (ArrayList<T>) copy(data, new ArrayList<>()) :
            new ArrayList<>(copy(data, new HashSet<>()));
    }

    /**
     * Creates a selector that chooses random points in the dataset.
     *
     * @return Selected centroids.*
     */
    public CentroidSelector<T> random() {
        return (data, classes) -> {
            final var ds = toList(data);

            final var centroids = new ArrayList<T>();
            for (var i = 0; i < classes; i++) {
                centroids.add(random(ds));
            }
            return centroids;
        };
    }

    /**
     * This selector chooses a random element from the data set to be the first centroid.
     *
     * Then, the remaining elements are ordered by their distance to this centroid, from farthest to closest.
     * Next centroids are chosen based in the formula: size * (rnd)^coef, where rnd is a number from 0 to 1 (exclusive).
     *
     * This algorithm is inspired by kmeans++.
     *
     * @param solver The solver to calculate distances.
     * @param coef Power coefficient. Use a value greater than one. Higher values decrease the chance to
     *             select closest elements.
     * @return The centroids.
     */
    public CentroidSelector<T> distance(ElementSolver<T> solver, double coef) {
        return (data, classes) -> {
            final var ds = toList(data);
            final var first = random(ds);
            ds.sort(solver.comparatorByDistance(first).reversed());

            final var centroids = new ArrayList<T>();
            centroids.add(first);

            for (var i = 1; i < classes; i++) {
                final var idx = (int) (ds.size() * Math.pow(RND.nextDouble(), coef));
                centroids.add(ds.get(idx));
            }
            return centroids;
        };
    }

    /**
     * This selector chooses a random element from the data set to be the first centroid.
     *
     * Then, the remaining elements are ordered by their distance to this centroid, from farthest to closest.
     * Next centroids are chosen based in the formula: size * (rnd)^2, where rnd is a number from 0 to 1 (exclusive).
     *
     * This algorithm is inspired by kmeans++.
     *
     * @param solver The solver to calculate distances.
     * @return The centroids.
     */
    public CentroidSelector<T> distance(ElementSolver<T> solver) {
        return distance(solver, 2);
    }
}
