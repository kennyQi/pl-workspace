package hsl.app.service.spi.hotel;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;

import slfx.api.base.ApiRequest;
import slfx.api.base.SlfxApiClient;
import slfx.api.v1.request.qo.jd.JDHotelDetailApiQO;
import slfx.api.v1.request.qo.jd.JDHotelListApiQO;
import slfx.api.v1.request.qo.jd.JDLocalHotelDetailApiQO;
import slfx.api.v1.response.jd.JDHotelListResponse;
import slfx.api.v1.response.jd.JDLocalHotelDetailResponse;
import hg.common.page.Pagination;
import hg.common.util.BeanMapperUtils;
import hg.common.util.EntityConvertUtils;
import hg.common.util.UUIDGenerator;
import hg.log.util.HgLogger;
import hsl.app.common.util.ClientKeyUtil;
import hsl.app.common.util.Tool;
import hsl.pojo.dto.hotel.HotelDTO;
import hsl.pojo.dto.hotel.JDLocalHotelDetailDTO;
import hsl.pojo.exception.HotelException;
import hsl.pojo.log.PLZXClickRecord;
import hsl.pojo.qo.hotel.JDHotelDetailQO;
import hsl.pojo.qo.hotel.JDHotelListQO;
import hsl.pojo.qo.hotel.JDLocalHotelDetailQO;
import hsl.pojo.qo.log.PLZXClickRecordQo;
import hsl.spi.inter.clickRecord.PLZXClickRecordService;
import hsl.spi.inter.hotel.HslHotelService;
/**
 * @类功能说明：直销酒店Service实现类
 * @类修改者：
 * @修改日期：
 * @修改说明：
 * @公司名称：浙江票量云数据科技股份有限公司
 * @部门：技术部
 * @作者：renfeng
 * @创建时间：2015年7月1日下午3:49:30
 * @版本：V1.5
 *
 */
@Service
public class HslHotelServiceImpl  implements HslHotelService {
	@Autowired
	private SlfxApiClient slfxApiClient;
	@Autowired
	private PLZXClickRecordService plzxClickRecordService;

	@Override
	public Pagination queryPagination(Pagination pagination) throws HotelException{
		HgLogger.getInstance().info("renfeng", "HslHotelServiceImpl->queryPagination（直销）查询酒店分页查询（pagination）:"+JSON.toJSONString(pagination));
		
		JDHotelListQO qo=(JDHotelListQO) pagination.getCondition();
		//入住日期、离店日期、城市编码
		if(qo==null||qo.getArrivalDate()==null||qo.getDepartureDate()==null||StringUtils.isBlank(qo.getCityId())||StringUtils.isBlank(qo.getCustomerType())){
			throw new HotelException(HotelException.QO_ERROR,JSON.toJSONString(pagination));
		}
		
		JDHotelListApiQO apiQo =new JDHotelListApiQO();
		
		apiQo=BeanMapperUtils.getMapper().map(qo,JDHotelListApiQO.class);
		ApiRequest apiRequest=new ApiRequest("JDHotelList",ClientKeyUtil.FROM_CLIENT_KEY, "127.0.0.1", UUIDGenerator.getUUID(), apiQo);
		
		JDHotelListResponse response = slfxApiClient.send(apiRequest, JDHotelListResponse.class);
		HgLogger.getInstance().info("renfeng", "HslHotelServiceImpl->queryPagination（直销）酒店分页查询,分销查询是否成功:"+response.getResult());
		
		if(response!=null&&response.getHotelListResultDTO()!=null&&response.getHotelListResultDTO().getHotels().size()>0){
			HgLogger.getInstance().info("renfeng", "HslHotelServiceImpl->queryPagination（直销）酒店分页查询,分销返回结果:"+JSON.toJSONString(response));
			pagination.setTotalCount(response.getHotelListResultDTO().getCount());
			pagination.setList(BeanMapperUtils.getMapper().mapAsList (response.getHotelListResultDTO().getHotels(),HotelDTO.class));
			
			HgLogger.getInstance().info("renfeng", "HslHotelServiceImpl->queryPagination（直销）酒店分页查询  结果:"+JSON.toJSONString(pagination));
		}
		return pagination;
	}

