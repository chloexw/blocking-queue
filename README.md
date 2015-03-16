# blocking-queue
blocking-queue project for COEN 283

Message passing via blocking queues

1. Message buffer using synchronization
Use a queue to implement the blocking queue, and do spin wait while the queue is empty/full.

2. Lockless message buffer
Create a ring buffer; use LockSupport during spin wait, and use AtomicLong for read and write address.

3. Message buffer using ArrayBlockingQueue


4. Thread observer to minitor the thread producer and consumer's efficiency
