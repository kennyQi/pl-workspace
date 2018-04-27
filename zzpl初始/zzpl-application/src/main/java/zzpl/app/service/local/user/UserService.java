package zzpl.app.service.local.user;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import zzpl.app.dao.user.UserDAO;
import zzpl.domain.model.user.User;
import zzpl.pojo.dto.user.UserDTO;
import zzpl.pojo.qo.user.UserQO;
import hg.common.component.BaseServiceImpl;
import hg.common.util.BeanMapperUtils;

@Service
@Transactional
public class UserService extends BaseServiceImpl<User, UserQO, UserDAO> {

	@Autowired
	private UserDAO userDAO;

	/**
	 * 
	 * @方法功能说明：查询单条user
	 * @修改者名字：cangs
	 * @修改时间：2015年6月23日上午10:17:43
	 * @修改内容：
	 * @参数：@param userQO
	 * @参数：@return
	 * @return:UserDTO
	 * @throws
	 */
	public UserDTO queryUser(UserQO userQO) {
		User user = userDAO.queryUnique(userQO);
		UserDTO userDTO = BeanMapperUtils.getMapper().map(user, UserDTO.class);
		return userDTO;
	}

	/**
	 * 
	 * @方法功能说明：查询多条user
	 * @修改者名字：cangs
	 * @修改时间：2015年6月23日上午10:23:10
	 * @修改内容：
	 * @参数：@param userQO
	 * @参数：@return
	 * @return:List<UserDTO>
	 * @throws
	 */
	public List<UserDTO> queryUserList(UserQO userQO) {
		List<User> users = userDAO.queryList(userQO);
		List<UserDTO> userDTOs = new ArrayList<UserDTO>();
		for (User user : users) {
			UserDTO userDTO = BeanMapperUtils.getMapper().map(user,
					UserDTO.class);
			userDTOs.add(userDTO);
		}
		return userDTOs;
	}

	@Override
	protected UserDAO getDao() {
		return userDAO;
	}

}
