package hsl.pojo.command.config.wx;

import hg.common.component.BaseCommand;

/**
 * 修改微信自动回复配置
 *
 * @author zhurz
 */
@SuppressWarnings("serial")
public class ModifyWxAutoReplyConfigCommand extends BaseCommand {

	/**
	 * 关注回复
	 */
	private String welcomeReply;

	/**
	 * 自动回复
	 */
	private String autoReply;
	
	/**
	 * 自动回复ID
	 */
	private String autoReplyId;

	public String getAutoReplyId() {
		return autoReplyId;
	}

	public void setAutoReplyId(String autoReplyId) {
		this.autoReplyId = autoReplyId;
	}

	public String getWelcomeReply() {
		return welcomeReply;
	}

	public void setWelcomeReply(String welcomeReply) {
		this.welcomeReply = welcomeReply;
	}

	public String getAutoReply() {
		return autoReply;
	}

	public void setAutoReply(String autoReply) {
		this.autoReply = autoReply;
	}
}
