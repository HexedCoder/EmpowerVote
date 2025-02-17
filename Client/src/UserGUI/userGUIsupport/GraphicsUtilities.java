package userGUIsupport;

import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.Raster;
import java.awt.image.WritableRaster;
import java.awt.GraphicsConfiguration;
import java.awt.Transparency;
import java.awt.Graphics;
import java.awt.GraphicsEnvironment;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.io.IOException;
import java.net.URL;
import javax.imageio.ImageIO;

/**
 * Utility class for graphics operations like creating compatible images,
 * loading images, resizing, and working with pixels.
 */
public class GraphicsUtilities {

    // Instance variables
    private static final GraphicsConfiguration graphicsConfig = GraphicsEnvironment.getLocalGraphicsEnvironment()
            .getDefaultScreenDevice().getDefaultConfiguration();

    private GraphicsUtilities() {
    } // End GraphicsUtilities constructor

    /**
     * Gets the default graphics configuration.
     *
     * @return The graphics configuration.
     */
    private static GraphicsConfiguration getGraphicsConfiguration() {
        return graphicsConfig;
    } // End getGraphicsConfiguration

    /**
     * Creates a color model-compatible image.
     *
     * @param image The original image.
     * @return The new compatible image.
     */
    public static BufferedImage createColorModelCompatibleImage(BufferedImage image) {
        ColorModel cm = image.getColorModel();
        return new BufferedImage(cm,
                cm.createCompatibleWritableRaster(image.getWidth(), image.getHeight()),
                cm.isAlphaPremultiplied(), null);
    } // End createColorModelCompatibleImage

    /**
     * Creates a compatible image with the same dimensions as the original.
     *
     * @param image The original image.
     * @return The compatible image.
     */
    public static BufferedImage createCompatibleImage(BufferedImage image) {
        return createCompatibleImage(image, image.getWidth(), image.getHeight());
    } // End createCompatibleImage

    /**
     * Creates a compatible image with the specified dimensions.
     *
     * @param image The original image.
     * @param width The width of the new image.
     * @param height The height of the new image.
     * @return The compatible image.
     */
    public static BufferedImage createCompatibleImage(BufferedImage image, int width, int height) {
        return getGraphicsConfiguration().createCompatibleImage(width, height, image.getTransparency());
    } // End createCompatibleImage

    /**
     * Creates a compatible translucent image with the specified dimensions.
     *
     * @param width The width of the new image.
     * @param height The height of the new image.
     * @return The translucent image.
     */
    public static BufferedImage createCompatibleTranslucentImage(int width, int height) {
        return getGraphicsConfiguration().createCompatibleImage(width, height, Transparency.TRANSLUCENT);
    } // End createCompatibleTranslucentImage

    /**
     * Loads a compatible image from a URL.
     *
     * @param resource The URL of the image.
     * @return The compatible image.
     * @throws IOException If the image cannot be loaded.
     */
    public static BufferedImage loadCompatibleImage(URL resource) throws IOException {
        BufferedImage image = ImageIO.read(resource);
        return toCompatibleImage(image);
    } // End loadCompatibleImage

    /**
     * Converts an image to a compatible image.
     *
     * @param image The image to convert.
     * @return The compatible image.
     */
    public static BufferedImage toCompatibleImage(BufferedImage image) {
        if (image.getColorModel().equals(getGraphicsConfiguration().getColorModel())) {
            return image;
        }
        BufferedImage compatibleImage = getGraphicsConfiguration().createCompatibleImage(image.getWidth(),
                image.getHeight(), image.getTransparency());
        Graphics g = compatibleImage.getGraphics();
        g.drawImage(image, 0, 0, null);
        g.dispose();
        return compatibleImage;
    } // End toCompatibleImage

    /**
     * Creates a thumbnail image with the specified maximum size.
     *
     * @param image The original image.
     * @param newSize The new size of the thumbnail.
     * @return The thumbnail image.
     */
    public static BufferedImage createThumbnailFast(BufferedImage image, int newSize) {
        float ratio;
        int width = image.getWidth();
        int height = image.getHeight();

        if (width > height) {
            if (newSize >= width) {
                throw new IllegalArgumentException("newSize must be lower than the image width");
            } else if (newSize <= 0) {
                throw new IllegalArgumentException("newSize must be greater than 0");
            }
            ratio = (float) width / (float) height;
            width = newSize;
            height = (int) (newSize / ratio);
        } else {
            if (newSize >= height) {
                throw new IllegalArgumentException("newSize must be lower than the image height");
            } else if (newSize <= 0) {
                throw new IllegalArgumentException("newSize must be greater than 0");
            }
            ratio = (float) height / (float) width;
            height = newSize;
            width = (int) (newSize / ratio);
        }

        BufferedImage temp = createCompatibleImage(image, width, height);
        Graphics2D g2 = temp.createGraphics();
        g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g2.drawImage(image, 0, 0, temp.getWidth(), temp.getHeight(), null);
        g2.dispose();
        return temp;
    } // End createThumbnailFast

