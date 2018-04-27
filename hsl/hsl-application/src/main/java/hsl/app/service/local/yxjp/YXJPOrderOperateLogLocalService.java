package hsl.app.service.local.yxjp;

import hg.common.component.BaseServiceImpl;
import hsl.app.dao.yxjp.YXJPOrderDAO;
import hsl.app.dao.yxjp.YXJPOrderOperateLogDAO;
import hsl.domain.model.yxjp.YXJPOrder;
import hsl.domain.model.yxjp.YXJPOrderOperateLog;
import hsl.pojo.qo.yxjp.YXJPOrderOperateLogQO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * 易行机票操作日志服务
 *
 * @author zhurz
 * @since 1.7
 */
@Service
@Transactional
public class YXJPOrderOperateLogLocalService extends BaseServiceImpl<YXJPOrderOperateLog, YXJPOrderOperateLogQO, YXJPOrderOperateLogDAO> {

	@Autowired
	private YXJPOrderOperateLogDAO dao;
	@Autowired
	private YXJPOrderDAO orderDAO;

	@Override
	protected YXJPOrderOperateLogDAO getDao() {
		return dao;
	}

	/**
	 * 记录操作日志
	 *
	 * @param orderId   易行机票订单ID
	 * @param operator  操作人
	 * @param content   操作内容
	 * @param debugInfo 调试信息
	 * @return
	 */
	public YXJPOrderOperateLog record(String orderId, String operator, String content, String debugInfo) {
		YXJPOrder order = orderDAO.load(orderId);
		YXJPOrderOperateLog log = new YXJPOrderOperateLog(order, operator, content, debugInfo);
		getDao().save(log);
		return log;
	}

	/**
	 * 记录操作日志在新开事务里
	 *
	 * @param orderId   易行机票订单ID
	 * @param operator  操作人
	 * @param content   操作内容
	 * @param debugInfo 调试信息
	 * @return
	 */
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public YXJPOrderOperateLog recordOnNewTx(String orderId, String operator, String content, String debugInfo) {
		return record(orderId, operator, content, debugInfo);
	}

}
