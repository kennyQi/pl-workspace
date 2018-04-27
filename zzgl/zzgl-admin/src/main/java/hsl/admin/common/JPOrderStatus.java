package hsl.admin.common;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.apache.commons.collections.KeyValue;

/**
 * @类功能说明：订单状态:
 * @类修改者：
 * @修改日期：
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @作者：zhangka
 * @创建时间：2014年9月22日下午5:52:53
 * @版本：V1.0
 */
@SuppressWarnings("serial")
public class JPOrderStatus implements Serializable {

	public static final String PRE_CREATE = "1";
	public static final String ORDER_CLOSED = "2";
	public static final String PAY_WAIT = "3";
	public static final String TICKET_FAIL_NEED_REFUND = "4";
	public static final String TICKET_FAIL_REFUND = "5";
	
	public static final String APPLY_DISCARD = "7";
	public static final String DISCARD_SUCC = "8";
	public static final String DISCARD_SUCC_NOT_REFUND = "9";
	public static final String DISCARD_SUCC_REFUND = "10";
	
	public static final String DISCARD_FAIL = "11";
	public static final String APPLY_BACK = "12";
	public static final String BACK_SUCC = "13";
	public static final String BACK_SUCC_NOT_REFUND = "14";
	public static final String BACK_SUCC_REFUND = "15";
	
	public static final String BACK_FAIL = "16";
	public static final String TICKET_CANCEL = "17";
	public static final String PAY_SUCC = "18";
	public static final String PAY_TIMEOUT = "19";
	public static final String PAY_FAIL = "20";
	
	public static final String CREATE_FAIL = "21";
	public static final String TICKET_SUCC = "22";
	public static final String ASK_TICKET_SUCC = "23";

	public static final String PRE_CREATE_VAL = "待提交";
	public static final String ORDER_CLOSED_VAL = "订单关闭";
	public static final String PAY_WAIT_VAL = "待支付";
	public static final String TICKET_FAIL_NEED_REFUND_VAL = "出票失败待退款";
	public static final String TICKET_FAIL_REFUND_VAL = "出票失败已退款";

	
	public static final String APPLY_DISCARD_VAL = "申请废票";
	public static final String DISCARD_SUCC_VAL = "废票成功";
	public static final String DISCARD_SUCC_NOT_REFUND_VAL = "废票成功待退款";
	public static final String DISCARD_SUCC_REFUND_VAL = "废票成功已退款";

	public static final String DISCARD_FAIL_VAL = "已出票";//已出票(废票失败)
	public static final String APPLY_BACK_VAL = "申请退票";
	public static final String BACK_SUCC_VAL = "退票成功";
	public static final String BACK_SUCC_NOT_REFUND_VAL = "退票成功待退款";
	public static final String BACK_SUCC_REFUND_VAL = "退票成功已退款";
	
	public static final String BACK_FAIL_VAL = "已出票";//已出票(退票失败)	
	public static final String TICKET_CANCEL_VAL = "订单取消";
	public static final String PAY_SUCC_VAL = "支付成功";
	public static final String PAY_TIMEOUT_VAL = "收款超时";
	public static final String PAY_FAIL_VAL = "扣款失败";
	
	public static final String CREATE_FAIL_VAL = "下单失败";
	public static final String TICKET_SUCC_VAL = "出票成功";
	public static final String ASK_TICKET_SUCC_VAL = "申请出票成功";

