package hsl.app.dao.yxjp;

import hg.common.component.BaseDao;
import hsl.domain.model.yxjp.YXJPOrderOperateLog;
import hsl.pojo.qo.yxjp.YXJPOrderOperateLogQO;
import org.hibernate.Criteria;
import org.springframework.stereotype.Repository;

/**
 * 操作日志DAO
 *
 * @author zhurz
 * @since 1.7
 */
@Repository("yxjpOrderOperateLogDAO")
public class YXJPOrderOperateLogDAO extends BaseDao<YXJPOrderOperateLog, YXJPOrderOperateLogQO> {

	@Override
	protected Criteria buildCriteria(Criteria criteria, YXJPOrderOperateLogQO qo) {
		return criteria;
	}

	@Override
	protected Class<YXJPOrderOperateLog> getEntityClass() {
		return YXJPOrderOperateLog.class;
	}
}
