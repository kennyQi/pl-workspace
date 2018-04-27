package hsl.app.service.local.hotel;
import hg.common.component.BaseServiceImpl;
import hg.common.util.BeanMapperUtils;
import hg.common.util.UUIDGenerator;
import hg.log.util.HgLogger;
import hsl.app.dao.CommericalLocationDao;
import hsl.app.dao.DistrictDao;
import hsl.app.dao.HotelGeoDao;
import hsl.domain.model.hotel.order.CommericalLocation;
import hsl.domain.model.hotel.order.District;
import hsl.domain.model.hotel.order.HotelGeo;
import hsl.pojo.command.HotelGeoCommand;
import hsl.pojo.dto.hotel.util.DistrictDTO;
import hsl.pojo.dto.hotel.util.HotelGeoDTO;
import hsl.pojo.qo.hotel.HotelGeoQO;
import java.text.ParseException;
import java.util.List;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
@Service
@Transactional
public class HotelGeoLocalService extends BaseServiceImpl<HotelGeo, HotelGeoQO, HotelGeoDao>{
	@Autowired
	private HotelGeoDao hotelGeoDao;
	@Autowired
	private DistrictDao districtDao;
	@Autowired
	private CommericalLocationDao commericalLocationDao;

	public void Create(HotelGeoCommand command) throws ParseException{
		try {
			HotelGeo entity = new HotelGeo();
			entity.createHotelGeo(command);
			hotelGeoDao.save(entity);
			List<DistrictDTO> entity1=command.getDistricts();
			if(entity1!=null){
				for(DistrictDTO commericalLocation:entity1){
					District district=new District();
					district.setId(UUIDGenerator.getUUID());
					district.setName(commericalLocation.getName());
					district.setDistrictId(commericalLocation.getDistrictId());
					district.setHotelGeo(entity);
					districtDao.saveOrUpdate(district);
				}
			}
			List<hsl.pojo.dto.hotel.util.CommericalLocationDTO> entity2=command.getCommericalLocations();
			if(entity2!=null){
				for(hsl.pojo.dto.hotel.util.CommericalLocationDTO commericalLocation:entity2){
					CommericalLocation commericalLocations=new CommericalLocation();
					commericalLocations.setHotelGeo(entity);
					commericalLocations.setId(UUIDGenerator.getUUID());
					commericalLocations.setName(commericalLocation.getName());
					commericalLocations.setCommericalLocationId(commericalLocation.getCommericalLocationId());
					commericalLocationDao.saveOrUpdate(commericalLocations);
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	public HotelGeoDTO queryUniqueHotelGeo(HotelGeoQO qo) {
		HotelGeo hotelGeo=hotelGeoDao.queryUnique(qo);
		try {
			List<District> districts =hotelGeo.getDistricts();
			for(District district:districts){
				Hibernate.initialize(district);
			}
			List<CommericalLocation> commericalLocations=hotelGeo.getCommericalLocations();
			for(CommericalLocation commericalLocation:commericalLocations){
				Hibernate.initialize(commericalLocation);
			}
			HotelGeoDTO hotelGeoDTO=BeanMapperUtils.map(hotelGeo, HotelGeoDTO.class);
/*			String hotelGeoId=hotelGeo.getId();
			DistrictQO districtQO=new DistrictQO();
			districtQO.setHotelGeoId(hotelGeoId);
			List<District> district=districtDao.queryList(districtQO);
			List<DistrictDTO> listDistrictDTO=new ArrayList<DistrictDTO>();
			for(int i=0;i<district.size();i++){
				DistrictDTO districtDTO=new DistrictDTO();
				districtDTO.setName(district.get(i).getName());
				districtDTO.setDistrictId(district.get(i).getDistrictId());
				listDistrictDTO.add(districtDTO);
			}
			CommericalLocationQO commericalLocationQO=new CommericalLocationQO();
			commericalLocationQO.setHotelGeoId(hotelGeoId);
			List<CommericalLocation> commericalLocation=commericalLocationDao.queryList(commericalLocationQO);
			List<CommericalLocationDTO> listCommericalLocation=new ArrayList<CommericalLocationDTO>();
			for(int i=0;i<commericalLocation.size();i++){
				CommericalLocationDTO commericalLocationDTO=new CommericalLocationDTO();
				commericalLocationDTO.setName(commericalLocation.get(i).getName());
				commericalLocationDTO.setCommericalLocationId(commericalLocation.get(i).getCommericalLocationId());
				listCommericalLocation.add(commericalLocationDTO);
			}
			HotelGeoDTO hotelGeoDTO=BeanMapperUtils.map(hotelGeo, HotelGeoDTO.class);
			hotelGeoDTO.setCommericalLocations(listCommericalLocation);
			hotelGeoDTO.setDistricts(listDistrictDTO);*/
			return hotelGeoDTO;
		} catch (Exception e) {
			e.printStackTrace();
			HgLogger.getInstance().error("zhaows","queryUniqueHotelGeo->查询商圈"+ HgLogger.getStackTrace(e));
			return null;
		}
	}
	@Override
	public HotelGeoDao getDao() {
		return hotelGeoDao;
	}

}
