package hg.service.image.app.dao;

import hg.common.component.BaseDao;
import hg.service.image.domain.model.ImageUseType;
import hg.service.image.domain.qo.ImageUseTypeLocalQo;
import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

/**
 * @类功能说明：用途Dao
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @作者：chenys
 * @创建时间：2014年11月3日 下午10:52:17
 */
@Repository
public class ImageUseTypeDao extends BaseDao<ImageUseType,ImageUseTypeLocalQo> {

	@Override
	protected Criteria buildCriteria(Criteria criteria, ImageUseTypeLocalQo qo) {
		if(null == qo)
			return criteria;
		
		if(StringUtils.isNotBlank(qo.getProjectId()))
			criteria.add(Restrictions.eq("projectId",qo.getProjectId()));
		//标题
		if(StringUtils.isNotBlank(qo.getTitle()) && qo.isTitleLike()){
			criteria.add(Restrictions.like("title",qo.getTitle(),MatchMode.ANYWHERE));
		}
		if(StringUtils.isNotBlank(qo.getTitle()) && !qo.isTitleLike()){
			criteria.add(Restrictions.eq("title",qo.getTitle()));
		}
		//图片规格KEY JSON
		if(StringUtils.isNotBlank(qo.getImageSpecKeysJson())){
			criteria.add(Restrictions.eq("imageSpecKeysJson",qo.getImageSpecKeysJson()));
		}
		return criteria;
	}

	@Override
	protected Class<ImageUseType> getEntityClass() {
		return ImageUseType.class;
	}
}