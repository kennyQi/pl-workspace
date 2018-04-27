package slfx.jp.spi.inter;
/**
 * 
 * @类功能说明：计划任务：对可退款的订单执行退款SERVICE
 * @类修改者：
 * @修改日期：
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @作者：caizhenghao
 * @创建时间：2015年1月12日上午9:08:51
 * @版本：V1.0
 *
 */
public interface RefundJPOrderService {
	/**
	 * 对可退款的订单执行退款
	 */
	public void refundJPOrderTask();
}
