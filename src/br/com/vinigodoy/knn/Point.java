package br.com.vinigodoy.knn;

import java.awt.Color;

import br.com.vinigodoy.math.Vector;

public class Point {
	private Vector local;

	public Cluster classe;
	
	public Point(Cluster classe, Color color) {
		this.classe = classe;
		this.local = new Vector(color.getRed(), color.getGreen(), color.getBlue());
	}
	
	public Cluster getClasse() {
		return classe;
	}
	
	public Vector getLocal() {
		return local;
	}
}