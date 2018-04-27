package plfx.jp.app.service.local.pay.balances;

import hg.common.component.BaseServiceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import plfx.jp.app.dao.pay.balances.PayBalancesDAO;
import plfx.jp.domain.model.pay.balances.PayBalances;
import plfx.jp.qo.pay.balances.PayBalancesQO;
@Service
@Transactional
public class PayBalancesLocalService extends BaseServiceImpl<PayBalances, PayBalancesQO, PayBalancesDAO>{

	@Autowired
	PayBalancesDAO payBalancesDAO;
	
	@Override
	protected PayBalancesDAO getDao() {
		return payBalancesDAO;
	}

}
