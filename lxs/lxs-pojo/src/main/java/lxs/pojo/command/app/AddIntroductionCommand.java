package lxs.pojo.command.app;

import hg.common.component.BaseCommand;

@SuppressWarnings("serial")
public class AddIntroductionCommand extends BaseCommand {
	private String introductionContent;
	
	private String id;
	
	private Integer introductionType=-1;

	

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getIntroductionContent() {
		return introductionContent;
	}

	public void setIntroductionContent(String introductionContent) {
		this.introductionContent = introductionContent;
	}

	public Integer getIntroductionType() {
		return introductionType;
	}

	public void setIntroductionType(Integer introductionType) {
		this.introductionType = introductionType;
	}
}
