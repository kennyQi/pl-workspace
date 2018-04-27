package plfx.gnjp.app.service.spi;

import hg.common.page.Pagination;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import plfx.gnjp.app.service.local.GNJPOrderLocalService;
import plfx.gnjp.app.service.spi.base.BaseJpSpiServiceImpl;
import plfx.jp.spi.inter.JPPlatformOrderService;
import plfx.yeexing.pojo.command.order.JPOrderCancelCommand;
import plfx.yeexing.pojo.command.order.JPOrderCommand;
import plfx.yeexing.pojo.command.order.JPOrderRefundCommand;
import plfx.yeexing.pojo.command.order.JPOrderRefuseCommand;
import plfx.yeexing.pojo.command.order.JPPayNotifyCommand;
import plfx.yeexing.pojo.dto.order.JPOrderDTO;
import plfx.yeexing.qo.admin.PlatformOrderQO;

/**
 * 
 * @类功能说明：平台订单SERVICE实现
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @作者：yuqz
 * @创建时间：2015年6月29日下午5:14:38
 * @版本：V1.0
 *
 */
@Service("jpPlatformOrderService")
public class JPPlatformOrderServiceImpl extends BaseJpSpiServiceImpl<JPOrderDTO, PlatformOrderQO, GNJPOrderLocalService> implements JPPlatformOrderService {

	@Autowired
	private GNJPOrderLocalService jpOrderLocalService;
	
	@Override
	protected GNJPOrderLocalService getService() {
		return jpOrderLocalService;
	}

	@Override
	protected Class<JPOrderDTO> getDTOClass() {
		return JPOrderDTO.class;
	}
	
	@Override
	public Boolean updateJPOrder(JPOrderCommand command) {
		return getService().updateJPOrder(command);
	}

	@Override
	public boolean updatePayStatus(JPPayNotifyCommand jpPayNotifyCommand) {
		return getService().updatePayStatus(jpPayNotifyCommand);
	}

	@Override
	public boolean jpOrderCancel(JPOrderCancelCommand jpOrderCancelCommand) {
		return getService().jpOrderCancel(jpOrderCancelCommand);
	}

	@Override
	public boolean jpOrderRefund(JPOrderRefundCommand jpOrderRefundCommand) {
		return getService().jpOrderRefund(jpOrderRefundCommand);
	}

	@Override
	public boolean jpOrderRefuse(JPOrderRefuseCommand jpOrderRefuseCommand) {
		return getService().jpOrderRefuse(jpOrderRefuseCommand);
	}

	@Override
	public Pagination queryFJPOrderList(Pagination pagination) {
		
		return queryPagination(pagination);
	}

	@Override
	public boolean saveErrorJPOrder(JPOrderCommand jpOrderCommand) {
		return jpOrderLocalService.saveErrorJPOrder(jpOrderCommand);
	}


}
