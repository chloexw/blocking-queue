package cs601.blkqueue;

import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.locks.LockSupport;

public class RingBuffer<T> implements MessageQueue<T> {
	private final AtomicLong w = new AtomicLong(-1); //just wrote address
	private final AtomicLong r = new AtomicLong(0); // will read address
	private final int capacity;
	private final Object[] array;
	
	public RingBuffer(int size) {
		if (!isPowerOfTwo(size)) {
			throw new IllegalArgumentException();
		}
		capacity = size;
		array = new Object[capacity];
	}

	public void put(T o) {
		//when the ring buffer is full, we need to wait
		//when w -r = capacity-1, the ring buffer is full
		long writeIndex = w.get();
		waitForFreeWriteSpot(writeIndex);
		array[(int)(writeIndex+1)&(capacity-1)] = o;
		w.incrementAndGet();
	}
	
	private void waitForFreeWriteSpot(final long writeIndex) {
		while (writeIndex - r.get() == capacity - 1) {
			LockSupport.parkNanos(1L);
		}
	}

	public T take() {
		// when the ring is empty, we wait
		//when r-w = 1, the ring is empty
		int readIndex = (int)r.get();
		waitForFreeReadAddress(readIndex);
		T res = (T)array[(int)(readIndex & (capacity-1))];
		r.incrementAndGet();
		return res;
	}
	
	private void waitForFreeReadAddress(final long readIndex) {
		while (readIndex - w.get() == 1) {
			LockSupport.parkNanos(1L);
		}
	}
	
	 // http://graphics.stanford.edu/~seander/bithacks.html#CountBitsSetParallel
	private static boolean isPowerOfTwo(int v) {
	    if (v<0) return false;
	    v = v - ((v >> 1) & 0x55555555);                    // reuse input as temporary
	    v = (v & 0x33333333) + ((v >> 2) & 0x33333333);     // temp
	    int onbits = ((v + (v >> 4) & 0xF0F0F0F) * 0x1010101) >> 24; // count
	    // if number of on bits is 1, it's power of two, except for sign bit
	    return onbits==1;
	}

}