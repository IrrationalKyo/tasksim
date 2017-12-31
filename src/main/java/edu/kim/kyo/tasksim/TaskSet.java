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

	public void addTask(Task t) {
		set[count] = t;
		count++;
	}

	public Task[] getSet() {
		return set;
	}
	
	public int getCount() {
		return count;
	}
	
	public Task getTaskAtIndex(int i) {
		return set[i];
	}
	
	public void activateTask(int id) {
		for (int i = 0; i < set.length; i++) {
			Task t = set[i];
			if (t.getId() == id) {
				t.resetWork();
			}
		}
	}

	public void execute(int id) throws TaskDoesNotExistException {
		for (int i = 0; i < count; i++) {
			Task t = set[i];
			if (t.getId() == id) {
				t.doSingleWork();
				return;
			}
		}
		throw new TaskDoesNotExistException("id:" + Integer.toString(id) + " does not exist");
	}

	public void decreaseDeadline() throws TaskOverloadException {
		for (int i = 0; i < set.length; i++) {
			Task t = set[i];
			t.stepDeadline();
			if (t.getRelativeDeadline() <= 0 && t.getWorkRemaining() > 0) {
				throw new TaskOverloadException("id:" + Integer.toString(t.getId()) + " didn't finish within deadline");
			} else if (t.getRelativeDeadline() == 0) {
				t.resetRelativeDeadline();
				t.resetWork();
			}
		}
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
