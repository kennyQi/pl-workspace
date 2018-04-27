package hsl.app.service.local.jp;

import hg.common.util.BeanMapperUtils;
import hg.log.util.HgLogger;
import hsl.app.common.util.ClientKeyUtil;
import hsl.pojo.dto.jp.FlightPolicyDTO;

import java.util.UUID;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSON;

import slfx.api.base.ApiRequest;
import slfx.api.base.SlfxApiClient;
import slfx.api.v1.request.qo.jp.JPPolicyQO;
import slfx.api.v1.response.jp.JPQueryPolicyResponse;

@Service("jpFlightPolicyLocalService")
@Transactional
public class JPFlightPolicyLocalService {

	@Resource
	private SlfxApiClient client;
	
	// 查询航班政策
	public FlightPolicyDTO queryFlightPolicy(JPPolicyQO qo) {
		//tandeng 20140831 add
		qo.setDealerCode(ClientKeyUtil.FROM_CLIENT_KEY);
		
		// 创建要发送的请求对象
		ApiRequest request = new ApiRequest("JPQueryFlightPolicy",ClientKeyUtil.FROM_CLIENT_KEY, "192.168.1.1", UUID.randomUUID().toString(), qo);

		JPQueryPolicyResponse response = null;
		FlightPolicyDTO flightPolicyDTO = null;
		try {
			
			HgLogger.getInstance().info("zhangka", "JPFlightPolicyLocalService->queryFlightPolicy->request[查询航班政策]:" + JSON.toJSONString(request));
			
			// 发送请求
			response = client.send(request, JPQueryPolicyResponse.class);
			
			HgLogger.getInstance().info("zhangka", "JPFlightPolicyLocalService->queryFlightPolicy->response[查询航班政策]:" +JSON.toJSONString(response.getResult()));
			
			if (response != null && !response.getResult().equals("-1") && response.getSlfxFlightPolicyDTO() != null)
				flightPolicyDTO = BeanMapperUtils.getMapper().map(response.getSlfxFlightPolicyDTO(), FlightPolicyDTO.class);
		} catch (Exception e) {
			HgLogger.getInstance().error("zhangka", "JPFlightPolicyLocalService->queryFlightPolicy->exception[查询航班政策]:" + HgLogger.getStackTrace(e));
			return null;
		}
		
		return flightPolicyDTO;
	}

}
