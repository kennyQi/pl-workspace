package hsl.app.service.spi.lineSalesPlan.order;
import hg.log.util.HgLogger;
import hsl.app.component.base.BaseSpiServiceImpl;
import hsl.app.service.local.lineSalesPlan.order.LSPOrderLocalService;
import hsl.domain.model.lineSalesPlan.order.LSPOrder;
import hsl.domain.model.lineSalesPlan.order.LSPOrderBaseInfo;
import hsl.pojo.command.lineSalesPlan.order.CreateLSPOrderCommand;
import hsl.pojo.command.lineSalesPlan.order.ModifyLSPOrderPayInfoCommand;
import hsl.pojo.command.lineSalesPlan.order.RefundLSPOrderCommand;
import hsl.pojo.command.lineSalesPlan.order.UpdateLSPOrderStatusCommand;
import hsl.pojo.dto.lineSalesPlan.order.LSPOrderDTO;
import hsl.pojo.exception.LSPException;
import hsl.pojo.qo.lineSalesPlan.order.LSPOrderQO;
import hsl.spi.inter.lineSalesPlan.order.LSPOrderService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
/**
 * @类功能说明：
 * @类修改者：
 * @修改日期：
 * @修改说明：
 * @公司名称： 浙江票量云数据科技有限公司
 * @部门： 技术部
 * @作者： chenxy
 * @创建时间： 2015/12/1 11:17
 */
@Service
public class LSPOrderServiceImpl extends BaseSpiServiceImpl<LSPOrderDTO,LSPOrderQO,LSPOrderLocalService> implements LSPOrderService{

	@Autowired
	private LSPOrderLocalService lspOrderLocalService;

	@Override
	protected LSPOrderLocalService getService() {
		return lspOrderLocalService;
	}

	@Override
	protected Class<LSPOrderDTO> getDTOClass() {
		return LSPOrderDTO.class;
	}

	@Override
	public LSPOrderDTO createLSPOrder(CreateLSPOrderCommand createLSPOrderCommand) throws LSPException {
		LSPOrderDTO lspOrderDTO;
		if(createLSPOrderCommand.getOrderType().equals(LSPOrderBaseInfo.LSP_ORDER_TYPE_GROUPBUY)){
			lspOrderDTO=lspOrderLocalService.createLSPOrderOfGroupBuy(createLSPOrderCommand);
		}else{
			lspOrderDTO=lspOrderLocalService.createLSPOrderOfSecKill(createLSPOrderCommand);
		}
		return lspOrderDTO;
	}

	@Override
	public void updateLSPOrderStatus(UpdateLSPOrderStatusCommand updateLSPOrderStatusCommand) throws LSPException {
		lspOrderLocalService.updateLSPOrderStatus(updateLSPOrderStatusCommand);
	}

	@Override
	public void modifyLSPOrderPayInfo(ModifyLSPOrderPayInfoCommand modifyLSPOrderPayInfoCommand) throws Exception {
		lspOrderLocalService.modifyLSPOrderPayInfo(modifyLSPOrderPayInfoCommand);
	}
	public void checkLspISGroupSuc(String dealerOrderNo) throws LSPException{
		lspOrderLocalService.checkLspISGroupSuc(dealerOrderNo);
	}

	@Override
	public void refundLSPOrder(String result_details) throws LSPException {
		lspOrderLocalService.refundLSPOrder(result_details);
	}
}
