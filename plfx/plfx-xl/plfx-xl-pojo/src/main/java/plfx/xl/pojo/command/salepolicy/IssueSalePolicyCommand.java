package plfx.xl.pojo.command.salepolicy;

import hg.common.component.BaseCommand;

/**
 * @类功能说明：发布价格政策    
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @作者：chenyanshou
 * @创建时间：2014年12月24日上午9:59:23
 * @版本：V1.0
 */

@SuppressWarnings("serial")
public class IssueSalePolicyCommand extends BaseCommand{
	
	/**
	 * 价格政策ID
	 */
	private String policyId;
	
	/**
	 * 操作人
	 */
	private String workerName;

	public String getPolicyId() {
		return policyId;
	}
	
	public void setPolicyId(String policyId) {
		this.policyId = policyId == null ? null : policyId.trim();
	}
	
	public String getWorkerName() {
		return workerName;
	}
	
	public void setWorkerName(String workerName) {
		this.workerName = workerName;
	}
	
	
}