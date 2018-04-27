package zzpl.app.service.spi.user;

import hg.common.page.Pagination;
import java.util.List;
import org.springframework.stereotype.Service;
import zzpl.pojo.dto.user.UserDTO;
import zzpl.pojo.qo.user.UserQO;
import zzpl.spi.inter.user.UserService;

@Service
public class UserServiceImpl implements UserService {

	@Override
	public UserDTO queryUnique(UserQO qo) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<UserDTO> queryList(UserQO qo) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Pagination queryPagination(Pagination pagination) {
		// TODO Auto-generated method stub
		return null;
	}
	
}
