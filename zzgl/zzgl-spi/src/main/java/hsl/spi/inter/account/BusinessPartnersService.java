package hsl.spi.inter.account;

import hsl.pojo.command.account.BusinessPartnersCommand;
import hsl.pojo.dto.account.BusinessPartnersDTO;
import hsl.pojo.qo.account.BusinessPartnersQO;
import hsl.spi.inter.BaseSpiService;

public interface BusinessPartnersService extends BaseSpiService<BusinessPartnersDTO,BusinessPartnersQO>{
	/**
	 * 保存公司信息
	 * @param businessPartnersDTO
	 * @return BusinessPartnersDTO
	 */
	public BusinessPartnersDTO createBusinessPartners(BusinessPartnersCommand businessPartnersCommand);
	/**
	 *修改公司信息
	 * @param BusinessPartnersQO
	 * @return BusinessPartnersDTO
	 */
	public String updateBusinessPartners(BusinessPartnersCommand businessPartnersCommand);
}
