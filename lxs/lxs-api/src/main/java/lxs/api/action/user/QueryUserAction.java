package lxs.api.action.user;

import java.util.Map;

import hg.log.util.HgLogger;
import lxs.api.action.LxsAction;
import lxs.api.base.ApiRequest;
import lxs.api.base.ApiResponse;
import lxs.api.v1.dto.ImageDTO;
import lxs.api.v1.dto.user.UserAuthInfoDTO;
import lxs.api.v1.dto.user.UserBaseInfoDTO;
import lxs.api.v1.dto.user.UserContactInfoDTO;
import lxs.api.v1.dto.user.UserDTO;
import lxs.api.v1.dto.user.UserStatusDTO;
import lxs.api.v1.request.qo.user.UserQO;
import lxs.api.v1.response.user.QueryUserResponse;
import lxs.app.service.user.LxsUserService;
import lxs.domain.model.user.LxsUser;
import lxs.pojo.exception.user.UserException;
import lxs.pojo.qo.user.user.LxsUserQO;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;

@Component("QueryUserAction")
public class QueryUserAction implements LxsAction {

	@Autowired
	private LxsUserService userService;
	@Autowired
	private HgLogger hgLogger;

	@Override
	public String execute(ApiRequest apiRequest) {
		HgLogger.getInstance().info("lxs_dev", "【QueryUserAction】"+ "进入action");
		UserQO apiUserQO = JSON.parseObject(apiRequest.getBody().getPayload(),
				UserQO.class);

		// lxs.api.v1.request.qo.user.UserQO转lxs.pojo.qo.user.UserQO
		LxsUserQO userQO = new LxsUserQO();
		HgLogger.getInstance().info("lxs_dev", "【QueryUserAction】"+userQO.getLoginName() + "查询用户开始");
		if (StringUtils.isNotBlank(apiUserQO.getUserId())) {
			userQO.setId(apiUserQO.getUserId());
		}

		if (StringUtils.isNotBlank(apiUserQO.getLoginName())) {
			userQO.setLoginName(apiUserQO.getLoginName());
		}

		if (StringUtils.isNotBlank(apiUserQO.getMobile())) {
			userQO.setMobile(apiUserQO.getMobile());
		}
		
		QueryUserResponse userResponse = new QueryUserResponse();
		try {
			LxsUser user = userService.queryUnique(userQO);
			if (user == null) {
				throw new UserException(UserException.USER_NOT_FOUND, "用户不存在");
			}
			userResponse.setUser(QueryUserAction.userToUserDTO(user));
			userResponse.setResult(ApiResponse.RESULT_CODE_OK);
			userResponse.setMessage("用户查询成功");
			HgLogger.getInstance().info("lxs_dev", "【QueryUserAction】"+userQO.getLoginName() + "查询用户成功");

		} catch (UserException e) {
			userResponse.setResult(e.getCode());
			hgLogger.error("lxs_dev",
					userQO.getLoginName() + "查询用户失败" + e.getMessage());
			userResponse.setMessage(e.getMessage());
		}
		HgLogger.getInstance().info("lxs_dev", "【QueryUserAction】"+"查询用户结果" + JSON.toJSONString(userResponse));
		return JSON.toJSONString(userResponse);
	}
	
	public static UserDTO userToUserDTO(LxsUser user){
		UserDTO userDTO = new UserDTO();
		if(user.getAuthInfo()!=null){
			UserAuthInfoDTO uaDTO = new UserAuthInfoDTO();
			uaDTO.setLoginName(user.getAuthInfo().getLoginName());	
			uaDTO.setUserId(user.getId());
			userDTO.setAuthInfo(uaDTO);
		}
		
		if(user.getBaseInfo()!=null){
			UserBaseInfoDTO ubDTO = new UserBaseInfoDTO();
			if(user.getBaseInfo().getBirthday() != null){
				ubDTO.setBirthday(user.getBaseInfo().getBirthday());
			}
			if(user.getBaseInfo().getCreateTime() != null){
				ubDTO.setCreateTime(user.getBaseInfo().getCreateTime());
			}
			if(user.getBaseInfo().getGender() != null){
				ubDTO.setGender(user.getBaseInfo().getGender());
			}
			if(user.getBaseInfo().getIdCardNo()!=null){
				ubDTO.setIdCardNo(user.getBaseInfo().getIdCardNo());
			}
			if(user.getBaseInfo().getName()!=null){
				ubDTO.setName(user.getBaseInfo().getName());
			}
			if(user.getBaseInfo().getNickName()!=null){
				ubDTO.setNickName(user.getBaseInfo().getNickName());
			}
			userDTO.setBaseInfo(ubDTO);
		}

		if(user.getContactInfo()!=null){
			UserContactInfoDTO ucDTO = new UserContactInfoDTO();
			if(user.getContactInfo().getCityId()!=null){
				ucDTO.setCityId(user.getContactInfo().getCityId());
			}
			if(user.getContactInfo().getEmail()!=null){
				ucDTO.setEmail(user.getContactInfo().getEmail());
			}
			if(user.getContactInfo().getIm()!=null){
				ucDTO.setIm(user.getContactInfo().getIm());
			}
			if(user.getContactInfo().getMobile()!=null){
				ucDTO.setMobile(user.getContactInfo().getMobile());
			}
			if(user.getContactInfo().getProvinceId()!=null){
				ucDTO.setProvinceId(user.getContactInfo().getProvinceId());
			}
			if(user.getContactInfo().getProvinceName()!=null){
				ucDTO.setProvinceName(user.getContactInfo().getProvinceName());
			}
			if(user.getContactInfo().getCityName()!=null){
				ucDTO.setCityName(user.getContactInfo().getCityName());
			}
			userDTO.setContactInfo(ucDTO);
		}
		
		if(user.getStatus()!=null){
			UserStatusDTO usDTO = new UserStatusDTO();
			if(user.getStatus().getActivated() != null){
				usDTO.setActivated(user.getStatus().getActivated());
			}
			if(user.getStatus().getIsEmailChecked() != null){
				usDTO.setIsEmailChecked(user.getStatus().getIsEmailChecked());
			}
			if(user.getStatus().getIsTelChecked() != null){
				usDTO.setIsTelChecked(user.getStatus().getIsTelChecked());
			}
			if(user.getStatus().getLastLoginTime()!=null){
				usDTO.setLastLoginTime(user.getStatus().getLastLoginTime());
			}
			userDTO.setStatus(usDTO);
		}
		
		if(user.getTitleImage()!=null){
			ImageDTO imagedto = new ImageDTO();
			if(user.getTitleImage().getImageId()!=null){
				imagedto.setImageId(user.getTitleImage().getImageId());
			}
			if(user.getTitleImage().getTitle()!=null){
							imagedto.setTitle(user.getTitleImage().getTitle());
						}
			if(user.getTitleImage().getUrl()!=null){
				imagedto.setUrl(JSON.parseObject(user.getTitleImage().getUrl(), Map.class).get("default").toString());
			}
			userDTO.setTitleImage(imagedto);
		}
		return userDTO;
	}
}
