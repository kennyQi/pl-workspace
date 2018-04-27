package slfx.mp.command.admin;

import hg.common.component.BaseCommand;

/**
 * 管理员取消订单
 * 
 * @author yuxx
 */
@SuppressWarnings("serial")
public class AdminCancelOrderCommand extends BaseCommand {

	/**
	 * 分销平台订单号
	 */
	private String id;

	/**
	 * 订单状态
	 */
	private boolean cancel;

	/**
	 * 取消原因(代码)
	 */
	private String cancelRemark;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public boolean isCancel() {
		return cancel;
	}

	public void setCancel(boolean cancel) {
		this.cancel = cancel;
	}

	public String getCancelRemark() {
		return cancelRemark;
	}

	public void setCancelRemark(String cancelRemark) {
		this.cancelRemark = cancelRemark;
	}

}
