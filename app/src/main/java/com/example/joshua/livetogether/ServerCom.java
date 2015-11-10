package com.example.joshua.livetogether;

import java.io.BufferedReader;
import java.io.*; 
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import org.json.*;


public class ServerCom
{
	// Define string constant
	public static final String HOST = "http://sdchargers.herokuapp.com/";


	// Allow user to sign in and get their User ID
	public static String signIn (String username, String password) {
		HttpURLConnection connection = null;
		username = "username=" + username;
		password = "password=" + password;

		String args = username + "&" + password;

		try {
			//Create connection
			URL url = new URL("http://sdchargers.herokuapp.com/login/");
			connection = (HttpURLConnection)url.openConnection();
			connection.setRequestMethod("POST");

			connection.setRequestProperty("Content-Type",
					"application/x-www-form-urlencoded");

	    	connection.setRequestProperty("Content-Length", 
	        Integer.toString(args.getBytes().length));
	    	connection.setRequestProperty("Content-Language", "en-US");  

	    	connection.setUseCaches(false);
	    	connection.setDoOutput(true);

	    	//Send request
	    	DataOutputStream wr = new DataOutputStream (
	        connection.getOutputStream());
	    	wr.writeBytes(args);
	    	wr.close();

			int responseCode = connection.getResponseCode();

			//Get Response
			BufferedReader in = new BufferedReader(
					new InputStreamReader(connection.getInputStream()));
			String inputLine;
			StringBuffer response = new StringBuffer();

			while ((inputLine = in.readLine()) != null) {
				response.append(inputLine);
			}
			in.close();

			// ---------------------
			// PROCESS JSON RESPONSE
			JSONObject respJson = new JSONObject(response.toString());
			JSONObject idObj = respJson.getJSONObject("_id");
			String uid = idObj.getString("$oid");
			return uid;


		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			if(connection != null) {
				connection.disconnect();
			}
		}
	}


	// Register a new user
	public static String register (String username, String password) {
		HttpURLConnection connection = null;

		// Prepare user registration info
		username = "username=" + username;
		password = "password=" + password;
		
		String args = username + "&" + password;	

		try {
			//Create connection
			URL url = new URL("http://sdchargers.herokuapp.com/register/");
			connection = (HttpURLConnection)url.openConnection();
			connection.setRequestMethod("POST");

			connection.setRequestProperty("Content-Type",
					"application/x-www-form-urlencoded");

	    	connection.setRequestProperty("Content-Length", 
	        Integer.toString(args.getBytes().length));
	    	connection.setRequestProperty("Content-Language", "en-US");  

	    	connection.setUseCaches(false);
	    	connection.setDoOutput(true);

	    	//Send request
	    	DataOutputStream wr = new DataOutputStream (
	        connection.getOutputStream());
	    	wr.writeBytes(args);
	    	wr.close();

			int responseCode = connection.getResponseCode();

			//Get Response
			BufferedReader in = new BufferedReader(
					new InputStreamReader(connection.getInputStream()));
			String inputLine;
			StringBuffer response = new StringBuffer();

			while ((inputLine = in.readLine()) != null) {
				response.append(inputLine);
			}
			in.close();

			// ---------------------
			// PROCESS JSON RESPONSE
			// If username was taken return null
			if (response.toString().equals("\"Username is already taken!\""))
				return null;

			JSONObject respJson = new JSONObject(response.toString());
			String uid = respJson.getString("$oid");
			return uid;


		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			if(connection != null) {
				connection.disconnect();
			}
		}
	}


	// Allow user to set apartment ID based on their User ID
	public static String setApartmentID (String userID, String aptName) {
		HttpURLConnection connection = null;
		aptName = "aptName=" + aptName;

		String args = aptName;

		try {
			//Create connection
			URL url = new URL("http://sdchargers.herokuapp.com/join/" + userID);
			connection = (HttpURLConnection)url.openConnection();
			connection.setRequestMethod("POST");

			connection.setRequestProperty("Content-Type",
					"application/x-www-form-urlencoded");

	    	connection.setRequestProperty("Content-Length", 
	        Integer.toString(aptName.getBytes().length));
	    	connection.setRequestProperty("Content-Language", "en-US");  

	    	connection.setUseCaches(false);
	    	connection.setDoOutput(true);

	    	//Send request
	    	DataOutputStream wr = new DataOutputStream (
	        connection.getOutputStream());
	    	wr.writeBytes(args);
	    	wr.close();

			int responseCode = connection.getResponseCode();

			//Get Response
			BufferedReader in = new BufferedReader(
					new InputStreamReader(connection.getInputStream()));
			String inputLine;
			StringBuffer response = new StringBuffer();

			while ((inputLine = in.readLine()) != null) {
				response.append(inputLine);
			}
			in.close();

			// ---------------------
			// PROCESS JSON RESPONSE - return apartment ID
			JSONObject respJson = new JSONObject(response.toString());
			JSONObject idObj = respJson.getJSONObject("_id");
			String aid = idObj.getString("$oid");
			if (aid.equals(""))
				return null;
			return aid;


		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			if(connection != null) {
				connection.disconnect();
			}
		}
	}


