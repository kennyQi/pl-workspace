package hsl.domain.model.notice;
import hg.system.model.M;

import java.util.*;

import javax.persistence.Column;
import javax.persistence.Embeddable;
/**
 * @类功能说明：公告基本信息
 * @类修改者：
 * @修改日期：2014年12月12日上午10:11:12
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @作者：chenxy
 * @创建时间：2014年12月12日上午10:11:12
 * 
 */
@Embeddable
public class NoticeBaseInfo {
	/**
	 * 标题
	 */
	@Column(name="TITLE", length = 64)
	private String title;
	/**
	 * 发布人
	 */
	@Column(name="ISSUEUSER", length = 64)
	private String issueUser;  
	/**
	 * 截止时间
	 */
	@Column(name="CUTOFFTIME", columnDefinition = M.DATE_COLUM)
	private Date cutOffTime;
	/**
	 * 创建时间
	 */
	@Column(name="CREATETIME", columnDefinition = M.DATE_COLUM)
	private Date createTime;
	/**
	 * 更新时间
	 */
	@Column(name="LASTTIME", columnDefinition = M.DATE_COLUM)
	private Date lastTime;
	/**
	 * 详情
	 */
	@Column(name="DETAILS", columnDefinition = M.TEXT_COLUM)
	private String details;
	
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
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

	public String getIssueUser() {
		return issueUser;
	}

	public void setIssueUser(String issueUser) {
		this.issueUser = issueUser;
	}

}