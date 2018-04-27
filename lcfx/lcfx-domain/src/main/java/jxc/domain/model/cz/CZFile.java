/**
 * @CZFileFlow.java Create on 2015-6-11上午9:50:18
 * Copyright (c) 2012 by www.hg365.com。
 */
package jxc.domain.model.cz;

import java.util.Date;

import hg.common.component.BaseModel;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @类功能说明：
 * @类修改者：
 * @修改日期：2015-6-11上午9:50:18
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @作者：<a href=gaoce@hgtech365.com>gaoce</a>
 * @创建时间：2015-6-11上午9:50:18
 * @version：
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "HJF_CZ_File")
public class CZFile extends BaseModel {
	/**
	 * 已发送
	 */
	public static final String SEND = "SEND";
	/**
	 * 已反馈
	 */
	public static final String FEEDBACK = "FEEDBACK";
	/**
	 * 待发送
	 */
	public static final String TOSEND = "TOSEND";
	/**
	 * 发送失败
	 */
	public static final String SENDFAIL = "SENDFAIL";
	/**
	 * 已发送文件
	 */
	public static final String SENDFILE = "SENDFILE";

	/**
	 * 反馈文件
	 */
	public static final String BACKFILE = "BACKFILE";

	@Column(name = "status", length = 64)
	private String status;
	@Column(name = "file_name", length = 164)
	private String fileName;
	@Column(name = "timestamp")
	private Date timestamp;
	@Column(name = "feedback_time")
	private Date feedbacktime;
	@Column(name = "type")
	private String type;

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

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

}
