package com.ai.showing.h2.model;

import java.util.LinkedHashMap;
import java.util.LinkedList;

/**
 * H2消息包体
 * 
 * @author Luorj
 */
public class H2Message
{

	/** 消息头 */
	private H2MessageHeader								header;

	/** 消息体 */
	private LinkedList<LinkedHashMap<String, String>>	body;

	public H2MessageHeader getHeader()
	{
		return header;
	}

	public void setHeader(H2MessageHeader header)
	{
		this.header = header;
	}

	public LinkedList<LinkedHashMap<String, String>> getBody()
	{
		return body;
	}

	public void setBody(LinkedList<LinkedHashMap<String, String>> body)
	{
		this.body = body;
	}

	@Override
	public String toString()
	{
		return "header:" + header.toString() + "body:" + body;
	}

}