	// Allow user to CREATE a new apartment and join it based on their User ID
	public static String createApartment (String userID, String aptName) {
		HttpURLConnection connection = null;
		aptName = "aptName=" + aptName;

		String args = aptName;

		try {
			//Create connection
			URL url = new URL("http://sdchargers.herokuapp.com/create/" + userID);
			connection = (HttpURLConnection)url.openConnection();
			connection.setRequestMethod("POST");

			connection.setRequestProperty("Content-Type",
					"application/x-www-form-urlencoded");

	    	connection.setRequestProperty("Content-Length", 
	        Integer.toString(aptName.getBytes().length));
	    	connection.setRequestProperty("Content-Language", "en-US");  

	    	connection.setUseCaches(false);
	    	connection.setDoOutput(true);

	    	//Send request
	    	DataOutputStream wr = new DataOutputStream (
	        connection.getOutputStream());
	    	wr.writeBytes(args);
	    	wr.close();

			int responseCode = connection.getResponseCode();

			//Get Response
			BufferedReader in = new BufferedReader(
					new InputStreamReader(connection.getInputStream()));
			String inputLine;
			StringBuffer response = new StringBuffer();

			while ((inputLine = in.readLine()) != null) {
				response.append(inputLine);
			}
			in.close();

			// ---------------------
			if (response.equals("Apartment name is already taken!"))
				return null;

			// PROCESS JSON RESPONSE - return ID of new apt
			JSONObject respJson = new JSONObject(response.toString());
			String aid = respJson.getString("$oid");
			if (aid.equals(""))
				return null;
			return aid;


		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			if(connection != null) {
				connection.disconnect();
			}
		}
	}



	// Given a user ID, return the associated apartment ID
	public static String getApartmentID (String userID) {
		HttpURLConnection connection = null;
		try {
			//Create connection
			URL url = new URL("http://sdchargers.herokuapp.com/users/"
				+ userID);
			connection = (HttpURLConnection)url.openConnection();
			connection.setRequestMethod("GET");

			// Set header properties
			connection.setRequestProperty("Content-Type",
				"application/x-www-form-urlencoded");
	    	connection.setRequestProperty("Content-Language", "en-US");  
	    	connection.setUseCaches(false);

	    	// Make the query and get response status
			int responseCode = connection.getResponseCode();

			//Get Response sa JSON in text format
			BufferedReader in = new BufferedReader(
					new InputStreamReader(connection.getInputStream()));
			String inputLine;
			StringBuffer response = new StringBuffer();
			while ((inputLine = in.readLine()) != null) {
				response.append(inputLine);
			}
			in.close();

			// ---------------------
			// PROCESS JSON RESPONSE
			JSONObject respJson = new JSONObject(response.toString());
			JSONObject apartment = respJson.getJSONObject("apartment");
			String aid = apartment.getString("$oid");
			return aid;

		// Make the compiler happy! and report errors...
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			if(connection != null) {
				connection.disconnect();
			}
		}
	}


	// Given an apartment ID and a task string, add it to the list
	public static String addTask (String apt_id, String task) {
	  HttpURLConnection connection = null;  
	  task = "task=" + task;

	  try {
	    //Create connection
	    URL url = new URL(HOST + "tasks/" + apt_id);
	    connection = (HttpURLConnection)url.openConnection();
	    connection.setRequestMethod("POST");
		  connection.setDoOutput(true);
	    connection.setRequestProperty("Content-Type", 
	        "application/x-www-form-urlencoded");

	    connection.setRequestProperty("Content-Length", 
	        Integer.toString(task.getBytes().length));
	    connection.setRequestProperty("Content-Language", "en-US");  

	    connection.setUseCaches(false);
	    connection.setDoOutput(true);

	    //Send request
	    DataOutputStream wr = new DataOutputStream (
	        connection.getOutputStream());
	    wr.writeBytes(task);
	    wr.close();

	    int responseCode = connection.getResponseCode();

	    //Get Response  
		BufferedReader in = new BufferedReader(
		        new InputStreamReader(connection.getInputStream()));
		String inputLine;
		StringBuffer response = new StringBuffer();

		while ((inputLine = in.readLine()) != null) {
			response.append(inputLine);
		}
		in.close();
 		return (response.toString());

	    
	  } catch (Exception e) {
	    e.printStackTrace();
	    return null;
	  } finally {
	    if(connection != null) {
	      connection.disconnect(); 
	    }
	  }
	}


	// Gets an array of tasks with descriptions and assignees
	public static Task[] getTasks (String apt_id) {
	  HttpURLConnection connection = null;
	  try {
	    //Create connection
	    URL url = new URL("http://sdchargers.herokuapp.com/tasks/" + apt_id);
	    connection = (HttpURLConnection)url.openConnection();
	    connection.setRequestMethod("GET");

		connection.setRequestProperty("Content-Type",
				  "application/x-www-form-urlencoded");


	    int responseCode = connection.getResponseCode();

	    //Get Response
		BufferedReader in = new BufferedReader(
		        new InputStreamReader(connection.getInputStream()));
		String inputLine;
		StringBuffer response = new StringBuffer();

		while ((inputLine = in.readLine()) != null) {
			response.append(inputLine);
		}
		in.close();

		// ---------------------
		// PROCESS JSON RESPONSE
		JSONObject respJson = new JSONObject(response.toString());
		JSONArray arr = respJson.getJSONArray("tasks");
		String[] descriptions = new String[arr.length()];
		String[] assignees = new String[arr.length()];
		Task[] toReturn = new Task[arr.length()];
		for (int i = 0; i < arr.length(); i++)
		{
			JSONObject cur = arr.getJSONObject(i);
			descriptions[i] = cur.getString("description");
			assignees[i] = cur.getString("assignee");
			toReturn[i] = new Task(assignees[i], descriptions[i]);
		}
 		return toReturn;


	  } catch (Exception e) {
	    e.printStackTrace();
	    return null;
	  } finally {
	    if(connection != null) {
	      connection.disconnect();
	    }
	  }
	}
}
