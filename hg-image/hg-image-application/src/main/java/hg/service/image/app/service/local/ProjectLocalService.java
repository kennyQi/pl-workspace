package hg.service.image.app.service.local;

import hg.common.component.BaseServiceImpl;
import hg.service.image.app.dao.ProjectDao;
import hg.service.image.domain.model.Project;
import hg.service.image.domain.qo.ProjectLocalQo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @类功能说明：项目本地service类
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @作者：zzb
 * @创建时间：2014年12月21日 上午10:58:26
 */
@Service
@Transactional
public class ProjectLocalService extends BaseServiceImpl<Project, ProjectLocalQo, ProjectDao> {
	/**
	 * 工程dao
	 */
	@Autowired
	private ProjectDao dao;
	
	@Override
	protected ProjectDao getDao() {
		return dao;
	}
}