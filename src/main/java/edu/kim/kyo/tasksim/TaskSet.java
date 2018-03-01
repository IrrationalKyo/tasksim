package edu.kim.kyo.tasksim;

import java.util.SortedSet;
import java.util.TreeSet;
import java.lang.ArrayIndexOutOfBoundsException;

/**
 * @author rick Assumes static task set
 */
public class TaskSet {
	private Task[] set;
	private int count;

	public TaskSet(int size) {
		set = new Task[size];
		count = 0;
	}
	
	public TaskSet(Task[] set) {
		this.set = set;
		count = set.length;
	}

	public void addTask(Task t) {
		set[count] = t;
		this.count+=1;
	}

	public Task[] getSet() {
		return set;
	}
	
	public int getCount() {
		return this.count;
	}
	
	public Task getTaskAtIndex(int i) {
		return set[i];
	}
	
	public Task getTaskWithId(int id) throws TaskDoesNotExistException {
		for (int i = 0; i < set.length; i++) {
			Task t = set[i];
			if (t.getId() == id) {
				return t;
			}
		}
		throw new TaskDoesNotExistException("id:" + Integer.toString(id) + " does not exist");
	}
	
	public void activateTask(int id) throws TaskDoesNotExistException {
		for (int i = 0; i < set.length; i++) {
			Task t = set[i];
			if (t.getId() == id) {
				t.resetWork();
				t.resetRelativeDeadline();
				return;
			}
		}
		throw new TaskDoesNotExistException("id:" + Integer.toString(id) + " does not exist");
	}

	public void execute(int id) throws TaskDoesNotExistException {
		Task t = getTaskWithId(id);
		t.doSingleWork();
	}

	public void decreaseDeadline() throws TaskOverloadException {
		for (int i = 0; i < set.length; i++) {
			Task t = set[i];
			t.stepDeadline();
			if (t.getRelativeDeadline() <= 0 && t.getWorkRemaining() > 0) {
				throw new TaskOverloadException("id:" + Integer.toString(t.getId()) + " didn't finish within deadline" + ". Remaining Work: " + Integer.toString(t.getWorkRemaining()));
			} else if (t.getRelativeDeadline() == 0 && t.getWorkRemaining() <= 0) {
				t.resetRelativeDeadline();
				t.resetWork();
			}
		}
	}
	
	public double getUtilFactor() {
		double totalUtil = 0.0;
		for (int i = 0; i < set.length; i++) {
			Task t = set[i];
			totalUtil += t.getUtilFactor();
		}
		return totalUtil;
	}
	
	public double getUtilVar() {
		double variance = 0.0;
		double average = getUtilFactor()/count;
		for(int i =0 ;i < count; i++) {
			variance += Math.pow((average - getTaskAtIndex(i).getUtilFactor()), 2);
		}
		variance /= 5;
		return variance;
	}
	
	public double [] getPerTaskUtil() {
		double [] perTask = new double[count];
		for(int i = 0; i < count; i++) {
			perTask[i] = set[i].getUtilFactor();
		}
		return perTask;
	}
	
	@Override
	public String toString() {
		String output = "";
		
		for (int i = 0; i < set.length; i++) {
			Task t = set[i];
			output += t.toString();
		}
		
		return output;
	}
}
