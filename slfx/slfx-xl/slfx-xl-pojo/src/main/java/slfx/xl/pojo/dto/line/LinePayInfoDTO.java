package slfx.xl.pojo.dto.line;

import slfx.xl.pojo.dto.EmbeddDTO;

/**
 * 
 * 
 *@类功能说明：线路支付信息DTO
 *@类修改者：
 *@修改日期：
 *@修改说明：
 *@公司名称：浙江汇购科技有限公司
 *@部门：技术部
 *@作者：luoyun
 *@创建时间：2014年12月8日上午9:11:03
 *
 */
@SuppressWarnings("serial")
public class LinePayInfoDTO extends EmbeddDTO{

	/**
	 * 订金支付比例
	 */
	private Double downPayment;

	/**
	 * 需付全款提前天数
	 */
	private Integer payTotalDaysBeforeStart;
	
	/**
	 * 最晚付款时间出发日期前
	 */
	private Integer lastPayTotalDaysBeforeStart;

	public Double getDownPayment() {
		return downPayment;
	}

	public void setDownPayment(Double downPayment) {
		this.downPayment = downPayment;
	}

	public Integer getPayTotalDaysBeforeStart() {
		return payTotalDaysBeforeStart;
	}

	public void setPayTotalDaysBeforeStart(Integer payTotalDaysBeforeStart) {
		this.payTotalDaysBeforeStart = payTotalDaysBeforeStart;
	}

	public Integer getLastPayTotalDaysBeforeStart() {
		return lastPayTotalDaysBeforeStart;
	}

	public void setLastPayTotalDaysBeforeStart(Integer lastPayTotalDaysBeforeStart) {
		this.lastPayTotalDaysBeforeStart = lastPayTotalDaysBeforeStart;
	}

	

	
	
	
	
}
