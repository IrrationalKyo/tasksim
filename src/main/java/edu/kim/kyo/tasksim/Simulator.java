package edu.kim.kyo.tasksim;

import java.util.LinkedList;

import edu.kim.kyo.tasksim.schedulealgo.RateMonotonic;
import edu.kim.kyo.tasksim.schedulealgo.ScheduleAlgo;

/**
 * @author rick
 * Super-class
 */
public class Simulator {
	
	private ScheduleAlgo algo;
	private int simLength;
	private LinkedList<Integer> trace;
	
	public Simulator(ScheduleAlgo sa, int simulationLength) {
		algo = sa;
		simLength = simulationLength;
		trace = new LinkedList<Integer>();
	}
	
	/**
	 * Outputs the task that ran on the timestep
	 * @throws TaskDoesNotExistException 
	 * @throws TaskOverloadException 
	 * */
	public void run() throws TaskOverloadException, TaskDoesNotExistException {
		for(int time = 0; time < simLength; time++) {
			trace.add(algo.incrementTime());
		}
	}
	
	public LinkedList<Integer> getTrace(){
		return trace;
	}
	
	public static void main(String [] args) {
		/**
		 * declare task
		 * */
		
	}
}
