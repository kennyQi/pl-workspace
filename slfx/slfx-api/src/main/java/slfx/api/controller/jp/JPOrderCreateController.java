package slfx.api.controller.jp;

import hg.log.util.HgLogger;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import slfx.api.base.ApiRequest;
import slfx.api.base.ApiResponse;
import slfx.api.controller.base.SLFXAction;
import slfx.api.v1.request.command.jp.APIJPOrderCreateCommand;
import slfx.api.v1.response.jp.JPCreateOrderResponse;
import slfx.jp.command.admin.jp.JPOrderCreateSpiCommand;
import slfx.jp.pojo.dto.order.JPOrderDTO;
import slfx.jp.pojo.exception.JPOrderException;
import slfx.jp.spi.inter.JPPlatformOrderService;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

/**
 * 
 * @类功能说明：机票订单创建Controller
 * @类修改者：
 * @修改日期：
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @作者：tandeng
 * @创建时间：2014年12月22日下午5:25:07
 * @版本：V1.0
 *
 */
@Component("jpOrderCreateController")
public class JPOrderCreateController implements SLFXAction {
	
	@Autowired
	private JPPlatformOrderService jpPlatformOrderService;

	@Override
	public String execute(ApiRequest apiRequest) {
		String fromClientKey = apiRequest.getHead().getFromClientKey();
		String clientUserKey = apiRequest.getHead().getClientUserKey();
		JSONObject jsonObject = JSON.parseObject(apiRequest.getBody().getPayload());
		jsonObject.put("fromClientKey", fromClientKey);
		jsonObject.put("clientUserKey", clientUserKey);
		APIJPOrderCreateCommand command = JSON.toJavaObject(jsonObject, APIJPOrderCreateCommand.class);
		
		return jpOrderCreate(command);
	}

	/**
	 * 
	 * @方法功能说明：机票订单创建
	 * @修改者名字：tandeng
	 * @修改时间：2014年8月6日上午10:30:11
	 * @修改内容：
	 * @参数：@param command
	 * @参数：@return
	 * @return:String
	 * @throws
	 */
	private String jpOrderCreate(APIJPOrderCreateCommand command){
		HgLogger.getInstance().info("tandeng","JPOrderCreateController->[机票订单创建开始]:"+ JSON.toJSONString(command));
		JPOrderDTO orderDTO = null;
		JPOrderCreateSpiCommand spiCommand = new JPOrderCreateSpiCommand();
		String messsage = "失败";
		try{
			BeanUtils.copyProperties(command, spiCommand);
			orderDTO=jpPlatformOrderService.shopCreateOrder(spiCommand);
		}catch(JPOrderException e){
			messsage = e.getMessage();
			HgLogger.getInstance().error("tandeng", "JPOrderCreateController->[机票订单创建异常]:" + HgLogger.getStackTrace(e));
		}
		
		JPCreateOrderResponse jpCreateOrderResponse = null;
		if(orderDTO != null && orderDTO.getPnr() != null && orderDTO.getPnr().length() == 6){
			jpCreateOrderResponse = new JPCreateOrderResponse();
			jpCreateOrderResponse.setMessage("成功");
			jpCreateOrderResponse.setResult(ApiResponse.RESULT_CODE_OK);
			jpCreateOrderResponse.setOrderCode(orderDTO.getOrderNo());
			jpCreateOrderResponse.setPnr(orderDTO.getPnr());
			jpCreateOrderResponse.setPayAmount(orderDTO.getUserPayAmount());
			jpCreateOrderResponse.setTicketPrice(orderDTO.getTicketPrice());
			jpCreateOrderResponse.setSingleTaxAmount(orderDTO.getTktTax());
			jpCreateOrderResponse.setCreateDate(orderDTO.getCreateDate());//订单创建时间
			jpCreateOrderResponse.setStatus(orderDTO.getStatus());//订单状态
			jpCreateOrderResponse.setWastWorkTime(orderDTO.getWastWorkTime());//废票截止时间date格式
			
			jpCreateOrderResponse.setRefundWorkTime(orderDTO.getRefundWorkTime());
			jpCreateOrderResponse.setWorkTime(orderDTO.getWorkTime());
			
			
			jpCreateOrderResponse.setAgencyName(orderDTO.getAgencyName());
			jpCreateOrderResponse.setPlatCode(orderDTO.getPlatCode());
		}else{
			jpCreateOrderResponse = new JPCreateOrderResponse();
			jpCreateOrderResponse.setMessage(messsage);
			jpCreateOrderResponse.setResult(ApiResponse.RESULT_CODE_FAILE);
		}
		HgLogger.getInstance().info("tandeng","JPOrderCreateController->->[机票订单创建结束]:"+ JSON.toJSONString(jpCreateOrderResponse));
		return JSON.toJSONString(jpCreateOrderResponse);
	}
}
