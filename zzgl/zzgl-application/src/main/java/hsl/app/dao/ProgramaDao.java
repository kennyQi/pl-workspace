package hsl.app.dao;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.Hibernate;
//import org.hibernate.Hibernate;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import hg.common.component.BaseDao;
import hg.common.page.Pagination;
import hsl.domain.model.programa.Programa;
import hsl.pojo.qo.programa.HslProgramaQO;
@Repository
public class ProgramaDao extends BaseDao<Programa, HslProgramaQO>{
	@Override
	protected Class<Programa> getEntityClass() {
		return Programa.class;
	}
	@Override
	protected Criteria buildCriteria(Criteria criteria, HslProgramaQO qo) {
		try {
			if(qo!=null){
				if(StringUtils.isNotBlank(qo.getName())){
					criteria.add(Restrictions.like("name",qo.getName(), MatchMode.ANYWHERE));
				}
				if(qo.getLocation()!=null&&qo.getLocation()!=0){
					criteria.add(Restrictions.eq("location", qo.getLocation()));
				}
				if(qo.getStatus()!=null&&qo.getStatus()!=0){
					criteria.add(Restrictions.eq("status", qo.getStatus()));
				}
				//查询关联栏目内容list			
				criteria.addOrder(Order.desc("createDate"));

			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return criteria;
	}
	@Override
	public Programa queryUnique(HslProgramaQO qo) {
		Programa programa=super.queryUnique(qo);
		if(programa!=null){
			Hibernate.initialize(programa.getProgramaContentList());
		}
		return programa;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public Pagination queryPagination(Pagination pagination) {
		 pagination= super.queryPagination(pagination);
		List<Programa> programaList=(List<Programa>) pagination.getList();
		for (Programa Programa:programaList) {
			Hibernate.initialize(Programa.getProgramaContentList());
		}
		return pagination;
	}
}
