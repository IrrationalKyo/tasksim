package edu.kim.kyo.tasksim.schedulealgo;

import edu.kim.kyo.tasksim.Task;
import edu.kim.kyo.tasksim.TaskSet;

public class RateMonotonic extends ScheduleAlgo{

	private int time;
	private TaskSet set;
	
	public RateMonotonic(TaskSet set) {
		this.set = set;
	}
	
	@Override
	public Task getCurrentTask() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void incrementTime() {
		// TODO Auto-generated method stub
		time++;
	}

	@Override
	public boolean isSchedulable() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void reset() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public int getTime() {
		// TODO Auto-generated method stub
		return time;
	}

}
