package lxs.admin.api;

import hg.common.util.SMSUtils;
import hg.dzpw.dealer.client.api.v1.request.ScenicSpotQO;
import hg.dzpw.dealer.client.api.v1.request.TicketOrderQO;
import hg.dzpw.dealer.client.api.v1.request.TicketPolicyQO;
import hg.dzpw.dealer.client.api.v1.response.ScenicSpotResponse;
import hg.dzpw.dealer.client.api.v1.response.TicketOrderResponse;
import hg.dzpw.dealer.client.api.v1.response.TicketPolicyResponse;
import hg.dzpw.dealer.client.dto.scenicspot.ScenicSpotDTO;
import hg.dzpw.dealer.client.dto.ticket.GroupTicketDTO;
import hg.log.util.HgLogger;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import lxs.admin.controller.BaseController;
import lxs.app.service.app.CarouselService;
import lxs.app.service.app.RecommendService;
import lxs.app.service.app.SubjectService;
import lxs.app.service.line.LineOrderLocalService;
import lxs.app.service.mp.AppService;
import lxs.app.service.mp.DZPWService;
import lxs.app.service.mp.GroupTicketService;
import lxs.app.service.mp.ScenicSpotPicService;
import lxs.app.service.mp.ScenicSpotSelectiveService;
import lxs.app.service.mp.ScenicSpotService;
import lxs.app.service.mp.SyncDZPWService;
import lxs.app.service.mp.TicketOrderService;
import lxs.app.service.mp.TouristService;
import lxs.domain.model.line.LineOrder;
import lxs.domain.model.mp.GroupTicket;
import lxs.domain.model.mp.ScenicSpot;
import lxs.domain.model.mp.ScenicSpotPic;
import lxs.domain.model.mp.TicketOrder;
import lxs.domain.model.mp.Tourist;
import lxs.pojo.exception.mp.CreateTicketOrderException;
import lxs.pojo.exception.mp.DZPWException;
import lxs.pojo.qo.line.LineOrderQO;
import lxs.pojo.qo.mp.ScenicSpotPicQO;
import lxs.pojo.qo.mp.TouristQO;
import lxs.pojo.status.mp.DZPWNotfiy;
import lxs.spi.inter.line.LineSpiService;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import slfx.xl.pojo.command.line.XLUpdateLineMessageApiCommand;
import slfx.xl.pojo.command.line.XLUpdateOrderStatusMessageApiCommand;
import slfx.xl.pojo.dto.order.LineOrderTravelerDTO;

import com.alibaba.fastjson.JSON;

/**
 * 
 * @类功能说明：旅行社API
 * @类修改者：
 * @修改日期：2015年5月19日下午1:29:26
 * @修改说明：
 * @公司名称：浙江票量科技有限公司
 * @作者：cangs
 * @创建时间：2015年5月19日下午1:29:26
 */
@Controller
@RequestMapping(value = "/api")
public class ApiController extends BaseController {

	@Autowired
	private LineSpiService lineService;
	
	@Autowired
	private LineOrderLocalService lineOrderLocalService;
	
	@Autowired
	private SMSUtils smsUtils;
	
	@Autowired
	private ScenicSpotService scenicSpotService;
	
	@Autowired
	private DZPWService dzpwService;
	
	@Autowired
	private SyncDZPWService syncDZPWService;
	
	@Autowired
	private ScenicSpotPicService scenicSpotPicService;
	
	@Autowired
	private ScenicSpotSelectiveService scenicSpotSelectiveService;
	
	@Autowired
	private SubjectService subjectService;
	
	@Autowired
	private TicketOrderService ticketOrderService;
	
	@Autowired
	private RecommendService recommendService;
	
	@Autowired
	private CarouselService carouselService;
	
	@Autowired
	private GroupTicketService groupTicketService;
	
	@Autowired
	private JedisPool jedisPool;
	
	@Autowired
	private TouristService touristService;
	@Autowired
	private AppService appService;
	
