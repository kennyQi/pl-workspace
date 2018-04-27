package hsl.app.dao;
import hg.common.component.BaseDao;
import hsl.domain.model.programa.ProgramaContent;
import hsl.pojo.qo.programa.HslProgramaContentQO;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

@Repository
public  class ProgramaContentDao extends BaseDao<ProgramaContent, HslProgramaContentQO>{

	@Override
	protected Criteria buildCriteria(Criteria criteria, HslProgramaContentQO qo) {
		if(StringUtils.isNotBlank(qo.getProgramaId())){
			Criteria criteria2=criteria.createCriteria("programa");
			criteria2.add(Restrictions.eq("programa.id", qo.getProgramaId()));
		}
		if(StringUtils.isNotBlank(qo.getContent())){
			criteria.add(Restrictions.ilike("content", qo.getContent()));
		}
		return criteria;
	}

	@Override
	protected Class<ProgramaContent> getEntityClass() {
		return ProgramaContent.class;
	}

}
