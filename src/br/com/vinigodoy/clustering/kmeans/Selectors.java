package br.com.vinigodoy.clustering.kmeans;

import br.com.vinigodoy.clustering.data.ElementSolver;
import br.com.vinigodoy.clustering.data.image.ImageDatasource;

import java.util.ArrayList;
import java.util.List;
import java.util.random.RandomGenerator;

/**
 * Contains default centroid selectors. Most selectors created by this class need to load the data into memory, at least
 * partially. For specific data types, consider providing a more efficient implementation.
 *
 * @see ImageDatasource#random() An efficient random algorithm for image data
 */
public class Selectors {
    public static final RandomGenerator RND = RandomGenerator.getDefault();

    private static <T> List<T> toList(Iterable<T> data, int limit) {
        final var dataList = new ArrayList<T>();
        final var it = data.iterator();

        var count = 0;
        while (it.hasNext() && count++ < limit) dataList.add(it.next());

        return dataList;
    }

    private static <T> T random(List<T> data) {
        return data.get(RND.nextInt(data.size()));
    }

    /**
     * Creates a selector that chooses random points in the dataset.
     *
     * Notice that the entire dataset will be loaded in the memory.
     *
     * @return Selected centroids.
     * @param <T> Element type
     * @see #random(int)
     */
    public static <T> CentroidSelector<T> random() {
        return random(Integer.MAX_VALUE);
    }

    /**
     * Creates a selector that chooses random points in the dataset. This method will only load the first limit
     * elements from the dataset.
     *
     * @return Selected centroids.
     * @param <T> Element type
     * @param limit Number of elements to load
     */
    public static <T> CentroidSelector<T> random(int limit) {
        return (data, classes) -> {
            final var ds = toList(data, limit);

            final var centroids = new ArrayList<T>();
            for (var i = 0; i < classes; i++) {
                centroids.add(random(ds));
            }
            return centroids;
        };
    }

    /**
     * This selector chooses a random element from the first limit elements in data set to be the first centroid.
     *
     * Then, the remaining elements are ordered by their distance to this centroid, from farthest to closest.
     * Next centroids are chosen based in the formula: size * (rnd)^coef, where rnd is a number from 0 to 1 (exclusive).
     *
     * This algorithm is inspired by kmeans++.
     *
     * @param solver The solver to calculate distances.
     * @param limit Number of elements to load
     * @param coef Power coefficient. Higher values decrease the chance to select closest elements.
     * @return The centroids.
     * @param <T> Element data type.
     */
    public static <T> CentroidSelector<T> distance(ElementSolver<T> solver, int limit, double coef) {
        return (data, classes) -> {
            final var ds = toList(data, limit);
            final var first = random(ds);
            ds.sort(solver.createDistanceComparator(first).reversed());

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
     * This selector chooses a random element from the first limit elements in data set to be the first centroid.
     *
     * Then, the remaining elements are ordered by their distance to this centroid, from farthest to closest.
     * Next centroids are chosen based in the formula: size * (rnd)^1.2, where rnd is a number from 0 to 1 (exclusive).
     *
     * This algorithm is inspired by kmeans++.
     *
     * @param solver The solver to calculate distances.
     * @param limit Number of elements to load
     * @return The centroids.
     * @param <T> Element data type.
     * @see #distance(ElementSolver, int, double) 
     */
    public static <T> CentroidSelector<T> distance(ElementSolver<T> solver, int limit) {
        return distance(solver, limit, 1.2);
    }

    /**
     * This selector chooses a random element from the data set to be the first centroid.
     *
     * Then, the remaining elements are ordered by their distance to this centroid, from farthest to closest.
     * Next centroids are chosen based in the formula: size * (rnd)^1.2, where rnd is a number from 0 to 1 (exclusive).
     *
     * This algorithm is inspired by kmeans++.
     *
     * Notice that this method will load the entire dataset in memory.
     *
     * @param solver The solver to calculate distances.
     * @return The centroids.
     * @param <T> Element data type.
     * @see #distance(ElementSolver, int, double)
     */
    public static <T> CentroidSelector<T> distance(ElementSolver<T> solver) {
        return distance(solver, Integer.MAX_VALUE);
    }
}
