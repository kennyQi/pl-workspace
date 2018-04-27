package plfx.jd.app.service.local;

import hg.common.component.BaseServiceImpl;
import hg.log.util.HgLogger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import plfx.jd.domain.model.hotel.YLHotel;
import plfx.jd.domain.model.hotel.YLHotelList;
import plfx.jd.app.common.util.EntityConvertUtils;
import plfx.jd.app.dao.HotelDAO;
import plfx.jd.app.dao.HotelListDAO;
import plfx.jd.pojo.dto.plfx.hotel.YLFacilitiesV2DTO;
import plfx.jd.pojo.dto.plfx.hotel.YLHotelDTO;
import plfx.jd.pojo.dto.plfx.hotel.YLHotelDetailDTO;
import plfx.jd.pojo.dto.plfx.hotel.YLServiceRankDTO;
import plfx.jd.pojo.qo.YLHotelListQO;
import plfx.jd.pojo.qo.YLHotelQO;

import com.alibaba.fastjson.JSON;

@Service
@Transactional
public class HotelListLocalService  extends BaseServiceImpl<YLHotelList, YLHotelListQO, HotelListDAO>{
	
	
	@Autowired
	private  HotelListDAO hotelListDAO;
	
	@Autowired
	private  HotelDAO hotelDAO;
	
	@Override
	protected HotelListDAO getDao() {
		return hotelListDAO;
	}

    /****
     * 
     * @方法功能说明：本地酒店详情查询
     * @修改者名字：yaosanfeng
     * @修改时间：2015年7月20日下午3:35:26
     * @修改内容：
     * @参数：@param qo
     * @参数：@return
     * @return:YLHotelDTO
     * @throws
     */
	public YLHotelDTO searchByHotelId(YLHotelQO qo) {
		YLHotelDTO ylHotelDTO=new YLHotelDTO();
		YLHotel ylHotel=hotelDAO.queryUnique(qo);
		if(ylHotel != null){
			ylHotelDTO=EntityConvertUtils.convertDtoToEntity(ylHotel,YLHotelDTO.class);
			HgLogger.getInstance().info("yaosanfeng", "HotelListLocalService->searchBYHotelID->ylHotelDTO:" + JSON.toJSONString(ylHotelDTO));
		}
		ylHotelDTO = this.covert(ylHotelDTO,ylHotel);
		HgLogger.getInstance().info("yaosanfeng", "HotelListLocalService->searchBYHotelID->[转化后的酒店详情]:" + JSON.toJSONString(ylHotelDTO));
		return ylHotelDTO;
	}

