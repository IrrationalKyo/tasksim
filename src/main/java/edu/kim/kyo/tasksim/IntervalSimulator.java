package edu.kim.kyo.tasksim;

import edu.kim.kyo.tasksim.schedulealgo.RateMonotonic;
import edu.kim.kyo.tasksim.schedulealgo.ScheduleAlgo;

import java.util.LinkedList;

/**
 * @author rick
 * Super-class
 */
public class IntervalSimulator {

	private ScheduleAlgo algo;
	// simLength := number of intervals being recorded
	private int simLength;
	private LinkedList<Integer> trace;

	public IntervalSimulator(ScheduleAlgo sa, int simulationLength) {
		algo = sa;
		simLength = simulationLength;
		trace = new LinkedList<Integer>();
	}

	private int stateChanged(int currentTask, int prevTask){
	    int currentState = currentTask > 0 ? 1 : 0;
	    int prevState =  prevTask > 0 ? 1 : 0;
	    if(currentState - prevState == 0)
	        return 0;
	    if(currentState > prevState)
	        return 1;
        return -1;
    }
	
	/**
	 * Outputs the task that ran on the timestep
	 * @throws TaskDoesNotExistException 
	 * @throws TaskOverloadException 
	 * */
	public void run() throws TaskOverloadException, TaskDoesNotExistException {
		int recordedInterval = 0;
		int intervalDuration = 1;
		int prevTask = algo.incrementTime();
	    while(recordedInterval < simLength){
	        int currentTask = algo.incrementTime();
	        // System went from busy to rest
	        if (stateChanged(currentTask, prevTask) < 0){
                trace.add(intervalDuration);
                intervalDuration = 1;
                recordedInterval++;
            } else if (stateChanged(currentTask, prevTask) > 0){
	            trace.add(intervalDuration * -1);
	            intervalDuration = 1;
                recordedInterval++;
            }
            intervalDuration++;
	        prevTask = currentTask;
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
