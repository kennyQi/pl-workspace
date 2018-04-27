package hg.dzpw.pojo.api.alipay;

/**
 * 
 * @类功能说明：支付宝即时支付批量退款接口参数
 * @类修改者：
 * @修改日期：
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @作者：guotx
 * @创建时间：2016-3-10上午10:42:52
 * @版本：
 */
public class RefundFastParameter extends AliPayBaseParameter {
	// 基础参数
	/**
	 * 充退通知地址，可空
	 */
	private String dback_notify_url;
	// 业务参数
	/**
	 * 退款批次号，不可空，8位日期+3~24位流水号
	 */
	private String batch_no;

	/**
	 * 退款请求时间，不可空，格式yyyy-MM-dd hh:mm:ss
	 */
	private String refund_date;

	/**
	 * 退款总笔数，不可空
	 */
	private String batch_num;

	/**
	 * 单笔数据集，不可空
	 */
	private String detail_data;
	/**
	 * 是否使用冻结金额退款，可空，Y或N，默认N
	 */
	private String use_freeze_amount;

	/**
	 * 申请结果返回类型，可空默认xml
	 */
	private String return_type;

	public String getDback_notify_url() {
		return dback_notify_url;
	}

	public void setDback_notify_url(String dback_notify_url) {
		this.dback_notify_url = dback_notify_url;
	}

	public String getRefund_date() {
		return refund_date;
	}

	public void setRefund_date(String refund_date) {
		this.refund_date = refund_date;
	}

	public String getBatch_num() {
		return batch_num;
	}

	public void setBatch_num(String batch_num) {
		this.batch_num = batch_num;
	}

	public String getUse_freeze_amount() {
		return use_freeze_amount;
	}

	public void setUse_freeze_amount(String use_freeze_amount) {
		this.use_freeze_amount = use_freeze_amount;
	}

	public String getReturn_type() {
		return return_type;
	}

	public void setReturn_type(String return_type) {
		this.return_type = return_type;
	}

	public String getBatch_no() {
		return batch_no;
	}

	public void setBatch_no(String batch_no) {
		this.batch_no = batch_no;
	}

	public String getDetail_data() {
		return detail_data;
	}

	public void setDetail_data(String detail_data) {
		this.detail_data = detail_data;
	}

}
