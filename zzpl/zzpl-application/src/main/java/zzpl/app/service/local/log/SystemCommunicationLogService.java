package zzpl.app.service.local.log;

import hg.common.component.BaseCommand;
import hg.common.component.BaseServiceImpl;
import hg.common.util.UUIDGenerator;
import hg.log.util.HgLogger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSON;

import zzpl.app.dao.log.SystemCommunicationLogDAO;
import zzpl.domain.model.log.SystemCommunicationLog;
import zzpl.pojo.qo.log.SystemCommunicationLogQO;

@Service
@Transactional
public class SystemCommunicationLogService extends BaseServiceImpl<SystemCommunicationLog, SystemCommunicationLogQO, SystemCommunicationLogDAO>{

	@Autowired
	private SystemCommunicationLogDAO systemCommunicationLogDAO;
	
	public void saveLocalLog(BaseCommand command){
		SystemCommunicationLog systemCommunicationLog = new SystemCommunicationLog(command, UUIDGenerator.getUUID());
		HgLogger.getInstance().info("ZZPL_DEV", "【智行本地日志】"+JSON.toJSONString(systemCommunicationLog));
		systemCommunicationLogDAO.save(systemCommunicationLog);
	}
	
	@Override
	protected SystemCommunicationLogDAO getDao() {
		return systemCommunicationLogDAO;
	}

}
