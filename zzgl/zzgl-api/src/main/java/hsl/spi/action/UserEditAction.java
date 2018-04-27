package hsl.spi.action;

import hg.common.util.BeanMapperUtils;
import hg.log.util.HgLogger;
import hsl.api.base.ApiRequest;
import hsl.api.base.ApiResponse;
import hsl.api.v1.request.command.user.UserEditCommand;
import hsl.api.v1.request.command.user.UserUpdatePasswordCommand;
import hsl.api.v1.response.user.UserEditResponse;
import hsl.domain.model.user.User;
import hsl.pojo.dto.user.UserDTO;
import hsl.pojo.qo.user.HslUserQO;
import hsl.spi.inter.user.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.dubbo.common.utils.StringUtils;
import com.alibaba.fastjson.JSON;

@Component("userEditAction")
public class UserEditAction implements HSLAction{
	@Autowired
	private UserService userService;
	@Autowired
	private HgLogger hgLogger;
	@Override
	public String execute(ApiRequest apiRequest) {
		UserEditCommand command = JSON.parseObject(
				apiRequest.getBody().getPayload(),
				UserEditCommand.class);
		hgLogger.info("yuqz", "用户更新个人资料请求"+JSON.toJSONString(command));
		return userEdit(command);
	}
	
	/**
	 * 
	 * @方法功能说明：修改资料
	 * @修改者名字：yuqz
	 * @修改时间：2014年10月17日上午11:04:13
	 * @修改内容：
	 * @参数：@param userEditCommand
	 * @参数：@return
	 * @return:String
	 * @throws
	 */
	private String userEdit(UserEditCommand command) {
		UserEditResponse userEditResponse = new UserEditResponse();
		if(command.getUserId() == null){
			userEditResponse.setResult(userEditResponse.RESULT_USERID_NULL);
			userEditResponse.setMessage("用户id不存在");
			return JSON.toJSONString(userEditResponse);
		}
		HslUserQO qo = new HslUserQO();
		qo.setId(command.getUserId());
		UserDTO user = new UserDTO();
		user = userService.queryUnique(qo);
		if(user == null){
			userEditResponse.setResult(userEditResponse.RESULT_USER_NOTEXIST);
			userEditResponse.setMessage("用户不存在");
			return JSON.toJSONString(userEditResponse);
		}
		command.setEmail(StringUtils.isBlank(command.getEmail()) ? user.getContactInfo().getEmail() : command.getEmail());
		command.setProvince(StringUtils.isBlank(command.getProvince()) ? user.getContactInfo().getProvinceId() : command.getProvince());
		command.setCity(StringUtils.isBlank(command.getCity()) ? user.getContactInfo().getCityId() : command.getCity());
		command.setNickName(StringUtils.isBlank(command.getNickName()) ? user.getBaseInfo().getNickName() : command.getNickName());
		command.setImage(StringUtils.isBlank(command.getImage()) ? user.getBaseInfo().getImage() : command.getImage());
		//hsl.api.v1.request.command.user.UserEditCommandd转hsl.spi.command.UpdateCompanyUserCommand
		hsl.pojo.command.UpdateCompanyUserCommand updateCompanyUserCommand = BeanMapperUtils.map(command, hsl.pojo.command.UpdateCompanyUserCommand.class);
		
		UserDTO userDTO = userService.updateCompanyUser(updateCompanyUserCommand);
		userEditResponse.setUserDTO(userDTO);
		userEditResponse.setResult(userEditResponse.RESULT_CODE_OK);
		userEditResponse.setMessage("修改资料成功");
		
		return JSON.toJSONString(userEditResponse);
	}

}
