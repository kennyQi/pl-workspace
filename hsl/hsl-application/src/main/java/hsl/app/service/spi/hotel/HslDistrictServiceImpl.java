package hsl.app.service.spi.hotel;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import hg.common.page.Pagination;
import hsl.app.component.base.BaseSpiServiceImpl;
import hsl.app.service.local.hotel.HslDistrictLocalService;
import hsl.pojo.dto.hotel.util.DistrictDTO;
import hsl.pojo.qo.hotel.DistrictQO;
import hsl.spi.inter.hotel.HslDistrictService;
@Service
public class HslDistrictServiceImpl extends BaseSpiServiceImpl<DistrictDTO, DistrictQO, HslDistrictLocalService> implements HslDistrictService {
	@Autowired 
	private HslDistrictLocalService hslDistrictLocalService;

	@Override
	public DistrictDTO queryUnique(DistrictQO qo) {
		// TODO Auto-generated method stub
		return hslDistrictLocalService.queryUniqueDistrict(qo);
	}

	@Override
	public List<DistrictDTO> queryList(DistrictQO qo) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Pagination queryPagination(Pagination pagination) {
		// TODO Auto-generated method stub
		return null;
	}

	

	@Override
	protected Class<DistrictDTO> getDTOClass() {
		return DistrictDTO.class;
	}

	@Override
	protected HslDistrictLocalService getService() {
		// TODO Auto-generated method stub
		return hslDistrictLocalService;
	}



}
