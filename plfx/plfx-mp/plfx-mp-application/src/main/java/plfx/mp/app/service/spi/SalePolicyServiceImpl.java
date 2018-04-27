package plfx.mp.app.service.spi;

import hg.common.page.Pagination;
import hg.common.util.BeanMapperUtils;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import plfx.mp.app.common.util.EntityConvertUtils;
import plfx.mp.app.pojo.qo.SalePolicySnapshotQO;
import plfx.mp.app.service.local.SalePolicySnapshotLocalService;
import plfx.mp.command.admin.CreatePlatformPolicyCommand;
import plfx.mp.command.admin.ModifyPlatformPolicyCommand;
import plfx.mp.command.admin.StartPlatformPolicyCommand;
import plfx.mp.command.admin.StopPlatformPolicyCommand;
import plfx.mp.domain.model.platformpolicy.SalePolicySnapshot;
import plfx.mp.pojo.dto.platformpolicy.SalePolicySnapshotDTO;
import plfx.mp.qo.PlatformPolicyQO;
import plfx.mp.spi.inter.AdminSalePolicyService;

@Service
public class SalePolicyServiceImpl implements AdminSalePolicyService {

	@Autowired
	private SalePolicySnapshotLocalService service;

	@Override
	public void createPlatformPolicy(CreatePlatformPolicyCommand command) {
		service.createPlatformPolicy(command);
	}

	@Override
	public void modifyPlatformPolicy(ModifyPlatformPolicyCommand command) {
		service.modifyPlatformPolicy(command);
	}

	@Override
	public void stopPlatformPolicy(StopPlatformPolicyCommand command) {
		service.stopPlatformPolicy(command);
	}
	
	protected SalePolicySnapshotQO parseQo(PlatformPolicyQO qo) {
		return BeanMapperUtils.map(qo, SalePolicySnapshotQO.class);
	}
	
	protected Pagination parsePaginationQo(Pagination pagination) {
		Pagination pagination2 = BeanMapperUtils.map(pagination, Pagination.class);
		PlatformPolicyQO qo = (PlatformPolicyQO) pagination.getCondition();
		SalePolicySnapshotQO salePolicySnapshotQO = parseQo(qo);
		pagination2.setCondition(salePolicySnapshotQO);
		return pagination2;
	}

	@Override
	public SalePolicySnapshotDTO queryUnique(PlatformPolicyQO qo) {
		SalePolicySnapshotQO salePolicySnapshotQO = parseQo(qo);
		SalePolicySnapshot snapshot = service.queryUnique(salePolicySnapshotQO);
		return EntityConvertUtils.convertEntityToDto(snapshot, SalePolicySnapshotDTO.class);
	}

	@Override
	public List<SalePolicySnapshotDTO> queryList(PlatformPolicyQO qo) {
		SalePolicySnapshotQO salePolicySnapshotQO = parseQo(qo);
		List<SalePolicySnapshot> list = service.queryList(salePolicySnapshotQO);
		return EntityConvertUtils.convertEntityToDtoList(list, SalePolicySnapshotDTO.class);
	}

	@SuppressWarnings("unchecked")
	@Override
	public Pagination queryPagination(Pagination pagination) {
		Pagination pagination2 = parsePaginationQo(pagination);
		pagination2.setPageNo(pagination.getPageNo());
		Pagination pagination3 = service.queryPagination(pagination2);
		List<SalePolicySnapshot> list = (List<SalePolicySnapshot>) pagination3.getList();
		pagination3.setList(EntityConvertUtils.convertEntityToDtoList(list, SalePolicySnapshotDTO.class));
		pagination3.setCondition(pagination.getCondition());
		return pagination3;
	}

	@Override
	public void startPlatformPolicy(StartPlatformPolicyCommand command) {
		service.startPlatformPolicy(command);
	}

}