	private YLHotelDTO covert(YLHotelDTO ylHotelDTO, YLHotel ylHotel) {
		YLHotelDetailDTO ylHotelDetailDTO=new YLHotelDetailDTO();
		ylHotelDetailDTO.setIntroEditor(ylHotel.getYlHotelDetail().getIntroEditor());
		ylHotelDetailDTO.setAddress(ylHotel.getYlHotelDetail().getAddress());
		ylHotelDetailDTO.setBaiduLat(ylHotel.getYlHotelDetail().getBaiduLat());
		ylHotelDetailDTO.setBaiduLon(ylHotel.getYlHotelDetail().getBaiduLon());
		ylHotelDetailDTO.setGoogleLat(ylHotel.getYlHotelDetail().getGoogleLat());
		ylHotelDetailDTO.setGoogleLon(ylHotel.getYlHotelDetail().getGoogleLon());
		ylHotelDetailDTO.setBrandId(ylHotel.getYlHotelDetail().getBrandId());
		ylHotelDetailDTO.setBusinessZone(ylHotel.getYlHotelDetail().getBusinessZone());
		ylHotelDetailDTO.setCategory(ylHotel.getYlHotelDetail().getCategory());
		ylHotelDetailDTO.setCityId(ylHotel.getYlHotelDetail().getCityId());
		ylHotelDetailDTO.setConferenceAmenities(ylHotel.getYlHotelDetail().getConferenceAmenities());
		ylHotelDetailDTO.setDescription(ylHotel.getYlHotelDetail().getDescription());
		ylHotelDetailDTO.setCreditCards(ylHotel.getYlHotelDetail().getCreditCards());
		ylHotelDetailDTO.setDiningAmenities(ylHotel.getYlHotelDetail().getDiningAmenities());
		ylHotelDetailDTO.setDistrict(ylHotel.getYlHotelDetail().getDistrict());
		ylHotelDetailDTO.setEstablishmentDate(ylHotel.getYlHotelDetail().getEstablishmentDate());
		ylHotelDetailDTO.setFacilities(ylHotel.getYlHotelDetail().getFacilities());
		ylHotelDetailDTO.setGeneralAmenities(ylHotel.getYlHotelDetail().getGeneralAmenities());
		ylHotelDetailDTO.setGroupId(ylHotel.getYlHotelDetail().getGroupId());
		ylHotelDetailDTO.setHasCoupon(ylHotel.getYlHotelDetail().getHasCoupon());
		ylHotelDetailDTO.setIsApartment(ylHotel.getYlHotelDetail().getIsApartment());
		ylHotelDetailDTO.setIsEconomic(ylHotel.getYlHotelDetail().getIsEconomic());
		ylHotelDetailDTO.setName(ylHotel.getYlHotelDetail().getName());
		ylHotelDetailDTO.setPhone(ylHotel.getYlHotelDetail().getPhone());
		ylHotelDetailDTO.setRecreationAmenities(ylHotel.getYlHotelDetail().getRecreationAmenities());
		ylHotelDetailDTO.setRenovationDate(ylHotel.getYlHotelDetail().getRenovationDate());
		ylHotelDetailDTO.setRoomAmenities(ylHotel.getYlHotelDetail().getRoomAmenities());
		ylHotelDetailDTO.setStarRate(ylHotel.getYlHotelDetail().getStarRate());
		ylHotelDetailDTO.setTraffic(ylHotel.getYlHotelDetail().getTraffic());
		ylHotelDTO.setYlHotelDetailDTO(ylHotelDetailDTO);
		YLServiceRankDTO ylServiceRankDTO = new YLServiceRankDTO();
		ylServiceRankDTO.setBookingSuccessRate(ylHotel.getServiceRank().getBookingSuccessRate());
		ylServiceRankDTO.setBookingSuccessScore(ylHotel.getServiceRank().getBookingSuccessScore());
		ylServiceRankDTO.setComplaintRate(ylHotel.getServiceRank().getComplaintRate());
		ylServiceRankDTO.setComplaintScore(ylHotel.getServiceRank().getComplaintScore());
		ylServiceRankDTO.setInstantConfirmRate(ylHotel.getServiceRank().getInstantConfirmRate());
		ylServiceRankDTO.setInstantConfirmScore(ylHotel.getServiceRank().getInstantConfirmScore());
		ylServiceRankDTO.setSummaryRate(ylHotel.getServiceRank().getSummaryRate());
		ylServiceRankDTO.setSummaryScore(ylHotel.getServiceRank().getSummaryScore());
		ylHotelDTO.setServiceRankDTO(ylServiceRankDTO);
		YLFacilitiesV2DTO ylFacilitiesV2DTO=new  YLFacilitiesV2DTO();
		ylFacilitiesV2DTO.setGeneralAmenities(ylHotel.getFacilitiesV2().getGeneralAmenities());
		ylFacilitiesV2DTO.setRecreationAmenities(ylHotel.getFacilitiesV2().getRecreationAmenities());
		ylFacilitiesV2DTO.setServiceAmenities(ylHotel.getFacilitiesV2().getServiceAmenities());
		ylHotelDTO.setFacilitiesV2DTO(ylFacilitiesV2DTO);
		return ylHotelDTO;
	}
 
}
