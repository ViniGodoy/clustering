package br.com.vinigodoy.knn;

import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Cluster {
    private String name;
    private Color color;

    private List<Point> points = new ArrayList<>();

    public Cluster(String name, Color color) {
        this.name = name;
        this.color = color;
    }

    public String getName() {
        return name;
    }

    public Color getColor() {
        return color;
    }

    public void addPoint(Color color) {
        points.add(new Point(this, color));
    }

    public List<Point> getPoints() {
        return Collections.unmodifiableList(points);
    }
}
