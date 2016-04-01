package com.ai.showing.main;

import java.io.FileReader;
import java.util.Properties;

import com.ai.showing.h2.utils.H2Utils;

public class Tests
{

	public static void main(String[] args)
	{
		String file = H2Utils.class.getResource("/h2/message.properties").getFile();
		
		//String file = Tests.class.getResource("/h2message.properties").getFile();
		try
		{
			Properties properties = new Properties();
			FileReader reader = new FileReader(file);
			properties.load(reader);
			String value = properties.getProperty("H2_Header");
			System.out.println(value);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

}
