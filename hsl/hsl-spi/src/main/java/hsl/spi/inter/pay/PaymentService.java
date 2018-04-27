package hsl.spi.inter.pay;


/**
 * 支付服务，实现类在web工程
 *
 * @author zhurz
 * @since 1.7
 */
public interface PaymentService {

//	/**
//	 * 构建易行机票订单的支付表单HTML
//	 *
//	 * @return
//	 */

//	/**
//	 * 客户端用户id
//	 */
//	private String payerClientUserId;
//	/**
//	 * 商户订单编号（必填）
//	 */
//	private String clientTradeNo;
//	/**
//	 * 用户姓名
//	 */
//	private String name;
//	/**
//	 * 用户身份证号
//	 */
//	private String idCardNo;
//	/**
//	 * 用户手机号
//	 */
//	private String mobile;
//	/**
//	 * 金额
//	 */
//	private Double amount;

	String buildPayFormHtmlToYXJPOrder(String tradeNo);

}
