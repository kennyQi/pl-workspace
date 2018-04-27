package hg.service.image.app.dao;

import hg.common.component.BaseDao;
import hg.service.image.domain.model.ImageSpec;
import hg.service.image.domain.qo.ImageSpecLocalQo;
import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.hibernate.sql.JoinType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * @类功能说明：图片规格Dao
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @作者：chenys
 * @创建时间：2014年11月3日 下午10:08:19
 */
@Repository("imageSpecDao2")
public class ImageSpecDao extends BaseDao<ImageSpec,ImageSpecLocalQo> {
	@Autowired
	private ImageDao imgDao;
	
	@Override
	protected Criteria buildCriteria(Criteria criteria, ImageSpecLocalQo qo) {
		if(null == qo)
			return criteria;
		
		if(StringUtils.isNotBlank(qo.getProjectId()))
			criteria.add(Restrictions.eq("projectId",qo.getProjectId()));
		if(StringUtils.isNotBlank(qo.getEnvName()))
			criteria.add(Restrictions.eq("envName", qo.getEnvName()));
		if(null != qo.getImage()){
			Criteria criteria2 = criteria.createCriteria("image",JoinType.LEFT_OUTER_JOIN);
			imgDao.buildCriteriaOut(criteria2,qo.getImage());
		}
		if(StringUtils.isNotBlank(qo.getKey())){
			criteria.add(Restrictions.eq("key",qo.getKey()));
		}
		return criteria;
	}

	@Override
	protected Class<ImageSpec> getEntityClass() {
		return ImageSpec.class;
	}
}