package hsl.app.service.local.yxjp;

import hg.common.component.BaseServiceImpl;
import hsl.app.dao.yxjp.YXJPOrderPayRecordDAO;
import hsl.domain.model.yxjp.YXJPOrderPayRecord;
import hsl.pojo.qo.yxjp.YXJPOrderPayRecordQO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 易行机票订单支付记录服务
 *
 * @author zhurz
 * @since 1.7
 */
@Service
@Transactional
public class YXJPOrderPayRecordLocalService extends BaseServiceImpl<YXJPOrderPayRecord, YXJPOrderPayRecordQO, YXJPOrderPayRecordDAO> {

	@Autowired
	private YXJPOrderPayRecordDAO dao;

	@Override
	protected YXJPOrderPayRecordDAO getDao() {
		return dao;
	}

}
