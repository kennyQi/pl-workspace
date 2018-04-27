package plfx.jd.pojo.dto.ylclient.hotel;

import java.io.Serializable;
import java.math.BigDecimal;

import plfx.jd.pojo.system.enumConstants.EnumCurrencyCode;
import plfx.jd.pojo.system.enumConstants.EnumValidateResult;

/**
 * 
 * @类功能说明：酒店数据验证提交订单前调用后反馈信息
 * @类修改者：
 * @修改日期：
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @作者：yaosanfeng
 * @创建时间：2015年3月26日上午10:48:54
 * @版本：V1.0
 * 
 */
@SuppressWarnings("serial")
public class HotelDataValidateResultDTO implements Serializable {
	/**
	 * 验证结果
	 */
	protected EnumValidateResult resultCode;
	/**
	 * 担保金额 Decimal 如果是担保订单才有这个值
	 */
	protected BigDecimal guaranteeRate;
	/**
	 * 最晚取消时间
	 */
	protected java.util.Date cancelTime;
	/**
	 * 货币类型
	 */
	protected EnumCurrencyCode currencyCode;
	/**
	 * 具体结果信息
	 */
	protected String errorMessage;

	public EnumValidateResult getResultCode() {
		return resultCode;
	}

	public void setResultCode(EnumValidateResult resultCode) {
		this.resultCode = resultCode;
	}

	public BigDecimal getGuaranteeRate() {
		return guaranteeRate;
	}

	public void setGuaranteeRate(BigDecimal guaranteeRate) {
		this.guaranteeRate = guaranteeRate;
	}

	public java.util.Date getCancelTime() {
		return cancelTime;
	}

	public void setCancelTime(java.util.Date cancelTime) {
		this.cancelTime = cancelTime;
	}

	public EnumCurrencyCode getCurrencyCode() {
		return currencyCode;
	}

	public void setCurrencyCode(EnumCurrencyCode currencyCode) {
		this.currencyCode = currencyCode;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

}
