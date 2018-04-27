package zzpl.app.service.local.user;

import hg.common.component.BaseServiceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import zzpl.app.dao.user.CostCenterOrderDAO;
import zzpl.domain.model.user.CostCenterOrder;
import zzpl.pojo.qo.user.CostCenterOrderQO;

@Service
@Transactional
public class CostCenterOrderService extends
		BaseServiceImpl<CostCenterOrder, CostCenterOrderQO, CostCenterOrderDAO> {
	@Autowired
	private CostCenterOrderDAO centerOrderDAO;

	@Override
	protected CostCenterOrderDAO getDao() {
		return centerOrderDAO;
	}

}
