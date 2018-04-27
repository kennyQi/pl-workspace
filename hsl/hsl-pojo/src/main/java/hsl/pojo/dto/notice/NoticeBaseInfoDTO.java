package hsl.pojo.dto.notice;

import java.util.*;

/**
 * @类功能说明：公告基本信息
 * @类修改者：
 * @修改日期：2014年12月12日上午10:11:12
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @作者：chenxy
 * @创建时间：2014年12月12日上午10:11:12
 */
public class NoticeBaseInfoDTO {
	/**
	 * 标题
	 */
	private String title;
	/**
	 * 发布人
	 */
	private String issueUser; 
	/**
	 * 截止时间
	 */
	private Date cutOffTime;
	/**
	 * 更新时间
	 */
	private Date createTime;
	/**
	 * 更新事件
	 */
	private Date lastTime;
	/**
	 * 公告详情
	 */
	private String details;
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getIssueUser() {
		return issueUser;
	}

	public void setIssueUser(String issueUser) {
		this.issueUser = issueUser;
	}

	public Date getCutOffTime() {
		return cutOffTime;
	}

	public void setCutOffTime(Date cutOffTime) {
		this.cutOffTime = cutOffTime;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getLastTime() {
		return lastTime;
	}

	public void setLastTime(Date lastTime) {
		this.lastTime = lastTime;
	}
	public String getDetails() {
		return details;
	}
	public void setDetails(String details) {
		this.details = details;
	}

}