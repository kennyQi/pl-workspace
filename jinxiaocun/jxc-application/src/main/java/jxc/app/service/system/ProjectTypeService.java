package jxc.app.service.system;

import hg.common.component.BaseQo;
import hg.common.component.BaseServiceImpl;
import jxc.app.dao.system.ProjectTypeDao;
import jxc.domain.model.system.ProjectType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
@Service
@Transactional
public class ProjectTypeService extends BaseServiceImpl<ProjectType,BaseQo,ProjectTypeDao>{

	@Autowired
	ProjectTypeDao projectTypeDao;
	
	@Override
	protected ProjectTypeDao getDao() {
		return projectTypeDao;
	}

	/**
	 * 新建项目类型
	 * @param name
	 * @return
	 */
	public ProjectType createProjectType(String name){
		
		ProjectType type = new ProjectType();
		type.createProjectType(name);
		this.save(type);
		
		return type;
	}
}
