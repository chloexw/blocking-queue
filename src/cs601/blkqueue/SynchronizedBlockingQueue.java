package cs601.blkqueue;

public class SynchronizedBlockingQueue<T> implements MessageQueue<T> {



    public SynchronizedBlockingQueue(int size) {
	}

	@Override
	public synchronized void put(T o) throws InterruptedException { // just put synchronized on the method
	}

	@Override
	public T take() throws InterruptedException {
	}
}
