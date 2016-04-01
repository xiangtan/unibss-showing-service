package com.ai.showing.h2.utils;

import java.io.UnsupportedEncodingException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.ai.showing.h2.Constant;

/**
 * 字符串工具类
 * 
 * @author Luorj
 */
public class StringUtils
{

	/**
	 * 字符串右补齐
	 * 
	 * @param source 原字符串
	 * @param padChar 填充字符串
	 * @param length 目标长度
	 * @return
	 */
	public static String rightFill(String source, char padChar, int length)
	{
		byte[] c = new byte[length];
		String ruslut = "";
		try
		{
			byte[] s = source.getBytes(Constant.CHARSET_NAME);
			if (s.length > length)
			{
				throw new RuntimeException("目标不能小于源字符串长度！");
			}
			int len = s.length;
			System.arraycopy(s, 0, c, 0, len);
			for (int i = len; i < length; i++)
			{
				c[i] = (byte) padChar;
			}
			ruslut = new String(c, Constant.CHARSET_NAME);
		}
		catch (UnsupportedEncodingException e)
		{
			e.printStackTrace();
		}
		return ruslut;
	}

	/**
	 * 字符串右补齐
	 * 
	 * @param source 原字符串
	 * @param padChar 填充字符串
	 * @param length 目标长度
	 * @return
	 */
	public static String leftFill(String source, char padChar, int length)
	{
		byte[] c = new byte[length];
		String ruslut = "";
		try
		{
			byte[] s = source.getBytes(Constant.CHARSET_NAME);
			if (s.length > length)
			{
				throw new RuntimeException("目标不能小于源字符串长度！");
			}
			int len = s.length;
			int fl = length - len;
			for (int i = 0; i < fl; i++)
			{
				c[i] = (byte) padChar;
			}
			System.arraycopy(s, 0, c, fl, len);
			ruslut = new String(c, Constant.CHARSET_NAME);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return ruslut;
	}

	/**
	 * 去除字符串中的
	 * 
	 * @param str
	 * @return
	 */
	public static String replaceTab(String str)
	{
		String dest = "";
		if (str != null)
		{
			Pattern p = Pattern.compile("\\t");
			Matcher m = p.matcher(str);
			dest = m.replaceAll("");
		}
		return dest;
	}

	public static void main(String[] args)
	{
		/*
		 * System.out.println(rightFill("1380000.00", (char) 32, 15));
		 * System.out.println(leftFill("1380000.00", (char) 32, 15));
		 */
		System.out.println(rightFill("中国", (char) 32, 30));
		System.exit(0);
	}

}
