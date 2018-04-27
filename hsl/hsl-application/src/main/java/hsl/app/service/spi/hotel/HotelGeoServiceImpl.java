package hsl.app.service.spi.hotel;
import java.util.List;

import hg.common.util.BeanMapperUtils;
import hsl.app.component.base.BaseSpiServiceImpl;
import hsl.app.service.local.hotel.HotelGeoLocalService;
import hsl.domain.model.hotel.order.HotelGeo;
import hsl.pojo.command.HotelGeoCommand;
import hsl.pojo.dto.hotel.util.HotelGeoDTO;
import hsl.pojo.dto.line.LineDTO;
import hsl.pojo.qo.hotel.HotelGeoQO;
import hsl.spi.inter.hotel.HotelGeoService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
@Service
public class HotelGeoServiceImpl extends BaseSpiServiceImpl<HotelGeoDTO,HotelGeoQO,HotelGeoLocalService> implements HotelGeoService {
	@Autowired 
	private HotelGeoLocalService hotelGeoLocalService;
	
	public HotelGeoDTO queryUnique(HotelGeoQO qo) {
		return this.hotelGeoLocalService.queryUniqueHotelGeo(qo);
		
	}

	@Override
	public List<HotelGeoDTO> queryList(HotelGeoQO qo) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void create(HotelGeoCommand command) {
		try {
			hotelGeoLocalService.Create(command);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	@Override
	protected HotelGeoLocalService getService() {
		return hotelGeoLocalService;
	}

	@Override
	protected Class<HotelGeoDTO> getDTOClass() {
		return HotelGeoDTO.class;
	}

	

	
	




}
