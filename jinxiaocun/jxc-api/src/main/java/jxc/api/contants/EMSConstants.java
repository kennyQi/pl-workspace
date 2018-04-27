package jxc.api.contants;

public interface EMSConstants {

	/**
	 * 入库单通知
	 */
	public static final String StockInNotice="WmsStockInNotice";
	
	/**
	 * 订单通知
	 */
	public static final String OrderNotice="WmsOrderNotice";
	
	/**
	 * 取消订单、出库单通知
	 */
	public static final String OrderCancelNotice="WmsOrderCancelNotice";
	
	/**
	 * 出库单通知
	 */
	public static final String StockOutNotice="WmsStockOutNotice";
	
	/**
	 * 取消入库单
	 */
	public static final String InstoreCancelNotice="WmsInstoreCancelNotice";
	
	
	
	
	
	//运单轨迹操作类型
	
	/**
	 * 收寄
	 */
	public static final String posting="00";
	/**
	 * 开拆
	 */
	public static final String opening="41";
	/**
	 * 封发
	 */
	public static final String dispatching="40";
	/**
	 * 安排投递
	 */
	public static final String delivery="50";
	/**
	 * 妥投（已签收）
	 */
	public static final String success="10";
	/**
	 * 未妥投（签收失败）
	 */
	public static final String fail="20";
}
