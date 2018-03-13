package edu.kim.kyo.tasksim;

import edu.kim.kyo.tasksim.schedulealgo.RateMonotonic;

import java.io.*;
import java.util.LinkedList;

/**
 * Hello world!
 *
 */
public class IntervalApp {
	public static File[] finder(String dirName) {
		File dir = new File(dirName);

		return dir.listFiles(new FilenameFilter() {
			public boolean accept(File dir, String filename) {
				return filename.endsWith("_meta.txt");
			}
		});

	}

	public static File[] traceFinder(String dirName) {
		File dir = new File(dirName);

		return dir.listFiles(new FilenameFilter() {
			public boolean accept(File dir, String filename) {
				return filename.endsWith(".data");
			}
		});

	}

	private LinkedList<Integer> oldFormatParser(String fileDir) {

		LinkedList<Integer> trace = new LinkedList<Integer>();
		// This will reference one line at a time
		String totalLine = "";
		String line = null;

		try {
			// FileReader reads text files in the default encoding.
			FileReader fileReader = new FileReader(fileDir);

			// Always wrap FileReader in BufferedReader.
			BufferedReader bufferedReader = new BufferedReader(fileReader);

			while ((line = bufferedReader.readLine()) != null) {
				totalLine += line;
			}

			Integer[] time_range = new Integer[] { 0, 0 };
			String[] parts = totalLine.split(",");
			for (int i = 0; i < parts.length; i++) {
				if (i % 3 == 0) {
					String[] raw_time_range = parts[i].split("-");
					time_range = new Integer[] { Integer.parseInt(raw_time_range[0].trim()),
							Integer.parseInt(raw_time_range[1].trim()) };
				} else if (i % 3 == 1) {
					int task_id = Integer.parseInt(parts[i].trim());
					for (int j = 0; j < time_range[1] - time_range[0]; j++) {
						trace.add(new Integer(task_id));
					}
				} else {
					time_range = null;
				}
			}

			// Always close files.
			bufferedReader.close();
		} catch (FileNotFoundException ex) {
			System.out.println("Unable to open file '" + fileDir + "'");
		} catch (IOException ex) {
			ex.printStackTrace();
		}
		return trace;
	}

	private static TaskSet oldFormatMetaParser(String fileDir) throws TaskDoesNotExistException {

		LinkedList<Task> set = new LinkedList<Task>();
		// This will reference one line at a time
		String line = null;

		try {
			// FileReader reads text files in the default encoding.
			FileReader fileReader = new FileReader(fileDir);

			// Always wrap FileReader in BufferedReader.
			BufferedReader bufferedReader = new BufferedReader(fileReader);

			while ((line = bufferedReader.readLine()) != null) {
				String[] info = line.trim().split("-");
				int id = -1;
				int period = -1;
				int executionTime = -1;
				int priority = -1;
				int offset = -1;
				if (info[0].toLowerCase().equals("task")) {
					String[] idInfo = info[1].split(":");
					id = Integer.parseInt(idInfo[0]);

					String[] otherInfo = idInfo[1].split(",");
					if (otherInfo.length != 4) {
						throw new TaskDoesNotExistException("One of the parameter is missing");
					}
					for (int i = 0; i < otherInfo.length; i++) {
						String[] param = otherInfo[i].split("=");
						switch (param[0].trim()) {
						case "p":
							period = Integer.parseInt(param[1].trim());
							break;
						case "c":
							executionTime = Integer.parseInt(param[1].trim());
							break;
						case "pri":
							priority = Integer.parseInt(param[1].trim());
							id = priority;
							break;
						case "offset":
							offset = Integer.parseInt(param[1].trim());
							break;
						default:
							throw new TaskDoesNotExistException("Invalid parameter");
						}
					}
					set.add(new Task(id, priority, period, executionTime, offset));
				}
			}

			// Always close files.
			bufferedReader.close();
		} catch (FileNotFoundException ex) {
			System.out.println("Unable to open file '" + fileDir + "'");
		} catch (IOException ex) {
			ex.printStackTrace();
		}
		return new TaskSet(set.toArray(new Task[set.size()]));
	}

	public static void saveTrace(String filename, LinkedList<Integer> trace) throws FileNotFoundException {
		try (PrintWriter out = new PrintWriter(filename)) {
			String text = trace.toString().replace("[", "") // remove the right bracket
					.replace("]", "") // remove the left bracket
					.trim();
			out.println(text);
		}
	}

