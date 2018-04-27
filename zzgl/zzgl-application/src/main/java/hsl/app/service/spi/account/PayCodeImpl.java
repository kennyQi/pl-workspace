package hsl.app.service.spi.account;
import hg.common.page.Pagination;
import hsl.app.component.base.BaseSpiServiceImpl;
import hsl.app.service.local.account.PayCodeLocalService;
import hsl.pojo.command.account.PayCodeCommand;
import hsl.pojo.dto.account.PayCodeDTO;
import hsl.pojo.qo.account.PayCodeQO;
import hsl.spi.inter.account.PayCodeService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PayCodeImpl extends BaseSpiServiceImpl<PayCodeDTO,PayCodeQO,PayCodeLocalService> implements PayCodeService{
	@Autowired
	private PayCodeLocalService payCodeLocalService;



	@Override
	protected PayCodeLocalService getService() {
		return payCodeLocalService;
	}

	@Override
	protected Class<PayCodeDTO> getDTOClass() {
		return PayCodeDTO.class;
	}

	@Override
	public String recharge(PayCodeCommand payCodeCommand) throws Exception {
		
		return payCodeLocalService.recharge(payCodeCommand);
	}
	public Pagination queryPagination(Pagination pagination){
		return payCodeLocalService.queryPayCodePagination(pagination);
	}

}
