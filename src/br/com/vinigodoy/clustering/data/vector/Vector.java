/******************************************************************************
 * COPYRIGHT Vin�cius G. Mendon�a ALL RIGHTS RESERVED.
 * <p>
 * This software cannot be copied, stored, distributed without Vinícius G.Mendonça prior authorization.
 * <p>
 * This file was made available on http://www.pontov.com.br and it is free to be restributed or used under Creative
 * Commons license 2.5 br: http://creativecommons.org/licenses/by-sa/2.5/br/
 ******************************************************************************/
package br.com.vinigodoy.clustering.data.vector;

import java.awt.*;
import java.util.Arrays;

public final class Vector implements Cloneable {

    public double[] v;

    public Vector(double... values) {
        if (values.length < 1) {
            throw new IllegalArgumentException("Vectors should have at least 1 dimension!");
        }
        v = values;
    }

    public static Vector fromRGB(int rgb) {
        return fromColor(new Color(rgb));
    }

    public static Vector fromColor(Color c) {
        return new Vector(c.getRed(), c.getGreen(), c.getBlue());
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

    @Override
    public boolean equals(Object o) {
        return o == this ||
                o != null &&
                        o.getClass() == this.getClass() &&
                        Arrays.equals(v, ((Vector) o).v);

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
        double sum = 0;

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
        final var size = getSize();
        if (size == 0) {
            return this;
        }
        return divide(size);
    }


    public double distance(Vector other) {
        return subtract(this, other).getSize();
    }

    public double distanceSqr(Vector other) {
        return subtract(this, other).getSizeSqr();
    }

    @Override
    protected Vector clone() {
        return new Vector(Arrays.copyOf(v, v.length));
    }

    public double[] toArray() {
        return Arrays.copyOf(v, v.length);
    }

    private int doubleHash(double number) {
        final var value = Double.doubleToLongBits(number);
        return (int) (value ^ value >>> 32);
    }

    @Override
    public int hashCode() {
        final var prime = 17;
        var result = 1;
        for (var value : v) {
            result = prime * result + doubleHash(value);
        }
        return result;
    }

    @Override
    public String toString() {
        return toString("%.4f");
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
