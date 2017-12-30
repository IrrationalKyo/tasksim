package edu.kim.kyo.tasksim.schedulealgo;

import edu.kim.kyo.tasksim.Task;

/**
 * @author Kyo
 * An abstract class. The subclasses must keep the internal state of the algorithm (e.g. current time, amount of 
 * execution time left per task).
 */
public abstract class ScheduleAlgo {
	
	abstract public Task getCurrentTask();
	abstract public void incrementTime();
	abstract public boolean isSchedulable();
	abstract public void reset();
	abstract public int getTime();
}
