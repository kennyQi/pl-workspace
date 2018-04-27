package hg.system.dao;

import hg.common.component.BaseDao;
import hg.system.model.image.ImageSpec;
import hg.system.qo.ImageSpecQo;

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
 * @类功能说明：图片附件_dao
 * @类修改者：zzb
 * @修改日期：2014年9月3日上午11:17:18
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @作者：zzb
 * @创建时间：2014年9月3日上午11:17:18
 *
 */
@Repository
public class ImageSpecDao extends BaseDao<ImageSpec, ImageSpecQo> {

	@Autowired
	private ImageDao imageDao;
	
	/**
	 * 查询接口实现
	 */
	@Override
	protected Criteria buildCriteria(Criteria criteria, ImageSpecQo qo) {
		if (qo != null) {
			// 图片
			if (qo.getImageQo() != null) {
				Criteria clientCriteria = criteria.createCriteria("image", 
						qo.getImageQo().getAlias(), JoinType.LEFT_OUTER_JOIN);
				imageDao.buildCriteriaOut(clientCriteria, qo.getImageQo());
			}
			// 别名
			if (StringUtils.isNotEmpty(qo.getKey())) {
				criteria.add(Restrictions.eq("key", qo.getKey()));
			}
			// md5
			if (StringUtils.isNotEmpty(qo.getMd5())) {
				criteria.add(Restrictions.like("fileInfo", 
						qo.getMd5(), MatchMode.ANYWHERE));
			}
		}
		return criteria;
	}

	@Override
	protected Class<ImageSpec> getEntityClass() {
		return ImageSpec.class;
	}
	
	@Override
	public ImageSpec queryUnique(ImageSpecQo qo) {
		List<ImageSpec> list = super.queryList(qo);
		if(list != null && list.size() > 0)
			return list.get(0);
		return null;
	}


}
