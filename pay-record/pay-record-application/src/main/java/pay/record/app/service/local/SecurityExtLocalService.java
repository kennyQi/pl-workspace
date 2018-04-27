package pay.record.app.service.local;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import pay.record.app.dao.SecurityExtDao;

@Service
@Transactional
public class SecurityExtLocalService {
	
	@Autowired
	private SecurityExtDao securityExtDao;

	/**
	 * @方法功能说明：查询这个角色有几个用户在用
	 * @修改者名字：zhurz
	 * @修改时间：2014-12-2下午5:29:26
	 * @修改内容：
	 * @参数：@param roleId
	 * @参数：@return
	 * @return:Integer
	 * @throws
	 */
	public Integer roleUseCount(String roleId) {
		return securityExtDao.roleUseCount(roleId);
	}
}
