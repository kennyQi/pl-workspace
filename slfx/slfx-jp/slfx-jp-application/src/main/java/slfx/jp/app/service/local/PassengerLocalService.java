package slfx.jp.app.service.local;

import hg.common.component.BaseServiceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import slfx.jp.app.dao.PassengerDAO;
import slfx.jp.domain.model.order.Passenger;
import slfx.jp.qo.admin.PassengerQO;

/**
 * @类功能说明： 乘客信息SERVICE
 * @类修改者：
 * @修改日期：
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @作者：yangkang
 * @创建时间：2014-8-6下午3:26:30
 * @版本：V1.0
 *
 */
@Service
@Transactional
public class PassengerLocalService extends BaseServiceImpl<Passenger, PassengerQO, PassengerDAO>{

	@Autowired
	private PassengerDAO passengerDao;

	@Override
	protected PassengerDAO getDao() {
		return passengerDao;
	}

}
