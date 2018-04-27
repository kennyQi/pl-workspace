package hsl.pojo.command.user;

import hsl.pojo.dto.user.UserDTO;

import java.util.ArrayList;
import java.util.List;
/**
 * 批量添加用户指令
 * @author Administrator
 *
 */
public class BatchRegisterUserCommand {
	
	List<UserDTO> users = new ArrayList<UserDTO>();

	public List<UserDTO> getUsers() {
		return users;
	}

	public void setUsers(List<UserDTO> users) {
		this.users = users;
	}
	
}