	/**
	 * 
	 * @方法功能说明：分销通知旅行社修改线路
	 * @修改者名字：cangs
	 * @修改时间：2015年5月19日下午1:28:11
	 * @修改内容：
	 * @参数：@param request
	 * @参数：@return
	 * @return:String
	 * @throws
	 */
	@RequestMapping(value = "/sync/line")
	@ResponseBody
	public String syncLine(HttpServletRequest request) {
		// 获取分销改变的的线路
		String variableLine = request.getParameter("variableLine").trim();
		HgLogger.getInstance().info("lxs_dev","【syncLine】"+"同步线路接口>改变的值>>:" + JSON.toJSONString(variableLine));
		XLUpdateLineMessageApiCommand xlUpdateLineMessageApiCommand = JSON.parseObject(variableLine, XLUpdateLineMessageApiCommand.class);
		try {
			synchronized(new Object()){
				lineService.updateLineData(xlUpdateLineMessageApiCommand);
			}
		} catch (Exception e) {
			 HgLogger.getInstance().info("lxs_dev", "【syncLine】"+"同步线路接口接口出现异常"+HgLogger.getStackTrace(e));
			 
			return "Fail";
		}
		return "SUCCESS";
	}
	
	/**
	 * 
	 * @方法功能说明：分销通知旅行社修改订单状态
	 * @修改者名字：cangs
	 * @修改时间：2015年5月19日下午1:28:34
	 * @修改内容：
	 * @参数：@param request
	 * @参数：@return
	 * @return:String
	 * @throws
	 */
	@RequestMapping(value = "/sync/lineOrderStatus")
	@ResponseBody
	public String syncLineOrderStatus(HttpServletRequest request) {
		HgLogger.getInstance().info("lxs_dev","【syncLineOrderStatus】"+"同步线路订单状态"+"开始>>>>>" );
		// 获取分销改变的的线路
		String lineOrder = request.getParameter("variableLine").trim();
		HgLogger.getInstance().info("lxs_dev","【syncLineOrderStatus】"+"同步线路订单状态"+ ">改变的值>>:" + JSON.toJSONString(lineOrder));
		XLUpdateOrderStatusMessageApiCommand updateLineOrderStatusCommand = JSON.parseObject(lineOrder, XLUpdateOrderStatusMessageApiCommand.class);
		try {
			boolean status=lineOrderLocalService.updateLineOrderStatus(updateLineOrderStatusCommand);
			if(!status){
				HgLogger.getInstance().info("lxs_dev","【syncLineOrderStatus】"+"同步线路订单状态失败，订单不存在");
				return "Fail";
			}
		} catch (Exception e) {
			e.printStackTrace();
			HgLogger.getInstance().info("lxs_dev","【syncLineOrderStatus】"+"同步线路订单状态"+"接口出现异常" + HgLogger.getStackTrace(e));
			return "Fail";
		}
		//实现短信通知功能
		 Set<LineOrderTravelerDTO> travelers = updateLineOrderStatusCommand.getTravelers();
		 for (LineOrderTravelerDTO lineOrderTravelerDTO : travelers) {
			if(lineOrderTravelerDTO.getXlOrderStatus().getStatus()==2003){
				final String dealerOrderNo = updateLineOrderStatusCommand.getLineOrderID();
				LineOrderQO lineOrderQO = new LineOrderQO();
				lineOrderQO.setDealerOrderNo(dealerOrderNo);
				HgLogger.getInstance().info("lxs_dev","【syncLineOrderStatus】"+"【短信通知】"+"订单号：" + dealerOrderNo);
				final LineOrder myLineOrder = lineOrderLocalService.queryUnique(lineOrderQO);
				new Thread(){
					public void run(){
						if(myLineOrder!=null&&myLineOrder.getLinkInfo()!=null&&myLineOrder.getLinkInfo().getLinkMobile()!=null){
							try {
								String text = "【票量旅游】亲爱的用户，您的订单"+dealerOrderNo+"已生效，请您在预订时间内出行，祝您旅途愉快！客服专线0571-28280815。";
								HgLogger.getInstance().info("lxs_dev","【syncLineOrderStatus】"+"【短信通知】"+"发送短信内容，电话"+myLineOrder.getLinkInfo().getLinkMobile());
								HgLogger.getInstance().info("lxs_dev","【syncLineOrderStatus】"+"【短信通知】"+"发送短信内容，内容"+text);
								smsUtils.sendSms(myLineOrder.getLinkInfo().getLinkMobile(),text);
							} catch (Exception e) {
								HgLogger.getInstance().info("lxs_dev","【syncLineOrderStatus】"+"【短信通知】"+"发生异常" +HgLogger.getStackTrace(e));
							}
							HgLogger.getInstance().info("lxs_dev","【syncLineOrderStatus】"+"【短信通知】"+"发送短信内容【成功】");
						}
					}
				}.start();
				break;
			}
		}
		return "SUCCESS";
	}
	
	
	
	
	/**
	 * 
	 * @方法功能说明：分销通知旅行社修改线路
	 * @修改者名字：cangs
	 * @修改时间：2015年5月19日下午1:28:11
	 * @修改内容：
	 * @参数：@param request
	 * @参数：@return
	 * @return:String
	 * @throws
	 */
	@RequestMapping(value = "/DZPWNotfiy")
	@ResponseBody
	public String DZPWNotfiy(HttpServletRequest request,HttpServletResponse response, 
			@RequestParam(value = "event", required = false) String event,
			@RequestParam(value = "message", required = false) String message) {
		HgLogger.getInstance().info("lxs_dev", "【收到电子票务通知】");
		if(StringUtils.isBlank(event)||StringUtils.isBlank(message)){
			HgLogger.getInstance().error("lxs_dev", "ruturn false");
			return "FAIL";
		}else{
			HgLogger.getInstance().info("lxs_dev", "【收到电子票务通知】event："+event);
			HgLogger.getInstance().info("lxs_dev", "【收到电子票务通知】message："+message);
			try{
				if(StringUtils.equals(event, DZPWNotfiy.SCENIC_SPOT_ISSUE)){
					HgLogger.getInstance().info("lxs_dev", "【收到电子票务通知】【景区启用】");
					/**					景区启用					*/
					//更新景区相关
					this.updateScenicSpot(message);
				}else if(StringUtils.equals(event, DZPWNotfiy.SCENIC_SPOT_FINISH)){
					HgLogger.getInstance().info("lxs_dev", "【收到电子票务通知】【景区禁用】");
					/**					景区禁用					*/
					ScenicSpot scenicSpot = scenicSpotService.get(message);
					if(scenicSpot!=null){
						//1更新主题
						if(scenicSpot.getBaseInfo()!=null&&StringUtils.isNotBlank(scenicSpot.getBaseInfo().getTheme())){
							subjectService.updateSubjectList(scenicSpot.getBaseInfo().getTheme().split("\\s"),1);
						}
						//2删除原来关联的景区图片
						ScenicSpotPicQO scenicSpotPicQO = new ScenicSpotPicQO();
						scenicSpotPicQO.setScenicSpotID(scenicSpot.getId());
						List<ScenicSpotPic> scenicSpotPics =	scenicSpotPicService.queryList(scenicSpotPicQO);
						for (ScenicSpotPic scenicSpotPic : scenicSpotPics) {
							scenicSpotPicService.deleteById(scenicSpotPic.getId());
						}
						//3删除景区
						subjectService.deleteById(scenicSpot.getId());
						//4更新精选
						scenicSpotSelectiveService.delSelectiveByNullScenicSpot();
						//5更新推荐
						recommendService.refresh();
						//6更新轮播
						carouselService.refresh();
						//7更新联票
						groupTicketService.deleteScenciSoptGroupTicket(scenicSpot.getId());
					}
				}else if(StringUtils.equals(event, DZPWNotfiy.TICKET_POLICY_ISSUE)){
					HgLogger.getInstance().info("lxs_dev", "【收到电子票务通知】【政策上架】");
					/**					政策上架					*/
					String[] ticketPolicyIds = {message};
					TicketPolicyQO ticketPolicyQO = new TicketPolicyQO();
					ticketPolicyQO.setCalendarFetch(true);
					ticketPolicyQO.setTicketPolicyIds(ticketPolicyIds);
					TicketPolicyResponse ticketPolicyResponse = dzpwService.queryTicketPolicy(ticketPolicyQO);
					if(ticketPolicyResponse!=null&&ticketPolicyResponse.getTicketPolicies().size()>0&&StringUtils.isNotBlank(ticketPolicyResponse.getTicketPolicies().get(0).getScenicSpotId())){
						this.updateScenicSpot(ticketPolicyResponse.getTicketPolicies().get(0).getScenicSpotId());
					}
				}else if(StringUtils.equals(event, DZPWNotfiy.TICKET_POLICY_FINISH)){
					HgLogger.getInstance().info("lxs_dev", "【收到电子票务通知】【政策下架】");
					/**					政策下架					*/
					groupTicketService.deleteById(message);
				}else if(StringUtils.equals(event, DZPWNotfiy.TICKET_ORDER_CLOSE)){
					HgLogger.getInstance().info("lxs_dev", "【收到电子票务通知】【关闭订单】");
					/**					关闭订单					*/
					TicketOrder ticketOrder = ticketOrderService.get(message);
					if(ticketOrder!=null){
						TouristQO touristQO = new TouristQO();
						touristQO.setOrderID(ticketOrder.getId());
						List<Tourist> tourists = touristService.queryList(touristQO);
						if(ticketOrder.getLocalPayStatus()==TicketOrder.PAYED){
							//已支付
							/**
							 * 调用退款
							 */
							appService.alipayRefund(ticketOrder.getSerialNumber(), ticketOrder.getPrice());
							ticketOrder.setCurrentValue(TicketOrder.ORDER_STATUS_DEAL_CLOSE);
							ticketOrder.localstatus(TicketOrder.ORDER_STATUS_DEAL_CLOSE, TicketOrder.PAYED, Tourist.WAIT_TO_REFUND_USER);
							ticketOrderService.update(ticketOrder);
							for (Tourist tourist : tourists) {
								tourist.setLocalStatus(Tourist.WAIT_TO_REFUND_USER);
								touristService.update(tourist);
							}
						}else if(ticketOrder.getLocalPayStatus()==TicketOrder.WAIT_TO_PAY){
							//未支付
							ticketOrder.setCurrentValue(TicketOrder.ORDER_STATUS_DEAL_CLOSE);
							ticketOrder.localstatus(TicketOrder.ORDER_STATUS_DEAL_CLOSE, TicketOrder.WAIT_TO_PAY, Tourist.NOTHING);
							ticketOrderService.update(ticketOrder);
						}
						ticketOrder.setCurrentValue(TicketOrder.ORDER_STATUS_DEAL_CLOSE);
						ticketOrderService.update(ticketOrder);
					}
				}else if(StringUtils.equals(event, DZPWNotfiy.TICKET_ORDER_STATUS_CHANGE)){
					HgLogger.getInstance().info("lxs_dev", "【收到电子票务通知】【订单状态变化】");
					/**					订单状态变化					*/
					TicketOrder ticketOrder = ticketOrderService.get(message);
					if(ticketOrder!=null){
						TicketOrderQO ticketOrderQO = new TicketOrderQO();
						ticketOrderQO.setOrderId(message);
						ticketOrderQO.setGroupTicketsFetch(true);
						ticketOrderQO.setSingleTicketsFetch(true);
						ticketOrderQO.setTouristFetch(true);
						TicketOrderResponse ticketOrderResponse = new TicketOrderResponse();
						ticketOrderResponse = dzpwService.queryTicketOrder(ticketOrderQO);
						if(ticketOrderResponse.getTicketOrders()!=null&&ticketOrderResponse.getTicketOrders().get(0).getGroupTickets()!=null){
							List<GroupTicketDTO> groupTicketDTOs =ticketOrderResponse.getTicketOrders().get(0).getGroupTickets();
							boolean flag = false;
							for (GroupTicketDTO groupTicketDTO : groupTicketDTOs) {
								if(groupTicketDTO.getStatus()!=null&&groupTicketDTO.getStatus().getRefundCurrent()!=null){
									if(groupTicketDTO.getStatus().getRefundCurrent()==0)
										flag=true;
								}
							}
							if(flag){
								ticketOrder.setReturnRule(0);
								ticketOrderService.update(ticketOrder);
							}
						}
					}
				}else{
					HgLogger.getInstance().error("lxs_dev", "【收到电子票务通知】ruturn false");
					return "FAIL";
				}
			}catch(Exception e){
				HgLogger.getInstance().error("lxs_dev", HgLogger.getStackTrace(e));
				HgLogger.getInstance().error("lxs_dev", "【收到电子票务通知】ruturn false");
				return "FAIL";
			}
		}
		HgLogger.getInstance().error("lxs_dev", "【收到电子票务通知】ruturn true");
		return "SUCCESS";
	}

