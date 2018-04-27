package hsl.app.service.local.hotel;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import hg.common.component.BaseServiceImpl;
import hsl.app.dao.DistrictDao;
import hsl.app.dao.HotelGeoDao;
import hsl.domain.model.hotel.order.District;
import hsl.domain.model.hotel.order.HotelGeo;
import hsl.pojo.dto.hotel.util.DistrictDTO;
import hsl.pojo.dto.hotel.util.HotelGeoDTO;
import hsl.pojo.qo.hotel.DistrictQO;
import hsl.pojo.qo.hotel.HotelGeoQO;
@Service
@Transactional
public class HslDistrictLocalService extends BaseServiceImpl<District, DistrictQO,DistrictDao>{
	@Autowired
	private DistrictDao districtDao;
	@Autowired
	private HotelGeoDao hotelGeoDao;
	@Override
	protected DistrictDao getDao() {
		// TODO Auto-generated method stub
		return this.districtDao;
	}
	public DistrictDTO queryUniqueDistrict(DistrictQO qo) {

		DistrictDTO districtDTO=new DistrictDTO();
		try {
			HotelGeoQO hotelGeoQO=new HotelGeoQO();
			hotelGeoQO.setCityCode(qo.getHotelGeoId());
			HotelGeo  hotelGeo=hotelGeoDao.queryUnique(hotelGeoQO);
			if(hotelGeo!=null){
				qo.setHotelGeoId(hotelGeo.getId());
			}
			District district=districtDao.queryUnique(qo);
			if(district!=null){
				districtDTO.setName(district.getName());
			}
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return districtDTO;
	}


}
