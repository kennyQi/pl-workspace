package plfx.yeexing.pojo.dto.order;

/****
 * 
 * @类功能说明:机票订单状态
 * @类修改者：
 * @修改日期：
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @作者：yaosanfeng
 * @创建时间：2015年7月8日上午11:14:36
 * @版本：V1.0
 *
 */
public class JPOrderStatus {

	/** 订单状态 */
	private Integer status;

	/** 支付状态 */
	private Integer payStatus;

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Integer getPayStatus() {
		return payStatus;
	}

	public void setPayStatus(Integer payStatus) {
		this.payStatus = payStatus;
	}

}
