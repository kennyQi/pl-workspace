package hsl.payment.alipay.pojo;

import java.util.Date;

/**
 * 支付退款请求
 *
 * @author zhurz
 * @since 1.7
 */
public class PayRefundRequest {

	/**
	 * 服务器异步通知路径
	 * 需http://格式的完整路径，不能加?id=123这类自定义参数
	 * 必填，不能修改
	 */
	private String notifyUrl;

	/**
	 * batch_no
	 * 退款批次号。格式为：退款日期（8位当天日期）+流水号（3～24位，不能接受“000”，但是可以接受英文字符）
	 */
	private String batchNo;

	/**
	 * refund_date
	 * 退款请求时间（yyyy-MM-dd HH:mm:ss）
	 */
	private Date refundDate;

	/**
	 * batch_num
	 * 退款总笔数，即参数detail_data的值中，“#”字符出现的数量加1，最大支持1000笔（即“#”字符出现的数量999个）
	 */
	private Integer batchNum;

	/**
	 * detail_data
	 * 单笔数据集
	 * 格式为：第一笔交易#第二笔交易#第三笔交易...#第N笔交易
	 * 交易格式为：原付款支付宝交易号^退款总金额^退款理由
	 * “退款理由”长度不能大于256字节；
	 * “退款理由”中不能有“^”、“|”、“$”、“#”等影响detail_data的格式的特殊字符；
	 * “退款总金额”不能大于交易总金额；
	 * 一笔交易可以多次退款，退款次数最多不能超过99次，需要遵守多次退款的总金额不超过该笔交易付款金额的原则；
	 * 不允许包含两条交易号相同的退款明细，同一条退款明细（第N笔交易）中不允许存在两条转出人和转入人都相同的退分润信息。
	 */
	private String detailData;

	public String getNotifyUrl() {
		return notifyUrl;
	}

	public void setNotifyUrl(String notifyUrl) {
		this.notifyUrl = notifyUrl;
	}

	public String getBatchNo() {
		return batchNo;
	}

	public void setBatchNo(String batchNo) {
		this.batchNo = batchNo;
	}

	public Date getRefundDate() {
		return refundDate;
	}

	public void setRefundDate(Date refundDate) {
		this.refundDate = refundDate;
	}

	public Integer getBatchNum() {
		return batchNum;
	}

	public void setBatchNum(Integer batchNum) {
		this.batchNum = batchNum;
	}

	public String getDetailData() {
		return detailData;
	}

	public void setDetailData(String detailData) {
		this.detailData = detailData;
	}
}
