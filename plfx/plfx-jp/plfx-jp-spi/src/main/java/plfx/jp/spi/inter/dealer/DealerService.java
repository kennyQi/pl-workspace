package plfx.jp.spi.inter.dealer;

import hg.common.page.Pagination;
import plfx.jp.command.DealerCommand;
import plfx.jp.pojo.dto.dealer.DealerDTO;
import plfx.jp.qo.admin.dealer.DealerQO;
import plfx.jp.spi.inter.BaseJpSpiService;

public interface DealerService extends BaseJpSpiService<DealerDTO, DealerQO> {
	
	
	
	public Pagination queryDealerList(Pagination pagination);
	
	public boolean saveDealer(DealerCommand command);

	public boolean updateDealer(DealerCommand command);
	
	public boolean deleteDealer(DealerCommand command );
	
	public boolean useDealer(DealerCommand command );
	
	public boolean multiUse(DealerCommand command);
	
}
