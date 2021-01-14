package com.main;


import java.util.*;
import com.excel.ExelUtil;
import com.excel.WriteExcel;
import com.session.Session;


public class ASPA_Active_User_main 
{
	static Map<String, String> propData = ExelUtil.getPropertiesData("conf.properties");
	public static final String URL= propData.get("URL");
	public static void main(String[] args) throws Exception 
	{
			WriteExcel exportObj = new WriteExcel();
			GetUser userObj = new GetUser();			
			String session = Session.getSession(URL+"/auth",propData.get("USERNAME"),propData.get("PASSWORD"));
			if(session != null)
			{
				List<LinkedHashMap<String,String>> userList = userObj.getuserlist(session, URL, propData);
				System.out.println("Output File is "+(exportObj.CSVFile(userList, propData)?"Created":"Not Created"));
			}
			else
				System.out.println("Session Not Created");
	}		
}
