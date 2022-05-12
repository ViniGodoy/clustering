package br.com.vinigodoy.samples;

import br.com.vinigodoy.clustering.data.image.*;

import br.com.vinigodoy.clustering.kmeans.Kmeans;

/**
 * Shows how to use k-means to simplify colors in a image
 */
public class KMeansWithImage {
    private static final int CLUSTERS = 20;

    public static void main(String[] args) throws Exception {
        //We create an image data source to read image data
        final var ds = ImageDatasource.load("./images/field.jpg");
        final var name = "./images/kmeans-" + CLUSTERS + "-field";

        //The image data source provide an easy way to create an image DataOutput, with the same dimensions of the
        // original image
        try (var out = ds.createOutput(name)) {
            //Create the kmeans object. Since we'll use images, we select the RGB solver.
            final var kmeans =
                    new Kmeans<>(new RGBSolver())
                            .setCentroidSelector(ds.random()) //Use an efficient random centroid selector for images
                            .train(ds, CLUSTERS); //Do the trainig

            //Set the output color based on the closest point to each centroid. This will guarantee that only colors
            //present in the image will be in the output. If you ommit this line, random colors will be used.
            out.setColorsByClosest(kmeans);

            //Classify the image based in the output.
            kmeans.classify(ds, out);
        }
    }
}
