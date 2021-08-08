package com.maxwell.fader.helpers;

import com.maxwell.fader.Exceptions.InvalidLoopTypeException;
import com.maxwell.fader.definitions.LoopType;

import java.util.ArrayList;

import static com.maxwell.fader.config.Config.totalFrames;
import static java.lang.Math.PI;

public class LoopHelper {

    private LoopHelper(){

    }

    // Given an image array of size 'imagesSize' and the index of the Frame to be rendered, along with a LoopType that describes the order
    // in which we will traverse the array of images to eventually end up back at the beginning:
    // Return integers corresponding to the Array index of images to be blended.
    public static ArrayList<Integer> whichImages(int imagesSize, int frameIndex, LoopType loopType) throws InvalidLoopTypeException {
        switch (loopType) {
            case CIRCULAR:
                return whichImagesCircular(imagesSize, frameIndex);
            case OSCILLATORY:
                return whichImagesOscillatory(imagesSize, frameIndex);
        }
        throw new InvalidLoopTypeException();
    }

    // In an array of images 1,2,3,4: Loop in order 1,2,3,4,1...
    public static ArrayList<Integer> whichImagesCircular(int imagesSize, int frameIndex) {
        ArrayList<Integer> result = new ArrayList<>();
        int completion = (imagesSize * frameIndex) / totalFrames;

        result.add(completion);
        if ((completion + 1) == imagesSize) {
            result.add(0);
        } else {
            result.add(completion + 1);
        }

        return result;
    }

    // In an array of images 1,2,3,4: Loop in order 1,2,3,4,3,2,1...
    public static ArrayList<Integer> whichImagesOscillatory(int imagesSize, int frameIndex) {
        return null;
    }

    // Returns a smooth alpha blend based on a Sin Squared pattern
    public static double getAlpha(int imagesSize, int frameIndex) {
        double x = (double)((imagesSize * frameIndex) % totalFrames) / totalFrames;
        double result = Math.sin(x * PI / 2);
        return result * result;
    }
}
