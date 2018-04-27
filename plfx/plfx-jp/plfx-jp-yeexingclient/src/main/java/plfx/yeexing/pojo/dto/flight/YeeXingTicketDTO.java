package plfx.yeexing.pojo.dto.flight;

import plfx.jp.pojo.dto.BaseJpDTO;

/**
 * 
 * @类功能说明：机票基本信息
 * @类修改者：
 * @修改日期：
 * @修改说明：继承BaseJpDTO
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @作者：yuxx
 * @创建时间：2014年7月31日下午4:55:02
 * @版本：V1.0
 *
 */
@SuppressWarnings("serial")
public class YeeXingTicketDTO extends BaseJpDTO{

	/**
	 * 所属订单
	 */
	private String orderId;

	/**
	 * 票号
	 */
	private String ticketNo;

	/**
	 * 乘客证件号
	 */
	private String idCardNo;

	/**
	 * 出票状态
	 */
	private Integer status;

	public final static Integer STATUS_SUCCESS = 1; // 出票成功
	public final static Integer STATUS_FAIL = 2; // 出票失败
	public final static Integer STATUS_PROCESS = 3; // 出票中

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getTicketNo() {
		return ticketNo;
	}

	public void setTicketNo(String ticketNo) {
		this.ticketNo = ticketNo;
	}

	public String getIdCardNo() {
		return idCardNo;
	}

	public void setIdCardNo(String idCardNo) {
		this.idCardNo = idCardNo;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

}
