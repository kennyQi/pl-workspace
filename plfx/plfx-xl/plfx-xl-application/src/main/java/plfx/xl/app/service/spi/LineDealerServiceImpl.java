package plfx.xl.app.service.spi;

import hg.common.page.Pagination;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import plfx.xl.app.component.base.BaseXlSpiServiceImpl;
import plfx.xl.app.service.local.LineDealerLocalService;
import plfx.xl.pojo.command.dealer.AuditLineDealerCommand;
import plfx.xl.pojo.command.dealer.CreateLineDealerCommand;
import plfx.xl.pojo.command.dealer.ModifyLineDealerCommand;
import plfx.xl.pojo.dto.LineDealerDTO;
import plfx.xl.pojo.qo.LineDealerQO;
import plfx.xl.spi.inter.LineDealerService;
/**
 * 
 * @类功能说明：
 * @类修改者：
 * @修改日期：
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @作者：tuhualiang
 * @创建时间：2014年12月5日上午9:31:54
 * @版本：V1.0
 *
 */
@Service("lineDealerService_xl")
public class LineDealerServiceImpl extends BaseXlSpiServiceImpl<LineDealerDTO, LineDealerQO, LineDealerLocalService> implements  LineDealerService {
	@Autowired
	LineDealerLocalService lineDealerLocalService;

	@Override
	protected LineDealerLocalService getService() {
		return lineDealerLocalService;
	}

	@Override
	protected Class<LineDealerDTO> getDTOClass() {
		return LineDealerDTO.class;
	}

	@Override
	public Pagination queryDealerList(Pagination pagination) {
		return queryPagination(pagination);
	}
	
	
	public boolean saveDealer(CreateLineDealerCommand command) {
		return lineDealerLocalService.saveDealer(command);
	}
		
	
	public boolean updateDealer(ModifyLineDealerCommand command){
		return lineDealerLocalService.updateDealer(command);
		
	}
	
	public boolean useDealer(AuditLineDealerCommand command){
		return lineDealerLocalService.useDealer(command);
	}
	
	
	/*public boolean deleteDealer(DealerCommand command){
		return dealerLocalService.deleteDealer(command);
	}*/
	public boolean multiUse(AuditLineDealerCommand command){
		return lineDealerLocalService.multiUse(command);
	}

	
}
