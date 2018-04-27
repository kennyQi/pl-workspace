package hg.demo.member.service.spi.impl;

import java.sql.Timestamp;
import java.util.Date;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import hg.demo.member.common.domain.model.OpLog;
import hg.demo.member.common.domain.result.Result;
import hg.demo.member.common.spi.OpLogSPI;
import hg.demo.member.common.spi.command.userinfo.CreateOpLogCommand;
import hg.demo.member.service.dao.hibernate.OpLogDAO;
import hg.framework.common.util.UUIDGenerator;
import hg.framework.service.component.base.BaseService;

/**
 * 
* <p>Title: UserBaseInfoSPIService</p>
* <p>Description: </p>
* <p>Company: </p> 
* @author xuwangwei
* @date 2016年6月27日 下午2:12:17
 */
@Transactional
@Service("opLogSPIService")
public class OpLogSPIService extends BaseService implements OpLogSPI {

	@Autowired
	private OpLogDAO opLogDAO;

	@Override
	public Result<OpLog> createOpLog(CreateOpLogCommand createOpLogCommand) {
		Result<OpLog> result = new Result<>();
		OpLog opLog = new OpLog();
		try {
			org.springframework.beans.BeanUtils.copyProperties(createOpLogCommand, opLog);
			opLog.setId(UUIDGenerator.getUUID());
			opLog.setCreateTime(new Timestamp(new Date().getTime()));
			opLog.setUpdateTime(new Timestamp(new Date().getTime()));
			opLog = opLogDAO.save(opLog);
			result.setResult(opLog);
		} catch (Exception e) {
			e.printStackTrace();
			result.setCode(-1);
			result.setMsg("未知错误");
		}
		
		return result;
	}
	
}
