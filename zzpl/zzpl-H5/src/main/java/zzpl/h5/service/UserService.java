package zzpl.h5.service;

import hg.common.util.Md5Util;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import zzpl.api.client.base.ApiClient;
import zzpl.api.client.base.ApiRequest;
import zzpl.api.client.request.user.AddFrequentFlyerCommand;
import zzpl.api.client.request.user.ChangePasswordCommand;
import zzpl.api.client.request.user.CheckSMSValidCommand;
import zzpl.api.client.request.user.DeleteFrequentFlyerCommand;
import zzpl.api.client.request.user.GetResetPasswordSMSValidCommand;
import zzpl.api.client.request.user.LoginCommand;
import zzpl.api.client.request.user.ModifyFrequentFlyerCommand;
import zzpl.api.client.request.user.ModifyUserCommand;
import zzpl.api.client.request.user.QueryFrequentFlyerQO;
import zzpl.api.client.request.user.QueryUserQO;
import zzpl.api.client.request.user.ResetPasswordCommand;
import zzpl.api.client.response.user.AddFrequentFlyerResponse;
import zzpl.api.client.response.user.CheckSMSValidResponse;
import zzpl.api.client.response.user.DeleteFrequentFlyerResponse;
import zzpl.api.client.response.user.GetResetPasswordSMSValidResponse;
import zzpl.api.client.response.user.LoginResponse;
import zzpl.api.client.response.user.ModifyFrequentFlyerResponse;
import zzpl.api.client.response.user.ModifyUserResponse;
import zzpl.api.client.response.user.QueryFrequentFlyerResponse;
import zzpl.api.client.response.user.QueryUserResponse;
import zzpl.api.client.response.user.ResetPasswordResponse;

@Service
public class UserService {

	@Autowired
	private ApiClient apiClient;

	public LoginResponse Login(LoginCommand loginCommand) {
		ApiRequest request = new ApiRequest("Login", "", loginCommand, "1.0");
		return apiClient.send(request, LoginResponse.class);
	}

	/**
	 * 查询个人信息
	 * 
	 * @param userId
	 * @return
	 */
	public QueryUserResponse getUserInfo(String userId) {
		QueryUserQO queryUserQO = new QueryUserQO();
		queryUserQO.setUserID(userId);
		ApiRequest request = new ApiRequest("QueryUser", userId, queryUserQO,"1.0");
		QueryUserResponse response = apiClient.send(request,QueryUserResponse.class);
		return response;
	}

	/**
	 * 修改个人信息
	 * 
	 * @param modifyUserCommand
	 */
	public ModifyUserResponse editPerson(ModifyUserCommand modifyUserCommand) {
		ApiRequest request = new ApiRequest("ModifyUser",modifyUserCommand.getUserID(), modifyUserCommand, "1.0");
		ModifyUserResponse response = apiClient.send(request,ModifyUserResponse.class);
		return response;

	}

	/**
	 * 查询所有联系人
	 * 
	 * @param queryFrequentFlyerQO
	 * @return
	 */
	public QueryFrequentFlyerResponse getFrequentFlyers(
			QueryFrequentFlyerQO queryFrequentFlyerQO) {
		ApiRequest request = new ApiRequest("QueryFrequentFlyer",queryFrequentFlyerQO.getUserID(), queryFrequentFlyerQO, "1.0");
		QueryFrequentFlyerResponse response = apiClient.send(request,QueryFrequentFlyerResponse.class);
		return response;

	}

	/**
	 * 
	 * @方法功能说明：新增联系人
	 * @修改者名字：cangs
	 * @修改时间：2015年12月8日下午1:41:19
	 * @修改内容：
	 * @参数：@param addFrequentFlyerCommand
	 * @参数：@return
	 * @return:AddFrequentFlyerResponse
	 * @throws
	 */
	public AddFrequentFlyerResponse addConnectUser(AddFrequentFlyerCommand addFrequentFlyerCommand) {
		ApiRequest request = new ApiRequest("AddFrequentFlyer",addFrequentFlyerCommand.getFrequentFlyer().getUserID(),addFrequentFlyerCommand, "1.0");
		AddFrequentFlyerResponse response = apiClient.send(request,AddFrequentFlyerResponse.class);
		return response;
	}