	/**
	 * @throws Exception 
	 * 
	 * @方法功能说明：传入景区id更新相关的景区和联票
	 * @修改者名字：cangs
	 * @修改时间：2016年4月7日上午9:50:36
	 * @修改内容：
	 * @参数：@param message
	 * @return:void
	 * @throws
	 */
	public void updateScenicSpot(String message) throws Exception{
		ScenicSpot scenicSpot = scenicSpotService.get(message);
		//初始化版本号
		int versionNO = this.getLastVersionNO(); 
		if(scenicSpot!=null){
			//1更新主题
			if(scenicSpot.getBaseInfo()!=null&&StringUtils.isNotBlank(scenicSpot.getBaseInfo().getTheme())){
				subjectService.updateSubjectList(scenicSpot.getBaseInfo().getTheme().split("\\s"),1);
			}
			//2删除原来关联的景区图片
			ScenicSpotPicQO scenicSpotPicQO = new ScenicSpotPicQO();
			scenicSpotPicQO.setScenicSpotID(scenicSpot.getId());
			List<ScenicSpotPic> scenicSpotPics =	scenicSpotPicService.queryList(scenicSpotPicQO);
			for (ScenicSpotPic scenicSpotPic : scenicSpotPics) {
				scenicSpotPicService.deleteById(scenicSpotPic.getId());
			}
			//3获取老版本号
			versionNO = scenicSpot.getVersionNO();
		}
		//4获取远程新景区
		ScenicSpotQO scenicSpotQO = new ScenicSpotQO();
		scenicSpotQO.setScenicSpotId(message);
		ScenicSpotResponse scenicSpotResponse = dzpwService.queryScenicSpot(scenicSpotQO);
		Map<String,GroupTicket> groupTicketMap = new HashMap<String, GroupTicket>();
		if(scenicSpotResponse!=null&&scenicSpotResponse.getScenicSpots().size()!=0){
			ScenicSpotDTO scenicSpotDTO = scenicSpotResponse.getScenicSpots().get(0);
			groupTicketMap = syncDZPWService.saveRemoteScenicSpot(scenicSpotDTO, versionNO,groupTicketMap);
			//5更新精选
			scenicSpotSelectiveService.delSelectiveByNullScenicSpot();
			//6更新主题
			if(scenicSpotDTO.getBaseInfo()!=null&&StringUtils.isNotBlank(scenicSpotDTO.getBaseInfo().getTheme())){
				String[] themes = scenicSpotDTO.getBaseInfo().getTheme().split("\\s");
				subjectService.updateSubjectList(themes,2);
			}
		}
		//7更新联票
		groupTicketService.saveGroupTicketMap(groupTicketMap);
	}
 
	public int getLastVersionNO(){
		int scenicSpotVersion = 0;
		Jedis jedis = null;
		jedis = jedisPool.getResource();
		String redisScenicSpotVersion=jedis.get("SUIXINYOU_"+"SCENICSPOT_"+"VERSION");
		if(!StringUtils.isBlank(redisScenicSpotVersion)){
			scenicSpotVersion = Integer.parseInt(redisScenicSpotVersion)-1;
		}
		return scenicSpotVersion;
	}
}
