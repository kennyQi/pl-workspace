package slfx.mp.app.service.local;

import hg.common.component.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import slfx.mp.app.dao.TCPolicyNoticeDAO;
import slfx.mp.app.pojo.qo.TCPolicyNoticeQO;
import slfx.mp.domain.model.supplierpolicy.TCPolicyNotice;

@Service
@Transactional
public class TCPolicyNoticeLocalService extends BaseServiceImpl<TCPolicyNotice, TCPolicyNoticeQO, TCPolicyNoticeDAO> {
	
	@Autowired
	private TCPolicyNoticeDAO dao;

	@Override
	protected TCPolicyNoticeDAO getDao() {
		return dao;
	}
	
}
