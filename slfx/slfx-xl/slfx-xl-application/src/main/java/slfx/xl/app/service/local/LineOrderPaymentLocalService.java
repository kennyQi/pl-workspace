package slfx.xl.app.service.local;

import hg.common.component.BaseServiceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import slfx.xl.app.dao.LineOrderPaymentDAO;
import slfx.xl.domain.model.order.LineOrderPayment;
import slfx.xl.pojo.qo.LineOrderPaymentQO;

/**
 * 
 * @类功能说明：订单支付信息LocalService
 * @类修改者：
 * @修改日期：
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @作者：luoyun
 * @创建时间：2014年12月23日下午4:49:54
 * @版本：V1.0
 *
 */
@Service
@Transactional
public class LineOrderPaymentLocalService extends BaseServiceImpl<LineOrderPayment,LineOrderPaymentQO,LineOrderPaymentDAO>{

	@Autowired
	private LineOrderPaymentDAO lineOrderPaymentDAO;
	
	@Override
	protected LineOrderPaymentDAO getDao() {
		return lineOrderPaymentDAO;
	}

}
