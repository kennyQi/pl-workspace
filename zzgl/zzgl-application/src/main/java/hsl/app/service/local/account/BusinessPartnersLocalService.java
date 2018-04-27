package hsl.app.service.local.account;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSON;

import hg.common.component.BaseServiceImpl;
import hg.common.util.BeanMapperUtils;
import hg.log.util.HgLogger;
import hsl.app.dao.account.BusinessPartnersDao;
import hsl.domain.model.user.account.BusinessPartners;
import hsl.pojo.command.account.BusinessPartnersCommand;
import hsl.pojo.dto.account.BusinessPartnersDTO;
import hsl.pojo.qo.account.BusinessPartnersQO;
@Service
@Transactional
public class BusinessPartnersLocalService extends BaseServiceImpl<BusinessPartners, BusinessPartnersQO, BusinessPartnersDao>{
	@Autowired
	private BusinessPartnersDao businessPartnersDao;
	@Override
	protected BusinessPartnersDao getDao() {
		return this.businessPartnersDao;
	}
	/**
	 * @方法功能说明：创建公司
	 * @创建者名字：zhaows
	 * @创建时间：2015-8-31下午1:21:13
	 * @参数：@param businessPartnersDTO
	 * @参数：@return
	 * @return:BusinessPartnersDTO
	 * @throws
	 */
	public BusinessPartnersDTO createBusinessPartners(BusinessPartnersCommand businessPartnersCommand) {
		BusinessPartners businessPartner=new BusinessPartners();
		businessPartner.createCompany(businessPartnersCommand);
		HgLogger.getInstance().info("zhaows","createBusinessPartners---创建公司参数" + JSON.toJSONString(businessPartnersCommand));
		businessPartnersDao.save(businessPartner);
		BusinessPartnersDTO businessPartnersDTO=BeanMapperUtils.map(businessPartnersCommand,BusinessPartnersDTO.class);
		return businessPartnersDTO;
	}
	/**
	 * @方法功能说明：修改公司信息
	 * @创建者名字：zhaows
	 * @创建时间：2015-8-31下午1:21:32
	 * @参数：@param businessPartnersQO
	 * @参数：@return
	 * @return:BusinessPartnersDTO
	 * @throws
	 */
	public String updateBusinessPartners(BusinessPartnersCommand businessPartnersCommand) {
		HgLogger.getInstance().info("zhaows","updateBusinessPartners---修改公司信息参数" + JSON.toJSONString(businessPartnersCommand));
		BusinessPartners businessPartners=businessPartnersDao.get(businessPartnersCommand.getId());
		HgLogger.getInstance().info("zhaows","updateBusinessPartners---查询公司信息" + JSON.toJSONString(businessPartners));
		if(businessPartners!=null){
			if(StringUtils.isNotBlank(businessPartnersCommand.getCompanyLinkName())){
				businessPartners.setCompanyLinkName(businessPartnersCommand.getCompanyLinkName());
			}
			if(StringUtils.isNotBlank(businessPartnersCommand.getCompanyLinkTel())){
				businessPartners.setCompanyLinkTel(businessPartnersCommand.getCompanyLinkTel());
			}
			if(StringUtils.isNotBlank(businessPartnersCommand.getCompanyName())){
				businessPartners.setCompanyName(businessPartnersCommand.getCompanyName());
			}
			businessPartnersDao.update(businessPartners);
			return "success";
		}else{
			return "error";
		}
	}

}
