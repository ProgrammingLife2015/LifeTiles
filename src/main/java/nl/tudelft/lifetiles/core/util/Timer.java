package nl.tudelft.lifetiles.core.util;

import java.util.Calendar;

/**
 * A timer utility class.
 *
 * @author Joren Hammudoglu
 *
 */
public final class Timer {

    /**
     * The start time.
     */
    private Long startTime;

    /**
     * The stop time.
     */
    private Long stopTime;

    /**
     * Create a new timer.
     */
    private Timer() {
        // noop
    }

    /**
     * Start the timer.
     */
    public void start() {
        if (startTime != null) {
            throw new IllegalStateException("Timer already started.");
        }
        startTime = Calendar.getInstance().getTimeInMillis();
    }

    /**
     * Stop the timer.
     */
    public void stop() {
        if (stopTime != null) {
            throw new IllegalStateException("Timer already stopped.");
        }
        stopTime = Calendar.getInstance().getTimeInMillis();
    }

    /**
     * Get the elapsed time.
     *
     * @return the elapsed time in milleseconds.
     */
    public long getElapsed() {
        return stopTime - startTime;
    }

    /**
     * Stop the timer and log the elapsed time.
     *
     * @param timee
     *            what got timed
     */
    public void stopAndLog(final String timee) {
        stop();
        long elapsed = getElapsed();
        Logging.info(timee + " took " + elapsed + " ms");
    }

    /**
     * Get a running timer.
     *
     * @return a running timer.
     */
    public static Timer getAndStart() {
        final Timer timer = new Timer();
        timer.start();
        return timer;
    }

}
