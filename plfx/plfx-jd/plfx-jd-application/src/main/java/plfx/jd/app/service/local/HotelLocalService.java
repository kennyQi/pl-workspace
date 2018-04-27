package plfx.jd.app.service.local;

import hg.common.component.BaseServiceImpl;
import hg.log.repository.DomainEventRepository;
import hg.log.util.HgLogger;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import plfx.jd.app.dao.HotelDAO;
import plfx.jd.domain.model.hotel.YLHotel;
import plfx.jd.domain.model.hotel.YLHotelRoom;
import plfx.jd.pojo.qo.YLHotelQO;
import plfx.yl.ylclient.yl.dto.HotelDataInventoryDTO;
import plfx.yl.ylclient.yl.dto.HotelDataValidateDTO;
import plfx.yl.ylclient.yl.dto.HotelListDTO;
import plfx.yl.ylclient.yl.inter.YLHotelService;
import plfx.yl.ylclient.yl.qo.HotelDetailQO;
import plfx.yl.ylclient.yl.qo.HotelListQO;
import plfx.yl.ylclient.yl.qo.InventoryQO;
import plfx.yl.ylclient.yl.qo.ValidateQO;

import com.alibaba.fastjson.JSON;

import elong.Detail;
import elong.Hotel;
import elong.Room;

/**
 * 
 *@类功能说明：线路订单LOCALSERVICE(操作数据库)实现
 *@类修改者：
 *@修改日期：
 *@修改说明：
 *@公司名称：浙江汇购科技有限公司
 *@部门：技术部
 *@作者：tandeng
 *@创建时间：2015年01月27日下午3:22:46
 *
 */
@Service
@Transactional
public class HotelLocalService extends BaseServiceImpl<YLHotel, YLHotelQO, HotelDAO>{

	@Autowired
	private DomainEventRepository domainEventRepository;

	@Autowired
	private HotelDAO hotelDAO;
	
	@Autowired
	private YLHotelService ylHotelService;
	
	@Autowired
	private HotelListLocalService hotelListLocalService;

	@Override
	protected HotelDAO getDao() {
		return hotelDAO;
	}

	public HotelListDTO queryHotelDetail(HotelDetailQO qo) {
		HgLogger.getInstance().info("yaosanfeng", "queryHotelDetail->:HotelDetailQO" + JSON.toJSONString(qo));
		HotelListDTO hotelListDTO = ylHotelService.queryHotelDetail(qo);
		//查询本地酒店详情放入动态数据中
		YLHotelQO ylHotelQO=new YLHotelQO();
		ylHotelQO.setHotelId(qo.getHotelIds());
		YLHotel ylHotel=hotelDAO.queryUnique(ylHotelQO);
		if(null == ylHotel){
			HgLogger.getInstance().error("yaosanfeng", "queryHotelDetail->[查询本地酒店详情异常]:" + JSON.toJSONString(ylHotel));
		}else{
			hotelListDTO = this.covert(hotelListDTO,ylHotel);
			HgLogger.getInstance().info("yaosanfeng", "queryHotelDetail->hotelListDTO:" + JSON.toJSONString(hotelListDTO));
		}
       
		return hotelListDTO;
	}

