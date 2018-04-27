package plfx.yxgjclient.pojo.param;

/**
 * 查询政策信息结果列表
 * @author guotx
 * 2015-07-10
 */
public class QueryPolicyStateResult {
	/**
	 * 政策是否可用
	 * 1可用 0不可用
	 */
	private String isEnable;
	/**
	 * 修改时间
	 * yyyy-MM-dd HH:mm:ss
	 */
	private String modiTime;
	/**
	 * 基础返点
	 */
	private String saleBasicDisc;
	/**
	 * 奖励返点
	 */
	private String saleExtraDisc;
	/**
	 * 开票费
	 */
	private String outTicketMoney;
	public String getIsEnable() {
		return isEnable;
	}
	public void setIsEnable(String isEnable) {
		this.isEnable = isEnable;
	}
	public String getModiTime() {
		return modiTime;
	}
	public void setModiTime(String modiTime) {
		this.modiTime = modiTime;
	}
	public String getSaleBasicDisc() {
		return saleBasicDisc;
	}
	public void setSaleBasicDisc(String saleBasicDisc) {
		this.saleBasicDisc = saleBasicDisc;
	}
	public String getSaleExtraDisc() {
		return saleExtraDisc;
	}
	public void setSaleExtraDisc(String saleExtraDisc) {
		this.saleExtraDisc = saleExtraDisc;
	}
	public String getOutTicketMoney() {
		return outTicketMoney;
	}
	public void setOutTicketMoney(String outTicketMoney) {
		this.outTicketMoney = outTicketMoney;
	}
}
