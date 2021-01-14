package com.transform;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.excel.ExelUtil;

public class VaultsName 
{
	public List<LinkedHashMap<String, String>> getvaults(List<LinkedHashMap<String, String>> list, Map<String, String> propData) throws Exception
	{
		String path = propData.get("INPUT_EXCEL");
		List<LinkedHashMap<String, String>> vaults = ExelUtil.readCSV(path);
		List<LinkedHashMap<String, String>> finalUpdatedList = new ArrayList<LinkedHashMap<String,String>>();
		for(LinkedHashMap<String, String> obj : list)
		{
			for(LinkedHashMap<String, String> obj1 : vaults)
			{
				String userName = obj1.get("User Name");
				String vaultName = obj1.get("Vaults").replaceAll(",", ";").toString();
				if(obj.get("User Name").equalsIgnoreCase(userName))
				{
					obj.put("Vaults", vaultName);
					break;
				}
			}
			finalUpdatedList.add(obj);
		}
		return finalUpdatedList;
	}
}
