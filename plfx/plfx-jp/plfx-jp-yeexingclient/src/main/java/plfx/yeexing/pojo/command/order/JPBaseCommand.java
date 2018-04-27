package plfx.yeexing.pojo.command.order;

import hg.common.util.SysProperties;

import java.io.Serializable;

/**
 * 
 * @类功能说明：机票基础COMMAND
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @作者：yuqz
 * @创建时间：2015年6月30日下午2:31:38
 * @版本：V1.0
 *
 */
@SuppressWarnings("serial")
public class JPBaseCommand implements Serializable{

	/**
	 * 易行天下订单号（易行天下系统中唯一）一次只能传一个易行订单
	 */
	private String yeeXingOrderId;
	
	/**
	 * 订单状态
	 */
	private Integer status;				
	
	/**
	 * 订单支付状态
	 */
	private Integer payStatus;	
	
	/**
	 * 经销商订单号
	 */
	private String dealerOrderCode;
	
	/**
	 * 签名
	 * 所有参数经MD5加密算法后得出的结果
	 */
	private String sign;
	
	/**
	 * 经销商通知地址
	 */
	private String notifyUrl = SysProperties.getInstance().get("http_domain") + "/api/ticket/notify";
	
	/**
	 * 通知类型
	 * 1：出票成功通知
	 * 3：取消成功通知
	 * 4：退废票通知
	 * 6：拒绝出票通知
	 */
	private String type;

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getYeeXingOrderId() {
		return yeeXingOrderId;
	}

	public void setYeeXingOrderId(String yeeXingOrderId) {
		this.yeeXingOrderId = yeeXingOrderId;
	}

	public String getSign() {
		return sign;
	}

	public void setSign(String sign) {
		this.sign = sign;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Integer getPayStatus() {
		return payStatus;
	}

	public void setPayStatus(Integer payStatus) {
		this.payStatus = payStatus;
	}

	public String getDealerOrderCode() {
		return dealerOrderCode;
	}

	public void setDealerOrderCode(String dealerOrderCode) {
		this.dealerOrderCode = dealerOrderCode;
	}

	public String getNotifyUrl() {
		return notifyUrl;
	}

	public void setNotifyUrl(String notifyUrl) {
		this.notifyUrl = notifyUrl;
	}
}
