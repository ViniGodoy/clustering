package br.com.vinigodoy.samples;

import br.com.vinigodoy.clustering.knn.Knn;
import br.com.vinigodoy.clustering.type.color.ImageDatasource;
import br.com.vinigodoy.clustering.type.color.RGB;
import br.com.vinigodoy.clustering.type.color.RGBSolver;

public class KnnWithImage {
    private static final int PLANT = 1;
    private static final int SOIL = 2;
    private static final int SKY = 3;

    public static void main(String[] args) throws Exception {
        //Cloud class
        final var ds = ImageDatasource.load("./images/field.jpg");
        final var name = "./images/knn-field";
        try (var out = ds.createOutput(name)) {
            final var knn = new Knn<>(new RGBSolver());
            //Classes creation
            //Vegetation
            knn.addSample(PLANT, new RGB(90, 130, 63));
            knn.addSample(PLANT, new RGB(150, 193, 113 ));
            knn.addSample(PLANT, new RGB(41, 47, 39));
            knn.addSample(PLANT, new RGB(168, 166, 107));

            //Soil
            knn.addSample(SOIL, new RGB(244, 174, 129));
            knn.addSample(SOIL, new RGB(224, 151, 97 ));
            knn.addSample(SOIL, new RGB(230, 212, 191));

            //Sky (including clouds)
            knn.addSample(SKY, new RGB(255, 254, 255));
            knn.addSample(SKY, new RGB(127, 169, 210 ));
            knn.addSample(SKY, new RGB(59, 130, 195));
            knn.addSample(SKY, new RGB(196, 210, 231));

            //Label colors in output
            out.setLabelColor(SKY, 0x0000FF);
            out.setLabelColor(PLANT, 0x00FF00);
            out.setLabelColor(SOIL, 0xFF0000);

            knn.setCached(true)
                    .classify(ds, out);
        }
    }
}
