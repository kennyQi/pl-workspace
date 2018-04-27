package hsl.app.service.spi.commonContact;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import hg.common.util.BeanMapperUtils;
import hsl.app.component.base.BaseSpiServiceImpl;
import hsl.app.service.local.commonContact.CommonContactLocalService;
import hsl.domain.model.commonContact.CommonContact;
import hsl.pojo.command.CommonContact.CreateCommonContactCommand;
import hsl.pojo.command.CommonContact.UpdateCommonContactCommand;
import hsl.pojo.dto.commonContact.CommonContactDTO;
import hsl.pojo.exception.CommonContactException;
import hsl.pojo.qo.CommonContact.CommonContactQO;
import hsl.spi.inter.commonContact.CommonContactService;
@Service
public class CommonContactServiceImpl extends BaseSpiServiceImpl<CommonContactDTO,CommonContactQO,CommonContactLocalService> implements CommonContactService{
	@Autowired
	private CommonContactLocalService commonContactLocalService;

	@Override
	protected CommonContactLocalService getService() {
		return commonContactLocalService;
	}

	@Override
	protected Class<CommonContactDTO> getDTOClass() {
		return CommonContactDTO.class;
	}

	@Override
	public CommonContactDTO addCommonContact(CreateCommonContactCommand command) throws CommonContactException{
		return commonContactLocalService.addCommonContact(command);
	}

	@Override
	public CommonContactDTO updateCommonContact (
			UpdateCommonContactCommand command) throws CommonContactException{
		CommonContactDTO contactDTO=null;
		CommonContact commonContact=commonContactLocalService.updateCommonContact(command);
		contactDTO=BeanMapperUtils.map(commonContact, CommonContactDTO.class);
		return contactDTO;
	}

	@Override
	public boolean delCommonContact(String id) {
		return commonContactLocalService.delCommonContact(id);
	}
}
