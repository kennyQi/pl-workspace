package plfx.api.controller.jd;

import hg.log.util.HgLogger;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import plfx.api.client.api.v1.jd.request.command.JDOrderCreateApiCommand;
import plfx.api.client.api.v1.jd.response.JDOrderCreateResponse;
import plfx.api.client.base.slfx.ApiRequest;
import plfx.api.client.base.slfx.ApiResponse;
import plfx.api.controller.base.SLFXAction;
import plfx.jd.pojo.command.plfx.ylclient.JDOrderCreateCommand;
import plfx.jd.pojo.dto.ylclient.order.ContactDTO;
import plfx.jd.pojo.dto.ylclient.order.CreateOrderRoomDTO;
import plfx.jd.pojo.dto.ylclient.order.CustomerDTO;
import plfx.jd.pojo.dto.ylclient.order.OrderCreateCommandDTO;
import plfx.jd.pojo.dto.ylclient.order.OrderCreateResultDTO;
import plfx.jd.spi.inter.HotelOrderService;
import plfx.yl.ylclient.yl.dto.CheckGuestDTO;
import plfx.yl.ylclient.yl.dto.CheckGuestNameDTO;
import plfx.yl.ylclient.yl.inter.YLHotelService;
import plfx.yl.ylclient.yl.qo.CheckGuestNameQO;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;

import elong.CheckGuestNameItem;

/**
 * 
 * @类功能说明：酒店订单创建api
 * @类修改者：
 * @修改日期：
 * @修改说明：
 * @公司名称：浙江票量云数据科技股份有限公司
 * @部门：技术部
 * @作者：caizhenghao
 * @创建时间：2015年2月11日下午2:03:25
 * @版本：V1.0
 *
 */
@Component("jdOrderCreateController")
public class JDOrderCreateController implements SLFXAction{

	@Autowired
	private HotelOrderService hotelOrderService;
	
	@Autowired
	private YLHotelService ylHotelService;
	
	@Override
	public String execute(ApiRequest apiRequest) {
		String fromClientKey = apiRequest.getHead().getFromClientKey();
		String clientUserKey = apiRequest.getHead().getClientUserKey();
		JSONObject jsonObject = JSON.parseObject(apiRequest.getBody().getPayload());
		jsonObject.put("fromClientKey", fromClientKey);
		jsonObject.put("clientUserKey", clientUserKey);
		JDOrderCreateApiCommand apiCommand = JSON.parseObject(jsonObject.toJSONString(), JDOrderCreateApiCommand.class);
		return JDOrderCreate(apiCommand);
	}
	/**
	 * 
	 * @方法功能说明：酒店订单创建
	 * @修改者名字：caizhenghao
	 * @修改时间：2015年2月11日下午2:02:59
	 * @修改内容：
	 * @参数：@param apiCommand
	 * @参数：@return
	 * @return:String
	 * @throws
	 */
	public String JDOrderCreate(JDOrderCreateApiCommand apiCommand){
		HgLogger.getInstance().info("caizhenghao", "JDOrderCreateController->[创建酒店订单开始]:" + JSON.toJSONString(apiCommand));
		OrderCreateResultDTO result = null;
		String message = "创建订单失败";
		boolean flag = false;
		try{
			//"apiCommand"转换"分销Command"
			JDOrderCreateCommand command = new JDOrderCreateCommand();
			BeanUtils.copyProperties(apiCommand,command);
			//先进行客人姓名认证
			CheckGuestNameQO qo =new CheckGuestNameQO();
			OrderCreateCommandDTO orderCreateDTO =command.getOrderCreateDTO();
			ContactDTO contact=orderCreateDTO.getContact();
			List<String> names=new ArrayList<String>();
			for(CreateOrderRoomDTO createOrderRoomDTO:orderCreateDTO.getOrderRoomsDTO()){
				for(CustomerDTO customerDTO:createOrderRoomDTO.getCustomers()){
					names.add(customerDTO.getName());
				}
			}
			names.add(contact.getName());
			qo.setNames(names);
			qo.setIsGangAo(false);
			CheckGuestNameDTO checkGuestNameDTO = ylHotelService.queryCheckGuestName(qo);
			CheckGuestDTO checkGuest=checkGuestNameDTO.getResult();
			for(CheckGuestNameItem checkGuestNameItem :checkGuest.getResult()){
				if(checkGuestNameItem.isIsValid()){
					flag = true;
				}else{
					flag = false;
					break;
				}
			}
			if(flag){
				result = hotelOrderService.orderCreate(command);
			}else{
				HgLogger.getInstance().info("yaosanfeng", "JDOrderCreateController->[客人姓名未通过校验]:" + flag);
			}
			
		}catch(Exception e){
			HgLogger.getInstance().error("caizhenghao", "JDOrderCreateController->[创建酒店订单异常]:" + HgLogger.getStackTrace(e));
		}
		
		
		JDOrderCreateResponse jdOrderCreateResponse = new JDOrderCreateResponse();
		if(result != null){
			jdOrderCreateResponse.setMessage("创建订单成功");
			jdOrderCreateResponse.setOrderCreateResultDTO(result);
			jdOrderCreateResponse.setResult(ApiResponse.RESULT_CODE_OK);
		}else{
			jdOrderCreateResponse.setMessage(message);
			jdOrderCreateResponse.setResult(ApiResponse.RESULT_CODE_FAILE);
		}
		
		HgLogger.getInstance().info("caizhenghao", "JDOrderCreateController->[创建酒店订单结束]:" + JSON.toJSONString(jdOrderCreateResponse));
		return JSON.toJSONString(jdOrderCreateResponse,SerializerFeature.DisableCircularReferenceDetect);
	}
	
	
	

}
