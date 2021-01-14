package com.session;

import java.io.*;
import java.net.*;

import org.apache.sling.commons.json.JSONException;
import org.apache.sling.commons.json.JSONObject;

public class Session {
	public static String getSession(String url,String username,String password)
	{ 
	HttpURLConnection con = null;
	BufferedReader read = null;
	try{
		con = (HttpURLConnection) new URL(url).openConnection();
		con.setRequestMethod("POST");
		con.setDoOutput(true);
		con.getOutputStream().write(("username="+username+"&password="+password).getBytes());

		read = new BufferedReader(new InputStreamReader(con.getInputStream()));
		String response = read.readLine();
		
		return response;		
	}
	catch(Exception e)
	{
		e.printStackTrace();
	}
	return null;
	}
	public static JSONObject getRequest(String url,String session_id) throws MalformedURLException, IOException, JSONException
	{
		HttpURLConnection con = (HttpURLConnection) new URL(url).openConnection();
		con.addRequestProperty("Authorization",session_id);
		con.addRequestProperty("accept","application/JSON");
		String result = new BufferedReader(new InputStreamReader(con.getInputStream())).readLine();
		JSONObject response = new JSONObject(result);
		return response;
	}
}
