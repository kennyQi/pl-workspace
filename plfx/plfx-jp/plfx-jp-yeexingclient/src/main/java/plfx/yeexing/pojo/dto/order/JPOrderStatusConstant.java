package plfx.yeexing.pojo.dto.order;



import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.Embeddable;

import org.apache.commons.collections.KeyValue;

import plfx.jp.pojo.system.Attr;

/**
 * 
 * @类功能说明：订单状态
 * 用户：
 * 待确认               USER_PAY_WAIT
 * 已取消	    USER_TICKET_CANCEL
 * 出票处理中       USER_TICKET_DEALING
 * 出票失败           USER_TICKET_FAIL
 * 已出票               USER_TICKET_SUCC
 * 退票处理中       USER_TICKET_REFUND_DEALING
 * 退票失败           USER_TICKET_REFUND_FAIL
 * 退票成功           USER_TICKET_REFUND_SUCC
 * 
 * 
 * 未支付            USER_TICKET_NO_PAY
 * 已支付            USER_TICKET_PAY_SUCC
 * 待退款            USER_TICKET_REBACK_WAIT
 * 已退款            USER_TICKET_REBACK_SUCC
 * 
 * 
 * 
 * 商城
 * 待确认               SHOP_PAY_WAIT
 * 已取消		SHOP_TICKET_CANCEL
 * 出票处理中       SHOP_TICKET_DEALING
 * 出票失败           SHOP_TICKET_FAIL
 * 已出票               SHOP_TICKET_SUCC
 * 退/废处理中       SHOP_TICKET_REFUND_DEALING
 * 退/废失败           SHOP_TICKET_REFUND_FAIL
 * 退/废成功           SHOP_TICKET_REFUND_SUCC
 * 
 * 
 * 待支付            SHOP_TICKET_NO_PAY
 * 已支付            SHOP_TICKET_PAY_SUCC
 * 已收款           SHOP_TICKET_RECEIVE_PAYMENT_SUCC
 * 待回款           SHOP_TICKET_TO_BE_BACK_WAIT
 * 已回款           SHOP_TICKET_TO_BE_BACK_SUCC
 * 待退款            SHOP_TICKET_REBACK_WAIT
 * 已退款            SHOP_TICKET_REBACK_SUCC
 * 
 * 分销
 * 待确认               PLFX_PAY_WAIT
 * 已取消		PLFX_TICKET_CANCEL
 * 出票处理中       PLFX_TICKET_DEALING
// * 出票待重试       PLFX_TICKET_TRY_AGAIN
 * 出票失败           PLFX_TICKET_FAIL
 * 已出票               PLFX_TICKET_SUCC
 * 退/废处理中       PLFX_TICKET_REFUND_DEALING
 * 退/废失败           PLFX_TICKET_REFUND_FAIL
 * 退/废成功           PLFX_TICKET_REFUND_SUCC
 * 
 * 待支付             PLFX_TICKET_NO_PAY
 * 已支付             PLFX_TICKET_PAY_SUCC
 * 已收款            PLFX_TICKET_RECEIVE_PAYMENT_SUCC
 * 待回款            PLFX_TICKET_TO_BE_BACK_WAIT
 * 已回款            PLFX_TICKET_TO_BE_BACK_SUCC
 * 待退款             PLFX_TICKET_REBACK_WAIT
 * 已退款             PLFX_TICKET_REBACK_SUCC
 * 
 	
         
         
	
	
 * @类修改者：
 * @修改日期：
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @作者：qiuxianxiang
 * @创建时间：2015年5月30日上午8:47:53
 * @版本：V1.0
 *
 */
@Embeddable
public class JPOrderStatusConstant implements  Serializable{

	/**
	 * @FieldsserialVersionUID:TODO
	 */
	private static final long serialVersionUID = 4903467088416374159L;


	public JPOrderStatusConstant(Integer status) {
		setStatus(status);
	}

	public JPOrderStatusConstant() {
		super();
		// TODO Auto-generated constructor stub
	}

	public static final String USER_PAY_WAIT = "1";
	public static final String USER_TICKET_CANCEL = "2";
	public static final String USER_TICKET_DEALING = "3";
	public static final String USER_TICKET_FAIL = "4";
	public static final String USER_TICKET_SUCC = "5";
	public static final String USER_TICKET_REFUND_DEALING = "6";
	public static final String USER_TICKET_REFUND_FAIL = "7";
	public static final String USER_TICKET_REFUND_SUCC = "8";
	
	
	public static final String USER_TICKET_NO_PAY = "9";
	public static final String USER_TICKET_PAY_SUCC = "10";
	public static final String USER_TICKET_REBACK_WAIT = "11";
	public static final String USER_TICKET_REBACK_SUCC = "12";
	
	
	
	
	
	public static final String SHOP_PAY_WAIT = "13";
	public static final String SHOP_TICKET_CANCEL = "14";
	public static final String SHOP_TICKET_DEALING = "15";
	public static final String SHOP_TICKET_FAIL = "16";
	public static final String SHOP_TICKET_SUCC = "17";
	public static final String SHOP_TICKET_REFUND_DEALING = "18";
	public static final String SHOP_TICKET_REFUND_FAIL = "19";
	public static final String SHOP_TICKET_REFUND_SUCC	 = "20";
	
	
	public static final String SHOP_TICKET_NO_PAY = "21";
	public static final String SHOP_TICKET_PAY_SUCC = "22";
	public static final String SHOP_TICKET_RECEIVE_PAYMENT_SUCC = "23";
	public static final String SHOP_TICKET_TO_BE_BACK_WAIT = "24";
	public static final String SHOP_TICKET_TO_BE_BACK_SUCC = "25";
	public static final String SHOP_TICKET_REBACK_WAIT = "26";
	public static final String SHOP_TICKET_REBACK_SUCC = "27";
	
	
	
	public static final String PLFX_PAY_WAIT = "28";
	public static final String PLFX_TICKET_CANCEL = "29";
	public static final String PLFX_TICKET_DEALING = "30";
//	public static final String PLFX_TICKET_TRY_AGAIN = "31";
	public static final String PLFX_TICKET_FAIL = "32";
	public static final String PLFX_TICKET_SUCC = "33";
	public static final String PLFX_TICKET_REFUND_DEALING = "34";
	public static final String PLFX_TICKET_REFUND_FAIL = "35";
	public static final String PLFX_TICKET_REFUND_SUCC = "36";
	
	
	
