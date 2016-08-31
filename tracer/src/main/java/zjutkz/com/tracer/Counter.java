package zjutkz.com.tracer;

import java.util.concurrent.TimeUnit;

/**
 * Created by kangzhe on 16/8/29.
 */
public class Counter {
    private long startTime;
    private long endTime;
    private long duration;

    public Counter() {

    }

    private void reset() {
        startTime = 0;
        endTime = 0;
        duration = 0;
    }

    public void start() {
        reset();
        startTime = System.nanoTime();
    }

    public void stop() {
        if (startTime != 0) {
            endTime = System.nanoTime();
            duration = endTime - startTime;
        } else {
            reset();
        }
    }

    public long getTotalTimeMillis() {
        return (duration != 0) ? TimeUnit.NANOSECONDS.toMillis(endTime - startTime) : 0;
    }
}