	@Override
	public HotelDTO queryHotelDetail(JDHotelDetailQO qo) throws HotelException{
		HgLogger.getInstance().info("renfeng", "HslHotelServiceImpl->queryHotelDetail（直销）查询酒店明细qo:"+JSON.toJSONString(qo));
		
		if(qo==null||qo.getArrivalDate()==null||qo.getDepartureDate()==null||StringUtils.isBlank(qo.getHotelIds())){
			throw new HotelException(HotelException.QO_ERROR,JSON.toJSONString(qo));
		}
		HotelDTO hotelDto=null;
		//直销QO转换为分销apiQO
		JDHotelDetailApiQO apiQo =new JDHotelDetailApiQO();
		apiQo=BeanMapperUtils.getMapper().map(qo,JDHotelDetailApiQO.class);
		
		ApiRequest apiRequest=new ApiRequest("JDHotelDetail",ClientKeyUtil.FROM_CLIENT_KEY, "127.0.0.1", UUIDGenerator.getUUID(), apiQo);
		
		JDHotelListResponse response = slfxApiClient.send(apiRequest, JDHotelListResponse.class);
		HgLogger.getInstance().info("renfeng", "HslHotelServiceImpl->queryHotelDetail（直销）查询酒店明细,分销查询是否成功:"+response.getResult());
		
		if(response!=null&&response.getHotelListResultDTO()!=null&&response.getHotelListResultDTO().getHotels().size()>0){
			HgLogger.getInstance().info("renfeng", "HslHotelServiceImpl->queryHotelDetail（直销）查询酒店明细,分销返回结果:"+JSON.toJSONString(response));
			hotelDto=EntityConvertUtils.convertDtoToEntity( response.getHotelListResultDTO().getHotels().get(0),HotelDTO.class);
			
			HgLogger.getInstance().info("renfeng", "HslHotelServiceImpl->queryHotelDetail（直销）查询酒店明细 结果:"+JSON.toJSONString(hotelDto));
		}
		return hotelDto;
	}

	@Override
	public JDLocalHotelDetailDTO queryHotelLocalDetail(JDLocalHotelDetailQO qo) throws HotelException{
		HgLogger.getInstance().info("renfeng", "HslHotelServiceImpl->queryHotelLocalDetail（直销本地查询）查询酒店明细qo:"+JSON.toJSONString(qo));
		
		if(qo==null||StringUtils.isBlank(qo.getHotelId())){
			throw new HotelException(HotelException.QO_ERROR,JSON.toJSONString(qo));
		}
		JDLocalHotelDetailDTO hotelDto=null;
		//直销QO转换为分销apiQO
		JDLocalHotelDetailApiQO apiQo =new JDLocalHotelDetailApiQO();
		apiQo=BeanMapperUtils.getMapper().map(qo,JDLocalHotelDetailApiQO.class);
		
		ApiRequest apiRequest=new ApiRequest("JDLocalHotelDetail",ClientKeyUtil.FROM_CLIENT_KEY, "127.0.0.1", UUIDGenerator.getUUID(), apiQo);
		
		JDLocalHotelDetailResponse response = slfxApiClient.send(apiRequest, JDLocalHotelDetailResponse.class);
		HgLogger.getInstance().info("renfeng", "HslHotelServiceImpl->queryHotelLocalDetail（直销本地查询）查询酒店明细,分销查询是否成功:"+response.getResult());
		
		if(response!=null&&response.getYlHotelDTO()!=null){
			HgLogger.getInstance().info("renfeng", "HslHotelServiceImpl->queryHotelLocalDetail（直销本地查询）查询酒店明细,分销返回结果:"+JSON.toJSONString(response));
			hotelDto=EntityConvertUtils.convertDtoToEntity( response.getYlHotelDTO(),JDLocalHotelDetailDTO.class);
			
			HgLogger.getInstance().info("renfeng", "HslHotelServiceImpl->queryHotelLocalDetail（直销本地查询）查询酒店明细 结果:"+JSON.toJSONString(hotelDto));
		}
		return hotelDto;
	}

	@Override
	public List<HotelDTO> queryUserClickRecord(PLZXClickRecordQo plzxClickRecordQo) throws HotelException{
		plzxClickRecordQo.setPageSize(2);
		List<PLZXClickRecord> plzxClickRecords=plzxClickRecordService.queryPLZXClickRecord(plzxClickRecordQo);
		List<HotelDTO> hotelDTOs=new ArrayList<HotelDTO>();
		Date date = new Date();	
		date =  Tool.addDate(date, 1);
		date.setHours(8);
		date.setMinutes(0);
		date.setSeconds(0);
		Date date2 =  Tool.addDate(date, 1);
		date2.setHours(14);
		date2.setMinutes(0);
		date2.setSeconds(0);
		
		for(PLZXClickRecord plzxClickRecord:plzxClickRecords){
			JDHotelDetailQO qo=new JDHotelDetailQO();
			qo.setHotelIds(plzxClickRecord.getObjectId());
			qo.setArrivalDate(date);
			qo.setDepartureDate(date2);
			qo.setOptions("1,2,3,4");
			HotelDTO hotel=queryHotelDetail(qo);
			hotelDTOs.add(hotel);
		}
		
		return hotelDTOs;
	}

}
