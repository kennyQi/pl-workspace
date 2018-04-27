package plfx.api.controller.xl;

import hg.log.util.HgLogger;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import plfx.api.client.api.v1.xl.request.command.XLModifyLineOrderTravelerApiCommand;
import plfx.api.client.api.v1.xl.response.XLModifyLineOrderTravelerResponse;
import plfx.api.client.base.slfx.ApiRequest;
import plfx.api.client.base.slfx.ApiResponse;
import plfx.api.controller.base.SLFXAction;
import plfx.xl.pojo.command.order.ModifyLineOrderTravelerCommand;
import plfx.xl.pojo.dto.order.LineOrderDTO;
import plfx.xl.spi.inter.LineOrderService;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

/**
 * 
 * @类功能说明：api接口修改线路游玩人Controller
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @作者：yaosanfeng
 * @创建时间：2015年4月15日上午8:28:02
 * @版本：V1.0
 *
 */

@Component("xlModifyLineOrderTravelerController")
public class XLModifyLineOrderTravelerController implements SLFXAction {

	@Autowired
	private LineOrderService lineOrderService;

	@Override
	public String execute(ApiRequest apiRequest) {

		String fromClientKey = apiRequest.getHead().getFromClientKey();
		String clientUserKey = apiRequest.getHead().getClientUserKey();
		JSONObject jsonObject = JSON.parseObject(apiRequest.getBody()
				.getPayload());
		jsonObject.put("fromClientKey", fromClientKey);
		jsonObject.put("clientUserKey", clientUserKey);
		XLModifyLineOrderTravelerApiCommand apiCommand = JSON.toJavaObject(jsonObject,
				XLModifyLineOrderTravelerApiCommand.class);

		return xlModifyLineOrderTravelerController(apiCommand);
	}
	
	
	/***
	 * 
	 * @方法功能说明：
	 * @修改者名字：yaosanfeng
	 * @修改时间：2015年5月5日下午5:41:51
	 * @修改内容：
	 * @参数：@param apiCommand
	 * @参数：@return
	 * @return:String
	 * @throws
	 */
	private String xlModifyLineOrderTravelerController(XLModifyLineOrderTravelerApiCommand apiCommand) {
		HgLogger.getInstance().info(
				"yaosanfeng",
				"XLModifyLineOrderTravelerController->[修改线路订单游玩人开始]:"
						+ JSON.toJSONString(apiCommand));

		LineOrderDTO lineOrderDTO = null;
		try {
			// "apiCommand"转换"分销Command"
			ModifyLineOrderTravelerCommand command = new ModifyLineOrderTravelerCommand();
			BeanUtils.copyProperties(apiCommand, command);

			lineOrderDTO = lineOrderService.modifyLineOrderTraveler(command);
		} catch (Exception e) {
			HgLogger.getInstance().error(
					"yaosanfeng",
					"XLModifyLineOrderTravelerController->[修改线路订单状态异常]:"
							+ HgLogger.getStackTrace(e));
		}

		XLModifyLineOrderTravelerResponse xlModifyLineOrderTravelerResponse = new XLModifyLineOrderTravelerResponse();

		if (lineOrderDTO != null) {
			xlModifyLineOrderTravelerResponse.setMessage("成功");
			xlModifyLineOrderTravelerResponse.setResult(ApiResponse.RESULT_CODE_OK);
			xlModifyLineOrderTravelerResponse.setLineOrderDTO(lineOrderDTO);
		} else {
			xlModifyLineOrderTravelerResponse.setMessage("失败");
			xlModifyLineOrderTravelerResponse.setResult(ApiResponse.RESULT_CODE_FAILE);
		}

		HgLogger.getInstance().info(
				"yaosanfeng",
				"xLModifyLineOrderResponse->[修改线路订单状态结束]:"
						+ JSON.toJSONString(xlModifyLineOrderTravelerResponse));
		return JSON.toJSONString(xlModifyLineOrderTravelerResponse);
	}

}
