package hgria.admin.app.service;

import hg.common.component.BaseServiceImpl;
import hg.system.dao.AuthStaffDao;
import hg.system.model.staff.Staff;
import hg.system.qo.AuthStaffQo;
import hgria.admin.app.dao.ProjectDao;
import hgria.admin.app.pojo.command.project.CreateProjectCommand;
import hgria.admin.app.pojo.command.project.ModifyProjectCommand;
import hgria.admin.app.pojo.command.project.RemoveProjectCommand;
import hgria.admin.app.pojo.exception.IMAGEException;
import hgria.admin.app.pojo.qo.ProjectQo;
import hgria.domain.model.project.Project;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @类功能说明：项目本地_service
 * @类修改者：zzb
 * @修改日期：2014年11月28日下午5:01:11
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @作者：zzb
 * @创建时间：2014年11月28日下午5:01:11
 */
@Service
@Transactional
public class ProjectService extends BaseServiceImpl<Project, ProjectQo, ProjectDao> {
	
	/**
	 * 工程dao
	 */
	@Autowired
	private ProjectDao dao;
	
	/**
	 * 操作员dao
	 */
	@Autowired
	private AuthStaffDao staffdao;
	

	@Override
	protected ProjectDao getDao() {
		return dao;
	}
	
	
	private void checkStaff(String staffId) throws IMAGEException  {
		
		// 1. 非空检查
		if (StringUtils.isBlank(staffId))
			throw new IMAGEException(
					IMAGEException.PROJECT_CREATE_NOT_REQUIRED,
					"工程管理员" + "不能为空");
		
		// 2. 是否占用检查
		ProjectQo projectQo = new ProjectQo();
		AuthStaffQo staffQo = new AuthStaffQo();
		staffQo.setId(staffId);
		projectQo.setStaffQo(staffQo);
		
		Project project = dao.queryUnique(projectQo);
		if (project != null)
			throw new IMAGEException(
					IMAGEException.PROJECT_STAFF_USED,
					"工程管理员已是【" + project.getName() + "】的管理员！请更换请他管理员！");
	}


	public void createProject(CreateProjectCommand command) throws IMAGEException {
		
		// 1. 检查工程操作员是否已占用
		checkStaff(command.getStaffId());
		
		// 2. 创建工程
		AuthStaffQo StaffQo = new AuthStaffQo();
		StaffQo.setId(command.getStaffId());
		Staff staff = staffdao.queryUnique(StaffQo);
		
		Project project = new Project();
		project.createProject(command, staff);
		
		// 3. 保存
		dao.save(project);
	}

	
	public Project getAndCheckProject(String id) throws IMAGEException {

		if (id == null)
			throw new IMAGEException(
					IMAGEException.PROJECT_ID_IS_NULL, "工程ID不能为空");

		Project project = getDao().get(id);

		if (project == null)
			throw new IMAGEException(
					IMAGEException.PROJECT_NOT_EXISTS, "工程不存在");

		return project;
	}
	

	public void modifyProject(ModifyProjectCommand command) throws IMAGEException {
		
		// 1. 获取到原对象
		Project project = getAndCheckProject(command.getProjectId());
		
		// 2. 检查工程操作员是否已占用
		if (StringUtils.isBlank(command.getStaffId()) 
				|| !project.getStaff().getId().equals(command.getStaffId())) {
			checkStaff(command.getStaffId());
		}
		
		// 3. 编辑属性
		project.modifyProject(command);
		
		// 4. 更新
		getDao().update(project);
	}

	
	public void removeProject(RemoveProjectCommand command) {
		
		if (StringUtils.isNotBlank(command.getProjectIds())) {
			ProjectQo qo = new ProjectQo();
			qo.setIds(command.getProjectIdList());
			List<Project> list = getDao().queryList(qo);
			for (Project project : list) {
				getDao().delete(project);
			}
		}
	}

}
