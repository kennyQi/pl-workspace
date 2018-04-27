package slfx.jp.app.service.spi.dealer;

import hg.common.page.Pagination;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import slfx.jp.app.component.base.BaseJpSpiServiceImpl;
import slfx.jp.app.service.local.dealer.DealerLocalService;
import slfx.jp.command.admin.dealer.DealerCommand;
import slfx.jp.pojo.dto.dealer.DealerDTO;
import slfx.jp.qo.admin.dealer.DealerQO;
import slfx.jp.spi.inter.dealer.DealerService;

/**
 * 
 * @类功能说明：
 * @类修改者：
 * @修改日期：
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @作者：qiuxianxiang
 * @创建时间：2014年8月4日下午3:39:50
 * @版本：V1.0
 * 
 */

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
