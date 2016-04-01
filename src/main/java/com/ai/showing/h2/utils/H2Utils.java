package com.ai.showing.h2.utils;

import java.io.FileReader;
import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import org.apache.log4j.Logger;

import com.ai.showing.h2.Constant;
import com.ai.showing.h2.H2Exception;
import com.ai.showing.h2.model.H2Message;
import com.ai.showing.h2.model.H2MessageHeader;

/**
 * H2协议工具类
 * 
 * @author Luorj
 */
public class H2Utils
{

	private static final Logger	LOG	= Logger.getLogger(H2Utils.class);
	private static Properties	properties;

	/**
	 * 获取H2协议配置
	 * 
	 * @param key
	 * @return
	 */
	public static String getValue(String key)
	{
		if (null == properties)
		{
			properties = new Properties();
			String file = H2Utils.class.getResource("/h2/message.properties").getFile();
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
	 * 发送H2协议报文
	 * 
	 * @param h2 包含H2包头包体
	 * @return
	 */
	public static String sendH2(H2Message h2)
	{
		return NetUtils.sendH2BySocket(h2Message2H2Str(h2, 1));
	}

	/**
	 * 将H2消息包体按照协议格式转换成字符串
	 * 
	 * @param body
	 * @param msgType 消息类型，请求类型为1；返回为2
	 * @return
	 */
	public static String h2Message2H2Str(H2Message h2, int msgType)
	{
		StringBuffer message = new StringBuffer();
		// 获取协议配置
		String svctype = h2.getHeader().getSvctype();
		Map<String, Integer> configMap = getConfigMap(svctype + "H2_" + msgType);
		StringBuffer bodyMsg = new StringBuffer();
		if (!configMap.isEmpty() && null != h2.getBody())
		{
			LinkedList<LinkedHashMap<String, String>> body = h2.getBody();
			for (LinkedHashMap<String, String> item : body)
			{
				Set<String> keySet = item.keySet();
				for (String key : keySet)
				{
					String val = item.get(key);
					int length = configMap.get(key);
					bodyMsg.append(StringUtils.rightFill(val, (char) 32, length) + (char) 0x09);
				}
			}
		}
		H2MessageHeader header = h2.getHeader();
		try
		{
			int msgLength = bodyMsg.toString().getBytes(Constant.CHARSET_NAME).length + 87;
			header.setLength(msgLength + "");
			if (msgLength > 4425)
			{
				// TODO:暂时不支持长度大于4425的字符串
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		message.append(header.toString());
		LOG.debug("header:" + header);
		message.append(bodyMsg);
		return message.toString() + (char) 0x1a;
	}

	/**
	 * 获取指定业务的配置
	 * 
	 * @param svctype
	 * @return
	 */
	public static Map<String, Integer> getConfigMap(String svctype)
	{
		String config = getValue(svctype);
		Map<String, Integer> configMap = new LinkedHashMap<String, Integer>();
		if (null != config && !"".equals(config.trim()))
		{
			String[] configs = config.split("\\|");
			for (String info : configs)
			{
				String[] vals = info.split(":");
				configMap.put(vals[0], Integer.valueOf(vals[1]));
			}
		}
		return configMap;
	}

	/**
	 * 将H2协议字符串转换成H2对象包体
	 * 
	 * @param message h2消息体
	 * @param msgType 请求为1，返回为2
	 * @return
	 */
	public static List<H2Message> h2Str2H2Message(String message, int msgType)
	{
		List<H2Message> msgList = new LinkedList<H2Message>();
		// 判断报文是单条还是多条
		String[] msgs = message.split((char) 0x1a + "");
		for (String msg : msgs)
		{
			if ("".equals(msg.trim()))
			{
				continue;
			}
			char[] msgHeader = new char[86];
			msg.getChars(0, 86, msgHeader, 0);
			H2MessageHeader header = new H2MessageHeader();
			int index = 0;
			header.setVersion(new String(Arrays.copyOfRange(msgHeader, index, index += 2)));
			header.setLength(new String(Arrays.copyOfRange(msgHeader, index, index += 5)));
			header.setSeqno(new String(Arrays.copyOfRange(msgHeader, index, index += 20)));
			header.setFlag(new String(Arrays.copyOfRange(msgHeader, index, index += 1)));
			header.setSvctype(new String(Arrays.copyOfRange(msgHeader, index, index += 12)));
			header.setBusino(new String(Arrays.copyOfRange(msgHeader, index, index += 20)));
			header.setNumtype(new String(Arrays.copyOfRange(msgHeader, index, index += 1)));
			header.setOperator(new String(Arrays.copyOfRange(msgHeader, index, index += 6)));
			header.setBusreg(new String(Arrays.copyOfRange(msgHeader, index, index += 8)));
			header.setPackagno(new String(Arrays.copyOfRange(msgHeader, index, index += 5)));
			header.setEndflag(new String(Arrays.copyOfRange(msgHeader, index, index += 1)));
			header.setErrorno(new String(Arrays.copyOfRange(msgHeader, index, index += 5)));
			LinkedList<LinkedHashMap<String, String>> body = new LinkedList<LinkedHashMap<String, String>>();
			try
			{
				if (msg.getBytes(Constant.CHARSET_NAME).length > 86)
				{
					String bodyMsg = msg.substring(86);
					String[] bodyMsgs = bodyMsg.split((char) 0x0d + "" + (char) 0x0a);
					for (String bodyStr : bodyMsgs)
					{
						LinkedHashMap<String, String> item = new LinkedHashMap<String, String>();
						if (bodyStr.contains("失败"))
						{
							if (bodyStr.matches(".*\\{.*\\}.*"))
							{
								int start = bodyStr.lastIndexOf("{");
								int end = bodyStr.lastIndexOf("}");
								item.put("ERROR", bodyStr.substring(start + 1, end - 1));
							}
							else
							{
								item.put("ERROR", bodyStr);
							}
							body.add(item);
							break;
						}
						Map<String, Integer> configMap = getConfigMap(header.getSvctype() + "H2_" + msgType);
						Set<String> keySet = configMap.keySet();
						index = 0;
						bodyStr = StringUtils.replaceTab(bodyStr);
						byte[] bodys = bodyStr.getBytes(Constant.CHARSET_NAME);
						for (String key : keySet)
						{
							item.put(key, new String(Arrays.copyOfRange(bodys, index, index += configMap.get(key)),
									Constant.CHARSET_NAME));
						}
						body.add(item);
					}
				}
			}
			catch (Exception e)
			{
				LOG.debug("", e);
			}
			H2Message h2Msg = new H2Message();
			h2Msg.setHeader(header);
			h2Msg.setBody(body);
			msgList.add(h2Msg);
		}
		return msgList;
	}

	public static void main(String[] args) throws UnsupportedEncodingException
	{
		/*
		 * int i = 0; while(i< 10){ ++i; String h2Message =
		 * "1193   070091119182746796801102011202000         186971100011Beijinh2st10061    100000201409"
		 * ; System.out.println(NetUtils.sendH2BySocket(h2Message)); }
		 */
		// 110134000000
		// String h2Message = "1193 070091119182746796801102011202000
		// 186971100011Beijinh2st10061 100000201409";
		// String h2Message = "11297 070091119182746796801103011428000
		// 186971000101Beijinh2st1006
		// 1100000啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊aa啊啊啊啊啊
		// ";
		/*
		 * String reMessage =
		 * "070091119182746796801102011202000         186971100011Beijinh2st10061    000000"
		 * ;
		 */
		// System.out.println((int)'');

		// System.out.println((int)Character.valueOf(''));
		// String smsdata =
		// "亲，您正在进行手机验证，验证码：4108切勿将验证码泄露与他人。如非本人操作，建议及时修改账号密码。【青海联通】";

		// System.out.println(smsdata.getBytes("GBK").length);

		H2Message h2 = new H2Message();
		H2MessageHeader header = new H2MessageHeader();
		header.setBusino("18697105150");
		header.setSvctype("110162000000");
		h2.setHeader(header);
		// 11532 010201110050113109713677 18888 8888 1 1000002 wx 自助办理流量包 {{} {}
		// {} {} 99106265 1 1 {0 0 0 0}}
		// SLLX:2|DITCH:8|REMARK:30|COMMAND:401
		/*
		 * LinkedList<LinkedHashMap<String,String>> body = new
		 * LinkedList<LinkedHashMap<String,String>>();
		 * LinkedHashMap<String,String> item = new
		 * LinkedHashMap<String,String>(); item.put("SLLX", "2");
		 * item.put("DITCH", ""); item.put("REMARK", "自助办理流量包");
		 * item.put("COMMAND", "{{} {} {} {} 99106265 1 1 {0 0 0 0}}");
		 * body.add(item); h2.setBody(body);
		 */
		// System.out.println(h2Message2H2Str(h2,1));
		// header.setLength(h2.toString().getBytes("UTF-8").length+"");
		/*
		 * System.out.println(h2Message2H2Str(h2,1));
		 * System.out.println(h2Message2H2Str(h2,1).getBytes("UTF-8").length);
		 */
		System.out.println(h2Str2H2Message(sendH2(h2), 2).get(0));
		// System.out.println(sendH2(h2));

		// System.out.println(NetUtils.sendH2BySocket("11228
		// 010301142800015500599989 1wx wx 1
		// 100000亲，您正在进行手机验证，验证码：4108切勿将验证码泄露与他人。如非本人操作，建议及时修改账号密码。【青海联通】 "));
	}
}
