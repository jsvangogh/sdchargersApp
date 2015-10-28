/* URL Constant to mongoDB server
HOST = "http://sdchargers.herokuapp.com/"
api.add_resource(ApartmentList, '/Apartments/')
api.add_resource(Apartment, '/Apartments/<ObjectId:apartment_id>')
api.add_resource(Register, '/register/')
api.add_resource(JoinApartment, '/join/')
*/

import java.io.BufferedReader;
import java.io.*; 
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;


public class ServerCom
{
	// Define string constant
	public static final String HOST = "http://sdchargers.herokuapp.com/";

	//HttpClient httpClient = new defaultHttpClient();

	/*public String login(String user, String pass)
	{
		HttpPost httpPost = new HttpPost(HOST);

		List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
		nameValuePairs.add(new BasicNameValuePair("user",user));
		nameValuePairs.add(new BasicNameValuePair("pass",pass));

		HttpResponse response = httpClient.sendPost(httpPost, nameValuePairs);

		// Read response here
		String responseBody = EntityUtils.toString(response.getEntity());
		return responseBody;
	}*/


	public static String addTask (String apt_id, String task) {
	  HttpURLConnection connection = null;  
	  try {
	    //Create connection
	    URL url = new URL(HOST + "Apartments/" + apt_id);
	    connection = (HttpURLConnection)url.openConnection();
	    connection.setRequestMethod("POST");
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

	public static String getTasks (String apt_id) {
	  HttpURLConnection connection = null;  
	  try {
	    //Create connection
	    URL url = new URL(HOST + "Apartments/" + apt_id);
	    connection = (HttpURLConnection)url.openConnection();
	    connection.setRequestMethod("GET");
	    connection.setDoOutput(true);
	    connection.setRequestProperty("User-Agent", "Mozilla/5.0");

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


	/*
	//-return an ArrayList that contains all of the tasks for the apartment
	//-return an empty ArrayList if there are none
	public static String getTaskList(String apt_id) {
		String route = "Apartments/" + apt_id;
		HttpGet httpGet = new HttpGet(HOST + route);

		// String session_id = "dummy_session_id";

		//List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
		//nameValuePairs.add(new BasicNameValuePair("session_id",session_id));

		try { inPost.setEntity(new UrlEncodedFormEntity(inData));
		} catch (UnsupportedEncodingException e) {
			System.out.print(e.printStackTrace());
		}

		try {
			HttpResponse response = httpClient.execute(httpGet);
			Log.d("Http Response:", response.toString());
		} catch (ClientProtocolException e) {
			System.out.print(e.printStackTrace());
		} catch (IOException e)	{
			System.out.print(e.printStackTrace());
		}

		HttpResponse response = httpClient.sendGet(httpGet, nameValuePairs);

		String responseBody = EntityUtils.toString(response.getEntity());
		return responseBody;
	}
	*/

	/*
	//-add a task to the database for the apartment
	//-return false if it fails (not sure if this can happen)
	String addTask(String task) {
		String route = "/apartment/tasks";
		HttpPost httpPost = new HttpPost(HOST + route);

		String session_id = "dummy_session_id";

		List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
		nameValuePairs.add(new BasicNameValuePair("session_id",session_id));
		nameValuePairs.add(new BasicNameValuePair("task",task));

		try {
			HttpResponse response = httpClient.execute(httpPost);
			Log.d("Http Response:", response.toString());
		} catch (ClientProtocolException e) {
			System.out.print(e.printStackTrace());
		} catch (IOException e)	{
			System.out.print(e.printStackTrace());
		}

		String responseBody = EntityUtils.toString(response.getEntity());
		return responseBody;
	}
	*/

	/* //-create a new apartment in the database
	//-return the newly generated ID
	String createApartment(String name) {
		String route = "/apartmentList/";
		HttpPost httpPost = new HttpPost(HOST + route);

		String session_id = "dummy_session_id";

		List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
		nameValuePairs.add(new BasicNameValuePair("session_id",session_id));
		nameValuePairs.add(new BasicNameValuePair("name",name));

		try {
			HttpResponse response = httpClient.execute(httpPost);
			Log.d("Http Response:", response.toString());
		} catch (ClientProtocolException e) {
			System.out.print(e.printStackTrace());
		} catch (IOException e)	{
			System.out.print(e.printStackTrace());
		}

		String responseBody = EntityUtils.toString(response.getEntity());
		return responseBody;
	} */

	/*
	//This one is less necessary for this iteration but if you want to implement it
	//-remove task from database
	//-return false if unsuccessful and true if it worked
	boolean removeTask(String task) {
		HttpDelete HttpDelete = new HttpDelete(HOST + "/apartment/task")

		String session_id = "dummy_session_id"
		String command = "removeTask"

		List<NameValuePair> nameValuePairs = ArrayList<NameValuePair>();
		nameValuePairs.add(new BasicNameValuePair("session_id",session_id));
		nameValuePairs.add(new BasicNameValuePair("command",command));
		nameValuePairs.add(new BasicNameValuePair("task",task));

		HttpResponse response = httpClient.sendPost(httpPost, nameValuePairs);
		String responseBody = EntityUtils.toString(response.getEntity());
		return responseBody
	}
	*/

}
