package hsl.domain.model.sys.wx;

import hg.common.component.BaseModel;
import hsl.pojo.command.config.wx.ModifyWxAutoReplyConfigCommand;

/**
 * 微信自动回复设置
 *
 * @author zhurz
 */
public class WxAutoReplyConfig extends BaseModel{


	private static final long serialVersionUID = 1L;

	/**
	 * 关注回复
	 */
	private String welcomeReply;

	/**
	 * 自动回复
	 */
	private String autoReply;


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

	// --------------------------------------------------------------------

	private transient Manager manager;

	public Manager manager() {
		if (manager == null)
			manager = new Manager();
		return manager;
	}

	public class Manager {
		/**
		 * 修改微信自动回复
		 *
		 * @param command
		 */
		public void modify(ModifyWxAutoReplyConfigCommand command) {
			setWelcomeReply(command.getWelcomeReply());
			setAutoReply(command.getAutoReply());
		}
	}
}
