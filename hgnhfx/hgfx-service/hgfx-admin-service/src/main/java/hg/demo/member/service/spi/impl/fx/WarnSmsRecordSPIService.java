package hg.demo.member.service.spi.impl.fx;

import java.util.List;

import hg.demo.member.service.dao.hibernate.fx.SmsCodeRecordDAO;
import hg.demo.member.service.dao.hibernate.fx.WarnSmsRecordDAO;
import hg.demo.member.service.domain.manager.fx.SmsCodeRecordManager;
import hg.demo.member.service.qo.hibernate.fx.SmsCodeRecordQO;
import hg.framework.service.component.base.BaseService;
import hg.fx.command.smsCodeRecord.CreateSmsCodeRecordCommand;
import hg.fx.command.smsCodeRecord.ModifySmsCodeRecordCommand;
import hg.fx.command.warnSmsCodeRecord.CreateWarnSmsRecord;
import hg.fx.domain.SmsCodeRecord;
import hg.fx.domain.WarnSmsRecord;
import hg.fx.spi.SmsCodeRecordSPI;
import hg.fx.spi.WarnSmsRecordSPI;
import hg.fx.spi.qo.SmsCodeRecordSQO;

import javax.transaction.Transactional;

import org.hibernate.engine.jdbc.spi.SqlExceptionHelper.WarningHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 * @类功能说明：doto
 * @类修改者：
 * @修改日期：
 * @修改说明：
 * @公司名称： 浙江汇购科技有限公司
 * @部门： 技术部
 * @作者： xinglj
 * @创建时间： 2016年6月1日 下午7:29:25
 * @版本： V1.0
 */
@Transactional
@Service("warnSmsRecordSPIService")
public class WarnSmsRecordSPIService extends BaseService implements  WarnSmsRecordSPI{
	@Autowired
	WarnSmsRecordDAO dao;

	@Override
	public WarnSmsRecord create(CreateWarnSmsRecord command) {
		WarnSmsRecord w=new WarnSmsRecord();
		w.setContent(command.getContent());
		w.setDistributor(command.getDistributor());
		w.setId(command.getId());
		w.setMobile(command.getMobile());
		w.setSendTime(command.getSendTime());
		return dao.save(w);
	}
	 
}
