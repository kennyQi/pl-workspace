package hg.demo.member.service.domain.manager;

import java.sql.Timestamp;
import java.util.Date;

import org.springframework.beans.BeanUtils;

import com.alibaba.dubbo.common.utils.StringUtils;

import hg.demo.member.common.domain.model.UserBaseInfo;
import hg.demo.member.common.spi.command.userinfo.ModifyUserInfoCommand;
import hg.framework.common.base.BaseDomainManager;
import hg.framework.common.util.UUIDGenerator;

/**
 * 
* <p>Title: UserBaseInfoManager</p>
* <p>Description: </p>
* <p>Company: </p> 
* @author xuwangwei
* @date 2016年6月28日 上午9:17:31
 */
public class UserBaseInfoManager extends BaseDomainManager<UserBaseInfo> {

	public UserBaseInfoManager(UserBaseInfo entity) {
		super(entity);
	}

	/**
	 * 创建部门
	 *
	 * @param command 创建命令
	 * @param manager 部门主管
	 * @return
	 */
	public UserBaseInfoManager create(ModifyUserInfoCommand command) {
		BeanUtils.copyProperties(command, entity);
		if(StringUtils.isEmpty(entity.getId())){
			entity.setId(UUIDGenerator.getUUID());
			entity.setRegisterAppId(command.getAppId());
			entity.setCreateTime(new Timestamp(new Date().getTime()));
		}
		entity.setUpdateTime(new Timestamp(new Date().getTime()));
		return this;
	}

}
