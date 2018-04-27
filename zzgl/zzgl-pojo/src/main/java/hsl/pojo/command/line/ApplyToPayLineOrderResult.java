package hsl.pojo.command.line;

/**
 * @类功能说明：确认支付线路订单结果
 * @类修改者：
 * @修改日期：2015-10-13下午4:00:42
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @作者：zhurz
 * @创建时间：2015-10-13下午4:00:42
 */
public class ApplyToPayLineOrderResult {

	/**
	 * 支付预订金表单html
	 */
	public static final Integer RESULT_DOWN_PAYMENT_FORM_HTML = 1;
	/**
	 * 支付尾款表单html
	 */
	public static final Integer RESULT_FINAL_PAYMENT_FORM_HTML = 2;
	/**
	 * 无需支付
	 */
	public static final Integer RESULT_FREE = 3;

	/**
	 * 结果代码
	 */
	private Integer resultCode;

	/**
	 * 支付表单（用于跳转）
	 */
	private String formHtml;

	public Integer getResultCode() {
		return resultCode;
	}

	public void setResultCode(Integer resultCode) {
		this.resultCode = resultCode;
	}

	public String getFormHtml() {
		return formHtml;
	}

	public void setFormHtml(String formHtml) {
		this.formHtml = formHtml;
	}

}
