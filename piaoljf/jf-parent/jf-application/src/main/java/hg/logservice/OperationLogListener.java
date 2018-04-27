package hg.logservice;

import hg.hjf.domain.model.log.OperationLog;

public interface OperationLogListener {
	public void beforeSaveLog(OperationLog entity);
	public void afterSaveLog(OperationLog entity);
	
}
