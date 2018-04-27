package zzpl.app.service.local.workflow;

import hg.common.component.BaseCommand;
import hg.common.component.BaseServiceImpl;
import hg.common.util.BeanMapperUtils;
import hg.common.util.SMSUtils;
import hg.common.util.SysProperties;
import hg.common.util.UUIDGenerator;
import hg.log.util.HgLogger;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import zzpl.app.dao.user.UserDAO;
import zzpl.app.dao.workflow.StepActionDAO;
import zzpl.app.dao.workflow.TasklistDAO;
import zzpl.app.dao.workflow.TasklistWaitDAO;
import zzpl.app.dao.workflow.WorkflowInstanceDAO;
import zzpl.app.dao.workflow.WorkflowStepActionDAO;
import zzpl.app.dao.workflow.WorkflowStepDAO;
import zzpl.app.service.local.util.CommonService;
import zzpl.app.service.local.util.ServiceContext;
import zzpl.domain.model.user.User;
import zzpl.domain.model.workflow.StepAction;
import zzpl.domain.model.workflow.Tasklist;
import zzpl.domain.model.workflow.TasklistWait;
import zzpl.domain.model.workflow.WorkflowInstance;
import zzpl.domain.model.workflow.WorkflowStep;
import zzpl.domain.model.workflow.WorkflowStepAction;
import zzpl.pojo.command.workflow.ModifyWorkflowStepCommand;
import zzpl.pojo.command.workflow.SendCommand;
import zzpl.pojo.command.workflow.SendbackCommand;
import zzpl.pojo.command.workflow.StartWorkflowCommand;
import zzpl.pojo.dto.workflow.ExecutorDTO;
import zzpl.pojo.dto.workflow.status.WorkflowInstanceStatus;
import zzpl.pojo.dto.workflow.status.WorkflowStepActionStatus;
import zzpl.pojo.exception.workflow.WorkflowException;
import zzpl.pojo.qo.workflow.TasklistQO;
import zzpl.pojo.qo.workflow.TasklistWaitQO;
import zzpl.pojo.qo.workflow.WorkflowStepActionQO;
import zzpl.pojo.qo.workflow.WorkflowStepQO;

import com.alibaba.fastjson.JSON;