	/**
	 * Look at current directory and get traces of all files that ends with
	 * _meta.txt
	 * 
	 * @throws TaskDoesNotExistException
	 * @throws FileNotFoundException
	 * @throws TaskOverloadException
	 */
	public static void outputTraces(String inputFolderDir, String outputFolderDir, int duration)
			throws TaskDoesNotExistException, FileNotFoundException, TaskOverloadException {
		File[] fs = finder(inputFolderDir);
		for (int i = 0; i < fs.length; i++) {
			String filename = fs[i].getName();
			String inputDir = inputFolderDir + "/" + filename;
			String outputDir = outputFolderDir + "/"+filename.replace("_meta.txt", ".data");
			outputTraceSingleFile(duration, inputDir, outputDir);
		}
	}

	public static void outputTraceSingleFile(int duration, String inputFileDir, String outputFileDir)
			throws TaskDoesNotExistException, FileNotFoundException, TaskOverloadException {
		TaskSet ts = oldFormatMetaParser(inputFileDir);
		IntervalSimulator sim = new IntervalSimulator(new RateMonotonic(ts), duration);
		try {
			sim.run();
		} catch (TaskOverloadException e) {
			System.out.println("In " + inputFileDir + ": ");
			System.out.println("\tTrace" + sim.getTrace().toString() + ": ");
			e.printStackTrace(System.out);
		} catch (TaskDoesNotExistException e) {
			System.out.println("In " + inputFileDir + ": ");
			e.printStackTrace(System.out);
			System.out.println(ts.toString());
		}

		LinkedList<Integer> trace = sim.getTrace();

		saveTrace(outputFileDir, trace);
	}

	public static boolean traceDiff(String trace1, String trace2) throws FileNotFoundException, IOException {
		try (BufferedReader br1 = new BufferedReader(new FileReader(trace1));
				BufferedReader br2 = new BufferedReader(new FileReader(trace2))) {

			String t1 = br1.readLine();
			String t2 = br2.readLine();
			if (t1.equals(t2)) {
				return false;
			} else {
				return true;
			}
		}
	}

	public static void compareTrace(String dir1, String dir2) {
		LinkedList<String> l = new LinkedList<String>();
		try {
			File[] traceFile = traceFinder(dir1);
			for (int i = 0; i < traceFile.length; i++) {
				String filename = traceFile[i].getName();
				if (traceDiff(dir1 + filename, dir2 + filename)) {
					l.add(filename);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println(l.toString());
	}

	public static void outputUtilVariance()
			throws TaskDoesNotExistException, FileNotFoundException, TaskOverloadException {
		File[] fs = finder("./");
		for (int i = 0; i < fs.length; i++) {
			String filename = fs[i].getName();
			TaskSet ts = oldFormatMetaParser(filename);
			System.out.println(filename + "\t" + ts.getUtilVar());
		}
	}

	public static void main(String[] args) throws TaskDoesNotExistException, TaskOverloadException, FileNotFoundException {
		// try {
		// System.out.println(oldFormatMetaParser("dataset_det_3_meta.txt").getUtilFactor());
		// } catch (TaskDoesNotExistException e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }

		// outputTraces(1000000);
		// outputUtilVariance();
		// TaskSet ts = oldFormatMetaParser("dataset_det_2_meta.txt");
		//double[] l = ts.getPerTaskUtil();
		//System.out.println("dataset_det_2_meta.txt" + "\t");
		//for (int i = 0; i < l.length; i++) {
		//	System.out.println("id:" + i + "=" + l[i]);
		//
		//}
		String inputFolderDir = "";
		String outputFolderDir = "";
		int duration = 2500;
		
		if(!(args.length <= 3 && args.length >= 2)) {
			throw new IllegalArgumentException("Expects 3 argument. args[0] = inputFolderDir. args[1] = outputFolderDir. args[2] = ");
		}
		
		inputFolderDir = args[0];
		outputFolderDir = args[1];
		
		if(args.length == 3)
			duration = Integer.parseInt(args[2]);
		
		File directory = new File(outputFolderDir);
	    if (! directory.exists()){
	        directory.mkdir();
	        // If you require it to make the entire directory path including parents,
	        // use directory.mkdirs(); here instead.
	    }
		
		outputTraces(inputFolderDir, outputFolderDir, duration);

	}
}
