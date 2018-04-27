package slfx.jp.pojo.dto.supplier.abe;

import slfx.jp.pojo.dto.BaseJpDTO;

/**
 * 
 * @类功能说明：ABE下单返回DTO
 * @类修改者：
 * @修改日期：
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @作者：tandeng
 * @创建时间：2014年7月31日下午4:56:43
 * @版本：V1.0
 *
 */
@SuppressWarnings("serial")
public class ABEOrderFlightDTO extends BaseJpDTO{
	
	/**
	 * PNR
	 */
	private String pnr;
	
	/**
	 * 预订单号
	private String subsOrderNo;
	 */
	
	
	/**
	 * PNR文本信息
	private String pnrText;
	 */
	
	/**
	 * Pat文本
	private String pataText;
	 */

	/**
	 * 乘机人数
	private Integer passengerCount;
	 */
	
	/**
	 * 订单金额
	private Double balanceMoney;
	 */
	
	/**
	 * 业务状态  （HK预订）
	private String status;
	 */
	
	/**
	 * 订单状态
	private String orderSt;
	 */
	
	/**
	 * 订单流程状态
	private String flowStep;
	 */
	
	/**
	 * 订单当前流程状态
	private String flowStatus;
	 */
	
	/**
	 * 修改标识
	private String modifyTag;
	 */
	
	/**
	 * 出票时限日期时间  年月日时分秒
	private Date ticketLimitDateTime;
	 */
	
	/**
	 * 错误描述
	private String errMsg;
	 */
	
	/**
	 * 平台订单号
	private String platformOrderNo;
	 */
	
	/**
	 * 平台交易流水号     如果为一站式，则为一站式平台流水号，用于与三方支付平台对账
	private String platformTransID;
	 */
	
	/**
	 * 向平台实付金额
	private Double totalPay;
	 */
	
	/**
	 * 平台备注
	private String platformRemark;
	 */
	
	/**
	 * 政策备注(用于保存一站式创建订单后返回的退改签规定)
	 * 接口没返回该字段
	private String policyRemark;
	 */

	public String getPnr() {
		return pnr;
	}

	public void setPnr(String pnr) {
		this.pnr = pnr;
	}

}