	public static final String PLFX_TICKET_NO_PAY = "37";
	public static final String PLFX_TICKET_PAY_SUCC = "38";
	public static final String PLFX_TICKET_RECEIVE_PAYMENT_SUCC = "39";
	public static final String PLFX_TICKET_TO_BE_BACK_WAIT = "40";
	public static final String PLFX_TICKET_TO_BE_BACK_SUCC = "41";
	public static final String PLFX_TICKET_REBACK_WAIT = "42";
	public static final String PLFX_TICKET_REBACK_SUCC = "43";
	

	public static final String USER_PAY_WAIT_VAL = "待确认";
	public static final String USER_TICKET_CANCEL_VAL = "已取消";
	public static final String USER_TICKET_DEALING_VAL = "出票处理中";
	public static final String USER_TICKET_FAIL_VAL = "出票失败";
	public static final String USER_TICKET_SUCC_VAL = "已出票";
	public static final String USER_TICKET_REFUND_DEALING_VAL = "退票处理中";
	public static final String USER_TICKET_REFUND_FAIL_VAL = "退票失败";
	public static final String USER_TICKET_REFUND_SUCC_VAL = "退票成功";
	
	
	public static final String USER_TICKET_NO_PAY_VAL = "未支付";
	public static final String USER_TICKET_PAY_SUCC_VAL = "已支付 ";
	public static final String USER_TICKET_REBACK_WAIT_VAL = "待退款";
	public static final String USER_TICKET_REBACK_SUCC_VAL = "已退款 ";
	
	
	public static final String SHOP_PAY_WAIT_VAL = "待确认";
	public static final String SHOP_TICKET_CANCEL_VAL = "已取消";
	public static final String SHOP_TICKET_DEALING_VAL = "出票处理中";
	public static final String SHOP_TICKET_FAIL_VAL = "出票失败";
	public static final String SHOP_TICKET_SUCC_VAL = "已出票";
	public static final String SHOP_TICKET_REFUND_DEALING_VAL = "退/废处理中";
	public static final String SHOP_TICKET_REFUND_FAIL_VAL = "退/废失败";
	public static final String SHOP_TICKET_REFUND_SUCC_VAL = "退/废成功";
	
	public static final String SHOP_TICKET_NO_PAY_VAL = "待支付";
	public static final String SHOP_TICKET_PAY_SUCC_VAL = "已支付";
	public static final String SHOP_TICKET_RECEIVE_PAYMENT_SUCC_VAL = "已收款";
	public static final String SHOP_TICKET_TO_BE_BACK_WAIT_VAL = "待回款";
	public static final String SHOP_TICKET_TO_BE_BACK_SUCC_VAL = "已回款";
	public static final String SHOP_TICKET_REBACK_WAIT_VAL = "待退款";
	public static final String SHOP_TICKET_REBACK_SUCC_VAL = "已退款";
	
	
	
	

	public static final String PLFX_PAY_WAIT_VAL = "待确认";
	public static final String PLFX_TICKET_CANCEL_VAL = "已取消";
	public static final String PLFX_TICKET_DEALING_VAL = "出票处理中";
//	public static final String PLFX_TICKET_TRY_AGAIN_VAL = "出票待重试";
	public static final String PLFX_TICKET_FAIL_VAL = "出票失败";
	public static final String PLFX_TICKET_SUCC_VAL = "已出票";
	public static final String PLFX_TICKET_REFUND_DEALING_VAL = "退/废处理中";
	public static final String PLFX_TICKET_REFUND_FAIL_VAL = "退/废失败";
	public static final String PLFX_TICKET_REFUND_SUCC_VAL = "退/废成功";
	
	public static final String PLFX_TICKET_NO_PAY_VAL = "待支付 ";
	public static final String PLFX_TICKET_PAY_SUCC_VAL = "已支付";
	public static final String PLFX_TICKET_RECEIVE_PAYMENT_SUCC_VAL = "已收款";
	public static final String PLFX_TICKET_TO_BE_BACK_WAIT_VAL = "待回款";
	public static final String PLFX_TICKET_TO_BE_BACK_SUCC_VAL = "已回款";
	public static final String PLFX_TICKET_REBACK_WAIT_VAL = "待退款";
	public static final String PLFX_TICKET_REBACK_SUCC_VAL = "已退款";
	

	
	
