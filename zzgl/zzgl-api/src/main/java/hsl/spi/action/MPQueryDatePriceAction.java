package hsl.spi.action;

import hg.common.util.BeanMapperUtils;
import hg.log.util.HgLogger;
import hsl.api.base.ApiRequest;
import hsl.api.v1.request.qo.mp.MPDatePriceQO;
import hsl.api.v1.response.mp.MPQueryDatePriceResponse;
import hsl.pojo.dto.mp.DateSalePriceDTO;
import hsl.pojo.exception.MPException;
import hsl.pojo.qo.mp.HslMPDatePriceQO;
import hsl.spi.action.constant.Constants;
import hsl.spi.inter.mp.MPScenicSpotService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;

@Component("mpQueryDatePriceAction")
@SuppressWarnings({"rawtypes","unchecked"})
public class MPQueryDatePriceAction implements HSLAction {
	@Autowired
	private MPScenicSpotService scenicSpotService;
	@Autowired
	private HgLogger hgLogger;
	
	
	@Override
	public String execute(ApiRequest apiRequest) {
		MPDatePriceQO mpDatePriceQO = JSON.parseObject(apiRequest.getBody().getPayload(), MPDatePriceQO.class);
		HslMPDatePriceQO hslMPDatePriceQO=BeanMapperUtils.map(mpDatePriceQO, HslMPDatePriceQO.class);
		
		hgLogger.info("liujz", "价格日历请求"+JSON.toJSONString(mpDatePriceQO));
		MPQueryDatePriceResponse datePriceResponse = new MPQueryDatePriceResponse();
		Map datePriceMap=new HashMap();;
		try {
			datePriceMap = scenicSpotService.queryDatePrice(hslMPDatePriceQO);
			datePriceResponse.setDateSalePrices((List<DateSalePriceDTO>)datePriceMap.get("dto"));
			datePriceResponse.setTotalCount(Integer.parseInt(datePriceMap.get("count").toString()));
		} catch (MPException e) {
			datePriceResponse.setResult(Constants.exceptionMap.get(e.getCode()));
			datePriceResponse.setMessage(e.getMessage());
		}
		hgLogger.info("liujz", "价格日历查询结果"+JSON.toJSONString(datePriceResponse));
		return JSON.toJSONString(datePriceResponse);
	}
	
}
