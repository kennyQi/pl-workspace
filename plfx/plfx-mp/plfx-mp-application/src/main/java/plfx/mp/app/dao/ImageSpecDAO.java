package plfx.mp.app.dao;

import hg.common.component.BaseDao;

import org.hibernate.Criteria;
import org.springframework.stereotype.Repository;

import plfx.mp.app.pojo.qo.ImageSpecQO;
import plfx.mp.domain.model.scenicspot.ImageSpecTemp;

@Repository
public class ImageSpecDAO extends BaseDao<ImageSpecTemp, ImageSpecQO> {

	@Override
	protected Criteria buildCriteria(Criteria criteria, ImageSpecQO qo) {
		return criteria;
	}

	@Override
	protected Class<ImageSpecTemp> getEntityClass() {
		return ImageSpecTemp.class;
	}
	
	/**
	 * 根据景区ID删除对应图片
	 * 
	 * @param scenicSpotId
	 */
	public void deleteByScenicSpotId(String scenicSpotId) {
		if (scenicSpotId == null) return;
		String hql = "delete from ImageSpecTemp where scenicSpot.id = ?";
		executeHql(hql, scenicSpotId);
	}
	
}
