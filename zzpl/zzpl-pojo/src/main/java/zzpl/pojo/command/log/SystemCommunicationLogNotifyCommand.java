package zzpl.pojo.command.log;

import hg.common.component.BaseCommand;

@SuppressWarnings("serial")
public class SystemCommunicationLogNotifyCommand extends BaseCommand{

	/**
	 * 通知发送方
	 */
	private String notifyHost;

	/**
	 * 通知内容
	 */
	private String notifyContent;

	public String getNotifyHost() {
		return notifyHost;
	}

	public void setNotifyHost(String notifyHost) {
		this.notifyHost = notifyHost;
	}

	public String getNotifyContent() {
		return notifyContent;
	}

	public void setNotifyContent(String notifyContent) {
		this.notifyContent = notifyContent;
	}

}