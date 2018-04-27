package hsl.app.service.spi.account;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import hsl.app.component.base.BaseSpiServiceImpl;
import hsl.app.service.local.account.BusinessPartnersLocalService;
import hsl.pojo.command.account.BusinessPartnersCommand;
import hsl.pojo.dto.account.BusinessPartnersDTO;
import hsl.pojo.qo.account.BusinessPartnersQO;
import hsl.spi.inter.account.BusinessPartnersService;
@Service
public class BusinessPartnersImpl extends BaseSpiServiceImpl<BusinessPartnersDTO,BusinessPartnersQO,BusinessPartnersLocalService> implements BusinessPartnersService{
	@Autowired
	private BusinessPartnersLocalService businessPartnersLocalService;
	@Override
	public BusinessPartnersDTO createBusinessPartners(
			BusinessPartnersCommand businessPartnersCommand) {
		return businessPartnersLocalService.createBusinessPartners(businessPartnersCommand);
	}

	@Override
	public String updateBusinessPartners(
			BusinessPartnersCommand businessPartnersCommand) {
		return businessPartnersLocalService.updateBusinessPartners(businessPartnersCommand);
	}

	@Override
	protected BusinessPartnersLocalService getService() {
		return businessPartnersLocalService;
	}

	@Override
	protected Class<BusinessPartnersDTO> getDTOClass() {
		return BusinessPartnersDTO.class;
	}


	


}
