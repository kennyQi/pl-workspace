package hsl.app.service.spi.user;

import hg.common.util.BeanMapperUtils;
import hg.common.util.MD5HashUtil;
import hg.common.util.UUIDGenerator;
import hg.log.po.clickrecord.ClickRecord;
import hg.log.util.HgLogger;
import hsl.app.common.util.ClientKeyUtil;
import hsl.app.component.base.BaseSpiServiceImpl;
import hsl.app.service.local.user.UserLocalService;
import hsl.domain.model.user.User;
import hsl.domain.model.user.UserBindAccount;
import hsl.pojo.command.BindWXCommand;
import hsl.pojo.command.CheckRegisterMailCommand;
import hsl.pojo.command.CheckValidCodeCommand;
import hsl.pojo.command.SendMailCodeCommand;
import hsl.pojo.command.SendValidCodeCommand;
import hsl.pojo.command.UpdateBindMobileCommand;
import hsl.pojo.command.UpdateCompanyUserCommand;
import hsl.pojo.command.UpdateHeadImageCommand;
import hsl.pojo.command.UserClickRecordCommand;
import hsl.pojo.command.UserRegisterCommand;
import hsl.pojo.command.UserUpdatePasswordCommand;
import hsl.pojo.command.user.BatchRegisterUserCommand;
import hsl.pojo.dto.mp.MPSimpleDTO;
import hsl.pojo.dto.user.UserBindAccountDTO;
import hsl.pojo.dto.user.UserDTO;
import hsl.pojo.exception.UserException;
import hsl.pojo.qo.mp.HslUserClickRecordQO;
import hsl.pojo.qo.user.HslSMSCodeQO;
import hsl.pojo.qo.user.HslUserBindAccountQO;
import hsl.pojo.qo.user.HslUserQO;
import hsl.spi.inter.user.UserService;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import slfx.api.base.ApiRequest;
import slfx.api.base.SlfxApiClient;
import slfx.api.v1.response.mp.MPQueryScenicSpotsResponse;

import com.alibaba.fastjson.JSON;
@Service
public class UserServiceImpl extends BaseSpiServiceImpl<UserDTO, HslUserQO, UserLocalService> implements UserService {

	@Autowired
	private UserLocalService userLocalService;
	@Autowired
	private SlfxApiClient slfxApiClient;
	@Autowired
	private HgLogger hgLogger;
	
	@Override
	protected UserLocalService getService() {
		return userLocalService;
	}

	@Override
	protected Class<UserDTO> getDTOClass() {
		return UserDTO.class;
	}

	public void resetPassword(UserUpdatePasswordCommand command) throws  UserException{
		command.setNewPwd(MD5HashUtil.toMD5(command.getNewPwd()));
		userLocalService.UserRetPassword(command);
	}
	
	public void createUserClickRecord(UserClickRecordCommand command){
		userLocalService.createUserClickRecord(command);
	}
	
	public List<MPSimpleDTO> queryUserClickRecord(HslUserClickRecordQO userClickRecordQO){
		List<MPSimpleDTO> list =new ArrayList<MPSimpleDTO>();
		
		if (StringUtils.isNotBlank(userClickRecordQO.getUserId())) {
			List<ClickRecord> clickRecords = userLocalService
					.queryUserClickRecord(userClickRecordQO);

			// 转换成slfx-api-client MPScenicSpotsQO
			slfx.api.v1.request.qo.mp.MPScenicSpotsQO qo = new slfx.api.v1.request.qo.mp.MPScenicSpotsQO();

			for (ClickRecord clickRecord : clickRecords) {
				qo.setScenicSpotId(clickRecord.getScenicSpotId());
				// 发送请求
				hgLogger.info("liujz",
						"对slfx-api发送景区查询请求" + JSON.toJSONString(qo));
				ApiRequest apiRequest = new ApiRequest("MPQueryScenicSpots",
						ClientKeyUtil.FROM_CLIENT_KEY, "127.0.0.1",
						UUIDGenerator.getUUID(), qo);
				MPQueryScenicSpotsResponse response = slfxApiClient.send(
						apiRequest, MPQueryScenicSpotsResponse.class);
				hgLogger.info(
						"liujz",
						"slfx-api返回景区查询结果"
								+ JSON.toJSONString(response.getResult()));

				MPSimpleDTO dto = new MPSimpleDTO();
				if(response.getScenicSpots()!=null&&response.getScenicSpots().size()>0){
					dto.setPrice(response.getScenicSpots().get(0)
							.getTcScenicSpotInfo().getAmountAdvice());
					dto.setScenicSpotId(response.getScenicSpots().get(0).getId());
					dto.setScenicSpotName(response.getScenicSpots().get(0)
							.getScenicSpotBaseInfo().getName());
					list.add(dto);
				}
			}

		}
		return list;
	}
	
	public UserDTO register(UserRegisterCommand command,String clientKey) throws UserException{
		hgLogger.info("chenxy",command.getLoginName()+"用户注册service开始");
		User user=userLocalService.register(command,clientKey);
		UserDTO userDTO=BeanMapperUtils.map(user, UserDTO.class);
		userDTO.getAuthInfo().setPassword(null);
		if(userDTO.getBaseInfo()!=null){
			userDTO.getBaseInfo().setSource(null);
			userDTO.getBaseInfo().setCreateTime(null);
		}
		if(userDTO.getStatus()!=null){
			userDTO.setStatus(null);
		}
		return userDTO;
	}
	
	public void batchRegister(BatchRegisterUserCommand  command) throws Exception{
		userLocalService.batchRegisterUsers(command);
	}
	