@Service
@Transactional
public class WorkflowStepService extends
		BaseServiceImpl<WorkflowStep, WorkflowStepQO, WorkflowStepDAO> {
	@Autowired
	private WorkflowStepDAO workflowStepDAO;
	@Autowired
	private UserDAO userDAO;
	@Autowired
	private WorkflowInstanceDAO workflowInstanceDAO;
	@Autowired
	private TasklistDAO tasklistDAO;
	@Autowired
	private TasklistWaitDAO tasklistWaitDAO;
	@Autowired
	private WorkflowStepActionDAO workflowStepActionDAO;
	@Autowired
	private StepActionDAO stepActionDAO;
	@Autowired
	private SMSUtils smsUtils;
	
	@Resource
	private ServiceContext serviceContext;

	/**
	 * 流程启动环节为0
	 */
	public final static String STEP_0 = "0";
	/**
	 * 第一个环节为1
	 */
	public final static Integer STEP_1 = 1;
	/**
	 * 流程启动环节为99
	 */
	public final static Integer STEP_99 = 99;

	/**
	 * 
	 * @方法功能说明：转送下一步
	 * @修改者名字：cangs
	 * @修改时间：2015年7月2日下午3:10:53
	 * @修改内容：
	 * @参数：@param sendCommand
	 * @参数：@param command
	 * @return:void
	 * @throws
	 */
	public void send(SendCommand sendCommand, String[] services, BaseCommand command) throws WorkflowException{
		//查询当前环节
		WorkflowStepQO workflowStepQO = new WorkflowStepQO();
		workflowStepQO.setWorkflowID(sendCommand.getWorkflowID());
		workflowStepQO.setStepNO(sendCommand.getCurrentStepNO());
		WorkflowStep workflowStep = workflowStepDAO.queryUnique(workflowStepQO);
		//获得当前环节进行判断，如果上一环节是0，说明是启动流程
		String workflowInstanceID = "";
		if(StringUtils.equals(STEP_0, workflowStep.getPreviousStepNO())&&sendCommand.getWorkflowInstanceID()==null){
			//启动流程
			workflowInstanceID = startWorkflow(JSON.parseObject(JSON.toJSONString(sendCommand),StartWorkflowCommand.class));
		}else{
			workflowInstanceID=sendCommand.getWorkflowInstanceID();
		}
		if(sendCommand.getNextUserIDs()==null){
			//1:如果下一步人员为空，说明当前环节是最后一个环节，进行办结
			//2:获得当前待办所绑定的action并执行
			String flag = CommonService.SUCCESS;
			String result = ";";
			for(int i=0;i<services.length;i++){
				CommonService service = serviceContext.get(services[i]);
				//service执行结果
				result = service.execute(command,workflowInstanceID); 
				if(StringUtils.equals(result, CommonService.FAIL)){
					flag=CommonService.FAIL;
					break;
				}else if(!StringUtils.equals(result, CommonService.FAIL)&&!StringUtils.equals(result, CommonService.SUCCESS)){
					flag="exception";
					break;
				}
			}
			//flag为true，绑定action正常
			if(StringUtils.equals(flag, CommonService.SUCCESS)){	
				//3:因为有可能最有一步处理人是多个，所以要把所有的待办删除，task_list（任务表）状态更改过来
				TasklistWaitQO tasklistWaitQO = new TasklistWaitQO();
				tasklistWaitQO.setWorkflowInstanceID(workflowInstanceID);
				tasklistWaitQO.setStepNO(sendCommand.getCurrentStepNO());
				List<TasklistWait> tasklistWaits = tasklistWaitDAO.queryList(tasklistWaitQO);
				for(TasklistWait tasklistWait:tasklistWaits){
					Tasklist tasklist = tasklistDAO.get(tasklistWait.getId());
					tasklist.setNextUserID(STEP_99.toString());
					tasklist.setNextUserName(STEP_99.toString());
					tasklist.setSendTime(new Date());
					//修改tasklist状态
					tasklistDAO.update(tasklist);
					//删除待办
					tasklistWaitDAO.deleteById(tasklist.getId());
				}
				//修改流程实例状态
				WorkflowInstance workflowInstance = workflowInstanceDAO.get(workflowInstanceID);
				workflowInstance.setFinishTime(new Date());
				workflowInstance.setStatus(WorkflowInstanceStatus.FINISHED);
				workflowInstanceDAO.update(workflowInstance);
			}else if(StringUtils.equals(flag, CommonService.FAIL)){	
			}else{
				throw new WorkflowException(Integer.valueOf(result.split(";")[0]),result.split(";")[1]);
			}
		}else{
			//1:正常送出
			//2：获得当前待办所绑定的action并执行
			String flag = CommonService.SUCCESS;
			String result = ";";
			for(int i=0;i<services.length;i++){
				CommonService service = serviceContext.get(services[i]);
				//service执行结果
				result = service.execute(command,workflowInstanceID); 
				if(StringUtils.equals(result, CommonService.FAIL)){
					flag=CommonService.FAIL;
					break;
				}else if(!StringUtils.equals(result, CommonService.FAIL)&&!StringUtils.equals(result, CommonService.SUCCESS)){
					flag="exception";
					break;
				}
			}
			//flag为true，绑定action正常
			if(StringUtils.equals(flag, CommonService.SUCCESS)){	
				//3:修改当前执行人tasklist状态，删除当前待办
				TasklistWaitQO tasklistWaitQO = new TasklistWaitQO();
				tasklistWaitQO.setWorkflowInstanceID(workflowInstanceID);
				tasklistWaitQO.setStepNO(sendCommand.getCurrentStepNO());
				tasklistWaitQO.setCurrentUserID(sendCommand.getCurrentUserID());
				TasklistWait tasklistWait = tasklistWaitDAO.queryUnique(tasklistWaitQO);
				Tasklist tasklist = tasklistDAO.get(tasklistWait.getId());
				if(tasklist==null)
					throw new WorkflowException(WorkflowException.TASKLIST_NOT_EXIST_CODE,WorkflowException.TASKLIST_NOT_EXIST_MESSAGE);
				//4:执行送出操作，有可能是会签会有多个人员信息
				String nextUserIDs = "";
				String nextUserNames = "";
				//拼接人员信息
				for(String id:sendCommand.getNextUserIDs()){
					User nextUser = userDAO.get(id);
					nextUserIDs=nextUserIDs+nextUser.getId()+";";
					nextUserNames=nextUserNames+nextUser.getName()+";";
				}
				tasklist.setNextUserID(nextUserIDs);
				tasklist.setNextUserName(nextUserNames);
				tasklist.setSendTime(new Date());
				//修改tasklist状态
				tasklistDAO.update(tasklist);
				//删除待办
				tasklistWaitDAO.deleteById(tasklist.getId());
				//5：为下一个人创建待办
				tasklistWaitQO = new TasklistWaitQO();
				tasklistWaitQO.setWorkflowInstanceID(workflowInstanceID);
				tasklistWaitQO.setStepNO(sendCommand.getCurrentStepNO());
				//判断当前流程，当前环节有几条待办，如果为零条，说明当前环节是单签，或者会签中最后一条待办，为下一环节处理人创建待办，
				//多于零条说明删除当前执行人待办后还有人在当前环节有待办，证明当前环节是会签并且还有人没有处理所以不为下一人声称待办
				if(tasklistWaitDAO.queryCount(tasklistWaitQO)==0){
					for(String string:sendCommand.getNextUserIDs()){
						Tasklist nextTasklist = new Tasklist();
						String tasklistID =  UUIDGenerator.getUUID();
						nextTasklist.setId(tasklistID);
						nextTasklist.setWorkflowInstanceID(workflowInstanceID);
						workflowStepQO = new WorkflowStepQO();
						workflowStepQO.setWorkflowID(sendCommand.getWorkflowID());
						workflowStepQO.setStepNO(sendCommand.getNextStepNO());
						WorkflowStep nextWorkflow = workflowStepDAO.queryUnique(workflowStepQO);
						nextTasklist.setStepNO(nextWorkflow.getStepNO());
						nextTasklist.setStepName(nextWorkflow.getStepName());
						User user = userDAO.get(string);
						nextTasklist.setCurrentUserID(user.getId());
						nextTasklist.setCurrentUserName(user.getName());
						TasklistQO tasklistQO = new TasklistQO();
						tasklistQO.setWorkflowInstanceID(workflowInstanceID);
						tasklistQO.setStepNO(sendCommand.getCurrentStepNO());
						List<Tasklist> tasklists = tasklistDAO.queryList(tasklistQO);
						String previousUserIDs = "";
						String previousUserNames = "";
						for(Tasklist currentTasklist:tasklists){
							User previousUser= userDAO.get(currentTasklist.getCurrentUserID());
							boolean addflag = true;
							//排除相同人员
							String[] previousUserIDArray = previousUserIDs.split(";");
							for(int i=0;i<previousUserIDArray.length;i++){
								if(StringUtils.equals(previousUserIDArray[i], previousUser.getId()))
									addflag=false;
							}
							if(addflag){
								previousUserIDs+=previousUser.getId()+";";
								previousUserNames+=previousUser.getName()+";";
							}
						}
						nextTasklist.setPreviousUserID(previousUserIDs);
						nextTasklist.setPreviousUserName(previousUserNames);
						nextTasklist.setPreviousStepNO(sendCommand.getCurrentStepNO());
						nextTasklist.setPreviousStepName(tasklist.getStepName());
						WorkflowStepActionQO workflowStepActionQO = new WorkflowStepActionQO();
						workflowStepActionQO.setWorkflowStepID(nextWorkflow.getId());
						workflowStepActionQO.setStatus(WorkflowStepActionStatus.SEND);
						workflowStepActionQO.setViewActionValue("view");
						List<WorkflowStepAction> workflowStepActions = workflowStepActionDAO.queryList(workflowStepActionQO);
						if(workflowStepActions!=null&&workflowStepActions.size()!=0){
							nextTasklist.setAction("view/"+workflowStepActions.get(0).getStepAction().getActionValue());
						}
						nextTasklist.setReceiveTime(new Date());
						tasklistDAO.save(nextTasklist);
						tasklistWaitDAO.save(JSON.parseObject(JSON.toJSONString(nextTasklist), TasklistWait.class));
					}
				}
			}else if(StringUtils.equals(flag, CommonService.FAIL)){	
				//3:因为有可能最有一步处理人是多个，所以要把所有的待办删除，task_list（任务表）状态更改过来
				TasklistWaitQO tasklistWaitQO = new TasklistWaitQO();
				tasklistWaitQO.setWorkflowInstanceID(workflowInstanceID);
				tasklistWaitQO.setStepNO(sendCommand.getCurrentStepNO());
				List<TasklistWait> tasklistWaits = tasklistWaitDAO.queryList(tasklistWaitQO);
				for(TasklistWait tasklistWait:tasklistWaits){
					Tasklist tasklist = tasklistDAO.get(tasklistWait.getId());
					tasklist.setNextUserID(STEP_99.toString());
					tasklist.setNextUserName(STEP_99.toString());
					tasklist.setSendTime(new Date());
					//修改tasklist状态
					tasklistDAO.update(tasklist);
					//删除待办
					tasklistWaitDAO.deleteById(tasklist.getId());
				}
				//修改流程实例状态
				WorkflowInstance workflowInstance = workflowInstanceDAO.get(workflowInstanceID);
				workflowInstance.setFinishTime(new Date());
				workflowInstance.setStatus(WorkflowInstanceStatus.FINISHED);
				workflowInstanceDAO.update(workflowInstance);
				throw new WorkflowException(300,"提交失败");
			}else{
				throw new WorkflowException(Integer.valueOf(result.split(";")[0]),result.split(";")[1]);
			}
		}
	}
	/**
	 * 
	 * @方法功能说明：启动流程
	 * @修改者名字：cangs
	 * @修改时间：2015年7月2日下午3:12:15
	 * @修改内容：
	 * @参数：@param startWorkflowCommand
	 * @参数：@return
	 * @return:String
	 * @throws
	 */
	public String startWorkflow(StartWorkflowCommand startWorkflowCommand){
		//获取当前环节
		WorkflowStepQO workflowStepQO = new WorkflowStepQO();
		workflowStepQO.setWorkflowID(startWorkflowCommand.getWorkflowID());
		workflowStepQO.setStepNO(STEP_1);
		WorkflowStep workflowStep = workflowStepDAO.queryUnique(workflowStepQO);
		//获取起草人
		User user = userDAO.get(startWorkflowCommand.getCurrentUserID());
		//建立流程实例
		WorkflowInstance workflowInstance = new WorkflowInstance();
		String workflowInstanceID = UUIDGenerator.getUUID(); 
		workflowInstance.setId(workflowInstanceID);
		workflowInstance.setWorkflowID(startWorkflowCommand.getWorkflowID());
		workflowInstance.setWorkflowName(workflowStep.getWorkflowName());
		workflowInstance.setCreateTime(new Date());
		workflowInstance.setUserID(user.getId());
		workflowInstance.setUserName(user.getName());
		workflowInstance.setCompanyID(user.getCompanyID());
		workflowInstance.setCompanyName(user.getCompanyName());
		workflowInstance.setDepartmentID(user.getDepartmentID());
		workflowInstance.setDepartmentName(user.getDepartmentName());
		workflowInstance.setStatus(WorkflowInstanceStatus.LIVING);
		workflowInstanceDAO.save(workflowInstance);
		//创建tasklist
		Tasklist tasklist = new Tasklist();
		String tasklistID =  UUIDGenerator.getUUID();
		tasklist.setId(tasklistID);
		tasklist.setWorkflowInstanceID(workflowInstanceID);
		tasklist.setStepNO(workflowStep.getStepNO());
		tasklist.setStepName(workflowStep.getStepName());
		tasklist.setCurrentUserID(user.getId());
		tasklist.setCurrentUserName(user.getName());
		tasklist.setPreviousUserID(STEP_0);
		tasklist.setPreviousUserName(STEP_0);
		tasklist.setPreviousStepNO(Integer.valueOf(STEP_0));
		tasklist.setPreviousStepName(STEP_0);
		WorkflowStepActionQO workflowStepActionQO = new WorkflowStepActionQO();
		workflowStepActionQO.setWorkflowStepID(workflowStep.getId());
		workflowStepActionQO.setStatus(WorkflowStepActionStatus.SEND);
		workflowStepActionQO.setViewActionValue("view");
		List<WorkflowStepAction> workflowStepActions = workflowStepActionDAO.queryList(workflowStepActionQO);
		if(workflowStepActions!=null&&workflowStepActions.size()!=0){
			tasklist.setAction("view/"+workflowStepActions.get(0).getStepAction().getActionValue());
		}
		tasklist.setReceiveTime(new Date());
		tasklistDAO.save(tasklist);
		tasklistWaitDAO.save(JSON.parseObject(JSON.toJSONString(tasklist), TasklistWait.class));
		return workflowInstanceID;
	}

	/**
	 * 
	 * @方法功能说明：退回操作
	 * @修改者名字：cangs
	 * @修改时间：2015年7月3日下午6:09:44
	 * @修改内容：
	 * @参数：@param sendbackCommand
	 * @参数：@param command
	 * @参数：@throws WorkflowException
	 * @return:void
	 * @throws
	 */
	public void sendback(SendbackCommand sendbackCommand,BaseCommand command) throws WorkflowException {
		//1：找到当前流程、当前环节所有待办，删除掉
		TasklistWaitQO tasklistWaitQO = new TasklistWaitQO();
		tasklistWaitQO.setStepNO(sendbackCommand.getCurrentStepNO());
		tasklistWaitQO.setWorkflowInstanceID(sendbackCommand.getWorkflowInstanceID());
		List<TasklistWait> tasklistWaits = tasklistWaitDAO.queryList(tasklistWaitQO);
		for(TasklistWait tasklistWait:tasklistWaits){
			tasklistWaitDAO.deleteById(tasklistWait.getId());
		}
		//2：找到当前流程、当前环节所有tasklist，修改其状态
		TasklistQO tasklistQO = new TasklistQO();
		tasklistQO.setStepNO(sendbackCommand.getCurrentStepNO());
		tasklistQO.setWorkflowInstanceID(sendbackCommand.getWorkflowInstanceID());
		List<Tasklist> tasklists =tasklistDAO.queryList(tasklistQO);
		//创建下一环节待办开关
		boolean flag = true;
		String previousUserIDs = "";
		for(Tasklist tasklist:tasklists){
			User previousUser= userDAO.get(tasklist.getCurrentUserID());
			boolean addflag = true;
			//排除相同人员
			String[] previousUserIDArray = previousUserIDs.split(";");
			for(int i=0;i<previousUserIDArray.length;i++){
				if(StringUtils.equals(previousUserIDArray[i], previousUser.getId()))
					addflag=false;
			}
			if(addflag){
				previousUserIDs+=previousUser.getId()+";";
				tasklist.setSendTime(new Date());
				//将下一环节处理人设置为送给我的人，无论上一个环节是多个人还是一个人
				tasklist.setNextUserID(tasklist.getPreviousUserID());
				tasklist.setNextUserName(tasklist.getPreviousUserName());
				if(flag){
					//3：为下一环节处理人创建tasklist和待办，无论是下个环节是多个人还是一个人，只创建一次待办
					flag=false;
					String[] previousUserID = tasklist.getPreviousUserID().split(";");
					String[] previousUserName = tasklist.getPreviousUserName().split(";");
					for(int i=0;i<previousUserID.length;i++){
						Tasklist nextTasklist = new Tasklist();
						nextTasklist.setId(UUIDGenerator.getUUID());
						nextTasklist.setWorkflowInstanceID(sendbackCommand.getWorkflowInstanceID());
						nextTasklist.setStepNO(tasklist.getPreviousStepNO());
						nextTasklist.setStepName(tasklist.getPreviousStepName());
						nextTasklist.setCurrentUserID(previousUserID[i]);
						nextTasklist.setCurrentUserName(previousUserName[i]);
						tasklistQO = new TasklistQO();
						tasklistQO.setWorkflowInstanceID(sendbackCommand.getWorkflowInstanceID());
						tasklistQO.setCurrentUserID(previousUserID[i]);
						tasklistQO.setStepNO(tasklist.getPreviousStepNO());
						tasklistQO.setOrderByReceiveTimeDesc(true);
						Tasklist oldtasklist = tasklistDAO.queryUnique(tasklistQO);
						nextTasklist.setPreviousUserID(oldtasklist.getPreviousUserID());
						nextTasklist.setPreviousUserName(oldtasklist.getPreviousUserName());
						nextTasklist.setPreviousStepNO(oldtasklist.getPreviousStepNO());
						nextTasklist.setPreviousStepName(oldtasklist.getPreviousStepName());
						WorkflowStepQO workflowStepQO = new WorkflowStepQO();
						workflowStepQO.setStepNO(tasklist.getPreviousStepNO());
						workflowStepQO.setWorkflowID(sendbackCommand.getWorkflowID());
						WorkflowStep workflowStep = workflowStepDAO.queryUnique(workflowStepQO);
						WorkflowStepActionQO workflowStepActionQO = new WorkflowStepActionQO();
						workflowStepActionQO.setWorkflowStepID(workflowStep.getId());
						workflowStepActionQO.setStatus(WorkflowStepActionStatus.SEND);
						workflowStepActionQO.setViewActionValue("view");
						List<WorkflowStepAction> workflowStepActions = workflowStepActionDAO.queryList(workflowStepActionQO);
						if(workflowStepActions!=null&&workflowStepActions.size()!=0){
							nextTasklist.setAction("view/"+workflowStepActions.get(0).getStepAction().getActionValue());
						}
						nextTasklist.setReceiveTime(new Date());
						tasklistDAO.save(nextTasklist);
						tasklistWaitDAO.save(JSON.parseObject(JSON.toJSONString(nextTasklist), TasklistWait.class));
					}
				}
				tasklistDAO.update(tasklist);
			}
		}
	}
	
	/**
	 * @Title: delStep 
	 * @author guok
	 * @Description: 删除环节
	 * @Time 2015年7月30日下午1:50:25
	 * @param command void 设定文件
	 * @throws
	 */
	public void delStep(String workflowStepID){
		HgLogger.getInstance().info("cs", "【WorkflowStepService】"+"delStep:"+JSON.toJSONString(workflowStepID));
		WorkflowStep workflowStep = workflowStepDAO.get(workflowStepID);
		WorkflowStepActionQO workflowStepActionQO = new WorkflowStepActionQO();
		workflowStepActionQO.setWorkflowStepID(workflowStep.getId());
		List<WorkflowStepAction> workflowStepActions = workflowStepActionDAO.queryList(workflowStepActionQO); 
		for (WorkflowStepAction workflowStepAction : workflowStepActions) {
			workflowStepActionDAO.delete(workflowStepAction);
		}
		workflowStepDAO.delete(workflowStep);
		WorkflowStepQO workflowStepQO = new WorkflowStepQO();
		workflowStepQO.setWorkflowID(workflowStep.getWorkflowID());
		workflowStepQO.setOrderByStepNOasc("orderByStepNOasc");
		List<WorkflowStep> workflowStepsloop1 = workflowStepDAO.queryList(workflowStepQO);
		List<WorkflowStep> workflowStepsloop2 = workflowStepDAO.queryList(workflowStepQO);
		for (WorkflowStep loop1 : workflowStepsloop1) {
			String previousStepNOs = "";
			String nextStepNOs = "";
			String[] loop1nextSteps = loop1.getNextStepNO().split(";");
			boolean endFlag = false;
			for (WorkflowStep loop2 : workflowStepsloop2) {
				if(StringUtils.isNotBlank(loop2.getNextStepNO())){
					String[] loop2nextSteps = loop2.getNextStepNO().split(";");
					for (int i = 0; i < loop2nextSteps.length; i++) {
						//将loop1的上一环节更新
						if(StringUtils.equals(loop2nextSteps[i], loop1.getStepNO().toString())){
							previousStepNOs+=loop2.getStepNO()+";";
						}
					}
					for(int i = 0; i < loop1nextSteps.length; i++) {
						//将loop1的下一环节更新
						if(StringUtils.equals(loop1nextSteps[i], loop2.getStepNO().toString())){
							nextStepNOs+=loop2.getStepNO()+";";
						}
						if(StringUtils.equals(loop1nextSteps[i], "99")){
							endFlag=true;
						}
					}
				}
			}
			if(endFlag||StringUtils.isBlank(nextStepNOs)){
				nextStepNOs+="99;";
			}
			if(StringUtils.isBlank(previousStepNOs)){
				previousStepNOs+="0";
			}
			loop1.setNextStepNO(nextStepNOs);
			loop1.setPreviousStepNO(previousStepNOs);
			HgLogger.getInstance().info("cs", "【WorkflowStepService】"+"delStep:loop1"+JSON.toJSONString(loop1));
			workflowStepDAO.update(loop1);
		}
	}
	
	/**
	 * @Title: update 
	 * @author guok
	 * @Description: 修改环节
	 * @Time 2015年7月30日下午3:04:44
	 * @param command void 设定文件
	 * @throws
	 */
	public void update(ModifyWorkflowStepCommand command) {
		HgLogger.getInstance().info("cs", "【WorkflowStepService】"+"update:command"+JSON.toJSONString(command));
		WorkflowStep workflowStep = workflowStepDAO.get(command.getWorkflowStepID());
		WorkflowStep newWorkflowStep = BeanMapperUtils.getMapper().map(workflowStep, WorkflowStep.class);
		newWorkflowStep.setId(UUIDGenerator.getUUID());
		//环节名称
		newWorkflowStep.setStepName(command.getStepName());
		switch (command.getChooseExecutorType()) {
		case 2:
			//执行人类型
			newWorkflowStep.setChooseExecutorType(command.getChooseExecutorType());
			//执行人
			newWorkflowStep.setExecutor(command.getExecutor());
			break;
		case 4:
			//执行人类型
			newWorkflowStep.setChooseExecutorType(command.getChooseExecutorType());
			//执行人
			ExecutorDTO executorDTO = new ExecutorDTO();
			List<Integer> userFlag = new ArrayList<Integer>();
			userFlag.add(Integer.parseInt(command.getExecutor()));
			executorDTO.setUserFlag(userFlag);
			newWorkflowStep.setExecutor(JSON.toJSONString(executorDTO));
			break;
		}
		int maxcount = workflowStepActionDAO.maxProperty("sort", new WorkflowStepActionQO());
		//更新下一环节
		if(StringUtils.isNotBlank(command.getNextStepNO())){
			String nextStepActions= command.getNextStepNO().replace(",", "");
			newWorkflowStep.setNextStepNO(nextStepActions);
		}else{
			newWorkflowStep.setNextStepNO("99;");
		}
		//新增关联
		List<WorkflowStepAction> newworkflowStepActions = new ArrayList<WorkflowStepAction>();
		if(StringUtils.isNotBlank(command.getActionID())){
			String[] actionIDs = command.getActionID().split(",");
			for (int i = 0; i < actionIDs.length; i++) {
				WorkflowStepAction workflowStepAction = new WorkflowStepAction();
				workflowStepAction.setId(UUIDGenerator.getUUID());
				StepAction stepAction = stepActionDAO.get(actionIDs[i]); 
				workflowStepAction.setStepAction(stepAction);
				workflowStepAction.setOrderType(stepAction.getOrderType());
				workflowStepAction.setWorkflowStep(newWorkflowStep);
				workflowStepAction.setStatus(1);
				workflowStepAction.setSort(maxcount+1);
				newworkflowStepActions.add(workflowStepAction);
				maxcount++;
			}
		}
		HgLogger.getInstance().info("cs", "【WorkflowStepService】"+"update:newworkflowStepActions"+JSON.toJSONString(newWorkflowStep));
		newWorkflowStep.setWorkflowStepActions(newworkflowStepActions);
		workflowStepDAO.save(newWorkflowStep);
		
		
		
		//删除原有 step和action关联
		/*WorkflowStepActionQO workflowStepActionQO =new WorkflowStepActionQO();
		workflowStepActionQO.setWorkflowStepID(workflowStep.getId());
		List<WorkflowStepAction> workflowStepActions = workflowStepActionDAO.queryList(workflowStepActionQO);
		for (WorkflowStepAction workflowStepAction : workflowStepActions) {
			workflowStepActionDAO.delete(workflowStepAction);
		}*/
		workflowStepDAO.delete(workflowStep);
		
		
		
		
		WorkflowStepQO workflowStepQO = new WorkflowStepQO();
		workflowStepQO.setWorkflowID(workflowStep.getWorkflowID());
		workflowStepQO.setOrderByStepNOasc("orderByStepNOasc");
		List<WorkflowStep> workflowStepsloop1 = workflowStepDAO.queryList(workflowStepQO);
		List<WorkflowStep> workflowStepsloop2 = workflowStepDAO.queryList(workflowStepQO);
		for (WorkflowStep loop1 : workflowStepsloop1) {
			String previousStepNOs = "";
			if(loop1.getStepNO()!=1){
				for (WorkflowStep loop2 : workflowStepsloop2) {
					if(StringUtils.isNotBlank(loop2.getNextStepNO())){
						String[] nextSteps = loop2.getNextStepNO().split(";");
						for (int i = 0; i < nextSteps.length; i++) {
							if(StringUtils.equals(nextSteps[i], loop1.getStepNO().toString())){
								previousStepNOs+=loop2.getStepNO()+";";
							}
						}
					}
				}
				if(StringUtils.isBlank(previousStepNOs))
					previousStepNOs="0";
				loop1.setPreviousStepNO(previousStepNOs);
				HgLogger.getInstance().info("cs", "【WorkflowStepService】"+"update:loop1"+JSON.toJSONString(loop1));
				workflowStepDAO.update(loop1);
			}
		}
	}
	
	/**
	 * 发送短信
	 * @Title: smsUser 
	 * @author guok
	 * @Description: 发送短信提醒
	 * @Time 2015年8月29日上午9:36:53
	 * @param nextUserID void 设定文件
	 * @throws
	 */
	public void smsUser(String nextUserID) {
		User user = userDAO.get(nextUserID);
		if (StringUtils.isNotBlank(user.getLinkMobile())) {
			final String text = "【"+SysProperties.getInstance().get("smsSign")+"】"+"您的智行平台有一条新的待办。";
			final String linkMobile = user.getLinkMobile();
			HgLogger.getInstance().info("cs", "【WorkflowStepService】"+"发送短信内容，电话"+user.getLinkMobile());
			HgLogger.getInstance().info("cs", "【WorkflowStepService】"+"发送短信内容，内容"+text);
			new Thread(){
				public void run(){
					try {
						smsUtils.sendSms(linkMobile, text);
					} catch (Exception e) {
						HgLogger.getInstance().info("cs", "【WorkflowStepService】"+"发送短信异常，"+HgLogger.getStackTrace(e));
					}
				}
			}.start();
		}
	}
	@Override
	protected WorkflowStepDAO getDao() {
		return workflowStepDAO;
	}

}
