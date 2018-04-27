package hsl.app.service.local.hotel;


import hg.common.component.BaseServiceImpl;
import hsl.app.dao.CommericalLocationDao;
import hsl.domain.model.hotel.order.CommericalLocation;
import hsl.pojo.dto.hotel.util.CommericalLocationDTO;
import hsl.pojo.qo.hotel.CommericalLocationQO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
@Service
@Transactional
public class HslBusinessZoneLocalService extends BaseServiceImpl<CommericalLocation, CommericalLocationQO,CommericalLocationDao>{
	@Autowired
	private CommericalLocationDao commericalLocationDao;
	@Override
	protected CommericalLocationDao getDao() {
		
		return commericalLocationDao;
	}
	public CommericalLocationDTO queryUniqueHotelGeo(CommericalLocationQO qo) {
		CommericalLocationDTO commericalLocationDTO=new CommericalLocationDTO();
		try {
			
			CommericalLocation commericalLocation=commericalLocationDao.queryUnique(qo);
			commericalLocationDTO.setName(commericalLocation.getName());
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return commericalLocationDTO;
	}
	
}
