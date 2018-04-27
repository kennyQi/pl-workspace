package hg.system.dao;

import hg.common.component.BaseDao;
import hg.system.model.image.Album;
import hg.system.qo.AlbumQo;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.hibernate.sql.JoinType;
import org.springframework.stereotype.Repository;

/**
 * 
 * @类功能说明：相册_dao
 * @类修改者：zzb
 * @修改日期：2014年9月3日上午9:14:52
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @作者：zzb
 * @创建时间：2014年9月3日上午9:14:52
 *
 */
@Repository
public class AlbumDao extends BaseDao<Album, AlbumQo> {

	/**
	 * 查询接口实现
	 */
	@Override
	protected Criteria buildCriteria(Criteria criteria, AlbumQo qo) {
		if (qo != null) {
			// 相册标题
			if (StringUtils.isNotBlank(qo.getTitle())) {
				if (qo.isTitleLike()) {
					criteria.add(Restrictions.like("title", qo.getTitle(), MatchMode.ANYWHERE));
				} else {
					criteria.add(Restrictions.eq("title", qo.getTitle()));
				}
			}
			// 上级节点id
			if(qo.isRoot()) {
				criteria.add(Restrictions.isNull("parent"));
			} else {
				if (qo.getParent() != null) {
					Criteria clientCriteria = criteria.createCriteria("parent", qo.getParent().getAlias(), JoinType.LEFT_OUTER_JOIN);
					this.buildCriteriaOut(clientCriteria, qo.getParent());
				}
			}
			// 项目id
			if (StringUtils.isNotBlank(qo.getProjectId())) {
				if (qo.isProjectIdLike()) {
					criteria.add(Restrictions.like("projectId", qo.getProjectId(), MatchMode.ANYWHERE));
				} else {
					criteria.add(Restrictions.eq("projectId", qo.getProjectId()));
				}
			}
			// 相册类型
			if (qo.getUseType() != null) {
				criteria.add(Restrictions.eq("useType", qo.getUseType()));
			}
			// 所有者id
			if (StringUtils.isNotBlank(qo.getOwnerId())) {
				criteria.add(Restrictions.eq("ownerId", qo.getOwnerId()));
			}
			// 所有者下属ID
			if (StringUtils.isNotBlank(qo.getOwnerItemId())) {
				criteria.add(Restrictions.eq("ownerItemId", qo.getOwnerItemId()));
			}
		}
		return criteria;
	}

	@Override
	protected Class<Album> getEntityClass() {
		return Album.class;
	}
	
	@Override
	public Album queryUnique(AlbumQo qo) {
		List<Album> list = super.queryList(qo);
		if(list != null && list.size() > 0)
			return list.get(0);
		return null;
	}


}
