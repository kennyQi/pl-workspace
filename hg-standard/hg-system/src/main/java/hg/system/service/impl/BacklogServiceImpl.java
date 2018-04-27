package hg.system.service.impl;

import hg.common.component.BaseServiceImpl;
import hg.system.command.backlog.CreateBacklogCommand;
import hg.system.command.backlog.CreateBacklogLogCommand;
import hg.system.command.backlog.ExecuteBacklogCommand;
import hg.system.dao.BacklogDao;
import hg.system.dao.BacklogLogDao;
import hg.system.exception.HGException;
import hg.system.model.backlog.Backlog;
import hg.system.model.backlog.BacklogLog;
import hg.system.qo.BacklogQo;
import hg.system.service.BacklogService;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class BacklogServiceImpl extends BaseServiceImpl<Backlog, BacklogQo, BacklogDao> implements BacklogService{

	@Autowired
	private BacklogDao dao;
	@Autowired
	private BacklogLogDao logDao;
	
	@Override
	protected BacklogDao getDao() {
		// TODO Auto-generated method stub
		return dao;
	}

	@Override
	public void createBacklog(CreateBacklogCommand command) throws HGException {
		
		if(StringUtils.isBlank(command.getType())){
			throw new HGException(HGException.BACKLOG_WITHOUT_NEED_PARAM,"请提供待办事项类型");
		}
		
		if(command.getExecuteNum() == null){
			throw new HGException(HGException.BACKLOG_WITHOUT_NEED_PARAM,"请提供待办事项限制执行次数");
		}
		
		Backlog backlog = new Backlog();
		backlog.create(command);
		
		dao.save(backlog);
		
	}

	@Override
	public void executeBacklog(ExecuteBacklogCommand command)
			throws HGException {
		
		if(StringUtils.isBlank(command.getBacklogId())){
			throw new HGException(HGException.BACKLOG_WITHOUT_NEED_PARAM,"请提供待办事项编号");
		}
		Backlog backlog = get(command.getBacklogId());
		if(backlog == null){
			throw new HGException(HGException.BACKLOG_NOT_FOUND,"待办事项不存在");
		}
		
		if(command.getSuccess() == null){
			throw new HGException(HGException.BACKLOG_WITHOUT_NEED_PARAM,"请提供待办事项是否成功的标识");
		}
		if(backlog.getBacklogStatus().getExecuteCount() >= backlog.getBacklogInfo().getExecuteNum()){
			throw new HGException(HGException.BACKLOG_EXECUTE_COUNT_NOTVALID, backlog.getBacklogInfo().getName() + "已超过规定执行次数，不再通知");
		}
		
		backlog.execute(command);
		dao.update(backlog);
		
		//记录待办事项日志流水
		CreateBacklogLogCommand logCommand = new CreateBacklogLogCommand();
		logCommand.setBacklogId(backlog.getId());
		logCommand.setOperateNum(backlog.getBacklogStatus().getExecuteCount());
		if(backlog.getBacklogStatus().getSuccess()){
			logCommand.setSuccess(true);
			logCommand.setOperateContent(backlog.getBacklogInfo().getName() + "第" + backlog.getBacklogStatus().getExecuteCount()  + "次成功");
		}else{
			logCommand.setSuccess(false);
			logCommand.setOperateContent(backlog.getBacklogInfo().getName() + "第" + backlog.getBacklogStatus().getExecuteCount()  + "次失败");
		}
		
		BacklogLog log = new BacklogLog();
		log.create(logCommand);
		logDao.save(log);
		
	}

	

}
