package plfx.gnjp.app.service.local;

import hg.common.component.BaseServiceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import plfx.gnjp.app.dao.PassengerDAO;
import plfx.gnjp.domain.model.order.GNJPPassenger;
import plfx.yeexing.qo.admin.PassengerQO;

/**
 * 
 * @类功能说明：乘客信息SERVICE
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @作者：yuqz
 * @创建时间：2015年6月30日下午2:48:58
 * @版本：V1.0
 *
 */
@Service
@Transactional
public class PassengerLocalService extends BaseServiceImpl<GNJPPassenger, PassengerQO, PassengerDAO>{

	@Autowired
	private PassengerDAO passengerDao;

	@Override
	protected PassengerDAO getDao() {
		return passengerDao;
	}

}
