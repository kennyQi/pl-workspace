package plfx.jp.pojo.system;

import java.util.HashMap;

/**
 * 
 * @类功能说明：支付记录类型常量类
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @作者：yuqz
 * @创建时间：2015年11月17日上午9:54:13
 * @版本：V1.0
 * 
 */
public class PayRecordConstants {
	/**
	 * 支付易行成功
	 */
	public final static Integer PAYRECORD_SUCCESS = 1;
	/**
	 * 支付易行失败
	 */
	public final static Integer PAYRECORD_FAIL = 2;

	/***
	 * 过程记录 
	 * 1经销商-->分销(把用户支付信息用command顺带过来保存记录) 2分销-->供应商(调自动扣款完记录支付信息保存)
	 * 3供应商-->分销(取消成功,退废成功记录和钱相关的信息记录保存) 4 经销商-->分销(用户退废成功通知分销记录和钱相关的信息)
	 */
	
	/***
	 * 1经销商-->分销   保存用户支付信息(用户在经销商支付的信息传到分销)
	 */
	public final static String DEALER_TO_DISTRIBUTOR_USER_PAYSUCCESS_RECORD = "1";

	/***
	 * 2分销-->供应商   调自动扣款成功保存支付信息
	 */
	public final static String DISTRIBUTOR_TO_SUPPLIER_PAYSUCCESS_RECORD = "2";
	
    /***
     * 3供应商-->分销  取消成功或退废成功供应商发通知分销保存退废信息
     */
	public final static String SUPPLIER_TO_DISTRIBUTOR_REFUNDSUCCESS_RECORD = "3";
	
    /***
     * 3供应商-->分销   取消成功供应商发通知分销保存退废信息
     */
//	public final static String SUPPLIER_TO_DISTRIBUTOR_CANCELSUCCESS_RECORD = "33";

	/***
	 * 4 经销商-->分销  用户退废成功通知分销保存退款信息
	 */
	public final static String DEALER_TO_DISTRIBUTOR_REFUNDSUCCESS_RECORD = "4";
	
	public static final String DEALER_TO_DISTRIBUTOR_USER_PAYSUCCESS_RECORD_VAL = "用户支付经销商成功";
	public static final String DISTRIBUTOR_TO_SUPPLIER_PAYSUCCESS_RECORD_VAL = "自动扣款供应商成功";  
	public static final String SUPPLIER_TO_DISTRIBUTOR_REFUNDSUCCESS_RECORD_VAL = "供应商退废票成功";
	public static final String SUPPLIER_TO_DISTRIBUTOR_CANCELSUCCESS_RECORD_VAL = "供应商取消成功"; 
	public static final String DEALER_TO_DISTRIBUTOR_REFUNDSUCCESS_RECORD_VAL = "用户退款成功"; 
 
	public final static HashMap<String,String> PAY_PROCESS_RECORD = new HashMap<String,String>();
	
	static {
		PAY_PROCESS_RECORD.put(DEALER_TO_DISTRIBUTOR_USER_PAYSUCCESS_RECORD, DEALER_TO_DISTRIBUTOR_USER_PAYSUCCESS_RECORD_VAL);
		PAY_PROCESS_RECORD.put(DISTRIBUTOR_TO_SUPPLIER_PAYSUCCESS_RECORD, DISTRIBUTOR_TO_SUPPLIER_PAYSUCCESS_RECORD_VAL);
		PAY_PROCESS_RECORD.put(SUPPLIER_TO_DISTRIBUTOR_REFUNDSUCCESS_RECORD, SUPPLIER_TO_DISTRIBUTOR_REFUNDSUCCESS_RECORD_VAL);
//		PAY_PROCESS_RECORD.put(SUPPLIER_TO_DISTRIBUTOR_CANCELSUCCESS_RECORD, SUPPLIER_TO_DISTRIBUTOR_CANCELSUCCESS_RECORD_VAL);
		PAY_PROCESS_RECORD.put(DEALER_TO_DISTRIBUTOR_REFUNDSUCCESS_RECORD, DEALER_TO_DISTRIBUTOR_REFUNDSUCCESS_RECORD_VAL);	
		}


}
