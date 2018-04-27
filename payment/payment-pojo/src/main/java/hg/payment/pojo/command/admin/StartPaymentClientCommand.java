package hg.payment.pojo.command.admin;

import hg.common.component.BaseCommand;

@SuppressWarnings("serial")
public class StartPaymentClientCommand extends BaseCommand{
	
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
