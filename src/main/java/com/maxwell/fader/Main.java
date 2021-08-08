package com.maxwell.fader;

import com.maxwell.fader.helpers.MovieHelper;

import java.awt.image.BufferedImage;
import java.util.ArrayList;

import static com.maxwell.fader.helpers.ImageHelper.*;

public class Main {

    public static void main(String [] args) {
        ArrayList<BufferedImage> images = new ArrayList<>();
        images.add(getBufferedImage("win10blue.png"));
        images.add(getBufferedImage("win10mix1.png"));
        images.add(getBufferedImage("win10mix2.png"));

        MovieHelper.createMovieFromBlend(images);
    }
}
