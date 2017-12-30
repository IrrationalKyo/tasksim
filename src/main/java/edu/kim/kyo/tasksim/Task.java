package edu.kim.kyo.tasksim;

/**
 * @author rick
 * Task assumes static priority, period, executionTime, offset
 * A struct class
 *
 */
public class Task {

	private final int offset;
	private final int executionTime;
	private final int priority;
	private final int period;
	private final int id;
	private int relativeDeadline;
	private int workRemaining;
	
	public Task(int id, int priority, int period, int executionTime, int offset) {
		this.id = id;
		this.priority = priority;
		this.period = period;
		this.executionTime = executionTime;
		this.offset = offset;
	}
	
	public int doSingleWork() {
		workRemaining--;
		return workRemaining;
	}
	public void resetWork() {
		workRemaining = executionTime;
	}
	
	public int getOffset() {
		return offset;
	}

	public int getExecutionTime() {
		return executionTime;
	}

	public int getPriority() {
		return priority;
	}

	public int getPeriod() {
		return period;
	}

	public int getId() {
		return id;
	}
	
	public int getRelativeDeadline() {
		return relativeDeadline;
	}

	public int getWorkRemaining() {
		return workRemaining;
	}
}