	public final static List<KeyValue> JPORDER_STATUS_LIST = new ArrayList<KeyValue>();
	static {
		JPORDER_STATUS_LIST.add(new Attr(USER_PAY_WAIT, USER_PAY_WAIT_VAL));
		JPORDER_STATUS_LIST.add(new Attr(USER_TICKET_CANCEL, USER_TICKET_CANCEL_VAL));
		JPORDER_STATUS_LIST.add(new Attr(USER_TICKET_DEALING, USER_TICKET_DEALING_VAL));
		JPORDER_STATUS_LIST.add(new Attr(USER_TICKET_FAIL, USER_TICKET_FAIL_VAL));
		JPORDER_STATUS_LIST.add(new Attr(USER_TICKET_SUCC, USER_TICKET_SUCC_VAL));
		
		JPORDER_STATUS_LIST.add(new Attr(USER_TICKET_REFUND_DEALING, USER_TICKET_REFUND_DEALING_VAL));
		JPORDER_STATUS_LIST.add(new Attr(USER_TICKET_REFUND_FAIL, USER_TICKET_REFUND_FAIL_VAL));
		JPORDER_STATUS_LIST.add(new Attr(USER_TICKET_REFUND_SUCC, USER_TICKET_REFUND_SUCC_VAL));
		
		
		JPORDER_STATUS_LIST.add(new Attr(USER_TICKET_NO_PAY, USER_TICKET_NO_PAY_VAL));
		JPORDER_STATUS_LIST.add(new Attr(USER_TICKET_PAY_SUCC, USER_TICKET_PAY_SUCC_VAL));
		JPORDER_STATUS_LIST.add(new Attr(USER_TICKET_REBACK_WAIT, USER_TICKET_REBACK_WAIT_VAL));
		JPORDER_STATUS_LIST.add(new Attr(USER_TICKET_REBACK_SUCC, USER_TICKET_REBACK_SUCC_VAL));
		
		
		JPORDER_STATUS_LIST.add(new Attr(SHOP_PAY_WAIT, SHOP_PAY_WAIT_VAL));
		JPORDER_STATUS_LIST.add(new Attr(SHOP_TICKET_CANCEL, SHOP_TICKET_CANCEL_VAL));
		JPORDER_STATUS_LIST.add(new Attr(SHOP_TICKET_DEALING,SHOP_TICKET_DEALING_VAL));
		JPORDER_STATUS_LIST.add(new Attr(SHOP_TICKET_FAIL, SHOP_TICKET_FAIL_VAL));
		JPORDER_STATUS_LIST.add(new Attr(SHOP_TICKET_SUCC, SHOP_TICKET_SUCC_VAL));
		
		JPORDER_STATUS_LIST.add(new Attr(SHOP_TICKET_REFUND_DEALING, SHOP_TICKET_REFUND_DEALING_VAL));
		JPORDER_STATUS_LIST.add(new Attr(SHOP_TICKET_REFUND_FAIL, SHOP_TICKET_REFUND_FAIL_VAL));
		JPORDER_STATUS_LIST.add(new Attr(SHOP_TICKET_REFUND_SUCC,SHOP_TICKET_REFUND_SUCC_VAL));
		JPORDER_STATUS_LIST.add(new Attr(SHOP_TICKET_NO_PAY, SHOP_TICKET_NO_PAY_VAL));
		JPORDER_STATUS_LIST.add(new Attr(SHOP_TICKET_PAY_SUCC, SHOP_TICKET_PAY_SUCC_VAL));
		
		JPORDER_STATUS_LIST.add(new Attr(SHOP_TICKET_RECEIVE_PAYMENT_SUCC,SHOP_TICKET_RECEIVE_PAYMENT_SUCC_VAL));
		JPORDER_STATUS_LIST.add(new Attr(SHOP_TICKET_TO_BE_BACK_WAIT, SHOP_TICKET_TO_BE_BACK_WAIT_VAL));
		JPORDER_STATUS_LIST.add(new Attr(SHOP_TICKET_TO_BE_BACK_SUCC,SHOP_TICKET_TO_BE_BACK_SUCC_VAL));
		JPORDER_STATUS_LIST.add(new Attr(SHOP_TICKET_REBACK_WAIT, SHOP_TICKET_REBACK_WAIT_VAL));
		JPORDER_STATUS_LIST.add(new Attr(SHOP_TICKET_REBACK_SUCC, SHOP_TICKET_REBACK_SUCC_VAL));
		
		
		
		
		JPORDER_STATUS_LIST.add(new Attr(PLFX_PAY_WAIT, PLFX_PAY_WAIT_VAL));
		JPORDER_STATUS_LIST.add(new Attr(PLFX_TICKET_CANCEL, PLFX_TICKET_CANCEL_VAL));
		JPORDER_STATUS_LIST.add(new Attr(PLFX_TICKET_DEALING, PLFX_TICKET_DEALING_VAL));
//		JPORDER_STATUS_LIST.add(new Attr(PLFX_TICKET_TRY_AGAIN, PLFX_TICKET_TRY_AGAIN_VAL));
		JPORDER_STATUS_LIST.add(new Attr(PLFX_TICKET_FAIL, PLFX_TICKET_FAIL_VAL));
		JPORDER_STATUS_LIST.add(new Attr(PLFX_TICKET_SUCC, PLFX_TICKET_SUCC_VAL));
		
		JPORDER_STATUS_LIST.add(new Attr(PLFX_TICKET_REFUND_DEALING, PLFX_TICKET_REFUND_DEALING_VAL));
		JPORDER_STATUS_LIST.add(new Attr(PLFX_TICKET_REFUND_FAIL, PLFX_TICKET_REFUND_FAIL_VAL));
		JPORDER_STATUS_LIST.add(new Attr(PLFX_TICKET_REFUND_SUCC, PLFX_TICKET_REFUND_SUCC_VAL));
		
		
		JPORDER_STATUS_LIST.add(new Attr(PLFX_TICKET_NO_PAY, PLFX_TICKET_NO_PAY_VAL));
		JPORDER_STATUS_LIST.add(new Attr(PLFX_TICKET_PAY_SUCC, PLFX_TICKET_PAY_SUCC_VAL));
		JPORDER_STATUS_LIST.add(new Attr(PLFX_TICKET_RECEIVE_PAYMENT_SUCC, PLFX_TICKET_RECEIVE_PAYMENT_SUCC_VAL));
		
		JPORDER_STATUS_LIST.add(new Attr(PLFX_TICKET_TO_BE_BACK_WAIT, PLFX_TICKET_TO_BE_BACK_WAIT_VAL));
		JPORDER_STATUS_LIST.add(new Attr(PLFX_TICKET_TO_BE_BACK_SUCC, PLFX_TICKET_TO_BE_BACK_SUCC_VAL));
		JPORDER_STATUS_LIST.add(new Attr(PLFX_TICKET_REBACK_WAIT, PLFX_TICKET_REBACK_WAIT_VAL));
		JPORDER_STATUS_LIST.add(new Attr(PLFX_TICKET_REBACK_SUCC, PLFX_TICKET_REBACK_SUCC_VAL));
		
	}
	
