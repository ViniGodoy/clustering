package br.com.vinigodoy.clustering.data.image;

/**
 * Represents an RGB pixel. Uses 3 integers to represent each color channel, allowing them to be outside the range of
 * [0-255]. This is important for accumulation and averaging functions, used in algorithms.
 *
 * When converted back to the integer format, R, G and B values are clamped to [0-255] interval. Alpha channel is
 * discarded and alpha component will always be considered 255 (fully opaque).
 */
public class RGB {
    protected int r;
    protected int g;
    protected int b;

    /**
     * Returns a new RGB object with the given color.
     * @param rgb The color, in argb format. Alpha is discarded.
     */
    public RGB(int rgb) {
        this((rgb >> 16) & 0xFF, (rgb >> 8) & 0xFF, rgb & 0xFF);
    }

    /**
     * Returns a new RGB object with the given color.
     * @param r Red component.
     * @param g Green component.
     * @param b Blue component.
     */
    public RGB(int r, int g, int b) {
        this.r = r;
        this.g = g;
        this.b = b;
    }

    /**
     * Construct a new gray RGB value using the same value for R, G and B.
     * @param tone The tone to use.
     * @return The RGB object.
     */
    public static RGB gray(int tone) {
        return new RGB(tone, tone, tone);
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

    /**
     * Returns the color in ARGB format. Alpha will be always set to 255 (0xFF).
     * R, G and B components are automatically clamped to [0-255] interval.
     * @return The color in ARGB format.
     */
    public int rgb() {
        return 0xff000000 | (r() << 16) | (g() << 8) | b();
    }

    @Override
    public int hashCode() {
        return rgb();
    }

    /**
     * Compares two RGB values. The comparison is done using the rgb() function.
     * @param obj The object to compare to
     * @return True if two RGB values are equal.
     * @see #rgb()
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == null || obj.getClass() != getClass()) return false;
        if (obj == this) return true;
        return rgb() == ((RGB)obj).rgb();
    }

    @Override
    public String toString() {
        return String.format("rgb(%02d, %02d, %02d)", r(), g(), b());
    }
}