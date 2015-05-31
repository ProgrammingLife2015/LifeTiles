package nl.tudelft.lifetiles.core.util;

import java.util.concurrent.TimeUnit;

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
        startTime = System.nanoTime();
    }

    /**
     * Stop the timer.
     */
    public void stop() {
        if (stopTime != null) {
            throw new IllegalStateException("Timer already stopped.");
        }
        stopTime = System.nanoTime();
    }

    /**
     * Get the elapsed time.
     *
     * @return the elapsed time in nanoseconds.
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
        Logging.info(timee + " took " + formatNanos(elapsed));
    }

    /**
     * Format number of nanoseconds to a more readable h:m:s.ms format.
     *
     * @param nanos
     *            the nanoseconds
     * @return a formatted string
     */
    private static String formatNanos(final long nanos) {
        final long hours = TimeUnit.NANOSECONDS.toHours(nanos);
        final long minutes = TimeUnit.NANOSECONDS.toMinutes(nanos
                - TimeUnit.HOURS.toNanos(hours));
        final long seconds = TimeUnit.NANOSECONDS.toSeconds(nanos
                - TimeUnit.HOURS.toNanos(hours)
                - TimeUnit.MINUTES.toNanos(minutes));
        final long millis = TimeUnit.NANOSECONDS.toMillis(nanos
                - TimeUnit.HOURS.toNanos(hours)
                - TimeUnit.MINUTES.toNanos(minutes)
                - TimeUnit.SECONDS.toNanos(seconds));
        return String.format("%02d:%02d:%02d.%03d", hours, minutes, seconds,
                millis);
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
