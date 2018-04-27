package hsl.app.dao.dzp.scenicspot;

import hg.common.component.BaseDao;
import hsl.domain.model.dzp.scenicspot.DZPScenicSpotPic;
import hsl.pojo.qo.dzp.scenicspot.DZPScenicSpotPicQO;
import org.hibernate.Criteria;
import org.springframework.stereotype.Repository;

/**
 * 景区封面QO
 * Created by hgg on 2016/3/7.
 */
@Repository("dzpScenicSpotPicDAO")
public class DZPScenicSpotPicDAO extends BaseDao<DZPScenicSpotPic,DZPScenicSpotPicQO> {

    @Override
    protected Criteria buildCriteria(Criteria criteria, DZPScenicSpotPicQO qo) {

        return null;
    }

    @Override
    protected Class<DZPScenicSpotPic> getEntityClass() {
        return DZPScenicSpotPic.class;
    }
}
