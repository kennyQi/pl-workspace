package pay.record.app.service.local.pay;

import hg.common.component.BaseServiceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import pay.record.app.common.util.EntityConvertUtils;
import pay.record.app.dao.pay.LinePayRecordDAO;
import pay.record.domain.model.pay.LinePayRecord;
import pay.record.pojo.dto.LinePayRecordDTO;
import pay.record.pojo.qo.pay.LinePayRecordQO;
@Service
@Transactional
public class LinePayRecordLocalService extends BaseServiceImpl<LinePayRecord, LinePayRecordQO, LinePayRecordDAO>{

	@Autowired
	LinePayRecordDAO lineRecordDAO;
	
	@Override
	protected LinePayRecordDAO getDao() {
		return lineRecordDAO;
	}

}
