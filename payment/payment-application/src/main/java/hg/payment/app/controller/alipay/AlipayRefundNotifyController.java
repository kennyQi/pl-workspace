package hg.payment.app.controller.alipay;

import hg.log.util.HgLogger;
import hg.payment.app.controller.BaseController;
import hg.payment.app.pojo.qo.refund.AlipayRefundLocalQO;
import hg.payment.app.service.local.refund.AlipayRefundLocalService;
import hg.payment.domain.common.util.RefundUtils;
import hg.payment.domain.common.util.alipay.util.AlipayNotify;
import hg.payment.domain.model.channel.alipay.AlipayPayChannel;
import hg.payment.domain.model.payorder.alipay.AlipayPayOrder;
import hg.payment.domain.model.refund.AlipayRefund;
import hg.payment.pojo.command.admin.ModifyAlipayRefundCommand;
import hg.payment.pojo.command.admin.dto.ModifyRefundDTO;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alibaba.fastjson.JSONArray;

/**
 * 
 * 
 *@类功能说明：支付宝退款异步回调
 *@类修改者：
 *@修改日期：
 *@修改说明：
 *@公司名称：浙江汇购科技有限公司
 *@部门：技术部
 *@作者：luoyun
 *@创建时间：2014年11月27日下午3:37:30
 *
 */
@Controller
@RequestMapping(value = "/alipay")
public class AlipayRefundNotifyController extends BaseController{
	
	@Autowired
	private RefundUtils refundUtils;
	@Autowired
	private AlipayRefundLocalService alipayRefundLocalService;
	@Autowired
	private AlipayPayChannel alipay;
	
	
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/refundNotify")
	public void alipayRefundNotify(HttpServletRequest request, HttpServletResponse response){
		
		PrintWriter out = super.getPrintWriter(response,"utf-8");
		String out_trade_no = "";
		try{
			//获取支付宝反馈信息
			Map<String,String> params = new HashMap<String,String>();
			Map requestParams = request.getParameterMap();
			
			for (Iterator iter = requestParams.keySet().iterator(); iter.hasNext();) {
				String name = (String) iter.next();
				String[] values = (String[]) requestParams.get(name);
				String valueStr = "";
				for (int i = 0; i < values.length; i++) {
					valueStr = (i == values.length - 1) ? valueStr + values[i]
							: valueStr + values[i] + ",";
				}
				params.put(name, valueStr);
			}
			
			String batchNo = request.getParameter("batch_no"); //批次号
			String result_details = request.getParameter("result_details");//退款结果详细信息
			
			HgLogger.getInstance().info("luoyun", "【支付宝】退款异步回调批次号" + batchNo+ "反馈信息:"+JSONArray.toJSONString(params));
			
			List<ModifyRefundDTO> dtoList = new ArrayList<ModifyRefundDTO>();
			dtoList = refundUtils.paraseRefundNotify(result_details);
			
			AlipayRefundLocalQO refundQo = new AlipayRefundLocalQO();
			refundQo.setThirdPartyTradeNo(dtoList.get(0).getThirdPartyTradeNo());  
			refundQo.setRefundBatchNo(batchNo);
			AlipayRefund refund = alipayRefundLocalService.queryUnique(refundQo);
			
			AlipayNotify notify = new AlipayNotify();  //同一批次号的退款记录的keys是一致的
			notify.setAlipay(alipay);
			notify.setKeys(refund.getKeys());
			notify.setPartner(refund.getPartner());
			
			if(notify.verify(params)){
				//解析处理详情result_details，修改订单状态，完整退款信息
				ModifyAlipayRefundCommand command = new ModifyAlipayRefundCommand();
				command.setRefundBatchNo(batchNo);
				command.setModifyRefundDTOList(dtoList);
				alipayRefundLocalService.refund(command);
				//通知商城
				alipayRefundLocalService.notifyClientRefundList(batchNo);
			}else{
				HgLogger.getInstance().info("luoyun", "【支付宝】支付平台支付宝订单" + out_trade_no + "退款异步回调验证失败");
				out.write("fail");
			}
			
			
			out.write("success");
			
	     }catch(Exception e){
				HgLogger.getInstance().error("luoyun", "【支付宝】支付平台支付宝订单" + out_trade_no + "异步回调失败:"+e.getMessage());
				e.printStackTrace();
				out.write("fail");
	  }
	}
}
