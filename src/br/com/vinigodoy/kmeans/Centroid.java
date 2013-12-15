package br.com.vinigodoy.kmeans;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Centroid implements Iterable<Vector> {
    private int number;
    private Vector center;
    private List<Vector> points = new ArrayList<>();

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
        Vector sum = new Vector(points.get(0).v);
        for (int i = 1; i < points.size(); i++) {
            sum.addMe(points.get(i));
        }

        return new Centroid(number, sum.divideMe(points.size()));
    }

    public double distance(Vector ponto) {
        return ponto.distance(center);
    }

    public void add(Vector element) {
        points.add(element);
    }

    public boolean contains(Vector element) {
        return points.contains(element);
    }

    public int size() {
        return points.size();
    }

    public double getAverageDistance() {
        if (points.size() == 0) {
            return 0;
        }

        double sum = 0;
        for (Vector v : points) {
            sum += v.distance(center);
        }
        return sum / points.size();
    }

    @Override
    public Iterator<Vector> iterator() {
        return points.iterator();
    }

}
