package hsl.app.dao;
import org.hibernate.Criteria;
import org.springframework.stereotype.Repository;
import hg.common.component.BaseDao;
import hsl.domain.model.event.MailValidateRecord;
import hsl.pojo.qo.user.HslMailCodeQO;

@Repository
public class MailCodeDao extends BaseDao<MailValidateRecord, HslMailCodeQO>{

	@Override
	protected Criteria buildCriteria(Criteria criteria, HslMailCodeQO qo) {
		return criteria;
	}

	@Override
	protected Class<MailValidateRecord> getEntityClass() {

		return MailValidateRecord.class;
	}

}
