package br.com.vinigodoy.knn;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import br.com.vinigodoy.math.Vector;

public class Knn {
	private List<Cluster> clusters = new ArrayList<>();
	private Map<Integer, List<Point>> cache = new HashMap<>();

	public Knn() {
	}

	public void addCluster(Cluster classe) {
		clusters.add(classe);
	}

	public Cluster classify(int neighboors, int color) {
		return findCluster(measureDistances(color), neighboors);
	}

	private List<Point> measureDistances(int color) {
		Vector pixel = Vector.fromRGB(color);

		// Mede a dist�ncia at� os pontos
		if (!cache.containsKey(color)) {
			List<Point> points = new ArrayList<>();
			for (Cluster cluster : clusters) {
				points.addAll(cluster.getPoints());
			}
			Collections.sort(points, new PointComparator(pixel));
			cache.put(color, points);
		}
		return cache.get(color);
	}

	private Cluster findCluster(List<Point> points, int neighboors) {
		// Conta os elementos em cada classe
		Map<Cluster, Integer> counting = new HashMap<>();
		for (Cluster cluster : clusters)
			counting.put(cluster, 0);

		for (int i = 0; i < neighboors; i++) {
			Cluster cluster = points.get(i).classe;
			counting.put(cluster, counting.get(cluster) + 1);
		}

		// Localiza a classe com maior counting
		Cluster result = null;
		int total = 0;
		for (Map.Entry<Cluster, Integer> item : counting.entrySet()) {
			if (item.getValue() > total) {
				result = item.getKey();
				total = item.getValue();
			}
		}
		return result;
	}

	public static class PointComparator implements Comparator<Point> {
		private Vector pixel;

		public PointComparator(Vector pixel) {
			this.pixel = pixel;
		}

		public double getDistance(Point ponto) {
			return ponto.getLocal().distance(pixel);
		}

		@Override
		public int compare(Point point, Point point2) {
			double dist1 = getDistance(point);
			double dist2 = getDistance(point2);

			if (dist1 < dist2)
				return -1;

			if (dist1 > dist2)
				return 1;

			return 0;
		}
	}
}
