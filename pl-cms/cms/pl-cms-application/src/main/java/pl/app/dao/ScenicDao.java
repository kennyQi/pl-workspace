package pl.app.dao;
import hg.common.component.BaseDao;
import org.hibernate.Criteria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import pl.cms.domain.entity.scenic.Scenic;
import pl.cms.pojo.qo.ScenicQO;
@Repository
public class ScenicDao extends BaseDao<Scenic, ScenicQO> {

	@Autowired
	private ArticleChannelDao channelDao;
	
	@Override
	protected Criteria buildCriteria(Criteria criteria, ScenicQO qo) {
		
		return criteria;
	}

	@Override
	protected Class<Scenic> getEntityClass() {
		return Scenic.class;
	}
	
}
