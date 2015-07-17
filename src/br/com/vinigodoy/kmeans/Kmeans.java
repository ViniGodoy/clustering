package br.com.vinigodoy.kmeans;

import br.com.vinigodoy.math.Vector;

import java.util.List;

public class Kmeans extends CentroidClassifier {
    public Kmeans(int nrClasses, List<Vector> amostras) {
        super(nrClasses, amostras);
        process(amostras);
    }

    @Override
    protected void addToCentroid(Vector sample) {
        double closestDistance = centroids.get(0).distance(sample);
        Centroid closest = centroids.get(0);

        for (int i = 1; i < centroids.size(); i++) {
            double distance = centroids.get(i).distance(sample);
            if (distance < closestDistance) {
                closestDistance = distance;
                closest = centroids.get(i);
            }
        }
        closest.add(sample);
    }
}