	//分销订单状态
	public final static List<KeyValue> PLFX_JPORDER_STATUS_LIST = new ArrayList<KeyValue>();
	static {
		PLFX_JPORDER_STATUS_LIST.add(new Attr(PLFX_PAY_WAIT, PLFX_PAY_WAIT_VAL));
		PLFX_JPORDER_STATUS_LIST.add(new Attr(PLFX_TICKET_CANCEL, PLFX_TICKET_CANCEL_VAL));
		PLFX_JPORDER_STATUS_LIST.add(new Attr(PLFX_TICKET_DEALING, PLFX_TICKET_DEALING_VAL));
//		JPORDER_STATUS_LIST.add(new Attr(PLFX_TICKET_TRY_AGAIN, PLFX_TICKET_TRY_AGAIN_VAL));
		PLFX_JPORDER_STATUS_LIST.add(new Attr(PLFX_TICKET_FAIL, PLFX_TICKET_FAIL_VAL));
		PLFX_JPORDER_STATUS_LIST.add(new Attr(PLFX_TICKET_SUCC, PLFX_TICKET_SUCC_VAL));
		
		PLFX_JPORDER_STATUS_LIST.add(new Attr(PLFX_TICKET_REFUND_DEALING, PLFX_TICKET_REFUND_DEALING_VAL));
		PLFX_JPORDER_STATUS_LIST.add(new Attr(PLFX_TICKET_REFUND_FAIL, PLFX_TICKET_REFUND_FAIL_VAL));
		PLFX_JPORDER_STATUS_LIST.add(new Attr(PLFX_TICKET_REFUND_SUCC, PLFX_TICKET_REFUND_SUCC_VAL));
		
	}
	//分销支付状态
		public final static List<KeyValue> PLFX_JPORDER_PAY_STATUS_LIST = new ArrayList<KeyValue>();
		static {
			PLFX_JPORDER_PAY_STATUS_LIST.add(new Attr(PLFX_TICKET_NO_PAY, PLFX_TICKET_NO_PAY_VAL));
			PLFX_JPORDER_PAY_STATUS_LIST.add(new Attr(PLFX_TICKET_PAY_SUCC, PLFX_TICKET_PAY_SUCC_VAL));
			PLFX_JPORDER_PAY_STATUS_LIST.add(new Attr(PLFX_TICKET_RECEIVE_PAYMENT_SUCC, PLFX_TICKET_RECEIVE_PAYMENT_SUCC_VAL));
			
			PLFX_JPORDER_PAY_STATUS_LIST.add(new Attr(PLFX_TICKET_TO_BE_BACK_WAIT, PLFX_TICKET_TO_BE_BACK_WAIT_VAL));
			PLFX_JPORDER_PAY_STATUS_LIST.add(new Attr(PLFX_TICKET_TO_BE_BACK_SUCC, PLFX_TICKET_TO_BE_BACK_SUCC_VAL));
			PLFX_JPORDER_PAY_STATUS_LIST.add(new Attr(PLFX_TICKET_REBACK_WAIT, PLFX_TICKET_REBACK_WAIT_VAL));
			PLFX_JPORDER_PAY_STATUS_LIST.add(new Attr(PLFX_TICKET_REBACK_SUCC, PLFX_TICKET_REBACK_SUCC_VAL));
		}
		//用户订单状态
		public final static List<KeyValue> USER_JPORDER_STATUS_LIST = new ArrayList<KeyValue>();
		static {
			USER_JPORDER_STATUS_LIST.add(new Attr(USER_PAY_WAIT, USER_PAY_WAIT_VAL));
			USER_JPORDER_STATUS_LIST.add(new Attr(USER_TICKET_CANCEL, USER_TICKET_CANCEL_VAL));
			USER_JPORDER_STATUS_LIST.add(new Attr(USER_TICKET_DEALING, USER_TICKET_DEALING_VAL));
			USER_JPORDER_STATUS_LIST.add(new Attr(USER_TICKET_FAIL, USER_TICKET_FAIL_VAL));
			USER_JPORDER_STATUS_LIST.add(new Attr(USER_TICKET_SUCC, USER_TICKET_SUCC_VAL));
			
			USER_JPORDER_STATUS_LIST.add(new Attr(USER_TICKET_REFUND_DEALING, USER_TICKET_REFUND_DEALING_VAL));
			USER_JPORDER_STATUS_LIST.add(new Attr(USER_TICKET_REFUND_FAIL, USER_TICKET_REFUND_FAIL_VAL));
			USER_JPORDER_STATUS_LIST.add(new Attr(USER_TICKET_REFUND_SUCC, USER_TICKET_REFUND_SUCC_VAL));
			
		}
		//用户支付状态
			public final static List<KeyValue> USER_JPORDER_PAY_STATUS_LIST = new ArrayList<KeyValue>();
			static {
				USER_JPORDER_PAY_STATUS_LIST.add(new Attr(USER_TICKET_NO_PAY, USER_TICKET_NO_PAY_VAL));
				USER_JPORDER_PAY_STATUS_LIST.add(new Attr(USER_TICKET_PAY_SUCC, USER_TICKET_PAY_SUCC_VAL));
				USER_JPORDER_PAY_STATUS_LIST.add(new Attr(USER_TICKET_REBACK_WAIT, USER_TICKET_REBACK_WAIT_VAL));
				USER_JPORDER_PAY_STATUS_LIST.add(new Attr(USER_TICKET_REBACK_SUCC, USER_TICKET_REBACK_SUCC_VAL));
			}
	
			//商城订单状态
			public final static List<KeyValue> SHOP_JPORDER_STATUS_LIST = new ArrayList<KeyValue>();
			static {
				SHOP_JPORDER_STATUS_LIST.add(new Attr(SHOP_PAY_WAIT, SHOP_PAY_WAIT_VAL));
				SHOP_JPORDER_STATUS_LIST.add(new Attr(SHOP_TICKET_CANCEL, SHOP_TICKET_CANCEL_VAL));
				SHOP_JPORDER_STATUS_LIST.add(new Attr(SHOP_TICKET_DEALING,SHOP_TICKET_DEALING_VAL));
				SHOP_JPORDER_STATUS_LIST.add(new Attr(SHOP_TICKET_FAIL, SHOP_TICKET_FAIL_VAL));
				SHOP_JPORDER_STATUS_LIST.add(new Attr(SHOP_TICKET_SUCC, SHOP_TICKET_SUCC_VAL));
				
				SHOP_JPORDER_STATUS_LIST.add(new Attr(SHOP_TICKET_REFUND_DEALING, SHOP_TICKET_REFUND_DEALING_VAL));
				SHOP_JPORDER_STATUS_LIST.add(new Attr(SHOP_TICKET_REFUND_FAIL, SHOP_TICKET_REFUND_FAIL_VAL));
				SHOP_JPORDER_STATUS_LIST.add(new Attr(SHOP_TICKET_REFUND_SUCC,SHOP_TICKET_REFUND_SUCC_VAL));
				
			}
			//商城支付状态
				public final static List<KeyValue> SHOP_JPORDER_PAY_STATUS_LIST = new ArrayList<KeyValue>();
				static {
					SHOP_JPORDER_PAY_STATUS_LIST.add(new Attr(SHOP_TICKET_NO_PAY, SHOP_TICKET_NO_PAY_VAL));
					SHOP_JPORDER_PAY_STATUS_LIST.add(new Attr(SHOP_TICKET_PAY_SUCC, SHOP_TICKET_PAY_SUCC_VAL));
					
					SHOP_JPORDER_PAY_STATUS_LIST.add(new Attr(SHOP_TICKET_RECEIVE_PAYMENT_SUCC,SHOP_TICKET_RECEIVE_PAYMENT_SUCC_VAL));
					SHOP_JPORDER_PAY_STATUS_LIST.add(new Attr(SHOP_TICKET_TO_BE_BACK_WAIT, SHOP_TICKET_TO_BE_BACK_WAIT_VAL));
					SHOP_JPORDER_PAY_STATUS_LIST.add(new Attr(SHOP_TICKET_TO_BE_BACK_SUCC,SHOP_TICKET_TO_BE_BACK_SUCC_VAL));
					SHOP_JPORDER_PAY_STATUS_LIST.add(new Attr(SHOP_TICKET_REBACK_WAIT, SHOP_TICKET_REBACK_WAIT_VAL));
					SHOP_JPORDER_PAY_STATUS_LIST.add(new Attr(SHOP_TICKET_REBACK_SUCC, SHOP_TICKET_REBACK_SUCC_VAL));
					
				}
		
		
		
	
	
