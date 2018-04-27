package hsl.payment.alipay.pojo;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 支付退款结果通知
 *
 * @author zhurz
 * @since 1.7
 */
public class PayRefundNotify {

	/**
	 * notify_time
	 * 通知发送的时间（yyyy-MM-dd HH:mm:ss）
	 */
	private Date notifyTime;
	/**
	 * batch_no
	 * 原请求退款批次号
	 */
	private String batchNo;
	/**
	 * success_num
	 * 退款成功总数
	 */
	private Integer successNum;
	/**
	 * result_details
	 * 处理结果详情
	 */
	private Map<String, ResultDetail> resultDetailMap;

	public PayRefundNotify() {
	}

	public PayRefundNotify(Map<String, String> alipayParams) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		setBatchNo(alipayParams.get("batch_no"));
		setSuccessNum(Integer.valueOf(alipayParams.get("success_num")));
		String[] strs = alipayParams.get("result_details").split("#");
		for (String str : strs) {
			String[] detail = str.split("[\\^\\|\\$]+");
			ResultDetail resultDetail = new ResultDetail(detail[0], Double.valueOf(detail[1]), "SUCCESS".equals(detail[2]));
			getResultDetailMap().put(resultDetail.getTradeNo(), resultDetail);
		}
		try {
			setNotifyTime(sdf.parse(alipayParams.get("notify_time")));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public Date getNotifyTime() {
		return notifyTime;
	}

	public void setNotifyTime(Date notifyTime) {
		this.notifyTime = notifyTime;
	}

	public String getBatchNo() {
		return batchNo;
	}

	public void setBatchNo(String batchNo) {
		this.batchNo = batchNo;
	}

	public Integer getSuccessNum() {
		return successNum;
	}

	public void setSuccessNum(Integer successNum) {
		this.successNum = successNum;
	}

	public Map<String, ResultDetail> getResultDetailMap() {
		if (resultDetailMap == null)
			resultDetailMap = new LinkedHashMap<String, ResultDetail>();
		return resultDetailMap;
	}

	public void setResultDetailMap(Map<String, ResultDetail> resultDetailMap) {
		this.resultDetailMap = resultDetailMap;
	}

	/**
	 * 处理结果详情
	 */
	public static class ResultDetail {
		/**
		 * 支付宝交易号
		 */
		private String tradeNo;
		/**
		 * 退款金额
		 */
		private Double amount;
		/**
		 * 是否成功
		 */
		private boolean success;

		public ResultDetail() {
		}

		public ResultDetail(String tradeNo, Double amount, boolean success) {
			this.tradeNo = tradeNo;
			this.amount = amount;
			this.success = success;
		}

		public String getTradeNo() {
			return tradeNo;
		}

		public void setTradeNo(String tradeNo) {
			this.tradeNo = tradeNo;
		}

		public Double getAmount() {
			return amount;
		}

		public void setAmount(Double amount) {
			this.amount = amount;
		}

		public boolean isSuccess() {
			return success;
		}

		public void setSuccess(boolean success) {
			this.success = success;
		}
	}

}
