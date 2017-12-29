package edu.kim.kyo.tasksim;

/**
 * @author rick
 * Task assumes static priority, period, executionTime, offset
 * A struct class
 *
 */
public class Task {

	private final int offset;
	private final int maxExecutionTime;
	private final int minExecutionTime;
	private final int priority;
	private final int period;
	private final int id;
	
	public Task(int id, int priority, int period, int executionTime, int offset) {
		this(id,priority,period,executionTime,executionTime,offset);
	}
	
	public Task(int id, int priority, int period, int maxExecutionTime, int minExecutionTime, int offset) {
		this.id = id;
		this.priority = priority;
		this.period = period;
		this.maxExecutionTime = maxExecutionTime;
		this.minExecutionTime = minExecutionTime;
		this.offset = offset;
	}
	
	
	public int getOffset() {
		return offset;
	}

	public int getExecutionTime() {
		return maxExecutionTime;
	}

	public int getPriority() {
		return priority;
	}

	public int getPeriod() {
		return period;
	}


	public int getMaxExecutionTime() {
		return maxExecutionTime;
	}


	public int getMinExecutionTime() {
		return minExecutionTime;
	}


	public int getId() {
		return id;
	}
}
