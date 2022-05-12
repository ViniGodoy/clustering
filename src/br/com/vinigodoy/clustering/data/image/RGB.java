package br.com.vinigodoy.clustering.data.image;

public class RGB {
    protected final int r;
    protected final int g;
    protected final int b;

    public RGB(int rgb) {
        this.r = (rgb >> 16) & 0xFF;
        this.g = (rgb >> 8) & 0xFF;
        this.b = rgb & 0xFF;
    }

    public RGB(int r, int g, int b) {
        this.r = r;
        this.g = g;
        this.b = b;
    }

    /**
     * Returns the red component in the range 0-255 in the default sRGB
     * space.
     * @return the red component.
     */
    public int r() {
        return Math.min(255, r);
    }

    /**
     * Returns the green component in the range 0-255 in the default sRGB
     * space.
     * @return the green component.
     */
    public int g() {
        return Math.min(255, g);
    }

    /**
     * Returns the blue component in the range 0-255 in the default sRGB
     * space.
     * @return the blue component.
     */
    public int b() {
        return Math.min(255, b);
    }

    public int rgb() {
        return 0xff000000 | (r() << 16) | (g() << 8) | b();
    }

    @Override
    public String toString() {
        return String.format("rgb(%02d, %02d, %02d)", r(), g(), b());
    }
}

