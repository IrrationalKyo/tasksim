package edu.kim.kyo.tasksim;

public class TaskDoesNotExistException extends Exception {
	public TaskDoesNotExistException(String message) {
		super(message);
	}
}
