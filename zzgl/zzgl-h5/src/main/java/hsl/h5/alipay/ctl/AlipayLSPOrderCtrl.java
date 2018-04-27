package hsl.h5.alipay.ctl;

import hg.common.util.SysProperties;
import hg.log.util.HgLogger;
import hsl.alipay.util.AlipayNotify;
import hsl.domain.model.lineSalesPlan.LineSalesPlanBaseInfo;
import hsl.pojo.command.lineSalesPlan.order.ModifyLSPOrderPayInfoCommand;
import hsl.pojo.command.lineSalesPlan.order.UpdateLSPOrderStatusCommand;
import hsl.pojo.dto.lineSalesPlan.LineSalesPlanConstant;
import hsl.pojo.dto.lineSalesPlan.order.LSPOrderDTO;
import hsl.pojo.qo.lineSalesPlan.order.LSPOrderQO;
import hsl.spi.inter.lineSalesPlan.order.LSPOrderService;

import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import plfx.api.client.base.slfx.SlfxApiClient;

import com.alibaba.fastjson.JSON;

@Controller
@RequestMapping("alipay")
public class AlipayLSPOrderCtrl {
	/**
	 * 地址后缀
	 */
	public final static String SUFFIX = "-lineSalesPlan-order";

	@Autowired
	private LSPOrderService lSPOrderService;

	/**
	 * @方法功能说明：接收线路订单支付宝回调
	 * @修改者名字：zhurz
	 * @修改时间：2015-10-13下午2:39:37
	 * @修改内容：
	 * @参数：@param request
	 * @参数：@param out
	 * @参数：@return
	 * @参数：@throws Exception
	 * @return:String
	 * @throws
	 */
	@RequestMapping(value = "callback" + SUFFIX)
	public String callback(HttpServletRequest request, PrintWriter out) throws Exception {
		HgLogger.getInstance().info("renfeng", "H5线路活动订单支付宝回调");
		Map<String, String> params = getAllReqParam(request);
		String ctx = getWebAppPath(request);
		String dealerOrderNo = params.get("out_trade_no");
		
		
		if (AlipayNotify.verifyReturn(params)) {
			// 验证成功
			dealerOrderNo = dealerOrderNo.split("-")[0];
			HgLogger.getInstance().info("renfeng", "H5线路活动订单支付宝回调，订单号："+dealerOrderNo);
			return "redirect:" + ctx + "/lineSalesPlan/orderPaySuccess?dealerOrderNo=" + dealerOrderNo;
		} else {
			return "redirect:" + ctx + "/alipay/payerror" + SUFFIX;
		}
	}
	
	/**
	 * @方法功能说明：支付宝中断页面
	 * @修改者名字：zhurz
	 * @修改时间：2015-10-16下午3:07:02
	 * @修改内容：
	 * @参数：@param request
	 * @参数：@param dealerOrderNo		订单号
	 * @参数：@return
	 * @return:ModelAndView
	 * @throws
	 */
	@RequestMapping("payerror" + SUFFIX)
	public ModelAndView error(
			HttpServletRequest request,
			@RequestParam(value = "dealerOrderNo", required = false) String dealerOrderNo) {
		
		
		if (StringUtils.isNotBlank(dealerOrderNo)) {
			
			dealerOrderNo = dealerOrderNo.split("-")[0];
			
			HgLogger.getInstance().info("renfeng", "H5线路活动订单支付宝支付异常，跳转错误页面，订单号："+dealerOrderNo);
			return new ModelAndView(new RedirectView("/lineSalesPlan/orderDetail?dealerOrderNo=" + dealerOrderNo, true));
			
		}
		return new ModelAndView("error");
	}

	/**
	 * 获取webapp路径
	 * 
	 * @return
	 */
	private String getWebAppPath(HttpServletRequest request) {
		Boolean isRoot = Boolean.parseBoolean(SysProperties.getInstance().get("root"));
		String proj = request.getContextPath();
		String port = SysProperties.getInstance().get("port");
		String system = "http://" + SysProperties.getInstance().get("host") + ("80".equals(port) ? "" : ":" + port);
		if (!isRoot) {
			system += proj;
		}
		return system;
	}

	/**
	 * @方法功能说明：获取请求的数据组合成map
	 * @修改者名字：zhurz
	 * @修改时间：2015-10-13下午2:41:17
	 * @修改内容：
	 * @参数：@param request
	 * @参数：@return
	 * @return:Map<String,String>
	 * @throws
	 */
	private Map<String, String> getAllReqParam(HttpServletRequest request) {
		Map<String, String> params = new HashMap<String, String>();
		Map<?, ?> requestParams = request.getParameterMap();
		for (Iterator<?> iter = requestParams.keySet().iterator(); iter.hasNext();) {
			String name = (String) iter.next();
			String[] values = (String[]) requestParams.get(name);
			String valueStr = "";
			for (int i = 0; i < values.length; i++) {
				valueStr = (i == values.length - 1) ? valueStr + values[i] : valueStr + values[i] + ",";
			}
			params.put(name, valueStr);
		}
		return params;
	}

