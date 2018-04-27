package plfx.xl.pojo.qo;

import hg.common.component.BaseQo;

/**
 * 
 * @类功能说明：线路价格政策快照qo
 * @类修改者：
 * @修改日期：
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @作者：tandeng
 * @创建时间：2014年12月24日下午2:45:59
 * @版本：V1.0
 *
 */
@SuppressWarnings("serial")
public class SalePolicySnapshotQO extends BaseQo {

	/**
	 * 所属价格政策
	 */
	private String salePolicyID;
	
	/**
	 * 是否查询最新快照
	 */
	private Boolean isNew;

	public String getSalePolicyID() {
		return salePolicyID;
	}

	public void setSalePolicyID(String salePolicyID) {
		this.salePolicyID = salePolicyID;
	}

	public Boolean getIsNew() {
		return isNew;
	}

	public void setIsNew(Boolean isNew) {
		this.isNew = isNew;
	}
	
	
	
}
