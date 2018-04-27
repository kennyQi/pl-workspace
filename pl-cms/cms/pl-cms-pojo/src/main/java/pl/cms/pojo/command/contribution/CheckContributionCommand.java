package pl.cms.pojo.command.contribution;
import pl.cms.pojo.command.AdminBaseCommand;
/**
 * @类功能说明：审核稿件command
 * @类修改者：
 * @修改日期：2015年3月18日下午4:11:10
 * @修改说明：
 * @公司名称：浙江票量科技有限公司
 * @作者：chenxy
 * @创建时间：2015年3月18日下午4:11:10
 */
@SuppressWarnings("serial")
public class CheckContributionCommand extends AdminBaseCommand {
	/**
	 * 稿件ID
	 */
	private String contributionId;
	/**
	 * 审核说明
	 */
	private String remark;
	/**
	 * 审核状态
	 */
	private Boolean checkStatus;
	/**
	 * 管理员ID
	 */
	private String adminId;
	/**
	 * 管理员 姓名
	 */
	private String adminName;
	public String getContributionId() {
		return contributionId;
	}
	public void setContributionId(String contributionId) {
		this.contributionId = contributionId;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public Boolean getCheckStatus() {
		return checkStatus;
	}
	public void setCheckStatus(Boolean checkStatus) {
		this.checkStatus = checkStatus;
	}
	public String getAdminId() {
		return adminId;
	}
	public void setAdminId(String adminId) {
		this.adminId = adminId;
	}
	public String getAdminName() {
		return adminName;
	}
	public void setAdminName(String adminName) {
		this.adminName = adminName;
	}
}
