package hsl.pojo.qo.company;

import hg.common.component.BaseQo;

/**
 * 部门搜索QO
 * @author zhuxy
 *
 */
@SuppressWarnings("serial")
public class HslDepartmentQO extends BaseQo {
	
	/**
	 * 公司qo
	 */
	private HslCompanyQO companyQO;
	
	// private String companyId;
	
	private String searchName;

	/*public String getCompanyId() {
		return companyId;
	}

	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}*/

	public String getSearchName() {
		return searchName;
	}

	public void setSearchName(String searchName) {
		this.searchName = searchName;
	}

	public HslCompanyQO getCompanyQO() {
		return companyQO;
	}

	public void setCompanyQO(HslCompanyQO companyQO) {
		this.companyQO = companyQO;
	}
	
}
