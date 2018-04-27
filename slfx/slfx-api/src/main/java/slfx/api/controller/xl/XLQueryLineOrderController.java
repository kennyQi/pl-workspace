package slfx.api.controller.xl;

import hg.log.util.HgLogger;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import slfx.api.base.ApiRequest;
import slfx.api.base.ApiResponse;
import slfx.api.controller.base.SLFXAction;
import slfx.api.v1.request.qo.xl.XLLineOrderApiQO;
import slfx.api.v1.response.xl.XLQueryLineOrderResponse;
import slfx.xl.pojo.dto.order.LineOrderDTO;
import slfx.xl.pojo.qo.LineOrderQO;
import slfx.xl.spi.inter.LineOrderService;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

/**
 * 
 * @类功能说明：api接口查询线路订单信息Controller
 * @类修改者：
 * @修改日期：
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @作者：tandeng
 * @创建时间：2014年12月23日上午10:41:06
 * @版本：V1.0
 *
 */
@Component("xlQueryLineOrderController")
public class XLQueryLineOrderController implements SLFXAction {
	
	@Autowired
	private LineOrderService lineOrderService;

	@Override
	public String execute(ApiRequest apiRequest) {
		String fromClientKey = apiRequest.getHead().getFromClientKey();
		String clientUserKey = apiRequest.getHead().getClientUserKey();
		JSONObject jsonObject = JSON.parseObject(apiRequest.getBody().getPayload());
		jsonObject.put("fromClientKey", fromClientKey);
		jsonObject.put("clientUserKey", clientUserKey);
		XLLineOrderApiQO qo = JSON.toJavaObject(jsonObject, XLLineOrderApiQO.class);
		
		return xlQueryLineOrder(qo);
	}
	
	/**
	 * 
	 * @方法功能说明：查询线路订单
	 * @修改者名字：tandeng
	 * @修改时间：2014年12月23日上午10:44:08
	 * @修改内容：
	 * @参数：@param qo
	 * @参数：@return
	 * @return:String
	 * @throws
	 */
	private String xlQueryLineOrder(XLLineOrderApiQO apiQO){
		HgLogger.getInstance().info("tandeng","XLQueryLineOrderController->[线路订单查询开始]:"+ JSON.toJSONString(apiQO));
		
		List<LineOrderDTO> lineOrderList = null;
		try{
			//"apiQO"转换"分销QO"
			LineOrderQO lineOrderQO = new LineOrderQO();
			BeanUtils.copyProperties(apiQO,lineOrderQO);
			
			lineOrderList = lineOrderService.shopQueryLineOrderList(lineOrderQO);			
		}catch(Exception e){
			HgLogger.getInstance().error("tandeng","XLQueryLineOrderController->[线路订单查询异常]:"+HgLogger.getStackTrace(e));
		}
		
		XLQueryLineOrderResponse xlQueryLineOrderResponse = new XLQueryLineOrderResponse();
		
		if(lineOrderList != null && lineOrderList.size() > 0){
			xlQueryLineOrderResponse.setMessage("成功");
			xlQueryLineOrderResponse.setResult(ApiResponse.RESULT_CODE_OK);
			xlQueryLineOrderResponse.setLineOrderList(lineOrderList);
			xlQueryLineOrderResponse.setTotalCount(lineOrderList.size());
		}else{
			xlQueryLineOrderResponse.setMessage("失败");
			xlQueryLineOrderResponse.setResult(ApiResponse.RESULT_CODE_FAILE);
			xlQueryLineOrderResponse.setLineOrderList(null);
			xlQueryLineOrderResponse.setTotalCount(null);
		}
		
		HgLogger.getInstance().info("tandeng","XLQueryLineOrderController->[线路订单查询结束]:"+ JSON.toJSONString(xlQueryLineOrderResponse));
		return JSON.toJSONString(xlQueryLineOrderResponse);
	}

}
