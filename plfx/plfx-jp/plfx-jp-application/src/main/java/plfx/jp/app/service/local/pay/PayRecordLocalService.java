package plfx.jp.app.service.local.pay;

import hg.common.component.BaseServiceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import plfx.jp.app.dao.pay.PayRecordDAO;
import plfx.jp.domain.model.pay.PayRecord;
import plfx.jp.qo.pay.PayRecordQO;
@Service
@Transactional
public class PayRecordLocalService extends BaseServiceImpl<PayRecord, PayRecordQO, PayRecordDAO>{

	@Autowired
	PayRecordDAO payRecordDAO;
	
	@Override
	protected PayRecordDAO getDao() {
		return payRecordDAO;
	}

}
