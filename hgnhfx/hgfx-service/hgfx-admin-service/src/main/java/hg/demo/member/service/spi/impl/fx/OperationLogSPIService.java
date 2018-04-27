package hg.demo.member.service.spi.impl.fx;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import hg.demo.member.service.dao.hibernate.fx.OperationLogDAO;
import hg.demo.member.service.domain.manager.fx.OperationLogManager;
import hg.demo.member.service.qo.hibernate.fx.OperationLogQO;
import hg.framework.common.model.Pagination;
import hg.framework.service.component.base.BaseService;
import hg.fx.command.operationLog.CreateOperationLogCommand;
import hg.fx.domain.OperationLog;
import hg.fx.spi.OperationLogSPI;
import hg.fx.spi.qo.OperationLogSQO;


/**
 * 运营端操作日志SPI
 * @author yangkang
 * @date 2016-06-01
 * */
@Transactional
@Service("operationLogSPIService")
public class OperationLogSPIService extends BaseService implements OperationLogSPI{

	@Autowired
	private OperationLogDAO dao;
	
	
	@Override
	public OperationLog createOperationLog(CreateOperationLogCommand command) {
		OperationLog log = new OperationLog();
		log = new OperationLogManager(log).create(command).get();
		dao.save(log);
		return log;
	}


	@Override
	public Pagination<OperationLog> queryPagination(OperationLogSQO sqo) {
		return dao.queryPagination(OperationLogQO.bulid(sqo));
	}
	

}
