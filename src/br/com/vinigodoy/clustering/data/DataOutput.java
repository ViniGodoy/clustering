package br.com.vinigodoy.clustering.data;

public interface DataOutput<T> extends AutoCloseable {
    void write(int label);
}