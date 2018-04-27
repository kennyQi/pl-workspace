package slfx.jp.domain.model.order;

/**
 * 
 * @类功能说明：
 * 
 * 订单状态
	1 HK 新订单待支付
	2 RR 已支付待出票
	3 DZ 已完成
	4 BB 退回状态
	5 SQ 申请状态
	
	HK	预订状态
	RR	申请状态（出票处理中）
	SQ	申请状态（退废票申请）
	DZ	出票状态（已出票/退款完成）
	BB	退回状态

	
	
 * @类修改者：
 * @修改日期：
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @作者：qiuxianxiang
 * @创建时间：2014年9月17日下午4:49:11
 * @版本：V1.0
 *
 */
public class YGOrderStatus {
	
	public static final String HK= "HK"; //预订状态
	public static final String RR= "RR"; //申请状态(出票处理中)
	public static final String SQ= "SQ"; //申请状态(退废票申请)
	public static final String DZ= "DZ"; //出票状态(已出票/退款完成)
	public static final String BB= "BB"; //退回状态
	
	public static final String HK_VAL= "预订状态"; //预订状态
	public static final String RR_VAL= "申请状态(出票处理中)"; //申请状态(出票处理中)
	public static final String SQ_VAL= "申请状态(退废票申请)"; //申请状态(退废票申请)
	public static final String DZ_VAL= "出票状态(已出票/退款完成)"; //出票状态(已出票/退款完成)
	public static final String BB_VAL= "退回状态"; //退回状态
	
	
}
