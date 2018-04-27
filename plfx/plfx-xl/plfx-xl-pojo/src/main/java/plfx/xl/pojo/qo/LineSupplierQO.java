package plfx.xl.pojo.qo;

import hg.common.component.BaseQo;

/**
 * 
 * 
 *@类功能说明：线路供应商QO
 *@类修改者：
 *@修改日期：
 *@修改说明：
 *@公司名称：浙江汇购科技有限公司
 *@部门：技术部
 *@作者：luoyun
 *@创建时间：2014年12月4日上午10:05:09
 *
 */
@SuppressWarnings("serial")
public class LineSupplierQO extends BaseQo{
	
	/**
	 * 供应商名称
	 */
	private String name;
	
	/**
	 *  供应商名称模糊查询
	 */
	private Boolean nameLike = true;
	
	/**
	 * 创建时间由小到大排序
	 */
	private Boolean createDateAsc = false;
	
	/**
	 * 审核状态
	 */
	private Integer status;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Boolean getNameLike() {
		return nameLike;
	}

	public void setNameLike(Boolean nameLike) {
		this.nameLike = nameLike;
	}

	public Boolean getCreateDateAsc() {
		return createDateAsc;
	}

	public void setCreateDateAsc(Boolean createDateAsc) {
		this.createDateAsc = createDateAsc;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}
	
	

}
