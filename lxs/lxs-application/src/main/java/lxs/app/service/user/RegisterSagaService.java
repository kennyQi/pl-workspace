package lxs.app.service.user;

import hg.common.component.BaseServiceImpl;

import java.util.Date;

import lxs.app.dao.user.RegisterSagaDao;
import lxs.domain.model.user.event.sms.SMSValidCodeCorrectEvent;
import lxs.domain.model.user.event.sms.SMSValidCodePastDueEvent;
import lxs.domain.model.user.event.sms.SMSValidCodeTooManyTimesEvent;
import lxs.domain.model.user.event.sms.SMSValidCodeWrongEvent;
import lxs.domain.model.user.saga.RegisterSaga;
import lxs.pojo.command.user.GetSMSValidCodeCommand;
import lxs.pojo.command.user.RegisterCommand;
import lxs.pojo.qo.user.user.RegisterSagaQO;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSON;

@Service
@Transactional
public class RegisterSagaService extends
		BaseServiceImpl<RegisterSaga, RegisterSagaQO, RegisterSagaDao> {

	
	private final static Integer SMSValidCodeCorrect = 100;//手机验证成功
	private final static Integer SMSValidCodePastDue = 101;//验证码过期
	private final static Integer SMSValidCodeTooManyTimes = 102;//验证次数超过最大验证次数
	private final static Integer SMSValidCodeWrong = 103;//验证码输入错误
	
	@Autowired
	private RegisterSagaDao registerSagaDao;

	@Override
	protected RegisterSagaDao getDao() {
		return registerSagaDao;
	}

	/**
	 * 保存流程信息
	 * 
	 * @param GetSMSValidCodeCommand
	 * @return
	 */
	public void createRegisterSaga(GetSMSValidCodeCommand command) {
		RegisterSaga registerSaga = new RegisterSaga(command);
		registerSagaDao.save(registerSaga);
	}

	/**
	 * 根据电话号码查询流程信息
	 * 
	 * @param RegisterSagaQO
	 * @return registerSaga
	 */
	public RegisterSaga queryByMoble(RegisterSagaQO qo) {
		RegisterSaga registerSaga = getDao().queryUnique(qo);
		return registerSaga;
	}

	/**
	 * 根据sagaid查询流程信息
	 * 
	 * @param RegisterSagaQO
	 * @return registerSaga
	 */
	public RegisterSaga queryBySagaId(RegisterSagaQO qo) {
		RegisterSaga registerSaga = getDao().queryUnique(qo);
		return registerSaga;
	}

	/***
	 * 校验验证码
	 * @param command
	 * @param registerSaga
	 * @return
	 */
	public int checkVaildCode(RegisterCommand command,RegisterSaga registerSaga){
		if(registerSaga.getSmsValidTimes()==registerSaga.getSmsValidMaxTimes()){
			SMSValidCodeTooManyTimesEvent event = new SMSValidCodeTooManyTimesEvent("lxs.user.app.service.RegisterSagaService.checkVaildCode", JSON.toJSONString(command)+","+JSON.toJSONString(registerSaga)+",", "用户"+registerSaga.getMobile()+"验证次数大于最大验证次数");
			registerSaga.handle(event);
			registerSagaDao.update(registerSaga);
			return SMSValidCodeTooManyTimes;
		}else if(registerSaga.getSmsInvalidDate().before(new Date())){
			SMSValidCodePastDueEvent event = new SMSValidCodePastDueEvent("lxs.user.app.service.RegisterSagaService.checkVaildCode", JSON.toJSONString(command)+","+JSON.toJSONString(registerSaga)+",", "用户"+registerSaga.getMobile()+"验证码已过期");
			registerSaga.handle(event);
			registerSagaDao.update(registerSaga);
			return SMSValidCodePastDue;
		}else if (StringUtils.equals(command.getValidCode(), registerSaga.getValidCode())) {
			SMSValidCodeCorrectEvent event = new SMSValidCodeCorrectEvent("lxs.user.app.service.RegisterSagaService.checkVaildCode", JSON.toJSONString(command)+","+JSON.toJSONString(registerSaga)+",", "用户"+registerSaga.getMobile()+"验证成功");
			registerSaga.handle(event);
			registerSagaDao.update(registerSaga);
			return SMSValidCodeCorrect;
		}else{
			SMSValidCodeWrongEvent event = new SMSValidCodeWrongEvent("lxs.user.app.service.RegisterSagaService.checkVaildCode", JSON.toJSONString(command)+","+JSON.toJSONString(registerSaga)+",", "用户"+registerSaga.getMobile()+"验证输入错误");
			registerSaga.handle(event);
			registerSagaDao.update(registerSaga);
			return SMSValidCodeWrong;
		}
	}
}
