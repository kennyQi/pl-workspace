package hsl.pojo.qo.programa;

import hg.common.component.BaseQo;
@SuppressWarnings("serial")
public class HslProgramaContentQO extends BaseQo{
	/**栏目内容*/
	private String content;
	/**
	 * 栏目ID
	 */
	private String programaId;
	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getProgramaId() {
		return programaId;
	}

	public void setProgramaId(String programaId) {
		this.programaId = programaId;
	}
	
}
