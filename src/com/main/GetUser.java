package com.main;


import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.sling.commons.json.JSONObject;
import com.transform.VaultsName;
import com.user.User;

public class GetUser 
{
	public List<LinkedHashMap<String, String>> getuserlist(String session, String URL, Map<String, String> propData) throws Exception
	{
		String session_id = null;
		User user = new User();
		VaultsName vault = new VaultsName();
		JSONObject jb = new JSONObject(session);
		if(jb.getString("responseStatus").equalsIgnoreCase("SUCCESS"))
		{
			session_id = jb.getString("sessionId");
		}
		if(session_id != null)
		{
			List<JSONObject> userlist =	user.getUsers(URL,session_id);
			//All Users Retrieved
			String[] label = {"User Name","Name","Status","Last Login Date","Vaults","License Type","Security Profile","Email","Location","Region"};
			String[] keyName = {"username__sys"," ","status__v","last_login__sys"," ","license_type__sys","security_profile__sysr.name__v","email__sys","business_location__cr.name__v","region__c"};
			List<LinkedHashMap<String, String>> list = new ArrayList<LinkedHashMap<String,String>>();
			for(JSONObject ob : userlist)
			{
				LinkedHashMap<String, String> map = new LinkedHashMap<String, String>();
				for(int i=0;i<label.length;i++)
				{
					String val = (ob.has(keyName[i])?ob.get(keyName[i]):" ").toString();
					if(label[i].equalsIgnoreCase("Status")) 
					{
						val=val.toString().replace("[", "").replace("]", "");
						
						if (val.contains("inactive__v"))
							val = val.replaceAll("inactive__v", "Inactive");							    
						else
							val = val.replaceAll("active__v", "Active");
						val=val.substring(1, val.length()-1);						
					}
					if(label[i].equalsIgnoreCase("License Type"))
					{
						val=val.toString().replace("[", "").replace("]", "");
						if (val.contains("full__v"))
							val = val.replace("full__v", "Full User");
						else if(val.contains("read_only__v"))
							val = val.replace("read_only__v", "Read Only User");
						val=val.substring(1, val.length()-1);
					}
					if(label[i].equalsIgnoreCase("Region")) 
					{
						val=val.toString().replace("[", "").replace("]", "");
						if (val.contains("aspac__c"))
							val = val.replace("aspac__c", "ASPAC");
						val=val.substring(1, val.length()-1);
					}
					if(label[i].equalsIgnoreCase("Last Login Date"))
					{
						val = val.replaceAll("T", " : ");
						val = val.replaceAll(".000Z", " ");
					}
					
					val = val.replace(",",";");
					map.put(label[i],val);
				}
				String fname = (ob.has("first_name__sys")?ob.get("first_name__sys"):" ").toString();
				String lname = (ob.has("last_name__sys")?ob.get("last_name__sys"):" ").toString();
				String name = "\""+lname+","+fname+"\"";
				map.put(label[1],name);
				list.add(map);
			}
			List<LinkedHashMap<String,String>> user_updated = new ArrayList<LinkedHashMap<String,String>>();
			for(LinkedHashMap<String, String> ob : list)
			{
				if(ob.get("Region").toString().contains("ASPAC") && ob.get("Status").toString().contains("Active") && !(ob.get("Security Profile").toString().contains("JnJ Limited Admin (DevOps) User Only")) && !(ob.get("Security Profile").toString().contains("JnJ Integration User Only")) && !(ob.get("Security Profile").toString().contains("Vault Owner")))
					user_updated.add(ob);
			}
			List<LinkedHashMap<String,String>> user_modified = vault.getvaults(user_updated, propData);
			return user_modified;
		}
		return null;
	}
}
