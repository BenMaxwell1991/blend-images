package com.maxwell.fader.graphics;


import jdk.nashorn.internal.ir.annotations.Immutable;
import lombok.Getter;

import static java.lang.Math.round;

@Getter
@Immutable
public class Pixel {

    private final int red;
    private final int green;
    private final int blue;
    private final int RGB;
    private final int BGR3;

    public Pixel(int colourRGB) {
        red = (colourRGB & 0x00ff0000) >> 16;
        green = (colourRGB & 0x0000ff00) >> 8;
        blue = colourRGB & 0x000000ff;
        RGB = colourRGB;
        BGR3 = (blue << 16) | (this.green << 8) | (red);
    }

    public static Pixel alphaBlend(Pixel pixel1, Pixel pixel2, double alpha) {

        int r = (int) round((pixel1.getRed() * (1 - alpha)) + (pixel2.getRed() * alpha));
        int g = (int) round((pixel1.getGreen() * (1 - alpha)) + (pixel2.getGreen() * alpha));
        int b = (int) round((pixel1.getBlue() * (1 - alpha)) + (pixel2.getBlue() * alpha));
        int colour = (r << 16) | (g << 8) | (b);

        return new Pixel(colour);
    }
}
