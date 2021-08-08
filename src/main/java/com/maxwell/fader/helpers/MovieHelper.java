package com.maxwell.fader.helpers;

import com.maxwell.fader.graphics.Frame;
import com.maxwell.fader.graphics.RenderFrame;
import com.xuggle.mediatool.IMediaWriter;
import com.xuggle.mediatool.ToolFactory;

import java.awt.image.BufferedImage;
import java.util.*;
import java.util.concurrent.*;

import static com.maxwell.fader.config.Config.*;
import static com.xuggle.xuggler.Global.DEFAULT_TIME_UNIT;

public class MovieHelper {

    private MovieHelper() {

    }

    // Take images 1 and 2 and generate a movie, blending slowly from image 1 to 2 and then back to 1 again.
    public static void createMovieFromBlend(ArrayList<BufferedImage> images) {

        int width = images.get(0).getWidth();
        int height = images.get(0).getHeight();
        final IMediaWriter writer = ToolFactory.makeWriter("movie.mov");
        writer.addVideoStream(vsIndex, vsID, width, height);

        // Render Frames concurrently and orders them
        TreeMap<Integer, BufferedImage> frames = renderFrames(images);

        // Encode frames as a movie
        for (BufferedImage frame: frames.values()) {
            writer.encodeVideo(vsIndex, frame, nextFrameTime, DEFAULT_TIME_UNIT);
            nextFrameTime += frameRate;

            double timestamp = (double) nextFrameTime / 1000000.0;

            if (nextFrameTime % (10 * frameRate) == 0) {
                System.out.println("Encoded movie duration: " + timestamp + "/" + durationSeconds + " Seconds");
                System.out.println("Frame Rate: " + fps + " fps\n");
            }
        }

        writer.close();
    }

    // Concurrently renders the frames for the movie, return them in an ordered TreeMap
    private static TreeMap<Integer, BufferedImage> renderFrames(ArrayList<BufferedImage> images) {

        TreeMap<Integer, BufferedImage> result = new TreeMap<>();

        // Thread pool setup
        ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(THREADS - 1);
        executor.setKeepAliveTime(1000, TimeUnit.MILLISECONDS);
        ArrayBlockingQueue<Callable<Frame>> taskQueue = new ArrayBlockingQueue<>(totalFrames);

        // Add tasks to queue
        for (int i = 0; i < totalFrames; i++) {
            taskQueue.add(new RenderFrame(images, i));
            if (i % 100 == 0) {System.out.println("Frames added to queue: " + i);}
        }

        // Execute taskQueue and add output to result object
        try {
            List<Future<Frame>> unsorted = executor.invokeAll(taskQueue);
            executor.shutdown();
            for (Future<Frame> future : unsorted) {
                try {
                    Frame frame = future.get();
                    result.put(frame.getIndex(), frame.getImage());
                } catch (Exception e) {
                    // won't fail
                }
            }
        } catch (InterruptedException e) {
            executor.shutdown();
            e.printStackTrace();
        }

        return result;
    }
}
