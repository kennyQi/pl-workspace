package plfx.mp.app.service.spi;

import hg.log.util.HgLogger;
import hg.system.cache.AddrProjectionCacheManager;
import hg.system.model.meta.Area;
import hg.system.model.meta.City;
import hg.system.model.meta.Province;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import plfx.api.client.api.v1.mp.request.qo.MPPolicyQO;
import plfx.api.client.api.v1.mp.response.MPQueryPolicyResponse;
import plfx.mp.app.common.util.EntityConvertUtils;
import plfx.mp.app.service.local.TCSupplierPolicySnapshotLocalService;
import plfx.mp.domain.model.supplierpolicy.TCSupplierPolicySnapshot;
import plfx.mp.pojo.dto.supplierpolicy.TCSupplierPolicySnapshotDTO;
import plfx.mp.spi.inter.api.ApiMPPolicyService;

@Service
public class ApiMPPolicyServiceImpl implements ApiMPPolicyService {
	
	@Autowired
	private TCSupplierPolicySnapshotLocalService tcSupplierPolicySnapshotLocalService;
	
	@Autowired
	private AddrProjectionCacheManager addrProjectionCacheManager;
	@Override
	public MPQueryPolicyResponse apiQueryPolicy(MPPolicyQO qo) {
		HgLogger.getInstance().info("wuyg", "景区政策查询");
		MPQueryPolicyResponse response = new MPQueryPolicyResponse();
		List<TCSupplierPolicySnapshotDTO> dtolist = new ArrayList<TCSupplierPolicySnapshotDTO>();
		
		try {
			if (StringUtils.isNotBlank(qo.getPolicyId())) {
				TCSupplierPolicySnapshot snapshot = tcSupplierPolicySnapshotLocalService
						.getLast(qo.getPolicyId());
				if (snapshot != null) {
					dtolist.add(EntityConvertUtils.convertEntityToDto(snapshot,
							TCSupplierPolicySnapshotDTO.class));
				} else {
					response.setResult(MPQueryPolicyResponse.RESULT_CODE_FAILE);
				}
			} else if (StringUtils.isNotBlank(qo.getScenicSpotId())) {
				List<TCSupplierPolicySnapshot> list = tcSupplierPolicySnapshotLocalService
						.getListByScenicSpotId(qo.getScenicSpotId());
				dtolist = EntityConvertUtils.convertEntityToDtoList(list,
						TCSupplierPolicySnapshotDTO.class);
			}
			// 设置省市区名称
			for (TCSupplierPolicySnapshotDTO dto : dtolist) {
				if (dto == null || dto.getScenicSpotSnapshot() == null)
					continue;
				Province p = addrProjectionCacheManager.getProvince(dto.getScenicSpotSnapshot().getProvinceCode());
				City c = addrProjectionCacheManager.getCity(dto.getScenicSpotSnapshot().getCityCode());
				Area a = addrProjectionCacheManager.getArea(dto.getScenicSpotSnapshot().getAreaCode());
				dto.getScenicSpotSnapshot().setProvinceName(p == null ? "" : p.getName());
				dto.getScenicSpotSnapshot().setCityName(c == null ? "" : c.getName());
				dto.getScenicSpotSnapshot().setAreaName(a == null ? "" : a.getName());
			}
		} catch (Exception e) {
			e.printStackTrace();
			HgLogger.getInstance().error("wuyg", "景区政策查询：失败 ,"+e.getMessage());
			response.setResult(MPQueryPolicyResponse.RESULT_CODE_FAILE);
		}

		response.setPolicies(dtolist);
		response.setTotalCount(dtolist.size());

		return response;
	}
	
	
}
