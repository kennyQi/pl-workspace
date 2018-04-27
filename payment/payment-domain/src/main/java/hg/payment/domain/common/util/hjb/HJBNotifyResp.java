package hg.payment.domain.common.util.hjb;

/**
 * 
 * 
 *@类功能说明：异步回调响应给汇金宝的参数
 *@类修改者：
 *@修改日期：
 *@修改说明：
 *@公司名称：浙江汇购科技有限公司
 *@部门：技术部
 *@作者：luoyun
 *@创建时间：2014年11月21日下午2:47:32
 *
 */
public class HJBNotifyResp {
	
	/**
	 * 回调状态
	 * 为空表示成功
	 */
	private String status;
	
	/**
	 * 错误详情
	 */
	private String message;
	
	/**
	 * 汇金宝订单号
	 */
	private String orderNo;
	

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	
	

}
