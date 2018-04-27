package zzpl.app.service.local.user;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import zzpl.app.dao.user.UserRoleDAO;
import zzpl.domain.model.user.UserRole;
import zzpl.pojo.dto.user.UserRoleDTO;
import zzpl.pojo.qo.user.UserRoleQO;
import hg.common.component.BaseServiceImpl;
import hg.common.util.BeanMapperUtils;

@Service
@Transactional
public class UserRoleService extends
		BaseServiceImpl<UserRole, UserRoleQO, UserRoleDAO> {

	@Autowired
	private UserRoleDAO userRoleDAO;

	/**
	 * 
	 * @方法功能说明：查询单条userRole
	 * @修改者名字：cangs
	 * @修改时间：2015年6月23日上午10:39:41
	 * @修改内容：
	 * @参数：@param userRoleQO
	 * @参数：@return
	 * @return:UserRoleDTO
	 * @throws
	 */
	public UserRoleDTO queryUserRole(UserRoleQO userRoleQO) {
		UserRole userRole = userRoleDAO.queryUnique(userRoleQO);
		UserRoleDTO userRoleDTO = BeanMapperUtils.getMapper().map(userRole,
				UserRoleDTO.class);
		return userRoleDTO;
	}

	/**
	 * 
	 * @方法功能说明：查询多条userRole
	 * @修改者名字：cangs
	 * @修改时间：2015年6月23日上午11:15:29
	 * @修改内容：
	 * @参数：@param userRoleQO
	 * @参数：@return
	 * @return:List<UserRoleDTO>
	 * @throws
	 */
	public List<UserRoleDTO> queryUserRoleList(UserRoleQO userRoleQO) {
		List<UserRole> userRoles = userRoleDAO.queryList(userRoleQO);
		List<UserRoleDTO> userRoleDTOs = new ArrayList<UserRoleDTO>();
		for (UserRole userRole : userRoles) {
			UserRoleDTO userRoleDTO = BeanMapperUtils.getMapper().map(userRole,
					UserRoleDTO.class);
			userRoleDTOs.add(userRoleDTO);
		}
		return userRoleDTOs;
	}

	@Override
	protected UserRoleDAO getDao() {
		return userRoleDAO;
	}

}
