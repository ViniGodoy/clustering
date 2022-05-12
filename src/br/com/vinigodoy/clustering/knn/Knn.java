package br.com.vinigodoy.clustering.knn;

import br.com.vinigodoy.clustering.type.DataOutput;
import br.com.vinigodoy.clustering.type.ElementSolver;

import java.util.*;

public class Knn<T> {
    private record Sample<T>(int label, T element) {}
    private class DistanceComparator implements Comparator<Sample<T>> {
        private final T element;

        public DistanceComparator(T element) {
            this.element = element;
        }

        @Override
        public int compare(Sample<T> e1, Sample<T> e2) {
            final var dist1 = solver.distance(element, e1.element());
            final var dist2 = solver.distance(element, e2.element());
            return Double.compare(dist1, dist2);
        }
    }

    private final ElementSolver<T> solver;
    private final Map<Long, Integer> cache = new HashMap<>();
    private final List<Sample<T>> samples = new ArrayList<>();

    private int k = 3;
    private boolean cached = false;

    public Knn(ElementSolver<T> solver) {
        if (solver == null) throw new IllegalArgumentException("You must provide a solver!");
        this.solver = solver;
    }

    public Knn<T> setK(int k) {
        this.k = k;
        return this;
    }

    public int getK() {
        return k;
    }

    public Knn<T> setCached(boolean cached) {
        this.cached = cached;
        return this;
    }

    public boolean isCached() {
        return cached;
    }

    public Knn<T> addSample(int label, T element) {
        cache.clear();
        samples.add(new Sample<>(label ,element));
        return this;
    }

    @SafeVarargs
    public final Knn<T> addSamples(int label, T... elements) {
        cache.clear();
        for (var element : elements) {
            samples.add(new Sample<>(label ,element));
        }
        return this;
    }

    private int findLabel(T element) {
        samples.sort(new DistanceComparator(element));

        //Count up to k labels, based on the distance
        final var count = new HashMap<Integer, Integer>();
        for (var i = 0; i < k; i++) {
            final var l = samples.get(i).label();
            count.put(l, count.containsKey(l) ? count.get(l)+1 : 1);
        }

        //Find the label with the greatest count
        var label = -1;
        var max = 0;
        for (var entry : count.entrySet()) {
            if (entry.getValue() > max) {
                label = entry.getKey();
                max = entry.getValue();
            }
        }
        return label;
    }

    public int classify(T element) {
        if (!isCached()) return findLabel(element);

        final var cacheValue = solver.cacheValue(element);
        if (cacheValue == null) return findLabel(element);

        if (!cache.containsKey(cacheValue)) cache.put(cacheValue, findLabel(element));
        return cache.get(cacheValue);
    }

    public Knn<T> classify(Iterable<T> data, DataOutput<T> output) {
        data.forEach(d -> output.write(classify(d)));
        return this;
    }
}
