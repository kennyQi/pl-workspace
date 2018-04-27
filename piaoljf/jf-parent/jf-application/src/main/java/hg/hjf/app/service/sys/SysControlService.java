package hg.hjf.app.service.sys;

import hg.hjf.app.dao.operationlog.OperationLogDao;
import hg.system.dao.KVConfigDao;
import hg.system.qo.KVConfigQo;
import hg.system.service.KVConfigService;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.hibernate.criterion.SimpleExpression;
import org.hibernate.internal.CriteriaImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Transactional
@Service
public class SysControlService {
	@Autowired
	OperationLogDao operationLogDao;

	public void checkDayEnd() {
		operationLogDao.checkDayEnd();
	}

	public boolean isDayEnd() {
		return operationLogDao.isDayEnd();
	}

	public void setDayEnd(boolean dayEnd) {
		operationLogDao.setDayEnd(dayEnd);
	}
	
	
	
	

}
