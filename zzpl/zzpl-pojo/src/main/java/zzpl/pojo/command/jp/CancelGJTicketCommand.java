package zzpl.pojo.command.jp;

import hg.common.component.BaseCommand;

import java.util.List;

@SuppressWarnings("serial")
public class CancelGJTicketCommand extends BaseCommand {

	/**
	 * 经销商订单号ID
	 */
	private String orderID;

	/**
	 * 经销商订单号
	 */
	private String orderNO;

	/**
	 * 申请种类(一次申请只能选择一种申请理由，多个乘客可以使用同一种种类)
	 * 
	 * 1. 当日作废 2. 自愿退票 3. 非自愿退票 4. 其他
	 */
	private Integer refundType;

	/**
	 * 申请理由(200字符以内）
	 */
	private String refundMemo;

	/**
	 * 乘客姓名
	 */
	private List<String> passengerNames;

	/**
	 * @类功能说明：需要退废票的乘客信息
	 * @类修改者：
	 * @修改日期：2015年7月18日下午10:35:58
	 * @修改说明：
	 * @公司名称：浙江汇购科技有限公司
	 * @作者：zhurz
	 * @创建时间：2015年7月18日下午10:35:58
	 */
	public static class Passenger {
		/**
		 * 乘客姓名
		 */
		private String passengerName;

		/**
		 * 证件类型
		 * 
		 * @see GJ#IDTYPE_MAP
		 */
		private Integer idType;

		/**
		 * 证件号
		 */
		private String idNo;

		public String getPassengerName() {
			return passengerName;
		}

		public void setPassengerName(String passengerName) {
			this.passengerName = passengerName;
		}

		public Integer getIdType() {
			return idType;
		}

		public void setIdType(Integer idType) {
			this.idType = idType;
		}

		public String getIdNo() {
			return idNo;
		}

		public void setIdNo(String idNo) {
			this.idNo = idNo;
		}
	}

	public String getOrderID() {
		return orderID;
	}

	public void setOrderID(String orderID) {
		this.orderID = orderID;
	}

	public String getOrderNO() {
		return orderNO;
	}

	public void setOrderNO(String orderNO) {
		this.orderNO = orderNO;
	}

	public Integer getRefundType() {
		return refundType;
	}

	public void setRefundType(Integer refundType) {
		this.refundType = refundType;
	}

	public String getRefundMemo() {
		return refundMemo;
	}

	public void setRefundMemo(String refundMemo) {
		this.refundMemo = refundMemo;
	}

	public List<String> getPassengerNames() {
		return passengerNames;
	}

	public void setPassengerNames(List<String> passengerNames) {
		this.passengerNames = passengerNames;
	}

}
