package jxc.app.service.system;

import hg.common.component.BaseQo;
import hg.common.component.BaseServiceImpl;
import jxc.app.dao.system.ProjectModeDao;
import jxc.domain.model.system.ProjectMode;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
@Service
@Transactional
public class ProjectModeService extends BaseServiceImpl<ProjectMode,BaseQo,ProjectModeDao>{

	@Autowired
	ProjectModeDao projectModeDao;
	
	@Override
	protected ProjectModeDao getDao() {
		return projectModeDao;
	}
	
	/**
	 * 新建项目模式
	 * @param name
	 * @return
	 */
	public ProjectMode createProjectMode(String name){
		
		ProjectMode mode = new ProjectMode();
		mode.createMode(name);
		this.save(mode);
		
		return mode;
	}
}
