/**
 * @CZFileFlow.java Create on 2015-6-11上午9:50:18
 * Copyright (c) 2012 by www.hg365.com。
 */
package hg.fx.domain;

import hg.demo.member.common.domain.model.def.O;
import hg.framework.common.base.BaseStringIdModel;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * @类功能说明：南航文件详情
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
@Table(name = O.FX_TABLE_PREFIX +"CZ_File")
public class CZFile extends BaseStringIdModel {
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

	/**
	 * 文件状态
	 * 已发送--SEND
	 * 已反馈--FEEDBACK
	 * 待发送--TOSEND
	 * 发送失败--SENDFAIL
	 */
	@Column(name = "STATUS", length = 64)
	private String status;
	/**
	 * 文件名
	 */
	@Column(name = "FILE_NAME", length = 164)
	private String fileName;
	/**
	 * 时间戳
	 */
	@Column(name = "TIMESTAMP", columnDefinition = O.DATE_COLUM)
	private Date timestamp;
	/**
	 * 反馈时间
	 */
	@Column(name = "FEEDBACK_TIME", columnDefinition = O.DATE_COLUM)
	private Date feedbacktime;
	/**
	 * 文件类型
	 * 已发送文件--SENDFILE
	 * 反馈文件--BACKFILE(文件反馈时 CZFILE表未存对应记录  只修改对应发送记录状态)
	 */
	@Column(name = "TYPE", length = 255)
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
