package plfx.jd.pojo.system;
import java.util.Map;
import java.util.TreeMap;
/**
 * @类功能说明：酒店订单状态常量类
 * @类修改者：
 * @修改日期：2015年7月14日下午2:05:07
 * @修改说明：
 * @公司名称：浙江票量科技有限公司
 * @作者：chenxy
 * @创建时间：2015年7月14日下午2:05:07
 */
public class HotelOrderStatusConstants {
	/**
	 * 订单状态
	 */
	public static final Map<String,String>	ORDER_STATUS_MAP = new TreeMap<String,String>();
	public final static String ORDER_A = "A";
	public final static String ORDER_B = "B";
	public final static String ORDER_B1 = "B1";
	public final static String ORDER_B2 = "B2";
	public final static String ORDER_C = "C";
	public final static String ORDER_D= "D";
	public final static String ORDER_E= "E";
	public final static String ORDER_F= "F";
	public final static String ORDER_G = "G";
	public final static String ORDER_H = "H";
	public final static String ORDER_I = "I";
	public final static String ORDER_N = "N";
	public final static String ORDER_O = "O";
	public final static String ORDER_P = "P";
	public final static String ORDER_S = "S";
	public final static String ORDER_T = "T";
	public final static String ORDER_U = "U";
	public final static String ORDER_V = "V";
	public final static String ORDER_W = "W";
	public final static String ORDER_Z = "Z";
	
	static{
		ORDER_STATUS_MAP.put(ORDER_A, "已确认");
		ORDER_STATUS_MAP.put(ORDER_B, "NO SHOW");
		ORDER_STATUS_MAP.put(ORDER_B1, "有预定未查到");
		ORDER_STATUS_MAP.put(ORDER_B2, "待查");
		ORDER_STATUS_MAP.put(ORDER_C, "已结帐");
		ORDER_STATUS_MAP.put(ORDER_D, "删除");
		ORDER_STATUS_MAP.put(ORDER_E, "取消");
		ORDER_STATUS_MAP.put(ORDER_F, "已入住");
		ORDER_STATUS_MAP.put(ORDER_G, "变价");
		ORDER_STATUS_MAP.put(ORDER_H, "变更");
		ORDER_STATUS_MAP.put(ORDER_I, "大单");
		ORDER_STATUS_MAP.put(ORDER_N, "新单");
		ORDER_STATUS_MAP.put(ORDER_O, "满房");
		ORDER_STATUS_MAP.put(ORDER_P, "暂无价格");
		ORDER_STATUS_MAP.put(ORDER_S, "特殊");
		ORDER_STATUS_MAP.put(ORDER_T, "计划中");
		ORDER_STATUS_MAP.put(ORDER_U, "特殊满房");
		ORDER_STATUS_MAP.put(ORDER_V, "已审");
		ORDER_STATUS_MAP.put(ORDER_W, "虚拟");
		ORDER_STATUS_MAP.put(ORDER_Z, "删除,另换酒店");
	}
	
	/**
	 * 订单展示状态
	 */
	public static final Map<String,String>	ORDER_SHOWSTATUS_MAP = new TreeMap<String,String>();
	static{
		/*ORDER_SHOWSTATUS_MAP.put(1, "担保失败");
		ORDER_SHOWSTATUS_MAP.put(2, "等待担保");
		ORDER_SHOWSTATUS_MAP.put(4, "等待确认");
		ORDER_SHOWSTATUS_MAP.put(8, "等待支付");
		ORDER_SHOWSTATUS_MAP.put(16, "等待核实入住");
		ORDER_SHOWSTATUS_MAP.put(32, "酒店拒绝订单");
		ORDER_SHOWSTATUS_MAP.put(64, "未入住");
		ORDER_SHOWSTATUS_MAP.put(128, "已经离店");
		ORDER_SHOWSTATUS_MAP.put(256, "已经取消");
		ORDER_SHOWSTATUS_MAP.put(512, "已经确认");
		ORDER_SHOWSTATUS_MAP.put(1024, "已经入住");
		ORDER_SHOWSTATUS_MAP.put(2048, "正在担保-处理中");
		ORDER_SHOWSTATUS_MAP.put(4096, "正在支付-处理中");
		ORDER_SHOWSTATUS_MAP.put(8192, "支付失败");*/
		
		
		ORDER_SHOWSTATUS_MAP.put("1", "担保失败");
		ORDER_SHOWSTATUS_MAP.put("2", "等待担保");
		ORDER_SHOWSTATUS_MAP.put("4", "等待确认");
		ORDER_SHOWSTATUS_MAP.put("8", "等待支付");
		ORDER_SHOWSTATUS_MAP.put("16", "等待核实入住");
		ORDER_SHOWSTATUS_MAP.put("32", "酒店拒绝订单");
		ORDER_SHOWSTATUS_MAP.put("64", "未入住");
		ORDER_SHOWSTATUS_MAP.put("128", "已经离店");
		ORDER_SHOWSTATUS_MAP.put("256", "已经取消");
		ORDER_SHOWSTATUS_MAP.put("512", "已经确认");
		ORDER_SHOWSTATUS_MAP.put("1024", "已经入住");
		ORDER_SHOWSTATUS_MAP.put("2048", "正在担保-处理中");
		ORDER_SHOWSTATUS_MAP.put("4096", "正在支付-处理中");
		ORDER_SHOWSTATUS_MAP.put("8192", "支付失败");
	}
}

