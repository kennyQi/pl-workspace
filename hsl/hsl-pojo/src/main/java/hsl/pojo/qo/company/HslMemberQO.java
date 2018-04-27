package hsl.pojo.qo.company;

import hg.common.component.BaseQo;

/**
 * 查找成员的QO
 * @author zhuxy
 *
 */
@SuppressWarnings("serial")
public class HslMemberQO extends BaseQo {
	/**
	 * 部门ID
	 */
	private String departmentId;
	/**
	 * 是否查找离职人员
	 */
	private Boolean isDel = false;
	
	/**
	 * 搜索关键字
	 */
	private String searchName;

	public String getDepartmentId() {
		return departmentId;
	}

	public void setDepartmentId(String departmentId) {
		this.departmentId = departmentId;
	}

	public Boolean getIsDel() {
		return isDel;
	}

	public void setIsDel(Boolean isDel) {
		this.isDel = isDel;
	}

	public String getSearchName() {
		return searchName;
	}

	public void setSearchName(String searchName) {
		this.searchName = searchName;
	}

}
