package plfx.jp.app.service.spi.dealer;

import hg.common.page.Pagination;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import plfx.jp.app.service.local.dealer.DealerLocalService;
import plfx.jp.app.service.spi.base.BaseJpSpiServiceImpl;
import plfx.jp.command.DealerCommand;
import plfx.jp.pojo.dto.dealer.DealerDTO;
import plfx.jp.qo.admin.dealer.DealerQO;
import plfx.jp.spi.inter.dealer.DealerService;


@Service
public class DealerServiceImpl extends BaseJpSpiServiceImpl<DealerDTO, DealerQO, DealerLocalService> implements DealerService {

	@Autowired
	DealerLocalService dealerLocalService;

	@Override
	protected DealerLocalService getService() {
		return dealerLocalService;
	}

	@Override
	protected Class<DealerDTO> getDTOClass() {
		return DealerDTO.class;
	}

	@Override
	public Pagination queryDealerList(Pagination pagination) {
		return queryPagination(pagination);
	}
	
	
	public boolean saveDealer(DealerCommand command) {
		return dealerLocalService.saveDealer(command);
	}
		
	
	public boolean updateDealer(DealerCommand command){
		return dealerLocalService.updateDealer(command);
		
	}
	
	public boolean useDealer(DealerCommand command){
		return dealerLocalService.useDealer(command);
	}
	
	
	public boolean deleteDealer(DealerCommand command){
		return dealerLocalService.deleteDealer(command);
	}
	public boolean multiUse(DealerCommand command){
		return dealerLocalService.multiUse(command);
	}
}
