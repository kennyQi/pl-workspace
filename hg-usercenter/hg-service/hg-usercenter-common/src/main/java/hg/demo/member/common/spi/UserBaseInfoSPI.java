package hg.demo.member.common.spi;

import hg.demo.member.common.domain.model.UserBaseInfo;
import hg.demo.member.common.domain.result.Result;
import hg.demo.member.common.spi.command.userinfo.ModifyPasswordCommand;
import hg.demo.member.common.spi.command.userinfo.ModifyUserInfoCommand;
import hg.demo.member.common.spi.qo.UserBaseInfoSQO;
import hg.framework.common.base.BaseServiceProviderInterface;
import hg.framework.common.model.Pagination;

/**
 * 
* <p>Title: UserSPI</p>
* <p>Description: </p>
* <p>Company: </p> 
* @author xuww
* @date 2016年6月27日 上午11:45:09
 */
public interface UserBaseInfoSPI extends BaseServiceProviderInterface {
	UserBaseInfo queryUserInfo(String id);
	
	Pagination<UserBaseInfo> queryUserListPage(UserBaseInfoSQO sqo);
	
	Result<UserBaseInfo> saveUserInfo(ModifyUserInfoCommand saveUserInfoCommand);
	
	Result<UserBaseInfo> changePassword(ModifyPasswordCommand savePasswordCommand);
	
	Result<UserBaseInfo> active(String id, int status);
	
}
