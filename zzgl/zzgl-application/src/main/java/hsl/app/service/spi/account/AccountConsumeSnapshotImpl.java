package hsl.app.service.spi.account;

import hsl.app.component.base.BaseSpiServiceImpl;
import hsl.app.service.local.account.AccountConsumeSnapshotLocalService;
import hsl.domain.model.user.account.AccountConsumeSnapshot;
import hsl.pojo.dto.account.AccountConsumeSnapshotDTO;
import hsl.pojo.qo.account.AccountConsumeSnapshotQO;
import hsl.spi.inter.account.AccountConsumeSnapshotService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AccountConsumeSnapshotImpl extends BaseSpiServiceImpl<AccountConsumeSnapshotDTO,AccountConsumeSnapshotQO,AccountConsumeSnapshotLocalService> implements AccountConsumeSnapshotService{
	@Autowired
	private AccountConsumeSnapshotLocalService accountConsumeSnapshotLocalService;
	
	@Override
	public void deleteById(String id) {
		accountConsumeSnapshotLocalService.deleteById(id);
	}

	@Override
	protected AccountConsumeSnapshotLocalService getService() {
		return accountConsumeSnapshotLocalService;
	}

	@Override
	protected Class<AccountConsumeSnapshotDTO> getDTOClass() {
		return AccountConsumeSnapshotDTO.class;
	}

	@Override
	public String update(String ids) {
		if(StringUtils.isNotBlank(ids)){
			ids=ids.substring(0,ids.lastIndexOf(","));
			String[] id=ids.split(",");
			for(int i=0;i<id.length;i++){
				//AccountConsumeSnapshot = EntityConvertUtils.convetDtoToEntity(AccountConsumeSnapshotCommand, AccountConsumeSnapshot.class);
				AccountConsumeSnapshot accountConsumeSnapshot=accountConsumeSnapshotLocalService.get(id[i]);
				accountConsumeSnapshot.setStatus(AccountConsumeSnapshot.STATUS_ZF);
				accountConsumeSnapshotLocalService.update(accountConsumeSnapshot);
			}
		}
		return "success";
	}
}