	public final static HashMap<String,String> JPORDER_STATUS_MAP = new HashMap<String,String>();
	static {
		JPORDER_STATUS_MAP.put(USER_PAY_WAIT, USER_PAY_WAIT_VAL);
		JPORDER_STATUS_MAP.put(USER_TICKET_CANCEL, USER_TICKET_CANCEL_VAL);
		JPORDER_STATUS_MAP.put(USER_TICKET_DEALING, USER_TICKET_DEALING_VAL);
		JPORDER_STATUS_MAP.put(USER_TICKET_FAIL, USER_TICKET_FAIL_VAL);
		JPORDER_STATUS_MAP.put(USER_TICKET_SUCC, USER_TICKET_SUCC_VAL);
		
		JPORDER_STATUS_MAP.put(USER_TICKET_REFUND_DEALING, USER_TICKET_REFUND_DEALING_VAL);
		JPORDER_STATUS_MAP.put(USER_TICKET_REFUND_FAIL, USER_TICKET_REFUND_FAIL_VAL);
		JPORDER_STATUS_MAP.put(USER_TICKET_REFUND_SUCC, USER_TICKET_REFUND_SUCC_VAL);
		
		
		JPORDER_STATUS_MAP.put(USER_TICKET_NO_PAY, USER_TICKET_NO_PAY_VAL);
		JPORDER_STATUS_MAP.put(USER_TICKET_PAY_SUCC, USER_TICKET_PAY_SUCC_VAL);
		JPORDER_STATUS_MAP.put(USER_TICKET_REBACK_WAIT, USER_TICKET_REBACK_WAIT_VAL);
		JPORDER_STATUS_MAP.put(USER_TICKET_REBACK_SUCC, USER_TICKET_REBACK_SUCC_VAL);
		
		
		JPORDER_STATUS_MAP.put(SHOP_PAY_WAIT, SHOP_PAY_WAIT_VAL);
		JPORDER_STATUS_MAP.put(SHOP_TICKET_CANCEL, SHOP_TICKET_CANCEL_VAL);
		JPORDER_STATUS_MAP.put(SHOP_TICKET_DEALING,SHOP_TICKET_DEALING_VAL);
		JPORDER_STATUS_MAP.put(SHOP_TICKET_FAIL, SHOP_TICKET_FAIL_VAL);
		JPORDER_STATUS_MAP.put(SHOP_TICKET_SUCC, SHOP_TICKET_SUCC_VAL);
		
		JPORDER_STATUS_MAP.put(SHOP_TICKET_REFUND_DEALING,SHOP_TICKET_REFUND_DEALING_VAL);
		JPORDER_STATUS_MAP.put(SHOP_TICKET_REFUND_FAIL, SHOP_TICKET_REFUND_FAIL_VAL);
		JPORDER_STATUS_MAP.put(SHOP_TICKET_REFUND_SUCC,SHOP_TICKET_REFUND_SUCC_VAL);
		JPORDER_STATUS_MAP.put(SHOP_TICKET_NO_PAY, SHOP_TICKET_NO_PAY_VAL);
		JPORDER_STATUS_MAP.put(SHOP_TICKET_PAY_SUCC, SHOP_TICKET_PAY_SUCC_VAL);
		
		JPORDER_STATUS_MAP.put(SHOP_TICKET_RECEIVE_PAYMENT_SUCC,SHOP_TICKET_RECEIVE_PAYMENT_SUCC_VAL);
		JPORDER_STATUS_MAP.put(SHOP_TICKET_TO_BE_BACK_WAIT, SHOP_TICKET_TO_BE_BACK_WAIT_VAL);
		JPORDER_STATUS_MAP.put(SHOP_TICKET_TO_BE_BACK_SUCC,SHOP_TICKET_TO_BE_BACK_SUCC_VAL);
		JPORDER_STATUS_MAP.put(SHOP_TICKET_REBACK_WAIT, SHOP_TICKET_REBACK_WAIT_VAL);
		JPORDER_STATUS_MAP.put(SHOP_TICKET_REBACK_SUCC, SHOP_TICKET_REBACK_SUCC_VAL);
		
		
		
		
		JPORDER_STATUS_MAP.put(PLFX_PAY_WAIT, PLFX_PAY_WAIT_VAL);
		JPORDER_STATUS_MAP.put(PLFX_TICKET_CANCEL, PLFX_TICKET_CANCEL_VAL);
		JPORDER_STATUS_MAP.put(PLFX_TICKET_DEALING, PLFX_TICKET_DEALING_VAL);
//		JPORDER_STATUS_MAP.put(PLFX_TICKET_TRY_AGAIN, PLFX_TICKET_TRY_AGAIN_VAL);
		JPORDER_STATUS_MAP.put(PLFX_TICKET_FAIL, PLFX_TICKET_FAIL_VAL);
		JPORDER_STATUS_MAP.put(PLFX_TICKET_SUCC, PLFX_TICKET_SUCC_VAL);
		
		JPORDER_STATUS_MAP.put(PLFX_TICKET_REFUND_DEALING, PLFX_TICKET_REFUND_DEALING_VAL);
		JPORDER_STATUS_MAP.put(PLFX_TICKET_REFUND_FAIL, PLFX_TICKET_REFUND_FAIL_VAL);
		JPORDER_STATUS_MAP.put(PLFX_TICKET_REFUND_SUCC, PLFX_TICKET_REFUND_SUCC_VAL);
		
		
		JPORDER_STATUS_MAP.put(PLFX_TICKET_NO_PAY, PLFX_TICKET_NO_PAY_VAL);
		JPORDER_STATUS_MAP.put(PLFX_TICKET_PAY_SUCC, PLFX_TICKET_PAY_SUCC_VAL);
		JPORDER_STATUS_MAP.put(PLFX_TICKET_RECEIVE_PAYMENT_SUCC, PLFX_TICKET_RECEIVE_PAYMENT_SUCC_VAL);
		
		JPORDER_STATUS_MAP.put(PLFX_TICKET_TO_BE_BACK_WAIT, PLFX_TICKET_TO_BE_BACK_WAIT_VAL);
		JPORDER_STATUS_MAP.put(PLFX_TICKET_TO_BE_BACK_SUCC, PLFX_TICKET_TO_BE_BACK_SUCC_VAL);
		JPORDER_STATUS_MAP.put(PLFX_TICKET_REBACK_WAIT, PLFX_TICKET_REBACK_WAIT_VAL);
		JPORDER_STATUS_MAP.put(PLFX_TICKET_REBACK_SUCC, PLFX_TICKET_REBACK_SUCC_VAL);
	}
	//用户订单状态
	public final static HashMap<String,String> USER_JPORDER_STATUS_MAP = new HashMap<String,String>();
	static {
		USER_JPORDER_STATUS_MAP.put(USER_PAY_WAIT, USER_PAY_WAIT_VAL);
		USER_JPORDER_STATUS_MAP.put(USER_TICKET_CANCEL, USER_TICKET_CANCEL_VAL);
		USER_JPORDER_STATUS_MAP.put(USER_TICKET_DEALING, USER_TICKET_DEALING_VAL);
		USER_JPORDER_STATUS_MAP.put(USER_TICKET_FAIL, USER_TICKET_FAIL_VAL);
		USER_JPORDER_STATUS_MAP.put(USER_TICKET_SUCC, USER_TICKET_SUCC_VAL);
		USER_JPORDER_STATUS_MAP.put(USER_TICKET_REFUND_DEALING, USER_TICKET_REFUND_DEALING_VAL);
		USER_JPORDER_STATUS_MAP.put(USER_TICKET_REFUND_FAIL, USER_TICKET_REFUND_FAIL_VAL);
		USER_JPORDER_STATUS_MAP.put(USER_TICKET_REFUND_SUCC, USER_TICKET_REFUND_SUCC_VAL);
	}
	//用户支付状态
		public final static HashMap<String,String> USER_JPORDER_PATY_STATUS_MAP = new HashMap<String,String>();
		static {
			USER_JPORDER_PATY_STATUS_MAP.put(USER_TICKET_NO_PAY, USER_TICKET_NO_PAY_VAL);
			USER_JPORDER_PATY_STATUS_MAP.put(USER_TICKET_PAY_SUCC, USER_TICKET_PAY_SUCC_VAL);
			USER_JPORDER_PATY_STATUS_MAP.put(USER_TICKET_REBACK_WAIT, USER_TICKET_REBACK_WAIT_VAL);
			USER_JPORDER_PATY_STATUS_MAP.put(USER_TICKET_REBACK_SUCC, USER_TICKET_REBACK_SUCC_VAL);
		}
		