	public final static List<KeyValue> JPORDER_STATUS_LIST = new ArrayList<KeyValue>();
	static {
		JPORDER_STATUS_LIST.add(new Attr(TICKET_CANCEL, TICKET_CANCEL_VAL));
		JPORDER_STATUS_LIST.add(new Attr(PRE_CREATE, PRE_CREATE_VAL));
		JPORDER_STATUS_LIST.add(new Attr(CREATE_FAIL, CREATE_FAIL_VAL));
		JPORDER_STATUS_LIST.add(new Attr(PAY_WAIT, PAY_WAIT_VAL));
		JPORDER_STATUS_LIST.add(new Attr(PAY_SUCC, PAY_SUCC_VAL));
		JPORDER_STATUS_LIST.add(new Attr(PAY_TIMEOUT, PAY_TIMEOUT_VAL));
		JPORDER_STATUS_LIST.add(new Attr(PAY_FAIL, PAY_FAIL_VAL));
		JPORDER_STATUS_LIST.add(new Attr(TICKET_FAIL_NEED_REFUND, TICKET_FAIL_NEED_REFUND_VAL));
		JPORDER_STATUS_LIST.add(new Attr(TICKET_FAIL_REFUND, TICKET_FAIL_REFUND_VAL));
		JPORDER_STATUS_LIST.add(new Attr(TICKET_SUCC, TICKET_SUCC_VAL));
		JPORDER_STATUS_LIST.add(new Attr(ASK_TICKET_SUCC, ASK_TICKET_SUCC_VAL));
		JPORDER_STATUS_LIST.add(new Attr(APPLY_DISCARD, APPLY_DISCARD_VAL));
		JPORDER_STATUS_LIST.add(new Attr(DISCARD_SUCC, DISCARD_SUCC_VAL));
		JPORDER_STATUS_LIST.add(new Attr(DISCARD_SUCC_NOT_REFUND, DISCARD_SUCC_NOT_REFUND_VAL));
		JPORDER_STATUS_LIST.add(new Attr(DISCARD_SUCC_REFUND, DISCARD_SUCC_REFUND_VAL));
		JPORDER_STATUS_LIST.add(new Attr(DISCARD_FAIL, DISCARD_FAIL_VAL));
		JPORDER_STATUS_LIST.add(new Attr(APPLY_BACK, APPLY_BACK_VAL));
		JPORDER_STATUS_LIST.add(new Attr(BACK_SUCC, BACK_SUCC_VAL));
		JPORDER_STATUS_LIST.add(new Attr(BACK_SUCC_NOT_REFUND, BACK_SUCC_NOT_REFUND_VAL));
		JPORDER_STATUS_LIST.add(new Attr(BACK_SUCC_REFUND, BACK_SUCC_REFUND_VAL));
		JPORDER_STATUS_LIST.add(new Attr(BACK_FAIL, BACK_FAIL_VAL));
		JPORDER_STATUS_LIST.add(new Attr(ORDER_CLOSED, ORDER_CLOSED_VAL));

	}

	public final static HashMap<String, String> JPORDER_STATUS_MAP = new HashMap<String, String>();
	static {
		JPORDER_STATUS_MAP.put(TICKET_CANCEL, TICKET_CANCEL_VAL);
		JPORDER_STATUS_MAP.put(PRE_CREATE, PRE_CREATE_VAL);
		JPORDER_STATUS_MAP.put(CREATE_FAIL, CREATE_FAIL_VAL);
		JPORDER_STATUS_MAP.put(PAY_WAIT, PAY_WAIT_VAL);
		JPORDER_STATUS_MAP.put(PAY_SUCC, PAY_SUCC_VAL);
		JPORDER_STATUS_MAP.put(PAY_TIMEOUT, PAY_TIMEOUT_VAL);
		JPORDER_STATUS_MAP.put(PAY_FAIL, PAY_FAIL_VAL);
		JPORDER_STATUS_MAP.put(TICKET_FAIL_NEED_REFUND, TICKET_FAIL_NEED_REFUND_VAL);
		JPORDER_STATUS_MAP.put(TICKET_FAIL_REFUND, TICKET_FAIL_REFUND_VAL);
		JPORDER_STATUS_MAP.put(TICKET_SUCC, TICKET_SUCC_VAL);
		JPORDER_STATUS_MAP.put(ASK_TICKET_SUCC, ASK_TICKET_SUCC_VAL);
		JPORDER_STATUS_MAP.put(APPLY_DISCARD, APPLY_DISCARD_VAL);
		JPORDER_STATUS_MAP.put(DISCARD_SUCC, DISCARD_SUCC_VAL);
		JPORDER_STATUS_MAP.put(DISCARD_SUCC_NOT_REFUND, DISCARD_SUCC_NOT_REFUND_VAL);
		JPORDER_STATUS_MAP.put(DISCARD_SUCC_REFUND, DISCARD_SUCC_REFUND_VAL);
		JPORDER_STATUS_MAP.put(DISCARD_FAIL, DISCARD_FAIL_VAL);
		JPORDER_STATUS_MAP.put(APPLY_BACK, APPLY_BACK_VAL);
		JPORDER_STATUS_MAP.put(BACK_SUCC, BACK_SUCC_VAL);
		JPORDER_STATUS_MAP.put(BACK_SUCC_NOT_REFUND, BACK_SUCC_NOT_REFUND_VAL);
		JPORDER_STATUS_MAP.put(BACK_SUCC_REFUND, BACK_SUCC_REFUND_VAL);
		JPORDER_STATUS_MAP.put(BACK_FAIL, BACK_FAIL_VAL);
		JPORDER_STATUS_MAP.put(ORDER_CLOSED, ORDER_CLOSED_VAL);
	}

