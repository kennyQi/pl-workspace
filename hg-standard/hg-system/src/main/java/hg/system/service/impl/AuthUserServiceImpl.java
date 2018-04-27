package hg.system.service.impl;

import hg.common.component.BaseServiceImpl;
import hg.system.dao.AuthUserDao;
import hg.system.model.auth.AuthUser;
import hg.system.qo.AuthUserQo;
import hg.system.service.AuthUserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @类功能说明：用户service_实现
 * @类修改者：zzb
 * @修改日期：2014年12月1日下午1:49:49
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @作者：zzb
 * @创建时间：2014年12月1日下午1:49:49
 */
@Service
@Transactional
public class AuthUserServiceImpl extends
		BaseServiceImpl<AuthUser, AuthUserQo, AuthUserDao> implements
		AuthUserService {

	/**
	 * 员工dao
	 */
	@Autowired
	private AuthUserDao 	authUserDao;
	
	@Override
	protected AuthUserDao getDao() {
		return authUserDao;
	}

}