	/**
	 * 
	 * @方法功能说明：修改联系人
	 * @修改者名字：cangs
	 * @修改时间：2015年12月8日下午1:41:30
	 * @修改内容：
	 * @参数：@param modifyFrequentFlyerCommand
	 * @参数：@return
	 * @return:ModifyFrequentFlyerResponse
	 * @throws
	 */
	public ModifyFrequentFlyerResponse editConnectUser(ModifyFrequentFlyerCommand modifyFrequentFlyerCommand) {
		ApiRequest request = new ApiRequest("ModifyFrequentFlyer",modifyFrequentFlyerCommand.getFrequentFlyer().getUserID(),modifyFrequentFlyerCommand, "1.0");
		ModifyFrequentFlyerResponse response = apiClient.send(request,ModifyFrequentFlyerResponse.class);
		return response;
	}

	/**
	 * 
	 * @方法功能说明：删除联系人
	 * @修改者名字：cangs
	 * @修改时间：2015年12月8日下午1:42:38
	 * @修改内容：
	 * @参数：@param deleteFrequentFlyerCommand
	 * @参数：@return
	 * @return:DeleteFrequentFlyerResponse
	 * @throws
	 */
	public DeleteFrequentFlyerResponse delete(DeleteFrequentFlyerCommand deleteFrequentFlyerCommand) {
		ApiRequest request = new ApiRequest("DeleteFrequentFlyer",deleteFrequentFlyerCommand.getId(), deleteFrequentFlyerCommand,"1.0");
		DeleteFrequentFlyerResponse response = apiClient.send(request,DeleteFrequentFlyerResponse.class);
		return response;
	}

	/**
	 * 
	 * @方法功能说明：修改密码
	 * @修改者名字：cangs
	 * @修改时间：2015年12月8日下午1:42:47
	 * @修改内容：
	 * @参数：@param changePasswordCommand
	 * @参数：@return
	 * @return:LoginResponse
	 * @throws
	 */
	public LoginResponse changePwd(ChangePasswordCommand changePasswordCommand) {
		changePasswordCommand.setPassword(Md5Util.MD5(changePasswordCommand.getPassword()));
		changePasswordCommand.setNewPassword(Md5Util.MD5(changePasswordCommand.getNewPassword()));
		ApiRequest request = new ApiRequest("ChangePassword","3387c8e2ec3a477595a2266d2799ff1d", changePasswordCommand,"1.0");
		LoginResponse response = apiClient.send(request, LoginResponse.class);
		return response;
	}

	/**
	 * 
	 * @方法功能说明：获取验证码
	 * @修改者名字：cangs
	 * @修改时间：2015年12月8日下午1:42:56
	 * @修改内容：
	 * @参数：@param getResetPasswordSMSValidCommand
	 * @参数：@param httpServletRequest
	 * @参数：@return
	 * @return:GetResetPasswordSMSValidResponse
	 * @throws
	 */
	public GetResetPasswordSMSValidResponse getResetPasswordSMSValid(GetResetPasswordSMSValidCommand getResetPasswordSMSValidCommand,HttpServletRequest httpServletRequest){
		ApiRequest request = new ApiRequest("GetResetPasswordSMSValid","", getResetPasswordSMSValidCommand, "1.0");
		return apiClient.send(request, GetResetPasswordSMSValidResponse.class);
	}
	
	/**
	 * 
	 * @方法功能说明：校验短信
	 * @修改者名字：cangs
	 * @修改时间：2015年12月8日下午1:43:04
	 * @修改内容：
	 * @参数：@param checkSMSValidCommand
	 * @参数：@return
	 * @return:CheckSMSValidResponse
	 * @throws
	 */
	public CheckSMSValidResponse checkSMSValid(CheckSMSValidCommand checkSMSValidCommand){
		ApiRequest request = new ApiRequest("CheckSMSValid","", checkSMSValidCommand, "1.0");
		return apiClient.send(request, CheckSMSValidResponse.class);
	}
	
	/**
	 * 
	 * @方法功能说明：重置密码
	 * @修改者名字：cangs
	 * @修改时间：2015年12月8日下午1:43:16
	 * @修改内容：
	 * @参数：@param resetPasswordCommand
	 * @参数：@return
	 * @return:ResetPasswordResponse
	 * @throws
	 */
	public ResetPasswordResponse resetPassword(ResetPasswordCommand resetPasswordCommand){
		resetPasswordCommand.setNewPassword(Md5Util.MD5(resetPasswordCommand.getNewPassword()));
		ApiRequest request = new ApiRequest("ResetPassword","", resetPasswordCommand, "1.0");
		return apiClient.send(request, ResetPasswordResponse.class);
	}
}
