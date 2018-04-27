package slfx.jp.spi.inter;
/**
 * @类功能说明：计划任务：定时 扫描 机票订单，超过20分钟未支付状态的订单，执行取消订单操作
 * @类修改者：
 * @修改日期：
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @作者：caizhenghao
 * @创建时间：2015年1月12日上午10:28:42
 * @版本：V1.0
 *
 */
public interface TimeoutCancelJPOrderService {
	/**
	 * 定时 扫描 机票订单，超过20分钟未支付状态的订单，执行取消订单操作
	 */
	public void timeoutCancelJPOrderTask();
}
