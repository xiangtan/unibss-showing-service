package com.ai.showing.main;

import java.io.IOException;

import org.springframework.context.support.ClassPathXmlApplicationContext;

public class DubboServiceStarter
{
	public static void main(String[] args) throws IOException
	{
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(
				new String[] { "classpath*:spring/applicationContext.xml"});
		context.start();

		System.in.read();
		/* com.alibaba.dubbo.container.Main.main(args); */
	}
}
