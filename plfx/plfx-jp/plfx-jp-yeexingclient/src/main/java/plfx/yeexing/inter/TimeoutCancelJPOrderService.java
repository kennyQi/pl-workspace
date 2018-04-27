package plfx.yeexing.inter;
/**
 * 
 * @类功能说明：计划任务：定时 扫描 机票订单，超过20分钟未支付状态的订单，执行取消订单操作
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @作者：yuqz
 * @创建时间：2015年8月7日上午10:17:14
 * @版本：V1.0
 *
 */
public interface TimeoutCancelJPOrderService {
	/**
	 * 定时 扫描 机票订单，超过20分钟未支付状态的订单，执行取消订单操作
	 */
	public void timeoutCancelJPOrderTask();
}