		//商城订单状态
		public final static HashMap<String,String> SHOP_JPORDER_STATUS_MAP = new HashMap<String,String>();
		static {
			SHOP_JPORDER_STATUS_MAP.put(SHOP_PAY_WAIT, SHOP_PAY_WAIT_VAL);
			SHOP_JPORDER_STATUS_MAP.put(SHOP_TICKET_CANCEL, SHOP_TICKET_CANCEL_VAL);
			SHOP_JPORDER_STATUS_MAP.put(SHOP_TICKET_DEALING,SHOP_TICKET_DEALING_VAL);
			SHOP_JPORDER_STATUS_MAP.put(SHOP_TICKET_FAIL, SHOP_TICKET_FAIL_VAL);
			JPORDER_STATUS_MAP.put(SHOP_TICKET_SUCC, SHOP_TICKET_SUCC_VAL);
			
			SHOP_JPORDER_STATUS_MAP.put(SHOP_TICKET_REFUND_DEALING, SHOP_TICKET_REFUND_DEALING_VAL);
			SHOP_JPORDER_STATUS_MAP.put(SHOP_TICKET_REFUND_FAIL, SHOP_TICKET_REFUND_FAIL_VAL);
			SHOP_JPORDER_STATUS_MAP.put(SHOP_TICKET_REFUND_SUCC,SHOP_TICKET_REFUND_SUCC_VAL);
		}
		//商城支付状态
			public final static HashMap<String,String> SHOP_JPORDER_PAY_STATUS_MAP = new HashMap<String,String>();
			static {
				SHOP_JPORDER_PAY_STATUS_MAP.put(SHOP_TICKET_NO_PAY, SHOP_TICKET_NO_PAY_VAL);
				SHOP_JPORDER_PAY_STATUS_MAP.put(SHOP_TICKET_PAY_SUCC, SHOP_TICKET_PAY_SUCC_VAL);
				
				SHOP_JPORDER_PAY_STATUS_MAP.put(SHOP_TICKET_RECEIVE_PAYMENT_SUCC,SHOP_TICKET_RECEIVE_PAYMENT_SUCC_VAL);
				SHOP_JPORDER_PAY_STATUS_MAP.put(SHOP_TICKET_TO_BE_BACK_WAIT, SHOP_TICKET_TO_BE_BACK_WAIT_VAL);
				SHOP_JPORDER_PAY_STATUS_MAP.put(SHOP_TICKET_TO_BE_BACK_SUCC,SHOP_TICKET_TO_BE_BACK_SUCC_VAL);
				SHOP_JPORDER_PAY_STATUS_MAP.put(SHOP_TICKET_REBACK_WAIT, SHOP_TICKET_REBACK_WAIT_VAL);
				SHOP_JPORDER_PAY_STATUS_MAP.put(SHOP_TICKET_REBACK_SUCC, SHOP_TICKET_REBACK_SUCC_VAL);
			}
			//分销订单状态
			public final static HashMap<String,String> PLFX_JPORDER_STATUS_MAP = new HashMap<String,String>();
			static {
				PLFX_JPORDER_STATUS_MAP.put(PLFX_PAY_WAIT, PLFX_PAY_WAIT_VAL);
				PLFX_JPORDER_STATUS_MAP.put(PLFX_TICKET_CANCEL, PLFX_TICKET_CANCEL_VAL);
				PLFX_JPORDER_STATUS_MAP.put(PLFX_TICKET_DEALING, PLFX_TICKET_DEALING_VAL);
//				JPORDER_STATUS_MAP.put(PLFX_TICKET_TRY_AGAIN, PLFX_TICKET_TRY_AGAIN_VAL);
				PLFX_JPORDER_STATUS_MAP.put(PLFX_TICKET_FAIL, PLFX_TICKET_FAIL_VAL);
				PLFX_JPORDER_STATUS_MAP.put(PLFX_TICKET_SUCC, PLFX_TICKET_SUCC_VAL);
				
				PLFX_JPORDER_STATUS_MAP.put(PLFX_TICKET_REFUND_DEALING, PLFX_TICKET_REFUND_DEALING_VAL);
				PLFX_JPORDER_STATUS_MAP.put(PLFX_TICKET_REFUND_FAIL, PLFX_TICKET_REFUND_FAIL_VAL);
				PLFX_JPORDER_STATUS_MAP.put(PLFX_TICKET_REFUND_SUCC, PLFX_TICKET_REFUND_SUCC_VAL);
			}
			//分销支付状态
				public final static HashMap<String,String> PLFX_JPORDER_PAY_STATUS_MAP = new HashMap<String,String>();
				static {
					PLFX_JPORDER_PAY_STATUS_MAP.put(PLFX_TICKET_NO_PAY, PLFX_TICKET_NO_PAY_VAL);
					PLFX_JPORDER_PAY_STATUS_MAP.put(PLFX_TICKET_PAY_SUCC, PLFX_TICKET_PAY_SUCC_VAL);
					PLFX_JPORDER_PAY_STATUS_MAP.put(PLFX_TICKET_RECEIVE_PAYMENT_SUCC, PLFX_TICKET_RECEIVE_PAYMENT_SUCC_VAL);
					
					PLFX_JPORDER_PAY_STATUS_MAP.put(PLFX_TICKET_TO_BE_BACK_WAIT, PLFX_TICKET_TO_BE_BACK_WAIT_VAL);
					PLFX_JPORDER_PAY_STATUS_MAP.put(PLFX_TICKET_TO_BE_BACK_SUCC, PLFX_TICKET_TO_BE_BACK_SUCC_VAL);
					PLFX_JPORDER_PAY_STATUS_MAP.put(PLFX_TICKET_REBACK_WAIT, PLFX_TICKET_REBACK_WAIT_VAL);
					PLFX_JPORDER_PAY_STATUS_MAP.put(PLFX_TICKET_REBACK_SUCC, PLFX_TICKET_REBACK_SUCC_VAL);
				}
		
	
	/**  平台订单状态与供应商订单状态的对应关系   */
	public final static HashMap<String,String> STATUS_KEY_RELATION = new HashMap<String,String>();
	public static final String YG_WAIT_PAY= "NW"; //新订单待支付
	public static final String YG_ALREADY_PAY= "PY";    //已支付
	public static final String YG_TEMPORARILY_TICKET= "ZP"; //暂不能出票
	public static final String YG_ALREADY_TICKET= "TP"; //已出票
	public static final String YG_WAIT_REFUND= "RW";   //出票失败待退款
	public static final String YG_ALREADY_REFUND= "RP";   //出票失败已退款
	public static final String YG_REFUND_SUCCESS="FR";//退款成功
	public static final String YG_REFUND_FAIL="RR";//退废失败
	public static final String YG_APPLY_FAIL="RF";//升舱失败
	public static final String YG_APPLY_REFUND_DISCARD= "AR";   //申请退/废票
	static {
//		STATUS_KEY_RELATION.put(WAIT_PAY, YG_WAIT_PAY);
//		STATUS_KEY_RELATION.put(ALREADY_PAY, YG_ALREADY_PAY);
//		STATUS_KEY_RELATION.put(TEMPORARILY_TICKET,YG_TEMPORARILY_TICKET);
//		STATUS_KEY_RELATION.put(ALREADY_TICKET, YG_ALREADY_TICKET);
//		
//		STATUS_KEY_RELATION.put(WAIT_REFUND, YG_WAIT_REFUND);
//		STATUS_KEY_RELATION.put(ALREADY_REFUND, YG_ALREADY_REFUND);
//		STATUS_KEY_RELATION.put(REFUND_SUCCESS, YG_REFUND_SUCCESS);
//		STATUS_KEY_RELATION.put(REFUND_FAIL, YG_REFUND_FAIL);
//		
//		STATUS_KEY_RELATION.put(APPLY_FAIL, YG_APPLY_FAIL);
//		STATUS_KEY_RELATION.put(APPLY_REFUND_DISCARD, YG_APPLY_REFUND_DISCARD);
	}
	
	
	
