package edu.kim.kyo.tasksim;

import edu.kim.kyo.tasksim.schedulealgo.ScheduleAlgo;

/**
 * @author rick
 * Super-class
 */
public class Simulator {
	private ScheduleAlgo algo;
	private int time;
	
	public Simulator(ScheduleAlgo sa) {
		algo = sa;
	}
	
	/**
	 * Outputs the task that ran on the timestep
	 * */
	public void increaseTimeStep() {
		algo.getNextTask();
	}
		
}
