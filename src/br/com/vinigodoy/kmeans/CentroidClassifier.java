package br.com.vinigodoy.kmeans;

import br.com.vinigodoy.math.Vector;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public abstract class CentroidClassifier {
    private double tolerance = 0.001;
    protected List<Centroid> centroids;

    public CentroidClassifier(List<Centroid> centroids) {
        this.centroids = centroids;
    }

    public CentroidClassifier(int classNumber, List<Vector> samples) {
        if (classNumber < 1)
            throw new IllegalArgumentException(
                    "The class number must be bigger than 0!");
        centroids = new ArrayList<>();
        createCentroids(classNumber, samples);
    }

    public double getTolerance() {
        return tolerance;
    }

    public void setTolerance(double tolerance) {
        this.tolerance = tolerance;
    }

    protected final List<Centroid> process(List<Vector> samples) {
        List<Centroid> oldCentroids = null;
        System.out.println("Processing...");
        int count = 0;
        while (true) {
            System.out.printf("  Iteration %d", ++count);
            addToCentroids(samples);
            if (notChanged(oldCentroids))
                return centroids;

            oldCentroids = centroids;
            centroids = new ArrayList<>();
            for (Centroid c : oldCentroids)
                centroids.add(c.recalculateCenter());
        }
    }

    private double calculateError(List<Centroid> oldCentroids) {
        double previousDistance = 0;
        double currentDistance = 0;
        for (int i = 0; i < oldCentroids.size(); i++) {
            previousDistance += oldCentroids.get(i).getAverageDistance();
            currentDistance += centroids.get(i).getAverageDistance();
        }
        return Math.abs(currentDistance - previousDistance)
                / (previousDistance + currentDistance);
    }

    private void addToCentroids(List<Vector> amostras) {
        for (Vector amostra : amostras)
            addToCentroid(amostra);
    }

    private boolean notChanged(List<Centroid> centroidesAntigos) {
        if (centroidesAntigos == null) {
            System.out.println();
            return false;
        }
        double error = calculateError(centroidesAntigos);
        System.out.printf(" - Error: %.5f%n", error);
        if (Double.isNaN(error))
            System.exit(1);
        return error <= tolerance;
    }

    private void createCentroids(int classNumber, List<Vector> samples) {
        centroids.add(new Centroid(1, samples.get(0)));

        int num = 2;
        for (int i = 1; i < classNumber; i++) {
            Vector biggest = null;
            double biggestDistance = 0;
            for (Vector sample : samples) {
                double avgDistance = averageDistance(sample);
                if (avgDistance > biggestDistance) {
                    biggest = sample;
                    biggestDistance = avgDistance;
                }
            }
            centroids.add(new Centroid(num, biggest));
            num++;
        }
    }

    private double averageDistance(Vector sample) {
        double sum = 0;
        for (Centroid c : centroids) {
            if (sample.equals(c.getCenter())) // Avoids equal centroids
                return -1;
            sum += c.distance(sample);
        }
        return sum / centroids.size();
    }

    protected abstract void addToCentroid(Vector sample);

    public Centroid classify(Vector sample) {
        Centroid closest = null;
        double closestDistance = Integer.MAX_VALUE;

        for (Centroid c : centroids)
            if (c.distance(sample) < closestDistance) {
                closest = c;
                closestDistance = c.distance(sample);
            }

        return closest;
    }

    public List<Centroid> getCentroids() {
        return Collections.unmodifiableList(centroids);
    }

    public void save(File file) throws FileNotFoundException {
        try (PrintWriter out = new PrintWriter(file)) {
            for (Centroid c : centroids)
                out.printf("%d: %s%n", c.getNumber(), c.getCenter().toString());
            out.flush();
        }
    }
}
