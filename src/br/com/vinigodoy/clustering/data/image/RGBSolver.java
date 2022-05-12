package br.com.vinigodoy.clustering.data.image;

import br.com.vinigodoy.clustering.data.ElementSolver;

public class RGBSolver implements ElementSolver<RGB> {
    @Override
    public double distance(RGB e1, RGB e2) {
        final var r = e1.r - e2.r;
        final var g = e1.g - e2.g;
        final var b = e1.b - e2.b;
        return Math.sqrt(r*r + g*g + b*b);
    }

    @Override
    public RGB add(RGB e1, RGB e2) {
        return new RGB(e1.r + e2.r, e1.g + e2.g, e1.b + e2.b);
    }

    @Override
    public RGB divide(RGB e, int scalar) {
        return new RGB(e.r / scalar, e.g / scalar, e.b / scalar);
    }

    @Override
    public Long cacheValue(RGB element) {
        return (long) element.rgb();
    }
}
