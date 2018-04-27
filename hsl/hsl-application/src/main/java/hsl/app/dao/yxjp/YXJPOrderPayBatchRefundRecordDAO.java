package hsl.app.dao.yxjp;

import hg.common.component.BaseDao;
import hsl.domain.model.yxjp.YXJPOrderPayBatchRefundRecord;
import hsl.pojo.qo.yxjp.YXJPOrderPayBatchRefundRecordQO;
import org.hibernate.Criteria;
import org.springframework.stereotype.Repository;

/**
 * 批量退款DAO
 *
 * @author zhurz
 * @since 1.7
 */
@Repository("yxjpOrderPayBatchRefundRecordDAO")
public class YXJPOrderPayBatchRefundRecordDAO extends BaseDao<YXJPOrderPayBatchRefundRecord, YXJPOrderPayBatchRefundRecordQO> {

	@Override
	protected Criteria buildCriteria(Criteria criteria, YXJPOrderPayBatchRefundRecordQO qo) {
		return criteria;
	}

	@Override
	protected Class<YXJPOrderPayBatchRefundRecord> getEntityClass() {
		return YXJPOrderPayBatchRefundRecord.class;
	}
}
