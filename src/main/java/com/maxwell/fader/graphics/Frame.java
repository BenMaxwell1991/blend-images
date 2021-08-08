package com.maxwell.fader.graphics;

import lombok.Getter;
import lombok.Setter;

import java.awt.image.BufferedImage;

@Getter
@Setter
public class Frame {

    private BufferedImage image;
    private int index;

    Frame(BufferedImage image, int index) {
        setImage(image);
        setIndex(index);
    }
}
