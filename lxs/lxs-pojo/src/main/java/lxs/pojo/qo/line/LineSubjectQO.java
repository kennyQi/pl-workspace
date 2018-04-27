package lxs.pojo.qo.line;

import hg.common.component.BaseQo;

@SuppressWarnings("serial")
public class LineSubjectQO extends BaseQo{

	private String id;
	/**
	 * 主题ID
	 */
	private String subjectID;

	/**
	 * 线路ID 当线路删除或者下架时应删除关联信息
	 */
	private String lineID;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getSubjectID() {
		return subjectID;
	}

	public void setSubjectID(String subjectID) {
		this.subjectID = subjectID;
	}

	public String getLineID() {
		return lineID;
	}

	public void setLineID(String lineID) {
		this.lineID = lineID;
	}
	
	
}
