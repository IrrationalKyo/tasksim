package edu.kim.kyo.tasksim;

import java.util.Comparator;

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
		this.relativeDeadline = offset;
	}
	
	public int doSingleWork() {
		workRemaining--;
		return workRemaining;
	}
	public int stepDeadline() {
		relativeDeadline--;
		return relativeDeadline;
	}
	public void resetWork() {
		workRemaining = executionTime;
	}
	public void resetRelativeDeadline() {
		relativeDeadline = period;
	}
	public void setRelativeDeadline(int d) {
		relativeDeadline = d;
	}
	
	public int priorityDifference(Task t) {
		return t.priority-this.priority;
	}
	
	public static Comparator<Task> priorityComparator = new Comparator<Task>() {

		public int compare(Task t1, Task t2) {
			// ascending order
			return t1.priorityDifference(t2);
		}

	};
	
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
	
	@Override
	public String toString() {
		String output = "";
		output+= "Id:" + Integer.toString(id);
		output+= "\tPri:" + Integer.toString(priority);
		output+= "\tPer:" + Integer.toString(period);
		output+= "\tWorkRem:" + Integer.toString(workRemaining);
		output+= "\tExeTime:" + Integer.toString(executionTime);
		output+= "\tOffset:" + Integer.toString(offset);
		output+= "\n";
		return output;

	}
	
}
