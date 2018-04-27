package zzpl.app.service.local.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import zzpl.app.dao.user.RoleDAO;
import zzpl.domain.model.user.Role;
import zzpl.pojo.qo.user.RoleQO;
import hg.common.component.BaseServiceImpl;

@Service
@Transactional
public class RoleService extends BaseServiceImpl<Role, RoleQO, RoleDAO> {

	@Autowired
	private RoleDAO roleDAO;

	@Override
	protected RoleDAO getDao() {
		return roleDAO;
	}

}
