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
	private final HashMap<String, Long> histogram;
    protected volatile boolean isTerminated = false;//once it is updated, other thread needs to see it immediately

	public ThreadObserver(Thread threadToMonitor, long periodInNanoSeconds) {
        threadMonitored = threadToMonitor;
        wakeUpInterval = (int)periodInNanoSeconds;
        histogram = new HashMap<String, Long>();
	}

	@Override
	public void run() {
		while (!isTerminated) {
			//record
			updateMethodHistogram();
			try {
				Thread.sleep(0, wakeUpInterval);
			} catch (InterruptedException e) {
				System.out.println("Interrupted.");
			}
		}
    }

    private void updateMethodHistogram() {
    	StackTraceElement[] array = threadMonitored.getStackTrace();
    	if (array.length == 0) {
    		return;
    	}
    	String method =  array[0].getMethodName();
    	if (histogram.containsKey(method)) {
    		histogram.put(method, 1L + histogram.get(method));
    	} else {
    		histogram.put(method, 1L);
    	}
    	//update state and events
    	numEvents ++;
    	switch (threadMonitored.getState()) {
			case BLOCKED:
				blocked++;
				break;
			case WAITING:
				waiting++;
				break;
			case TIMED_WAITING:
				sleeping++;
				break;
			default:
				break;
    	}
    }

    public Map<String, Long> getMethodSamples() { 
    	return histogram; 
    }

	public void terminate() {
		isTerminated = true;
	}
	
			 
	public String toString() {
		return String.format("(%d blocked + %d waiting + %d sleeping) / %d samples = %.2f%% wasted", 
				blocked,
				waiting,
				sleeping,
				numEvents,
				100.0*(blocked+waiting+sleeping)/numEvents);		
	}
}
