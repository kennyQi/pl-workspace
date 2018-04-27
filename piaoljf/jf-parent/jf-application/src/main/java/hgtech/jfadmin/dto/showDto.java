/**
 * @showDto.java Create on 2015年1月29日下午2:02:24
 * Copyright (c) 2012 by www.hg365.com。
 */
package hgtech.jfadmin.dto;

import hg.common.model.qo.DwzPaginQo;

/**
 * 
 * @类功能说明：接收积分查询，流水查询的参数
 * @类修改者：
 * @修改日期：2014年11月7日下午2:22:33
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @作者：pengel
 * @创建时间：2014年11月7日下午2:22:33
 * 
 **                           url中传递参数如下：。 名字 解释 code 帐号 startDate 查询起始时间
 *                           endDate 查询结束时间
 */
public class showDto extends DwzPaginQo {
	public static String TRADERAMERK = "开户奖励积分";
	public String user;
    public String code;
	public String startDate;
	public String endDate;
	public  String type;
	public  String status;
	public  String tradeRemark;
	public  String batchNo;
	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	/**
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * @param status
	 *            the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
	}

	public String getTradeRemark() {
		return tradeRemark;
	}

	public void setTradeRemark(String tradeRemark) {
		this.tradeRemark = tradeRemark;
	}

	public String getBatchNo() {
		return batchNo;
	}

	public void setBatchNo(String batchNo) {
		this.batchNo = batchNo;
	}
	

}