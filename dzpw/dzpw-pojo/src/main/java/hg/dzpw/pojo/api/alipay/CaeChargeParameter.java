package hg.dzpw.pojo.api.alipay;

/**
 * 
 * @类功能说明：支付宝代扣请求参数实体
 * @类修改者：
 * @修改日期：
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @作者：guotx
 * @创建时间：2016-2-29下午2:30:27
 * @版本：
 */
public class CaeChargeParameter extends AliPayBaseParameter{
	// 业务参数
	
	/** 商户订单号 不可空*/
	private String out_order_no;
	
	/** 金额，不可空，两位小数,单位元*/
	private String amount;
	
	/** 支付宝标题，不可空*/
	private String subject;
	
	/** 转出支付宝账号，partner+0156，也可使用支付宝登陆号，不可空*/
	private String trans_account_out;
	
	/** 转入支付宝账号，partner+0156，也可使用支付宝登陆号，不可空*/
	private String trans_account_in;
	
	/** 代扣模式，不可空
	 *  代扣时候传 trade
	 * */
	private String charge_type;
	
	/** 代理业务编号，parner+1000310004,不可空*/
	private String type_code;
	
	/** 商户订单创建时间 ，可空，格式必须为yyyy-MM-dd HH:mm:ss*/
	private String gmt_out_order_create;
	
	/** 提成类型，目前只支持给第三方提成，值为10，可空*/
	private String royalty_type;
	
	/** 提成信息集*/
	private String royalty_parameters;
	
	/** 操作员ID，可空*/
	private String operator_id;
	
	/** 操作员登录名*/
	private String operator_name;

	public String getOut_order_no() {
		return out_order_no;
	}

	public void setOut_order_no(String out_order_no) {
		this.out_order_no = out_order_no;
	}

	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getTrans_account_out() {
		return trans_account_out;
	}

	public void setTrans_account_out(String trans_account_out) {
		this.trans_account_out = trans_account_out;
	}

	public String getTrans_account_in() {
		return trans_account_in;
	}

	public void setTrans_account_in(String trans_account_in) {
		this.trans_account_in = trans_account_in;
	}

	public String getCharge_type() {
		return charge_type;
	}

	public void setCharge_type(String charge_type) {
		this.charge_type = charge_type;
	}

	public String getType_code() {
		return type_code;
	}

	public void setType_code(String type_code) {
		this.type_code = type_code;
	}

	public String getGmt_out_order_create() {
		return gmt_out_order_create;
	}

	public void setGmt_out_order_create(String gmt_out_order_create) {
		this.gmt_out_order_create = gmt_out_order_create;
	}

	public String getRoyalty_type() {
		return royalty_type;
	}

	public void setRoyalty_type(String royalty_type) {
		this.royalty_type = royalty_type;
	}

	public String getRoyalty_parameters() {
		return royalty_parameters;
	}

	public void setRoyalty_parameters(String royalty_parameters) {
		this.royalty_parameters = royalty_parameters;
	}

	public String getOperator_id() {
		return operator_id;
	}

	public void setOperator_id(String operator_id) {
		this.operator_id = operator_id;
	}

	public String getOperator_name() {
		return operator_name;
	}

	public void setOperator_name(String operator_name) {
		this.operator_name = operator_name;
	}
}
