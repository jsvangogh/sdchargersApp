//package com.example.joshua.livetogether;

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

	public static String addTask (String apt_id, String task) {
	  HttpURLConnection connection = null;  
	  task = "task=" + task;

	  try {
	    //Create connection
	    URL url = new URL(HOST + "Apartments/" + apt_id);
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

	public static String[] getTasks (String apt_id) {
	  HttpURLConnection connection = null;
	  try {
	    //Create connection
	    URL url = new URL("http://sdchargers.herokuapp.com/Apartments/" + apt_id);
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
		String[] toReturn = new String[arr.length()];
		for (int i = 0; i < arr.length(); i++)
		{
			toReturn[i] = arr.getString(i);
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
