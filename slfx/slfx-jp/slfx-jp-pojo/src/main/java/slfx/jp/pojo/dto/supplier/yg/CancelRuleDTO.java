package slfx.jp.pojo.dto.supplier.yg;

import slfx.jp.pojo.dto.BaseJpDTO;

/**
 * 
 * @类功能说明：退改规定DTO
 * @类修改者：
 * @修改日期：
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @作者：tandeng
 * @创建时间：2014年7月31日下午4:57:00
 * @版本：V1.0
 *
 */
@SuppressWarnings("serial")
public class CancelRuleDTO extends BaseJpDTO{
	
   /** 退改规定内容 */
   private String cancelRule;

	public String getCancelRule() {
		return cancelRule;
	}
	
	public void setCancelRule(String cancelRule) {
		this.cancelRule = cancelRule;
	}
   
}