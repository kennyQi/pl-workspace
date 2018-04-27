package lxs.pojo.qo.app;

import hg.common.component.BaseQo;

@SuppressWarnings("serial")
public class IntroductionQO extends BaseQo {

	private Integer introductionType;
	private String introductionContent;
	private String id;

	public Integer getIntroductionType() {
		return introductionType;
	}

	public void setIntroductionType(Integer introductionType) {
		this.introductionType = introductionType;
	}

	public String getIntroductionContent() {
		return introductionContent;
	}

	public void setIntroductionContent(String introductionContent) {
		this.introductionContent = introductionContent;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

}
