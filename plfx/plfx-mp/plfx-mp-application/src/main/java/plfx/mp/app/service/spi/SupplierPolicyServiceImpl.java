package plfx.mp.app.service.spi;

import hg.common.page.Pagination;
import hg.common.util.BeanMapperUtils;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import plfx.mp.app.common.util.EntityConvertUtils;
import plfx.mp.app.service.local.TCSupplierPolicySnapshotLocalService;
import plfx.mp.domain.model.supplierpolicy.TCSupplierPolicySnapshot;
import plfx.mp.pojo.dto.supplierpolicy.TCSupplierPolicySnapshotDTO;
import plfx.mp.qo.TCSupplierPolicySnapshotQO;
import plfx.mp.spi.inter.AdminSupplierPolicyService;

@Service
public class SupplierPolicyServiceImpl implements AdminSupplierPolicyService {

	@Autowired
	private TCSupplierPolicySnapshotLocalService service;
	
	protected plfx.mp.app.pojo.qo.TCSupplierPolicySnapshotQO parseQo(TCSupplierPolicySnapshotQO qo) {
		return BeanMapperUtils.map(qo, plfx.mp.app.pojo.qo.TCSupplierPolicySnapshotQO.class);
	}
	
	protected Pagination parsePaginationQo(Pagination pagination) {
		Pagination pagination2 = BeanMapperUtils.map(pagination, Pagination.class);
		TCSupplierPolicySnapshotQO qo = (TCSupplierPolicySnapshotQO) pagination.getCondition();
		plfx.mp.app.pojo.qo.TCSupplierPolicySnapshotQO snapshotQO = parseQo(qo);
		pagination2.setCondition(snapshotQO);
		return pagination2;
	}
	@Override
	public TCSupplierPolicySnapshotDTO queryUnique(TCSupplierPolicySnapshotQO qo) {
		plfx.mp.app.pojo.qo.TCSupplierPolicySnapshotQO snapshotQO = parseQo(qo);
		TCSupplierPolicySnapshot snapshot = service.queryUnique(snapshotQO);
		return EntityConvertUtils.convertEntityToDto(snapshot, TCSupplierPolicySnapshotDTO.class);
	}
	@Override
	public List<TCSupplierPolicySnapshotDTO> queryList(TCSupplierPolicySnapshotQO qo) {
		plfx.mp.app.pojo.qo.TCSupplierPolicySnapshotQO snapshotQO = parseQo(qo);
		List<TCSupplierPolicySnapshot> list = service.queryList(snapshotQO);
		return EntityConvertUtils.convertEntityToDtoList(list, TCSupplierPolicySnapshotDTO.class);
	}

	@SuppressWarnings("unchecked")
	@Override
	public Pagination queryPagination(Pagination pagination) {
		Pagination pagination2 = parsePaginationQo(pagination);
		pagination2.setPageNo(pagination.getPageNo());
		Pagination pagination3 = service.queryPagination(pagination2);
		List<TCSupplierPolicySnapshot> list = (List<TCSupplierPolicySnapshot>) pagination3.getList();
		pagination3.setList(EntityConvertUtils.convertEntityToDtoList(list, TCSupplierPolicySnapshotDTO.class));
		pagination3.setCondition(pagination.getCondition());
		return pagination3;
	}

	
	

}
