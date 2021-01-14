package com.excel;

import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Map.Entry;
import com.opencsv.CSVReader;

public class ExelUtil 
{
	public static List<LinkedHashMap<String, String>> readCSV(String fileName) {
		List<LinkedHashMap<String, String>> data = new ArrayList<>();
		try (CSVReader reader = new CSVReader(
				new FileReader(new File(fileName)))) {
			List<String[]> readData = reader.readAll();
			if (readData.size() > 0) {
				List<String> headers = getHeader(readData.get(0));
				readData = readData.subList(1, readData.size());
				for (String[] temp : readData) {
					LinkedHashMap<String, String> map = new LinkedHashMap<String, String>();
					int i = 0;
					for (String h : headers) {
						map.put(h, temp[i++]);
					}
					data.add(map);

				}

			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return data;

	}

	private static List<String> getHeader(String[] data) {
		List<String> headers = new ArrayList<>();
		for (String d : data) {
			headers.add(d);
		}
		return headers;

	}
	
	public static Map<String, String> getPropertiesData(String propFile) 
	{
		Map<String, String> data = new HashMap<>();
		try 
		{
			Properties prop = new Properties();
			prop.load(new ExelUtil().getClass().getClassLoader().getResourceAsStream(propFile));
			for (Entry<Object, Object> entry : prop.entrySet()) 
			{
				Object key = entry.getKey();
				Object val = entry.getValue();
				data.put(String.valueOf(key), String.valueOf(val).trim());

			}

		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}
		return data;
	}
}
