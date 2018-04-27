package lxs.pojo.command.app;

import hg.common.component.BaseCommand;

@SuppressWarnings("serial")
public class AddFunctionIntroductionCommand extends BaseCommand {

	private String versionNumber;

	/**
	 * 版本说明
	 */

	private String versionContent;

	private String id;

	public String getVersionNumber() {
		return versionNumber;
	}

	public void setVersionNumber(String versionNumber) {
		this.versionNumber = versionNumber;
	}

	public String getVersionContent() {
		return versionContent;
	}

	public void setVersionContent(String versionContent) {
		this.versionContent = versionContent;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	
}
