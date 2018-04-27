package plfx.yeexing.qo.admin;

import hg.common.component.BaseQo;

/****
 * 
 * @类功能说明：机票日志QO
 * @类修改者：
 * @修改日期：
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @作者：yaosanfeng
 * @创建时间：2015年7月9日下午2:54:55
 * @版本：V1.0
 *
 */
@SuppressWarnings("serial")
public class JPOrderLogQO extends BaseQo{
	private String orderId;

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
}
