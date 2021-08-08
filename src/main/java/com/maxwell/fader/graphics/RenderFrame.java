package com.maxwell.fader.graphics;

import com.maxwell.fader.Exceptions.InvalidLoopTypeException;
import lombok.Getter;
import lombok.Setter;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.concurrent.Callable;

import static com.maxwell.fader.config.Config.totalFrames;
import static com.maxwell.fader.definitions.LoopType.CIRCULAR;
import static com.maxwell.fader.helpers.ImageHelper.doAlphaBlend;
import static com.maxwell.fader.helpers.LoopHelper.getAlpha;
import static com.maxwell.fader.helpers.LoopHelper.whichImages;


// Execute this object to blend two images together with an alpha value based upon their index.
@Getter
@Setter
public class RenderFrame implements Callable<Frame> {


    private ArrayList<BufferedImage> images;
    private Integer index;

    public RenderFrame(ArrayList<BufferedImage> images, Integer index) {
        setImages(images);
        setIndex(index);
    }

    @Override
    public Frame call() throws InvalidLoopTypeException {
        double alpha = getAlpha(images.size(), index);
        ArrayList<Integer> indexes = whichImages(images.size(), index, CIRCULAR);

        if (index % 5 == 0) {
            System.out.println("ThreadPool Size: " + Thread.activeCount());
            System.out.println("Thread active: " + Thread.currentThread());
            System.out.println("Frames completed: " + index + "/" + totalFrames + "\n");
        }

        BufferedImage img1 = images.get(indexes.get(0));
        BufferedImage img2 = images.get(indexes.get(1));

        return new Frame(doAlphaBlend(img1,img2, alpha), index);
    }
}
