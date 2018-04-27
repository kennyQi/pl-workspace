package pl.app.dao;
import hg.common.component.BaseDao;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import pl.cms.domain.entity.lunbo.IndexLunbo;
import pl.cms.pojo.qo.IndexLunboQO;

@Repository
public class IndexLunboDao extends BaseDao<IndexLunbo, IndexLunboQO> {

	
	@Override
	protected Criteria buildCriteria(Criteria criteria, IndexLunboQO qo) {
		
//		if (qo != null) {
//			if(qo.getIsShow()!=null){
//				criteria.add(Restrictions.eq("isShow",qo.getIsShow()));
//			}
//		}
		return criteria;
	}

	@Override
	protected Class<IndexLunbo> getEntityClass() {
		return IndexLunbo.class;
	}

}
