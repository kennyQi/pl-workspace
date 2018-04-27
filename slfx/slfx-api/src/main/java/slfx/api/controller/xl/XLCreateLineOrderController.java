package slfx.api.controller.xl;

import hg.log.util.HgLogger;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import slfx.api.base.ApiRequest;
import slfx.api.base.ApiResponse;
import slfx.api.controller.base.SLFXAction;
import slfx.api.v1.request.command.xl.XLCreateLineOrderApiCommand;
import slfx.api.v1.response.xl.XLCreateLineOrderResponse;
import slfx.xl.pojo.command.line.XLUpdateLineMessageApiCommand;
import slfx.xl.pojo.command.order.CreateLineOrderCommand;
import slfx.xl.pojo.dto.line.LineDTO;
import slfx.xl.pojo.dto.order.LineOrderDTO;
import slfx.xl.pojo.exception.SlfxXlException;
import slfx.xl.spi.inter.LineOrderService;
import slfx.xl.spi.inter.LineSnapshotService;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

/**
 * 
 * @类功能说明：api接口创建线路订单Controller
 * @类修改者：
 * @修改日期：
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @作者：tandeng
 * @创建时间：2014年12月24日上午10:41:06
 * @版本：V1.0
 *
 */
@Component("xlCreateLineOrderController")
public class XLCreateLineOrderController implements SLFXAction {
	
	@Autowired
	private LineOrderService lineOrderService;
	@Autowired
	private LineSnapshotService lineSnapshotService;

	@Override
	public String execute(ApiRequest apiRequest) {
		String fromClientKey = apiRequest.getHead().getFromClientKey();
		String clientUserKey = apiRequest.getHead().getClientUserKey();
		JSONObject jsonObject = JSON.parseObject(apiRequest.getBody().getPayload());
		jsonObject.put("fromClientKey", fromClientKey);
		jsonObject.put("clientUserKey", clientUserKey);
		XLCreateLineOrderApiCommand apiCommand = JSON.toJavaObject(jsonObject, XLCreateLineOrderApiCommand.class);
		
		return xlCreateLineOrder(apiCommand);
	}
	
	/**
	 * 
	 * @方法功能说明：创建线路订单
	 * @修改者名字：tandeng
	 * @修改时间：2014年12月24日上午10:37:45
	 * @修改内容：
	 * @参数：@param apiCommand
	 * @参数：@return
	 * @return:String
	 * @throws
	 */
	private String xlCreateLineOrder(XLCreateLineOrderApiCommand apiCommand){
		HgLogger.getInstance().info("tandeng","XLCreateLineOrderController->[创建线路订单开始]:"+ JSON.toJSONString(apiCommand));
		
		LineOrderDTO lineOrderDTO = null;
		try{
			//"apiCommand"转换"分销Command"
			CreateLineOrderCommand command = new CreateLineOrderCommand();
			BeanUtils.copyProperties(apiCommand,command);
			
			lineOrderDTO = lineOrderService.shopCreateLineOrder(command);
		}catch(SlfxXlException e){
			HgLogger.getInstance().error("tandeng","XLCreateLineOrderController->[创建线路订单异常]:"+HgLogger.getStackTrace(e));
		}catch(Exception e){
			HgLogger.getInstance().error("tandeng","XLCreateLineOrderController->[创建线路订单异常]:"+HgLogger.getStackTrace(e));
		}
		
		XLCreateLineOrderResponse xlCreateLineOrderResponse = new XLCreateLineOrderResponse();
		
		if(lineOrderDTO != null){
			//更新线路快照并通知经销商修改线路信息
			LineDTO lineDTO = new LineDTO();
			lineDTO.setId(apiCommand.getLineID());
			lineSnapshotService.createLineSnapshot(lineDTO);
			XLUpdateLineMessageApiCommand xlUpdateLineMessageApiCommand = new XLUpdateLineMessageApiCommand();
			xlUpdateLineMessageApiCommand.setLineId(apiCommand.getLineID());
			lineOrderService.sendModifyLineImageMessage(xlUpdateLineMessageApiCommand);
			
			xlCreateLineOrderResponse.setMessage("成功");
			xlCreateLineOrderResponse.setResult(ApiResponse.RESULT_CODE_OK);
			xlCreateLineOrderResponse.setLineOrderDTO(lineOrderDTO);
		}else{
			xlCreateLineOrderResponse.setMessage("失败");
			xlCreateLineOrderResponse.setResult(ApiResponse.RESULT_CODE_FAILE);
		}
		
		HgLogger.getInstance().info("tandeng","XLCreateLineOrderController->[创建线路订单结束]:"+ JSON.toJSONString(xlCreateLineOrderResponse));
		return JSON.toJSONString(xlCreateLineOrderResponse);
	}

}
