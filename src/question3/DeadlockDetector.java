package question3;

import java.lang.management.ManagementFactory;
import java.lang.management.ThreadInfo;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;

/**
 * Class detects the deadlock and prints corresponding info to the console
 * @author Natalja Olefire
 *
 */
public class DeadlockDetector {

	private final long period;
	private final TimeUnit unit;
	
	// deadlock checking thread, performs the actual check for the deadlock
	// and print out deadlock-related info, if found
	final Runnable deadlockCheck = new Runnable() {
		@Override
		public void run() {
			long[] deadlockedThreadIds = ManagementFactory.getThreadMXBean().findDeadlockedThreads();
			if (deadlockedThreadIds != null) {
				handleDeadlock(ManagementFactory.getThreadMXBean().getThreadInfo(deadlockedThreadIds));
			}
		}
	};

	public DeadlockDetector(final long period, final TimeUnit unit) {
		this.period = period;
		this.unit = unit;
	}

	// schedule checking thread for execution
	void start() {
		Executors.newScheduledThreadPool(1, new DaemonThreadFactory()).scheduleAtFixedRate(this.deadlockCheck, this.period,
				this.period, this.unit);
	}

	// Just print all the info to console
	void handleDeadlock(final ThreadInfo[] deadlockedThreads) {
		if (deadlockedThreads == null)
			return;
		System.err.println("Deadlock detected!");
		for (ThreadInfo threadInfo : deadlockedThreads) {
			if (threadInfo == null)
				continue;
			for (Thread thread : Thread.getAllStackTraces().keySet()) {
				if (thread.getId() != threadInfo.getThreadId())
					continue;
				System.err.println(threadInfo.toString().trim());
				for (StackTraceElement ste : thread.getStackTrace()) {
					System.err.println("\t" + ste.toString().trim());
				}
			}
		}
	}

    // We need this class only for making DeadlockDetector thread daemon
	class DaemonThreadFactory implements ThreadFactory {
		public Thread newThread(Runnable r) {
			Thread thread = new Thread(r);
			thread.setDaemon(true);
			return thread;
		}
	
	}
}