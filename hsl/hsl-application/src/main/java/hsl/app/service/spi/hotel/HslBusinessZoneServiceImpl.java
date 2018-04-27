package hsl.app.service.spi.hotel;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import hg.common.page.Pagination;
import hsl.app.component.base.BaseSpiServiceImpl;
import hsl.app.service.local.hotel.HslBusinessZoneLocalService;
import hsl.pojo.dto.hotel.util.CommericalLocationDTO;
import hsl.pojo.qo.hotel.CommericalLocationQO;
import hsl.spi.inter.hotel.HslBusinessZoneService;
@Service
public class HslBusinessZoneServiceImpl extends BaseSpiServiceImpl<CommericalLocationDTO, CommericalLocationQO, HslBusinessZoneLocalService> implements HslBusinessZoneService {
	@Autowired 
	private HslBusinessZoneLocalService hslBusinessZoneLocalService;
	@Override
	public CommericalLocationDTO queryUnique(CommericalLocationQO qo) {
		return hslBusinessZoneLocalService.queryUniqueHotelGeo(qo);
	}

	@Override
	public List<CommericalLocationDTO> queryList(CommericalLocationQO qo) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Pagination queryPagination(Pagination pagination) {
		// TODO Auto-generated method stub
		return null;
	}

	

	@Override
	protected Class<CommericalLocationDTO> getDTOClass() {
		return CommericalLocationDTO.class;
	}

	@Override
	protected HslBusinessZoneLocalService getService() {
		// TODO Auto-generated method stub
		return hslBusinessZoneLocalService;
	}

}
