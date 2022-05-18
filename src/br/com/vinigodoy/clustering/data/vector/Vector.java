package br.com.vinigodoy.clustering.data.vector;

import java.util.Arrays;
import java.util.Objects;

public final class Vector implements Cloneable {
    public double[] v;

    public Vector(double... values) {
        if (values.length < 1) {
            throw new IllegalArgumentException("Vectors should have at least 1 dimension!");
        }
        v = values;
    }

    public static Vector add(Vector v1, Vector v2) {
        return v1.clone().add(v2);
    }

    public static Vector subtract(Vector v1, Vector v2) {
        return v1.clone().subtract(v2);
    }

    public static Vector multiply(Vector v, double s) {
        return v.clone().multiply(s);
    }

    public static Vector divide(Vector v, double s) {
        return v.clone().divide(s);
    }

    public static Vector negate(Vector v) {
        return v.clone().multiply(-1);
    }

    public static Vector normalize(Vector v) {
        return v.clone().normalize();
    }

    public static double distance(Vector v1, Vector v2) {
        return Math.sqrt(distanceSqr(v1, v2));
    }

    public static double distanceSqr(Vector v1, Vector v2) {
        return subtract(v1, v2).getSizeSqr();
    }

    public Vector add(double... values) {
        if (values.length != v.length) {
            throw new IllegalArgumentException("Trying to add different vectors! " + v.length + " != " + values.length);
        }
        for (var i = 0; i < values.length; ++i) {
            v[i] += values[i];
        }
        return this;
    }

    public Vector add(Vector other) {
        return add(other.v);
    }

    public Vector subtract(double... values) {
        if (values.length != v.length) {
            throw new IllegalArgumentException("Trying to add different vectors!");
        }
        for (var i = 0; i < values.length; ++i) {
            v[i] -= values[i];
        }
        return this;
    }

    public Vector subtract(Vector other) {
        return subtract(other.v);
    }

    public Vector multiply(double s) {
        for (var i = 0; i < v.length; ++i) {
            v[i] *= s;
        }
        return this;
    }

    public Vector divide(double c) {
        return multiply(1.0 / c);
    }

    public Vector negate() {
        return multiply(-1);
    }

    public double dot(Vector other) {
        if (other.v.length != v.length) {
            throw new IllegalArgumentException("Trying to add different vectors!");
        }

        double sum = 0;
        for (var i = 0; i < v.length; ++i) {
            sum += v[i] * other.v[i];
        }

        return sum;
    }

    public double getSizeSqr() {
        var sum = 0.0;

        for (var value : v) {
            sum += value * value;
        }

        return sum;
    }

    public double getSize() {
        return Math.sqrt(getSizeSqr());
    }

    public Vector setSize(double size) {
        return size == 0 ? multiply(0) : normalize().multiply(size);
    }

    public Vector truncate(double size) {
        return getSizeSqr() > size * size ? setSize(size) : this;
    }

    public Vector normalize() {
        return getSizeSqr() == 0 ? this : divide(getSize());
    }

    public double[] toArray() {
        return Arrays.copyOf(v, v.length);
    }

    @Override
    public boolean equals(Object o) {
        return o.getClass() == getClass() && Objects.equals(this, o);
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(v);
    }

    @Override
    public String toString() {
        return toString("%.4f");
    }

    @Override
    protected Vector clone() {
        return new Vector(Arrays.copyOf(v, v.length));
    }

    public String toString(String numberFormat) {
        final var sb = new StringBuilder("[");
        sb.append(String.format(numberFormat, v[0]));
        final var nFormat = " " + numberFormat;
        for (var i = 1; i < v.length; ++i) {
            sb.append(String.format(nFormat, v[i]));
        }
        return sb.append("]").toString();
    }
}
