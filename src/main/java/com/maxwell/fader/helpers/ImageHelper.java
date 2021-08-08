package com.maxwell.fader.helpers;

import com.maxwell.fader.Main;
import com.maxwell.fader.graphics.Pixel;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.WritableRaster;
import java.io.IOException;
import java.io.InputStream;


public class ImageHelper {

    private ImageHelper() {

    }

    public static BufferedImage deepCopy(BufferedImage bi) {
        ColorModel cm = bi.getColorModel();
        boolean isAlphaPremultiplied = cm.isAlphaPremultiplied();
        WritableRaster raster = bi.copyData(null);
        return new BufferedImage(cm, raster, isAlphaPremultiplied, null);
    }

    public static BufferedImage doAlphaBlend(BufferedImage img1, BufferedImage img2, double alpha) {

        int width = Math.max(img1.getWidth(), img2.getWidth());
        int height = Math.max(img1.getHeight(), img2.getHeight());
        BufferedImage blended = deepCopy(img1);

        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                Pixel pixel1 = new Pixel(img1.getRGB(x, y));
                Pixel pixel2 = new Pixel(img2.getRGB(x, y));
                Pixel blendedPixel = Pixel.alphaBlend(pixel1, pixel2, alpha);

                blended.setRGB(x, y, blendedPixel.getRGB());
            }
        }

        return blended;
    }

    public static BufferedImage getBufferedImage(String path) {

        InputStream is1 = Main.class.getClassLoader().getResourceAsStream(path);

        try {
            if (is1 == null) {
                throw new IOException();
            }

            return ImageIO.read(is1);

        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static BufferedImage RGB_2_3BYTE_BGR(BufferedImage input) {
        int w = input.getWidth();
        int h = input.getHeight();
        BufferedImage imageBGR = new BufferedImage(w, h, BufferedImage.TYPE_3BYTE_BGR);

        for (int x = 0; x < w; x++) {
            for (int y = 0; y < h; y++) {
                imageBGR.setRGB(x, y, input.getRGB(x, y));
            }
        }
        return imageBGR;
    }
}