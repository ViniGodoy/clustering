package br.com.vinigodoy.clustering.type;

public interface DataOutput<T> extends AutoCloseable {
    void write(int label);
}