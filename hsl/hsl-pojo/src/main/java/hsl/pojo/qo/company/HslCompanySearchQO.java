package hsl.pojo.qo.company;

import hg.common.component.BaseQo;

/**
 * 
 * @类功能说明：公司查询QO
 * @类修改者：
 * @修改日期：
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @作者：yuqz
 * @创建时间：2014年10月21日上午9:56:12
 * @版本：V1.0
 *
 */
@SuppressWarnings("serial")
public class HslCompanySearchQO extends BaseQo{
	/**
	 * 用户id
	 */
	private String userId;
	
	/**
	 * 查询关键字
	 */
	private String searchName;

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getSearchName() {
		return searchName;
	}

	public void setSearchName(String searchName) {
		this.searchName = searchName;
	}
	
	
}
