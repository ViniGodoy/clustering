package br.com.vinigodoy.clustering.kmeans;

import br.com.vinigodoy.clustering.type.ElementSolver;

public class Cluster<T> {
        private final int label;
        private final T centroid;
        private final ElementSolver<T> solver;

        private T sum = null;
        private int count = 0;
        private double distances = 0;

        private T closest = null;
        private double closestDist = Double.MAX_VALUE;

        protected Cluster(int label, T centroid, ElementSolver<T> solver) {
            this.label = label;
            this.centroid = centroid;
            this.solver = solver;
        }

        protected void add(T element) {
            count += 1;
            if (sum == null) sum = element;
            else sum = solver.add(sum, element);

            final var distance = solver.distance(element, centroid);
            distances += distance;
            if (distance < closestDist) {
                closestDist = distance;
                closest = element;
            }
        }

        protected Cluster<T> createOnCenter() {
            return new Cluster<>(label, solver.divide(sum, count), solver);
        }

        protected double avgDistance() {
            return distances / count;
        }

    public int getLabel() {
        return label;
    }

    public T getClosest() {
            return closest;
        }

        public T getCentroid() {
            return centroid;
        }
    }
