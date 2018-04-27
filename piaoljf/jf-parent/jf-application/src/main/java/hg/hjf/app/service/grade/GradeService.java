package hg.hjf.app.service.grade;

import hg.common.component.BaseServiceImpl;
import hg.common.page.Pagination;
import hg.hjf.app.dao.grade.GradeDao;
import hg.hjf.domain.model.grade.GradeQo;
import hgtech.jfaccount.Grade;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * 会员等级设置的service
 * 
 * @author xinglj
 *
 */
@Service
@Transactional
public class GradeService extends BaseServiceImpl<Grade, GradeQo, GradeDao>{

	@Autowired
	GradeDao gradeDao;
	
	@Override
	protected GradeDao getDao() {
		return gradeDao;
	}

	public Pagination findPagination(Pagination paging) {
		return getDao().queryPagination(paging);
	}

	public Grade queryByCode(String code) {
		GradeQo qo = new GradeQo();
		qo.setCode(code);
		return getDao().queryUnique(qo);
	}

	
	
	/**
	 * 升级、降级处理
	 */
	public void handleUserGrade(){
		gradeDao.handleUserGrade();
	}
	
}
