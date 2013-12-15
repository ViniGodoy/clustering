package br.com.vinigodoy.kmeans;

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
        double erro = calculateError(centroidesAntigos);
        System.out.printf(" - Erro: %.5f%n", erro);
        if (Double.isNaN(erro))
            System.exit(1);
        return erro <= tolerance;
    }

    private void createCentroids(int classNumber, List<Vector> samples) {
        centroids.add(new Centroid(1, samples.get(0)));

        int num = 2;
        for (int i = 1; i < classNumber; i++) {
            Vector maior = null;
            double maiorDistancia = 0;
            for (Vector amostra : samples) {
                if (averageDistance(amostra) > maiorDistancia)
                    maior = amostra;
            }
            centroids.add(new Centroid(num, maior));
            num++;
        }
    }

    private double averageDistance(Vector amostra) {
        double soma = 0;
        for (Centroid c : centroids) {
            if (amostra.equals(c.getCenter())) // Evita centroids iguais
                return -1;
            soma += c.distance(amostra);
        }
        return soma / centroids.size();
    }

    protected abstract void addToCentroid(Vector sample);

    public int classify(Vector sample) {
        int closest = -1;
        double closestDistance = Integer.MAX_VALUE;

        for (Centroid c : centroids)
            if (c.distance(sample) < closestDistance) {
                closest = c.getNumber();
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
