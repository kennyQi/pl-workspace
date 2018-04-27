package lxs.api.action.order.line;

import hg.log.util.HgLogger;
import lxs.api.action.LxsAction;
import lxs.api.base.ApiRequest;
import lxs.api.base.ApiResponse;
import lxs.api.v1.request.command.order.line.PayBargainMoneyCommand;
import lxs.api.v1.response.order.line.PayBargainMoneyResponse;
import lxs.app.service.line.LineOrderLocalService;
import lxs.pojo.command.line.AlipayCommand;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
/**
 * 
 * @类功能说明：为了满足支付定金为0元情况
 * @类修改者：
 * @修改日期：2015年6月5日下午12:06:24
 * @修改说明：
 * @公司名称：浙江票量科技有限公司
 * @作者：cangs
 * @创建时间：2015年6月5日下午12:06:24
 */
@Component("PayBargainMoneyAction")
public class PayBargainMoneyAction implements LxsAction{

	@Autowired
	private LineOrderLocalService lineOrderLocalService;
	
	@Override
	public String execute(ApiRequest apiRequest) {
		HgLogger.getInstance().info("lxs_dev", "【PayBargainMoneyAction】"+"进入action");
		PayBargainMoneyCommand payBargainMoneyCommand= JSON.parseObject(apiRequest.getBody().getPayload(), PayBargainMoneyCommand.class);
		AlipayCommand alipayCommand = new AlipayCommand();
		alipayCommand.setDealerOrderNo(payBargainMoneyCommand.getDealerOrderNo());
		//付款零元
		alipayCommand.setPrice(0.0);
		alipayCommand.setRequestType(AlipayCommand.LOCAL);
		HgLogger.getInstance().info("lxs_dev", "【PayBargainMoneyAction】"+"开始修改状态（APP内部支付0元）");
		lineOrderLocalService.payLineOrder(alipayCommand);
		HgLogger.getInstance().info("lxs_dev", "【PayBargainMoneyAction】"+"完成修改状态（APP内部支付0元）");
		PayBargainMoneyResponse payBargainMoneyResponse = new PayBargainMoneyResponse();
		payBargainMoneyResponse.setResult(ApiResponse.RESULT_CODE_OK);
		payBargainMoneyResponse.setMessage("修改状态成功");
		HgLogger.getInstance().info("lxs_dev", "【PayBargainMoneyAction】"+"查询线路结果"+JSON.toJSONString(payBargainMoneyResponse));
		return JSON.toJSONString(payBargainMoneyResponse);
	}

}
