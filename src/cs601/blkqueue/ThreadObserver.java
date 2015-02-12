package cs601.blkqueue;

import java.util.HashMap;
import java.util.Map;

/** A runnable class that attaches to another thread and wakes up
 *  at regular intervals to determine that thread's state. The goal
 *  is to figure out how much time that thread is blocked, waiting,
 *  or sleeping.
 */
class ThreadObserver implements Runnable {
    private final int wakeUpInterval;
    private final Thread threadMonitored;
    protected int numEvents = 0;
	protected int blocked = 0;
	protected int waiting = 0;
	protected int sleeping = 0;
    protected volatile boolean isTerminated = false;

	public ThreadObserver(Thread threadToMonitor, long periodInNanoSeconds) {
        threadMonitored = threadToMonitor;
        wakeUpInterval = (int)periodInNanoSeconds;
	}

	@Override
	public void run() {
    }

    private void updateMethodHistogram() {
    }

    public Map<String, Long> getMethodSamples() { return histogram; }

	public void terminate() {
}
