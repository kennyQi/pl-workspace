package slfx.jp.spi.inter;

import slfx.jp.pojo.dto.supplier.yg.YGQueryOrderStatusDTO;

/**
 * 
 * @类功能说明：预订单状态查询SERVICE
 * @类修改者：
 * @修改日期：
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @作者：tandeng
 * @创建时间：2014年7月31日下午5:12:36
 * @版本：V1.0
 *
 */
public interface PlatformQueryOrderStatusService {

	public YGQueryOrderStatusDTO queryOrderStatus(final String orderNo);
}
