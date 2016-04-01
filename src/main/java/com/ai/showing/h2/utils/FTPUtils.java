package com.ai.showing.h2.utils;

import java.io.FileReader;
import java.util.Properties;

import org.apache.log4j.Logger;

import com.ai.showing.h2.H2Exception;

public class FTPUtils
{
	private static final Logger	LOG	= Logger.getLogger(FTPUtils.class);
	private static Properties	properties;

	public static String getValue(String key)
	{
		if (null == properties)
		{
			properties = new Properties();
			String file = H2Utils.class.getResource("/h2/ftpupload.properties").getFile();
			try
			{
				FileReader reader = new FileReader(file);
				properties.load(reader);
			}
			catch (Exception e)
			{
				LOG.error("加载配置文件失败!", e);
				throw new H2Exception("加载配置文件失败!", e);
			}
		}
		return properties.getProperty(key);
	}
}
