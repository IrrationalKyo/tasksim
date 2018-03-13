package edu.kim.kyo.tasksim;

import edu.kim.kyo.tasksim.TaskDoesNotExistException;
import edu.kim.kyo.tasksim.TaskOverloadException;
import edu.kim.kyo.tasksim.schedulealgo.ScheduleAlgo;

import java.util.LinkedList;

/**
 * @author rick
 * Super-class
 */
public class SequenceSimulator {

	private ScheduleAlgo algo;
	// simLength := number of intervals being recorded
	private int simLength;
	private LinkedList<Integer> trace;

	public SequenceSimulator(ScheduleAlgo sa, int simulationLength) {
		algo = sa;
		simLength = simulationLength;
		trace = new LinkedList<Integer>();
	}

	private int stateChanged(int currentTask, int prevTask){
	    if(currentTask != prevTask) return prevTask;
        return -1;
    }
	
	/**
	 * Outputs the task that ran on the timestep
	 * @throws TaskDoesNotExistException
	 * @throws TaskOverloadException
	 * */
	public void run() throws TaskOverloadException, TaskDoesNotExistException {
		int recordedInterval = 0;
		int prevTask = algo.incrementTime();
	    while(recordedInterval < simLength){
	        int currentTask = algo.incrementTime();
	        int state = stateChanged(currentTask, prevTask);
            // System state changed
            if (state >= 0){
	            trace.add(state);
                recordedInterval++;
            }
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
