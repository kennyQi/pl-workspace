package hg.payment.app.controller.client;

import hg.common.page.Pagination;
import hg.common.util.DwzJsonResultUtil;
import hg.log.util.HgLogger;
import hg.payment.app.common.util.PayChannelUtils;
import hg.payment.app.controller.BaseController;
import hg.payment.app.pojo.qo.client.PaymentClientLocalQO;
import hg.payment.app.service.local.client.PaymentClientLocalService;
import hg.payment.domain.model.channel.PayChannel;
import hg.payment.domain.model.channel.alipay.AlipayPayChannel;
import hg.payment.domain.model.channel.hjbPay.HJBPayChannel;
import hg.payment.domain.model.client.PaymentClient;
import hg.payment.pojo.command.admin.CreatePaymentClientCommand;
import hg.payment.pojo.command.admin.ModifyPaymentClientCommand;
import hg.payment.pojo.command.admin.StartPaymentClientCommand;
import hg.payment.pojo.command.admin.StopPaymentClientCommand;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/client")
public class ClientController extends BaseController{
	
	@Autowired
	private PaymentClientLocalService clientService;
	@Autowired
	private AlipayPayChannel alipay;
	@Autowired
	private HJBPayChannel hjbPay;
//	@Autowired
//	private HgLogger hgLogger;

	/**
	 * 查询客户端列表
	 * @param request
	 * @param response
	 * @param model
	 * @param qo
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/list")
	public String clientList(HttpServletRequest request,HttpServletResponse response,Model model,
			@ModelAttribute PaymentClientLocalQO qo,
			@RequestParam(value="pageNum",required=false) Integer pageNo, 
			@RequestParam(value="numPerPage",required=false) Integer pageSize){
		
		try{ 
			
			Pagination pagination = new Pagination();
			pagination.setPageNo(pageNo==null? new Integer(1):pageNo);
			pagination.setPageSize(pageSize==null?new Integer(20):pageSize);
			
			if(qo == null){
				qo = new PaymentClientLocalQO();
			}
			
			pagination.setCondition(qo);
			pagination = clientService.queryPagination(pagination);
			
			//查询客户端支付渠道集合信息
			List<PaymentClient> clientList = (List<PaymentClient>) pagination.getList();
			if(clientList != null && clientList.size() > 0){
				for(PaymentClient client :clientList){
					Set<PayChannel> channelSet = new HashSet<PayChannel>();
					String[] channelArr = client.getValidPayChannels().split(",");
					for(String channelType:channelArr){
						PayChannel payChannel = PayChannelUtils.getPayChannel(Integer.valueOf(channelType));
						channelSet.add(payChannel);
//						if(PayChannel.PAY_TYPE_ALIPAY == Integer.valueOf(channelType)){
//							channelSet.add(alipay);
//						}if(PayChannel.PAY_TYPE_HJB == Integer.valueOf(channelType)){
//							channelSet.add(hjbPay);
//						}
					}
					client.setValidPayChannelSet(channelSet);
				}
			}
			pagination.setList(clientList);
			
			//获取支付渠道集合
			List<PayChannel> payChannelList = PayChannelUtils.getPayChannelList();
			
			model.addAttribute("pagination", pagination);
			model.addAttribute("qo",qo);
			model.addAttribute("paychannellist", payChannelList);
			
			return "/client/client_list.html";
			
		}catch(Exception e){
			e.printStackTrace();
			HgLogger.getInstance().error("luoyun", "支付平台查询客户端列表失败:"+e.getMessage());
			model.addAttribute("message", "查询客户端列表失败");
			return "/error/error.html";
		}
		
		
	}
	
	/**
	 * 跳转到添加页面
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/toAdd")
	public String toAdd(HttpServletRequest request,HttpServletResponse response,Model model){
		List<PayChannel> payChannelList = PayChannelUtils.getPayChannelList();
	    model.addAttribute("paychannellist", payChannelList);
		return "/client/client_add.html";
	}
	
	/**
	 * 添加客户端
	 * @param request
	 * @param response
	 * @param command
	 * @return
	 */
	@RequestMapping("/add")
	@ResponseBody
	public String clientAdd(HttpServletRequest request,HttpServletResponse response,
			@ModelAttribute CreatePaymentClientCommand command){
		
		String message = "添加客户端成功";
		try{
			clientService.createPaymentClient(command);
		}catch(Exception e){
			message = e.getMessage();
			HgLogger.getInstance().error("luoyun", "支付平台添加客户端失败:"+e.getMessage());
			return DwzJsonResultUtil.createJsonString("300", message, "closeCurrent", "client");
		}
		return DwzJsonResultUtil.createJsonString("200", message, "closeCurrent", "client");
	}
	
