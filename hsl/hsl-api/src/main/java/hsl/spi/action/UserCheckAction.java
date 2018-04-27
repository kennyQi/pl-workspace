package hsl.spi.action;

import hg.common.util.BeanMapperUtils;
import hg.log.util.HgLogger;
import hsl.api.base.ApiRequest;
import hsl.api.base.ApiResponse;
import hsl.api.v1.request.qo.user.UserCheckQO;
import hsl.api.v1.response.user.UserCheckResponse;
import hsl.pojo.dto.user.UserDTO;
import hsl.pojo.exception.UserException;
import hsl.pojo.qo.user.HslUserQO;
import hsl.spi.action.constant.Constants;
import hsl.spi.inter.user.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;

@Component("userCheckAction")
public class UserCheckAction implements HSLAction {
	@Autowired
	private UserService userService;
	@Autowired
	private HgLogger hgLogger;
	@Override
	public String execute(ApiRequest apiRequest) {
		UserCheckQO userCheckQO = JSON.parseObject(apiRequest.getBody().getPayload(), UserCheckQO.class);
		hgLogger.info("liujz", "用户登录请求"+JSON.toJSONString(userCheckQO));
		return userCheck(userCheckQO);
	}

	/**
	 * 校验用户名密码
	 */
	private String userCheck(UserCheckQO userCheckQO) {

		//hsl.api.v1.request.qo.user.UserCheckQO转hsl.pojo.qo.user.HslUserQO
		HslUserQO userQO=BeanMapperUtils.map(userCheckQO, HslUserQO.class);
		
		UserCheckResponse userCheckResponse = new UserCheckResponse();
		try {
			UserDTO userDTO = userService.userCheck(userQO);
			userCheckResponse.setUserDTO(userDTO);
			userCheckResponse.setResult(ApiResponse.RESULT_CODE_OK);
			userCheckResponse.setMessage("校验成功");
			hgLogger.info("chenxy", userDTO.getAuthInfo().getLoginName()+"用户登录成功");
		} catch (UserException e) {
			userCheckResponse.setResult(Constants.exceptionMap.get(e.getCode()));
			userCheckResponse.setMessage(e.getMessage());
			hgLogger.error("chenxy",userCheckQO.getLoginName()+"用户登录失败>>"+e.getMessage());
		}
		hgLogger.info("liujz", "用户登录结果"+JSON.toJSONString(userCheckResponse));
		return JSON.toJSONString(userCheckResponse);
	}

}
