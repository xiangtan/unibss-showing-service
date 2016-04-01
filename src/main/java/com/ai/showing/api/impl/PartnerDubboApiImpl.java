package com.ai.showing.api.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ai.showing.api.IPartnerDubboApi;
import com.ai.showing.biz.PartnerBiz;
import com.ai.showing.entity.Partner;

@Service("partnerDubboApi")
public class PartnerDubboApiImpl implements IPartnerDubboApi
{
	@Autowired
	private PartnerBiz partnerBiz;

	@Override
	public Partner getById(int id)
	{
		return partnerBiz.getById(id);
	}

}
