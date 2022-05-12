package br.com.vinigodoy.clustering.data.image;

import br.com.vinigodoy.clustering.kmeans.Kmeans;
import br.com.vinigodoy.clustering.data.DataOutput;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.HashMap;

public class ImageOutput implements DataOutput<RGB> {
    private static final int[] COLORS = {
        0xff0000, 0x00ff00, 0x0000ff, 0x00ffff, 0xff00ff, 0xffff00, 0xffffff,
        0x9ba37e, 0xd35400, 0xf69785, 0x8e44ad, 0x2ecc71, 0x7f8c8d, 0x3498db,
        0xb49255, 0xe74c3c, 0xec87bf, 0x563aa7, 0x1abc9c, 0x587c7e, 0x2980b9,
        0xf39c12, 0xc0392b, 0xd870ad, 0x3d277a, 0x16a085, 0x34495e, 0x0f1386,
        0xe67e22, 0xa94136, 0x9b59b6, 0x510c0c, 0x27ae60, 0x2c3e50, 0x050851
    };

    private final BufferedImage img;
    private final String path;
    private int count = 0;
    private final HashMap<Integer, Integer> labelColors = new HashMap<>();


    public ImageOutput(int w, int h, String path) {
        this.img = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
        this.path = path.endsWith(".png") ? path : path + ".png";
    }

    public ImageOutput setLabelColor(int label, int color) {
        labelColors.put(label, color);
        return this;
    }

    public void setColorsByClosest(Kmeans<RGB> kmeans) {
        kmeans.getClusters().forEach(c -> setLabelColor(c.getLabel(), c.getClosest().rgb()));
    }

    public void setColorsByCentroid(Kmeans<RGB> kmeans) {
        kmeans.getClusters().forEach(c -> setLabelColor(c.getLabel(), c.getCentroid().rgb()));
    }

    private int labelColor(int label) {
        return labelColors.containsKey(label) ?
                labelColors.get(label) : COLORS[Math.abs(label) % COLORS.length];
    }

    @Override
    public void write(int label) {
        final var x = count % img.getWidth();
        final var y = count / img.getWidth();

        img.setRGB(x, y, labelColor(label));
        count++;
    }

    public BufferedImage getImage() {
        return img;
    }

    @Override
    public void close() throws Exception {
        ImageIO.write(img, "png", new File(path));
    }
}
