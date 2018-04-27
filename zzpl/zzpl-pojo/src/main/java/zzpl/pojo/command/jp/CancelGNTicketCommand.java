package zzpl.pojo.command.jp;

import hg.common.component.BaseCommand;

@SuppressWarnings("serial")
public class CancelGNTicketCommand extends BaseCommand {
	/**
	 * 经销商订单号ID
	 */
	private String orderID;
	/**
	 * 经销商订单号
	 */
	private String orderNO;

	/**
	 * 申请种类 1.当日作废2.自愿退票3.非自愿退票 4.差错退款 5.其他
	 */
	private String refundType;

	/***
	 * 申请理由
	 */
	private String refundMemo;

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

	public String getRefundType() {
		return refundType;
	}

	public void setRefundType(String refundType) {
		this.refundType = refundType;
	}

	public String getRefundMemo() {
		return refundMemo;
	}

	public void setRefundMemo(String refundMemo) {
		this.refundMemo = refundMemo;
	}

	// /**
	// * 经销商订单号ID
	// */
	// private String orderID;
	// /**
	// * 经销商订单号
	// */
	// private String orderNO;
	//
	// /**
	// * 乘客姓名
	// */
	// private List<String> passengerNames;
	//
	// /**
	// * 申请种类 1.当日作废2.自愿退票3.非自愿退票 4.差错退款 5.其他
	// */
	// private String refundType;
	//
	// /***
	// * 申请理由
	// */
	// private String refundMemo;
	//
	// public String getOrderID() {
	// return orderID;
	// }
	//
	// public void setOrderID(String orderID) {
	// this.orderID = orderID;
	// }
	//
	// public String getOrderNO() {
	// return orderNO;
	// }
	//
	// public void setOrderNO(String orderNO) {
	// this.orderNO = orderNO;
	// }
	//
	// public List<String> getPassengerNames() {
	// return passengerNames;
	// }
	//
	// public void setPassengerNames(List<String> passengerNames) {
	// this.passengerNames = passengerNames;
	// }
	//
	// public String getRefundType() {
	// return refundType;
	// }
	//
	// public void setRefundType(String refundType) {
	// this.refundType = refundType;
	// }
	//
	// public String getRefundMemo() {
	// return refundMemo;
	// }
	//
	// public void setRefundMemo(String refundMemo) {
	// this.refundMemo = refundMemo;
	// }

}