	/**
	 * 跳转到编辑页面
	 * @param request
	 * @param response
	 * @param model
	 * @param id
	 * @return
	 */
	@RequestMapping("/toEdit")
	public String toModify(HttpServletRequest request,HttpServletResponse response,Model model,
			@RequestParam(value="id",required = true) String id){
		
		try{
			
			if(StringUtils.isBlank(id)){
				model.addAttribute("message", "跳转到客户端编辑页面失败,请提供客户端编号");
				return "/error/error.html";
			}
			PaymentClientLocalQO qo = new PaymentClientLocalQO();
			qo.setId(id);
			PaymentClient client = clientService.queryUnique(qo);
			if(client == null){
				model.addAttribute("message", "跳转到客户端编辑页面失败,编号为："+id+"的客户端不存在");
				return "/error/error.html";
			}
			model.addAttribute("client", client);
			if(StringUtils.isNotBlank(client.getValidPayChannels())){
				String[] validPayChannelArr = client.getValidPayChannels().split(",");
				StringBuffer validPayChannel = new StringBuffer();
				for(String channel:validPayChannelArr){
					PayChannel payChannel = PayChannelUtils.getPayChannel(Integer.valueOf(channel));
					validPayChannel = validPayChannel.append(payChannel.getName()+",");
//					if(Integer.valueOf(channel) == PayChannel.PAY_TYPE_ALIPAY){
//						validPayChannel = validPayChannel.append(alipay.getName()+",");
//					}else if(Integer.valueOf(channel) == PayChannel.PAY_TYPE_HJB){
//						validPayChannel = validPayChannel.append(hjbPay.getName()+",");
//					}
				}
				model.addAttribute("validPayChannelName", validPayChannel.toString().subSequence(0, validPayChannel.length()-1));
			}else{
				model.addAttribute("validPayChannelName", "");
			}
			//获取支付渠道列表
			List<PayChannel> payChannelList = PayChannelUtils.getPayChannelList();
			model.addAttribute("paychannellist", payChannelList);
			
			return "/client/client_edit.html";
			
		}catch(Exception e){
			HgLogger.getInstance().error("luoyun", "支付平台跳转到客户端编辑页面失败:"+e.getMessage());
			e.printStackTrace();
			model.addAttribute("message", "跳转到客户端编辑页面失败");
			return "/error/error.html";
		}
		
	}
	
	
	/**
	 * 修改客户端信息
	 * @param request
	 * @param response
	 * @param command
	 * @return
	 */
	@RequestMapping("/edit")
	@ResponseBody
	public String clientModify(HttpServletRequest request,HttpServletResponse response,
			@ModelAttribute ModifyPaymentClientCommand command){
		String message = "修改客户端信息成功";
		try{
			clientService.modifyPaymentClient(command);
		}catch(Exception e){
			HgLogger.getInstance().error("luoyun", "支付平台修改客户端信息失败:"+e.getMessage());
			message = e.getMessage();
			return DwzJsonResultUtil.createJsonString("300", message, "closeCurrent", "client");
		}
		return DwzJsonResultUtil.createJsonString("200", message, "closeCurrent", "client");
	}
	
	/**
	 * 启用客户端
	 * @param request
	 * @param response
	 * @param command
	 * @return
	 */
	@RequestMapping("/start")
	@ResponseBody
	public String startClient(HttpServletRequest request,HttpServletResponse response,
			@RequestParam(value="id",required = true) String id){
		
		String message = "启用客户端成功";
		try{
			StartPaymentClientCommand command = new StartPaymentClientCommand();
			if(StringUtils.isNotBlank(id)){
				command.setId(id);
			}else{
				HgLogger.getInstance().error("luoyun", "支付平台启用客户端失败,请提供客户端编号");
				message = "启用客户端失败,请提供客户端编号";
				return DwzJsonResultUtil.createJsonString("300", message, "", "client");
			}
			clientService.startPaymentClient(command);
		}catch(Exception e){
			message = e.getMessage();
			HgLogger.getInstance().error("luoyun", "支付平台启用客户端失败:"+e.getMessage());
			return DwzJsonResultUtil.createJsonString("300", message, "", "client");
		}
		return DwzJsonResultUtil.createJsonString("200", message, "", "client");
	}
	
	/**
	 * 禁用客户端
	 * @param request
	 * @param response
	 * @param command
	 * @return
	 */
	@RequestMapping("/stop")
	@ResponseBody
	public String stopClient(HttpServletRequest request,HttpServletResponse response,
			@RequestParam(value="id",required = true) String id){
		String message = "禁用客户端成功";
		try{
			StopPaymentClientCommand command = new StopPaymentClientCommand();
			if(StringUtils.isNotBlank(id)){
				command.setId(id);
			}else{
				HgLogger.getInstance().error("luoyun", "支付平台禁用客户端失败,请提供客户端编号");
				message = "禁用客户端失败,请提供客户端编号";
				return DwzJsonResultUtil.createJsonString("300", message, "", "client");
			}
			clientService.stopPaymentClient(command);
		}catch(Exception e){
			HgLogger.getInstance().error("luoyun", "支付平台禁用客户端失败:"+e.getMessage());
			message = e.getMessage();
			return DwzJsonResultUtil.createJsonString("300", message, "", "client");
		}
		return DwzJsonResultUtil.createJsonString("200", message, "", "client");
	}
	
}
