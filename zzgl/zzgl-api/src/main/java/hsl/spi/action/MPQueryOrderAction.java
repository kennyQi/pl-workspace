package hsl.spi.action;

import hg.common.util.BeanMapperUtils;
import hg.log.util.HgLogger;
import hsl.api.base.ApiRequest;
import hsl.api.base.ApiResponse;
import hsl.api.v1.request.qo.mp.MPOrderQO;
import hsl.api.v1.response.mp.MPQueryOrderResponse;
import hsl.pojo.dto.mp.MPOrderDTO;
import hsl.pojo.exception.MPException;
import hsl.pojo.qo.mp.HslMPOrderQO;
import hsl.spi.action.constant.Constants;

import hsl.spi.inter.mp.MPOrderService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.alibaba.fastjson.JSON;

@Component("mpQueryOrderAction")
@SuppressWarnings({ "rawtypes", "unchecked" })
public class MPQueryOrderAction implements HSLAction {
	
	@Autowired
	private MPOrderService mpOrderService;
	@Autowired
	private HgLogger hgLogger;
	
	@Override
	public String execute(ApiRequest apiRequest) {
		MPOrderQO mpOrderQO = JSON.parseObject(apiRequest.getBody().getPayload(), MPOrderQO.class);
		hgLogger.info("liujz", "订单查询请求"+JSON.toJSONString(mpOrderQO));
		return queryMPOrder(mpOrderQO);
	}

	/**
	 * 门票订单查询
	 * 
	 * @param mpOrderQO
	 * @return
	 */

	public String queryMPOrder(MPOrderQO mpOrderQO) {
		MPQueryOrderResponse mpQueryOrderResponse = new MPQueryOrderResponse();
		HslMPOrderQO hslMPOrderQO=BeanMapperUtils.map(mpOrderQO, HslMPOrderQO.class);
		Map map=new HashMap();
		try {
			map = mpOrderService.queryMPOrderList(hslMPOrderQO);
			List<MPOrderDTO> mpOrderDTOList = (List<MPOrderDTO>) map.get("dto");
			if(mpOrderDTOList != null && mpOrderDTOList.size() > 0){
				mpQueryOrderResponse.setOrders(mpOrderDTOList);
				mpQueryOrderResponse.setTotalCount(Integer.parseInt(map.get("count").toString()));
				mpQueryOrderResponse.setResult(ApiResponse.RESULT_CODE_OK);
				mpQueryOrderResponse.setMessage("订单查询成功");				
			}else{
				mpQueryOrderResponse.setOrders(null);
				mpQueryOrderResponse.setTotalCount(0);
				mpQueryOrderResponse.setResult(ApiResponse.RESULT_CODE_OK);
				mpQueryOrderResponse.setMessage("没有数据");
			}
		} catch (MPException e) {
			mpQueryOrderResponse.setResult(Constants.exceptionMap.get(e.getCode()));
			mpQueryOrderResponse.setMessage(e.getMessage());
		}
		hgLogger.info("liujz", "订单查询结果"+JSON.toJSONString(mpQueryOrderResponse));
		return JSON.toJSONString(mpQueryOrderResponse);
	}

}
