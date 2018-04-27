package slfx.jp.app.service.local;

import hg.common.component.BaseServiceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import slfx.jp.app.dao.FlightPolicyDAO;
import slfx.jp.domain.model.order.FlightPolicy;
import slfx.jp.qo.admin.YGPolicyQO;


/**
 * @类功能说明：LOCAL机票政策SERVICE
 * @类修改者：
 * @修改日期：
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @作者：yangkang
 * @创建时间：2014-8-8下午1:47:10
 * @版本：V1.0
 *
 */
@Service
@Transactional
public class JPFlightPolicyLocalService extends BaseServiceImpl<FlightPolicy, YGPolicyQO, FlightPolicyDAO>{

	@Autowired
	private FlightPolicyDAO flightPolicyDao;
	
	@Override
	protected FlightPolicyDAO getDao() {
		return flightPolicyDao;
	}
	
	
	
	
	
	
}
