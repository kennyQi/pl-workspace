package plfx.yeexing.pojo.command.order;

import hg.common.component.BaseCommand;



/*****
 * 
 * @类功能说明：查询退票状态(只支持单人查询)
 * @类修改者：
 * @修改日期：
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @作者：yaosanfeng
 * @创建时间：2015年9月16日上午9:46:46
 * @版本：V1.0
 *
 */
@SuppressWarnings("serial")
public class JPRefundQueryOrderStatusSpiCommand extends BaseCommand {
    /***
     * 数据库订单的主键id
     */
	private String id;
	/**
	 * 订单号   
	 * 易行天下订单号（易行天下系统中唯一）一次只能传一个订单
	 */
	private String orderid;

	/**
	 * 乘客姓名   
	 * 
	 */
	private String passengername;

	/**
	 * 机票票号  (姓名 与票号二者必填其一)
	 * 并与姓名相对应
	 */
	private String airId;
	
	/***
	 * 合作伙伴用户名
	 */
	private String userName;
	
	/***
	 * 指定用户名
	 * 指定已签约的易行天下用户名
	 */
	private String appUserName;
	
	/***
	 * 签名  所有参数经MD5加密算法后得出的结果
	 */
	private String sign;
	
	/**
	 * 退废种类
	 * T:退废票订单
	 * C:取消的订单
	 * A:排除退废和取消的订单
	 */
    private String refundType;
    
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getRefundType() {
		return refundType;
	}
	public void setRefundType(String refundType) {
		this.refundType = refundType;
	}
	public String getAppUserName() {
		return appUserName;
	}
	public void setAppUserName(String appUserName) {
		this.appUserName = appUserName;
	}
	public String getOrderid() {
		return orderid;
	}
	public void setOrderid(String orderid) {
		this.orderid = orderid;
	}
	
	public String getPassengername() {
		return passengername;
	}
	public void setPassengername(String passengername) {
		this.passengername = passengername;
	}
	public String getAirId() {
		return airId;
	}
	public void setAirId(String airId) {
		this.airId = airId;
	}
	
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getSign() {
		return sign;
	}
	public void setSign(String sign) {
		this.sign = sign;
	}
	
	
	
}
