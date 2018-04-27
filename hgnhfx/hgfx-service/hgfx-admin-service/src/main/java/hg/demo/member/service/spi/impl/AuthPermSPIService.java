/**
 * @SecuritySPIService.java Create on 2016-5-23下午2:23:05
 * Copyright (c) 2012 by www.hg365.com。
 */
package hg.demo.member.service.spi.impl;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;




import hg.demo.member.common.domain.model.AuthPerm;
import hg.demo.member.common.spi.AuthPermSPI;
import hg.demo.member.common.spi.command.authPerm.CreateOrModifyAuthPermCommand;
import hg.demo.member.common.spi.command.authPerm.DeleteAuthPermCommand;
import hg.demo.member.common.spi.qo.AuthPermSQO;
import hg.demo.member.service.dao.hibernate.AuthPermDao;
import hg.demo.member.service.domain.manager.AuthPermManager;
import hg.demo.member.service.qo.hibernate.AuthPermQO;
import hg.framework.common.model.Pagination;
import hg.framework.service.component.base.BaseService;

/**
 * @类功能说明：
 * @类修改者：
 * @修改日期：2016-5-23下午2:23:05
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @作者：<a href=gaoce@hgtech365.com>gaoce</a>
 * @创建时间：2016-5-23下午2:23:05
 * @version：
 */
@Transactional
@Service("authPermSPIService")
public class AuthPermSPIService extends BaseService implements AuthPermSPI{
	@Autowired
	private AuthPermDao authPermDao;
	
	@Override
	public Pagination<AuthPerm> queryAuthPermPagination(AuthPermSQO sqo) {
		return authPermDao.queryPagination(AuthPermQO.build(sqo));
	}
	@Override
	public List<AuthPerm> queryAuthPerms(AuthPermSQO sqo) {
		return authPermDao.queryList(AuthPermQO.build(sqo));
	}
	@Override
	public AuthPerm queryAuthPerm(AuthPermSQO sqo) {
		return authPermDao.queryUnique(AuthPermQO.build(sqo));
	}
	@Override
	public AuthPerm create(CreateOrModifyAuthPermCommand command) {
		AuthPerm authPerm= new AuthPerm();
		return authPermDao.save(new AuthPermManager(authPerm).create(command, authPerm).get());
	}
	@Override
	public AuthPerm modify(CreateOrModifyAuthPermCommand command){
		AuthPerm authPerm = authPermDao.get(command.getPerm().getId());
		return authPermDao.update(new AuthPermManager(authPerm).modify(command, authPerm).get());
	}
	@Override
	public void delete(DeleteAuthPermCommand command) {
		AuthPerm authPerm= authPermDao.get(command.getId());
		authPermDao.delete(authPerm);
	}
}
