package hg.service.image.app.dao;

import hg.common.component.BaseDao;
import hg.service.image.domain.model.Album;
import hg.service.image.domain.qo.AlbumLocalQo;
import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.hibernate.sql.JoinType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * @类功能说明：相册Dao
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @作者：chenys
 * @创建时间：2014年11月3日 下午9:49:00
 */
@Repository("albumDao2")
public class AlbumDao extends BaseDao<Album, AlbumLocalQo> {
	@Autowired
	private ImageUseTypeDao typeDao;

	@Override
	protected Criteria buildCriteria(Criteria criteria, AlbumLocalQo qo) {
		if (null == qo)
			return criteria;

		// 排序
		if (qo.getCreateDateSort() != 0)
			criteria.addOrder(qo.getCreateDateSort() > 0 ? Order
					.asc("createDate") : Order.desc("createDate"));
		// 环境和项目
		if (StringUtils.isNotBlank(qo.getProjectId()))
			criteria.add(Restrictions.eq("projectId", qo.getProjectId()));
		if (StringUtils.isNotBlank(qo.getEnvName()))
			criteria.add(Restrictions.eq("envName", qo.getEnvName()));
		// 标题
		if (StringUtils.isNotBlank(qo.getTitle()) && qo.isTitleLike()) {
			criteria.add(Restrictions.like("title", qo.getTitle(),
					MatchMode.ANYWHERE));
		}
		if (StringUtils.isNotBlank(qo.getTitle()) && !qo.isTitleLike()) {
			criteria.add(Restrictions.eq("title", qo.getTitle()));
		}
		// 创建时间
		if (null != qo.getCreateBeginDate()) {
			criteria.add(Restrictions.ge("createDate", qo.getCreateBeginDate()));
		}
		if (null != qo.getCreateEndDate()) {
			criteria.add(Restrictions.le("createDate", qo.getCreateEndDate()));
		}
		/*// 所有者id
		if (StringUtils.isNotBlank(qo.getOwnerId())) {
			criteria.add(Restrictions.eq("ownerId", qo.getOwnerId()));
		}
		// 对应id
		if (StringUtils.isNotBlank(qo.getFromId())) {
			criteria.add(Restrictions.eq("fromId", qo.getFromId()));
		}
		// 是否回收(回收站)
		if (null != qo.getIsRefuse()) {
			criteria.add(Restrictions.eq("refuse", qo.getIsRefuse()));
		}*/
		// 图片用途
//		if (null != qo.getUseType()) {
//			Criteria criteria2 = criteria.createCriteria("useType",
//					JoinType.LEFT_OUTER_JOIN);
//			typeDao.buildCriteriaOut(criteria2, qo.getUseType());
//		}
		
		// 相册路径
		if (StringUtils.isNotBlank(qo.getPath())) {
			if (qo.isPathLike()) {
				criteria.add(Restrictions.like("path", qo.getPath(),
						MatchMode.START));
			} else {
				criteria.add(Restrictions.eq("path", qo.getPath()));
			}
		}
		// 相册全名
		if (StringUtils.isNotBlank(qo.getName())) {
			if (qo.isNameLike()) {
				criteria.add(Restrictions.like("name", qo.getName(),
						MatchMode.ANYWHERE));
			} else {
				criteria.add(Restrictions.eq("name", qo.getName()));
			}
		}
		return criteria;
	}

	@Override
	protected Class<Album> getEntityClass() {
		return Album.class;
	}
}