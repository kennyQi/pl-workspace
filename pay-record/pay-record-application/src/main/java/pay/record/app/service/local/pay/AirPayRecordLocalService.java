package pay.record.app.service.local.pay;

import hg.common.component.BaseServiceImpl;
import hg.log.util.HgLogger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import pay.record.app.dao.pay.AirPayRecordDAO;
import pay.record.domain.model.pay.AirPayRecord;
import pay.record.pojo.command.CreateAirPayReocrdSpiCommand;
import pay.record.pojo.command.ModifyAirPayReocrdSpiCommand;
import pay.record.pojo.qo.pay.AirPayRecordQO;
@Service
@Transactional
public class AirPayRecordLocalService extends BaseServiceImpl<AirPayRecord, AirPayRecordQO, AirPayRecordDAO>{

	@Autowired
	AirPayRecordDAO payRecordDAO;
	
	@Override
	protected AirPayRecordDAO getDao() {
		return payRecordDAO;
	}

}
