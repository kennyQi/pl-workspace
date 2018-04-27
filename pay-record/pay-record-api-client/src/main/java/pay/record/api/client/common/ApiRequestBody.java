package pay.record.api.client.common;

import java.io.Serializable;
import java.util.Date;

/**
 * 
 * @类功能说明：API请求体
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @作者：yuqz
 * @创建时间：2015年12月1日下午3:17:26
 * @版本：V1.0
 *
 */
public abstract class ApiRequestBody implements Serializable {
	private static final long serialVersionUID = 1L;
	
	/**
	 * RSA密文 
	 */
	private String ciphertext;
	
	/**
	 * 请求来源IP
	 */
	private String fromProjectIP;
	
	/**
	 * 来源项目标识
	 * 0:票量直销
	 * 1：商旅分销
	 * 2：中智票量
	 * 3：票量分销
	 * 4：智行
	 * 5：旅行社
	 */
	private Integer fromProjectCode;
	
	/**
	 * 来源项目环境
	 * 0：开发
	 * 1：测试
	 * 2：生产
	 */
	private Integer fromProjectEnv;
	
	/**
	 * 订购人
	 */
	private String booker;
	
	/**
	 * 乘机人/游客，多个人之间用"|"分割
	 */
	private String passengers;
	
	/**
	 * 出发地
	 */
	private String startPlace;
	
	/**
	 * 目的地
	 */
	private String destination;
	
	/**
	 * 订单状态
	 */
	private String orderStatus;
	
	/**
	 * 支付状态
	 */
	private String payStatus;
	
	/**
	 * 支付平台
	 * 0—支付宝
	 */
	private String payPlatform;
	
	/**
	 * 支付时间
	 */
	private Date payTime;
	
	/**
	 * 备注
	 */
	private String remarks;
	
	/**
	 * 来源客户端类型 
	 * 0：移动端，1：pc端
	 */
	private String fromClientType;

	public String getCiphertext() {
		return ciphertext;
	}

	public void setCiphertext(String ciphertext) {
		this.ciphertext = ciphertext;
	}

	public String getFromProjectIP() {
		return fromProjectIP;
	}

	public void setFromProjectIP(String fromProjectIP) {
		this.fromProjectIP = fromProjectIP;
	}

	public Integer getFromProjectCode() {
		return fromProjectCode;
	}

	public void setFromProjectCode(Integer fromProjectCode) {
		this.fromProjectCode = fromProjectCode;
	}

	public Integer getFromProjectEnv() {
		return fromProjectEnv;
	}

	public void setFromProjectEnv(Integer fromProjectEnv) {
		this.fromProjectEnv = fromProjectEnv;
	}

	public String getBooker() {
		return booker;
	}

	public void setBooker(String booker) {
		this.booker = booker;
	}

	public String getPassengers() {
		return passengers;
	}

	public void setPassengers(String passengers) {
		this.passengers = passengers;
	}

	public String getStartPlace() {
		return startPlace;
	}

	public void setStartPlace(String startPlace) {
		this.startPlace = startPlace;
	}

	public String getDestination() {
		return destination;
	}

	public void setDestination(String destination) {
		this.destination = destination;
	}

	public String getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}

	public String getPayStatus() {
		return payStatus;
	}

	public void setPayStatus(String payStatus) {
		this.payStatus = payStatus;
	}

	public String getPayPlatform() {
		return payPlatform;
	}

	public void setPayPlatform(String payPlatform) {
		this.payPlatform = payPlatform;
	}

	public Date getPayTime() {
		return payTime;
	}

	public void setPayTime(Date payTime) {
		this.payTime = payTime;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public String getFromClientType() {
		return fromClientType;
	}

	public void setFromClientType(String fromClientType) {
		this.fromClientType = fromClientType;
	}
}
