package br.com.vinigodoy.clustering.type.color;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Iterator;


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

    public ImageFileOutput createOutput(String path) {
        return new ImageFileOutput(image.getWidth(), image.getHeight(), path);
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
