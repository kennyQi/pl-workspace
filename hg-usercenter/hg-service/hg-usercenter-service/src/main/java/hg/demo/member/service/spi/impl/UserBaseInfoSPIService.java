package hg.demo.member.service.spi.impl;

import java.sql.Timestamp;
import java.util.Date;

import javax.transaction.Transactional;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import hg.demo.member.common.domain.model.UserBaseInfo;
import hg.demo.member.common.domain.result.Result;
import hg.demo.member.common.spi.UserBaseInfoSPI;
import hg.demo.member.common.spi.command.userinfo.ModifyPasswordCommand;
import hg.demo.member.common.spi.command.userinfo.ModifyUserInfoCommand;
import hg.demo.member.common.spi.qo.UserBaseInfoSQO;
import hg.demo.member.service.dao.hibernate.UserBaseInfoDAO;
import hg.demo.member.service.domain.manager.UserBaseInfoManager;
import hg.demo.member.service.qo.hibernate.UserBaseInfoQO;
import hg.framework.common.model.Pagination;
import hg.framework.service.component.base.BaseService;

/**
 * 
* <p>Title: UserBaseInfoSPIService</p>
* <p>Description: </p>
* <p>Company: </p> 
* @author xuwangwei
* @date 2016年6月27日 下午2:12:17
 */
@Transactional
@Service("userBaseInfoSPIService")
public class UserBaseInfoSPIService extends BaseService implements UserBaseInfoSPI {

	@Autowired
	private UserBaseInfoDAO userBaseInfoDAO;
	
	@Override
	public UserBaseInfo queryUserInfo(String id) {
		UserBaseInfoQO ubiQO = new UserBaseInfoQO();
		ubiQO.setId(id);
		UserBaseInfo userBaseInfo = userBaseInfoDAO.queryFirst(ubiQO);
		return userBaseInfo;
	}

	@Override
	public Pagination<UserBaseInfo> queryUserListPage(UserBaseInfoSQO sqo) {
		return userBaseInfoDAO.queryPagination(UserBaseInfoQO.build(sqo));
	}

	@Override
	public Result<UserBaseInfo> saveUserInfo(ModifyUserInfoCommand command) {
		Result<UserBaseInfo> result = new Result<>();
		UserBaseInfo userBaseInfo = null;
		if(StringUtils.isNotEmpty(command.getId())){
			userBaseInfo = this.queryUserInfo(command.getId());
			if(userBaseInfo == null){
				result.setCode(-1);
				result.setMsg("用户ID不存在！");
				return result;
			}
		}else if(StringUtils.isEmpty(command.getUserName())){
			result.setCode(-2);
			result.setMsg("用户名不能為空！");
			return result;
		}else{
			UserBaseInfoSQO sqo = new UserBaseInfoSQO();
			sqo.setUserName(command.getUserName());
			Pagination<UserBaseInfo> userpage = this.queryUserListPage(sqo);
			if(userpage.getTotalCount()>0){
				result.setCode(-1);
				result.setMsg("用户名已存在！");
				return result;
			}
		}
		
		userBaseInfo = userBaseInfoDAO.saveOrUpdate(new UserBaseInfoManager(userBaseInfo).create(command).get());
		if(userBaseInfo==null || userBaseInfo.getId()==null){
			result.setMsg("操作成功！");
		}
		result.setResult(userBaseInfo);
		return result;
	}

	@Override
	public Result<UserBaseInfo> changePassword(ModifyPasswordCommand command) {
		Result<UserBaseInfo> result = new Result<>();
		UserBaseInfo userBaseInfo = null;
		if(StringUtils.isNotEmpty(command.getId())){
			userBaseInfo = this.queryUserInfo(command.getId());
			if(userBaseInfo == null){
				result.setCode(-1);
				result.setMsg("用户ID不存在！");
				return result;
			}else{
				if(command.getOrginalPass()==null || command.getNewPass()==null){
					result.setCode(-1);
					result.setMsg("密码不能为空！");
					return result;
				}else{
					if(!command.getOrginalPass().equals(userBaseInfo.getPassword())){
						result.setCode(-1);
						result.setMsg("原始密码不正确！");
						return result;
					}
				}
			}
			userBaseInfo.setPassword(command.getNewPass());
			userBaseInfo.setUpdateTime(new Timestamp(new Date().getTime()));
			userBaseInfo = userBaseInfoDAO.saveOrUpdate(userBaseInfo);
			if(userBaseInfo==null || userBaseInfo.getId()==null){
				result.setMsg("密码修改失败！");
				return result;
			}else{
				result.setMsg("密码修改成功！");
				return result;
			}
		}else{
			result.setCode(-1);
			result.setMsg("用户ID不存在！");
			return result;
		}
		
	}

	@Override
	public Result<UserBaseInfo> active(String id, int status) {
		Result<UserBaseInfo> result = new Result<>();
		UserBaseInfo userBaseInfo = this.queryUserInfo(id);
		if(userBaseInfo == null){
			result.setCode(-1);
			result.setMsg("用户ID不存在！");
			return result;
		}
		userBaseInfo.setStatus(status);
		userBaseInfo.setUpdateTime(new Timestamp(new Date().getTime()));
		userBaseInfo = userBaseInfoDAO.saveOrUpdate(userBaseInfo);
		String msgpre = "";
		switch(status){
			case -1:
				msgpre = "冻结"; 
				break;
			case 0:
				msgpre = "未激活"; 
				break;
			case 1:
				msgpre = "激活"; 
				break;
			default:
				msgpre = "设为未知状态"+status; 
				break;
		}
		if(userBaseInfo==null || userBaseInfo.getId()==null){
			result.setCode(-2);
			result.setMsg(msgpre +"失败！");
		}else{
			result.setMsg(msgpre +"成功！");
		}
		return result;
	}

}
