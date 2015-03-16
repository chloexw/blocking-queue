package cs601.blkqueue;

import java.util.LinkedList;
import java.util.Queue;

public class SynchronizedBlockingQueue<T> implements MessageQueue<T> {
    private int count;
    private int capacity;
    private Queue<T> queue;

    public SynchronizedBlockingQueue(int size) {
    	capacity = size;
    	count = 0;
    	queue = new LinkedList<T>();
	}

	@Override
	public synchronized void put(T o) throws InterruptedException { 
		while (count == capacity) {
			wait();
		}
		queue.add(o);
		count ++;
		notifyAll();
	}

	@Override
	public synchronized T take() throws InterruptedException {
		while (count == 0) {
			wait();
		}
		T res = queue.remove();
		count --;
		notifyAll();
		return res;
	}
}
