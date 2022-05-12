package br.com.vinigodoy.clustering.kmeans;

import br.com.vinigodoy.clustering.type.DataOutput;
import br.com.vinigodoy.clustering.type.ElementSolver;

import java.util.ArrayList;
import java.util.List;
import java.util.random.RandomGenerator;
import java.util.stream.Collectors;

public class Kmeans<T> {
    private static final RandomGenerator RND = RandomGenerator.getDefault();

    private final ElementSolver<T> solver;
    private List<Cluster<T>> clusters;
    private double tolerance = 0.01;

    public Kmeans(ElementSolver<T> solver) {
        this.solver = solver;
    }

    public Kmeans<T> setTolerance(double tolerance) {
        this.tolerance = tolerance;
        return this;
    }

    public double getTolerance() {
        return tolerance;
    }

    public List<Cluster<T>> getClusters() {
        return List.copyOf(clusters);
    }

    private void createRandomClusters(Iterable<T> data, int classes) {
        final var ds = new ArrayList<T>();
        data.forEach(ds::add);

        clusters = new ArrayList<>();
        for (var i = 0; i < classes; i++) {
            clusters.add(new Cluster<>(i+1, ds.get(RND.nextInt(ds.size())), solver));
        }
    }

    private Cluster<T> findCluster(T element, List<Cluster<T>> clusters) {
        Cluster<T> closest = null;
        var closestDist = Double.MAX_VALUE;
        for (var cluster : clusters) {
            final var dist = solver.distance(element, cluster.getCentroid());
            if (dist < closestDist) {
                closestDist = dist;
                closest = cluster;
            }
        }

        return closest;
    }

    private Cluster<T> findCluster(T element) {
        return findCluster(element, clusters);
    }

    private void doClustering(Iterable<T> ds, List<Cluster<T>> clusters) {
        ds.forEach(elem -> findCluster(elem, clusters).add(elem));
    }

    private double calculateError(List<Cluster<T>> newClusters) {
        var newDistance = 0.0;
        var prevDistance = 0.0;

        for (var i = 0; i < clusters.size(); i++) {
            prevDistance += clusters.get(i).avgDistance();
            newDistance += newClusters.get(i).avgDistance();
        }
        return Math.abs(newDistance - prevDistance) /
                (newDistance + prevDistance);
    }


    public Kmeans<T> train(Iterable<T> ds, int classes) {
        createRandomClusters(ds, classes);
        doClustering(ds, clusters);

        while (true) {
            final var newClusters = clusters.stream()
                    .map(Cluster::createOnCenter)
                    .collect(Collectors.toList());
            doClustering(ds, newClusters);
            if (calculateError(newClusters) <= tolerance) break;

            clusters = newClusters;
        }
        return this;
    }

    public Kmeans<T> classify(Iterable<T> data, DataOutput<T> out) {
        data.forEach(d -> out.write(findCluster(d).getLabel()));
        return this;
    }
}
