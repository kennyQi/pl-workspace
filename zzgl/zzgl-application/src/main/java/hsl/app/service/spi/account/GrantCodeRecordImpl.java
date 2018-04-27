package hsl.app.service.spi.account;
import java.util.List;

import hg.common.util.EntityConvertUtils;
import hsl.app.component.base.BaseSpiServiceImpl;
import hsl.app.service.local.account.GrantCodeRecordLocalService;
import hsl.domain.model.user.account.PayCode;
import hsl.pojo.command.account.GrantCodeRecordCommand;
import hsl.pojo.dto.account.GrantCodeRecordDTO;
import hsl.pojo.dto.account.PayCodeDTO;
import hsl.pojo.qo.account.GrantCodeRecordQO;
import hsl.spi.inter.account.GrantCodeRecordService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
@Service
public class GrantCodeRecordImpl extends BaseSpiServiceImpl<GrantCodeRecordDTO,GrantCodeRecordQO,GrantCodeRecordLocalService> implements GrantCodeRecordService{
	@Autowired
	private GrantCodeRecordLocalService grantCodeRecordLocalService;
	@Override
	public List<PayCodeDTO> issueRechargeCode(GrantCodeRecordCommand grantCodeRecordCommand) throws Exception {
		List<PayCode> list=grantCodeRecordLocalService.issueRechargeCode(grantCodeRecordCommand);
		return EntityConvertUtils.convertEntityToDtoList(list, PayCodeDTO.class);
	}
	@Override
	public String examineRechargeCode(GrantCodeRecordCommand grantCodeRecordCommand) {
		return grantCodeRecordLocalService.examineRechargeCode(grantCodeRecordCommand);

	}
	public List<GrantCodeRecordDTO> queryList(GrantCodeRecordQO grantCodeRecordQO){
		List<GrantCodeRecordDTO> list=grantCodeRecordLocalService.queryListGrantCodeRecordDTO(grantCodeRecordQO);
		return list;

	}
	/*@Override
	public Pagination queryRecordPagination(Pagination pagination) {
		return grantCodeRecordLocalService.queryRecordPagination(pagination);
	}*/



	@Override
	protected GrantCodeRecordLocalService getService() {
		return grantCodeRecordLocalService;
	}
	@Override
	protected Class<GrantCodeRecordDTO> getDTOClass() {
		return GrantCodeRecordDTO.class;
	}


}
