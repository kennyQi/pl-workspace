package zzpl.app.service.local.user;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import zzpl.app.dao.user.RoleMenuDAO;
import zzpl.domain.model.user.RoleMenu;
import zzpl.pojo.dto.user.RoleMenuDTO;
import zzpl.pojo.qo.user.RoleMenuQO;
import hg.common.component.BaseServiceImpl;
import hg.common.util.BeanMapperUtils;

@Service
@Transactional
public class RoleMenuService extends
		BaseServiceImpl<RoleMenu, RoleMenuQO, RoleMenuDAO> {

	@Autowired
	private RoleMenuDAO roleMenuDAO;

	/**
	 * 
	 * @方法功能说明：查询单条roleMenu
	 * @修改者名字：cangs
	 * @修改时间：2015年6月23日下午12:34:18
	 * @修改内容：
	 * @参数：@param roleMenuQO
	 * @参数：@return
	 * @return:RoleMenuDTO
	 * @throws
	 */
	public RoleMenuDTO queryRoleMenu(RoleMenuQO roleMenuQO) {
		RoleMenu roleMenu = roleMenuDAO.queryUnique(roleMenuQO);
		RoleMenuDTO roleMenuDTO = BeanMapperUtils.getMapper().map(roleMenu,
				RoleMenuDTO.class);
		return roleMenuDTO;
	}

	/**
	 * 
	 * @方法功能说明：查询多条roleMenu
	 * @修改者名字：cangs
	 * @修改时间：2015年6月23日下午12:34:39
	 * @修改内容：
	 * @参数：@param roleMenuQO
	 * @参数：@return
	 * @return:List<RoleMenuDTO>
	 * @throws
	 */
	public List<RoleMenuDTO> queryRoleMenuList(RoleMenuQO roleMenuQO) {
		List<RoleMenu> roleMenus = roleMenuDAO.queryList(roleMenuQO);
		List<RoleMenuDTO> roleMenuDTOs = new ArrayList<RoleMenuDTO>();
		for (RoleMenu roleMenu : roleMenus) {
			RoleMenuDTO roleMenuDTO = BeanMapperUtils.getMapper().map(roleMenu,
					RoleMenuDTO.class);
			roleMenuDTOs.add(roleMenuDTO);
		}
		return roleMenuDTOs;
	}

	@Override
	protected RoleMenuDAO getDao() {
		return null;
	}

}
