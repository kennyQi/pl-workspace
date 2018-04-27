package hg.dzpw.pojo.api.alipay;

import java.util.Date;

/**
 * 
 * @类功能说明：支付宝退款异步通知接口请求参数列表
 * @类修改者：
 * @修改日期：
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @作者：guotx
 * @创建时间：2016-3-23上午11:10:46
 * @版本：
 */
public class RefundNotifyRequest {
	// 基本参数
	/** 通知发送的时间，非空 */
	private String notify_time;
	/** 通知类型，非空，trade_status_sync */
	private String notify_type;
	/** 通知校验id，非空，可用于通过接口校验是不是支付宝发送的 */
	private String notify_id;
	/** 签名方式，DSA,RSA,MD5，非空 */
	private String sign_type;
	/** 签名，非空 */
	private String sign;

	// 业务参数
	/** 退款批次号，非空 */
	private String batch_no;
	/** 退款成功总数，非空 */
	private String success_num;
	/** 处理结果详情，非空 */
	private String result_details;
	/** 解冻结果明细 ，可空 */
	private String unfreezed_datails;

	public String getNotify_time() {
		return notify_time;
	}

	public void setNotify_time(String notify_time) {
		this.notify_time = notify_time;
	}

	public String getNotify_type() {
		return notify_type;
	}

	public void setNotify_type(String notify_type) {
		this.notify_type = notify_type;
	}

	public String getNotify_id() {
		return notify_id;
	}

	public void setNotify_id(String notify_id) {
		this.notify_id = notify_id;
	}

	public String getSign_type() {
		return sign_type;
	}

	public void setSign_type(String sign_type) {
		this.sign_type = sign_type;
	}

	public String getSign() {
		return sign;
	}

	public void setSign(String sign) {
		this.sign = sign;
	}

	public String getBatch_no() {
		return batch_no;
	}

	public void setBatch_no(String batch_no) {
		this.batch_no = batch_no;
	}

	public String getSuccess_num() {
		return success_num;
	}

	public void setSuccess_num(String success_num) {
		this.success_num = success_num;
	}

	public String getResult_details() {
		return result_details;
	}

	public void setResult_details(String result_details) {
		this.result_details = result_details;
	}

	public String getUnfreezed_datails() {
		return unfreezed_datails;
	}

	public void setUnfreezed_datails(String unfreezed_datails) {
		this.unfreezed_datails = unfreezed_datails;
	}

}
