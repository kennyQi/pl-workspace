package hsl.spi.inter.user;



import java.util.List;

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
import hsl.spi.inter.BaseSpiService;


/**
 * 汇商旅用户
 * @author liujz
 *
 */
public interface UserService extends BaseSpiService<UserDTO, HslUserQO>{

	/**
	 * 汇商旅后台重置密码
	 * @param id
	 */
	public void resetPassword(UserUpdatePasswordCommand command) throws  UserException;
	
	/**
	 * 新建用户浏览记录
	 * @param command
	 */
	public void createUserClickRecord(UserClickRecordCommand command);
	
	/**
	 * 查询用户浏览记录
	 * @param userClickRecordQO
	 * @return
	 */
	public List<MPSimpleDTO> queryUserClickRecord(HslUserClickRecordQO userClickRecordQO);
	
	/**
	 * 用户注册
	 * @param command
	 * @param clientKey
	 * @return
	 */
	public UserDTO register(UserRegisterCommand command,String clientKey) throws UserException;
	
	/**
	 * 发送短信
	 * @param command
	 */
	public String sendValidCode(SendValidCodeCommand command)throws UserException;
	/**
	 * 发送邮箱验证码
	 * @param command
	 */
	public String sendMailValidCode(SendMailCodeCommand command)throws UserException;
	
	
	/**
	 * 查询验证码
	 * @param hslSMSCodeQO
	 * @return
	 */
	public String querySendValidCode(HslSMSCodeQO hslSMSCodeQO)throws UserException;
	
	/**
	 * 校验用户名密码
	 * @param userCheckQO
	 * @return
	 * @throws UserException
	 */
	public UserDTO userCheck(HslUserQO userQO) throws  UserException;
	
	/**
	 * 查询用户
	 * @param userQO
	 * @return
	 */
	public UserDTO queryUser(HslUserBindAccountQO userQO) throws  UserException;
	
	/**
	 * 更新密码
	 * @param command
	 * @return
	 */
	public UserDTO updatePassword(UserUpdatePasswordCommand command) throws  UserException;
	
	/**
	 * 绑定微信帐号
	 * @param command
	 * @return
	 */
	public UserBindAccountDTO bindWXCommand(BindWXCommand command) throws  UserException;
	
	/**
	 * 修改企业用户信息
	 * @param command
	 * @return
	 */
	public UserDTO updateCompanyUser(UpdateCompanyUserCommand command);

	/**
	 * 修改绑定手机
	 * @param command
	 * @return
	 */
	public UserDTO updateBindMobile(UpdateBindMobileCommand command);

	/**
	 * 修改头像
	 * @参数：@param command
	 * @参数：@return
	 * @return:UserDTO
	 * @throws
	 */
	public UserDTO updateHeadImage(UpdateHeadImageCommand command);
	/**
	 * @方法功能说明：检查注册激活邮件
	 * @修改者名字：chenxy
	 * @修改时间：2014年11月20日下午4:46:42
	 * @修改内容：
	 * @参数：@param command
	 * @参数：@return
	 * @return:UserDTO
	 * @throws
	 */
	public UserDTO checkRegisterMail(CheckRegisterMailCommand command) throws UserException;
	/**
	 * @方法功能说明：重新发送激活邮件
	 * @修改者名字：chenxy
	 * @修改时间：2014年11月26日上午11:15:27
	 * @修改内容：
	 * @参数：@param command
	 * @参数：@return
	 * @参数：@throws UserException
	 * @return:String
	 * @throws
	 */
	public String sendRegisterMail(SendMailCodeCommand command) throws UserException;
	/**
	 * @方法功能说明：
	 * @修改者名字：yuqz
	 * @修改时间：2015年1月28日下午2:28:58
	 * @修改内容：
	 * @参数：@param command
	 * @参数：@throws UserException
	 * @return:void
	 * @throws
	 */
	public void checkValidCode(CheckValidCodeCommand command) throws UserException;
	
	/**
	 * 批量保存用户
	 * @param command
	 * @throws Exception
	 */
	public void batchRegister(BatchRegisterUserCommand  command) throws Exception;
	
	/**
	 * 查询用户数量
	 * @param qo
	 * @return 
	 */
	public Integer queryCount(HslUserQO qo);
}
