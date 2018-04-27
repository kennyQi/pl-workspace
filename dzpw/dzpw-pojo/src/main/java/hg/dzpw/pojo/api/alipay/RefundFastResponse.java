package hg.dzpw.pojo.api.alipay;

/**
 * 
 * @类功能说明：支付宝批量退款返回实体
 * @类修改者：
 * @修改日期：
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @作者：guotx
 * @创建时间：2016-3-10下午3:17:07
 * @版本：
 */
public class RefundFastResponse {
	/**
	 * 是否成功
	 */
	private boolean isSuccess;

	/**
	 * 提示信息
	 */
	private String message;

	public boolean isSuccess() {
		return isSuccess;
	}

	public void setSuccess(boolean isSuccess) {
		this.isSuccess = isSuccess;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}
