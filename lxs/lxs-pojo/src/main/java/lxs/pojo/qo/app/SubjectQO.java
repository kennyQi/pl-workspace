package lxs.pojo.qo.app;

import hg.common.component.BaseQo;

@SuppressWarnings("serial")
public class SubjectQO extends BaseQo {

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

	private Integer sort;

	private Boolean sortProductSum;

	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}

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

	public Boolean getSortProductSum() {
		return sortProductSum;
	}

	public void setSortProductSum(Boolean sortProductSum) {
		this.sortProductSum = sortProductSum;
	}

}
