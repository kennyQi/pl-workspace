package zzpl.app.service.local.jp.gn;

import hg.common.component.BaseCommand;
import hg.common.util.SMSUtils;
import hg.common.util.SysProperties;
import hg.common.util.UUIDGenerator;
import hg.log.util.HgLogger;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import zzpl.api.client.request.order.FlightOrderInfoQO;
import zzpl.app.dao.jp.FlightOrderDAO;
import zzpl.app.dao.jp.PassengerDAO;
import zzpl.app.dao.user.COSAOFDAO;
import zzpl.app.dao.user.CostCenterDAO;
import zzpl.app.dao.user.CostCenterOrderDAO;
import zzpl.app.dao.user.UserDAO;
import zzpl.app.dao.workflow.CommentDAO;
import zzpl.app.dao.workflow.TasklistDAO;
import zzpl.app.dao.workflow.WorkflowInstanceOrderDAO;
import zzpl.app.service.local.push.PushService;
import zzpl.app.service.local.util.CommonService;
import zzpl.domain.model.order.FlightOrder;
import zzpl.domain.model.order.Passenger;
import zzpl.domain.model.user.COSAOF;
import zzpl.domain.model.user.CostCenterOrder;
import zzpl.domain.model.user.User;
import zzpl.domain.model.workflow.Comment;
import zzpl.domain.model.workflow.Tasklist;
import zzpl.domain.model.workflow.WorkflowInstanceOrder;
import zzpl.pojo.command.jp.ManaulVoteTicketCommand;
import zzpl.pojo.dto.jp.status.COSAOFStatus;
import zzpl.pojo.dto.jp.status.FlightOrderStatus;
import zzpl.pojo.dto.jp.status.FlightPayStatus;
import zzpl.pojo.dto.workflow.CommentDTO;
import zzpl.pojo.qo.jp.FlightOrderQO;
import zzpl.pojo.qo.jp.PassengerQO;
import zzpl.pojo.qo.user.COSAOFQO;
import zzpl.pojo.qo.workflow.WorkflowInstanceOrderQO;

import com.alibaba.fastjson.JSON;

/**
 * 
 * @类功能说明：手动出票
 * @类修改者：
 * @修改日期：2015年8月29日下午5:23:33
 * @修改说明：
 * @公司名称：浙江票量科技有限公司
 * @作者：cangs
 * @创建时间：2015年8月29日下午5:23:33
 */
@Component("ManaulGNFlightService")
public class ManaulGNFlightService implements CommonService {

	@Autowired
	private FlightOrderDAO flightOrderDAO;
	@Autowired
	private WorkflowInstanceOrderDAO workflowInstanceOrderDAO;
	@Autowired
	private TasklistDAO tasklistDAO;
	@Autowired
	private CommentDAO commentDAO;
	@Autowired
	private PassengerDAO passengerDAO;
	@Autowired
	private UserDAO userDAO;
	@Autowired
	private COSAOFDAO cosaofdao;
	@Autowired
	private PushService pushService;
	@Autowired
	private SMSUtils smsUtils;
	@Autowired
	private CostCenterOrderDAO costCenterOrderDAO;
	@Autowired
	private CostCenterDAO costCenterDAO;
	
