package jxc.app.service.system;

import hg.common.component.BaseServiceImpl;
import hg.pojo.command.CreateCheckRecordCommand;
import hg.pojo.exception.SettingException;
import hg.pojo.qo.CheckRecordQo;
import jxc.app.dao.system.CheckRecordDao;
import jxc.app.util.JxcLogger;
import jxc.domain.model.system.CheckRecord;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CheckRecordService extends BaseServiceImpl<CheckRecord, CheckRecordQo, CheckRecordDao> {
	@Autowired
	private CheckRecordDao checkRecordDao;

	@Override
	protected CheckRecordDao getDao() {
		return checkRecordDao;
	}

	public void createCheckRecord(CreateCheckRecordCommand command) {

		CheckRecord checkRecord = new CheckRecord();
		checkRecord.createCheckRecord(command);
		save(checkRecord);
	}

}
