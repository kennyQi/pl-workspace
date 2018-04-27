package lxs.app.service.user;
import hg.common.component.BaseServiceImpl;
import hg.common.util.SMSUtils;
import hg.log.clickrecord.ClickRateDao;
import hg.log.clickrecord.ClickRecordDao;
import hg.log.repository.ClickRateRepository;
import hg.log.repository.ClickRecordRepository;
import hg.log.util.HgLogger;
import hg.system.model.meta.City;
import hg.system.model.meta.Province;

import javax.annotation.Resource;

import lxs.app.dao.user.LxsUserDao;
import lxs.domain.model.user.Image;
import lxs.domain.model.user.LxsUser;
import lxs.domain.model.user.UserAuthInfo;
import lxs.domain.model.user.saga.RegisterSaga;
import lxs.pojo.command.user.ChangePasswordCommand;
import lxs.pojo.command.user.ModifyUserInfoCommand;
import lxs.pojo.command.user.RegisterCommand;
import lxs.pojo.command.user.ResetPasswordCommand;
import lxs.pojo.qo.user.user.LxsUserQO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@Transactional
public class LxsUserService extends BaseServiceImpl<LxsUser, LxsUserQO, LxsUserDao>{

	@Autowired
	private LxsUserDao userDao;
	@Autowired
	private ClickRecordRepository clickRecordRepository;
	@Autowired
	private ClickRecordDao clickRecordDao;
	@Autowired
	private ClickRateDao clickRateDao;
	@Autowired
	private ClickRateRepository clickRateRepository;
	
	@Resource
	private SMSUtils smsUtils;
	@Autowired
	private HgLogger hgLogger;
	/**
	 * 注册
	 * @param registerCommand
	 * @param registerSaga
	 */
	public void register(RegisterCommand registerCommand,RegisterSaga registerSaga) {
		LxsUser user = new LxsUser();
		user.register(registerCommand, registerSaga);
		userDao.save(user);
	}
	/**
	 * 修改资料
	 * @param command
	 * @param user
	 * @param province
	 * @param city
	 * @param area
	 */
	public void modifyUserInfo(ModifyUserInfoCommand command,LxsUser user,Province province,City city){
		Image image = new Image();
		image.setUrl(command.getImg_url());
		image.setImageId(command.getImg_image_id());
		image.setTitle(command.getImg_title());
		user.modifyInfo(command, province,city, image);
		userDao.update(user);
	}
	/**
	 * 修改密码
	 * @param command
	 * @param user
	 */
	public void ChangePassword(ChangePasswordCommand command,LxsUser user){
		UserAuthInfo authInfo = new UserAuthInfo();
		authInfo=user.getAuthInfo();
		authInfo.setPassword(command.getNewPassWord());
		user.setAuthInfo(authInfo);
		userDao.update(user);
	}
	/**
	 * 登录
	 * @param user
	 */
	public void Login(LxsUser user){
		user.login();
		userDao.update(user);
	}
	
	/**
	 * 更新密码
	 * @param command
	 */
	public void ResetPassword(ResetPasswordCommand command){
		LxsUser user = userDao.get(command.getUserId());
		user.updatePassword(command.getNewPassword());
		userDao.update(user);
	}
	
	@Override
	protected LxsUserDao getDao() {
		return userDao;
	}
	
}
