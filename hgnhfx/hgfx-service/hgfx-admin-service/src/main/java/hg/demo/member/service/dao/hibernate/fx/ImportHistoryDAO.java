package hg.demo.member.service.dao.hibernate.fx;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import hg.demo.member.service.qo.hibernate.fx.ImportHistoryQO;
import hg.framework.service.component.base.hibernate.BaseHibernateDAO;
import hg.fx.domain.ImportHistory;

/**
 * @类功能说明：doto
 * @类修改者：
 * @修改日期：
 * @修改说明：
 * @公司名称： 浙江汇购科技有限公司
 * @部门： 技术部
 * @作者： zhouwei
 * @创建时间： 2016年6月1日 上午11:54:21
 * @版本： V1.0
 */
@Repository("importHistoryDAO")
public class ImportHistoryDAO extends BaseHibernateDAO<ImportHistory, ImportHistoryQO> {

	@Override
	protected Class<ImportHistory> getEntityClass() {
		return ImportHistory.class;
	}

	@Override
	protected void queryEntityComplete(ImportHistoryQO qo,
			List<ImportHistory> list) {
	}

	@Override
	protected Criteria buildCriteria(Criteria criteria, ImportHistoryQO qo) {
		if(qo.isQueryDistributorUser()){
			ProjectionList projectionList = Projections.projectionList();
			projectionList.add(Projections.groupProperty("dstributorUser.id"));
			criteria.setProjection(projectionList);
		}
		return criteria;
	}

}
