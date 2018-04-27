package plfx.xl.pojo.qo;

import hg.common.component.BaseQo;

/**
 * @类功能说明：价格政策操作日志查询qo
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @作者：liusong
 * @创建时间：2014年12月22日上午10:18:40
 * @版本：V1.0
 */
@SuppressWarnings("serial")
public class SalePolicyWorkLogQO extends BaseQo {

	/**
	 * 价格政策id
	 */
	private String salePolicyId;
	
	/**
	 * 按创建时间排序
	 */
	private Boolean createDateAsc = false;

	public String getSalePolicyId() {
		return salePolicyId;
	}
	
	public void setSalePolicyId(String salePolicyId) {
		this.salePolicyId = salePolicyId;
	}

	public Boolean getCreateDateAsc() {
		return createDateAsc;
	}

	public void setCreateDateAsc(Boolean createDateAsc) {
		this.createDateAsc = createDateAsc;
	}
	
	
	
}