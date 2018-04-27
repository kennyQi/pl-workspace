package hg.dzpw.app.dao;

import hg.common.component.BaseDao;
import hg.dzpw.app.pojo.qo.ClientDeviceQo;
import hg.dzpw.domain.model.scenicspot.ClientDevice;

import org.hibernate.Criteria;
import org.hibernate.sql.JoinType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class ClientDeviceDao extends BaseDao<ClientDevice, ClientDeviceQo> {

	@Autowired
	private ScenicSpotDao scenicSpotDao;

	@Override
	protected Criteria buildCriteria(Criteria criteria, ClientDeviceQo qo) {
		if (qo != null) {
			if (qo.getScenicSpotQo() != null) {
				Criteria scenicSpotCriteria = criteria.createCriteria(
						"scenicSpot", qo.getScenicSpotQo().getAlias(), JoinType.INNER_JOIN);
				scenicSpotDao.buildCriteriaOut(scenicSpotCriteria, qo.getScenicSpotQo());
			}
		}
		return criteria;
	}

	@Override
	protected Class<ClientDevice> getEntityClass() {
		return ClientDevice.class;
	}
}