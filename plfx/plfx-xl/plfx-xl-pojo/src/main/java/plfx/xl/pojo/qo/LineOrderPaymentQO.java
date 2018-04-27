package plfx.xl.pojo.qo;

import hg.common.component.BaseQo;

/**
 * 
 * @类功能说明：订单支付信息QO
 * @类修改者：
 * @修改日期：
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @作者：luoyun
 * @创建时间：2014年12月23日下午4:42:30
 * @版本：V1.0
 *
 */
@SuppressWarnings("serial")
public class LineOrderPaymentQO extends BaseQo{

	/**
	 * 订单编号
	 */
	private String lineOrderID;
	
	/**
	 * 按创建时间排序
	 */
	private Boolean createDateAsc = false;

	public String getLineOrderID() {
		return lineOrderID;
	}

	public void setLineOrderID(String lineOrderID) {
		this.lineOrderID = lineOrderID;
	}

	public Boolean getCreateDateAsc() {
		return createDateAsc;
	}

	public void setCreateDateAsc(Boolean createDateAsc) {
		this.createDateAsc = createDateAsc;
	}
	
	
}
