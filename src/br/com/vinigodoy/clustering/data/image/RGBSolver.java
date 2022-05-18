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

    public RGB create() {
        return new RGB();
    }


    @Override
    public void accumulate(RGB acc, RGB element) {
        acc.r += element.r;
        acc.g += element.g;
        acc.b += element.b;
    }

    @Override
    public RGB divide(RGB e, int scalar) {
        return new RGB(e.r / scalar, e.g / scalar, e.b / scalar);
    }
}
