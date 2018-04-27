package lxs.api.action.order.line;

import hg.log.util.HgLogger;

import java.util.Date;

import lxs.api.action.LxsAction;
import lxs.api.base.ApiRequest;
import lxs.api.base.ApiResponse;
import lxs.api.v1.request.command.order.line.GetShouldPayMoneyCommand;
import lxs.api.v1.response.order.line.GetShouldPayMoneyResponse;
import lxs.app.service.line.LineOrderLocalService;
import lxs.domain.model.line.Line;
import lxs.domain.model.line.LineOrder;
import lxs.pojo.dto.line.XLOrderStatusConstant;
import lxs.pojo.exception.line.LineOrderException;
import lxs.pojo.qo.line.LineOrderQO;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
@Component("GetShouldPayMoneyAction")
public class GetShouldPayMoneyAction implements LxsAction {

	@Autowired
	private LineOrderLocalService lineOrderLocalService;
	@Override
	public String execute(ApiRequest apiRequest) {
		HgLogger.getInstance().info("lxs_dev",  "进入action");
		GetShouldPayMoneyCommand apiGetShouldPayMoneyCommand = JSON.parseObject(apiRequest.getBody().getPayload(), GetShouldPayMoneyCommand.class);
		GetShouldPayMoneyResponse getShouldPayMoneyResponse = new GetShouldPayMoneyResponse();
		LineOrderQO lineOrderQO = new LineOrderQO();
		lineOrderQO.setDealerOrderNo(apiGetShouldPayMoneyCommand.getOrderNO());
		try{
			if(apiGetShouldPayMoneyCommand.getOrderNO()==null){
				throw new LineOrderException(LineOrderException.RESULT_NO_ORDERNO,"订单号为空");
			}
			HgLogger.getInstance().info("lxs_dev",  "根据订单号："+apiGetShouldPayMoneyCommand.getOrderNO()+"，开始查找订单");
			LineOrder lineOrder =	lineOrderLocalService.queryUnique(lineOrderQO);
			if(lineOrder==null){
				throw new LineOrderException(LineOrderException.RESULT_ORDER_NOT_FOUND,"订单查询失败");
			}
			getShouldPayMoneyResponse.setOrderID(lineOrder.getId());
			getShouldPayMoneyResponse.setOrderNO(lineOrder.getBaseInfo().getDealerOrderNo());
			Line line=JSON.parseObject(lineOrder.getLineSnapshot().getAllInfoLineJSON(),Line.class);
			//支付截止日
			long lastPayTotalDaysBeforeStart=line.getPayInfo().getLastPayTotalDaysBeforeStart()*24*60*60*1000;
			//当前时间（付款请求时间）
			Date now= new Date();
			//出发时间
			Date travelDate=lineOrder.getBaseInfo().getTravelDate();
			if(now.getTime()+lastPayTotalDaysBeforeStart>travelDate.getTime()){
				throw new LineOrderException(LineOrderException.RESULT_PAY_DEADLINE_PASSED,"超过最后支付时间");
			}else if(StringUtils.equals(lineOrder.getStatus().getOrderStatus().toString(),XLOrderStatusConstant.SHOP_CREATE_SUCCESS_NOT_RESERVE)&&StringUtils.equals(lineOrder.getStatus().getPayStatus().toString(),XLOrderStatusConstant.SHOP_WAIT_PAY_BARGAIN_MONEY)){
				HgLogger.getInstance().info("lxs_dev",  "订单无任何支付记录");
				//预定成功还没进行任何付款行为
				//支付定金截止日
				long payTotalDaysBeforeStart=line.getPayInfo().getPayTotalDaysBeforeStart()*24*60*60*1000;
				if(now.getTime()+payTotalDaysBeforeStart<travelDate.getTime()){
					HgLogger.getInstance().info("lxs_dev",  "获取定金金额");
					//支付定金
					getShouldPayMoneyResponse.setShouldPayMoney(Double.toString(lineOrder.getBaseInfo().getBargainMoney()));
					getShouldPayMoneyResponse.setPayType(GetShouldPayMoneyResponse.PAY_BARGAIN_MONEY);
					getShouldPayMoneyResponse.setResult(ApiResponse.RESULT_CODE_OK);
					getShouldPayMoneyResponse.setMessage("查询成功");
				}else{
					//支付全款
					HgLogger.getInstance().info("lxs_dev",  "获取全款金额");
					getShouldPayMoneyResponse.setShouldPayMoney(Double.toString(lineOrder.getBaseInfo().getSalePrice()));
					getShouldPayMoneyResponse.setPayType(GetShouldPayMoneyResponse.SHOP_ALL_MONEY);
					getShouldPayMoneyResponse.setResult(ApiResponse.RESULT_CODE_OK);
					getShouldPayMoneyResponse.setMessage("查询成功");
				}
			}else if(StringUtils.equals(lineOrder.getStatus().getOrderStatus().toString(),XLOrderStatusConstant.SHOP_CREATE_SUCCESS_LOCK_SEAT)&&StringUtils.equals(lineOrder.getStatus().getPayStatus().toString(), XLOrderStatusConstant.SHOP_WAIT_PAY_BALANCE_MONEY)){
				//预订成功并且已支付预付款
				HgLogger.getInstance().info("lxs_dev",  "获取尾款金额");
				getShouldPayMoneyResponse.setShouldPayMoney(Double.toString(lineOrder.getBaseInfo().getSalePrice()-lineOrder.getBaseInfo().getBargainMoney()));
				getShouldPayMoneyResponse.setPayType(GetShouldPayMoneyResponse.PAY_BALANCE_MONEY);
				getShouldPayMoneyResponse.setResult(ApiResponse.RESULT_CODE_OK);
				getShouldPayMoneyResponse.setMessage("查询成功");
			}
		}catch(LineOrderException e){
			HgLogger.getInstance().info("lxs_dev",  "根据订单号："+apiGetShouldPayMoneyCommand.getOrderNO()+"，获取应支付金额失败");
			getShouldPayMoneyResponse.setResult(e.getCode());
			getShouldPayMoneyResponse.setMessage(e.getMessage());			
		}
		HgLogger.getInstance().info("lxs_dev", "获取订单金额"+JSON.toJSONString(getShouldPayMoneyResponse));
		return JSON.toJSONString(getShouldPayMoneyResponse);
	}

}
