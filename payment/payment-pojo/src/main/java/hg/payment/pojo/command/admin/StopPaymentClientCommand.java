package hg.payment.pojo.command.admin;

import hg.common.component.BaseCommand;

/**
 * 停用支付客户端
 * @author luoyun
 *
 */
@SuppressWarnings("serial")
public class StopPaymentClientCommand extends BaseCommand{
	
	/**
	 * 主键
	 */
	private String id;
	

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	
	
	

}
