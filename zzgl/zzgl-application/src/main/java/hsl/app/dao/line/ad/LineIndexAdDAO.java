package hsl.app.dao.line.ad;
import hg.common.component.BaseDao;
import hsl.domain.model.xl.ad.LineIndexAd;
import hsl.pojo.qo.line.ad.LineIndexAdQO;
import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
@Repository
public class LineIndexAdDAO extends BaseDao<LineIndexAd,LineIndexAdQO>{
	@Override
	protected Criteria buildCriteria(Criteria criteria, LineIndexAdQO qo) {
		if(StringUtils.isNotBlank(qo.getContentId())){//qo.getProgramaId()
			Criteria criteria2=criteria.createCriteria("programaContent");
			criteria2.add(Restrictions.eq("id", qo.getContentId()));
			//criteria.add(Restrictions.eq("programaContent.id", qo.getContentId()));
		}
		if(StringUtils.isNotBlank(qo.getAdId())){
			criteria.add(Restrictions.eq("adId", qo.getAdId()));
		}
//		if(qo.getIsFecthContent()){
//			criteria.setFetchMode("programaContent", FetchMode.JOIN);
//		}
		if(qo.getLineType()!=null){
			criteria.add(Restrictions.eq("lineType", qo.getLineType()));
		}
		
		
		return criteria;
	}
	
	@Override
	protected Class<LineIndexAd> getEntityClass() {
		return LineIndexAd.class;
	}
}
