package edu.kim.kyo.tasksim;

import edu.kim.kyo.tasksim.schedulealgo.ScheduleAlgo;

/**
 * @author rick
 * Super-class
 */
public class Scheduler {
	private ScheduleAlgo algo;
	
	public Scheduler(ScheduleAlgo sa) {
		algo = sa;
	}
	
	public getNextTask() {
		algo.getNextTask();
	}
		
}
