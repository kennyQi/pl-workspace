package hsl.spi.action;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import hg.log.util.HgLogger;
import hsl.api.base.ApiRequest;
import hsl.api.base.ApiResponse;
import hsl.api.v1.request.qo.user.UserQO;
import hsl.api.v1.response.user.QueryUserResponse;
import hsl.pojo.dto.user.UserDTO;
import hsl.pojo.exception.UserException;
import hsl.pojo.qo.user.HslUserBindAccountQO;
import hsl.pojo.qo.user.HslUserQO;
import hsl.spi.action.constant.Constants;
import hsl.spi.inter.user.UserService;

import com.alibaba.fastjson.JSON;

@Component("queryUserAction")
public class QueryUserAction implements HSLAction{
	
	@Autowired
	private UserService userService;
	@Autowired
	private HgLogger hgLogger;
	
	@Override
	public String execute(ApiRequest apiRequest) {
		UserQO userQO=JSON.parseObject(apiRequest.getBody().getPayload(), UserQO.class);
		hgLogger.info("liujz", "查询用户请求"+JSON.toJSONString(userQO));
		return queryUser(userQO);
	}

	public String queryUser(UserQO userQO){
		//hsl.api.v1.request.qo.user.UserQO转hsl.pojo.qo.user.HslUserBindAccountQO
		HslUserBindAccountQO userBindAccountQO=new HslUserBindAccountQO();
		HslUserQO hslUserQO=new HslUserQO();
		hgLogger.info("chenxy",userQO.getLoginName()+"查询用户开始");
		if(StringUtils.isNotBlank(userQO.getUserId()))
			hslUserQO.setId(userQO.getUserId());
		
		if(StringUtils.isNotBlank(userQO.getLoginName()))
			hslUserQO.setLoginName(userQO.getLoginName());
		
		if(userQO.getType()!=null){
			hgLogger.info("chenxy",userQO.getLoginName()+"用户类型不为空的设置用户类型");
			userBindAccountQO.setAccountType(userQO.getType());
			
			if(StringUtils.isNotBlank(userQO.getBindAccountId()))
				userBindAccountQO.setBindAccountId(userQO.getBindAccountId());
			
			if(StringUtils.isNotBlank(userQO.getBindAccountName()))
				userBindAccountQO.setBindAccountName(userQO.getBindAccountName());
		}

		userBindAccountQO.setUserQO(hslUserQO);
		
		QueryUserResponse userResponse=new QueryUserResponse();
		try {
			UserDTO userDTO=userService.queryUser(userBindAccountQO);
			userResponse.setUserDTO(userDTO);
			userResponse.setResult(ApiResponse.RESULT_CODE_OK);
			userResponse.setMessage("用户查询成功");
			hgLogger.info("chenxy",userQO.getLoginName()+"查询用户成功");
		} catch (UserException e) {
			userResponse.setResult(Constants.exceptionMap.get(e.getCode()));
			hgLogger.error("chenxy",userQO.getLoginName()+"查询用户失败"+e.getMessage());
			userResponse.setMessage(e.getMessage());
		}
		hgLogger.info("liujz", "查询用户结果"+JSON.toJSONString(userResponse));
		return JSON.toJSONString(userResponse);
	}
}
