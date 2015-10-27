//// URL Constant to mongoDB server
//HOST = "http://sdchargers.herokuapp.com/"
//api.add_resource(ApartmentList, '/Apartments/')
//api.add_resource(Apartment, '/Apartments/<ObjectId:apartment_id>')
//api.add_resource(Register, '/register/')
//api.add_resource(JoinApartment, '/join/')
//
//
//class serverCom(String hostURI) {
//
//	HttpClient httpClient = new defaultHttpClient();
//
//	public String login(String user, String pass) {
//		HttpPost httpPost = new HttpPost(HOST)
//
//		List<NameValuePair> nameValuePairs = ArrayList<NameValuePair>();
//		nameValuePairs.add(new BasicNameValuePair("user",user));
//		nameValuePairs.add(new BasicNameValuePair("pass",pass));
//
//		HttpResponse response = httpClient.sendPost(httpPost, nameValuePairs);
//
//		// Read response here
//		String responseBody = EntityUtils.toString(response.getEntity());
//		return responseBody
//	}
//
//	//-return an ArrayList that contains all of the tasks for the apartment
//	//-return an empty ArrayList if there are none
//	String getTaskList() {
//		String route = "/apartment/tasks"
//		HttpGet httpGet = new HttpGet(HOST + route);
//
//		String session_id = "dummy_session_id"
//
//		List<NameValuePair> nameValuePairs = ArrayList<NameValuePair>();
//		nameValuePairs.add(new BasicNameValuePair("session_id",session_id));
//
//		try { inPost.setEntity(new UrlEncodedFormEntity(inData));
//		} catch (UnsupportedEncodingException e) {print e.printStackTrace();}
//
//		try {
//			HttpResponse response = httpClient.execute(httpGet);
//			Log.d("Http Response:", response.toString());
//		} catch (ClientProtocolException e) {
//			print e.printStackTrace();
//		} catch (IOException e)	{
//			print e.printStackTrace();
//		}
//
//		HttpResponse response = httpClient.sendGet(httpGet, nameValuePairs);
//
//		String responseBody = EntityUtils.toString(response.getEntity());
//		return responseBody
//	}
//
//	//-add a task to the database for the apartment
//	//-return false if it fails (not sure if this can happen)
//	String addTask(String task) {
//		String route = "/apartment/tasks"
//		HttpPost httpPost = new HttpPost(HOST + route)
//
//		String session_id = "dummy_session_id"
//
//		List<NameValuePair> nameValuePairs = ArrayList<NameValuePair>();
//		nameValuePairs.add(new BasicNameValuePair("session_id",session_id));
//		nameValuePairs.add(new BasicNameValuePair("task",task));
//
//		try {
//			HttpResponse response = httpClient.execute(httpPost);
//			Log.d("Http Response:", response.toString());
//		} catch (ClientProtocolException e) {
//			print e.printStackTrace();
//		} catch (IOException e)	{
//			print e.printStackTrace();
//		}
//
//		String responseBody = EntityUtils.toString(response.getEntity());
//		return responseBody
//	}
//
//	//-create a new apartment in the database
//	//-return the newly generated ID
//	String createApartment(String name) {
//		String route = "/apartmentList/"
//		HttpPost httpPost = new HttpPost(HOST + route)
//
//		String session_id = "dummy_session_id"
//
//		List<NameValuePair> nameValuePairs = ArrayList<NameValuePair>();
//		nameValuePairs.add(new BasicNameValuePair("session_id",session_id));
//		nameValuePairs.add(new BasicNameValuePair("name",name));
//
//		try {
//			HttpResponse response = httpClient.execute(httpPost);
//			Log.d("Http Response:", response.toString());
//		} catch (ClientProtocolException e) {
//			print e.printStackTrace();
//		} catch (IOException e)	{
//			print e.printStackTrace();
//		}
//
//		String responseBody = EntityUtils.toString(response.getEntity());
//		return responseBody
//	}
//
//	/*
//	//This one is less necessary for this iteration but if you want to implement it
//	//-remove task from database
//	//-return false if unsuccessful and true if it worked
//	boolean removeTask(String task) {
//		HttpDelete HttpDelete = new HttpDelete(HOST + "/apartment/task")
//
//		String session_id = "dummy_session_id"
//		String command = "removeTask"
//
//		List<NameValuePair> nameValuePairs = ArrayList<NameValuePair>();
//		nameValuePairs.add(new BasicNameValuePair("session_id",session_id));
//		nameValuePairs.add(new BasicNameValuePair("command",command));
//		nameValuePairs.add(new BasicNameValuePair("task",task));
//
//		HttpResponse response = httpClient.sendPost(httpPost, nameValuePairs);
//		String responseBody = EntityUtils.toString(response.getEntity());
//		return responseBody
//	}
//	*/
//
//}
