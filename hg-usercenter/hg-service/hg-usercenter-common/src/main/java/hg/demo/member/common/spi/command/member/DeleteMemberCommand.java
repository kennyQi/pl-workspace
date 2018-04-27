package hg.demo.member.common.spi.command.member;

import hg.framework.common.base.BaseSPICommand;

/**
 * @author zhurz
 */
@SuppressWarnings("serial")
public class DeleteMemberCommand extends BaseSPICommand {

	/**
	 * 成员ID
	 */
	private String id;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
}
