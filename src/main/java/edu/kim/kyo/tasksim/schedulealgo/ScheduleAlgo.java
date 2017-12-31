package edu.kim.kyo.tasksim.schedulealgo;

import edu.kim.kyo.tasksim.Task;
import edu.kim.kyo.tasksim.TaskDoesNotExistException;
import edu.kim.kyo.tasksim.TaskOverloadException;

/**
 * @author Kyo
 * An abstract class. The subclasses must keep the internal state of the algorithm (e.g. current time, amount of 
 * execution time left per task).
 */
public abstract class ScheduleAlgo {
	
	abstract public Task pickTask();
	abstract public int incrementTime() throws TaskOverloadException, TaskDoesNotExistException;
	abstract public boolean isSchedulable();
	abstract public void reset();
}
