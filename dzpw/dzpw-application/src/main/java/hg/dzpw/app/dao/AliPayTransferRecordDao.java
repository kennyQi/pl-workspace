package hg.dzpw.app.dao;

import hg.common.component.BaseDao;
import hg.dzpw.app.pojo.qo.AliPayTransferRecordQo;
import hg.dzpw.domain.model.pay.AliPayTransferRecord;

import org.hibernate.Criteria;
import org.springframework.stereotype.Repository;

@Repository
public class AliPayTransferRecordDao extends BaseDao<AliPayTransferRecord, AliPayTransferRecordQo>{

	@Override
	protected Criteria buildCriteria(Criteria criteria, AliPayTransferRecordQo qo) {
		
		if(null == qo)
			return criteria;
		
		
		return criteria;
	}

	@Override
	protected Class<AliPayTransferRecord> getEntityClass() {
		return AliPayTransferRecord.class;
	}

}
