package plfx.xl.app.service.local;

import hg.common.component.BaseServiceImpl;
import hg.common.page.Pagination;
import hg.log.repository.DomainEventRepository;

import java.util.List;

import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import plfx.xl.app.dao.LineFinanceDAO;
import plfx.xl.domain.model.order.LineOrder;
import plfx.xl.pojo.qo.LineFinanceQO;

/**
 * 
 *@类功能说明：线路财务管理LOCALSERVICE(操作数据库)实现
 *@类修改者：
 *@修改日期：
 *@修改说明：
 *@公司名称：浙江汇购科技有限公司
 *@部门：技术部
 *@作者：tandeng
 *@创建时间：2014年12月25日下午3:22:46
 *
 */
@Service
@Transactional
public class LineFinanceLocalService extends BaseServiceImpl<LineOrder, LineFinanceQO, LineFinanceDAO>{
	
	@Autowired
	private DomainEventRepository domainEventRepository;

	@Autowired
	private LineFinanceDAO lineFinanceDAO;

	@Override
	protected LineFinanceDAO getDao() {
		return lineFinanceDAO;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public Pagination queryPagination(Pagination pagination) {
		Pagination pagination2 = lineFinanceDAO.queryPagination(pagination);
		List<LineOrder> lineOrderList = (List<LineOrder>) pagination2.getList();
		for (LineOrder lineOrder : lineOrderList) {
			Hibernate.isInitialized(lineOrder.getTravelers());
			lineOrder.getTravelers().size();
		}
		pagination2.setList(lineOrderList);
		return pagination2;
	}
	
}
