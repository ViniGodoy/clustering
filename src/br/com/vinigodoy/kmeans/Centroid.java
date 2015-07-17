package br.com.vinigodoy.kmeans;

import br.com.vinigodoy.math.Vector;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Centroid implements Iterable<Vector> {
    private int number;
    private Vector center;
    private List<Vector> samples = new ArrayList<>();

    public Centroid(int number, Vector center) {
        this.number = number;
        this.center = center;
    }

    public Vector getCenter() {
        return center;
    }

    public int getNumber() {
        return number;
    }

    public Centroid recalculateCenter() {
        Vector sum = new Vector(samples.get(0).v);
        for (int i = 1; i < samples.size(); i++) {
            sum.add(samples.get(i));
        }

        return new Centroid(number, sum.divide(samples.size()));
    }

    public double distance(Vector sample) {
        return sample.distance(center);
    }

    public void add(Vector element) {
        samples.add(element);
    }

    public boolean contains(Vector sample) {
        return samples.contains(sample);
    }

    public int size() {
        return samples.size();
    }

    public double getAverageDistance() {
        if (samples.size() == 0) {
            return 0;
        }

        double sum = 0;
        for (Vector v : samples) {
            sum += v.distance(center);
        }
        return sum / samples.size();
    }

    @Override
    public Iterator<Vector> iterator() {
        return samples.iterator();
    }

}
