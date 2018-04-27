package hg.payment.pojo.command.spi.payorder.hjb;


import javax.persistence.Column;

import hg.common.component.BaseCommand;
import hg.payment.pojo.command.dto.PayOrderRequestDTO;

/**
 * 
 * 
 *@类功能说明：商城请求创建汇金宝订单
 *@类修改者：
 *@修改日期：
 *@修改说明：
 *@公司名称：浙江汇购科技有限公司
 *@部门：技术部
 *@作者：luoyun
 *@创建时间：2014年11月17日下午3:42:24
 *
 */
public class CreateHJBPayOrderCommand extends BaseCommand{

	private static final long serialVersionUID = 1L;
	
	/**
	 * 支付请求基本参数
	 */
	private PayOrderRequestDTO payOrderCreateDTO;
	
	/**
	 * 交易名称(必填)
	 */
	private String tradeName;

	/**
	 * 商品名称(必填)
	 */
	private String subject;
	
	/**
	 * 商品描述(必填)
	 */
	private String description;
	
	/**
	 * B2C商城唯一标识
	 */
	private String channelId;
	
	/**
	 * 收款商户编号
	 */
	private String merCode;
	
	/**
	 * 收款商户开户行银行类型
	 */
	private String payeeBankType;
	
	/**
	 * 收款商户银行开户名
	 */
	private String payeeBankAccountName;
	
	/**
     * 付款方银行类型
     */
	private String payerBankType;
	
	/**
	 * 付款方用户类型 
	 * 2:企业
	 * 1:个人
	 */
	private String customerType;
	
	/**
	 * 调用端IP
	 */
	private String callerIp;

	public PayOrderRequestDTO getPayOrderCreateDTO() {
		return payOrderCreateDTO;
	}

	public void setPayOrderCreateDTO(PayOrderRequestDTO payOrderCreateDTO) {
		this.payOrderCreateDTO = payOrderCreateDTO;
	}

	public String getTradeName() {
		return tradeName;
	}

	public void setTradeName(String tradeName) {
		this.tradeName = tradeName;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getChannelId() {
		return channelId;
	}

	public void setChannelId(String channelId) {
		this.channelId = channelId;
	}

	public String getCustomerType() {
		return customerType;
	}

	public void setCustomerType(String customerType) {
		this.customerType = customerType;
	}

	public String getMerCode() {
		return merCode;
	}

	public void setMerCode(String merCode) {
		this.merCode = merCode;
	}

	public String getPayeeBankType() {
		return payeeBankType;
	}

	public void setPayeeBankType(String payeeBankType) {
		this.payeeBankType = payeeBankType;
	}

	public String getPayeeBankAccountName() {
		return payeeBankAccountName;
	}

	public void setPayeeBankAccountName(String payeeBankAccountName) {
		this.payeeBankAccountName = payeeBankAccountName;
	}

	public String getPayerBankType() {
		return payerBankType;
	}

	public void setPayerBankType(String payerBankType) {
		this.payerBankType = payerBankType;
	}

	public String getCallerIp() {
		return callerIp;
	}

	public void setCallerIp(String callerIp) {
		this.callerIp = callerIp;
	}

	
	
	
	
	

}
