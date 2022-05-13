package br.com.vinigodoy.clustering.data.image;

import br.com.vinigodoy.clustering.kmeans.CentroidSelector;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.Iterator;

import static br.com.vinigodoy.clustering.kmeans.CentroidSelector.RND;

public class ImageDatasource implements Iterable<RGB> {
    private final BufferedImage image;

    public ImageDatasource(BufferedImage image) {
        this.image = image;
    }

    public static ImageDatasource load(String file) {
        try {
            return new ImageDatasource(ImageIO.read(new File(file)));
        } catch (IOException e) {
            throw new IllegalArgumentException(e.getMessage(), e);
        }
    }

    public ImageOutput createOutput(String path) {
        return new ImageOutput(image.getWidth(), image.getHeight(), path);
    }

    /**
     * A more efficient random centroid selection strategy for this image.
     * Pixels are chosen at a random line and column, directly from the image. Guarantees that selected pixels are
     * different.
     *
     * @return The centroids.
     */
    public CentroidSelector<RGB> random() {
        return ((data, classes) -> {
            final var centroids = new HashSet<RGB>();
            while (centroids.size() < classes) {
                final var x = RND.nextInt(image.getWidth());
                final var y = RND.nextInt(image.getHeight());
                centroids.add(new RGB(image.getRGB(x, y)));
            }
            return centroids;
        });
    }

    @Override
    public Iterator<RGB> iterator() {
        return new Iterator<>() {
            private int pixel = -1;

            @Override
            public boolean hasNext() {
                return pixel < (image.getWidth() * image.getHeight() - 1);
            }

            @Override
            public RGB next() {
                pixel = pixel + 1;
                final var x = pixel % image.getWidth();
                final var y = pixel / image.getWidth();
                return new RGB(image.getRGB(x, y));
            }
        };
    }
}
