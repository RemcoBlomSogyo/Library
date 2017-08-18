package com.model;

public class SuccessMessage {
	
	private boolean commandSucceeded;
	private String message;
	
	public SuccessMessage(boolean commandSucceeded) {
		this.commandSucceeded = commandSucceeded;
		if (commandSucceeded) {
			message = "Book is added to the database.";
		} else {
			message = "Something went wrong. No book was added.";
		}
	}
}
