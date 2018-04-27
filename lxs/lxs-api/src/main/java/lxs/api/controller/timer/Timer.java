package lxs.api.controller.timer;

import hg.common.util.SMSUtils;
import hg.log.util.HgLogger;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import lxs.api.controller.BaseController;
import lxs.app.service.line.LineOrderLocalService;
import lxs.domain.model.line.LineOrder;
import lxs.pojo.qo.line.LineOrderQO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 
 * @类功能说明：各种定时器
 * @类修改者：
 * @修改日期：2015年11月9日下午1:10:36
 * @修改说明：
 * @公司名称：浙江票量科技有限公司
 * @作者：cangs
 * @创建时间：2015年11月9日下午1:10:36
 */
@Controller
@RequestMapping(value = "/timer")
public class Timer extends BaseController{

	@Autowired
	private LineOrderLocalService lineOrderLocalService;
	@Autowired
	private SMSUtils smsUtils;
	/**
	 * 
	 * @方法功能说明：下单十分钟未支付给用户发短信
	 * @修改者名字：cangs
	 * @修改时间：2015年11月9日下午1:11:16
	 * @修改内容：
	 * @参数：@param request
	 * @参数：@param response
	 * @return:void
	 * @throws
	 */
	@RequestMapping(value="/sendSMS")
	@ResponseBody
	public String sendSMS(HttpServletRequest request, HttpServletResponse response){
		HgLogger.getInstance().info("lxs_dev","【定时器轮训发SMS】"+"【开始轮训】"+new Date().toString());
		LineOrderQO lineOrderQO = new LineOrderQO();
		lineOrderQO.setHaveSendedSMSFlag("yes");
		List<LineOrder> lineOrders = lineOrderLocalService.queryList(lineOrderQO);
		for (LineOrder lineOrder : lineOrders) {
			final String dealerOrderNo = lineOrder.getBaseInfo().getDealerOrderNo();
			lineOrderQO = new LineOrderQO();
			lineOrderQO.setDealerOrderNo(dealerOrderNo);
			HgLogger.getInstance().info("lxs_dev","【定时器轮训发SMS】"+"订单号：" + dealerOrderNo);
			final LineOrder myLineOrder = lineOrderLocalService.queryUnique(lineOrderQO);
			new Thread(){
				public void run(){
					if(myLineOrder!=null&&myLineOrder.getLinkInfo()!=null&&myLineOrder.getLinkInfo().getLinkMobile()!=null){
						try {
							String text = "【票量旅游】亲爱的用户，您的订单"+dealerOrderNo+"已提交成功！请尽快完成支付，以免耽误您的出行！客服专线0571-28280815。";
							HgLogger.getInstance().info("lxs_dev","【定时器轮训发SMS】"+"发送短信内容，电话"+myLineOrder.getLinkInfo().getLinkMobile());
							HgLogger.getInstance().info("lxs_dev","【定时器轮训发SMS】"+"发送短信内容，内容"+text);
							smsUtils.sendSms(myLineOrder.getLinkInfo().getLinkMobile(),text);
							HgLogger.getInstance().info("lxs_dev","【定时器轮训发SMS】"+"发送短信内容【成功】");
						} catch (Exception e) {
							HgLogger.getInstance().info("lxs_dev","【定时器轮训发SMS】"+"发生异常" +HgLogger.getStackTrace(e));
						}
					}
				}
			}.start();
			lineOrder.setHaveSendedSMS(LineOrder.YES);
			lineOrderLocalService.update(lineOrder);
		}
		HgLogger.getInstance().info("lxs_dev","【定时器轮训发SMS】"+"【结束轮训】"+new Date().toString());
		return "success";
	}
}