	/** 平台订单状态与供应商订单状态的对应关系 */
	public final static HashMap<String, String> STATUS_KEY_RELATION = new HashMap<String, String>();
	public static final String YG_WAIT_PAY = "NW"; // 新订单待支付
	public static final String YG_ALREADY_PAY = "PY"; // 已支付
	public static final String YG_TEMPORARILY_TICKET = "ZP"; // 暂不能出票
	public static final String YG_ALREADY_TICKET = "TP"; // 已出票
	public static final String YG_WAIT_REFUND = "RW"; // 出票失败待退款
	public static final String YG_ALREADY_REFUND = "RP"; // 出票失败已退款
	public static final String YG_REFUND_SUCCESS = "FR";// 退款成功
	public static final String YG_REFUND_FAIL = "RR";// 退废失败
	public static final String YG_APPLY_FAIL = "RF";// 升舱失败
	public static final String YG_APPLY_REFUND_DISCARD = "AR"; // 申请退/废票
	static {
		// STATUS_KEY_RELATION.put(WAIT_PAY, YG_WAIT_PAY);
		// STATUS_KEY_RELATION.put(ALREADY_PAY, YG_ALREADY_PAY);
		// STATUS_KEY_RELATION.put(TEMPORARILY_TICKET,YG_TEMPORARILY_TICKET);
		// STATUS_KEY_RELATION.put(ALREADY_TICKET, YG_ALREADY_TICKET);
		//
		// STATUS_KEY_RELATION.put(WAIT_REFUND, YG_WAIT_REFUND);
		// STATUS_KEY_RELATION.put(ALREADY_REFUND, YG_ALREADY_REFUND);
		// STATUS_KEY_RELATION.put(REFUND_SUCCESS, YG_REFUND_SUCCESS);
		// STATUS_KEY_RELATION.put(REFUND_FAIL, YG_REFUND_FAIL);
		//
		// STATUS_KEY_RELATION.put(APPLY_FAIL, YG_APPLY_FAIL);
		// STATUS_KEY_RELATION.put(APPLY_REFUND_DISCARD,
		// YG_APPLY_REFUND_DISCARD);
	}

	/** 当前状态 */
	private Integer status;

	public static final String COMMON_TYPE = "0";
	public static final String EXCEPTION_TYPE = "1";

	public static final String COMMON_TYPE_STR = "普通订单";
	public static final String EXCEPTION_TYPE_STR = "异常订单";
	public final static HashMap<String, String> JPORDER_TYPE_MAP = new HashMap<String, String>();
	static {
		JPORDER_TYPE_MAP.put(COMMON_TYPE, COMMON_TYPE_STR);
		JPORDER_TYPE_MAP.put(EXCEPTION_TYPE, EXCEPTION_TYPE_STR);
	}

	/** 退废订单状态 */
	public static final Integer CATEGORY_REFUND = 0;// 废票
	public static final Integer CATEGORY_BACK = 1; // 退票
	/** 退废订单状态 */
	public static final String CATEGORY_REFUND_VAL = "已废票";// 废票
	public static final String CATEGORY_BACK_VAL = "已退票"; // 退票


	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

}