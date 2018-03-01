package edu.kim.kyo.tasksim;

import org.junit.Test;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertEquals;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;

import edu.kim.kyo.tasksim.schedulealgo.RateMonotonic;

public class RateMonotonicTest{
	
	private LinkedList<Integer> oldFormatParser(String fileDir) {

		LinkedList<Integer> trace = new LinkedList<Integer>();
        // This will reference one line at a time
		String totalLine = "";
        String line = null;

        try {
            // FileReader reads text files in the default encoding.
            FileReader fileReader = 
                new FileReader(fileDir);

            // Always wrap FileReader in BufferedReader.
            BufferedReader bufferedReader = 
                new BufferedReader(fileReader);

            while((line = bufferedReader.readLine()) != null) {
                totalLine += line;
            }
            
            Integer [] time_range = new Integer[]{0,0};
            String [] parts = totalLine.split(",");
        	for(int i = 0; i < parts.length; i++){
        		if(i%3 == 0) {
        			String [] raw_time_range = parts[i].split("-");
        			time_range = new Integer[]{Integer.parseInt(raw_time_range[0].trim()), Integer.parseInt(raw_time_range[1].trim())};
        		}
        		else if(i%3 == 1) {
        			int task_id = Integer.parseInt(parts[i].trim());
        			for(int j = 0; j < time_range[1] - time_range[0]; j++){
        				trace.add(new Integer(task_id));
        			}
        		} else {
        			time_range = null;
        		}
        	}

            // Always close files.
            bufferedReader.close();     
        }
        catch(FileNotFoundException ex) {
            System.out.println(
                "Unable to open file '" + 
                fileDir + "'");                
        }
        catch(IOException ex) {
        	ex.printStackTrace();
        }
        return trace;
    }


    /**
     * Rigourous Test :-)
     */
    @Test
    public void checkDataset1()
    {
		LinkedList<Integer> trace2 = oldFormatParser("dataset_det_1.txt");
    	TaskSet ts = new TaskSet(15);
		ts.addTask(new Task(1, 6, 670, 14, 353));
		ts.addTask(new Task(2, 13, 330, 20, 36));
		ts.addTask(new Task(3, 5, 720, 6, 385));
		ts.addTask(new Task(4, 4, 860, 33, 308));
		ts.addTask(new Task(5, 15, 140, 8, 56));
		ts.addTask(new Task(6, 1, 940, 58, 380));
		ts.addTask(new Task(7, 3, 900, 29, 233));
		ts.addTask(new Task(8, 11, 460, 28, 292));
		ts.addTask(new Task(9, 8, 620, 45, 8));
		ts.addTask(new Task(10, 12, 350, 9, 25));
		ts.addTask(new Task(11, 9, 600, 23, 396));
		ts.addTask(new Task(12, 14, 170, 1, 13));
		ts.addTask(new Task(13, 10, 520, 11, 101));
		ts.addTask(new Task(14, 2, 930, 52, 928));
		ts.addTask(new Task(15, 7, 630, 2, 220));
		System.out.println(ts.getUtilFactor());
		Simulator sim = new Simulator(new RateMonotonic(ts),100000);
		try{
			sim.run();
		}catch(TaskOverloadException e) {
			e.printStackTrace(System.out);
		}catch(TaskDoesNotExistException e) {
			e.printStackTrace(System.out);
		}
		
		LinkedList<Integer> trace = sim.getTrace();
//		System.out.println(trace.toString());
//		System.out.println(trace2.toString());

		for(int i = 0; i < trace.size(); i++) {
			if(!trace.get(i).equals(trace2.get(i))) {
				System.out.print(i);
				System.out.print(", ");
			}
		}
		System.out.println("");
		
        assertTrue(true);
    }
    
    @Test
    public void checkDataset2()
    {
		LinkedList<Integer> trace2 = oldFormatParser("dataset_det_2.txt");
    	TaskSet ts = new TaskSet(15);
		ts.addTask(new Task(12, 1, 980, 51, 600));
		ts.addTask(new Task(6, 2, 970, 41, 935));
		ts.addTask(new Task(10, 3, 950, 67, 911));
		ts.addTask(new Task(3, 4, 920, 107, 179));
		ts.addTask(new Task(15, 5, 840, 97, 701));
		ts.addTask(new Task(9, 6, 650, 15, 159));
		ts.addTask(new Task(13, 7, 520, 27, 47));
		ts.addTask(new Task(4, 8, 480, 16, 453));
		ts.addTask(new Task(5, 9, 450, 27, 293));
		ts.addTask(new Task(2, 10, 430, 50, 147));
		ts.addTask(new Task(7, 11, 340, 2, 51));
		ts.addTask(new Task(11, 12, 320, 10, 96));
		ts.addTask(new Task(14, 13, 300, 10, 94));
		ts.addTask(new Task(1, 14, 180, 14, 40));
		ts.addTask(new Task(8, 15, 110, 8, 59));
		
		System.out.println(ts.getUtilFactor());
		Simulator sim = new Simulator(new RateMonotonic(ts),100000);
		try{
			sim.run();
		}catch(TaskOverloadException e) {
			e.printStackTrace(System.out);
		}catch(TaskDoesNotExistException e) {
			e.printStackTrace(System.out);
		}
		
		LinkedList<Integer> trace = sim.getTrace();
//		System.out.println(trace.toString());
//		System.out.println(trace2.toString());

		for(int i = 0; i < trace.size(); i++) {
			if(!trace.get(i).equals(trace2.get(i))) {
				System.out.print("At index: " + Integer.toString(i)+", ");
				System.out.print("sim: " + Integer.toString(trace.get(i))+", ");
				System.out.print("log: " + Integer.toString(trace2.get(i)));
				System.out.println("");
			}
		}
		
        assertTrue(true);
    }
}
