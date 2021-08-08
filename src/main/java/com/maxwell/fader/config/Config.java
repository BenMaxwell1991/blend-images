package com.maxwell.fader.config;

import java.util.concurrent.TimeUnit;

import static com.xuggle.xuggler.Global.DEFAULT_TIME_UNIT;
import static java.lang.Math.round;

public class Config {

    //  number of threads
    public static final int THREADS =  Runtime.getRuntime().availableProcessors();

    public static int fps = 30;
    public static int durationSeconds = 20;
    public static final int totalFrames = durationSeconds * fps;


    public static long nextFrameTime = 0;
    public static final int vsIndex = 0;
    public static final int vsID = 0;
    public static final long frameRate = DEFAULT_TIME_UNIT.convert((int) round(1000.0 / fps), TimeUnit.MILLISECONDS);

}
