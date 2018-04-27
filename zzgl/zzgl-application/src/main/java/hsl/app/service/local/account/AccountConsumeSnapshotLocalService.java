package hsl.app.service.local.account;

import hg.common.component.BaseServiceImpl;
import hsl.app.dao.account.AccountConsumeSnapshotDao;
import hsl.domain.model.user.account.AccountConsumeSnapshot;
import hsl.pojo.qo.account.AccountConsumeSnapshotQO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class AccountConsumeSnapshotLocalService extends BaseServiceImpl<AccountConsumeSnapshot, AccountConsumeSnapshotQO,AccountConsumeSnapshotDao>{
	@Autowired
	private AccountConsumeSnapshotDao accountConsumeSnapshotDao;
	@Override
	protected AccountConsumeSnapshotDao getDao() {
		return accountConsumeSnapshotDao;
	}

}
