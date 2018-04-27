package plfx.xl.pojo.system;
/**
 * 
 * @类功能说明：线路消息常量类
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @作者：yuqz
 * @创建时间：2015年5月13日下午3:00:00
 * @版本：V1.0
 *
 */
public class LineMessageConstants {
	
	public static final String MESSAGE_TYPE_LINE = "0"; //修改线路通知类型
	
	public static final String MESSAGE_TYPE_LINE_ORDER = "1"; //修改线路订单通知类型
	
	public final static String AUDIT_UP = "0";// 上架线路

	public final static String AUDIT_DOWN = "1";// 下架线路

	public static final String UPDATE_PICTURE = "2";// 修改图片

	public static final String UPDATE_DATE_SALE_PRICE = "3";// 修改价格日历
	
	public static final String UPDATE_DATE_SALE_MINPRICE = "4";// 更新最低价格
	
	public static final String UPDATE_LINE_IMAGE = "5";// 更新线路图片
	
	public static final String UPDATE_LINE_ORDER_SALEPRICE = "6";// 更新线路订单金额
	
}
