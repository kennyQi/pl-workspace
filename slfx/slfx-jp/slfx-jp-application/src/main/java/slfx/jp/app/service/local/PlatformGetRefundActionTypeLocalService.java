package slfx.jp.app.service.local;

import hg.common.component.BaseServiceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import slfx.jp.app.dao.JPOrderDAO;
import slfx.jp.domain.model.order.JPOrder;
import slfx.jp.pojo.dto.supplier.yg.YGRefundActionTypesDTO;
import slfx.jp.qo.admin.PlatformOrderQO;
import slfx.yg.open.inter.YGFlightService;

/**
 * 
 * @类功能说明：LOCAL申请退废票类型SERVICE实现
 * @类修改者：
 * @修改日期：
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @作者：tandeng
 * @创建时间：2014年7月31日下午4:45:13
 * @版本：V1.0
 *
 */
@Service
@Transactional
public class PlatformGetRefundActionTypeLocalService extends BaseServiceImpl<JPOrder, PlatformOrderQO, JPOrderDAO> {

	@Autowired
	private YGFlightService ygFlightService;
	
	@Override
	protected JPOrderDAO getDao() {
		return null;
	}
	
	public YGRefundActionTypesDTO getRefundActionType(final String platCode) {
		return ygFlightService.getRefundActionType(platCode);
	}

}
