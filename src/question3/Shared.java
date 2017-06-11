package question3;

import java.lang.management.ManagementFactory;
import java.util.concurrent.TimeUnit;

public class Shared {
    private Object sync = new Object();
	private final String name;
    
    public Shared(String name) {
        this.name = name;
    }

    public void methodA(Shared other) throws InterruptedException {
    	synchronized(this.sync) {
    		System.out.println(Thread.currentThread().getName() + ": acquired lock for methodA, from object: " + this);
    		Thread.sleep(1000);
    	}
    }

    public void methodB(Shared other) throws InterruptedException {
    	synchronized(this.sync) {
    		System.out.println(Thread.currentThread().getName() + ": acquired lock for methodB, from object: " + this);
            other.methodA(this);
    	}
    }

    public static void main(String[] args) {

    	System.out.println("My PID most probably is: " + ManagementFactory.getRuntimeMXBean().getName() + ", you can try to use it jConsole (use numeric part)");
    	
    	//deadlock detector, scheduled to run every 5 seconds
    	DeadlockDetector deadlockDetector = new DeadlockDetector(5, TimeUnit.SECONDS);
    	deadlockDetector.start();

        final Shared shared1 = new Shared("Shared1");
        final Shared shared2 = new Shared("Shared2");
        Thread th1 = new Thread(new Runnable() {
            public void run() { 
            	try {
					shared1.methodB(shared2); // from thread1
				} catch (InterruptedException e) {
					e.printStackTrace();
				} 
            }
        });
        Thread th2 = new Thread(new Runnable() {
            public void run() { 
            	try {
					shared2.methodB(shared1); // from thread2
				} catch (InterruptedException e) {
					e.printStackTrace();
				} 
            }
        });
        th1.start();
        th2.start();
    }

    @Override
	public String toString() {
		return "Shared [name=" + name + ", sync=" + sync + "]";
	}
}
