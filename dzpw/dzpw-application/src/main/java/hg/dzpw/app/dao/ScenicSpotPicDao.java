package hg.dzpw.app.dao;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import hg.common.component.BaseDao;
import hg.dzpw.app.pojo.qo.ScenicSpotPicQo;
import hg.dzpw.domain.model.scenicspot.ScenicSpotPic;
/**
 * 
 * @类功能说明：景区图片数据库dao
 * @类修改者：
 * @修改日期：
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @作者：guotx
 * @创建时间：2015-12-11下午2:15:18
 * @版本：
 */
@Repository
public class ScenicSpotPicDao extends BaseDao<ScenicSpotPic, ScenicSpotPicQo> {
	@Autowired
	private ScenicSpotDao scenicSpotDao;
	@Override
	protected Criteria buildCriteria(Criteria criteria, ScenicSpotPicQo qo) {
		if (StringUtils.isNotBlank(qo.getId())) {
			criteria.add(Restrictions.eq("id", qo.getId()));
		}
		if (StringUtils.isNotBlank(qo.getPicUrl())) {
			criteria.add(Restrictions.eq("url", qo.getPicUrl()));
		}
		if (qo.getScenicSpotQo()!=null) {
			Criteria scenicSpotCriteria=criteria.createCriteria("scenicSpot");
			scenicSpotDao.buildCriteriaOut(scenicSpotCriteria, qo.getScenicSpotQo());
		}
		return criteria;
	}

	@Override
	protected Class<ScenicSpotPic> getEntityClass() {
		return ScenicSpotPic.class;
	}

}
