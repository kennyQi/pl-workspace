package slfx.xl.app.service.local;

import hg.common.component.BaseServiceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import slfx.xl.app.dao.LineOrderTravelerDAO;
import slfx.xl.domain.model.order.LineOrderTraveler;
import slfx.xl.pojo.qo.LineOrderTravelerQO;

/**
 * 
 *@类功能说明：线路游玩人LOCALSERVICE(操作数据库)实现
 *@类修改者：
 *@修改日期：
 *@修改说明：
 *@公司名称：浙江汇购科技有限公司
 *@部门：技术部
 *@作者：tandeng
 *@创建时间：2014年12月18日下午3:22:46
 *
 */
@Service
@Transactional
public class LineOrderTravelerLocalService extends BaseServiceImpl<LineOrderTraveler, LineOrderTravelerQO, LineOrderTravelerDAO>{

	@Autowired
	private LineOrderTravelerDAO lineOrderTravelerDAO;
	
	
	@Override
	protected LineOrderTravelerDAO getDao() {
		return lineOrderTravelerDAO;
	}
}
