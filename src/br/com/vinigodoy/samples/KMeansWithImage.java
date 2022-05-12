package br.com.vinigodoy.samples;

import br.com.vinigodoy.clustering.type.color.*;

import br.com.vinigodoy.clustering.kmeans.Kmeans;

/**
 * Shows how to use k-means to simplify colors in a image
 */
public class KMeansWithImage {
    private static final int CLUSTERS = 20;

    public static void main(String[] args) throws Exception {
        final var ds = ImageDatasource.load("./images/field.jpg");
        final var name = "./images/kmeans-" + CLUSTERS + "-field";
        try (var out = ds.createOutput(name)) {
            final var kmeans =
                    new Kmeans<>(new RGBSolver())
                            .train(ds, CLUSTERS);
            out.setColorsByClosest(kmeans);
            kmeans.classify(ds, out);
        }
    }
}
