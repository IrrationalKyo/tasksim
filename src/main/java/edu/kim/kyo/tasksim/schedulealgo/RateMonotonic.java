package edu.kim.kyo.tasksim.schedulealgo;

import java.util.Arrays;

import edu.kim.kyo.tasksim.Task;
import edu.kim.kyo.tasksim.TaskDoesNotExistException;
import edu.kim.kyo.tasksim.TaskOverloadException;
import edu.kim.kyo.tasksim.TaskSet;

public class RateMonotonic extends ScheduleAlgo{

	private int time;
	private TaskSet set;
	
	public RateMonotonic(TaskSet ts) {
		time = 0;
		set = ts;
	}
	
	/**
	 * returns id of the task that is going to be picked
	 * */
	@Override
	public Task pickTask() {
		
		// TODO Auto-generated method stub
		Task [] tasks = set.getSet();
		// sort from high priority  to low priority (larger the value, higher the priority)
		Arrays.sort(tasks, Task.priorityComparator);
		
		for(int i = 0; i<tasks.length; i++) {
			Task t = tasks[i];
			if(t.getWorkRemaining() > 0) {
				return t;
			}
		}
		
		return null;
	}
	
	/**
	 * runs a single timestep of the algorithm and outputs the task that ran.
	 * */
	@Override
	public int incrementTime() throws TaskOverloadException, TaskDoesNotExistException {	
		// TODO Auto-generated method stub
		for(int i = 0; i < set.getCount(); i++) {
			Task t = set.getTaskAtIndex(i);
			if(t.getOffset() == this.time) {
				if(this.time == 1) {
					System.out.println("TIME IS ZERO");
				}
				set.activateTask(t.getId());
			}
		}
		
		int id = 0;
		this.time+=1;
		Task t = pickTask();
		if(t != null) {
			id = t.getId();
			set.execute(id);
		}
		set.decreaseDeadline();
		
		return id;
	}

	@Override
	public boolean isSchedulable() {
		// TODO Auto-generated method stub
		System.out.println("NOT IMPLEMENTED YET");
		
		
		return true;
	}

	@Override
	public void reset() {
		// TODO Auto-generated method stub
		System.out.println("NOT IMPLEMENTED YET");
	}
	
	public static void main(String [] args) {
		System.out.println("rebuild");
	}

	
}
