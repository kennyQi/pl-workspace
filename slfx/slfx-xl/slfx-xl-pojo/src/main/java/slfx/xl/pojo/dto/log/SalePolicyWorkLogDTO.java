package slfx.xl.pojo.dto.log;

import java.util.Date;

import slfx.xl.pojo.dto.BaseXlDTO;
import slfx.xl.pojo.dto.salepolicy.SalePolicyLineDTO;


/**
 * @类功能说明：价格政策操作日志DTO
 * @类修改者：
 * @修改日期：
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @作者：liusong
 * @创建时间：2014年12月22日上午10:16:06
 * @版本：V1.0
 *
 */
@SuppressWarnings("serial")
public class SalePolicyWorkLogDTO  extends BaseXlDTO{

	
	/**
	 *日志操作时间
	 */
	private Date logDate;
	
	/**
	 * 操作名称
	 */
	private   String     salePolicyLogName;
	
	/**
	 * 操作人
	 */
	private    String     salePolicyWorkerName;
	
	
	/**
	 * 操作信息
	 */
	private    String     salePolicyLogInfoName;
	
	/**
	 * 价格政策基本信息
	 */
	private  SalePolicyLineDTO   salePolicy;

	 /*set以及get方法*/
	public Date getLogDate() {
		return logDate;
	}

	public void setLogDate(Date logDate) {
		this.logDate = logDate;
	}

	public String getSalePolicyLogName() {
		return salePolicyLogName;
	}

	public void setSalePolicyLogName(String salePolicyLogName) {
		this.salePolicyLogName = salePolicyLogName;
	}

	public String getSalePolicyWorkerName() {
		return salePolicyWorkerName;
	}

	public void setSalePolicyWorkerName(String salePolicyWorkerName) {
		this.salePolicyWorkerName = salePolicyWorkerName;
	}

	public String getSalePolicyLogInfoName() {
		return salePolicyLogInfoName;
	}

	public void setSalePolicyLogInfoName(String salePolicyLogInfoName) {
		this.salePolicyLogInfoName = salePolicyLogInfoName;
	}

	public SalePolicyLineDTO getSalePolicy() {
		return salePolicy;
	}

	public void setSalePolicy(SalePolicyLineDTO salePolicy) {
		this.salePolicy = salePolicy;
	}
	
	
}
