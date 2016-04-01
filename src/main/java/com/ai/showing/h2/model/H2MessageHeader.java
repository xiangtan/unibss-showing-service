package com.ai.showing.h2.model;

import com.ai.showing.h2.utils.StringUtils;

/**
 * H2消息头
 * 
 * @author Luorj
 */
public class H2MessageHeader
{
	/** 版本号信息 */
	private String	version;
	/** 数据包大小 */
	private String	length;
	/** 流水号 */
	private String	seqno;
	/** 标志 */
	private String	flag;
	/** 服务类型 */
	private String	svctype;
	/** 业务号码 */
	private String	busino;
	/** 业务号码类型 */
	private String	numtype;
	/** 营业点 */
	private String	operator;
	/** 营业员 */
	private String	busreg;
	/** 包编号 */
	private String	packagno;
	/** 最后一包标志 */
	private String	endflag;
	/** 错误码 */
	private String	errorno;

	/**
	 * 默认初始化
	 */
	public H2MessageHeader()
	{
		this.setVersion("11");
		this.setLength("87");
		this.setSeqno("");
		this.setFlag("0");
		this.setSvctype("");
		this.setBusino("");
		this.setNumtype("1");
		this.setOperator("8888");
		this.setBusreg("8888");
		this.setPackagno("1");
		this.setEndflag("1");
		this.setErrorno("00000");
	}

	public String getVersion()
	{
		return version;
	}

	public void setVersion(String version)
	{
		this.version = StringUtils.rightFill(version, (char) 32, 2);
	}

	public String getLength()
	{
		return length;
	}

	/**
	 * 发信息时自动计算，不需要手动设置
	 * 
	 * @param length
	 */
	public void setLength(String length)
	{
		this.length = StringUtils.rightFill(length, (char) 32, 5);
	}

	public String getSeqno()
	{
		return seqno;
	}

	public void setSeqno(String seqno)
	{
		this.seqno = StringUtils.rightFill(seqno, (char) 32, 20);
	}

	public String getFlag()
	{
		return flag;
	}

	public void setFlag(String flag)
	{
		this.flag = StringUtils.rightFill(flag, (char) 32, 1);
	}

	public String getSvctype()
	{
		return svctype;
	}

	public void setSvctype(String svctype)
	{
		this.svctype = StringUtils.rightFill(svctype, (char) 32, 12);
	}

	public String getBusino()
	{
		return busino;
	}

	public void setBusino(String busino)
	{
		this.busino = StringUtils.rightFill(busino, (char) 32, 20);
	}

	public String getNumtype()
	{
		return numtype;
	}

	public void setNumtype(String numtype)
	{
		this.numtype = StringUtils.rightFill(numtype, (char) 32, 1);
	}

	public String getOperator()
	{
		return operator;
	}

	public void setOperator(String operator)
	{
		this.operator = StringUtils.rightFill(operator, (char) 32, 6);
	}

	public String getBusreg()
	{
		return busreg;
	}

	public void setBusreg(String busreg)
	{
		this.busreg = StringUtils.rightFill(busreg, (char) 32, 8);
	}

	public String getPackagno()
	{
		return packagno;
	}

	public void setPackagno(String packagno)
	{
		this.packagno = StringUtils.rightFill(packagno, (char) 32, 5);
	}

	public String getEndflag()
	{
		return endflag;
	}

	public void setEndflag(String endflag)
	{
		this.endflag = StringUtils.rightFill(endflag, (char) 32, 1);
	}

	public String getErrorno()
	{
		return errorno;
	}

	public void setErrorno(String errorno)
	{
		this.errorno = StringUtils.rightFill(errorno, (char) 32, 5);
	}

	@Override
	public String toString()
	{
		return version + length + seqno + flag + svctype + busino + numtype + operator + busreg + packagno + endflag
				+ errorno;
	}
}
