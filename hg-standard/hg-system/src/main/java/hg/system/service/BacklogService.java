package hg.system.service;

import hg.common.component.BaseService;
import hg.system.command.backlog.CreateBacklogCommand;
import hg.system.command.backlog.ExecuteBacklogCommand;
import hg.system.exception.HGException;
import hg.system.model.backlog.Backlog;
import hg.system.qo.BacklogQo;


public interface BacklogService extends BaseService<Backlog, BacklogQo>{

	/**
	 * 添加待办事项
	 * @param command
	 * @throws HGException
	 */
	public void createBacklog(CreateBacklogCommand command) throws HGException;
	
	/**
	 * 执行待办事项
	 * @param command
	 * @throws HGException
	 */
	public void executeBacklog(ExecuteBacklogCommand command) throws HGException;
	
	
}
