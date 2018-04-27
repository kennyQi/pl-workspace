package hg.dzpw.pojo.api.alipay;

import java.util.Date;

/**
 * 
 * @类功能说明：支付宝扣款接口返回参数实体
 * @类修改者：
 * @修改日期：
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @作者：guotx
 * @创建时间：2016-3-1下午3:51:24
 * @版本：
 */
public class CaeChargeResponse {

	/** 请求是否成功，不可空，T表示成功，F表示失败*/
	private boolean success;
	
	/** 签名，不可空*/
	private String sign;
	
	/** 签名方式， DSA、RSA、 MD5，可空*/
	private String sign_type;
	
	/** 错误码，可空
	 * SYSTEM_ERROR 支付宝系统错误
	 * SESSION_TIMEOUT session超时
	 * ILLEGAL_TARGER_SERVICE 错误的target_service
	 * ILLEGAL_ACCESS_SWITCH_SYSTEM 不允许访问该类型系统
	 * ILLEGAL_SWITCH_SYSTEM 切换系统异常
	 * EXTERFACE_IS_CLOSED 接口已关闭
	 * */
	private String error;
	
	/** 商户订单号，不可空*/
	private String out_order_no;
	
	/** 订单状态，不可空
	 * TRADE_FINISHED：交易完成
	 * TRADE_SUCCESS：支付成功，此时可退款
	 * WAIT_BUYER_PAY：交易创建，等待支付
	 * TRADE_CLOSED：交易关闭
	 * */
	private String status;
	
	/** 交易流水号，不可空
	 *  支付宝交易流水号  最短16位，最长64位。
	 * */
	private String trade_no;
	
	/** 财务执行时间,可空
	 *  2009-06-24 19:19:09
	 * */
	private Date pay_date;

	public String getSign() {
		return sign;
	}

	public void setSign(String sign) {
		this.sign = sign;
	}

	public String getSign_type() {
		return sign_type;
	}

	public void setSign_type(String sign_type) {
		this.sign_type = sign_type;
	}

	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}

	public String getOut_order_no() {
		return out_order_no;
	}

	public void setOut_order_no(String out_order_no) {
		this.out_order_no = out_order_no;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getTrade_no() {
		return trade_no;
	}

	public void setTrade_no(String trade_no) {
		this.trade_no = trade_no;
	}

	public Date getPay_date() {
		return pay_date;
	}

	public void setPay_date(Date pay_date) {
		this.pay_date = pay_date;
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

}
