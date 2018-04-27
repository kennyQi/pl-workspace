package hg.logservice;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.caucho.hessian.io.ThrowableSerializer;

import hg.common.component.BaseServiceImpl;
import hg.common.page.Pagination;
import hg.common.util.UUIDGenerator;
import hg.hjf.app.dao.operationlog.OperationLogDao;
import hg.hjf.domain.model.log.OperationLog;
import hg.hjf.domain.model.log.OperationLogQo;
import hgtech.jf.JfProperty;

@Transactional(propagation=Propagation.REQUIRES_NEW)
@Service
public class OperationLogService extends BaseServiceImpl<OperationLog,OperationLogQo, OperationLogDao>{

	@Autowired
	OperationLogDao operationLogDao;
	@Autowired
	OperationLogListener logListener;
	
	@Override
	protected OperationLogDao getDao() {
		return operationLogDao;
	}

	@Override
	/**
	 * 为null则不记录
	 */
	public OperationLog save(OperationLog entity) {
		if(entity==null)
			return null;
		try {
			OperationLog save = super.save(entity);
			getDao().flush();
			String status = JfProperty.getJfRuleStatus();
			if (status.equals("1") && entity.isOperOk()) {
				if(logListener != null && entity.getLoginName() != null)
					logListener.beforeSaveLog(entity);
			}
			return save;
		} catch (Throwable e) {
			e.printStackTrace();
			return entity;
		}
	}
	
	public void login(String loginname,String ip,String entry,boolean ok,String reason){
		String operType = "login";
		log(loginname, ok, reason, operType,ip,entry);
	}
	public void logout(String loginname,String ip,String entry,boolean ok,String reason){
		String operType = "logout";
		log(loginname, ok, reason, operType,ip,entry);
	}

	public void log(String loginname, boolean ok, String reason, String operType, String ip, String entry) {
		OperationLog log = new OperationLog();
		log.setFailReason(reason);
		log.setId(UUIDGenerator.getUUID());
		log.setLoginName(loginname);
		log.setOperOk(ok);
		log.setOperTime(new Date());
		log.setOperType(operType);
		log.setIp(ip);
		log.setEntry(entry);
		save(log);
	}

	/**
	 * @方法功能说明：
	 * @修改者名字：<a href=gaoce@hgtech365.com>gaoce</a>
	 * @修改时间：2016-1-8上午10:17:14
	 * @version：
	 * @修改内容：
	 * @参数：@param paging
	 * @参数：@return
	 * @return:Pagination
	 * @throws
	 */
	public Pagination findPagination(Pagination paging) {
		// TODO Auto-generated method stub
		return operationLogDao.findPagination(paging);
	}
}
