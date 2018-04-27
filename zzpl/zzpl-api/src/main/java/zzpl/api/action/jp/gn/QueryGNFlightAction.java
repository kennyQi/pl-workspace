package zzpl.api.action.jp.gn;

import hg.log.util.HgLogger;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import zzpl.api.action.CommonAction;
import zzpl.api.client.base.ApiRequest;
import zzpl.api.client.base.ApiResponse;
import zzpl.api.client.dto.jp.GNFlightListDTO;
import zzpl.api.client.request.jp.GNFlightQO;
import zzpl.api.client.response.jp.GNFlightResponse;
import zzpl.app.service.local.jp.gn.GNFlightService;
import zzpl.pojo.dto.jp.plfx.gn.FlightGNDTO;
import zzpl.pojo.dto.jp.plfx.gn.JPQueryFlightListGNDTO;
import zzpl.pojo.exception.jp.GNFlightException;
import zzpl.pojo.qo.jp.plfx.gn.JPFlightGNQO;
import zzpl.pojo.util.UserSecurity;

import com.alibaba.fastjson.JSON;

@Component("QueryGNFlightAction")
public class QueryGNFlightAction implements CommonAction{
	@Autowired
	private GNFlightService gnFlightService;
	@Autowired
	private JedisPool jedisPool;

