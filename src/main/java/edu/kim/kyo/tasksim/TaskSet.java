package edu.kim.kyo.tasksim;

import java.util.SortedSet;
import java.util.TreeSet;

/**
 * @author rick
 * Assumes static task set
 */
public class TaskSet {
	private SortedSet<Task> set;
	public TaskSet() {
		set = new TreeSet<Task>();
	}
}
