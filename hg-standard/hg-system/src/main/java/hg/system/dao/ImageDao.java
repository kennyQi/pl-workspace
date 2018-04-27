package hg.system.dao;

import hg.common.component.BaseDao;
import hg.common.util.DateUtil;
import hg.system.model.image.Image;
import hg.system.qo.ImageQo;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.hibernate.sql.JoinType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * 
 * @类功能说明：图片_dao
 * @类修改者：zzb
 * @修改日期：2014年9月3日上午9:15:10
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @作者：zzb
 * @创建时间：2014年9月3日上午9:15:10
 *
 */
@Repository
public class ImageDao extends BaseDao<Image, ImageQo> {

	@Autowired
	private AlbumDao albumDao;
	
	/**
	 * 查询接口实现
	 */
	@Override
	protected Criteria buildCriteria(Criteria criteria, ImageQo qo) {
		if (qo != null) {
			// 图片标题
			if (StringUtils.isNotBlank(qo.getTitle())) {
				if (qo.isTitleLike()) {
					criteria.add(Restrictions.like("title", qo.getTitle(), MatchMode.ANYWHERE));
				} else {
					criteria.add(Restrictions.eq("title", qo.getTitle()));
				}
			}
			// 创建时间
			if (StringUtils.isNotBlank(qo.getCreateDateBegin())) {
				criteria.add(Restrictions.ge("createDate", DateUtil.dateStr2BeginDate(qo.getCreateDateBegin())));
			}
			if (StringUtils.isNotBlank(qo.getCreateDateEnd())) {
				criteria.add(Restrictions.le("createDate", DateUtil.dateStr2EndDate(qo.getCreateDateEnd())));
			}
			// 相册
			if (qo.getAlbumQo() != null) {
				Criteria clientCriteria = criteria.createCriteria("album", qo.getAlbumQo().getAlias(), JoinType.LEFT_OUTER_JOIN);
				albumDao.buildCriteriaOut(clientCriteria, qo.getAlbumQo());
			}
		}
		return criteria;
	}

	@Override
	protected Class<Image> getEntityClass() {
		return Image.class;
	}
	
	@Override
	public Image queryUnique(ImageQo qo) {
		List<Image> list = super.queryList(qo);
		if(list != null && list.size() > 0)
			return list.get(0);
		return null;
	}


}
