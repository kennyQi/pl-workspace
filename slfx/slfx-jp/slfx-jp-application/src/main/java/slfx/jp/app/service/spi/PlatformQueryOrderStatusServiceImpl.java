package slfx.jp.app.service.spi;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import slfx.jp.pojo.dto.supplier.yg.YGQueryOrderStatusDTO;
import slfx.jp.spi.inter.PlatformQueryOrderStatusService;
import slfx.yg.open.inter.YGFlightService;

/**
 * 
 * @类功能说明：平台订单状态SERVICE实现
 * @类修改者：
 * @修改日期：
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @作者：tandeng
 * @创建时间：2014年7月31日下午4:49:12
 * @版本：V1.0
 *
 */
@Service("platformQueryOrderStatusService")
public class PlatformQueryOrderStatusServiceImpl implements PlatformQueryOrderStatusService {

	@Autowired
	private YGFlightService ygFlightService;

	@Override
	public YGQueryOrderStatusDTO queryOrderStatus(final String orderNo) {
		return ygFlightService.queryOrderStatus(orderNo);
	}

}
