package com.excel;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class WriteExcel 
{
	public boolean CSVFile(List<LinkedHashMap<String, String>> data, Map<String, String> propData) throws IOException
	{
		String fileName = propData.get("OUTPUT_EXCEL");
		Iterator<LinkedHashMap<String, String>> it = data.iterator();
		FileWriter writer=null;
		if(it.hasNext())
		{
			writer=new FileWriter(fileName);
			writer.write(convertToCommaDelimited(data.get(0).keySet().toArray(),","));
			writer.write("\n");
		}
		while(it.hasNext())
		{
			LinkedHashMap<String, String> sub = it.next();
			Iterator<String> key = sub.keySet().iterator();
			while(key.hasNext())
			{
				String keys=key.next();
				writer.write(sub.get(keys).toString());
				writer.write(",");
			}
			writer.write("\n");
		}
		if(writer!=null)
		{
			writer.flush();
			writer.close();
			return true;
		}
		return false;

	}

	public static String convertToCommaDelimited(Object[] list,String delimeter) {
		StringBuffer ret = new StringBuffer("");
		for (int i = 0; list != null && i < list.length; i++) {
			ret.append(list[i]);
			if (i < list.length - 1) {
				ret.append(delimeter);
			}
		}
		return ret.toString();
	}
}
