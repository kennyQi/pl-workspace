package lxs.api.action.order.line;

import hg.log.util.HgLogger;
import lxs.api.action.LxsAction;
import lxs.api.base.ApiRequest;
import lxs.api.base.ApiResponse;
import lxs.api.v1.request.command.order.line.UpdateLineOrderStatusCommand;
import lxs.api.v1.response.order.line.UpdateLineOrderStatusResponse;
import lxs.app.service.line.LineOrderLocalService;
import lxs.pojo.dto.line.XLOrderStatusConstant;
import lxs.pojo.exception.line.LineException;
import lxs.pojo.exception.line.LineOrderException;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;

@Component("UpdateLineOrderStatusAction")
public class UpdateLineOrderStatusAction implements LxsAction{

	@Autowired
	private LineOrderLocalService lineOrderLocalService;
	@Override
	public String execute(ApiRequest apiRequest) {
		HgLogger.getInstance().info("lxs_dev", "进入action");
		UpdateLineOrderStatusCommand apiUpdateLineOrderStatusCommand= JSON.parseObject(apiRequest.getBody().getPayload(), UpdateLineOrderStatusCommand.class);
		UpdateLineOrderStatusResponse updateLineOrderStatusResponse = new UpdateLineOrderStatusResponse();
		Integer statuscoupon = null;
		Integer payStatuscoupon = null;
		try{
			HgLogger.getInstance().info("lxs_dev", "开始获取状态");
			if(apiUpdateLineOrderStatusCommand.getPayType()==null){
				throw new LineOrderException(LineOrderException.RESULT_NO_PAY_TYPE, "支付状态为空");
			}
			if(StringUtils.endsWith(apiUpdateLineOrderStatusCommand.getPayType(), UpdateLineOrderStatusCommand.PAY_BARGAIN_MONEY)){
				//支付定金
				statuscoupon = Integer.parseInt(XLOrderStatusConstant.SHOP_CREATE_SUCCESS_NOT_RESERVE);
				payStatuscoupon = Integer.parseInt(XLOrderStatusConstant.SHOP_WAIT_PAY_BALANCE_MONEY);
			}else if(StringUtils.endsWith(apiUpdateLineOrderStatusCommand.getPayType(), UpdateLineOrderStatusCommand.PAY_BALANCE_MONEY)){
				//支付尾款
				if(lineOrderLocalService.get(apiUpdateLineOrderStatusCommand.getOrderID()).getStatus().getOrderStatus()==Integer.parseInt(XLOrderStatusConstant.SHOP_CREATE_SUCCESS_LOCK_SEAT)){
					statuscoupon = Integer.parseInt(XLOrderStatusConstant.SHOP_RESERVE_SUCCESS);
					payStatuscoupon = Integer.parseInt(XLOrderStatusConstant.SHOP_PAY_SUCCESS);
				}else{
					throw new LineOrderException(LineOrderException.RESULT_NOT_RESERVE, "经销商未确认位置");
				}
			}else if(StringUtils.endsWith(apiUpdateLineOrderStatusCommand.getPayType(), UpdateLineOrderStatusCommand.SHOP_ALL_MONEY)){
				//支付全款
				statuscoupon = Integer.parseInt(XLOrderStatusConstant.SHOP_CREATE_SUCCESS_NOT_RESERVE);
				payStatuscoupon = Integer.parseInt(XLOrderStatusConstant.SHOP_PAY_SUCCESS);
			}else{
				throw new LineOrderException(LineOrderException.RESULT_PAY_TYPE_WRONG, "支付状态错误");
			}
			lxs.pojo.command.line.UpdateLineOrderStatusCommand updateLineOrderStatusCommands=new lxs.pojo.command.line.UpdateLineOrderStatusCommand();
			updateLineOrderStatusCommands.setOrderStatus(statuscoupon);
			updateLineOrderStatusCommands.setPayStatus(payStatuscoupon);
			if(apiUpdateLineOrderStatusCommand.getOrderNO()==null){
				throw new LineOrderException(LineOrderException.RESULT_NO_ORDERNO, "订单号为空");
			}
			updateLineOrderStatusCommands.setDealerOrderCode(apiUpdateLineOrderStatusCommand.getOrderNO());
			if(apiUpdateLineOrderStatusCommand.getOrderID()==null){
				throw new LineOrderException(LineOrderException.RESULT_NO_ORDERID, "订单ID为空");
			}
			updateLineOrderStatusCommands.setOrderId(apiUpdateLineOrderStatusCommand.getOrderID());
			HgLogger.getInstance().info("lxs_dev", "开始将订单号："+apiUpdateLineOrderStatusCommand.getOrderNO()+"订单ID："+apiUpdateLineOrderStatusCommand.getOrderID()+"更新订单状态："+statuscoupon+"，支付状态："+payStatuscoupon);
			try {
				if(lineOrderLocalService.updateOrderStatus(updateLineOrderStatusCommands)){
					updateLineOrderStatusResponse.setResult(ApiResponse.RESULT_CODE_OK);
					updateLineOrderStatusResponse.setMessage("更新订单状态成功");
				}else{
					throw new LineOrderException(LineOrderException.RESULT_UPDATE_ORDER_STATUS_FAILED, "更新订单状态失败");
				}
			} catch (LineException e) {
				throw new LineOrderException(LineOrderException.RESULT_UPDATE_ORDER_STATUS_FAILED, "更新订单状态失败");
			}
		}catch(LineOrderException e){
			HgLogger.getInstance().info("lxs_dev", "【UpdateLineOrderStatusAction】"+"异常，"+HgLogger.getStackTrace(e));
			updateLineOrderStatusResponse.setResult(e.getCode());
			updateLineOrderStatusResponse.setMessage(e.getMessage());
		}
		HgLogger.getInstance().info("lxs_dev", "修改订单状态结果"+JSON.toJSONString(updateLineOrderStatusResponse));
		return JSON.toJSONString(updateLineOrderStatusResponse);
	}

}
