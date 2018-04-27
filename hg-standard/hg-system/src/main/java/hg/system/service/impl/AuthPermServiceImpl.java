package hg.system.service.impl;

import hg.common.component.BaseServiceImpl;
import hg.system.command.perm.CreateAuthPermCommand;
import hg.system.command.perm.ModifyAuthPermCommand;
import hg.system.command.perm.RemoveAuthPermCommand;
import hg.system.dao.AuthPermDao;
import hg.system.exception.HGException;
import hg.system.model.auth.AuthPerm;
import hg.system.qo.AuthPermQo;
import hg.system.service.AuthPermService;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 
 * @类功能说明：权限_资源service实现
 * @类修改者：zzb
 * @修改日期：2014年10月31日上午11:29:29
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @作者：zzb
 * @创建时间：2014年10月31日上午11:29:29
 *
 */
@Service
@Transactional
public class AuthPermServiceImpl extends BaseServiceImpl<AuthPerm, AuthPermQo, AuthPermDao>
		implements AuthPermService {

	/**
	 * 资源dao
	 */
	@Autowired
	private AuthPermDao authPermDao;
	
	@Override
	public void createAuthPerm(CreateAuthPermCommand command) throws HGException {
		
		if(command == null)
			throw new HGException(
					HGException.AUTH_PERM_COMMAND_NULL, "添加异常！");

		AuthPerm authPerm = new AuthPerm();
		authPerm.createAuthPerm(command);
		
		getDao().save(authPerm);
	}
	
	@Override
	public void modifyAuthPerm(ModifyAuthPermCommand command) throws HGException {
		
		if(command == null || StringUtils.isBlank(command.getPermId()))
			throw new HGException(
					HGException.AUTH_PERM_COMMAND_NULL, "编辑异常！");
		
		AuthPerm authPerm = get(command.getPermId());
		if(authPerm == null) 
			throw new HGException(
					HGException.AUTH_PERM_NOT_EXISTS, "编辑异常！");

		authPerm.modifyAuthPerm(command);
		
		getDao().update(authPerm);
	}
	
	@Override
	public void removeAuthPerm(RemoveAuthPermCommand command) {
		
		if (StringUtils.isNotBlank(command.getPermIds())) {
			AuthPermQo qo = new AuthPermQo();
			qo.setIds(command.getPermIdList());
			List<AuthPerm> list = getDao().queryList(qo);
			for (AuthPerm authPerm : list) {
				authPermDao.delete(authPerm);
			}
		}
	}
	
	@Override
	protected AuthPermDao getDao() {
		return authPermDao;
	}

}
