package hg.dzpw.app.service.local;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import hg.common.component.BaseServiceImpl;
import hg.dzpw.app.dao.TicketPolicySnapshotDao;
import hg.dzpw.app.pojo.qo.TicketPolicySnapshotQo;
import hg.dzpw.domain.model.policy.TicketPolicySnapshot;

@Service
@Transactional(rollbackFor = Exception.class)
public class TicketPolicySnapshotLocalService extends
		BaseServiceImpl<TicketPolicySnapshot, TicketPolicySnapshotQo, TicketPolicySnapshotDao> {

	@Autowired
	private TicketPolicySnapshotDao dao;
	
	@Override
	protected TicketPolicySnapshotDao getDao() {
		// TODO Auto-generated method stub
		return dao;
	}

}
