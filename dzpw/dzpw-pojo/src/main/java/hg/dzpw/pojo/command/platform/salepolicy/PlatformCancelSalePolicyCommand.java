package hg.dzpw.pojo.command.platform.salepolicy;

import hg.dzpw.pojo.common.DZPWPlatformBaseCommand;

import java.util.Date;

/**
 * @类功能说明： 取消价格策略
 * @类修改者：
 * @修改日期：2014-11-19上午10:43:37
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @作者：zzx
 * @创建时间：2014-11-19上午10:43:37
 */
@Deprecated
public class PlatformCancelSalePolicyCommand extends DZPWPlatformBaseCommand {
	private static final long serialVersionUID = 1L;

	private String salePolicyId;
	
	/**
	 * 取消原因
	 */
	private String cancleIntro;

	/**
	 * 操作人
	 */
	private String adminName;
	
	/**
	 * 操作时间
	 */
	private Date createDate;
	
	public String getSalePolicyId() {
		return salePolicyId;
	}
	public void setSalePolicyId(String salePolicyId) {
		this.salePolicyId = salePolicyId == null ? null : salePolicyId.trim();
	}
	public String getCancleIntro() {
		return cancleIntro;
	}
	public void setCancleIntro(String cancleIntro) {
		this.cancleIntro = cancleIntro == null ? null : cancleIntro.trim();
	}
	public String getAdminName() {
		return adminName;
	}
	public void setAdminName(String adminName) {
		this.adminName = adminName == null ? null : adminName.trim();
	}
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
}