   /** 订单状态 */
   private Integer status;
   
   /** 支付状态 */
   private Integer payStatus;
   

	public JPOrderStatusConstant(Integer status,Integer payStatus) {
		super();
		setStatus(status);
		setPayStatus(payStatus);
}

	public static final String COMMON_TYPE = "0";
	public static final String EXCEPTION_TYPE = "1";
	
	public static final String COMMON_TYPE_STR = "普通订单";
	public static final String EXCEPTION_TYPE_STR = "异常订单";
	public final static HashMap<String,String> JPORDER_TYPE_MAP = new HashMap<String,String>();
	static {
		JPORDER_TYPE_MAP.put(COMMON_TYPE, COMMON_TYPE_STR);
		JPORDER_TYPE_MAP.put(EXCEPTION_TYPE, EXCEPTION_TYPE_STR);
	}
	
	/** 退废订单状态   */
	public static final Integer CATEGORY_REFUND = 0;// 废票
	public static final Integer CATEGORY_BACK = 1;  // 退票
	/** 退废订单状态   */
	public static final String CATEGORY_REFUND_VAL = "已废票";// 废票
	public static final String CATEGORY_BACK_VAL = "已退票";  // 退票
	
	
	/***
	 * 乘客类型map
	 */
	public static final String ADT = "1";
	public static final String CHIL = "2";  
	
	public static final String ADT_VAL = "成人";
	public static final String CHIL_VAL = "儿童";  
	public final static HashMap<String,String> PASSENGER_TYPE_MAP = new HashMap<String,String>();
	static {
		PASSENGER_TYPE_MAP.put(ADT, ADT_VAL);
		PASSENGER_TYPE_MAP.put(CHIL, CHIL_VAL);
	}
	
	
	
	/***
	 * 证件类型map
	 */
	//0-身份证，1-护照，2-军人证，3-台胞证，4-回乡证，5-文职证
	public static final String SFZ = "0";
	public static final String HZ = "1";  
	public static final String JRZ = "2";
	public static final String TBZ = "3"; 
	public static final String HXZ = "4";
	public static final String WZZ= "5";  
	public static final String SFZ_VAL = "身份证";
	public static final String HZ_VAL = "护照";  
	public static final String JRZ_VAL = "军人证";
	public static final String TBZ_VAL = "台胞证"; 
	public static final String HXZ_VAL = "回乡证";
	public static final String WZZ_VAL= "文职证";  
	public final static HashMap<String,String> DOCUMENT_TYPE_MAP = new HashMap<String,String>();
	static {
		DOCUMENT_TYPE_MAP.put(SFZ, SFZ_VAL);
		DOCUMENT_TYPE_MAP.put(HZ, HZ_VAL);
		DOCUMENT_TYPE_MAP.put(JRZ, JRZ_VAL);
		DOCUMENT_TYPE_MAP.put(TBZ, TBZ_VAL);
		DOCUMENT_TYPE_MAP.put(HXZ, HXZ_VAL);
		DOCUMENT_TYPE_MAP.put(WZZ, WZZ_VAL);	
	}

	
	/***
	 * 退废类型
	 * 1.	当日作废
       2.	自愿退票
       3.	非自愿退票
       4.	差错退款
       5.	其他

	 */
	public static final String DRZF = "1";
	public static final String ZYTP = "2";  
	public static final String FZYTP = "3";
	public static final String CCTK = "4";  
	public static final String QT = "5"; 
	
