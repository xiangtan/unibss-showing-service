package com.ai.showing.h2.utils;

import java.io.DataInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.Properties;

import org.apache.log4j.Logger;

import com.ai.showing.h2.Constant;
import com.ai.showing.h2.H2Exception;

/**
 * 网络工具包
 * 
 * @author Luorj
 */
public class NetUtils
{
	private static final Logger	LOG	= Logger.getLogger(NetUtils.class);
	private static Properties	properties;
	private static Socket		client;

	static
	{
		try
		{
			client = new Socket();
			client.connect(new InetSocketAddress(getValue("H2.Socket_Ip"), Integer.valueOf(getValue("H2.Socket_Prot"))),
					Integer.valueOf(getValue("H2.Socket_SotIimeout")));
			client.setKeepAlive(Boolean.valueOf(getValue("H2.Socket_KeepAlive")));
			client.setSoTimeout(Integer.valueOf(getValue("H2.Socket_SotIimeout")));
		}
		catch (Exception e)
		{
			LOG.error("创建Socket客户端失败", e);
			throw new H2Exception("创建Socket客户端失败", e);
		}
	}

	/**
	 * 获取Net配置
	 * 
	 * @param key
	 * @return
	 */
	public static String getValue(String key)
	{
		if (null == properties)
		{
			properties = new Properties();
			String file = NetUtils.class.getResource("/h2/net.properties").getFile();
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

	/**
	 * 获取Socket客户端
	 * 
	 * @return
	 */
	public static Socket getClient()
	{
		try
		{
			if (client.isClosed() || !client.isConnected())
			{
				client = new Socket();
				client.connect(
						new InetSocketAddress(getValue("H2.Socket_Ip"), Integer.valueOf(getValue("H2.Socket_Prot"))),
						Integer.valueOf(getValue("H2.Socket_SotIimeout")));
				client.setKeepAlive(Boolean.valueOf(getValue("H2.Socket_KeepAlive")));
				client.setSoTimeout(Integer.valueOf(getValue("H2.Socket_SotIimeout")));
			}
		}
		catch (Exception e)
		{
			LOG.error("创建Socket客户端失败", e);
			throw new H2Exception("创建Socket客户端失败", e);
		}
		return client;
	}

	/**
	 * 通过Socket发送数据，并获得相应数据
	 * 
	 * @param requestmMsg
	 * @return
	 */
	public static String sendH2BySocket(String requestmMsg)
	{
		LOG.info("发起请求:" + requestmMsg);
		StringBuffer result = new StringBuffer();
		OutputStream out = null;
		DataInputStream in = null;
		try
		{
			Socket client = getClient();
			LOG.debug("client:" + client.toString());
			out = client.getOutputStream();
			out.write(requestmMsg.getBytes(Constant.CHARSET_NAME));
			out.flush();
			in = new DataInputStream(client.getInputStream());
			byte[] bytes = new byte[4245];
			int data_len = 0;
			do
			{
				data_len = in.read(bytes);
				LOG.debug("data_len:" + data_len);
				if (data_len != -1)
				{
					String responseMsg = new String(bytes, Constant.CHARSET_NAME);
					LOG.debug("responseMsg:" + responseMsg);
					result.append(responseMsg);
					Thread.sleep(100);
				}
			}
			while (data_len == 4245);
		}
		catch (Exception e)
		{
			LOG.error("Socket请求失败", e);
			throw new H2Exception("Socket请求失败", e);
		}
		finally
		{
			if (null != in)
			{
				try
				{
					in.close();
				}
				catch (IOException e)
				{
				}
			}
			if (null != out)
			{
				try
				{
					out.close();
				}
				catch (IOException e)
				{
				}
			}
		}
		LOG.debug("请求返回字符串长度：" + result.length());
		return result.toString();
	}
}
