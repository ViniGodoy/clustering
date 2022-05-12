package br.com.vinigodoy.samples;

import br.com.vinigodoy.clustering.knn.Knn;
import br.com.vinigodoy.clustering.data.image.ImageDatasource;
import br.com.vinigodoy.clustering.data.image.RGB;
import br.com.vinigodoy.clustering.data.image.RGBSolver;

/**
 * Shows how to use knn to classify an image in 3 classes.
 */
public class KnnWithImage {
    private static final int PLANT = 1;
    private static final int SOIL = 2;
    private static final int SKY = 3;

    public static void main(String[] args) throws Exception {
        //Load the image using an image data source
        final var ds = ImageDatasource.load("./images/field.jpg");
        final var name = "./images/knn-field";

        //Create the image output. The datasource provide an output with the same dimensions of the loaded image.
        try (var out = ds.createOutput(name)) {
            //Create a knn with an RGB Solver.
            final var knn = new Knn<>(new RGBSolver());

            //Classes creation. These colors where manually chosen in the image

            //Vegetation
            knn.addSamples(PLANT,
                new RGB(90, 130, 63),
                new RGB(150, 193, 113 ),
                new RGB(41, 47, 39),
                new RGB(168, 166, 107)
            );

            //Soil
            knn.addSamples(SOIL,
                new RGB(244, 174, 129),
                new RGB(224, 151, 97 ),
                new RGB(230, 212, 191)
            );

            //Sky (including clouds)
            knn.addSamples(SKY,
                new RGB(255, 254, 255),
                new RGB(127, 169, 210 ),
                new RGB(59, 130, 195),
                new RGB(196, 210, 231)
            );

            //Label colors in output. If you ommit this lines, random colors will be used.
            out.setLabelColor(SKY, 0x0000FF) //Blue
                .setLabelColor(PLANT, 0x00FF00) //Green
                .setLabelColor(SOIL, 0xFF0000); //Red

            //Since several pixels have the same color, and RGB is a cacheable data type, activate the cache to reduce
            //classification effort.
            knn.setCached(true)
                .classify(ds, out); //Do the classification and save it to the output
        }
    }
}