	private HotelListDTO covert(HotelListDTO hotelListDTO, YLHotel ylHotel) {
		List<Hotel> hotelList = new ArrayList<Hotel>();
		List<Room> roomList = new ArrayList<Room>();
		for(Hotel hotel:hotelListDTO.getHotels()){
			if(hotel != null && ylHotel !=null && ylHotel.getYlHotelDetail() != null){
				    Detail hotelDetail = new Detail();
				 // if(StringUtils.isNotBlank(ylHotel.getYlHotelDetail().getEstablishmentDate())){
					  hotelDetail.setEstablishmentDate(ylHotel.getYlHotelDetail().getEstablishmentDate());
				//  }else if(StringUtils.isNotBlank(ylHotel.getYlHotelDetail().getConferenceAmenities())){
					  hotelDetail.setConferenceAmenities(ylHotel.getYlHotelDetail().getConferenceAmenities());
				//  }else if(StringUtils.isNotBlank(ylHotel.getYlHotelDetail().getCreditCards())){
					  hotelDetail.setCreditCards(ylHotel.getYlHotelDetail().getCreditCards());
				//  }else if(StringUtils.isNotBlank(ylHotel.getYlHotelDetail().getDescription())){
					  hotelDetail.setDescription(ylHotel.getYlHotelDetail().getDescription());
				//  }else if(StringUtils.isNotBlank(ylHotel.getYlHotelDetail().getDiningAmenities())){
					  hotelDetail.setDiningAmenities(ylHotel.getYlHotelDetail().getDiningAmenities());  
				//  }else if(StringUtils.isNotBlank(ylHotel.getYlHotelDetail().getGeneralAmenities())){
					  hotelDetail.setGeneralAmenities(ylHotel.getYlHotelDetail().getGeneralAmenities());
				//  }else if(StringUtils.isNotBlank(ylHotel.getYlHotelDetail().getIntroEditor())){
					  hotelDetail.setIntroEditor(ylHotel.getYlHotelDetail().getIntroEditor());
				//  }else if(StringUtils.isNotBlank(ylHotel.getYlHotelDetail().getRecreationAmenities())){
					  hotelDetail.setRecreationAmenities(ylHotel.getYlHotelDetail().getRecreationAmenities());
				//  }else if(StringUtils.isNotBlank(ylHotel.getYlHotelDetail().getRoomAmenities())){
					  hotelDetail.setRoomAmenities(ylHotel.getYlHotelDetail().getRoomAmenities()); 
				//  }else if(StringUtils.isNotBlank(ylHotel.getYlHotelDetail().getName())){
					  hotelDetail.setHotelName(ylHotel.getYlHotelDetail().getName());
				//  }else if(StringUtils.isNotBlank(ylHotel.getYlHotelDetail().getAddress())){
					  hotelDetail.setAddress(ylHotel.getYlHotelDetail().getAddress());
				//  }else if(StringUtils.isNotBlank(ylHotel.getYlHotelDetail().getPhone())){
					  hotelDetail.setPhone(ylHotel.getYlHotelDetail().getPhone());
			//	  }else if(StringUtils.isNotBlank(ylHotel.getYlHotelDetail().getBaiduLat())){
					  hotelDetail.setLatitude(ylHotel.getYlHotelDetail().getBaiduLat());
			//	  }else if(StringUtils.isNotBlank(ylHotel.getYlHotelDetail().getBaiduLon())){
					  hotelDetail.setLongitude(ylHotel.getYlHotelDetail().getBaiduLon());  
				//  }else if(StringUtils.isNotBlank(ylHotel.getYlHotelDetail().getStarRate())){
					  hotelDetail.setStarRate(Integer.parseInt(ylHotel.getYlHotelDetail().getStarRate()));  
				//  }else if(StringUtils.isNotBlank(ylHotel.getYlHotelDetail().getCategory())){
					  hotelDetail.setCategory(Integer.parseInt(ylHotel.getYlHotelDetail().getCategory()));
			//	  }
					
				  hotel.setDetail(hotelDetail);
				  hotelList.add(hotel);
				  for(Room room : hotel.getRooms()){
					  for(YLHotelRoom ylHotelRoom : ylHotel.getRoomList()){
						  if(room.getRoomId().equals(ylHotelRoom.getRoomId())){
							  room.setArea(ylHotelRoom.getArea()== null ? "" :ylHotelRoom.getArea());
							  room.setBroadnetAccess(ylHotelRoom.getBroadnetAccess()== null ? "" :ylHotelRoom.getBroadnetAccess());
							  room.setBroadnetFee(ylHotelRoom.getBroadnetFee()== null ? "" :ylHotelRoom.getBroadnetFee());
							  room.setCapacity(ylHotelRoom.getCapacity()== null ? "" :ylHotelRoom.getCapacity());
							  room.setFacilities(ylHotelRoom.getFacilities()== null ? "" :ylHotelRoom.getFacilities()); 
							  roomList.add(room);
						  } 
						  
					  }
				  }
				  hotel.setRooms(roomList);
			}
		}
		hotelListDTO.setHotels(hotelList);
		return hotelListDTO;
	}

	public HotelListDTO queryHotelList(HotelListQO qo) {
		return ylHotelService.queryHotelList(qo);
	}

	public HotelDataInventoryDTO queryHotelInventory(InventoryQO inventoryQO) {
		return ylHotelService.queryHotelInventory(inventoryQO);
	}
	
	public HotelDataValidateDTO queryHotelValidate(ValidateQO qo){
		return ylHotelService.queryOrderValidate(qo);
	}
}
