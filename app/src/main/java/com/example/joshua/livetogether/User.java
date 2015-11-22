package com.example.joshua.livetogether;

/**
 * Class to store data associated with the registration
 * of a new user
 */
 public class User
 {
 	private String uid;
 	private int confirmation;

 	public User (String idNum, int twilioConfirm)
 	{
 		uid = idNum;
 		confirmation = twilioConfirm;
 	}

 	public String getUID ()
 	{
		return uid; 	
 	}

 	public int getConfirm ()
 	{
 		return confirmation;
 	}
 }
