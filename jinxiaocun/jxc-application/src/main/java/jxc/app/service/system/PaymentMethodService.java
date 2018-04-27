package jxc.app.service.system;

import hg.common.component.BaseServiceImpl;
import hg.pojo.command.CreatePaymentMethodCommand;
import hg.pojo.command.ModifyPaymentMethodCommand;
import hg.pojo.command.RemovePaymentMethodCommand;
import hg.pojo.exception.SettingException;
import hg.pojo.qo.PaymentMethodQo;
import jxc.app.dao.system.PaymentMethodDao;
import jxc.app.util.JxcLogger;
import jxc.domain.model.system.PaymentMethod;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PaymentMethodService extends BaseServiceImpl<PaymentMethod, PaymentMethodQo, PaymentMethodDao> {
	@Autowired
	private PaymentMethodDao paymentMethodDao;

	@Override
	protected PaymentMethodDao getDao() {
		return paymentMethodDao;
	}

	@Autowired
	private JxcLogger logger;

	public void createPaymentMethod(CreatePaymentMethodCommand command) throws SettingException {
		if (paymentMethodNameIsExisted(command.getPaymentMethodName(), null)) {
			throw new SettingException(SettingException.PAYMENT_METHOD_NAME_EXIST, "结款方式已存在");
		}

		PaymentMethod paymentMethod = new PaymentMethod();
		paymentMethod.createPaymentMethod(command);
		save(paymentMethod);

		logger.debug(this.getClass(), "wkq", "添加结款方式:" + command.getPaymentMethodName(), command.getOperatorName(), command.getOperatorType(),
				command.getOperatorAccount(), "");

	}

	public void modifyPaymentMethod(ModifyPaymentMethodCommand command) throws SettingException {
		if (paymentMethodNameIsExisted(command.getPaymentMethodName(), command.getId())) {
			throw new SettingException(SettingException.PAYMENT_METHOD_NAME_EXIST, "结款方式已存在");
		}

		PaymentMethod paymentMethod = get(command.getId());
		paymentMethod.modifyPaymentMethod(command);
		update(paymentMethod);

		logger.debug(this.getClass(), "wkq", "修改结款方式:" + command.getPaymentMethodName(), command.getOperatorName(), command.getOperatorType(),
				command.getOperatorAccount(), "");

	}

	public void removePaymentMethod(RemovePaymentMethodCommand command) throws SettingException {
		if (paymentMethodIsUsing(command.getId())) {
			throw new SettingException(SettingException.PAYMENT_METHOD_NAME_EXIST, "结款方式已使用");
		}

		PaymentMethodQo qo = new PaymentMethodQo();
		qo.setId(command.getId());
		PaymentMethod paymentMethod = queryUnique(qo);
		paymentMethod.removePaymentMethod(command);
		update(paymentMethod);

		logger.debug(this.getClass(), "wkq", "删除结款方式:" + command.getId(), command.getOperatorName(), command.getOperatorType(), command.getOperatorAccount(),
				"");

	}

	private boolean paymentMethodIsUsing(String id) {
		return false;
	}

	private boolean paymentMethodNameIsExisted(String paymentMethodName, String id) {
		PaymentMethodQo qo = new PaymentMethodQo();
		qo.setPaymentMethodName(paymentMethodName);
		PaymentMethod paymentMethod = queryUnique(qo);
		if (id == null) {
			if (paymentMethod != null) {
				return true;
			}
		} else {
			if (paymentMethod != null && !id.equals(paymentMethod.getId())) {
				return true;
			}
		}
		return false;
	}
}
