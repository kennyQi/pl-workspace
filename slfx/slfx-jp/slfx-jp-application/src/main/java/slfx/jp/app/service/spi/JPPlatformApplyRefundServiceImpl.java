package slfx.jp.app.service.spi;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import slfx.jp.app.service.local.PlatformApplyRefundLocalService;
import slfx.jp.command.client.ABEDeletePnrCommand;
import slfx.jp.command.client.YGApplyRefundCommand;
import slfx.jp.pojo.dto.supplier.abe.ABEDeletePnrDTO;
import slfx.jp.pojo.dto.supplier.yg.YGApplyRefundDTO;
import slfx.jp.spi.inter.JPPlatformApplyRefundService;

/**
 * 
 * @类功能说明：平台请求退废票SERVICE实现
 * @类修改者：
 * @修改日期：
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @作者：tandeng
 * @创建时间：2014年7月31日下午4:46:43
 * @版本：V1.0
 *
 */
@Service("jpPlatformApplyRefundService")
public class JPPlatformApplyRefundServiceImpl implements JPPlatformApplyRefundService {

	@Autowired
	private PlatformApplyRefundLocalService platformApplyRefundLocalService;
	
	@Override
	public YGApplyRefundDTO applyRefund(YGApplyRefundCommand command) {
		return platformApplyRefundLocalService.applyRefund(command);
	}

	@Override
	public ABEDeletePnrDTO deletePnr(ABEDeletePnrCommand deleteCommand) {
		return platformApplyRefundLocalService.deletePnr(deleteCommand);
	}

}
