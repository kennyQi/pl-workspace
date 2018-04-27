package zzpl.app.service.local.saga;

import hg.common.component.BaseServiceImpl;

import java.util.Date;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import zzpl.app.dao.saga.ResetPasswordSagaDAO;
import zzpl.domain.model.event.SMSValidCodeCorrectEvent;
import zzpl.domain.model.event.SMSValidCodePastDueEvent;
import zzpl.domain.model.event.SMSValidCodeTooManyTimesEvent;
import zzpl.domain.model.event.SMSValidCodeWrongEvent;
import zzpl.domain.model.saga.ResetPasswordSaga;
import zzpl.pojo.command.saga.CheckSMSValidCommand;
import zzpl.pojo.command.saga.ResetPasswordCommand;
import zzpl.pojo.dto.user.status.ResetPasswordSagaStatus;
import zzpl.pojo.qo.saga.ResetPasswordSagaQO;

@Service
@Transactional
public class ResetPasswordSagaService
		extends
		BaseServiceImpl<ResetPasswordSaga, ResetPasswordSagaQO, ResetPasswordSagaDAO> {
	@Autowired
	private ResetPasswordSagaDAO resetPasswordSagaDAO;

	public String getResetPasswordSMSValid(ResetPasswordCommand command){
		ResetPasswordSaga resetPasswordSaga = new ResetPasswordSaga(command);
		resetPasswordSaga.setLastSendSMSDate(new Date());
		resetPasswordSaga.setUserID(command.getUserID());
		resetPasswordSagaDAO.save(resetPasswordSaga);
		return resetPasswordSaga.getId()+":"+resetPasswordSaga.getValidCode();
	}
	
	public int checkSMSValid(CheckSMSValidCommand command){
		ResetPasswordSaga resetPasswordSaga = resetPasswordSagaDAO.get(command.getSagaID());
		if(resetPasswordSaga.getSmsValidTimes()==resetPasswordSaga.getSmsValidMaxTimes()){
			SMSValidCodeTooManyTimesEvent event = new SMSValidCodeTooManyTimesEvent("", "", "");
			resetPasswordSaga.handle(event);
			resetPasswordSagaDAO.update(resetPasswordSaga);
			return ResetPasswordSagaStatus.CODE_TOO_MANY_TIMES;
		}else if(resetPasswordSaga.getSmsInvalidDate().before(new Date())){
			SMSValidCodePastDueEvent event = new SMSValidCodePastDueEvent("", "", "");
			resetPasswordSaga.handle(event);
			resetPasswordSagaDAO.update(resetPasswordSaga);
			return ResetPasswordSagaStatus.CODE_PAST_DUE;
		}else if (StringUtils.equals(command.getSmsValid(), resetPasswordSaga.getValidCode())) {
			SMSValidCodeCorrectEvent event = new SMSValidCodeCorrectEvent("", "", "");
			resetPasswordSaga.handle(event);
			resetPasswordSagaDAO.update(resetPasswordSaga);
			return ResetPasswordSagaStatus.CODE_CORRECT;
		}else{
			SMSValidCodeWrongEvent event = new SMSValidCodeWrongEvent("", "", "");
			resetPasswordSaga.handle(event);
			resetPasswordSagaDAO.update(resetPasswordSaga);
			return ResetPasswordSagaStatus.CODE_WRONG;
		}
	}
	
	@Override
	protected ResetPasswordSagaDAO getDao() {
		return resetPasswordSagaDAO;
	}

}