	@Override
	public String execute(BaseCommand command, String workflowInstanceID) {
		//手动出票
		ManaulVoteTicketCommand manaulVoteTicketCommand = JSON.parseObject(JSON.toJSONString(command), ManaulVoteTicketCommand.class);
		HgLogger.getInstance().info("gk", "【ManaulGNFlightService】【execute】【manaulVoteTicketCommand】"+JSON.toJSONString(manaulVoteTicketCommand));
		try {
			//查询订单
			WorkflowInstanceOrderQO workflowInstanceOrderQO = new WorkflowInstanceOrderQO();
			workflowInstanceOrderQO.setWorkflowInstanceID(workflowInstanceID);
			List<WorkflowInstanceOrder> workflowInstanceOrders = workflowInstanceOrderDAO.queryList(workflowInstanceOrderQO);
			for (WorkflowInstanceOrder workflowInstanceOrder : workflowInstanceOrders) {
				FlightOrderQO flightOrderQO = new FlightOrderQO();
				flightOrderQO.setId(workflowInstanceOrder.getOrderID());
				FlightOrder flightOrder = flightOrderDAO.queryUnique(flightOrderQO);
				HgLogger.getInstance().info("gk", "【ManaulGNFlightService】【execute】【flightOrder】:"+JSON.toJSONString(flightOrder));
				flightOrder.setTotalPrice(manaulVoteTicketCommand.getTotalPrice());
				flightOrder.setPlatTotalPrice(manaulVoteTicketCommand.getPlatTotalPrice());
				flightOrder.setPrintTicketType(FlightOrder.MANAUL_VOTE_PRIMT_TICKET);
				flightOrder.setStatus(FlightOrderStatus.PRINT_TICKET_SUCCESS);
				flightOrder.setPayStatus(FlightPayStatus.PAY_SUCCESS);
				if(manaulVoteTicketCommand.getTicketChannel()!=null){
					flightOrder.setTicketChannel(manaulVoteTicketCommand.getTicketChannel());
				}
				if(manaulVoteTicketCommand.getTicketChannel()!=null){
					switch (manaulVoteTicketCommand.getTicketChannel()) {
					case 2:
						flightOrder.setTicketChannelName("去哪");
						break;
					case 3:
						flightOrder.setTicketChannelName("携程");
						break;
					default:
						flightOrder.setTicketChannelName(manaulVoteTicketCommand.getTicketChannelName());
						break;
					}
				}
				HgLogger.getInstance().info("gk", "【ManaulGNFlightService】手动出票更新订单"+"flightOrder:"+JSON.toJSONString(flightOrder));
				flightOrderDAO.update(flightOrder);
				PassengerQO passengerQO = new PassengerQO();
				passengerQO.setFlightOrderID(flightOrder.getId());
				List<Passenger> passengers = passengerDAO.queryList(passengerQO);
				for (Passenger passenger : passengers) {
					passenger.setStatus(FlightOrderStatus.PRINT_TICKET_SUCCESS.toString());
					passenger.setAirID(manaulVoteTicketCommand.getAirID());
					passengerDAO.update(passenger);
				}
				
				//添加默认意见信息
				CommentDTO commentDTO = new CommentDTO();
				commentDTO.setAccount("手动出票");
				commentDTO.setAirID(manaulVoteTicketCommand.getAirID());
				commentDTO.setFlightNO(manaulVoteTicketCommand.getFlightNO());
				Comment comment = new Comment();
				comment.setId(UUIDGenerator.getUUID());
				commentDTO.setStartData(manaulVoteTicketCommand.getStartData());
				comment.setCommentJSON(JSON.toJSONString(commentDTO));
				comment.setCommentType(CommentDTO.class.getSimpleName());
				comment.setCurrentUserID(manaulVoteTicketCommand.getCurrentUserID());
				comment.setCurrentUserName(manaulVoteTicketCommand.getCurrentUserName());
				comment.setStepNO(manaulVoteTicketCommand.getStepNO());
				comment.setStepName(manaulVoteTicketCommand.getStepName());
				comment.setCommentTime(new Date());
				Tasklist tasklist = tasklistDAO.get(manaulVoteTicketCommand.getTasklistID());
				comment.setTasklist(tasklist);
				commentDAO.save(comment);
				
				/**
				 * 如果要是选择公司代扣 需要新增结算中心 和 订单流转记录
				 * 
				 * 如果是 申请人自己支付 则已经有 结算中心 和 订单流转记录了 需要 将结算价格更更新进去
				 */
				if(Integer.parseInt(flightOrder.getPayType())==FlightOrder.PAY_BY_TRAVEL){
					/**
					 * 创建结算中心数据
					 */
					User user = userDAO.get(flightOrder.getUserID());
					COSAOF cosaof = new COSAOF();
					cosaof.setId(UUIDGenerator.getUUID());
					PassengerQO passengerQO1 = new PassengerQO();
					passengerQO1.setFlightOrderID(flightOrder.getId());
					List<Passenger> passengers1 = passengerDAO.queryList(passengerQO1);
					String passengerNames = "";
					for (Passenger passenger : passengers1) {
						passengerNames=passengerNames+passenger.getPassengerName()+";";
					}
					cosaof.setPassengerName(passengerNames.substring(0, passengerNames.length()-1));
					cosaof.setOrderType(CostCenterOrder.GN_FLIGHT_ORDER);
					cosaof.setOrderID(flightOrder.getId());
					cosaof.setCompanyID(user.getCompanyID());
					cosaof.setCompanyName(user.getCompanyName());
					cosaof.setAirID(manaulVoteTicketCommand.getAirID());
					cosaof.setDepartmentID(user.getDepartmentID());
					cosaof.setDepartmentName(user.getDepartmentName());
					cosaof.setVoyage(flightOrder.getOrgCityName()+"--"+flightOrder.getDstCityName());
					cosaof.setTotalPrice(manaulVoteTicketCommand.getTotalPrice());
					cosaof.setPlatTotalPrice(manaulVoteTicketCommand.getPlatTotalPrice());
					cosaof.setPayType(flightOrder.getPayType());
					cosaof.setCreateTime(flightOrder.getCreateTime());
					cosaof.setCosaofStatus(COSAOFStatus.NOT_SETTLED);
					cosaof.setRefundPrice(0.0);
					cosaof.setCasaofPrice(manaulVoteTicketCommand.getPlatTotalPrice()-cosaof.getRefundPrice());
					cosaof.setOrderStatus(FlightOrderStatus.PRINT_TICKET_SUCCESS);
					HgLogger.getInstance().info("cs", "【手动出票成功】【公司代扣】【记录结算中心】"+"cosaof:"+JSON.toJSONString(cosaof));
					cosaofdao.save(cosaof);
					/*
					 * 创建成本中心订单记录
					 */
					/*CostCenterOrder costCenterOrder = new CostCenterOrder();
					costCenterOrder.setId(UUIDGenerator.getUUID());
					costCenterOrder.setOrderNO(flightOrder.getOrderNO());
					if (StringUtils.isNotBlank(flightOrder.getCostCenterID())) {
						CostCenter costCenter = costCenterDAO.get(flightOrder.getCostCenterID());
						costCenterOrder.setCostCenter(costCenter);
					}
					costCenterOrder.setOrderType(CostCenterOrder.GN_FLIGHT_ORDER);
					costCenterOrder.setOrderID(flightOrder.getId());
					costCenterOrder.setOrderNO(flightOrder.getOrderNO());
					costCenterOrder.setUserID(user.getId());
					costCenterOrder.setName(user.getName());
					costCenterOrder.setIdCardNO(user.getIdCardNO());
					costCenterOrder.setCompanyID(user.getCompanyID());
					costCenterOrder.setCompanyName(user.getCompanyName());
					costCenterOrder.setDepartmentID(user.getDepartmentID());
					costCenterOrder.setDepartmentName(user.getDepartmentName());
					costCenterOrder.setCostfPrice(manaulVoteTicketCommand.getPlatTotalPrice());
					costCenterOrder.setCreateTime(new Date());
					HgLogger.getInstance().info("cs", "【手动出票成功】【公司代扣】【记录订单流转信息】"+"costCenterOrder:"+JSON.toJSONString(costCenterOrder));
					costCenterOrderDAO.save(costCenterOrder);*/
				}else{
					COSAOFQO cosaofqo = new COSAOFQO();
					cosaofqo.setOrderID(flightOrder.getId());
					COSAOF cosaof = cosaofdao.queryUnique(cosaofqo);
					cosaof.setAirID(manaulVoteTicketCommand.getAirID());
					cosaof.setTotalPrice(manaulVoteTicketCommand.getTotalPrice());
					cosaof.setPlatTotalPrice(manaulVoteTicketCommand.getPlatTotalPrice());
					cosaof.setRefundPrice(0.0);
					cosaof.setCasaofPrice(manaulVoteTicketCommand.getPlatTotalPrice()-cosaof.getUserPayPrice());
					cosaof.setOrderStatus(FlightOrderStatus.PRINT_TICKET_SUCCESS);
					HgLogger.getInstance().info("cs", "【手动出票成功】【申请人支付】【更新】【记录结算中心】"+"cosaof:"+JSON.toJSONString(cosaof));
					cosaofdao.update(cosaof);
					
					/*
					 * 创建成本中心订单记录
					 */
					/*User user = userDAO.get(flightOrder.getUserID());
					CostCenterOrder costCenterOrder = new CostCenterOrder();
					costCenterOrder.setId(UUIDGenerator.getUUID());
					costCenterOrder.setOrderNO(flightOrder.getOrderNO());
					if (StringUtils.isNotBlank(flightOrder.getCostCenterID())) {
						CostCenter costCenter = costCenterDAO.get(flightOrder.getCostCenterID());
						costCenterOrder.setCostCenter(costCenter);
					}
					costCenterOrder.setOrderType(CostCenterOrder.GN_FLIGHT_ORDER);
					costCenterOrder.setOrderID(flightOrder.getId());
					costCenterOrder.setOrderNO(flightOrder.getOrderNO());
					costCenterOrder.setUserID(user.getId());
					costCenterOrder.setName(user.getName());
					costCenterOrder.setIdCardNO(user.getIdCardNO());
					costCenterOrder.setCompanyID(user.getCompanyID());
					costCenterOrder.setCompanyName(user.getCompanyName());
					costCenterOrder.setDepartmentID(user.getDepartmentID());
					costCenterOrder.setDepartmentName(user.getDepartmentName());
					costCenterOrder.setCostfPrice(manaulVoteTicketCommand.getPlatTotalPrice());
					costCenterOrder.setCreateTime(new Date());
					HgLogger.getInstance().info("cs", "【手动出票成功】【申请人支付】【记录订单流转信息】"+"costCenterOrder:"+JSON.toJSONString(costCenterOrder));
					costCenterOrderDAO.save(costCenterOrder);*/
				}
				//手动出票消息推送
				Map<String, String> map = new HashMap<String, String>();
				map.put("actionName", "GetFlightOrderInfo");
				FlightOrderInfoQO flightOrderInfoQO = new FlightOrderInfoQO();
				flightOrderInfoQO.setOrderID(flightOrder.getId());
				map.put("payload", JSON.toJSONString(flightOrderInfoQO));
				pushService.pushAndroidByDeviceID(flightOrder.getUserID(),"您有一张机票已出票", map);
				final String text = "【"+SysProperties.getInstance().get("smsSign")+"】起飞时间"+flightOrder.getStartTime().toString().substring(0, flightOrder.getStartTime().toString().length()-5)+"降落时间"+flightOrder.getEndTime().toString().substring(0, flightOrder.getEndTime().toString().length()-5)+"的"+flightOrder.getAirCompanyName()+flightOrder.getFlightNO()+flightOrder.getOrgCityName()+"-"+flightOrder.getDstCityName()+"已出票，"+passengers.get(0).getPassengerName()+"票号"+passengers.get(0).getAirID()+"。请提前2个小时到机场办理乘机手续。客服电话0571-28280813。";
				final String linkMobile = flightOrder.getLinkTelephone();
				HgLogger.getInstance().info("cs", "【ManaulGNFlightService】"+"发送短信内容，电话"+flightOrder.getLinkTelephone());
				HgLogger.getInstance().info("cs", "【ManaulGNFlightService】"+"发送短信内容，内容"+text);
				new Thread(){
					public void run(){
						try {
							smsUtils.sendSms(linkMobile, text);
						} catch (Exception e) {
							HgLogger.getInstance().info("cs", "【ManaulGNFlightService】"+"发送短信异常，"+HgLogger.getStackTrace(e));
						}
					}
				}.start();
				return CommonService.SUCCESS;
			}
			return CommonService.SUCCESS;
		} catch (Exception e) {
			HgLogger.getInstance().error("cs",	"【ManaulGNFlightService】【execute】【手动出票】：手动出票失败"+e.getMessage());
			return CommonService.FAIL;
		}
		
	}
	
}
