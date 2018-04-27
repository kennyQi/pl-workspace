package zzpl.app.service.local.jp;

import javax.annotation.Resource;

import hg.common.component.BaseServiceImpl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import zzpl.app.dao.jp.JPOrderDao;
import zzpl.domain.model.jp.JPOrder;
import zzpl.pojo.qo.jp.JPOrderQO;

@Service
@Transactional
public class JPOrderLocalService extends BaseServiceImpl<JPOrder, JPOrderQO, JPOrderDao> {

	@Resource
	private JPOrderDao jpOrderDao;
	@Override
	protected JPOrderDao getDao() {
		return jpOrderDao;
	}
	
}
