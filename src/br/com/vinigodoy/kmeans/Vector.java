/******************************************************************************
 *
 * COPYRIGHT Vin�cius G. Mendon�a ALL RIGHTS RESERVED.
 *
 * This software cannot be copied, stored, distributed without Vinícius G.Mendonça prior authorization.
 *
 * This file was made available on http://www.pontov.com.br and it is free to be restributed or used under Creative
 * Commons license 2.5 br: http://creativecommons.org/licenses/by-sa/2.5/br/
 *
 ******************************************************************************/
package br.com.vinigodoy.kmeans;

import java.util.Arrays;

public final class Vector implements Cloneable {

    public double v[];

    public Vector(double... values) {
        if (values.length < 1) {
            throw new IllegalArgumentException("Vectors should have at least 1 dimension!");
        }
        v = values;
    }

    public Vector addMe(double... values) {
        if (values.length != v.length) {
            throw new IllegalArgumentException("Trying to add different vectors! " + v.length + " != " + values.length);
        }
        for (int i = 0; i < values.length; ++i) {
            v[i] += values[i];
        }
        return this;
    }

    public Vector addMe(Vector other) {
        return addMe(other.v);
    }

    public Vector add(Vector other) {
        return clone().addMe(other);
    }

    @Override
    public boolean equals(Object o) {
        if (o == null) {
            return false;
        }
        if (o == this) {
            return true;
        }
        if (o.getClass() != this.getClass()) {
            return false;
        }

        Vector other = (Vector) o;
        return Arrays.equals(v, other.v);
    }

    public Vector subtractMe(double... values) {
        if (values.length != v.length) {
            throw new IllegalArgumentException("Trying to add different vectors!");
        }
        for (int i = 0; i < values.length; ++i) {
            v[i] -= values[i];
        }
        return this;
    }

    public Vector subtractMe(Vector other) {
        return subtractMe(other.v);
    }

    public Vector subtract(Vector other) {
        return clone().subtractMe(other);
    }

    public Vector multiplyMe(double c) {
        for (int i = 0; i < v.length; ++i) {
            v[i] *= c;
        }
        return this;

    }

    public Vector multiply(double c) {
        return clone().multiplyMe(c);
    }

    public Vector divideMe(double c) {
        return multiplyMe(1.0 / c);
    }

    public Vector divide(double t) {
        return clone().divideMe(t);
    }

    public Vector negateMe() {
        return multiplyMe(-1);
    }

    public Vector negate() {
        return multiply(-1);
    }

    public double dot(Vector other) {
        if (other.v.length != v.length) {
            throw new IllegalArgumentException("Trying to add different vectors!");
        }

        double sum = 0;
        for (int i = 0; i < v.length; ++i) {
            sum += v[i] * other.v[i];
        }

        return sum;
    }

    public double getSizeSqr() {
        double sum = 0;

        for (double value : v) {
            sum += value * value;
        }

        return sum;
    }

    public double getSize() {
        return Math.sqrt(getSizeSqr());
    }

    public Vector setSize(double size) {
        return size == 0 ? multiply(0) : normalizeMe().multiplyMe(size);
    }

    public Vector truncate(double size) {
        return getSizeSqr() > size * size ? setSize(size) : this;
    }

    public Vector normalizeMe() {
        double size = getSize();
        if (size == 0) {
            return this;
        }
        return divideMe(size);
    }

    public Vector normalize() {
        return clone().normalizeMe();
    }

    public double distance(Vector other) {
        return other.subtract(this).getSize();
    }

    public double distanceSqr(Vector other) {
        return other.subtract(this).getSizeSqr();
    }

    @Override
    protected Vector clone() {
        double copy[] = Arrays.copyOf(v, v.length);
        return new Vector(copy);
    }

    public double[] toArray() {
        return Arrays.copyOf(v, v.length);
    }

    private int doubleHash(double number) {
        long value = Double.doubleToLongBits(number);
        return (int) (value ^ value >>> 32);
    }

    @Override
    public int hashCode() {
        final int prime = 17;
        int result = 1;
        for (double value : v) {
            result = prime * result + doubleHash(value);
        }
        return result;
    }

    @Override
    public String toString() {
        return toString("%.4f");
    }

    public String toString(String numberFormat) {
        StringBuilder sb = new StringBuilder("[");
        sb.append(String.format(numberFormat, v[0]));
        String nFormat = " " + numberFormat;
        for (int i = 1; i < v.length; ++i) {
            sb.append(String.format(nFormat, v[i]));
        }
        return sb.append("]").toString();
    }
}