	@Override
	public String execute(ApiRequest apiRequest) {
		long startTime=System.currentTimeMillis(); 
		GNFlightResponse gnFlightResponse = new GNFlightResponse();
		try {
			GNFlightQO gnFlightQO = JSON.parseObject(apiRequest.getBody().getPayload(), GNFlightQO.class);
			HgLogger.getInstance().info("cs", "【QueryGNFlightAction】"+"gnFlightQO:"+JSON.toJSONString(gnFlightQO));
			JPFlightGNQO jpFlightGNQO = JSON.parseObject(JSON.toJSONString(gnFlightQO),JPFlightGNQO.class);
			JPQueryFlightListGNDTO jpQueryFlightListGNDTO = new JPQueryFlightListGNDTO();
			//创建返回对象
			List<GNFlightListDTO> gnFlightListDTOs = new ArrayList<GNFlightListDTO>();
			//通过分销查询航班列表
			jpQueryFlightListGNDTO = gnFlightService.queryGNFlight(jpFlightGNQO);
			long startTime1=System.currentTimeMillis(); 
			HgLogger.getInstance().info("cs", "【QueryGNFlightAction】"+"jpQueryFlightListGNDTO:"+JSON.toJSONString(jpQueryFlightListGNDTO));
			//初始化jedis
			Jedis jedis = null;
			jedis = jedisPool.getResource();
			//获取当前登录人
			UserSecurity userSecurity = JSON.parseObject(jedis.get("ZZPL_"+apiRequest.getHead().getSessionID()),UserSecurity.class);
			//jedis中航班列表ID
			int i=0;
			//将分销返回的结果转化为本地DTO
			if(jpQueryFlightListGNDTO!=null&&jpQueryFlightListGNDTO.getFlightList()!=null){
				for(FlightGNDTO flightGNDTO:jpQueryFlightListGNDTO.getFlightList()){
					GNFlightListDTO gnFlightListDTO = new GNFlightListDTO();
					//航空公司
					gnFlightListDTO.setAirComp(flightGNDTO.getAirComp());
					//航空公司名字
					gnFlightListDTO.setAirCompanyName(flightGNDTO.getAirCompanyName());
					//到港机场
					gnFlightListDTO.setArrivalAirport(flightGNDTO.getDstCityName());
					//到港航站楼
					gnFlightListDTO.setArrivalTerm(flightGNDTO.getArrivalTerm());
					//机场建设费
					gnFlightListDTO.setBuildFee(jpQueryFlightListGNDTO.getBuildFee());
					//折扣
					gnFlightListDTO.setCabinDiscount(flightGNDTO.getCabinList().get(flightGNDTO.getCabinList().size()-1).getCabinDiscount());
					//舱位名
					gnFlightListDTO.setCabinName(flightGNDTO.getCabinList().get(flightGNDTO.getCabinList().size()-1).getCabinName());
					//离港机场
					gnFlightListDTO.setDepartAirport(flightGNDTO.getOrgCityName());
					//离港航站楼
					gnFlightListDTO.setDepartTerm(flightGNDTO.getDepartTerm());
					//到达城市
					gnFlightListDTO.setDstCity(flightGNDTO.getDstCity());
					//落地时间
					gnFlightListDTO.setEndTime(flightGNDTO.getEndTime());
					//航班在jedis中主键
					gnFlightListDTO.setFlightID(String.valueOf(i));
					//航班号
					gnFlightListDTO.setFlightNO(flightGNDTO.getFlightno());
					//票面价
					gnFlightListDTO.setIbePrice(flightGNDTO.getCabinList().get(flightGNDTO.getCabinList().size()-1).getIbePrice());
					//燃油附加费
					gnFlightListDTO.setOilFee(jpQueryFlightListGNDTO.getOilFee());
					//出发城市
					gnFlightListDTO.setOrgCity(flightGNDTO.getOrgCity());
					//机型
					gnFlightListDTO.setPlaneType(flightGNDTO.getPlaneType());
					//出发时间
					gnFlightListDTO.setStartTime(flightGNDTO.getStartTime());
					gnFlightListDTOs.add(gnFlightListDTO);
					i++;
				}
			}
			if(StringUtils.isNotBlank(gnFlightQO.getOrderBy())){
				//按照价格排序
				if(StringUtils.equals(gnFlightQO.getOrderBy(),String.valueOf(GNFlightQO.IBE_PRICE))){
					if(StringUtils.isNotBlank(gnFlightQO.getOrderType())){
						//正序
						if(StringUtils.equals(gnFlightQO.getOrderType(),String.valueOf(GNFlightQO.ASC))){
							Collections.sort(gnFlightListDTOs, new Comparator<GNFlightListDTO>() {
								@Override
								public int compare(GNFlightListDTO o1, GNFlightListDTO o2) {
									return Integer.valueOf(o1.getIbePrice()).compareTo(Integer.valueOf(o2.getIbePrice()));
								}
							});
						}
						//倒序
						if(StringUtils.equals(gnFlightQO.getOrderType(),String.valueOf(GNFlightQO.DESC))){
							Collections.sort(gnFlightListDTOs, new Comparator<GNFlightListDTO>() {
								@Override
								public int compare(GNFlightListDTO o1, GNFlightListDTO o2) {
									return Integer.valueOf(o2.getIbePrice()).compareTo(Integer.valueOf(o1.getIbePrice()));
								}
							});
						}
					}
				}
				//起飞时间
				if(StringUtils.equals(gnFlightQO.getOrderBy(),String.valueOf(GNFlightQO.START_TIME))){
					if(StringUtils.isNotBlank(gnFlightQO.getOrderType())){
						//正序
						if(StringUtils.equals(gnFlightQO.getOrderType(),String.valueOf(GNFlightQO.ASC))){
							Collections.sort(gnFlightListDTOs, new Comparator<GNFlightListDTO>() {
								@Override
								public int compare(GNFlightListDTO o1, GNFlightListDTO o2) {
									return o1.getStartTime().compareTo(o2.getStartTime());
								}
							});
						}
						//倒序
						if(StringUtils.equals(gnFlightQO.getOrderType(),String.valueOf(GNFlightQO.DESC))){
							Collections.sort(gnFlightListDTOs, new Comparator<GNFlightListDTO>() {
								@Override
								public int compare(GNFlightListDTO o1, GNFlightListDTO o2) {
									return o2.getStartTime().compareTo(o1.getStartTime());
								}
							});
						}
					}
				}
			}
			jedis.setex("ZZPL_GNFlightList_"+userSecurity.getUserID(),600,JSON.toJSONString(jpQueryFlightListGNDTO.getFlightList()));
			HgLogger.getInstance().info("cs", "【QueryGNFlightAction】"+"jpQueryFlightListGNDTO:"+JSON.toJSONString(jpQueryFlightListGNDTO));
			jedisPool.returnResource(jedis);
			gnFlightResponse.setFlightGNListDTOs(gnFlightListDTOs);
			gnFlightResponse.setResult(ApiResponse.RESULT_CODE_OK);
			gnFlightResponse.setMessage("查询成功");
			long endTime1=System.currentTimeMillis(); 
			HgLogger.getInstance().info("cs", "【QueryGNFlightAction】【运行时间】"+"查询航班本地化处理时间： "+(endTime1-startTime1)+"ms");
		} catch (GNFlightException e) {
			HgLogger.getInstance().info("cs", "【QueryGNFlightAction】"+"国内航班查询失败，"+HgLogger.getStackTrace(e));
			gnFlightResponse.setResult(e.getCode());
			gnFlightResponse.setMessage(e.getMessage());
		}
		HgLogger.getInstance().info("cs", "【QueryGNFlightAction】"+"gnFlightResponse:"+JSON.toJSONString(gnFlightResponse));
		long endTime=System.currentTimeMillis(); 
		HgLogger.getInstance().info("cs", "【QueryGNFlightAction】【运行时间】"+"查询航班程序运行时间： "+(endTime-startTime)+"ms");
		return JSON.toJSONString(gnFlightResponse);
	}
}