	/**
	 * 支付宝异步通知地址 web处理方式(线路抢购活动订单)
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping("/notify" + SUFFIX)
	@ResponseBody
	public String notify(HttpServletRequest request) {
		HgLogger.getInstance().info("renfeng", "支付宝线路活动订单->notify->处理支付宝异步通知开始");
		//获取支付宝POST过来反馈信息
		Map<String, String> params = getAllReqParam(request);
		HgLogger.getInstance().info("zhangka", "支付宝线路活动订单->notify->params[post参数]"+JSON.toJSONString(params));
		
		
		//商户订单号
		String out_trade_no = "";
		//支付宝交易号
		String trade_no = "";
		//交易状态
		String trade_status = "";
		//付款支付宝账号
		String buyer_email = "";
		
		try {
			//支付宝支付结果
			boolean verifyNotify = AlipayNotify.verifyNotify(params,false);
			Document doc_notify_data = DocumentHelper.parseText(params.get("notify_data"));
			//订单号
			out_trade_no = doc_notify_data.selectSingleNode("//notify/out_trade_no").getText();
			//交易号
			trade_no = doc_notify_data.selectSingleNode("//notify/trade_no").getText();
			//交易状态
			trade_status = doc_notify_data.selectSingleNode("//notify/trade_status").getText();
			//支付帐号
			buyer_email = doc_notify_data.selectSingleNode("//notify/buyer_email").getText();
			// 交易总金额
			String total_fee = doc_notify_data.selectSingleNode("//notify/total_fee").getText();

			//如果支付成功
			if (verifyNotify) {
				long startTime=System.currentTimeMillis();
				// 经销商订单号
				String dealerOrderNo = out_trade_no.split("-")[0];
				HgLogger.getInstance().info("renfeng", "H5线路活动订单支付宝支付成功，异步回调，订单号："+dealerOrderNo);
				if(StringUtils.isNotBlank(dealerOrderNo)){
					LSPOrderQO orderQo=new LSPOrderQO();
					orderQo.setDealerOrderNo(dealerOrderNo);
					LSPOrderDTO orderDto=this.lSPOrderService.queryUnique(orderQo);
					HgLogger.getInstance().info("renfeng", "H5线路活动订单支付宝支付成功，异步回调，活动订单查询返回："+orderDto.getId());
					//设置订单支付信息
					ModifyLSPOrderPayInfoCommand cmd=new ModifyLSPOrderPayInfoCommand();
					cmd.setOrderId(orderDto.getId());
					cmd.setBuyerEmail(buyer_email);
					cmd.setPayTradeNo(trade_no);
					cmd.setPayPrice(Double.valueOf(total_fee));
					/**订单的支付状态为未支付支付状态，则做更新操作，防止支付宝异步重复通知*/
					if(LineSalesPlanConstant.LSP_PAY_STATUS_NOPAY.equals(orderDto.getOrderStatus().getPayStatus())){
						HgLogger.getInstance().info("renfeng", "H5线路活动订单支付宝支付成功，异步回调，修改活动订单JSON-->：" + JSON.toJSONString(orderDto));
						HgLogger.getInstance().info("renfeng", "H5线路活动订单支付宝支付成功，异步回调，修改活动订单支付信息command：" + JSON.toJSONString(cmd));
						this.lSPOrderService.modifyLSPOrderPayInfo(cmd);
						//支付完成后 ，检查是否拼团成功
						if(LineSalesPlanBaseInfo.PLANTYPE_GROUP.equals(orderDto.getOrderBaseInfo().getOrderType())){
							HgLogger.getInstance().info("renfeng", "H5线路活动订单支付宝支付成功，异步回调，检查是否拼团成功"+orderDto.getOrderBaseInfo().getDealerOrderNo());
							this.lSPOrderService.checkLspISGroupSuc(orderDto.getOrderBaseInfo().getDealerOrderNo());
						}else if(LineSalesPlanBaseInfo.PLANTYPE_SECKILL.equals(orderDto.getOrderBaseInfo().getOrderType())){
							HgLogger.getInstance().info("chenxy", "H5线路活动订单支付宝支付成功，异步回调，更新秒杀状态到分销"+orderDto.getId());
							UpdateLSPOrderStatusCommand updateLSPOrderStatusCommand=new UpdateLSPOrderStatusCommand();
							updateLSPOrderStatusCommand.setOrderId(orderDto.getId());
							updateLSPOrderStatusCommand.setPayStatus(LineSalesPlanConstant.LSP_PAY_STATUS_PAY_SUCCESS);
							lSPOrderService.updateLSPOrderStatus(updateLSPOrderStatusCommand);
						}
					}
					long endTime=System.currentTimeMillis();
					HgLogger.getInstance().info("chenxy","活动订单异步处理耗时-->毫秒-->"+(endTime-startTime)+"ms,秒-->"+((endTime-startTime)/1000));
				}

			}
		} catch (Exception e) {
			HgLogger.getInstance().error("renfeng", "支付宝线路订单->notify->获取参数为空:" 
					+"|out_trade_no="+out_trade_no
					+"|trade_no"+trade_no
					+"|trade_status"+trade_status
					+"|buyer_email"+buyer_email
					+ HgLogger.getStackTrace(e));
			return "fail";
		}
		
		HgLogger.getInstance().info("renfeng", "out_trade_no="+out_trade_no+",trade_no="+trade_no+",trade_status="+trade_status);
		HgLogger.getInstance().info("rnefeng", "支付宝线路活动订单->处理支付宝异步通知结束");
		return "success";
	}
	public static void main(String[] args){
		Integer integer=new Integer(2105);
		if(!LineSalesPlanConstant.LSP_PAY_STATUS_PAY_SUCCESS.equals(integer)){
			System.out.println("sdsds");
		}else{
			System.out.println("11111");
		}
	}
}
