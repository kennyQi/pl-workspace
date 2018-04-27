package hg.demo.member.common.spi.command.authRole;

import hg.framework.common.base.BaseSPICommand;

/**
 * Created by admin on 2016/5/20.
 */
@SuppressWarnings("serial")
public class DeleteAuthRoleCommand extends BaseSPICommand {
	
	/**
	 * ID
	 */
	private String id;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
}
