package edu.kim.kyo.tasksim.schedulealgo;

import edu.kim.kyo.tasksim.Task;
import edu.kim.kyo.tasksim.TaskDoesNotExistException;
import edu.kim.kyo.tasksim.TaskOverloadException;
import edu.kim.kyo.tasksim.TaskSet;

/**
 * @author Kyo
 * An abstract class. The subclasses must keep the internal state of the algorithm (e.g. current time, amount of 
 * execution time left per task).
 */
public abstract class ScheduleAlgo {
	private int time;
	private TaskSet set;
	
	abstract public Task pickTask();
	abstract public int incrementTime() throws TaskOverloadException, TaskDoesNotExistException;
	abstract public boolean isSchedulable();
	abstract public void reset();
	
	public double totalUtilFactor() {
		double output = 0.0;
		for(int i = 0; i < set.getCount(); i++) {
			Task t = set.getTaskAtIndex(i);
			output += t.getExecutionTime() / t.getPeriod();
		}
		return output;
	}
	
	public double taskUtilFactorByIndex(int index) {
		Task t = set.getTaskAtIndex(index);
		return (double) t.getExecutionTime() / t.getPeriod();
	}
	
	public double taskUtilFactorById(int id) throws TaskDoesNotExistException {
		Task t = set.getTaskWithId(id);
		return (double) t.getExecutionTime() / t.getPeriod();
	}
}
