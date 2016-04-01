package com.ai.showing.unit.test;

import java.util.List;

import org.apache.ibatis.session.RowBounds;
import org.apache.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.ai.showing.api.IPartnerDubboApi;
import com.ai.showing.entity.Partner;
import com.ai.showing.mapper.PartnerMapper;
import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import tk.mybatis.mapper.entity.Example;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath*:spring/applicationContext.xml" })
public class PartnerTest
{
	private static Logger		log	= Logger.getLogger(PartnerTest.class);
	@Autowired
	private IPartnerDubboApi	partnerDubboApi;

	@Autowired
	private PartnerMapper		partnerMapper;

	@Test
	public void testGetById()
	{
		Partner bean = partnerDubboApi.getById(6);
		System.out.println(JSON.toJSONString(bean));
	}

	@Test
	public void testSelectByPage1()
	{
		Example example = new Example(Partner.class);
		example.createCriteria().andGreaterThan("id", "10");
		PageHelper.startPage(2, 3);
		List<Partner> list = partnerMapper.selectByExample(example);
		PageInfo<Partner> pageinfo = new PageInfo<>(list);
		log.info(JSON.toJSONString(pageinfo));
	}

	@Test
	public void testSelectByPage2()
	{
		Example example = new Example(Partner.class);
		example.createCriteria().andGreaterThan("id", "10");
		// PageHelper.startPage(3, 3);
		RowBounds rowBounds = new RowBounds(3, 3);

		List<Partner> list = partnerMapper.selectByExampleAndRowBounds(example, rowBounds);
		PageInfo<Partner> pageinfo = new PageInfo<>(list);
		log.info(JSON.toJSONString(pageinfo));
	}
}
