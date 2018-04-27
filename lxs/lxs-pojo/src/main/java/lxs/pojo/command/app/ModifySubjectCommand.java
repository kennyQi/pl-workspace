package lxs.pojo.command.app;

import hg.common.component.BaseCommand;

@SuppressWarnings("serial")
public class ModifySubjectCommand extends BaseCommand {
	
	/**
	 * 主题ID
	 */
	private String subjectID;

	/**
	 * 主题名
	 */
	private String subjectName;

	/**
	 * 主题分类
	 */
	private Integer subjectType;

	public String getSubjectID() {
		return subjectID;
	}

	public void setSubjectID(String subjectID) {
		this.subjectID = subjectID;
	}

	public String getSubjectName() {
		return subjectName;
	}

	public void setSubjectName(String subjectName) {
		this.subjectName = subjectName;
	}

	public Integer getSubjectType() {
		return subjectType;
	}

	public void setSubjectType(Integer subjectType) {
		this.subjectType = subjectType;
	}

}
