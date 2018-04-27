package zzpl.api.action.user;

import hg.common.util.BeanMapperUtils;
import hg.common.util.SysProperties;
import hg.log.util.HgLogger;

import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import zzpl.api.action.CommonAction;
import zzpl.api.client.base.ApiRequest;
import zzpl.api.client.base.ApiResponse;
import zzpl.api.client.dto.user.UserDTO;
import zzpl.api.client.request.user.QueryUserQO;
import zzpl.api.client.response.user.QueryUserResponse;
import zzpl.app.service.local.user.UserRoleService;
import zzpl.app.service.local.user.UserService;
import zzpl.domain.model.user.User;
import zzpl.domain.model.user.UserRole;
import zzpl.pojo.qo.user.UserRoleQO;

import com.alibaba.fastjson.JSON;

@Component("QueryUserAction")
public class QueryUserAction implements CommonAction{

	@Autowired
	private UserService userService;
	@Autowired
	private UserRoleService userRoleService;
	
	@Override
	public String execute(ApiRequest apiRequest) {
		QueryUserResponse queryUserResponse = new QueryUserResponse();
		try{
			QueryUserQO queryUserQO = JSON.parseObject(apiRequest.getBody().getPayload(), QueryUserQO.class);
			HgLogger.getInstance().info("cs", "【QueryUserAction】"+"queryUserQO:"+JSON.toJSONString(queryUserQO));
			User user = userService.get(queryUserQO.getUserID());
			UserDTO userDTO = BeanMapperUtils.map(user, UserDTO.class);
			UserRoleQO userRoleQO = new UserRoleQO();
			userRoleQO.setUserID(queryUserQO.getUserID());
			List<UserRole> userRoles = userRoleService.queryList(userRoleQO);
			List<String> roleList = new LinkedList<String>();
			for (UserRole userRole : userRoles) {
				roleList.add(userRole.getRole().getId());
			}
			userDTO.setRoleList(roleList);
			if(userDTO.getImage()!=null){
				userDTO.setImage(SysProperties.getInstance().get("fileUploadPath")+userDTO.getImage());
			}
			queryUserResponse.setUserDTO(userDTO);
			queryUserResponse.setResult(ApiResponse.RESULT_CODE_OK);
		}catch(Exception e){
			HgLogger.getInstance().info("cs", "【QueryUserAction】"+"异常，"+HgLogger.getStackTrace(e));
			queryUserResponse.setMessage("用户查询失败");
			queryUserResponse.setResult(ApiResponse.RESULT_CODE_FAIL);
		}
		HgLogger.getInstance().info("cs", "【ModifyUserAction】"+"queryUserResponse:"+JSON.toJSONString(queryUserResponse));
		return JSON.toJSONString(queryUserResponse);
	}

}
