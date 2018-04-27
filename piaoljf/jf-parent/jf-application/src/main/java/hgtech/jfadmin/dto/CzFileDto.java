/**
 * @showDto.java Create on 2015年1月29日下午2:02:24
 * Copyright (c) 2012 by www.hg365.com。
 */
package hgtech.jfadmin.dto;

import java.util.Date;

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
public class CzFileDto extends DwzPaginQo {

	private static final long serialVersionUID = 1L;
	private String id;
	private String status;
	private String fileName;
	public String startDate;
	public String endDate;
	public String type;
	public Date timestamp;
	public Date feedbacktime;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	
	
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
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
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public Date getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}
	public Date getFeedbacktime() {
		return feedbacktime;
	}
	public void setFeedbacktime(Date feedbacktime) {
		this.feedbacktime = feedbacktime;
	}

}