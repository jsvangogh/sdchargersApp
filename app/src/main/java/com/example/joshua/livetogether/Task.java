package com.example.joshua.livetogether;

/**
 * Class to store data associated with a task
 * including task asignee and description
 */
 public class Task
 {
 	private String assignee; // name of person assigned to task
 	private String description; // task description

 	public Task (String assign, String describe)
 	{
 		assignee = assign;
 		description = describe;
 	}

 	public String getAssignee ()
 	{
		return assignee; 	
 	}

 	public String getDescription ()
 	{
 		return description;
 	}
 }
