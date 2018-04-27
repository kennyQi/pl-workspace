package zzpl.app.service.local.jp.gn;

import hg.common.component.BaseCommand;
import hg.common.util.UUIDGenerator;
import hg.log.util.HgLogger;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import zzpl.api.client.request.order.FlightOrderInfoQO;
import zzpl.app.dao.jp.FlightOrderDAO;
import zzpl.app.dao.user.COSAOFDAO;
import zzpl.app.dao.workflow.CommentDAO;
import zzpl.app.dao.workflow.TasklistDAO;
import zzpl.app.dao.workflow.WorkflowInstanceOrderDAO;
import zzpl.app.service.local.push.PushService;
import zzpl.app.service.local.util.CommonService;
import zzpl.domain.model.order.FlightOrder;
import zzpl.domain.model.user.COSAOF;
import zzpl.domain.model.workflow.Comment;
import zzpl.domain.model.workflow.Tasklist;
import zzpl.domain.model.workflow.WorkflowInstanceOrder;
import zzpl.pojo.command.workflow.CancelOrderCommentCommand;
import zzpl.pojo.dto.jp.status.FlightOrderStatus;
import zzpl.pojo.qo.jp.FlightOrderQO;
import zzpl.pojo.qo.user.COSAOFQO;
import zzpl.pojo.qo.workflow.WorkflowInstanceOrderQO;

import com.alibaba.fastjson.JSON;
/**
 * 
 * @类功能说明：部门经理退票审批 
 * 
 * 如果更改这个方法需要注意！！！如果在这里更改flightorder的状态值需要在 取消订单通知 里更改 ，因为在取消通知里用flightorder的状态来区分 目前收到的通知是真正的取消订单 还是 因为之前申请机票成本价下单，然后太长时间没支付，如果是前者，则退给申请人钱，如果后者不退钱
 * 
 * 
 * @类修改者：
 * @修改日期：2015年8月29日下午7:38:25
 * @修改说明：
 * @公司名称：浙江票量科技有限公司
 * @作者：cangs
 * @创建时间：2015年8月29日下午7:38:25
 */
@Component("DepartmentCancelService")
public class DepartmentCancelService implements CommonService {

	@Autowired
	private CommentDAO commentDAO;
	@Autowired
	private TasklistDAO tasklistDAO;
	@Autowired
	private FlightOrderDAO flightOrderDAO;
	@Autowired
	private WorkflowInstanceOrderDAO workflowInstanceOrderDAO;
	@Autowired
	private PushService pushService;
	@Autowired
	private COSAOFDAO cosaofdao;
	
	@Override
	public String execute(BaseCommand command, String workflowInstanceID) {
		
		CancelOrderCommentCommand cancelOrderCommentCommand = JSON.parseObject(JSON.toJSONString(command), CancelOrderCommentCommand.class);
		HgLogger.getInstance().info("gk", "【DepartmentCancelService】【execute】"+JSON.toJSONString(cancelOrderCommentCommand));
		
		try {
			Comment comment = new Comment();
			comment.setId(UUIDGenerator.getUUID());
			comment.setCommentJSON(cancelOrderCommentCommand.getCommentJSON());
			comment.setCommentType(cancelOrderCommentCommand.getCommentType());
			comment.setCurrentUserID(cancelOrderCommentCommand.getCurrentUserID());
			comment.setCurrentUserName(cancelOrderCommentCommand.getCurrentUserName());
			comment.setStepNO(cancelOrderCommentCommand.getStepNO());
			comment.setStepName(cancelOrderCommentCommand.getStepName());
			comment.setCommentTime(new Date());
			
			Tasklist tasklist = tasklistDAO.get(cancelOrderCommentCommand.getTasklistID());
			comment.setTasklist(tasklist);
			commentDAO.save(comment);
			
			HgLogger.getInstance().info("gk", "【DepartmentCancelService】【execute】【tasklist】"+JSON.toJSONString(tasklist));
			tasklist.setOpinion(cancelOrderCommentCommand.getOpinion());
			tasklistDAO.update(tasklist);
			
			WorkflowInstanceOrderQO workflowInstanceOrderQO = new WorkflowInstanceOrderQO();
			workflowInstanceOrderQO.setWorkflowInstanceID(workflowInstanceID);
			List<WorkflowInstanceOrder> workflowInstanceOrders = workflowInstanceOrderDAO.queryList(workflowInstanceOrderQO);
			for (WorkflowInstanceOrder workflowInstanceOrder : workflowInstanceOrders) {
				FlightOrderQO flightOrderQO = new FlightOrderQO();
				flightOrderQO.setId(workflowInstanceOrder.getOrderID());
				FlightOrder flightOrder = flightOrderDAO.queryUnique(flightOrderQO);
				flightOrder.setStatus(FlightOrderStatus.CONFIRM_CANCEL_ORDER_SUCCESS);
				HgLogger.getInstance().info("gk", "【DepartmentCancelService】【execute】【flightOrder】:"+JSON.toJSONString(flightOrder));
				flightOrderDAO.update(flightOrder);
				
				COSAOFQO cosaofqo = new COSAOFQO();
				cosaofqo.setOrderID(flightOrder.getId());
				COSAOF cosaof = cosaofdao.queryUnique(cosaofqo);
				cosaof.setOrderStatus(FlightOrderStatus.CONFIRM_CANCEL_ORDER_SUCCESS);
				HgLogger.getInstance().info("cs", "【DepartmentCancelService】【execute】"+"cosaof:"+JSON.toJSONString(cosaof));
				cosaofdao.update(cosaof);
				
				
				Map<String, String> map = new HashMap<String, String>();
				map.put("actionName", "GetFlightOrderInfo");
				FlightOrderInfoQO flightOrderInfoQO = new FlightOrderInfoQO();
				flightOrderInfoQO.setOrderID(flightOrder.getId());
				map.put("payload", JSON.toJSONString(flightOrderInfoQO));
				if (StringUtils.equals(cancelOrderCommentCommand.getOpinion(), "Y")) {
					pushService.pushAndroidByDeviceID(flightOrder.getUserID(),"您的退票审批已通过。", map);
				}else {
					pushService.pushAndroidByDeviceID(flightOrder.getUserID(),"您的退票审批未通过。", map);
				}
			}
			return CommonService.SUCCESS;
		} catch (Exception e) {
			HgLogger.getInstance().error("cs",	"【DepartmentCancelService】【execute】："+e.getMessage());
			return CommonService.FAIL;
		}
		
		
	}

}
