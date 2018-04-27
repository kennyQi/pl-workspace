package slfx.jp.spi.inter;
/**
 * @类功能说明：定时同步易购订单状态(查询已经成功支付但未能获取票号的订单，一分钟轮询)
 * @类修改者：
 * @修改日期：
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @作者：caizhenghao
 * @创建时间：2015年1月12日上午10:14:46
 * @版本：V1.0
 */
public interface UpdateJPOrderStatusService {
	/**
	 * 定时同步易购订单状态
	 */
	public void updateJPOrderStatusTask();
}
