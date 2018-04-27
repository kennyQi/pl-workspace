package plfx.jd.app.service.spi;

import hg.common.page.Pagination;
import hg.common.util.DateUtil;
import hg.common.util.UUIDGenerator;
import hg.log.util.HgLogger;

import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import javax.annotation.Resource;

import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import plfx.jd.app.service.local.HotelListLocalService;
import plfx.jd.app.service.local.HotelLocalService;
import plfx.jd.domain.model.hotel.YLAvailPolicy;
import plfx.jd.domain.model.hotel.YLFacilitiesV2;
import plfx.jd.domain.model.hotel.YLHelpfulTips;
import plfx.jd.domain.model.hotel.YLHotel;
import plfx.jd.domain.model.hotel.YLHotelDetail;
import plfx.jd.domain.model.hotel.YLHotelImage;
import plfx.jd.domain.model.hotel.YLHotelList;
import plfx.jd.domain.model.hotel.YLHotelRoom;
import plfx.jd.domain.model.hotel.YLLocation;
import plfx.jd.domain.model.hotel.YLReview;
import plfx.jd.domain.model.hotel.YLServiceRank;
import plfx.jd.domain.model.hotel.YLSupplier;
import plfx.yl.ylclient.yl.dto.HotelDataInventoryDTO;
import plfx.yl.ylclient.yl.dto.HotelDataValidateDTO;
import plfx.yl.ylclient.yl.dto.HotelListDTO;
import plfx.yl.ylclient.yl.qo.HotelDetailQO;
import plfx.yl.ylclient.yl.qo.HotelListQO;
import plfx.yl.ylclient.yl.qo.InventoryQO;
import plfx.yl.ylclient.yl.qo.ValidateQO;
import plfx.jd.app.component.base.BaseJDSpiServiceImpl;
import plfx.jd.app.dao.HotelListDAO;
import plfx.jd.pojo.dto.ylclient.hotel.HotelDataInventoryResultDTO;
import plfx.jd.pojo.dto.ylclient.hotel.HotelDataValidateResultDTO;
import plfx.jd.pojo.dto.ylclient.hotel.HotelListResultDTO;
import plfx.jd.pojo.qo.HotelDTO;
import plfx.jd.pojo.qo.YLHotelListQO;
import plfx.jd.pojo.qo.ylclient.JDHotelDetailQO;
import plfx.jd.pojo.qo.ylclient.JDHotelListQO;
import plfx.jd.pojo.qo.ylclient.JDInventoryQO;
import plfx.jd.pojo.qo.ylclient.JDValidateQO;
import plfx.jd.spi.inter.HotelService;

import com.alibaba.fastjson.JSON;

/**
 * 
 * @类功能说明：线路订单service实现
 * @类修改者：
 * @修改日期：
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @作者：tandeng
 * @创建时间：2015年01月27日下午2:55:10
 * @版本：V1.0
 *
 */
@Service("hotelService")
public class HotelServiceImpl extends BaseJDSpiServiceImpl<HotelDTO, YLHotelListQO, HotelLocalService> implements HotelService {

	@Autowired
	private  HotelListDAO hotelListDAO;
	
	@Resource
	private HotelLocalService hotelLocalService;
	
	@Autowired
	private HotelListLocalService hotelListLocalService;
	

	
	private static ExecutorService executor = Executors.newFixedThreadPool(2);
	@Override
	protected HotelLocalService getService() {
		return hotelLocalService;
	}

	@Override
	protected Class<HotelDTO> getDTOClass() {
		return HotelDTO.class;
	}

	@Override
	public HotelListResultDTO queryHotelDetail(JDHotelDetailQO qo) {
		HotelListResultDTO dto = null;
		HotelDetailQO jdHotelDetailQO = JSON.parseObject(JSON.toJSONString(qo),HotelDetailQO.class);
		HgLogger.getInstance().info("yaosanfeng", "HotelServiceImpl->queryHotelDetail->jdHotelDetailQO:" + JSON.toJSONString(jdHotelDetailQO));
		HotelListDTO result = hotelLocalService.queryHotelDetail(jdHotelDetailQO);
		HgLogger.getInstance().info("yaosanfeng", "HotelServiceImpl->queryHotelDetail->[查询酒店详情结果]:" + JSON.toJSONString(result));
		if(result != null){
			dto = new HotelListResultDTO();
			BeanUtils.copyProperties(result, dto);
		}
		return dto;
	}

	@Override
	public HotelListResultDTO queryHotelList(JDHotelListQO qo) {
		HotelListResultDTO dto = null;
		HotelListQO jdHotelListQO = JSON.parseObject(JSON.toJSONString(qo), HotelListQO.class);
		HgLogger.getInstance().info("yaosanfeng", "HotelServiceImpl->queryHotelList->jdHotelListQO:" + JSON.toJSONString(jdHotelListQO));
		HotelListDTO result = hotelLocalService.queryHotelList(jdHotelListQO);
		HgLogger.getInstance().info("yaosanfeng", "HotelServiceImpl->queryHotelList->[查询酒店列表结果]:" + JSON.toJSONString(result));
		if(result != null){
			dto = new HotelListResultDTO();
			BeanUtils.copyProperties(result, dto);
		}
		
		return dto;
	}

	@Override
	public HotelDataInventoryResultDTO queryHotelInventory(JDInventoryQO qo) {
		HotelDataInventoryResultDTO dto = null;
		InventoryQO inventoryQO = new InventoryQO();
		BeanUtils.copyProperties(qo, inventoryQO);
		HotelDataInventoryDTO result = hotelLocalService.queryHotelInventory(inventoryQO);
		if(result != null){
			dto = new HotelDataInventoryResultDTO();
			BeanUtils.copyProperties(result, dto);
			
		}
		return dto;
	}
	
	@Override
	public HotelDataValidateResultDTO queryHotelValidate(JDValidateQO qo) {
		HotelDataValidateResultDTO dto = null;
		ValidateQO validateQO = new ValidateQO();
		BeanUtils.copyProperties(qo, validateQO);
		HotelDataValidateDTO result = hotelLocalService.queryHotelValidate(validateQO);
		if(result != null){
			dto = new HotelDataValidateResultDTO();
			BeanUtils.copyProperties(result, dto);
		}
		return dto;
	}

}
