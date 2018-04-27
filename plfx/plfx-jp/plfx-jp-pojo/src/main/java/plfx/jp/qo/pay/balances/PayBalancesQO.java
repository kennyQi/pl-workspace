package plfx.jp.qo.pay.balances;

import hg.common.component.BaseQo;

/**
 * 
 * @类功能说明：支付宝余额查询QO
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @作者：yuqz
 * @创建时间：2015年12月25日上午11:03:53
 * @版本：V1.0
 *
 */
@SuppressWarnings("serial")
public class PayBalancesQO extends BaseQo{
	/**
	 * 预警类型
	 * 0:票量分销国内机票
	 */
	private Integer type;

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}
}
