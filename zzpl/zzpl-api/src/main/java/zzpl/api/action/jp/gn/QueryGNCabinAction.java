package zzpl.api.action.jp.gn;

import hg.log.util.HgLogger;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import zzpl.api.action.CommonAction;
import zzpl.api.client.base.ApiRequest;
import zzpl.api.client.base.ApiResponse;
import zzpl.api.client.dto.jp.GNCabinListDTO;
import zzpl.api.client.request.jp.GNCabinQO;
import zzpl.api.client.response.jp.GNCabinResponse;
import zzpl.pojo.dto.jp.plfx.gn.CabinGNDTO;
import zzpl.pojo.dto.jp.plfx.gn.FlightGNDTO;
import zzpl.pojo.exception.jp.GNFlightException;
import zzpl.pojo.util.UserSecurity;

import com.alibaba.fastjson.JSON;
@Component("QueryGNCabinAction")
public class QueryGNCabinAction implements CommonAction{
	@Autowired
	private JedisPool jedisPool;
	@Override
	public String execute(ApiRequest apiRequest) {
		GNCabinResponse gnCabinResponse = new GNCabinResponse();
		try{
			GNCabinQO gnCabinQO = JSON.parseObject(apiRequest.getBody().getPayload(), GNCabinQO.class);
			HgLogger.getInstance().info("cs", "【QueryGNCabinAction】"+"gnCabinQO:"+JSON.toJSONString(gnCabinQO));
			//初始化jedis
			Jedis jedis = null;
			jedis = jedisPool.getResource();
			//获取当前登录人
			UserSecurity userSecurity = JSON.parseObject(jedis.get("ZZPL_"+apiRequest.getHead().getSessionID()),UserSecurity.class);
			jedisPool.returnResource(jedis);
			List<FlightGNDTO> flightGNInfoDTOs = JSON.parseArray(jedis.get("ZZPL_GNFlightList_"+userSecurity.getUserID()), FlightGNDTO.class);
			FlightGNDTO flightGNInfoDTO = flightGNInfoDTOs.get(Integer.parseInt(gnCabinQO.getFlightID()));
			List<GNCabinListDTO> gnCabinListDTOs = new ArrayList<GNCabinListDTO>();
			for(CabinGNDTO cabinGNDTO:flightGNInfoDTO.getCabinList()){
				GNCabinListDTO gnCabinListDTO = new GNCabinListDTO();
				gnCabinListDTO.setCabinDiscount(cabinGNDTO.getCabinDiscount());
				gnCabinListDTO.setCabinName(cabinGNDTO.getCabinName());
				gnCabinListDTO.setCabinSales(cabinGNDTO.getCabinSales());
				gnCabinListDTO.setCabinType(cabinGNDTO.getCabinType());
				gnCabinListDTO.setCabinCode(cabinGNDTO.getCabinCode());
				gnCabinListDTO.setEncryptString(cabinGNDTO.getEncryptString());
				gnCabinListDTO.setIbePrice(cabinGNDTO.getIbePrice());
				gnCabinListDTOs.add(gnCabinListDTO);
			}
			gnCabinResponse.setGnCabinListDTOs(gnCabinListDTOs);
			gnCabinResponse.setResult(ApiResponse.RESULT_CODE_OK);
			gnCabinResponse.setMessage("查询成功");
			HgLogger.getInstance().info("cs", "【QueryGNCabinAction】"+"gnCabinResponse:"+JSON.toJSONString(gnCabinResponse));
		}catch(Exception e){
			HgLogger.getInstance().info("cs", "【QueryGNCabinAction】"+"异常，"+HgLogger.getStackTrace(e));
			gnCabinResponse.setResult(GNFlightException.QUERY_GN_CABIN_FAILED_CODE);
			gnCabinResponse.setMessage(GNFlightException.QUERY_GN_CABIN_FAILED_MESSAGE);
		}
		return JSON.toJSONString(gnCabinResponse);
	}

}
