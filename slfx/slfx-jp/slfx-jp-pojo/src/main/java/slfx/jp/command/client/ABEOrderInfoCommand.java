package slfx.jp.command.client;

/**
 * 
 * @类功能说明：ABE订单信息描述command
 * @类修改者：
 * @修改日期：
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @作者：tandeng
 * @创建时间：2014年7月31日下午5:05:08
 * @版本：V1.0
 *
 */
public class ABEOrderInfoCommand {
	
	/**
	 * 联系人  必填
	 */
	private String linker;
	
	/**
	 * 送票地址  不设置
	 */
	private String address;
	
	/**
	 * 联系电话
	 */
	private String telephone;
	
	/**
	 * 国内/国际(D/I)  必填  目前只设置国内I
	 */
	private String isDomc;
	
	/**
	 * 出票时限   不设值
	 */
	private String ticketLimitDate;
	
	/**
	 * 出票时限   不设值
	 */
	private String ticketLimitTime;
	
	/**
	 * 支付平台   不设值
	 */
	private String payPlatform;
	
	/**
	 * 银行代码  不设值
	 */
	private String bankCode;
	
	/**
	 * 回调地址   不设值
	 */
	private String notifyURL;
	
	/**
	 * 通知类型  不设值
	 */
	private String notifyType;
	
	/**
	 * 订单备注  不设值
	 */
	private String remark;
	
	/**
	 * 结算价格   订单总金额
	 */
	private Double balanceMoney;

	/**
	 * 平台代码  不设值
	 */
	private String platformCode;
	
	/**
	 * 是否生成本地订单    不设值
	 */
	private String localOrder;
	
	/**
	 * 申请单号(避免重复预订唯一Key值)   不设值
	 */
	private String reqOrderNo;
	
	/**
	 * 分销用户编码 暂仅适用一站式    不设值
	 */
	private String spid;
	
	/**
	 * 授权Office列表   创建PNR同时向列表中的Office做授权 多个以”,”分隔  
	 * 不设值
	 */
	private String authOffices;

	public String getLinker() {
		return linker;
	}

	public void setLinker(String linker) {
		this.linker = linker;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public String getIsDomc() {
		return isDomc;
	}

	public void setIsDomc(String isDomc) {
		this.isDomc = isDomc;
	}

	public String getTicketLimitDate() {
		return ticketLimitDate;
	}

	public void setTicketLimitDate(String ticketLimitDate) {
		this.ticketLimitDate = ticketLimitDate;
	}

	public String getTicketLimitTime() {
		return ticketLimitTime;
	}

	public void setTicketLimitTime(String ticketLimitTime) {
		this.ticketLimitTime = ticketLimitTime;
	}

	public String getPayPlatform() {
		return payPlatform;
	}

	public void setPayPlatform(String payPlatform) {
		this.payPlatform = payPlatform;
	}

	public String getBankCode() {
		return bankCode;
	}

	public void setBankCode(String bankCode) {
		this.bankCode = bankCode;
	}

	public String getNotifyURL() {
		return notifyURL;
	}

	public void setNotifyURL(String notifyURL) {
		this.notifyURL = notifyURL;
	}

	public String getNotifyType() {
		return notifyType;
	}

	public void setNotifyType(String notifyType) {
		this.notifyType = notifyType;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Double getBalanceMoney() {
		return balanceMoney;
	}

	public void setBalanceMoney(Double balanceMoney) {
		this.balanceMoney = balanceMoney;
	}

	public String getPlatformCode() {
		return platformCode;
	}

	public void setPlatformCode(String platformCode) {
		this.platformCode = platformCode;
	}

	public String getLocalOrder() {
		return localOrder;
	}

	public void setLocalOrder(String localOrder) {
		this.localOrder = localOrder;
	}

	public String getReqOrderNo() {
		return reqOrderNo;
	}

	public void setReqOrderNo(String reqOrderNo) {
		this.reqOrderNo = reqOrderNo;
	}

	public String getSpid() {
		return spid;
	}

	public void setSpid(String spid) {
		this.spid = spid;
	}

	public String getAuthOffices() {
		return authOffices;
	}

	public void setAuthOffices(String authOffices) {
		this.authOffices = authOffices;
	}
	
}
