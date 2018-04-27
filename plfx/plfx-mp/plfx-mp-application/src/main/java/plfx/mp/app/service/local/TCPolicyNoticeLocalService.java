package plfx.mp.app.service.local;

import hg.common.component.BaseServiceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import plfx.mp.app.dao.TCPolicyNoticeDAO;
import plfx.mp.app.pojo.qo.TCPolicyNoticeQO;
import plfx.mp.domain.model.supplierpolicy.TCPolicyNotice;

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
