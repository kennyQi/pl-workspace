package hsl.pojo.command;

import hg.common.component.BaseCommand;

/**
 * @类功能说明：创建公告
 * @类修改者：
 * @修改日期：2014年12月15日上午9:52:32
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @作者：chenxy
 * @创建时间：2014年12月15日上午9:52:32
 *
 */
public class CreateNoticeCommand extends BaseCommand{
	private static final long serialVersionUID = 1L;
	/**
	 * 标题
	 */
	private String title;
	/**
	 * 发布人（登录帐号）
	 */
	private String issueUser; 
	/**
	 * 截止时间
	 */
	private String cutOffTime;
	/**
	 * 是否审核
	 */
	private int checkedStatus;
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
	
	public String getCutOffTime() {
		return cutOffTime;
	}

	public void setCutOffTime(String cutOffTime) {
		this.cutOffTime = cutOffTime;
	}
	public int getCheckedStatus() {
		return checkedStatus;
	}

	public void setCheckedStatus(int checkedStatus) {
		this.checkedStatus = checkedStatus;
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
