package hg.service.image.app.dao;

import hg.common.component.BaseDao;
import hg.service.image.domain.model.Image;
import hg.service.image.domain.qo.ImageLocalQo;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.FetchMode;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.hibernate.sql.JoinType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * @类功能说明：图片Dao
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @作者：chenys
 * @创建时间：2014年11月3日 下午10:08:19
 */
@Repository("imageDao2")
public class ImageDao extends BaseDao<Image,ImageLocalQo> {
	
	@Autowired
	private ImageUseTypeDao typeDao;
	
	@Autowired
	private AlbumDao albumDao;
	
	@Override
	protected Criteria buildCriteria(Criteria criteria, ImageLocalQo qo) {
		if(null == qo)
			return criteria;
		// 所属相册
		if (qo.getAlbumQo() != null) {
			Criteria clientCriteria = criteria.createCriteria("album", qo.getAlbumQo().getAlias(), JoinType.LEFT_OUTER_JOIN);
			albumDao.buildCriteriaOut(clientCriteria, qo.getAlbumQo());
		}
		// 排序
		if (qo.getCreateDateSort() != 0) 
			criteria.addOrder(qo.getCreateDateSort() > 0 ? Order.asc("createDate") : Order.desc("createDate"));
		//环境和项目
		if(StringUtils.isNotBlank(qo.getProjectId()))
			criteria.add(Restrictions.eq("projectId",qo.getProjectId()));
		if(StringUtils.isNotBlank(qo.getEnvName()))
			criteria.add(Restrictions.eq("envName", qo.getEnvName()));
		//标题
		if(StringUtils.isNotBlank(qo.getTitle()) && qo.isTitleLike()){
			criteria.add(Restrictions.like("title",qo.getTitle(),MatchMode.ANYWHERE));
		}
		if(StringUtils.isNotBlank(qo.getTitle()) && !qo.isTitleLike()){
			criteria.add(Restrictions.eq("title",qo.getTitle()));
		}
		//创建时间
		if(null != qo.getCreateBeginDate()){
			criteria.add(Restrictions.ge("createDate",qo.getCreateBeginDate()));
		}
		if(null != qo.getCreateEndDate()){
			criteria.add(Restrictions.le("createDate",qo.getCreateEndDate()));
		}
		//图片规格Key
		if(StringUtils.isNotBlank(qo.getImageSpecKey())){
			criteria.createCriteria("specImageMap",JoinType.RIGHT_OUTER_JOIN).add(Restrictions.eq("key",qo.getImageSpecKey()));
		}
		//所有者id
		if(StringUtils.isNotBlank(qo.getOwnerId())){
			criteria.add(Restrictions.eq("ownerId",qo.getOwnerId()));
		}
		//对应id
		if(StringUtils.isNotBlank(qo.getFromId())){
			criteria.add(Restrictions.eq("fromId",qo.getFromId()));
		}
		//相册用途
		if(null != qo.getUseType()){
			Criteria criteria2 = criteria.createCriteria("useType",JoinType.LEFT_OUTER_JOIN);
			typeDao.buildCriteriaOut(criteria2, qo.getUseType());
		}
		//是否回收(回收站)
		if(null != qo.getIsRefuse()){
			criteria.add(Restrictions.eq("status.current",qo.getIsRefuse()));
		}
		//图片路径
		/*if(StringUtils.isNotBlank(qo.getWaypath()) && qo.isWaypathLike()){
			criteria.add(Restrictions.like("waypath",qo.getWaypath(),MatchMode.START));
		}
		if(StringUtils.isNotBlank(qo.getWaypath()) && !qo.isWaypathLike()){
			criteria.add(Restrictions.eq("waypath",qo.getWaypath()));
		}*/
		//图片全名
		if(StringUtils.isNotBlank(qo.getFullName()) && qo.isFullNameLike()){
			criteria.add(Restrictions.like("fullName",qo.getFullName(),MatchMode.ANYWHERE));
		}
		if(StringUtils.isNotBlank(qo.getFullName()) && !qo.isFullNameLike()){
			criteria.add(Restrictions.eq("fullName",qo.getFullName()));
		}
		if (qo.isFetchAlbum()) {
			criteria.setFetchMode("album", FetchMode.JOIN);
		}
		if (qo.isFetchUseType()) {
			criteria.setFetchMode("useType", FetchMode.JOIN);
		}
		criteria.addOrder(Order.desc("createDate"));
		return criteria;
	}

	@Override
	protected Class<Image> getEntityClass() {
		return Image.class;
	}
}