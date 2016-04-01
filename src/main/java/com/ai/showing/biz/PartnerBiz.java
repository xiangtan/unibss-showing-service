package com.ai.showing.biz;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ai.showing.entity.Partner;
import com.ai.showing.mapper.PartnerMapper;

@Service("partnerBiz")
public class PartnerBiz
{
	@Autowired
	private PartnerMapper partnerMapper;

	public Partner getById(int id)
	{
		return partnerMapper.selectByPrimaryKey(id);
	}
}