    /**
     * Creates a thumbnail image with the specified width and height.
     *
     * @param image The original image.
     * @param newWidth The new width of the thumbnail.
     * @param newHeight The new height of the thumbnail.
     * @return The thumbnail image.
     */
    public static BufferedImage createThumbnailFast(BufferedImage image, int newWidth, int newHeight) {
        if (newWidth >= image.getWidth() || newHeight >= image.getHeight()) {
            throw new IllegalArgumentException("newWidth and newHeight cannot be greater than the image dimensions");
        } else if (newWidth <= 0 || newHeight <= 0) {
            throw new IllegalArgumentException("newWidth and newHeight must be greater than 0");
        }

        BufferedImage temp = createCompatibleImage(image, newWidth, newHeight);
        Graphics2D g2 = temp.createGraphics();
        g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g2.drawImage(image, 0, 0, temp.getWidth(), temp.getHeight(), null);
        g2.dispose();
        return temp;
    } // End createThumbnailFast

    /**
     * Creates a thumbnail image with the specified maximum size while maintaining aspect ratio.
     *
     * @param image The original image.
     * @param newSize The new size of the thumbnail.
     * @return The thumbnail image.
     */
    public static BufferedImage createThumbnail(BufferedImage image, int newSize) {
        int width = image.getWidth();
        int height = image.getHeight();
        boolean isWidthGreater = width > height;

        if (isWidthGreater) {
            if (newSize >= width) {
                throw new IllegalArgumentException("newSize must be lower than the image width");
            }
        } else if (newSize >= height) {
            throw new IllegalArgumentException("newSize must be lower than the image height");
        }

        if (newSize <= 0) {
            throw new IllegalArgumentException("newSize must be greater than 0");
        }

        float ratioWH = (float) width / (float) height;
        float ratioHW = (float) height / (float) width;
        BufferedImage thumb = image;

        do {
            if (isWidthGreater) {
                width /= 2;
                if (width < newSize) {
                    width = newSize;
                }
                height = (int) (width / ratioWH);
            } else {
                height /= 2;
                if (height < newSize) {
                    height = newSize;
                }
                width = (int) (height / ratioHW);
            }

            BufferedImage temp = createCompatibleImage(image, width, height);
            Graphics2D g2 = temp.createGraphics();
            g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
            g2.drawImage(thumb, 0, 0, temp.getWidth(), temp.getHeight(), null);
            g2.dispose();
            thumb = temp;
        } while (newSize != (isWidthGreater ? width : height));

        return thumb;
    } // End createThumbnail

    /**
     * Creates a thumbnail image with the specified width and height.
     *
     * @param image The original image.
     * @param newWidth The new width of the thumbnail.
     * @param newHeight The new height of the thumbnail.
     * @return The thumbnail image.
     */
    public static BufferedImage createThumbnail(BufferedImage image, int newWidth, int newHeight) {
        int width = image.getWidth();
        int height = image.getHeight();

        if (newWidth >= width || newHeight >= height) {
            throw new IllegalArgumentException("newWidth and newHeight cannot be greater than the image dimensions");
        } else if (newWidth <= 0 || newHeight <= 0) {
            throw new IllegalArgumentException("newWidth and newHeight must be greater than 0");
        }

        BufferedImage thumb = image;

        do {
            if (width > newWidth) {
                width /= 2;
                if (width < newWidth) {
                    width = newWidth;
                }
            }

            if (height > newHeight) {
                height /= 2;
                if (height < newHeight) {
                    height = newHeight;
                }
            }

            BufferedImage temp = createCompatibleImage(image, width, height);
            Graphics2D g2 = temp.createGraphics();
            g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
            g2.drawImage(thumb, 0, 0, temp.getWidth(), temp.getHeight(), null);
            g2.dispose();
            thumb = temp;
        } while (width != newWidth || height != newHeight);

        return thumb;
    } // End createThumbnail

    /**
     * Gets the pixel data from a specific region of an image.
     *
     * @param img The image to get pixels from.
     * @param x The x-coordinate of the region.
     * @param y The y-coordinate of the region.
     * @param w The width of the region.
     * @param h The height of the region.
     * @param pixels The array to store the pixel data.
     * @return The pixel data.
     */
    public static int[] getPixels(BufferedImage img, int x, int y, int w, int h, int[] pixels) {
        if (w == 0 || h == 0) {
            return new int[0];
        }

        if (pixels == null) {
            pixels = new int[w * h];
        } else if (pixels.length < w * h) {
            throw new IllegalArgumentException("pixels array must have a length >= w*h");
        }

        int imageType = img.getType();
        if (imageType == BufferedImage.TYPE_INT_ARGB || imageType == BufferedImage.TYPE_INT_RGB) {
            Raster raster = img.getRaster();
            return (int[]) raster.getDataElements(x, y, w, h, pixels);
        }

        return img.getRGB(x, y, w, h, pixels, 0, w);
    } // End getPixels

    /**
     * Sets the pixel data for a specific region of an image.
     *
     * @param img The image to set pixels for.
     * @param x The x-coordinate of the region.
     * @param y The y-coordinate of the region.
     * @param w The width of the region.
     * @param h The height of the region.
     * @param pixels The pixel data to set.
     */
    public static void setPixels(BufferedImage img, int x, int y, int w, int h, int[] pixels) {
        if (pixels == null || w == 0 || h == 0) {
            return;
        } else if (pixels.length < w * h) {
            throw new IllegalArgumentException("pixels array must have a length >= w*h");
        }

        int imageType = img.getType();
        if (imageType == BufferedImage.TYPE_INT_ARGB || imageType == BufferedImage.TYPE_INT_RGB) {
            WritableRaster raster = img.getRaster();
            raster.setDataElements(x, y, w, h, pixels);
        } else {
            img.setRGB(x, y, w, h, pixels, 0, w);
        }
    } // End setPixels
} // End GraphicsUtilities