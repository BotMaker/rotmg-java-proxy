package client;

import java.util.ArrayList;

public class Threader {
	private Object LOCK = new Object();
	private Object FLOCK = new Object();
	private static int MAX_THREADS = 3;
	private boolean running = true;
	private volatile ArrayList<Runnable> work = new ArrayList<Runnable>();
	public int finished;

	public Threader(int j) {
		MAX_THREADS = j;
		for (int i = 0; i < MAX_THREADS; i++)
			new Thr().start();
	}
	
	public Threader() {
		this(MAX_THREADS);
	}

	public void finish() {
		work.add(new Runnable() {
			public void run() {
				running = false;
				synchronized (LOCK) {
					LOCK.notifyAll();
				}
				synchronized (FLOCK) {
					FLOCK.notifyAll();
				}
			}
		});
		synchronized (LOCK) {
			LOCK.notify();
		}
	}

	public void add(Runnable c) {
		work.add(c);
		synchronized (LOCK) {
			LOCK.notify();
		}
	}
	
	public void waitOnFLock() throws InterruptedException {
		synchronized (FLOCK) {
			FLOCK.wait();
		}
	}
	
	public boolean isDone() {
//		System.out.println(finished + " | " + MAX_THREADS);
		return finished == MAX_THREADS;
	}

	private class Thr extends Thread {
		public void run() {
			while (running) {
				try {
					if (work.isEmpty()) {
						synchronized (LOCK) {
							LOCK.wait();
						}
					}
					Runnable _work;
					synchronized (LOCK) {
						if (work.size() == 0)
							continue; // possibly finished
						_work = work.remove(0);
					}
					if (_work == null) {
						System.err.println("dafuq?!");
						continue;
					}
					_work.run();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			finished++;
			synchronized (FLOCK) {
				FLOCK.notifyAll();
			}
		}
	}

}
