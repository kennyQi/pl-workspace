package hsl.pojo.command.line;

import hg.common.component.BaseCommand;
import hsl.pojo.util.HSLConstants;

import java.util.ArrayList;
import java.util.List;

/**
 * @类功能说明：确认支付订单
 * @类修改者：
 * @修改日期：2015-10-12下午4:29:06
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @作者：zhurz
 * @创建时间：2015-10-12下午4:29:06
 */
@SuppressWarnings("serial")
public class ApplyToPayLineOrderCommand extends BaseCommand implements HSLConstants.LineOrderPayType {

	/**
	 * 用户ID
	 */
	private String userId;

	/**
	 * 线路订单ID
	 */
	private String lineOrderId;

	/**
	 * 支付类型
	 * 
	 * @see HSLConstants.LineOrderPayType
	 */
	private Integer payType;

	/**
	 * 使用的卡券id
	 */
	private List<String> couponIds;

	/**
	 * 支付表单HTML构造器
	 */
	private PaymentFormHtmlBuilder paymentFormHtmlBuilder;
	
	/**
	 * 订单使用余额
	 */
	private Double balanceMoney;

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getLineOrderId() {
		return lineOrderId;
	}

	public void setLineOrderId(String lineOrderId) {
		this.lineOrderId = lineOrderId;
	}

	public Double getBalanceMoney() {
		return balanceMoney;
	}

	public void setBalanceMoney(Double balanceMoney) {
		this.balanceMoney = balanceMoney;
	}

	public Integer getPayType() {
		return payType;
	}

	public void setPayType(Integer payType) {
		this.payType = payType;
	}

	public List<String> getCouponIds() {
		if (couponIds == null)
			couponIds = new ArrayList<String>();
		return couponIds;
	}

	public void setCouponIds(List<String> couponIds) {
		this.couponIds = couponIds;
	}

	public PaymentFormHtmlBuilder getPaymentFormHtmlBuilder() {
		return paymentFormHtmlBuilder;
	}

	public void setPaymentFormHtmlBuilder(PaymentFormHtmlBuilder paymentFormHtmlBuilder) {
		this.paymentFormHtmlBuilder = paymentFormHtmlBuilder;
	}

	public static interface PaymentFormHtmlBuilder {
		/**
		 * @throws Exception 
		 * @方法功能说明：创建支付宝支付信息表单HTML
		 * @修改者名字：zhurz
		 * @修改时间：2015-10-12下午5:51:39
		 * @修改内容：
		 * @参数：@param out_trade_no 订单号
		 * @参数：@param subject 主题
		 * @参数：@param total_fee 总支付金额
		 *  @参数：@param suffix 区分哪个项目
		 * @参数：@return
		 * @return:String
		 * @throws
		 */
		String buildAlipayFormHtml(String out_trade_no, String subject, double total_fee,String suffix) throws Exception;
	}
}