	public String sendValidCode(SendValidCodeCommand command) throws UserException{
			return userLocalService.sendValidCode(command);
	}
	
	public String sendMailValidCode(SendValidCodeCommand command) throws UserException{
		return userLocalService.sendValidCode(command);
	}
	
	@Override
	public UserDTO userCheck(HslUserQO userQO) throws  UserException{
		User user=userLocalService.userCheck(userQO);
		UserDTO userDTO=BeanMapperUtils.map(user, UserDTO.class);
		userDTO.getAuthInfo().setPassword(null);
		if(userDTO.getBaseInfo()!=null){
			userDTO.getBaseInfo().setSource(null);
			userDTO.getBaseInfo().setCreateTime(null);
		}
		if(userDTO.getStatus()!=null){
			userDTO.setStatus(null);
		}
		return userDTO;
	}
	
	/**
	 * 查询用户
	 * @param userQO
	 * @return
	 */
	public UserDTO queryUser(HslUserBindAccountQO userQO) throws UserException{
		User user=userLocalService.queryUser(userQO);
		HgLogger.getInstance().info("yuqz","特殊日志");
		UserDTO userDTO=BeanMapperUtils.map(user, UserDTO.class);
		return userDTO;
	}
	
	public UserDTO updatePassword(UserUpdatePasswordCommand command) throws  UserException{
		User user=userLocalService.UserUpdatePassword(command);
		UserDTO userDTO=BeanMapperUtils.map(user, UserDTO.class);
		userDTO.getAuthInfo().setPassword(null);
		if(userDTO.getBaseInfo()!=null){
			userDTO.getBaseInfo().setSource(null);
			userDTO.getBaseInfo().setCreateTime(null);
		}
		return userDTO;
	}
	
	/**
	 * 绑定微信帐号
	 * @param command
	 * @return
	 */
	public UserBindAccountDTO bindWXCommand(BindWXCommand command) throws UserException{
		UserBindAccount userBindAccount=userLocalService.bindWX(command);
		UserBindAccountDTO userBindAccountDTO=BeanMapperUtils.map(userBindAccount, UserBindAccountDTO.class);
		userBindAccountDTO.getUser().getAuthInfo().setPassword(null);
		if(userBindAccountDTO.getUser().getBaseInfo()!=null){
			userBindAccountDTO.getUser().getBaseInfo().setSource(null);
			userBindAccountDTO.getUser().getBaseInfo().setCreateTime(null);
		}
		if(userBindAccountDTO.getUser().getStatus()!=null){
			userBindAccountDTO.getUser().setStatus(null);
		}
		return userBindAccountDTO;
	}
	
	public UserDTO updateCompanyUser(UpdateCompanyUserCommand command){
		User user=userLocalService.updateCompanyUser(command);
		UserDTO userDTO=BeanMapperUtils.map(user, UserDTO.class);
		userDTO.getAuthInfo().setPassword(null);
		if(userDTO.getBaseInfo()!=null){
			userDTO.getBaseInfo().setSource(null);
			userDTO.getBaseInfo().setCreateTime(null);
		}
		if(userDTO.getStatus()!=null){
			userDTO.setStatus(null);
		}
		return userDTO;
	}
	
	/**
	 * 查询验证码
	 * @param hslSMSCodeQO
	 * @return
	 */
	public String querySendValidCode(HslSMSCodeQO hslSMSCodeQO)throws UserException{
		return userLocalService.querySendValidCode(hslSMSCodeQO);
	}
	
	/**
	 * 修改绑定手机
	 * @param command
	 * @return
	 */
	public UserDTO updateBindMobile(UpdateBindMobileCommand command){
		User user=userLocalService.updateBindMobile(command);
		UserDTO userDTO=BeanMapperUtils.map(user, UserDTO.class);
		userDTO.getAuthInfo().setPassword(null);
		if(userDTO.getBaseInfo()!=null){
			userDTO.getBaseInfo().setSource(null);
			userDTO.getBaseInfo().setCreateTime(null);
		}
		if(userDTO.getStatus()!=null){
			userDTO.setStatus(null);
		}
		return userDTO;
	}

	/**
	 * 修改头像
	 * @param command
	 * @return
	 */
	public UserDTO updateHeadImage(UpdateHeadImageCommand command) {
		User user = userLocalService.updateHeadImage(command);
		UserDTO userDTO = BeanMapperUtils.map(user, UserDTO.class);
		userDTO.getAuthInfo().setPassword(null);
		if(userDTO.getBaseInfo()!=null){
			userDTO.getBaseInfo().setSource(null);
			userDTO.getBaseInfo().setCreateTime(null);
		}
		if(userDTO.getStatus()!=null){
			userDTO.setStatus(null);
		}
		return userDTO;
	}

	@Override
	public String sendMailValidCode(SendMailCodeCommand command)throws UserException {
		return userLocalService.sendMailValidCode(command);
	}
	
	@Override
	public UserDTO checkRegisterMail(CheckRegisterMailCommand command) throws UserException {
		User user=userLocalService.checkRegisterMail(command);
		UserDTO userDTO = BeanMapperUtils.map(user, UserDTO.class);
		return userDTO;
	}
	
	public String sendRegisterMail(SendMailCodeCommand command) throws UserException{
		return userLocalService.sendRegisterMail(command);
	}

	@Override
	public void checkValidCode(CheckValidCodeCommand command) throws UserException {
		userLocalService.checkValidCode(command);
	}

	@Override
	public Integer queryCount(HslUserQO qo) {
		return userLocalService.queryCount(qo);
	}
}