	public static final String DRZF_VAL = "当日作废";
	public static final String ZYTP_VAL  = "自愿退票";  
	public static final String FZYTP_VAL  = "非自愿退票";
	public static final String CCTK_VAL  = "差错退款";  
	public static final String QT_VAL  = "其他";
	
	public final static HashMap<String,String> REFUND_TYPE_MAP = new HashMap<String,String>();
	static {
		REFUND_TYPE_MAP.put(DRZF, DRZF_VAL);
		REFUND_TYPE_MAP.put(ZYTP, ZYTP_VAL);
		REFUND_TYPE_MAP.put(FZYTP, FZYTP_VAL);
		REFUND_TYPE_MAP.put(CCTK, CCTK_VAL);
		REFUND_TYPE_MAP.put(QT, QT_VAL);
	}
	
	
	public static final String TFDD = "T";//退废订单
	public static final String QXDD = "C";  //取消订单
	public static final String  REMOVETC= "A"; //除去退废和取消订单
	
	public static final String TFDD_VAL  = "退废记录订单";
	public static final String QXDD_VAL  = "取消记录订单";  
	public static final String REMOVETC_VAL  = "订单";
	
	public final static HashMap<String,String> JP_ORDER_TYPE = new HashMap<String,String>();
	static {
		JP_ORDER_TYPE.put(TFDD, TFDD_VAL);
		JP_ORDER_TYPE.put(QXDD, QXDD_VAL);
		JP_ORDER_TYPE.put(REMOVETC, REMOVETC_VAL);
		
	}
	/***
	 * 支付方式
	 * 支付宝
	 */
	public static final Integer ZFFS_ZFB = 1;
	
	//----------------------------------------------------------
    public final static String PAY_PLATFORM_ZFB = "0";
	
	public final static String PAY_PLATFORM_ZFB_STRING = "支付宝";
	
	/**
	 * 支付平台map
	 */
	public final static Map<String,String> PAY_PLATFORM_MAP = new HashMap<String,String>();
	static {
		PAY_PLATFORM_MAP.put( PAY_PLATFORM_ZFB, PAY_PLATFORM_ZFB_STRING);
	}
	
	public final static String FROM_PROJECT_PLZX = "1000";
	public final static String FROM_PROJECT_SLFX = "1001";
	public final static String FROM_PROJECT_ZZPL = "1002";
	public final static String FROM_PROJECT_PLFX = "1003";
	public final static String FROM_PROJECT_ZX = "1004";
	public final static String FROM_PROJECT_LXS = "1005";
	
	public final static String FROM_PROJECT_PLZX_STRING = "票量直销";
	public final static String FROM_PROJECT_SLFX_STRING = "商旅分销";
	public final static String FROM_PROJECT_ZZPL_STRING = "中智票量";
	public final static String FROM_PROJECT_PLFX_STRING = "票量分销";
	public final static String FROM_PROJECT_ZX_STRING = "智行";
	public final static String FROM_PROJECT_LXS_STRING = "旅行社";
	
	/**
	 * 来源项目标识map
	 */
	public final static Map<String,String> FROM_PROJECT_CODE_MAP = new HashMap<String,String>();
	static {
		FROM_PROJECT_CODE_MAP.put( FROM_PROJECT_PLZX, FROM_PROJECT_PLZX_STRING);
		FROM_PROJECT_CODE_MAP.put( FROM_PROJECT_SLFX, FROM_PROJECT_SLFX_STRING);
		FROM_PROJECT_CODE_MAP.put( FROM_PROJECT_ZZPL, FROM_PROJECT_ZZPL_STRING);
		FROM_PROJECT_CODE_MAP.put( FROM_PROJECT_PLFX, FROM_PROJECT_PLFX_STRING);
		FROM_PROJECT_CODE_MAP.put( FROM_PROJECT_ZX, FROM_PROJECT_ZX_STRING);
		FROM_PROJECT_CODE_MAP.put( FROM_PROJECT_LXS, FROM_PROJECT_LXS_STRING);
	}
	
	public final static String RECORD_TYEP_UZ = "1";
	public final static String RECORD_TYEP_FG = "2";
	public final static String RECORD_TYEP_GF = "3";
	public final static String RECORD_TYEP_FU = "4";
	public final static String RECORD_TYEP_ZF = "5";
	public final static String RECORD_TYEP_FZ = "6";
	
	public final static String RECORD_TYEP_UZ_STRING = "用户->直销";
	public final static String RECORD_TYEP_FG_STRING = "分销->供应商";
	public final static String RECORD_TYEP_GF_STRING = "供应商->分销";
	public final static String RECORD_TYEP_FU_STRING = "分销->用户";
	public final static String RECORD_TYEP_ZF_STRING = "直销->分销";
	public final static String RECORD_TYEP_FZ_STRING= "分销->直销";
	
	/**
	 * 记录类型map
	 */
	public final static Map<String,String> RECORD_TYEP_MAP = new HashMap<String,String>();
	static {
		RECORD_TYEP_MAP.put( RECORD_TYEP_UZ, RECORD_TYEP_UZ_STRING);
		RECORD_TYEP_MAP.put( RECORD_TYEP_FG, RECORD_TYEP_FG_STRING);
		RECORD_TYEP_MAP.put( RECORD_TYEP_GF, RECORD_TYEP_GF_STRING);
		RECORD_TYEP_MAP.put( RECORD_TYEP_FU, RECORD_TYEP_FU_STRING);
		RECORD_TYEP_MAP.put( RECORD_TYEP_ZF, RECORD_TYEP_ZF_STRING);
		RECORD_TYEP_MAP.put( RECORD_TYEP_FZ, RECORD_TYEP_FZ_STRING);
	}
	
	public final static String FROM_CLIENT_TYPE_MOBILE = "0";
	public final static String FROM_CLIENT_TYPE_PC = "1";
	
	public final static String FROM_CLIENT_TYPE_MOBILE_STRING = "移动端";
	public final static String FROM_CLIENT_TYPE_PC_STRING = "PC端";
	
	/**
	 * 来源客户端类型map
	 */
	public final static Map<String,String> FROM_CLIENT_TYPE = new HashMap<String,String>();
	static {
		FROM_CLIENT_TYPE.put( FROM_CLIENT_TYPE_MOBILE, FROM_CLIENT_TYPE_MOBILE_STRING);
		FROM_CLIENT_TYPE.put( FROM_CLIENT_TYPE_PC, FROM_CLIENT_TYPE_PC_STRING);
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

   
}