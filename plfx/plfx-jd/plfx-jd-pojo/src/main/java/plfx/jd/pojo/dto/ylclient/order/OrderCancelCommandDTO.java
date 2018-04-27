
package plfx.jd.pojo.dto.ylclient.order;

import java.io.Serializable;
/***
 * 
 * @类功能说明：取消订单DTO
 * @类修改者：
 * @修改日期：
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @作者：yaosanfeng
 * @创建时间：2015年6月24日下午4:55:23
 * @版本：V1.0
 *
 */
public class OrderCancelCommandDTO implements Serializable{
	/**
	 * 订单Id(必填)
	 */
    protected long orderId;
    /**
     * 取消类型(必填)
     * 示例:
                        对酒店相关条件不满意
                        航班推迟
                        价格过高，客人不接受
                       通过其它途径预订
                       行程变更
                       已换酒店
     * 
     */
    protected String cancelCode;
    /**
     * 取消原因
     */
    protected String reason;
	public long getOrderId() {
		return orderId;
	}
	public void setOrderId(long orderId) {
		this.orderId = orderId;
	}
	public String getCancelCode() {
		return cancelCode;
	}
	public void setCancelCode(String cancelCode) {
		this.cancelCode = cancelCode;
	}
	public String getReason() {
		return reason;
	}
	public void setReason(String reason) {
		this.reason = reason;
	}

    

}
