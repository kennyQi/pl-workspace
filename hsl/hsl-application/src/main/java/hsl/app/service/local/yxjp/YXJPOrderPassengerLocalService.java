package hsl.app.service.local.yxjp;

import hg.common.component.BaseServiceImpl;
import hsl.app.dao.yxjp.YXJPOrderPassengerDAO;
import hsl.domain.model.yxjp.YXJPOrderPassenger;
import hsl.pojo.qo.yxjp.YXJPOrderPassengerQO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 易行机票乘客服务
 *
 * @author zhurz
 * @since 1.7
 */
@Service
@Transactional
public class YXJPOrderPassengerLocalService extends BaseServiceImpl<YXJPOrderPassenger, YXJPOrderPassengerQO, YXJPOrderPassengerDAO> {

	@Autowired
	private YXJPOrderPassengerDAO dao;

	@Override
	protected YXJPOrderPassengerDAO getDao() {
		return dao;
	}
}
